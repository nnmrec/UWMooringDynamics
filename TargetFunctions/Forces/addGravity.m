function eGrav = addGravity(q)

global Mooring

bodies = Mooring.bodies;
lines = Mooring.lines;

Num_Body = Mooring.NumBody;
Num_Line = Mooring.NumLine;
grav = Mooring.environment.grav;

eGrav = zeros(size(q,1),1);

% Gravitational force/moment acting on free or constrained bodies
for i = 1:Num_Body
    RowIndices = bodies(i).RowIndices;
    eGrav(RowIndices) = eGrav(RowIndices) - bodies(i).Mass*grav*[0;0;1;0;0;0];
end

% Gravitational force/moment acting on mooring line nodes
for i = 1:Num_Line
    segMass = lines(i).segMass;
    gravF = [0;0;-segMass*grav];
    InternalNodes = lines(i).InternalNodes;
    
    eGrav = eGrav + includeForceOnNode(0.5*gravF,lines(i).StartNode,q);
    for j = 1:lines(i).NumSegments - 1
        eGrav(InternalNodes(j).RowIndices) = eGrav(InternalNodes(j).RowIndices) + gravF;
    end
    eGrav = eGrav + includeForceOnNode(0.5*gravF,lines(i).EndNode,q);
end