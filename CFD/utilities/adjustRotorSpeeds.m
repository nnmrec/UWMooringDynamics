function [rotors] = adjustRotorSpeeds(OPTIONS,filesIO,rotors)
%%adjustRotorSpeeds ensures that RPM control uses the correct rotor speeds specified in the "input table"
%

for n = 1:OPTIONS.nUpdateRPM   

    switch OPTIONS.control
        case 'RPM'
            % compute new rotor speed and write new inputs files with updated rotor speeds
            rotors = lookupRotorSpeeds(filesIO,rotors);

            % update the Virtual Disks
            system(['starccm+ -batch macros/updateVirtualDisks.java' ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@flex.cd-adapco.com -power -podkey $myPODkey -batch-report runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);
            
            % updated the stopping criteria
            OPTIONS = updateSolverCFD(OPTIONS,'update');
           
            % now can run STAR-CCM+ again with updated inputs
            OPTIONS = runSTARCCM(OPTIONS,'updateAndRun.java');

        case 'TSR'

        case 'none'

        otherwise
            error('ERROR: unrecognized option for control.  why you do that?');
    end
        
end

end % function

function [rotors] = lookupRotorSpeeds(filesIO,rotors)
%updateRotorSpeeds uses the last known rotor speed from STAR-CCM+ and then update according to the input table
%   Detailed explanation goes here

% compute the new rotor speed based on the updated inflow speeds
new_rpm = zeros(numel(rotors.names),1);
% each turbine can have a different lookup table, so loop through all
for n = 1:size(rotors.tables, 1)
    % read wind and rpm from the file table
    file_perf  = [filesIO.dir_input filesep 'tables' filesep rotors.tables{n} '.csv'];
    perf_table = csvread(file_perf,1);
    wind       = perf_table(:,1);
    rpm        = perf_table(:,4);
    new_rpm(n) = interp1(wind, rpm, rotors.vel(n));
end
rotors.data(:,1) = new_rpm;

% write the new coordinates/settings to file (for the next iteration of the CFD model)
writeInputsRotors(filesIO,rotors);
    
end % function

