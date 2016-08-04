function hFig = plotInstant(Y,fps,instant)
%Y: Value of displacement variables (rows) at each time step (columns)

global Mooring               

if ~Mooring.OptionsCFD.graphics
    % need to disable graphics when running in console mode
    set(0,'DefaultFigureVisible','off')
end

%%
hAx1 = subplot(2,1,1);

hold off

lines = Mooring.lines;

Num_Segments = [lines(:).NumSegments];
Num_Body = Mooring.NumBody;
Num_Line = Mooring.NumLine;

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

i = round(fps*instant + 1); % Gives the column number of the instant being plotted

pCount = 6*Num_Body + 1;
p = Y(:,i);
for j = 1:Num_Line
    LineStart0 = findNodeGlobalPosition(p,lines(j).StartNode);
    LineEnd0 = findNodeGlobalPosition(p,lines(j).EndNode);
    
    if Num_Segments(j) > 1
        plot3([LineStart0(1);p(pCount:3:pCount+3*(Num_Segments(j)-2));LineEnd0(1)],...
            [LineStart0(2);p(pCount+1:3:pCount+3*(Num_Segments(j)-2)+1);LineEnd0(2)],...
            [LineStart0(3);p(pCount+2:3:pCount+3*(Num_Segments(j)-2)+2);LineEnd0(3)],...
            '-ko','LineWidth',1,'MarkerSize',3);
        hold on
        pCount = pCount + 3*(Num_Segments(j)-1);
    else
        plot3([LineStart0(1),LineEnd0(1)],[LineStart0(2),LineEnd0(2)],[LineStart0(3),LineEnd0(3)],'-ko','LineWidth',1,'MarkerSize',7);
        %pCount = pCount + 3;
        hold on
    end
end


for j = 1:Num_Body
    pGlobal = trans(p(6*(j-1)+1:6*j),pBody(:,3*(j-1)+1:3*j));
    pMap = map(pGlobal);
    plot3(pMap(:,1),pMap(:,2),pMap(:,3),'LineWidth',2);
end


axis equal
axis tight
grid on
box on
xlabel('x (m)')
ylabel('y (m)')
zlabel('z (m)')
axis([-50 200 -50 100 0 60])
view(0,0)
%text(-20,21,32.5,['\fontname{times}Time = ',num2str(instant,'%5.2f'),' s'],'FontSize',18)



%% same as the other subplot, copy it and change the view
hAx2 = subplot(2,1,2);
cla
copyobj(allchild(hAx1),hAx2);

axis equal
axis tight
grid on
box on
xlabel('x (m)')
ylabel('y (m)')
zlabel('z (m)')
axis([-50 200 -50 100 0 60])
view(0,90)


%%
drawnow
hFig = gcf;

