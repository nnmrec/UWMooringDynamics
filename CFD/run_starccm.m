%% this file is supposed to run the CFD code and return forces to the mooring model
%   inputs: xyz coordinates of moorning model
%  outputs: forces (at center of mass of bodies, and at mooring line 'segments')
%           moments about local coordinate system of mooring 'bodies'

% note: danny add OPTIONS to configSimple.m


% xyzProbes: table with x, y, z positions of line segment centers
% xyzBody: table with x, y, z positions of body COMs
% [VelocityAtProbes,ForcesOnBodies] = runStarCCM(xyzProbes,xyzBody,Mooring);
% OPTIONS = Mooring.OptionsCFD;

% function [VelocityAtProbes,ForcesOnBodies] = run_starccm(xyzProbes,xyzBody,Mooring)
% function [VelocityAtProbes,ForcesOnBodies] = run_starccm(probes,rotors,Mooring)
function [probes,rotors] = run_starccm(probes,rotors,Mooring)
%run_starccm runs the STAR-CCM+ model and returns output to the mooring model
%   Detailed explanation goes here

% read the main user options (read/initialize "user inputs" ... note: some of the java macros also have "user inputs")
% [OPTIONS,filesIO,probes,rotors] = initMain(optionsFile);
% [OPTIONS,filesIO,probes,rotors] = initMain(Mooring.casename);
% [OPTIONS,filesIO,probes,rotors] = init_cfd(Mooring);

%% initialize the starccm .sim file
% determine the starccm license server
if Mooring.OptionsCFD.runOnHPC
    lic_server = '1999@mgmt2.hyak.local';
else
    lic_server = '1999@lmas.engr.washington.edu';
end


%% run STAR-CCM+, can run on your local *nix computer or supercomputer ... sorry Windows is not supported! :-P
% easiest to run from the same directory of the .sim file
cwd = pwd;
cd('CFD');

% create a new empty file and then save it ... it will always have the same name
% then rename the empty file with a meaningful filename
system(['starccm+ -batch macros/saveEmptySimFile.java -np 1 -licpath ' lic_server ' -new']);
system(['mv empty_case.sim runs.' Mooring.casename '.sim']);

% the actual command to run starccm+
if Mooring.OptionsCFD.runOnHPC
    % qsub already been called and Matlab will already be running (waiting
    % for starccm to complete), so instead assume we are already in an active batch session
    % meaning the PBS variables will be available to use
    system(['starccm+ -batch macros/main.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs.' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename]);
else
    % if on your local workstation, PBS variables are not used, and the license server is different
    system(['starccm+ -batch macros/main.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs.' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename]);
end

cd(cwd);


%% now parse the outputs of the CFD model
%% READ the output from the CFD model
% note: this only reports 1D velocity, 1D forcesand moments thrust and torque, but
% should add 3 components of velocity, 3 forces, and 3 moments about the endpoint mooring node in local coordinate system 
% (as opposed to the body center-of-mass)
[probes, rotors] = readOutputs(Mooring.OptionsCFD.filesIO,probes,rotors);

end % function

