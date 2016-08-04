function F = EvaluateStaticPhi(x)
% F(x) = 0, where
%
% x = [q; lambda; mu; nu;]
%
% F = [V_q + phi_q^T * lambda + psi_q^T * mu - e;
%      phi;
%      psi_j + nu_j;
%      mu_j + nu_j - sqrt(mu_j^2 + nu_j^2 + 2*epsilon);

global Mooring
epsilon = 1e-6;

n = Mooring.TotalDOF; % Number of displacement variables
m = Mooring.NumBodyCon;
p = Mooring.NumSeafloorCon+Mooring.NumSlacklineCon;

q = x(1:n);
lambda = x(n+1:n+m);
mu = x(n+m+1:n+m+p);
nu = x(n+m+p+1:n+m+2*p);

% Calculate dV/dq (spring forces) from stiffness of mooring lines
%V_q = zeros(15,1);
V_q = PotentialEnergyVariation(q);

% Calculate e (other forces)
F_grav = addGravity(q);
F_buoy = addBuoyancy(q);
F_drag = addDrag(q,zeros(n,1),0);
F_appl = addApplied(q,0);

e = F_grav + F_buoy + F_drag + F_appl;

if Mooring.CFD && size(Mooring.VelocityAtProbes,2) > 1
    F_CFD = addCFD(q);
    e = e + F_CFD;
end

[phi_Body,phi_q_Body] = constrainBody(q);
[psi_Seafloor,psi_q_Seafloor] = constrainSeafloor(q);
if Mooring.SlacklineConstraint
    [psi_Slackline,psi_q_Slackline] = constrainSlackline(q);
else
    psi_Slackline = zeros(0,0);
    psi_q_Slackline = zeros(0,n);
end;

phi = phi_Body;
phi_q = phi_q_Body;
psi = [psi_Seafloor;psi_Slackline];
psi_q = [psi_q_Seafloor;psi_q_Slackline];

F = [V_q + phi_q'*lambda + psi_q'*mu - e;
    phi;
    psi + nu
    mu + nu - sqrt(mu.^2 + nu.^2 + 2*epsilon)];

