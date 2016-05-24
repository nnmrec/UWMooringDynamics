function [xyzProbes,xyzBody] = GetProbeLocations(qMooring)

% Finds the coordinates of the velocity probes to be used in the CFD model 
% 

global GenSysPara bodies lines

%======== BAND-AID FIX ================
bodies(5).Type = 'Turbine';
bodies(6).Type = 'Turbine';
%======================================

NumTurbines = 0;
NumBuoyProbes = 0;
for i = 1:GenSysPara.NumBody
    if strcmp(bodies(i).Type,'Turbine')
        NumTurbines = NumTurbines + 1;
    else
        NumBuoyProbes = NumBuoyProbes + 1;
    end
end

RotorPosition = zeros(NumTurbines,3);
BuoyPosition = zeros(NumBuoyProbes,3);

% Find xyz coordinates of rotor hub if body is a turbine, or buoy COM if
% body is a buoy
RotorCount = 1;
BuoyCount = 1;
for i = 1:GenSysPara.NumBody
    qBodyIndex = [6*(i-1)+1;6*(i-1)+3]; 
    if strcmp(bodies(i).Type,'Turbine')
        RotorPosition(RotorCount,:) = qMooring(qBodyIndex(1):qBodyIndex(2));
        RotorCount = RotorCount + 1;
    else
        BuoyPosition(BuoyCount,:) = qMooring(qBodyIndex(1):qBodyIndex(2));
        BuoyCount = BuoyCount + 1;
    end
end

NumLineProbes = sum([lines.NumSegments]);
LineProbePosition = zeros(NumLineProbes,3);

% Find xyz coordinates of line segment midpoints
qLineIndex = 6*GenSysPara.NumBody + 3*GenSysPara.NumNode + 1 ;
LineProbeIndex = 1;
for i = 1:GenSysPara.NumLine
    % Find start and end node positions in global frame
    LineStartGlobal = findAttachPointGlobalPosition(qMooring,lines(i).LineStart);
    LineEndGlobal = findAttachPointGlobalPosition(qMooring,lines(i).LineEnd);
    
    if lines(i).NumSegments == 1
        ThisLineProbePosition = (LineEndGlobal - LineStartGlobal)/2 + LineStartGlobal;
        LineProbePosition(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        LineProbeIndex = LineProbeIndex + 1;
    else
        ThisLineProbePosition = (qMooring(qLineIndex:qLineIndex+2) - LineStartGlobal)/2 + LineStartGlobal;
        LineProbePosition(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        LineProbeIndex = LineProbeIndex + 1;
        for j = 1:lines(i).NumSegments - 2
            ThisLineProbePosition = (qMooring(qLineIndex+3:qLineIndex+5) - qMooring(qLineIndex:qLineIndex+2))/2 + qMooring(qLineIndex:qLineIndex+2);
            LineProbePosition(LineProbeIndex,:) = transpose(ThisLineProbePosition);
            qLineIndex = qLineIndex + 3;
            LineProbeIndex = LineProbeIndex + 1;
        end
        ThisLineProbePosition = (LineEndGlobal - qMooring(qLineIndex:qLineIndex+2))/2 + qMooring(qLineIndex:qLineIndex+2);
        LineProbePosition(LineProbeIndex,:) = transpose(ThisLineProbePosition);
        qLineIndex = qLineIndex + 3;
        LineProbeIndex = LineProbeIndex + 1;
    end
end

xyzBody = RotorPosition;
xyzProbes = [BuoyPosition;LineProbePosition];
        