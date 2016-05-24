function [qSol,err,NewtonData] = ConjugateGradient(F,q0)
% Solves the equation DF*qStep = -F for qStep; DF and F evaluated at q0

% Solver properties
tol = 1e-3;
MaxIterations = 1000;

n = size(q0,1); % Total # of variables incl. multipliers (mu) and slacks (nu)

A = getJacobianSparse(F,q0);
b = F(q0);
%qGuess = A\b;
qGuess = zeros(n,1);
r = -A*qGuess - b; 
p = -r;
norm_r = 0.5*transpose(r)*r;

% For Debug
errorStep = 28;
All_qGuess = zeros(n,MaxIterations);
All_p = zeros(n,MaxIterations);
All_norm_p = zeros(MaxIterations,1);
All_alpha = zeros(MaxIterations,1);
err = 0;

% Newton Iteration
for i = 1:MaxIterations
    %Debug
    if i == errorStep
        errorStep = 0;
    end
    
    %plotInstant(qGuess,1,0);
    %drawnow
    All_qGuess(:,i) = qGuess;
    All_p(:,i) = p;
    All_norm_p(i) = norm_r;

    % Convergence/error Test
    if sqrt(2*norm_r) < tol
        qSol = qGuess;
        NewtonData = struct('qIterationData',All_qGuess,'PhiIterationData',All_p,'PhiNormIterationData',All_norm_p,'AlphaIterationData',All_alpha);
        break
    elseif i == MaxIterations
        fprintf('\nNewton Solver: Non-convergence error\n')
        err = 1;
        qSol = [];
        NewtonData = struct('qIterationData',All_qGuess,'PhiIterationData',All_p,'PhiNormIterationData',All_norm_p,'AlphaIterationData',All_alpha);
        break
    elseif i>10 && abs(mean(sqrt(2*All_norm_p(i-10))-sqrt(2*All_norm_p(i-10:i))))<eps
        fprintf('\nNewton Solver: Convergence to Infeasible Solution\n')
        err = 1;
        qSol = [];
        NewtonData = struct('qIterationData',All_qGuess,'PhiIterationData',All_p,'PhiNormIterationData',All_norm_p,'AlphaIterationData',All_alpha);
        break
    end
    
    w = A*p;
    alpha = (r'*r)/(p'*w);
    qNext = qGuess - alpha*p;
    rNext = r + alpha*w;
    beta = (rNext'*rNext)/(r'*r);
    pNext = -rNext + beta*p;

    qGuess = qNext;
    r = rNext;
    if rem(i,10) ~= 0
        p = pNext;
    else
        %p = -r;
        p = pNext;
    end
    norm_r = 0.5*(r'*r);
    All_alpha(i) = alpha;
end