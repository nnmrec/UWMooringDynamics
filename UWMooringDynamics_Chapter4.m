%% ====== DYNAMIC MODELLING OF COMPLIANT-MOORED TIDAL TURBINES ============

MooringModel = 'MezzanineConcept1Reduced';
% MooringModel = 'configSimple';
% MooringModel = 'RamanNair_Baddour_2001_TestProblem3Multi';

%% PART I: Initialize program =============================================
% STARTUP - start from a clean slate, and add any dependencies to the path
clear global
close all
clearvars
fclose('all');
clc

addpath(genpath([pwd filesep 'Analysis']));
addpath(genpath([pwd filesep 'CFD']));
addpath(genpath([pwd filesep 'CommonFunctions']));
addpath(genpath([pwd filesep 'ConfigurationFiles']));
addpath(genpath([pwd filesep 'Development']));
addpath(genpath([pwd filesep 'Documentation']));
addpath(genpath([pwd filesep 'ModelConstruction']));
addpath(genpath([pwd filesep 'Solvers']));
addpath(genpath([pwd filesep 'TargetFunctions']));

%% General System Parameters, set to default values
%  everything is fully initialized at this point?
global Mooring
Mooring = struct('t0',0,...
    'tFinal',20,...
    'tStep',0.1,...
    'NumBody',0,...
    'NumNode',0,...
    'NumLine',0,...
    'NumBodyCon',0,...
    'NumSeafloorCon',0,...
    'NumSlacklineCon',0,...
    'environment',[],...
    'bodies',[],...
    'nodes',[],...
    'lines',[],...
    'q0',[],...
    'lambda0',[],...
    'SlacklineConstraint',false);

Mooring.environment = struct('grav',9.81,...
    'rho_f',1020,...
    'StreamVelocity',@UniformVelocity);

% =========================================================================
%% PART II: Build system model ============================================
run(MooringModel)

Mooring.OptionsCFD = options;

if exist('t0','var')
    Mooring.t0 = t0;
end
if exist('tFinal','var')
    Mooring.tFinal = tFinal;
end
if exist('tStep','var')
    Mooring.tStep = tStep;
end
if exist('grav','var')
    Mooring.environment.grav = grav;
end
if exist('rho_f','var')
    Mooring.environment.rho_f = rho_f;
end
if exist('StreamVelocity','var')
    Mooring.environment.StreamVelocity = StreamVelocity;
end
if exist('CFD','var')
    Mooring.CFD = CFD;
end
if exist('SlacklineConstraint','var')
    Mooring.SlacklineConstraint = SlacklineConstraint;
end

clearvars -except Mooring

% =========================================================================
%% PART III: Complete system description based on input parameters ========
fprintf('\nFormatting system for computation...')

% Time step array
Mooring.Time = transpose(Mooring.t0:Mooring.tStep:Mooring.tFinal);

bodies = Mooring.bodies;
lines = Mooring.lines;
nodes = Mooring.nodes;

% Calculate number of degrees of freedom attributed to each feature type
BodyDOF = 6*Mooring.NumBody;
NodeDOF = 0; % initialize to zero, only free nodes add to DOF
for i = 1:Mooring.NumNode
    if strcmpi(nodes(i).Type,'Free')
        NodeDOF = NodeDOF + 3;
    end
end
LineDOF = 3*(sum([lines(:).NumSegments])-Mooring.NumLine);
Mooring.BodyDOF = BodyDOF;
Mooring.NodeDOF = NodeDOF;
Mooring.LineDOF = LineDOF;
Mooring.TotalDOF = BodyDOF + NodeDOF + LineDOF;

% Names vectors for indexing
BodyNames = cell(Mooring.NumBody,1);
for i = 1:Mooring.NumBody
    BodyNames{i} = bodies(i).Name;
end
NodeNames = cell(Mooring.NumNode,1);
for i = 1:Mooring.NumNode
    NodeNames{i} = nodes(i).Name;
end

% Convert string values for mooring line start/end nodes to their
% corresponding numerical index
for i = 1:Mooring.NumLine
    if ischar(lines(i).StartNode)
        while ~any(strcmp(lines(i).StartNode,NodeNames))
            fprintf('Unrecognized node name "%s" listed as Start Node for mooring line %s\n\n',lines(i).StartNode,lines(i).Name)
            lines(i).StartFrame = input('Please enter correct Start Node belonging to this line\n\n---->     ','s');
        end
        lines(i).StartNode = find(strcmp(lines(i).StartNode,NodeNames));
    end
    
    if ischar(lines(i).EndNode)
        while ~any(strcmp(lines(i).EndNode,NodeNames))
            fprintf('Unrecognized node name "%s" listed as End Node for mooring line %s\n\n',lines(i).EndNode,lines(i).Name)
            lines(i).EndFrame = input('Please enter correct End Frame belonging to this line\n\n---->     ','s');
        end
        lines(i).EndNode = find(strcmp(lines(i).EndNode,NodeNames));
    end
end

% Convert string values for node parent bodies to their corresponding numerical value
for i = 1:Mooring.NumNode
    if ischar(nodes(i).ParentBody)
        while ~any(strcmp(nodes(i).ParentBody,BodyNames))
            fprintf('Unrecognized body name "%s" listed as Parent Body for node %s\n\n',nodes(i).ParentBody,nodes(i).Name)
            nodes(i).ParentBody = input('Please enter correct Parent Body for this node\n\n---->     ','s');
        end
        nodes(i).ParentBody = find(strcmp(nodes(i).ParentBody,BodyNames));
    end        
end

% Initialize vectors for initial positions
initialBodyDisp = zeros(BodyDOF,1);
initialNodeDisp = zeros(NodeDOF,1);
initialLineDisp = zeros(LineDOF,1);

% Populate initial position vector for rigid bodies
for i = 1:Mooring.NumBody
    bodyRowIndices = (6*(i-1)+1):6*i;
    bodies(i).RowIndices(:) = bodyRowIndices';
    initialBodyDisp(bodyRowIndices) = [bodies(i).InitPosition;bodies(i).InitOrientation];
    Mooring.NumBodyCon = Mooring.NumBodyCon + size(find(bodies(i).BodyConstraints),1);
    Mooring.NumSeafloorCon = Mooring.NumSeafloorCon + 1;
end

% Find line properties for each mooring line
qLineCount = 1;
for i = 1:Mooring.NumLine
    % Location of LineStart in global coordinates
    StartNodeIndex = lines(i).StartNode;
    if strcmpi(nodes(StartNodeIndex).Type,'BodyFixed')
        ParentBodyIndex = nodes(StartNodeIndex).ParentBody;
        PBAngles = bodies(ParentBodyIndex).InitOrientation;
        T = EulerAngles(PBAngles(1),PBAngles(2),PBAngles(3)); % Transformation matrix
        LineStart0 = bodies(ParentBodyIndex).InitPosition + T*nodes(StartNodeIndex).InitPosition;
    else % Start Node is either FREE or FIXED to global frame
        LineStart0 = nodes(StartNodeIndex).InitPosition;
    end
    
    % Location of LineEnd in global coordinates
    EndNodeIndex = lines(i).EndNode;
    if strcmpi(nodes(EndNodeIndex).Type,'BodyFixed')
        ParentBodyIndex = nodes(EndNodeIndex).ParentBody;
        PBAngles = bodies(ParentBodyIndex).InitOrientation;
        T = EulerAngles(PBAngles(1),PBAngles(2),PBAngles(3)); % Transformation matrix
        LineEnd0 = bodies(ParentBodyIndex).InitPosition + T*nodes(EndNodeIndex).InitPosition;
    else
        LineEnd0 = nodes(EndNodeIndex).InitPosition;
    end
    
    lineVector = LineEnd0 - LineStart0;
    if lines(i).Length == 0
        lines(i).Length = norm(lineVector);
    end    
    
    % Unit vector in the direction of line axis (all line segments are initially parallel)
    unitVector = lineVector./lines(i).Length;
    segLength = lines(i).Length/lines(i).NumSegments;
    
    % Find initial position of line nodes in global coords assuming
    % straight line between start and end points
    InternalNodes = struct('q0',[0;0;0],'RowIndices',[0;0;0]);
    for j = 1:lines(i).NumSegments-1
        nodeCoord = LineStart0 + unitVector.*(j*segLength);
        InternalNodes(j).q0 = nodeCoord;
        initialLineDisp(qLineCount:qLineCount+2) = nodeCoord;
        RowIndices = BodyDOF + NodeDOF + qLineCount : BodyDOF + NodeDOF + qLineCount + 2;
        InternalNodes(j).RowIndices = RowIndices';
        qLineCount = qLineCount + 3;
    end
    
    lines(i).InternalNodes = InternalNodes;
    lines(i).segLength = segLength;
    lines(i).segMass = lines(i).LinearMass*segLength;
    lines(i).segK = lines(i).UnitK/segLength;
end

if Mooring.SlacklineConstraint
    Mooring.NumSlacklineCon = Mooring.NumSlacklineCon + sum([lines(:).NumSegments]);
end
Mooring.NumSeafloorCon = Mooring.NumSeafloorCon + sum([lines(:).NumSegments]) - Mooring.NumLine;

% Update structures in The Model
Mooring.bodies = bodies;
Mooring.nodes = nodes;
Mooring.lines = lines;
Mooring.q0 = [initialBodyDisp; initialNodeDisp; initialLineDisp];
Mooring.lambda0 = 0.1*ones(Mooring.NumBodyCon,1);
Mooring.mu0 = 0.1*ones(Mooring.NumSeafloorCon+Mooring.NumSlacklineCon,1);
Mooring.nu0 = 0.1*ones(Mooring.NumSeafloorCon+Mooring.NumSlacklineCon,1);

% BANDAID FIX
Mooring.VelocityAtProbes = [0,0,0];

clearvars -except Mooring

% Checkpoint: save all the stuff needed to solve the system
save('Assembled System')
fprintf('     Done!\n')

% =========================================================================
%% PART IV: Solve equations of motion =====================================
fprintf('\nSolving initial equilibrium position...')

q0 = [Mooring.q0;Mooring.lambda0;Mooring.mu0;Mooring.nu0];
plotInstant(q0,1,0);
drawnow
[qStatic,err,data] = UWMDNewton(@EvaluateStaticPhi,q0);
if ~err
    fprintf('     Done!\n')
    IsItGood = input('\nUse this static equilibrium position? (Y/N)\n\n---->     ','s');
    if strcmpi(IsItGood(1),'n')
        fprintf('\nOk, try again\n\n')
        return
    end
    Mooring.qStatic = qStatic;
else
    fprintf('\nUnable to find initial equilibrium position =(\n')
    return
end

if Mooring.CFD
    % CFD Coupling
        %Repeat static equilibrium calculation using fluid velocity at line
        %segment centers given in VelocityAtProbes to find drag on line segments,
        %and forces on bodies given in ForcesOnBodies
        %(drag on bodies is already accounted for in ForcesOnBodies, gravity and buoyancy is not)

    NumCFDIterations = 5;
    CFDtol = 1e-3;

    for i = 1:NumCFDIterations
        [xyzProbes,xyzBody] = GetProbeLocations(qStatic);
        % xyzProbes: table with x, y, z positions of line segment centers
        % xyzBody: table with x, y, z positions of body COMs
        [VelocityAtProbes,ForcesOnBodies] = runStarCCM(xyzProbes,xyzBody,Mooring);
        Mooring.VelocityAtProbes = VelocityAtProbes;
        Mooring.ForcesOnBodies = ForcesOnBodies;
        [qStaticNext,err,data] = UWMDNewton(@EvaluateStaticPhi,qStatic);
        if norm(qStaticNext - qStatic) < CFDtol
            qStatic = qStaticNext;
            break
        end
        qStatic = qStaticNext;
    end
end
    
    
if Mooring.DynamicModel
    fprintf('\nSolving dynamic model...')
    f0 = zeros(Mooring.TotalDOF,1);
    a0 = zeros(Mooring.TotalDOF,1);
    q0Dynamic = [qStatic;f0;a0];

    tic
    [qDynamic,err] = UWMDImpEulerSmoothedFBComp(@EvaluateDynamicPhi,q0Dynamic,Mooring.Time);
    toc
    if ~err
        fprintf('     Done!\n')
    else
        fprintf('\nDynamic simulation has exploded :O\n')
        return
    end
end

% =========================================================================
%% PART V: Analysisization ================================================

%UWMD_Main_Phase5

% =========================================================================