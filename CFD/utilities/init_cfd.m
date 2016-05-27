function filesIO = init_cfd()
%initMain read the user inputs, and does some bookkeeping about file I/O
%   Detailed explanation goes here

%% some bookkeeping
filesIO.dir_input       = [pwd filesep 'CFD' filesep 'inputs'];
filesIO.dir_output      = [pwd filesep 'CFD' filesep 'outputs'];
filesIO.fileIn_probes   = [filesIO.dir_output filesep 'probes.csv'];
filesIO.fileIn_rotors   = [filesIO.dir_output filesep 'rotors.csv'];    


% filesIO.fileOut_probes = [filesIO.dir_output filesep 'probes-velocity.csv'];
filesIO.fileOut_probesX = [filesIO.dir_output filesep 'probes-velocity-x.csv'];
filesIO.fileOut_probesY = [filesIO.dir_output filesep 'probes-velocity-y.csv'];
filesIO.fileOut_probesZ = [filesIO.dir_output filesep 'probes-velocity-z.csv'];
filesIO.fileOut_rotors  = [filesIO.dir_output filesep 'rotors-velocity.csv'];  
filesIO.fileOut_thrust  = [filesIO.dir_output filesep 'rotors-thrust.csv'];  
filesIO.fileOut_torque  = [filesIO.dir_output filesep 'rotors-torque.csv'];  
   
%% modify the default convergence criteria
% note: does this really need to be continuosly updated?  maybe better to
% just setup a smarter convergence criteria from the beginning (use asymptotic
% and residual tolerances)
% OPTIONS = updateSolverCFD(OPTIONS,'first');

end

