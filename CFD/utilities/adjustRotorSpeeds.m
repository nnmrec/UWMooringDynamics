function [probes, rotors] = adjustRotorSpeeds(Mooring,probes,rotors)
%%adjustRotorSpeeds ensures that RPM control uses the correct rotor speeds specified in the "input table"


%% initialize things for CFD stuff
% determine the starccm license server
if Mooring.OptionsCFD.runOnHPC
    lic_server = '1999@mgmt2.hyak.local';
else
    lic_server = '1999@lmas.engr.washington.edu';
end

% the actual command to run starccm+
if Mooring.OptionsCFD.runOnHPC
    % qsub already been called and Matlab will already be running (waiting
    % for starccm to complete), so instead assume we are already in an active batch session
    % meaning the PBS variables will be available to use
    run_starccm_command1 = ['starccm+ -batch macros/update_VirtualDisks.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command2 = ['starccm+ -batch macros/update_SolverAndRun.java -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
else
    % if on your local workstation, PBS variables are not used, and the license server is different
    run_starccm_command1 = ['starccm+ -batch macros/update_VirtualDisks.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
    run_starccm_command2 = ['starccm+ -batch macros/update_SolverAndRun.java -np ' num2str(Mooring.OptionsCFD.nCPUs) ' -licpath ' lic_server ' -batch-report runs_' Mooring.casename '.sim 2>&1 | tee log.' Mooring.casename];
end


%%
switch Mooring.OptionsCFD.control
    case 'RPM'
        % compute new rotor speed and write new inputs files with updated rotor speeds
        rotors = lookupRotorSpeeds(Mooring.OptionsCFD.filesIO,rotors);

        % update the Virtual Disks from the updated input files about rotor speed
        system(run_starccm_command1);

        % re-run the solver now that Virtual Disk rotor speeds are updated
        system(run_starccm_command2);
        % wake is fully developed and turbines operate at target rpm now

    case 'TSR'
        error('ERROR: sorry, TSR control is not finished yet.');

    case 'none'
        error('ERROR: sorry, "none" control is not finished yet');

    otherwise
        error('ERROR: unrecognized option for control.  why you do that?');
end     

% read the output from the previous starccm iteration
[probes, rotors] = readOutputs(Mooring.OptionsCFD.filesIO,probes,rotors);
    
end % function

function [rotors] = lookupRotorSpeeds(filesIO,rotors)
%updateRotorSpeeds uses the last known rotor speed from STAR-CCM+ and then update according to the input table
%   Detailed explanation goes here

% compute the new rotor speed based on the updated inflow speeds
for n = 1:size(rotors.data, 1)
    % read wind and rpm from the file table
%     file_perf  = [filesIO.dir_input filesep 'tables' filesep rotors.tables{n} '.csv'];
    file_perf  = [filesIO.dir_input filesep 'tables' filesep rotors.data{n,2} '.csv'];
    perf_table = csvread(file_perf,1);
    wind       = perf_table(:,1);
    rpm        = perf_table(:,4);
    new_rpm    = interp1(wind, rpm, rotors.vel(n));
    
    rotors.data{n,3} = new_rpm;
end

% write the new coordinates/settings to file (for the next iteration of the CFD model)
writeInputsRotors(filesIO,rotors);
    
end % function

