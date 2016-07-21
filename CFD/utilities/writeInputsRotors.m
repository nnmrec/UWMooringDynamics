function [rotors] = writeInputsRotors(filesIO,rotors)
%writeInputs writes the input files for STAR-CCM+
%   Detailed explanation goes here

% construct the cell array
rotors_vars = {'name','table','rotor_rpm','x','y','z','nx','ny','nz','rotor_radius','hub_radius','rotor_thick'};
R           = vertcat(rotors_vars, rotors.data);

% write to CSV file
f = CsvWriter(filesIO.fileIn_rotors,'delimiter',',');
f.append(R);
f.close();

end

