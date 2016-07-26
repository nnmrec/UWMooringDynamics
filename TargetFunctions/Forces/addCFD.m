function eCFD = addCFD(thrust,torque)

global Mooring

bodies = Mooring.bodies;

n = Mooring.TotalDOF;

eCFD = zeros(n,1);

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
for i = 1:size(BodiesThatAreTurbines,2)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
    
    eCFD(RowIndices(1:3)) = transpose(thrust(i,:));
    eCFD(RowIndices(4:6)) = transpose(torque(i,:));
end