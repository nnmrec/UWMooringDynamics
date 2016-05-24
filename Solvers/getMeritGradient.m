function g = getMeritGradient(F,q)

% Evaluate gradient g of scalar function f at q

n = size(q,1);
Phi = F(q);
Merit = 0.5*(Phi'*Phi);
g = zeros(n,1);

qStep = q;
for i = 1:n
    stepSize = sqrt(eps)*max(1,abs(q(i)));
    qStep(i) = qStep(i) + stepSize;
    PhiStep = F(qStep);
    MeritStep = 0.5*(PhiStep'*PhiStep);
    g(i,1) = (1/stepSize)*(MeritStep - Merit);
    qStep(i) = q(i);
end