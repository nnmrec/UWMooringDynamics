function [qSol,err,NewtonData] = UWMDNewtonWithCGStep(F,q0)

% Solver properties
tol = 1e-3;
MaxIterations = 100;
MaxLineSearch = 10;

n = size(q0,1); % Total # of variables incl. multipliers (mu) and slacks (nu)

qSol = zeros(n,1); % Initialize solution array
qGuess = q0;
Phi = F(qGuess);
normPhi = 0.5*transpose(Phi)*Phi;

% For Debug
errorStep = 28;
AllqGuess = zeros(n,MaxIterations);
AllPhis = zeros(n,MaxIterations);
AllPhiNorms = zeros(MaxIterations,1);
AllAlphas = zeros(MaxIterations,1);
err = 0;

% Newton Iteration
for i = 1:MaxIterations
    %Debug
    if i == errorStep
        errorStep = 0;
    end
    
%     plotInstant(qGuess,1,0);
%     drawnow
    AllqGuess(:,i) = qGuess;
    AllPhis(:,i) = Phi;
    AllPhiNorms(i) = normPhi;

    % Convergence/error Test
    if sqrt(2*normPhi) < tol
        qSol = qGuess;
        NewtonData = struct('qIterationData',AllqGuess,'PhiIterationData',AllPhis,'PhiNormIterationData',AllPhiNorms,'AlphaIterationData',AllAlphas);
        break
    elseif i == MaxIterations
        fprintf('\nNewton Solver: Non-convergence error\n')
        err = 1;
        qSol = struct('qIterationData',AllqGuess,'PhiIterationData',AllPhis,'PhiNormIterationData',AllPhiNorms,'AlphaIterationData',AllAlphas);
        break
    elseif i>10 && abs(mean(sqrt(2*AllPhiNorms(i-10))-sqrt(2*AllPhiNorms(i-10:i))))<eps
        fprintf('\nNewton Solver: Convergence to Infeasible Solution\n')
        err = 1;
        qSol = struct('qIterationData',AllqGuess,'PhiIterationData',AllPhis,'PhiNormIterationData',AllPhiNorms,'AlphaIterationData',AllAlphas);
        break
    end
    
    % Evaluate step delta_q such that (DF)*delta_q = F via Conjugate Gradient
    [delta_q, CG_err, CG_data] = ConjugateGradient(F,qGuess);
    alpha = 1;
    
    % Increment solution with Backtracking Line Search
    for j = 1:MaxLineSearch
        qNextGuess = qGuess + alpha*delta_q;
        PhiNext = F(qNextGuess);
        normPhiNext = 0.5*transpose(PhiNext)*PhiNext;
        if normPhiNext < normPhi
            break
        else
            alpha = alpha/2;
        end
    end
    qGuess = qNextGuess;
    Phi = PhiNext;
    normPhi = normPhiNext;
    AllAlphas(i) = alpha;
end