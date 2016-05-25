function [xyzProbes,xyzBody] = GetProbeLocations(qMooring)

% Finds the coordinates of the velocity probes to be used in the CFD model 
% 

global Mooring
bodies = Mooring.bodies;
nodes = Mooring.nodes;
lines = Mooring.lines;

%======== BAND-AID FIX ================
bodies(5).Type = 'Turbine';
bodies(6).Type = 'Turbine';
%======================================


xyzBody = zeros(Mooring.NumBody,3);

% Find xyz coordinates of body COM

for i = 1:Mooring.NumBody
    qBodyIndex = [6*(i-1)+1;6*(i-1)+3]; 
    xyzBody(i,:) = qMooring(qBodyIndex(1):qBodyIndex(2));
end

NumLineProbes = sum([lines.NumSegments]);
xyzProbes = zeros(NumLineProbes,3);

% Find xyz coordinates of line segment midpoints
qLineIndex = Mooring.BodyDOF + Mooring.NodeDOF + 1 ;
LineProbeIndex = 1;
for i = 1:Mooring.NumLine
    % Find start and end node positions in global frame
    LineStartGlobal = findNodeGlobalPosition(qMooring,lines(i).StartNode);
    LineEndGlobal = findNodeGlobalPosition(qMooring,lines(i).EndNode);
    
    if lines(i).NumSegments == 1
        ThisLineProbePosition = (LineEndGlobal - LineStartGlobal)/2 + LineStartGlobal;
        xyzProbes(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        LineProbeIndex = LineProbeIndex + 1;
    else
        ThisLineProbePosition = (qMooring(qLineIndex:qLineIndex+2) - LineStartGlobal)/2 + LineStartGlobal;
        xyzProbes(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        LineProbeIndex = LineProbeIndex + 1;
        for j = 1:lines(i).NumSegments - 2
            ThisLineProbePosition = (qMooring(qLineIndex+3:qLineIndex+5) - qMooring(qLineIndex:qLineIndex+2))/2 + qMooring(qLineIndex:qLineIndex+2);
            xyzProbes(LineProbeIndex,:) = transpose(ThisLineProbePosition);
            qLineIndex = qLineIndex + 3;
            LineProbeIndex = LineProbeIndex + 1;
        end
        ThisLineProbePosition = (LineEndGlobal - qMooring(qLineIndex:qLineIndex+2))/2 + qMooring(qLineIndex:qLineIndex+2);
        xyzProbes(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        qLineIndex = qLineIndex + 3;
        LineProbeIndex = LineProbeIndex + 1;
    end
end
        