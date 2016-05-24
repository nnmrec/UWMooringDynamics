function NodeGlobalPosition = findNodeGlobalPosition(q,ThisNode)

global Mooring

bodies = Mooring.bodies;
nodes = Mooring.nodes;

if strcmpi(nodes(ThisNode).Type,'Free')
    NodeGlobalPosition = q(nodes(ThisNode).RowIndices);
elseif strcmpi(nodes(ThisNode).Type,'Fixed')
    NodeGlobalPosition = nodes(ThisNode).InitPosition;
elseif strcmpi(nodes(ThisNode).Type,'BodyFixed');
    ParentBodyIndex = nodes(ThisNode).ParentBody;
    PBRowIndices = bodies(ParentBodyIndex).RowIndices;
    PBPosition = q(PBRowIndices(1:3));
    PBAngles = q(PBRowIndices(4:6));
    T = EulerAngles(PBAngles(1),PBAngles(2),PBAngles(3)); % Transformation matrix
    NodeLocalPosition = nodes(ThisNode).InitPosition;
    NodeGlobalPosition = PBPosition + T*NodeLocalPosition;
else
    fprintf('I done errored')
end