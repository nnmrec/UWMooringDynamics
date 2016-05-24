function [ZSol,err] = UWMDImpEulerSmoothedFBComp(F,q0,Time)
% This function implements a Fully Implicit Euler method to solve the
% system of equations F over the temporal domain Time.  q0 holds the
% initial values of all variables in F.  The Lagrange Multiplier and slack 
% variable complementarity conditions are stated in the system of equations 
% F as the Smoothed Fischer-Burmeister function.  A Newton Iteration Method 
% is used to solve the Implicit Euler condition at each time step.

% F: function handle to force vector (RHS of equations of motion)
% M: function handle to mass matrix (factor on LHS of equations of motion)
% q0: vector of initial values of all variables, [q0;f0;a0,lambda0;mu0,nu0]
% Time: vector of time values at all time steps

% DAE of the form 0=F(y,t)

% Format Time as a column vector
if ~iscolumn(Time)
    Time = Time';
end

% Initialize solution array
n = size(q0,1); % Total # of variables incl. multipliers (mu) and slacks (nu)
NumberOfTimeSteps = size(Time,1);
ZSol = zeros(n,NumberOfTimeSteps);
ZSol(:,1) = q0;
err = 0;

% Initialize global variables to pass to Newton solver
global TimeStepData
TimeStepData = struct('PreviousDisplacement',zeros(n,1),'Time',0,'TimeStep',0);

% For Debug
errorStepEuler = 113;

% Fully Implicit Euler Method loop
for i = 1:size(Time,1)-1
    % Display loop iteration number in Command Window
    fprintf('%d,',i)
    if rem(i,50) == 0
        fprintf('\n')
    end
    
    % Debug
    if i == errorStepEuler;
        errorStepEuler = 0;
    end
    
    % Assign variables for 'i'th iteration
    t = Time(i);
    tNext = Time(i+1);
    h = tNext - t;
    q = ZSol(:,i);
    TimeStepData.PreviousDisplacement = q;
    TimeStepData.Time = tNext;
    TimeStepData.TimeStep = h;
    
    % Solve Phi(y_i+1) = My_i+1 - hF(y_i+1,t_i+1) - My_i for y_i+1 = 0
    % Implicit Euler condition
     [Z,err] = UWMDNewton(F,q);
     if err
         Z.SolUpToFailure = ZSol(:,1:i);
         ZSol = Z;
         break
     end
     ZSol(:,i+1) = Z;
end

%ZSol = transpose(ZSol);