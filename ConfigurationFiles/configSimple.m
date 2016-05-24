% System Parameters =================================================
tFinal = 20;  % s
tStep = 0.05;  % s
StreamVelocity = @ZeroVelocity;
% ===================================================================

newBody('Name','SillyBuoy',...
    'Mass',1,...
    'MMoInertia',[1;1;1],...
    'Vol',0.5,...
    'InitPosition',[0;0;10],...
    'Drag',[1;1;1],...
    'TransDissipation',1,...
    'AngularDissipation',1,...
    'Area',[1;1;1],...
    'AppliedForcesGlobal',@SinusoidalForce,...
    'BodyConstraints',[0;0;0;1;1;1]);

newNode('Name','SillyNode',...
    'Type','BodyFixed',...
    'ParentBody','SillyBuoy',...
    'InitPosition',[0;0;0]);

newNode('Name','AnchorNode',...
    'Type','Fixed',...
    'InitPosition',[0;0;0]);

newLine('Name','Mooring',...
    'StartNode','AnchorNode',...
    'EndNode','SillyNode',...
    'LinearMass',8,...
    'Length',10,...
    'Diameter',1,...
    'UnitK',60000,...
    'TangentialDrag',1,...
    'NormalDrag',1,...
    'NumSegments',6,...
    'DampingCoeff',1);