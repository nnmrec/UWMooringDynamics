% This function applies the buoyant force acting on a rigid body to the
% system of equations

function eBuoy = addBuoyancy(q)

global Mooring

bodies = Mooring.bodies;
rho_f = Mooring.environment.rho_f;
grav = Mooring.environment.grav;

eBuoy = zeros(size(q,1),1);

for i = 1:Mooring.NumBody
    BodyIndex = bodies(i).RowIndices;
    
    BuoyForce = bodies(i).Vol*rho_f*grav; % Buoyant force on fully submerged body
    eBuoy(BodyIndex(1:3)) = eBuoy(BodyIndex(1:3)) + BuoyForce*[0;0;1];
end