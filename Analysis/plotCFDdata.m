function plotCFDdata(q,Probe_xyz,Rotor_xyz)

VelocityScaleFactor = 5;
ForceScaleFactor = 0.0001;

global Mooring

VelocityAtProbes = Mooring.VelocityAtProbes*VelocityScaleFactor;
ForcesOnTurbines_raw = Mooring.ForcesOnBodies*ForceScaleFactor;

bodies = Mooring.bodies;

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
ForcesOnTurbines = zeros(size(ForcesOnTurbines_raw,1),3);
MomentsOnTurbines = zeros(size(ForcesOnTurbines_raw,1),3);
for i = 1:size(ForcesOnTurbines_raw,1)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
    BodyOrientation = q(RowIndices(4:6));
    A = EulerAngles(BodyOrientation(1),BodyOrientation(2),BodyOrientation(3));
    UnitVectorAlignedWithTurbine = A*[1;0;0]; % [1;0;0] is unit vector of turbine axis in body-fixed frame
    ForcesOnTurbines(i,:) = transpose(ForcesOnTurbines_raw(i,1)*UnitVectorAlignedWithTurbine);
    MomentsOnTurbines(i,:) = transpose(ForcesOnTurbines_raw(i,2)*UnitVectorAlignedWithTurbine);
end

hold on

quiver3(Probe_xyz(:,1),Probe_xyz(:,2),Probe_xyz(:,3),...
    VelocityAtProbes(:,1),VelocityAtProbes(:,2),VelocityAtProbes(:,3),...
    'AutoScale','off','Color','b','LineWidth',1)

quiver3(Rotor_xyz(:,1),Rotor_xyz(:,2),Rotor_xyz(:,3),...
    ForcesOnTurbines(:,1),ForcesOnTurbines(:,2),ForcesOnTurbines(:,3),...
    'AutoScale','off','Color','r','LineWidth',1)

ForceVectorHead = [Rotor_xyz(:,1)+ForcesOnTurbines(:,1),...
    Rotor_xyz(:,2)+ForcesOnTurbines(:,2),...
    Rotor_xyz(:,3)+ForcesOnTurbines(:,3)];

quiver3(ForceVectorHead(:,1),ForceVectorHead(:,2),ForceVectorHead(:,3),...
    MomentsOnTurbines(:,1),MomentsOnTurbines(:,2),MomentsOnTurbines(:,3),...
    'AutoScale','off','Color','g','LineWidth',1)