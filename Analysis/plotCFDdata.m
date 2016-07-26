function plotCFDdata(Probe_xyz,Rotor_xyz)

VelocityScaleFactor = 5;
ForceScaleFactor = 0.0001;

global Mooring

VelocityAtProbes = Mooring.VelocityAtProbes*VelocityScaleFactor;
ForcesOnTurbines = Mooring.Thrust*ForceScaleFactor;
MomentsOnTurbines = Mooring.Torque*ForceScaleFactor;

% bodies = Mooring.bodies;
% BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));

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