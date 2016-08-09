function [probes, rotors] = writeInputsMooringToCFD(Mooring,qStatic)

% Get unit vectors in direction of turbine flow axis from turbine orientation
TurbineAxisUnitVectors = GetTurbineFlowAxisUnitVector(qStatic);

% parse the solution of the mooring code, to find
% xyzProbes: table with x, y, z positions of line segment centers
%   xyzBody: table with x, y, z positions of body COMs
[xyzProbes,xyzBody] = GetProbeLocations(qStatic);

% For bodies find which indicies correspond to turbines and buoy
% buoys: only sample 3 velocities
% turbines: sample 3 forces, 3 moments, and 1 inflow speed
ind_buoy    = find(ismember({Mooring.bodies.Type},'buoy'));
ind_turbine = find(ismember({Mooring.bodies.Type},'turbine'));

% point probes must be added at the buoys, and line segments
% append the buoy bodies onto the line segment coordinates
probes.xyz      = [xyzProbes; xyzBody(ind_buoy,:)];

% now come up with meaningful names for the probes
% list all the line segment names
segsPerLine = [Mooring.lines.NumSegments]';
names      = {Mooring.lines.Name}';
probesname = cell(sum([Mooring.lines.NumSegments]),1);
k = 1;
for n = 1:numel(names)
    for m = 1:segsPerLine(n)
        probeName = [char(names(n)) '_' sprintf('%3.3d',m)];
        probesname(k) = cellstr(probeName);
        k = k+1;
    end
end
% now append the buoy names after line segment names
bodynames    = {Mooring.bodies.Name}';
probes.names = [probesname; bodynames(ind_buoy)];



% now get the names and coordinates of the turbine type bodies
rotors.xyz = xyzBody(ind_turbine,:);
rotors.names = bodynames(ind_turbine);        
rotors.data = horzcat(rotors.names, ...
                      Mooring.Turbines.data_cfd(:,1:2), ...
                      num2cell(rotors.xyz), ...
                      num2cell(TurbineAxisUnitVectors), ...
                      Mooring.Turbines.data_cfd(:,6:8));                 


% need to write a CSV file of the coordiantes to create point probes
% WRITE the initial conditions to "input files" for the CFD model 
writeInputsProbes(Mooring.OptionsCFD.filesIO,probes);  % writes a file probes.csv in the CFD output directory
                  
% turbines have their own way of reporting the forces/moments/inflowspeed
% need to write a CSV file with coordinates of turbines
writeInputsRotors(Mooring.OptionsCFD.filesIO,rotors);  % writes a file rotors.csv in the CFD output directory




end