function newBody(varargin)

global Mooring

% Increment rigid body count
Mooring.NumBody = Mooring.NumBody + 1;

% Ensure that name/value pairs were properly entered as function arguments
nArgs = length(varargin);
if round(nArgs/2) ~= nArgs/2
    error('newBody requires property name and value pairs')
end

% Gather optional name/value pairs into a two-row matrix
% Parameter names in first row, corresponding value in second row
ValueParaPair = reshape(varargin,2,[]);

% Name all Rigid Body parameters and assign default values
defName = sprintf('Body%g',Mooring.NumBody);
rigidBodyProperties = struct('Name',defName,...
    'Mass',1,...
    'MMoInertia',[1;1;1],...
    'Vol',2,...
    'AddedMass',0,...
    'Drag',[0.1;0.1;0.1],...
    'Area',[1;1;1],...
    'TransDissipation',0,...
    'AngularDissipation',0,...
    'InitPosition',[0;0;0],...
    'InitOrientation',[0;0;0],...
    'AppliedForcesGlobal',[0;0;0],...
    'AppliedMomentsGlobal',[0;0;0],...
    'AppliedForcesBodyFixed',[0;0;0],...
    'AppliedMomentsBodyFixed',[0;0;0],...
    'BodyConstraints',[0;0;0;0;0;0],...
    'RowIndices',zeros(6,1));

% Create Cell Array of all parameter names
optionNames = fieldnames(rigidBodyProperties);

% Replace default parameter values with user-defined values
for i = 1:nArgs/2
    if any(strcmp(ValueParaPair{1,i},optionNames)) % If the input argument name matches a property name
        rigidBodyProperties.(ValueParaPair{1,i}) = ValueParaPair{2,i};
    else % Error message for unrecognized parameter name
        error('"%s" is not a recognized parameter name you dullard',ValueParaPair{1,i})
    end
end

% add body to model data structure
Mooring.bodies = [Mooring.bodies,rigidBodyProperties];