function [phi, phi_q] = constrainBody(q)

global Mooring
bodies = Mooring.bodies;
phi = zeros(Mooring.NumBodyCon, 1);
phi_q = zeros(Mooring.NumBodyCon, Mooring.TotalDOF);

count = 1;
for i = 1:Mooring.NumBody
    BodyIndex = bodies(i).RowIndices;
    BodyConstraints = find(bodies(i).BodyConstraints);
    phi(count:count + size(BodyConstraints,1) - 1) = q(BodyIndex(BodyConstraints));
    for j = 1:size(BodyConstraints,1)
        phi_q(count,BodyIndex(BodyConstraints(j))) = 1;
        count = count + 1;
    end
end
    