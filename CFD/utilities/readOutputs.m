function [probes, rotors] = readOutputs(filesIO,probes,rotors)
%readOutputs reads the output files from STAR-CCM+
%   Detailed explanation goes here

    %% PROBES: read/process the file header
    fid = fopen(filesIO.fileOut_probes, 'r');
    header = fgetl(fid);
    fclose(fid);

    cols        = strsplit(header,',');
    probes_name = cell(size(cols,2)-1, 1);
    for n = 2:size(cols,2)
        token            = strtok(cols(n), ':');
        varname          = token{1}(2:end);
        probes_name{n-1} = varname;
    end
    % read the data section
    probes.vel = csvread(filesIO.fileOut_probes,1);
    probes.vel = probes.vel(end,2:end)';    % only keep the last iteration
    % actually this appears to be in proper order! yay!
    % horzcat(probes_name, num2cell(probes.vel))


    %% ROTOR INFLOW SPEEDS: read/process the file header
    fid = fopen(filesIO.fileOut_rotors, 'r');
    header = fgetl(fid);
    fclose(fid);
    cols        = strsplit(header,',');
    rotors_name = cell(size(cols,2)-1, 1);
    for n = 2:size(cols,2)
        token            = strtok(cols(n), ')');
        [token, remain]  = strtok(token, '(');
        rotors_name{n-1} = remain{1}(2:end);
    end
    % read the data section
    rotors.vel = csvread(filesIO.fileOut_rotors,1);
    rotors.vel = rotors.vel(end,2:end)';    % only keep last iteration
    % actually this appears to be in proper order! yay!
    % horzcat(rotors_name, num2cell(rotors.vel))
    
    %% ROTOR THRUST: read/process the file header
    fid = fopen(filesIO.fileOut_thrust, 'r');
    header = fgetl(fid);
    fclose(fid);
    cols        = strsplit(header,',');
    rotors_name = cell(size(cols,2)-1, 1);
    for n = 2:size(cols,2)
        token            = strtok(cols(n), ')');
        [token, remain]  = strtok(token, '(');
        rotors_name{n-1} = remain{1}(2:end);
    end
    % read the data section
    rotors.thrust = csvread(filesIO.fileOut_thrust,1);
    rotors.thrust = rotors.thrust(end,2:end)';    % only keep last iteration
    % actually this appears to be in proper order! yay!
    % horzcat(rotors_name, num2cell(rotors.thrust))
    
    %% ROTOR TORQUE: read/process the file header
    fid = fopen(filesIO.fileOut_torque, 'r');
    header = fgetl(fid);
    fclose(fid);
    cols        = strsplit(header,',');
    rotors_name = cell(size(cols,2)-1, 1);
    for n = 2:size(cols,2)
        token            = strtok(cols(n), '}');
        [token, remain]  = strtok(token, '{');
        rotors_name{n-1} = remain{1}(2:end);
    end
    % read the data section
    rotors.torque = csvread(filesIO.fileOut_torque,1);
    rotors.torque = rotors.torque(end,2:end)';    % only keep last iteration
    % actually this appears to be in proper order! yay!
    % horzcat(rotors_name, num2cell(rotors.torque))

end

