function [probes, rotors] = mooring(OPTIONS, probes, rotors)


%% reshape the data into format that mooring code prefers
% probes: coordinates at points along the mooring lines, and the buoyancy pods (plus anything else not a turbine)
xyz_probes = probes.xyz;
vel_probes = probes.vel;

% rotors: coordinates of turbines (center of rotor)
xyz_rotors = rotors.data(:,2:4);
ang_rotors = rotors.data(:,5:7);
vel_rotors = rotors.vel;
thr_rotors = rotors.thrust;
tor_rotors = rotors.torque;

% BODY nodes
nBodies = size(xyz_rotors,1);
q_body  = zeros(nBodies, 6);
v_body  = zeros(nBodies, 1);
f_body  = zeros(nBodies, 6);
for n = 1:nBodies
    % positions (x,y,z,nx,ny,nz)
    q_body(n,:) = [xyz_rotors(n,:), ang_rotors(n,:)];    
    % inflow velocities (velocity magnitude, volume avg. upsteam of rotor)
    v_body(n)   = vel_rotors(n);
    % forces (Fx=thrust,Fy,Fz,Mx,My,Mz=torque)
    f_body(n,:) = [thr_rotors(n), 0, 0, 0, 0, tor_rotors(n)];
end

% LINE nodes
nLines = size(xyz_probes,1);
q_line = zeros(nLines, 3);
v_line = zeros(nLines, 1);
f_line = zeros(nLines, 3);
for n = 1:nLines
    % positions (x,y,z)
    q_line(n,:) = xyz_probes(n,:);    
    % inflow velocities (velocity magnitude, volume avg. upsteam of rotor)
    v_line(n)   = vel_probes(n);
    % forces (Fx,Fy,Fz) ... (you will need to add your drag force calculations here, I imagine some angles will give X, Y, and Z force components, but assume 0 for now)
    f_line(n,:) = [0,0,0];
end

%% run the equilibrium mooring model to calculate new positions
% new_xyz = mooring_equilibrium_solver(OPTIONS,q_body,v_body,f_body,q_line,v_line,f_line);

% for now just move the coordinates randomly, to demostrate the code "works"
% new_xyz.probes = xyz_probes;
% new_xyz.rotors = xyz_rotors;
new_xyz.probes = xyz_probes + 1*rand(size(xyz_probes));
new_xyz.rotors = xyz_rotors + 1*rand(size(xyz_rotors));


%% collect the output
probes.xyz         = new_xyz.probes;
rotors.data(:,2:4) = new_xyz.rotors;


end % function
