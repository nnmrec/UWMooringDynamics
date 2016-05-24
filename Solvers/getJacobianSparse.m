function J = getJacobianSparse(F,q)
% Evaluate Jacobian of F at q

n = size(q,1);
Phi = F(q);
J = zeros(n,n);

% Forward Difference method to approximate Jacobian ===========
qStep = q;
for i = 1:n
    stepSize = sqrt(eps)*max(1,abs(q(i)));
    qStep(i) = qStep(i) + stepSize;
    PhiStep = F(qStep);
    J(:,i) = (1/stepSize)*(PhiStep - Phi);
    qStep(i) = q(i);
end

J = sparse(J);