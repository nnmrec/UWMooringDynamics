function [TurbineAxisUnitVector] = GetTurbineFlowAxisUnitVector(qMooring)

global Mooring
bodies = Mooring.bodies;

BodiesThatAreTurbines = find(ismember({bodies.Type},'turbine'));
TurbineAxisUnitVector = zeros(max(size(BodiesThatAreTurbines)),3);

TurbineBodyFixedFlowAxis = [1;0;0];

for i = 1:max(size(BodiesThatAreTurbines))
    qBodyIndex = bodies(BodiesThatAreTurbines(i)).RowIndices;
    alpha = qMooring(qBodyIndex(4));
    beta = qMooring(qBodyIndex(5));
    gamma = qMooring(qBodyIndex(6));
    
    A = EulerAngles(alpha,beta,gamma);
    
    TurbineGlobalFlowAxis = A*TurbineBodyFixedFlowAxis;
    
    TurbineAxisUnitVector(i,:) = TurbineGlobalFlowAxis';
end
        