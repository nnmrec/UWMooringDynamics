function newLine(varargin)

global Mooring

% Increment mooring line count
Mooring.NumLine = Mooring.NumLine + 1;

% Ensure that name/value pairs were properly entered as function arguments
nArgs = length(varargin);
if round(nArgs/2) ~= nArgs/2
    error('newLine requires property name/value pairs')
end

% Gather optional name/value pairs into a two-row matrix
% Parameter names in first row, corresponding value in second row
ValueParaPair = reshape(varargin,2,[]);

% Name all Mooring Line parameters and assign default values
defaultName = sprintf('Line%g',Mooring.NumLine);
lineProperties = struct('Name',defaultName,...
    'LinearMass',1,...
    'Diameter',1,...
    'UnitK',1000,...
    'BendK',0,...
    'TangentialDrag',0.1,...
    'NormalDrag',0.1,...
    'DampingCoeff',0,...
    'NumSegments',1,...
    'Length',0,...
    'StartNode',1,...
    'EndNode',1,...
    'InternalNodes',[]);
    
% Create Cell Array of all parameter names
optionNames = fieldnames(lineProperties);

% Replace default parameter values with user-defined values
for i = 1:nArgs/2
    if any(strcmp(ValueParaPair{1,i},optionNames))
        lineProperties.(ValueParaPair{1,i}) = ValueParaPair{2,i};
    else % Error message for unrecognized parameter name
        error('"%s" is not a recognized parameter name you dullard',ValueParaPair{1,i})
    end
end

% add body to model data structure
Mooring.lines = [Mooring.lines,lineProperties];