function F = Animate(Y,timeFactor,domain,perspective)
% Y: Value of displacement variables (rows) at each time step (columns)
% timeFactor: Scaling factor, Animation time : Simulation time

global Mooring

lines = Mooring.lines;

Time = Mooring.Time;
Num_Body = Mooring.NumBody;
Num_Line = Mooring.NumLine;
Num_Segments = [lines(:).NumSegments];

n = size(Y,2); % Number of time steps
m = Mooring.TotalDOF; % Number of state variables

step = round((size(Time,1)-1)/(Time(end)*30*timeFactor));
if step == 0
    Y_compressed = Y;
    Time_compressed = Time;
else
    Y_compressed = [Y(1:m,1:step:n),Y(1:m,n)];
    Time_compressed = [Time(1:step:n);Time(n)];
end

pBody = zeros(8,3*Num_Body);
for i = 1:Num_Body
    xL = 2;
    yL = 2;
    zL = 2;
    pBody(1,3*(i-1)+1:3*i) = [-xL/2 yL/2 zL/2];
    pBody(2,3*(i-1)+1:3*i) = [-xL/2 yL/2 -zL/2];
    pBody(3,3*(i-1)+1:3*i) = [xL/2 yL/2 -zL/2];
    pBody(4,3*(i-1)+1:3*i) = [xL/2 yL/2 zL/2];
    pBody(5,3*(i-1)+1:3*i) = [-xL/2 -yL/2 zL/2];
    pBody(6,3*(i-1)+1:3*i) = [-xL/2 -yL/2 -zL/2];
    pBody(7,3*(i-1)+1:3*i) = [xL/2 -yL/2 -zL/2];
    pBody(8,3*(i-1)+1:3*i) = [xL/2 -yL/2 zL/2];
end

clear xL yL zL Dims
figure
F(1:n) = getframe; % Preallocate memory for frame structure array

for i = 1:n
    pCount = 6*Num_Body + 1;
    p = Y_compressed(:,i);
    for j = 1:Num_Line
        % Location of LineStart in global coordinates
        LineStart0 = findNodeGlobalPosition(p,lines(j).StartNode);

        % Location of LineEnd in global coordinates
        LineEnd0 = findNodeGlobalPosition(p,lines(j).EndNode);
        
        if Num_Segments(j) > 1
            plot3([LineStart0(1),p(pCount)],[LineStart0(2),p(pCount+1)],[LineStart0(3),p(pCount+2)]);
            hold on
            for l = 1:Num_Segments(j)-2
                plot3([p(pCount),p(pCount+3)],[p(pCount+1),p(pCount+4)],[p(pCount+2),p(pCount+5)]);
                pCount = pCount + 3;
            end
            plot3([p(pCount),LineEnd0(1)],[p(pCount+1),LineEnd0(2)],[p(pCount+2),LineEnd0(3)]);
            pCount = pCount + 3;
        else
            plot3([LineStart0(1),LineEnd0(1)],[LineStart0(2),LineEnd0(2)],[LineStart0(3),LineEnd0(3)]);
            hold on
        end
    end
    for j = 1:Num_Body
        pGlobal = trans(p(6*(j-1)+1:6*(j-1)+6),pBody(:,3*(j-1)+1:3*j));
        pMap = map(pGlobal);
        plot3(pMap(:,1),pMap(:,2),pMap(:,3));
    end
    
    axis equal
    axis(domain)
    view(perspective)
    xlabel('x [m]')
    ylabel('y [m]')
    zlabel('z [m]')
    
    grid on
    title(['\fontname{times}Time = ',num2str(Time_compressed(i),'%5.2f'),' s'],'FontSize',18);
    %text(-20,21,32.5,['\fontname{times}Time = ',num2str(Time(i),'%5.2f'),' s'],'FontSize',18)
    F(i) = getframe(gcf,[0 0 560 422]);
    hold off
end

% Add a ~1.5 second pause at the beginning and end of the show
FrameStructureWithPauseAtStartAndEnd(1:n+90) = getframe;
FrameStructureWithPauseAtStartAndEnd(1:45) = F(1);
FrameStructureWithPauseAtStartAndEnd(46:n+45) = F;
FrameStructureWithPauseAtStartAndEnd(n+46:n+90) = F(end);
F = FrameStructureWithPauseAtStartAndEnd;