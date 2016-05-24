function [ForceVector] = includeForceOnNode(Force,ThisNode,q)
% q - Instantaneous displacement vector, all variables
% Force - Force vector in global frame to be applied to the attachment point
% ThisNode - Index of node to which Force is being applied
% ForceVector = Vector of size equal to that of q with the translated force
%               included in the appropriate rows

global Mooring

bodies = Mooring.bodies;
nodes = Mooring.nodes;

ForceVector = zeros(size(q,1),1);

if strcmpi(nodes(ThisNode).Type,'Free')
    ForceVector(nodes(ThisNode).RowIndices) = Force;
elseif strcmpi(nodes(ThisNode).Type,'BodyFixed');
    ParentBodyIndex = nodes(ThisNode).ParentBody;
    PBRowIndices = bodies(ParentBodyIndex).RowIndices;
    PBAngles = q(PBRowIndices(4:6));
    T = EulerAngles(PBAngles(1),PBAngles(2),PBAngles(3)); % Transformation matrix
    NodeLocalPosition = nodes(ThisNode).InitPosition;
    
    ForceVector(PBRowIndices(1:3)) = Force;
    ForceVector(PBRowIndices(4:6)) = cross(T*NodeLocalPosition,Force);
elseif ~strcmpi(nodes(ThisNode).Type,'Fixed')
    fprintf('I done errored')
end