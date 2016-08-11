function eCFD = addCFD(q)

global Mooring

bodies = Mooring.bodies;

n = Mooring.TotalDOF;

eCFD = zeros(n,1);

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
ThrustFromCFD = Mooring.Thrust;
TorqueFromCFD = Mooring.Torque;

% TurbineBodyFixedFlowAxis = [1;0;0];

for i = 1:size(BodiesThatAreTurbines,2)
    RowIndices = bodies(BodiesThatAreTurbines(i)).RowIndices;
%     alpha = q(RowIndices(4));
%     beta = q(RowIndices(5));
%     gamma = q(RowIndices(6));
%     
%     A = EulerAngles(alpha,beta,gamma);
%     TurbineUnitVector = A*TurbineBodyFixedFlowAxis;
%     
%     ThrustMagnitude = sqrt(ThrustFromCFD(i,1)^2+ThrustFromCFD(i,2)^2+ThrustFromCFD(i,3)^2);
%     TorqueMagnitude = sqrt(TorqueFromCFD(i,1)^2+TorqueFromCFD(i,2)^2+TorqueFromCFD(i,3)^2);
%     ThrustVector    = ThrustFromCFD(i,:)' ./ norm(ThrustFromCFD(i,:));
% 
%     % Angle between thrust vector and nominal turbine orientation
%     CosTheta1 = dot(ThrustFromCFD(i,:)',TurbineBodyFixedFlowAxis)/ThrustMagnitude;
%     
%     MaxThrust = ThrustMagnitude/CosTheta1; % approx thrust if turbine was aligned with flow
%     MaxTorque = TorqueMagnitude/CosTheta1;
%     
%     % Angle between nominal turbine orientation and current turbine orientation
%     CosTheta2 = dot(TurbineUnitVector,TurbineBodyFixedFlowAxis);
%     
%     % Angle between initial turbine thrust unit vector (from the CFD
%     % calculation) and the iterating turbine unit vector (changing during the Newton solver)
% %     CosTheta3 = dot(ThrustVector, TurbineUnitVector);
%     CosTheta3 = max(dot(ThrustVector, TurbineUnitVector), 0);
%     
%     % Adjusted Thrust and Torque magnitudes
%     AdjThrust = ThrustMagnitude*CosTheta3;
%     AdjTorque = TorqueMagnitude*CosTheta3;
% %     AdjThrust = CosTheta2*MaxThrust*TurbineUnitVector;
% %     AdjTorque = CosTheta2*MaxTorque*TurbineUnitVector;
    

    eCFD(RowIndices(1:3)) = transpose(ThrustFromCFD(i,:));
    eCFD(RowIndices(4:6)) = transpose(TorqueFromCFD(i,:));
%     eCFD(RowIndices(1:3)) = AdjThrust;
%     eCFD(RowIndices(4:6)) = AdjTorque;
    
    % DEBUG
%     if i==1
%         fprintf(1,'DEBUG: %g %g %g %g %g %g \n',CosTheta1,CosTheta2,CosTheta3,ThrustMagnitude,MaxThrust,norm(AdjThrust));
%     end
    
end