function fitnessVal = optimize_mooring(xo,OPTIONS)

%% xo is a vector containing the design variables:
%  xyz position, tip-speed ratio, yaw angle
    
%% make sure the xo is a column vector
xo = xo(:);

%% write the input files for the CFD model (modify the options file)
% modify options_opt.m
% rotors.tables     % to update rotor speeds (tip-speed-ratios)
% rotors.data 		% to update position and pitch/roll/yaw
 
%% call the CFD code
try
	%% run the CFD model to convergence
	run('mezzanine(OPTIONS)');

catch
    % something unspeakable happened, oh the horror
    fprintf(1, 'WARNING: a severe error occured, something crashed ...continuing anyways.\n');
    fitnessVal = Inf;   % an error occured, return a placeholder value and hope the optimization can recover from it
    return
end

%% READ the output from the CFD model
[probes, rotors] = readOutputs(filesIO,probes,rotors);
rpm 			 = rotor.data(:,1);
power 			 = rotor.torque .* rpm .* (pi/30);
totalPower 		 = sum(power);

%% compute the fitness value (want to maximize this ... so take negative bc Matlab assumes minimization)
fitnessVal = -1 * totalPower;

end % function
