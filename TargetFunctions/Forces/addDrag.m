function eDrag = addDrag(q,f,tNum)

global Mooring

bodies = Mooring.bodies;
lines = Mooring.lines;

eDrag = zeros(size(q,1),1);

lineDrag = [lines(:).TangentialDrag;lines(:).NormalDrag];
lineDiameter = [lines(:).Diameter];
bodyDrag = [bodies(:).Drag];
bodyArea = [bodies(:).Area];
rho_f = Mooring.environment.rho_f;

Num_Body = Mooring.NumBody;
Num_Line = Mooring.NumLine;

%============ Section 1 - Fluid Force on Rigid body ============
if ~Mooring.CFD % No CFD coupling
    for i = 1:Num_Body
        BodyIndex = bodies(i).RowIndices;
        [Ux,Uy,Uz] = findVelocity(q(BodyIndex(1)),q(BodyIndex(2)),q(BodyIndex(3)),tNum);
        A = EulerAngles(q(BodyIndex(4)),q(BodyIndex(5)),q(BodyIndex(6)));
        u1 = A\([Ux;Uy;Uz] - f(BodyIndex(1:3))); % Relative fluid velocity at body COM (body-fixed frame)

        DragForce_BodyFrame = (1/2)*rho_f*bodyDrag(1:3,i).*bodyArea(1:3,i).*abs(u1).*u1;
        DragForce_Inertial = A*DragForce_BodyFrame;

        eDrag(BodyIndex(1:3)) = eDrag(BodyIndex(1:3)) + DragForce_Inertial;
    end
else % Yes CFD coupling
    VelocityAtProbes = Mooring.VelocityAtProbes;
    if size(VelocityAtProbes,2) <= 1 % First equilibrium calculation, no CFD data yet, abide by the StreamVelocity (or ramp velocity)
        for i = 1:Num_Body
            BodyIndex = bodies(i).RowIndices;
%             [Ux,Uy,Uz] = findVelocity(q(BodyIndex(1)),q(BodyIndex(2)),q(BodyIndex(3)),tNum);
            Ux = VelocityAtProbes(1);
            Uy = VelocityAtProbes(2);
            Uz = VelocityAtProbes(3);
            A = EulerAngles(q(BodyIndex(4)),q(BodyIndex(5)),q(BodyIndex(6)));
            u1 = A\([Ux;Uy;Uz] - f(BodyIndex(1:3))); % Relative fluid velocity at body COM (body-fixed frame)

            DragForce_BodyFrame = (1/2)*rho_f*bodyDrag(1:3,i).*bodyArea(1:3,i).*abs(u1).*u1;
            DragForce_Inertial = A*DragForce_BodyFrame;

            eDrag(BodyIndex(1:3)) = eDrag(BodyIndex(1:3)) + DragForce_Inertial;
        end
    else % CFD data is available
        BuoyProbeIndex = sum([lines.NumSegments]) + 1;
        BodiesThatAreNotTurbines = find(~ismember({bodies.Type},'turbine'));
        for i = 1:size(BodiesThatAreNotTurbines,2)
            BodyIndex = bodies(BodiesThatAreNotTurbines(i)).RowIndices;
            Ux = VelocityAtProbes(BuoyProbeIndex,1);
            Uy = VelocityAtProbes(BuoyProbeIndex,2);
            Uz = VelocityAtProbes(BuoyProbeIndex,3);
            BuoyProbeIndex = BuoyProbeIndex + 1;
            A = EulerAngles(q(BodyIndex(4)),q(BodyIndex(5)),q(BodyIndex(6)));
            u1 = A\([Ux;Uy;Uz] - f(BodyIndex(1:3))); % Relative fluid velocity at body COM (body-fixed frame)

            DragForce_BodyFrame = (1/2)*rho_f*bodyDrag(1:3,i).*bodyArea(1:3,i).*abs(u1).*u1;
            DragForce_Inertial = A*DragForce_BodyFrame;

            eDrag(BodyIndex(1:3)) = eDrag(BodyIndex(1:3)) + DragForce_Inertial;
        end
    end
end

%============ Section 2 - Mooring Line Drag ============
LineProbeIndex = 1;
for i = 1:Num_Line
    % Location of LineStart/End in global coordinates
    LineStart0 = findNodeGlobalPosition(q,lines(i).StartNode);
    vLineStart0 = findNodeGlobalVelocity(q,f,lines(i).StartNode);
    LineEnd0 = findNodeGlobalPosition(q,lines(i).EndNode);
    vLineEnd0 = findNodeGlobalVelocity(q,f,lines(i).EndNode);
    
    if lines(i).NumSegments == 1  % Code for condition Num_Segments(i)==1 needs to be updated to include functionality for rigid links between bodies

        SegVector = LineStart0 - LineEnd0; % Vector from attachment point to first node
        l = norm(SegVector); % Instantaneous length of first cable segment
        segUnit = SegVector/l; % unit vector in direction of segVector

        vCOM = (1/2)*(vLineStart0 + vLineEnd0);
        xCOM = (LineStart0(1) - LineEnd0(1))/2 + LineEnd0(1);
        yCOM = (LineStart0(2) - LineEnd0(2))/2 + LineEnd0(2);
        zCOM = (LineStart0(3) - LineEnd0(3))/2 + LineEnd0(3);
        
        if ~Mooring.CFD
            [Ux,Uy,Uz] = findVelocity(xCOM,yCOM,zCOM,tNum);
        elseif size(VelocityAtProbes,2) > 1             % CFD data is available
            Ux = VelocityAtProbes(LineProbeIndex,1);
            Uy = VelocityAtProbes(LineProbeIndex,2);
            Uz = VelocityAtProbes(LineProbeIndex,3);
            LineProbeIndex = LineProbeIndex + 1;
        else                                            % CFD data is not available, abide by the StreamVelocity (or ramp velocity)
            % [Ux,Uy,Uz] = findVelocity(xCOM,yCOM,zCOM,tNum);
            Ux = VelocityAtProbes(1);
            Uy = VelocityAtProbes(2);
            Uz = VelocityAtProbes(3);
        end
        
        u = [Ux;Uy;Uz] - vCOM; % Relative stream velocity at COM of segment

        uTan = u(1)*segUnit(1) + u(2)*segUnit(2) + u(3)*segUnit(3);
        uNorm = sqrt(abs((u(1)^2+u(2)^2+u(3)^2) - uTan^2));
        if uNorm ~= 0
            segNorm = (u-uTan*segUnit)/uNorm;
        else
            segNorm = 0;
        end

        ATan = pi*lineDiameter(i)*l;
        ANorm = lineDiameter(i)*l;

        dragF = (1/2)*rho_f*(lineDrag(1,i)*ATan*abs(uTan)*uTan*segUnit + lineDrag(2,i)*ANorm*abs(uNorm)*uNorm*segNorm);
        
        % Apply force to first node
        eDrag = eDrag + includeForceOnNode(0.5*dragF,lines(i).StartNode,q);
        % Apply force to second node
        eDrag = eDrag + includeForceOnNode(0.5*dragF,lines(i).EndNode,q);

    else % Number of line segments in line(i) is >1
        InternalNodes = lines(i).InternalNodes;
        SegVector = q(InternalNodes(1).RowIndices) - LineStart0;
        
        l = sqrt(SegVector(1)^2+SegVector(2)^2+SegVector(3)^2); % Instantaneous length of first cable segment 
        segUnit = SegVector/l; % unit vector in direction of segVector
        
        COM = (q(InternalNodes(1).RowIndices) - LineStart0)/2 + LineStart0;
        vCOM = (1/2)*(vLineStart0 + f(InternalNodes(1).RowIndices));
        
        if ~Mooring.CFD
            [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
        elseif size(VelocityAtProbes,2) > 1             % CFD data is available
            Ux = VelocityAtProbes(LineProbeIndex,1);
            Uy = VelocityAtProbes(LineProbeIndex,2);
            Uz = VelocityAtProbes(LineProbeIndex,3);
            LineProbeIndex = LineProbeIndex + 1;
        else                                            % CFD data was not available, abide by the StreamVelocity (or ramp velocity)
            % [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
            Ux = VelocityAtProbes(1);
            Uy = VelocityAtProbes(2);
            Uz = VelocityAtProbes(3);
            
        end
        u = [Ux;Uy;Uz] - vCOM;

        uTan = u(1)*segUnit(1) + u(2)*segUnit(2) + u(3)*segUnit(3);
        uNorm = sqrt(abs((u(1)^2+u(2)^2+u(3)^2) - uTan^2));
        if uNorm ~= 0
            segNorm = (u-uTan*segUnit)/uNorm;
        else
            segNorm = 0;
        end

        ATan = pi*lineDiameter(i)*l;
        ANorm = lineDiameter(i)*l;
        
        % Drag force on first line segment
        dragF = (1/2)*rho_f*(lineDrag(1,i)*ATan*abs(uTan)*uTan*segUnit + lineDrag(2,i)*ANorm*abs(uNorm)*uNorm*segNorm);
        
        % Apply force to first node
        eDrag = eDrag + includeForceOnNode(0.5*dragF,lines(i).StartNode,q);
        
        eDrag(InternalNodes(1).RowIndices) = eDrag(InternalNodes(1).RowIndices) + 0.5*dragF; % Drag force on first line node (not attachment point)

        for j = 1:lines(i).NumSegments-2
            SegVector = q(InternalNodes(j+1).RowIndices) - q(InternalNodes(j).RowIndices); % Vector from previous node to current node
            l = sqrt(SegVector(1)^2+SegVector(2)^2+SegVector(3)^2); % Instantaneous length of cable segment
            segUnit = SegVector/l; % unit vector in direction of segVector

            vCOM = (1/2)*(f(InternalNodes(j+1).RowIndices) + f(InternalNodes(j).RowIndices));
            COM = (q(InternalNodes(j+1).RowIndices) - q(InternalNodes(j).RowIndices))/2 + q(InternalNodes(j).RowIndices);
            if ~Mooring.CFD
                [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
            elseif size(VelocityAtProbes,2) > 1             % CFD data is available
                Ux = VelocityAtProbes(LineProbeIndex,1);
                Uy = VelocityAtProbes(LineProbeIndex,2);
                Uz = VelocityAtProbes(LineProbeIndex,3);
                LineProbeIndex = LineProbeIndex + 1;
            else                                            % CFD data is not available, abide by the StreamVelocity (or ramp velocity)
                % [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
                Ux = VelocityAtProbes(1);
                Uy = VelocityAtProbes(2);
                Uz = VelocityAtProbes(3);
            end
            u = [Ux;Uy;Uz] - vCOM;
            uTan = u(1)*segUnit(1) + u(2)*segUnit(2) + u(3)*segUnit(3);
            uNorm = sqrt(abs((u(1)^2+u(2)^2+u(3)^2) - uTan^2));
            if uNorm ~= 0
                segNorm = (u-uTan*segUnit)/uNorm;
            else
                segNorm = 0;
            end

            ATan = pi*lineDiameter(i)*l;
            ANorm = lineDiameter(i)*l;

            dragF = (1/2)*rho_f*(lineDrag(1,i)*ATan*abs(uTan)*uTan*segUnit + lineDrag(2,i)*ANorm*abs(uNorm)*uNorm*segNorm);

            eDrag(InternalNodes(j).RowIndices) = eDrag(InternalNodes(j).RowIndices) + 0.5*dragF;
            eDrag(InternalNodes(j+1).RowIndices) = eDrag(InternalNodes(j+1).RowIndices) + 0.5*dragF;
        end
        
        SegVector = LineEnd0 - q(InternalNodes(end).RowIndices);

        l = sqrt(SegVector(1)^2+SegVector(2)^2+SegVector(3)^2); % Instantaneous length of last cable segment 
        segUnit = SegVector/l; % unit vector in direction of segVector
        
        COM = (LineEnd0 - q(InternalNodes(end).RowIndices))/2 + q(InternalNodes(end).RowIndices);
        vCOM = (1/2)*(f(InternalNodes(end).RowIndices) + vLineEnd0);
        
        if ~Mooring.CFD
            [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
        elseif size(VelocityAtProbes,2) > 1             % CFD data is available
            Ux = VelocityAtProbes(LineProbeIndex,1);
            Uy = VelocityAtProbes(LineProbeIndex,2);
            Uz = VelocityAtProbes(LineProbeIndex,3);
            LineProbeIndex = LineProbeIndex + 1;
        else                                            % CFD data was not available, abide by the StreamVelocity (or ramp velocity)
            % [Ux,Uy,Uz] = findVelocity(COM(1),COM(2),COM(3),tNum);
            Ux = VelocityAtProbes(1);
            Uy = VelocityAtProbes(2);
            Uz = VelocityAtProbes(3);
        end
        u = [Ux;Uy;Uz] - vCOM;

        uTan = u(1)*segUnit(1) + u(2)*segUnit(2) + u(3)*segUnit(3);
        uNorm = sqrt(abs((u(1)^2+u(2)^2+u(3)^2) - uTan^2));
        if uNorm ~= 0
            segNorm = (u-uTan*segUnit)/uNorm;
        else
            segNorm = 0;
        end
        
        ATan = pi*lineDiameter(i)*l;
        ANorm = lineDiameter(i)*l;

        dragF = (1/2)*rho_f*(lineDrag(1,i)*ATan*abs(uTan)*uTan*segUnit + lineDrag(2,i)*ANorm*abs(uNorm)*uNorm*segNorm);
        
        eDrag(InternalNodes(end).RowIndices) = eDrag(InternalNodes(end).RowIndices) + 0.5*dragF; % Drag force on last line node (not attachment point)
        % Apply force to last node
        eDrag = eDrag + includeForceOnNode(0.5*dragF,lines(i).EndNode,q);
    end
end
end

function [Ux,Uy,Uz] = findVelocity(x,y,z,t)
global Mooring
StreamVelocityFunction = Mooring.environment.StreamVelocity; % Designates a function handle
[Ux,Uy,Uz] = StreamVelocityFunction(x,y,z,t);
end

