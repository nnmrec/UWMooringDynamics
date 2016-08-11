%% General System Parameters ===============================================
% Mooring options
startEquil = true;
seafloorConstraint = false;
MWL = 60;
rho_f = 1020; % [kg/m^3]
tf = 30;  % s
tStep = 0.02;  % s
domain = [-20 140 -20 80 0 40];
perspective = [105 10]; % [Azimuth, Elevation]
DynamicModel = false;
SlacklineConstraint = false;
CFD = true;                                                 % use CFD to compute forces and velocities on mooring elements
InletVelocity = [2.0; 0; 0];                                % the final inflow speed
RampVel = 2.0;                                              % velocity increment until final speed (set to same as InletVelocity to skip the ramping steps)

% CFD options
OptionsCFD.graphics         = true;                         % turn off all matlab graphics and prompts (hide in background), when running on HPC
OptionsCFD.runOnHPC         = false;                        % option to run a PBS script for HPC systems (like Hyak), or run locally
OptionsCFD.AMR              = false;                        % Use Adaptive Mesh Refinement? true or false.
OptionsCFD.nCPUs            = 16;                           % number of CPU cores to run in parallel (check that it matches your PBS submit job script)
OptionsCFD.control          = 'RPM';                        % choose 'RPM' for rotor speed control.  choose 'TSR' for local tip-speed-ratio control
OptionsCFD.nUpdateRPM       = 1;                            % number of inner loops to update the rotor speed based on inflow velocity, should be 1 or greater (this options is probably dependent on max iterations of RANS model)
OptionsCFD.NumCFDIterations = 20;                            % number of iterations between mooring and CFD
OptionsCFD.CFDtol           = 1e-3;                         % convergence criteria between mooring and CFD coupling (residual is defined in the Newton solver)

% =========================================================================
%% It would be nice to define a new origin, and everything below is defined to this origin
%  this would allow easier alignment with the CFD domain (or CFD domain
%  should just center itself around the min/max + buffer of the mooring nodes
OptionsCFD.origin = [domain(1) + (domain(1)+domain(2))./2, ...
                     domain(3) + (domain(3)+domain(4))./2, ...
                     domain(5) + (domain(5)+domain(6))./2];

%% Corner Buoys
BuoyNames = {'CornerBuoy1','CornerBuoy2','CornerBuoy3','CornerBuoy4'};
BuoyType  = {'buoy','buoy','buoy','buoy'};
BuoyPositions = [[0;0;30],[0;60;30],[120;0;30],[120;60;30]];
% BuoyPositions = ? % an altertnative idea could to these positions and types with a GUI

% regarding matching the origin with teh CFD domain, I think would be more easily acheived if you
% write a new function that does the translation/scaling/rotation, like:
% BuoyPositions = bsxfun(@plus, BuoyPositions, origin);         

for i = 1:size(BuoyNames,2);
    newBody('Name',BuoyNames{i},...
        'Type',BuoyType{i},...
        'Mass',50,...
        'MMoInertia',[4;4;4],...
        'Vol',10,...
        'InitPosition',BuoyPositions(:,i),...
        'Drag',[1.2;1.2;1.2],...
        'TransDissipation',5,...
        'AngularDissipation',50,...
        'Dimensions',[6;6;6],...
        'BodyConstraints',[0;0;0;1;1;1]);
end

%% Buoy Nodes
BuoyNodeNames = {'BuoyNode1','BuoyNode2','BuoyNode3','BuoyNode4'};
for i = 1:size(BuoyNodeNames,2)
    newNode('Name',BuoyNodeNames{i},...
        'Type','BodyFixed',...
        'ParentBody',BuoyNames{i},...
        'InitPosition',[0;0;0]);
end

%% Anchor Nodes
AnchorNodeNames = {'AnchorNode1','AnchorNode2','AnchorNode3','AnchorNode4'};
AnchorNodePositions = [[-15;-15;0],[-15;75;0],[135;-15;0],[135;75;0]];
for i = 1:size(AnchorNodeNames,2)
    newNode('Name',AnchorNodeNames{i},...
        'Type','Fixed',...
        'InitPosition',AnchorNodePositions(:,i));
end

%% Corner and side perimeter lines
CornerLineNames = {'CornerLine1','CornerLine2','CornerLine3','CornerLine4',...
    'Perimeter1','Perimeter2'};
CornerLineStartNodes = {AnchorNodeNames{1,:},BuoyNodeNames{1},BuoyNodeNames{2}};
CornerLineEndNodes = {BuoyNodeNames{1,:},BuoyNodeNames{3},BuoyNodeNames{4}};
CornerLineSegments = [6,6,6,6,12,12];
CornerLineLength = [36,36,36,36,90,90];
for i = 1:size(CornerLineNames,2)
    newLine('Name',CornerLineNames{i},...
        'StartNode',CornerLineStartNodes{i},...
        'EndNode',CornerLineEndNodes{i},...
        'LinearMass',4,...
        'Diameter',0.06,...
        'Length',CornerLineLength(i),...
        'UnitK',480000,...
        'TangentialDrag',0.6,...
        'NormalDrag',0.6,...
        'NumSegments',CornerLineSegments(i),...
        'DampingCoeff',2000);
end

%% Turbines
TurbineNames = {'Turbine1','Turbine2'};
TurbineType  = {'turbine','turbine'};
TurbinePositions = [[0;30;30],[120;30;30]];
TurbineNode1Names = {'Turbine1Node1','Turbine2Node1'};
TurbineNode2Names = {'Turbine1Node2','Turbine2Node2'};
for i = 1:size(TurbineNames,2)
    newBody('Name',TurbineNames{i},...
        'Type',TurbineType{i},...
        'Mass',19300,...
        'MMoInertia',[4;4;4],...
        'Vol',1.6*9.67,...
        'InitPosition',TurbinePositions(:,i),...
        'Drag',[1;1;1],...
        'TransDissipation',5,...
        'AngularDissipation',50,...
        'Dimensions',[10;25;25]);
    
    newNode('Name',TurbineNode1Names{i},...
        'Type','BodyFixed',...
        'ParentBody',TurbineNames{i},...
        'InitPosition',[0;-3;1]);
    newNode('Name',TurbineNode2Names{i},...
        'Type','BodyFixed',...
        'ParentBody',TurbineNames{i},...
        'InitPosition',[0;3;1]);
end
    
% cell array of turbine properties, used by the CFD module (NOTE: avoid using colons, :, in these names (or else!) ... a starccm thing)
% 8 columns:        turbine_type,rotor_rpm,nx,ny,nz,rotor_radius,hub_radius,rotor_thick
Turbines.data_cfd =	{
                    'Mezzanine', 9.2, 1.0, 0.0, 0.0, 12.5, 0.0, 2.0;
                    'Mezzanine', 9.2, 1.0, 0.0, 0.0, 12.5, 0.0, 2.0;
                 	};         
                
%% Turbine Lines
TurbineLineNames = {'TurbineLine1','TurbineLine2','TurbineLine3','TurbineLine4'};
TurbineStartNodes = {'BuoyNode1','Turbine1Node2','BuoyNode3','Turbine2Node2'};
TurbineEndNodes = {'Turbine1Node1','BuoyNode2','Turbine2Node1','BuoyNode4'};
for i = 1:size(TurbineLineNames,2)
    newLine('Name',TurbineLineNames{i},...
        'StartNode',TurbineStartNodes{i},...
        'EndNode',TurbineEndNodes{i},...
        'LinearMass',4,...
        'Diameter',0.06,...
        'UnitK',480000,...
        'TangentialDrag',0.6,...
        'Length',20,...
        'NormalDrag',0.6,...
        'NumSegments',4,...
        'DampingCoeff',2000);
end

%%
