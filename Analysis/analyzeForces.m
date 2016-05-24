function [Tension,Forces] = analyzeForces(YFull)
% Y: Value of displacement/flow variables (rows) at each time step (columns)

% returns 'Tension' matrix.  Rows correspond to successive time steps,
% columns correspond to line segments, entries indicate the tension in N
% experienced by the corresponding line segment at the corresponding time

global Mooring

lines = Mooring.lines;
Num_Body = Mooring.NumBody;
Num_Node = Mooring.NumNode;
Num_Line = Mooring.NumLine;
Time = Mooring.Time;

% Number of line segments = No. of line nodes + 1
% Initialize tension structure array
n = Mooring.BodyDOF + Mooring.NodeDOF + Mooring.LineDOF; % Number of variables
NumberOfSteps = size(YFull,2);

Y = YFull(1:n,:);
Y_dot = YFull(n+1:2*n,:);
if NumberOfSteps == 1
    NumberOfSteps = size(Y,1);
    t = Time*ones(NumberOfSteps,1);
else
    t = Time;
end
GravityForceData = zeros(n,NumberOfSteps);
BuoyancyForceData = zeros(n,NumberOfSteps);
FluidForceData = zeros(n,NumberOfSteps);
AppliedForceData = zeros(n,NumberOfSteps);
SpringForceData = zeros(n,NumberOfSteps);
DampingForceData = zeros(n,NumberOfSteps);

for i = 1:NumberOfSteps
    
    GravityForceData(:,i) = addGravity(Y(:,i));
    BuoyancyForceData(:,i) = addBuoyancy(Y(:,i));
    FluidForceData(:,i) = addDrag(Y(:,i),Y_dot(:,i),t(i));
    AppliedForceData(:,i) = addApplied(Y(:,i),t(i));
    SpringForceData(:,i) = -PotentialEnergyVariation(Y(:,i));
    %DampingForceData(:,i) = -DampingVariation(Y(:,i),Y_dot(:,i));
end
Forces = struct('Gravity',GravityForceData,'Buoyancy',BuoyancyForceData,...
    'Fluid',FluidForceData,'Applied',AppliedForceData,...
    'Spring',SpringForceData,'Damping',DampingForceData);

Tension = cell(Num_Line,1);
for i = 1:Num_Line
    Tension{i} = zeros(lines(i).NumSegments,NumberOfSteps);
end

for i = 1:NumberOfSteps
    
    CounterVariable_LineNodeIndex = 6*Num_Body+1;
    CV_LNI = CounterVariable_LineNodeIndex; % Abvar (abbreviated variable, no other purpose)
    
    SolutionDisplacementVectorAtTime_i = Y(:,i);
    SDVATi = SolutionDisplacementVectorAtTime_i;
    for j = 1:Num_Line
        % Line Segment Axial Stiffness = lines.segK;
        % Line Segment Nominal Length = lines.Length;
        
        TensionUpToStep_i = Tension{j};
        % Find tension in first line segment
        qLineStart = findNodeGlobalPosition(SDVATi,lines(j).StartNode);
        
        FirstSegmentInstantaneousVector = SDVATi(CV_LNI:CV_LNI+2) - qLineStart;
        FirstSegmentInstantaneousLength = norm(FirstSegmentInstantaneousVector);
        FirstSegmentInstantaneousTension = lines(j).segK*(FirstSegmentInstantaneousLength - lines(j).segLength);
        TensionUpToStep_i(1,i) = FirstSegmentInstantaneousTension;
        
        for k = 1:lines(j).NumSegments-2
            CounterVariable_LineNodeIndex = CounterVariable_LineNodeIndex + 3;
            CV_LNI = CounterVariable_LineNodeIndex; % Abvar (abbreviated variable, no other purpose)
            
            SegmentVector = SDVATi(CV_LNI:CV_LNI+2) - SDVATi(CV_LNI-3:CV_LNI-1);
            SegmentInstantaneousLength = norm(SegmentVector);
            SegmentInstantaneousTension = lines(j).segK*(SegmentInstantaneousLength - lines(j).segLength);
            TensionUpToStep_i(k+1,i) = SegmentInstantaneousTension;
        end
        
        % Find tension in last line segment
        qLineEnd = findNodeGlobalPosition(SDVATi,lines(j).EndNode);
        LastSegmentInstantaneousVector = qLineEnd - SDVATi(CV_LNI:CV_LNI+2);
        LastSegmentInstantaneousLength = norm(LastSegmentInstantaneousVector);
        LastSegmentInstantaneousTension = lines(j).segK*(LastSegmentInstantaneousLength - lines(j).segLength);
        TensionUpToStep_i(end,i) = LastSegmentInstantaneousTension;
        
        Tension{j} = TensionUpToStep_i;
        
        CounterVariable_LineNodeIndex = CounterVariable_LineNodeIndex + 3;
        CV_LNI = CounterVariable_LineNodeIndex; % Abvar
    end
    
end