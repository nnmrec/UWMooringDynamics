function [thrust_vector,torque_vector] = CFDForceMagnitudeToVector(q,thrust_magnitude,torque_magnitude)

global Mooring

bodies = Mooring.bodies;

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));

thrust_vector = zeros(size(BodiesThatAreTurbines,2),3);
torque_vector = zeros(size(BodiesThatAreTurbines,2),3);

for i = 1:size(BodiesThatAreTurbines,2)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
    BodyOrientation = q(RowIndices(4:6));
    A = EulerAngles(BodyOrientation(1),BodyOrientation(2),BodyOrientation(3));
    UnitVectorAlignedWithTurbine = A*[1;0;0]; % [1;0;0] is unit vector of turbine axis in body-fixed frame
    thrust_vector(i,:) = transpose(thrust_magnitude(i)*UnitVectorAlignedWithTurbine);
    torque_vector(i,:) = transpose(torque_magnitude(i)*UnitVectorAlignedWithTurbine);
end