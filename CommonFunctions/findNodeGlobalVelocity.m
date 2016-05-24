function NodeGlobalVelocity = findNodeGlobalVelocity(q,f,ThisNode)

global Mooring

bodies = Mooring.bodies;
nodes = Mooring.nodes;

if strcmpi(nodes(ThisNode).Type,'Free')
    NodeGlobalVelocity = f(nodes(ThisNode).RowIndices);
elseif strcmpi(nodes(ThisNode).Type,'Fixed')
    NodeGlobalVelocity = [0;0;0];
elseif strcmpi(nodes(ThisNode).Type,'BodyFixed');
    ParentBodyIndex = nodes(ThisNode).ParentBody;
    PBRowIndices = bodies(ParentBodyIndex).RowIndices;
    alpha = q(PBRowIndices(4));
    beta = q(PBRowIndices(5));
    gamma = q(PBRowIndices(6));
    T = EulerAngles(alpha,beta,gamma); % Transformation matrix
    NodeLocalPosition = nodes(ThisNode).InitPosition;
    r = T*NodeLocalPosition; % Vector from body-fixed axis origin to attachment point in global frame

    omega = [cos(beta)*cos(gamma)*f(PBRowIndices(4)) + sin(gamma)*f(PBRowIndices(5));
        -cos(beta)*sin(gamma)*f(PBRowIndices(4)) + cos(gamma)*f(PBRowIndices(5));
        sin(beta)*f(PBRowIndices(4)) + f(PBRowIndices(6))];
    NodeGlobalVelocity = f(PBRowIndices(1:3)) + T*cross(omega,r); % Velocity of attachment point in global coords.
else
    fprintf('I done errored')
end