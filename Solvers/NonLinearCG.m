function [qSol,err,NewtonData] = NonLinearCG(F,q0)
% Conjugate Gradient algorithm:
% STEP 1: given x_1, d_1 = -grad(f(x_1)), k = 1
% STEP 2: compute alpha_k>0 (various methods, see Dai & Yuan (1999) equations 1.7 and 1.10)
% STEP 3: compute x_k+1 = x_k + alpha_k*d_k; if grad(f(x_k+1)) < tol, break
% STEP 4: compute beta_k and d_k+1 (various methods, see Dai & Yuan (1999) equations 2.4 and 1.3)
%           k = k+1, GOTO STEP 2

% Dai & Yuan (1999)
% 1.7: f(x_k) - f(x_k + alpha_k*d_k) >= -delta*alpha_k*g_k'*d_k
% 1.10: g(x_k + alpha_k*d_k)'*d_k > sigma*g_k'd_k
%       0 < delta < sigma < 1
% 2.4: beta_k = ||grad(f(x_k+1))||^2 / d_k'*(grad(f(x_k+1)) - grad(f(x_k)))
% 1.3: d_k+1 = -grad(f(x_k+1)) + beta_k*d_k

% Solver properties
tol = 1e-3;
MaxIterations = 50;

n = size(q0,1); % Total # of variables incl. multipliers (mu) and slacks (nu)

qSol = zeros(n,1); % Initialize solution array
qGuess = q0;

% Find first search direction (gradient of the merit function)
g = getMeritGradient(F,qGuess); % gradient of objective function at qGuess
d = -g; % search direction
norm_g = 0.5*transpose(g)*g; 

% For Debug
errorStep = 28;
All_qGuess = zeros(n,MaxIterations);
All_d = zeros(n,MaxIterations);
All_norm_g = zeros(MaxIterations,1);
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
    All_d(:,i) = d;
    All_norm_g(i) = norm_g;

    % Convergence/error Test
    if sqrt(2*norm_g) < tol
        qSol = qGuess;
        NewtonData = struct('qIterationData',All_qGuess,'PhiIterationData',All_d,'PhiNormIterationData',All_norm_g,'AlphaIterationData',All_alpha);
        break
    elseif i == MaxIterations
        fprintf('\nNewton Solver: Non-convergence error\n')
        err = 1;
        qSol = struct('qIterationData',All_qGuess,'PhiIterationData',All_d,'PhiNormIterationData',All_norm_g,'AlphaIterationData',All_alpha);
        break
    elseif i>10 && abs(mean(sqrt(2*All_norm_g(i-10))-sqrt(2*All_norm_g(i-10:i))))<eps
        fprintf('\nNewton Solver: Convergence to Infeasible Solution\n')
        err = 1;
        qSol = struct('qIterationData',All_qGuess,'PhiIterationData',All_d,'PhiNormIterationData',All_norm_g,'AlphaIterationData',All_alpha);
        break
    end
    
    % Compute steplength alpha
    Ap = A*p;
    alpha = r'*r/(p'*Ap);
    qNext = qGuess + alpha*p;
    rNext = r + alpha*Ap;
    beta = rNext'*rNext/(r'*r);
    pNext = -rNext + beta*p;

    qGuess = qNext;
    r = rNext;
    p = pNext;
    norm_g = 0.5*(r'*r);
    All_alpha(i) = alpha;
end