function writeCFD_BC(Mooring,inflow_speed)
% writes some boundary conditions for starccm
%   Detailed explanation goes here

% construct the cell array
varnames = {'inflow_speed_x'};
R        = horzcat(varnames, inflow_speed);


% write to CSV file
f = CsvWriter(Mooring.OptionsCFD.filesIO.environment,'delimiter',',');
f.append(R);
f.close();


end