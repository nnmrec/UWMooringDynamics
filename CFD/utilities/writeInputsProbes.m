function [probes] = writeInputsProbes(filesIO,probes)
%writeInputs writes the input files for STAR-CCM+
%   Detailed explanation goes here

% construct the cell array
probes_vars = {'ProbeName','x', 'y','z'};
probes_data = horzcat(probes.names, num2cell(probes.xyz));
P           = vertcat(probes_vars, probes_data);

% write to CSV file
f = CsvWriter(filesIO.fileIn_probes,'delimiter',',');
f.append(P);
f.close();

end

