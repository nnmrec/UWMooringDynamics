function eCFD = addCFD(q)

global Mooring

bodies = Mooring.bodies;

n = Mooring.TotalDOF;

eCFD = zeros(n,1);

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
ThrustFromCFD = Mooring.Thrust;
TorqueFromCFD = Mooring.Torque;

TurbineBodyFixedFlowAxis = [1;0;0];

for i = 1:size(BodiesThatAreTurbines,2)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
    alpha = q(RowIndices(4));
    beta = q(RowIndices(5));
    gamma = q(RowIndices(6));
    
    A = EulerAngles(alpha,beta,gamma);
    TurbineUnitVector = A*TurbineBodyFixedFlowAxis;
    
    ThrustMagnitude = sqrt(ThrustFromCFD(i,1)^2+ThrustFromCFD(i,2)^2+ThrustFromCFD(i,3)^2);
    TorqueMagnitude = sqrt(TorqueFromCFD(i,1)^2+TorqueFromCFD(i,2)^2+TorqueFromCFD(i,3)^2);
    
    % Angle between thrust vector and nominal turbine orientation
    CosTheta1 = dot(ThrustFromCFD(i,:)',TurbineBodyFixedFlowAxis)/ThrustMagnitude;
    
    MaxThrust = ThrustMagnitude/CosTheta1; % approx thrust if turbine was aligned with flow
    MaxTorque = TorqueMagnitude/CosTheta1;
    
    % Angle between nominal turbine orientation and current turbine orientation
    CosTheta2 = dot(TurbineUnitVector,TurbineBodyFixedFlowAxis);
    
    % Adjusted Thrust and Torque magnitudes
    AdjThrust = CosTheta2*MaxThrust*TurbineUnitVector;
    AdjTorque = CosTheta2*MaxTorque*TurbineUnitVector;
    
    eCFD(RowIndices(1:3)) = AdjThrust;
    eCFD(RowIndices(4:6)) = AdjTorque;
end