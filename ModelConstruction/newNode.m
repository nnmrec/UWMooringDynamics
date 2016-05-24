function newNode(varargin)

global Mooring

% Increment mooring line count
Mooring.NumNode = Mooring.NumNode + 1;

% Ensure that name/value pairs were properly entered as function arguments
nArgs = length(varargin);
if round(nArgs/2) ~= nArgs/2
    error('newNode requires property name/value pairs')
end

% Gather optional name/value pairs into a two-row matrix
% Parameter names in first row, corresponding value in second row
pair = reshape(varargin,2,[]);

% Name all Node parameters and assign default values
defaultName = sprintf('Node%g',Mooring.NumNode);
nodeProperties = struct('Name',defaultName,...
    'Type','Free',...
    'ParentBody',0,...
    'InitPosition',[0;0;0],...
    'RowIndices',[0;0;0]);
    
% Create Cell Array of all parameter names
optionNames = fieldnames(nodeProperties);

% Replace default parameter values with user-defined values
for i = 1:nArgs/2
    if any(strcmp(pair{1,i},optionNames))
        nodeProperties.(pair{1,i}) = pair{2,i};
    else
        error('"%s" is not a recognized parameter name you dullard',pair{1,i})
    end
end

% add body to model data structure
Mooring.nodes = [Mooring.nodes,nodeProperties];