% This function needs to be updated to include constrained body
% functionality

function eApplied = addApplied(q,t)

global Mooring

bodies = Mooring.bodies;
eApplied = zeros(Mooring.TotalDOF,1);

for i = 1:Mooring.NumBody
    BodyIndex = bodies(i).RowIndices;
    
    AppliedForcesGlobal = bodies.AppliedForcesGlobal;
    if ~isnumeric(AppliedForcesGlobal)
        AppliedForcesGlobal = AppliedForcesGlobal(q(BodyIndex(1)),q(BodyIndex(2)),q(BodyIndex(3)),t);
    end
    AppliedMomentsGlobal = bodies.AppliedMomentsGlobal;
    AppliedForcesBodyFixed = bodies.AppliedForcesBodyFixed;
    AppliedMomentsBodyFixed = bodies.AppliedMomentsBodyFixed;

    A = EulerAngles(q(BodyIndex(4)),q(BodyIndex(5)),q(BodyIndex(6)));
    e1 = [A*AppliedForcesBodyFixed;A*AppliedMomentsBodyFixed];
    totF = [AppliedForcesGlobal;AppliedMomentsGlobal]+e1;
    eApplied(BodyIndex) = eApplied(BodyIndex) + totF;
end