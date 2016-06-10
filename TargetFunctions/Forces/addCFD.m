function eCFD = addCFD(q,CFD_raw)

global Mooring

bodies = Mooring.bodies;

n = Mooring.TotalDOF;

eCFD = zeros(n,1);

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
for i = 1:size(CFD_raw,1)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
    BodyOrientation = q(RowIndices(4:6));
    A = EulerAngles(BodyOrientation(1),BodyOrientation(2),BodyOrientation(3));
    UnitVectorAlignedWithTurbine = A*[1;0;0]; % [1;0;0] is unit vector of turbine axis in body-fixed frame
    eCFD(RowIndices(1:3)) = CFD_raw(i,1)*UnitVectorAlignedWithTurbine;
    eCFD(RowIndices(4:6)) = CFD_raw(i,2)*UnitVectorAlignedWithTurbine;
end