function D_f = DampingVariation(q,f)

global MooringModelData
lines = MooringModelData.lines;
bodies = MooringModelData.bodies;

% Initialize Spring Force vector F_spring
F_damping = zeros(size(q,1),1);

Num_Body = MooringModelData.NumBody;
Num_ConBody = MooringModelData.NumConBody;
Num_Lines = MooringModelData.NumLine;
Num_Segments = [lines(:).NumSegments];

for i = 1:Num_Body + Num_ConBody
    B = [bodies(i).TransDissipation;bodies(i).AngularDissipation];
    if bodies(i).FixedFrame == 0
        wx = cos(q(6*(i-1)+5))*cos(q(6*(i-1)+6))*f(6*(i-1)+4) + sin(q(6*(i-1)+6))*f(6*(i-1)+5);
        wy = -cos(q(6*(i-1)+5))*sin(q(6*(i-1)+6))*f(6*(i-1)+4) + cos(q(6*(i-1)+6))*f(6*(i-1)+5);
        wz = sin(q(6*(i-1)+5))*f(6*(i-1)+4) + f(6*(i-1)+6);  % From 1-2-3 Euler Angles, Fabien Pg. 64
        %D_f = D_f + (1/2)*(B(1)*(f(6*(i-1)+1)^2+f(6*(i-1)+2)^2+f(6*(i-1)+3)^2)+B(2)*(wx^2+wy^2+wz^2));
        % Assuming small angle
        F_damping(6*(i-1)+1:6*(i-1)+6) = -[B(1)*f(6*(i-1)+1:6*(i-1)+3);B(2)*f(6*(i-1)+4:6*(i-1)+6)];
    else
        j = bodies(i).FixedFrame;
        wx = cos(q(6*(j-1)+5))*cos(q(6*(j-1)+6))*f(6*(j-1)+4) + sin(q(6*(j-1)+6))*f(6*(j-1)+5);
        wy = -cos(q(6*(j-1)+5))*sin(q(6*(j-1)+6))*f(6*(j-1)+4) + cos(q(6*(j-1)+6))*f(6*(j-1)+5);
        wz = sin(q(6*(j-1)+5))*f(6*(j-1)+4) + f(6*(j-1)+6);
        vel = [f(6*(j-1)+1);f(6*(j-1)+2);f(6*(j-1)+3)] + cross([wx;wy;wz],bodies(i).InitPosition);
        %D_f = D_f + (1/2)*(B(1)*(vel(1)^2+vel(2)^2+vel(3)^2)+B(2)*(wx^2+wy^2+wz^2));
    end
end

fCount = 6*Num_Body + 1;

for i = 1:Num_Lines
    B = lines(i).DampingCoeff;
    ThisLineDOF = 3*(Num_Segments(i)-1);
    F_damping(fCount:fCount+ThisLineDOF-1) = -B*f(fCount:fCount+ThisLineDOF-1);
    fCount = fCount+ThisLineDOF;
end

D_f = -F_damping;