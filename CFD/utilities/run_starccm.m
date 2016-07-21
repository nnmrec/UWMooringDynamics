%% this file is supposed to run the CFD code and return forces to the mooring model
%   inputs: xyz coordinates of moorning model
%  outputs: forces (at center of mass of bodies, and at mooring line 'segments')
%           moments about local coordinate system of mooring 'bodies'

function [probes,rotors] = run_starccm(probes,rotors,Mooring)
%run_starccm runs the STAR-CCM+ model and returns output to the mooring model
%   Detailed explanation goes here


%% run STAR-CCM+, can run on your local *nix/GNU computer or supercomputer ... sorry Windows is not supported! :-P
%  initialize the starccm file(s)

% determine the starccm license server
if Mooring.OptionsCFD.runOnHPC
    lic_server = '1999@mgmt2.hyak.local';
else
    lic_server = '1999@lmas.engr.washington.edu';
end

% runs from the same directory of the .sim file
cwd = pwd;      
cd('CFD');

% create a new empty file and then save it ... it will always have the same name
% then rename the empty file with a meaningful filename (weird, but I could not figure out how improve starccm about this)
system(['starccm+ -batch macros/init_EmptySimFile.java -np 1 -licpath ' lic_server ' -new']);
system(['mv empty_case.sim runs_' Mooring.casename '.sim']);

% the actual command to run starccm+
if Mooring.OptionsCFD.runOnHPC
    % qsub already been called and Matlab will already be running (waiting
    % for starccm to complete), so instead assume we are already in an active batch session
    % meaning the PBS variables will be available to use
    run_starccm_command1 = ['starccm+ -batch macros/_main.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command2 = ['starccm+ -batch macros/AMR_Initialize.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command3 = ['starccm+ -batch macros/AMR_Main.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
else
    % if on your local workstation, PBS variables are not used, and the license server is different
    run_starccm_command1 = ['starccm+ -batch macros/_main.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command2 = ['starccm+ -batch macros/AMR_Initialize.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command3 = ['starccm+ -batch macros/AMR_Main.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
end

%% now run starccm

% note: this initial run will not have run all the rotor-speed update stages 
system(run_starccm_command1);

% if this has AMR enabled, it will refine the mesh multiple times before 
% checking if the rotor speeds are close to the target value
if Mooring.OptionsCFD.AMR
    system(run_starccm_command2);   % init AMR
    system(run_starccm_command3);   % run AMR
end

% read the output from the previous starccm iteration
[probes, rotors] = readOutputs(Mooring.OptionsCFD.filesIO,probes,rotors);
    
for n = 1:Mooring.OptionsCFD.nUpdateRPM
    
    % RUN STAR-CCM+, and as the velocity field update ... the rotor speed
    % should also update to perform at target tip-speed-ratio, this call
    % also updates the positions of the rotors (from the mooring model)
    % and finally, the starccm solver is run again
    [probes, rotors] = adjustRotorSpeeds(Mooring,probes,rotors);
    
    % read the output from the previous starccm iteration
    [probes, rotors] = readOutputs(Mooring.OptionsCFD.filesIO,probes,rotors);

end

% or can choose to do AMR after the adjust TSR loop, this could save time
% if Mooring.OptionsCFD.AMR
%     system(run_starccm_command3);   % re-run the AMR
% end


%% READ the final output from the CFD model
% note: this reports 3 velocity components at line segments and buoys, 
%       at turbine bodies, 1 Force component (thrust) and 1 Moment component (torque), but
% should add 3 components of velocity, 3 forces, and 3 moments about the endpoint mooring node in local coordinate system 
% (as opposed to the body center-of-mass)
[probes, rotors] = readOutputs(Mooring.OptionsCFD.filesIO,probes,rotors);


%% cleanup/finalize anything after CFD stuff
cd(cwd);


end % function
