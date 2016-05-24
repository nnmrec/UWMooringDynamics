function Phi = EvaluateDynamicPhi(Y)
% Y: [q;lambda;mu;nu;f;a]

global Mooring TimeStepData
epsilon = 1e-8;

bodies = Mooring.bodies;
lines = Mooring.lines;

n = Mooring.TotalDOF; % Number of displacement variables
m = Mooring.NumBodyCon; % Number of equality constraints
p = Mooring.NumSeafloorCon+Mooring.NumSlacklineCon;

q = Y(1:n);
lambda = Y(n+1:n+m);
mu = Y(n+m+1:n+m+p);
nu = Y(n+m+p+1:n+m+2*p);
f = Y(n+m+2*p+1:2*n+m+2*p);
a = Y(2*n+m+2*p+1:3*n+m+2*p);

t = TimeStepData.Time;
h = TimeStepData.TimeStep;
Y_previous = TimeStepData.PreviousDisplacement;
q_previous = Y_previous(1:n);
f_previous = Y_previous(n+m+2*p+1:2*n+m+2*p);

q_dot = (q - q_previous)/h;
f_dot = (f - f_previous)/h;

M = zeros(n,1);
% Assign values to M(2*n+1:3*n,2*n+1:3*n)
for i = 1:Mooring.NumBody
    BodyIndex = bodies(i).RowIndices;
    M(BodyIndex(1)) = bodies(i).Mass;
    M(BodyIndex(2)) = bodies(i).Mass;
    M(BodyIndex(3)) = bodies(i).Mass;
    MMoI = bodies(i).MMoInertia;
    M(BodyIndex(4)) = MMoI(1);
    M(BodyIndex(5)) = MMoI(2);
    M(BodyIndex(6)) = MMoI(3);
end

for i = 1:Mooring.NumLine
    InternalNodes = lines(i).InternalNodes;
    for j = 1:size(InternalNodes,2)
        INRI = InternalNodes(j).RowIndices; %InternalNodeRowIndices
        M(INRI(1)) = M(INRI(1)) + lines(i).segMass;
        M(INRI(2)) = M(INRI(2)) + lines(i).segMass;
        M(INRI(3)) = M(INRI(3)) + lines(i).segMass;
    end
end

% Calculate dV/dq (spring forces) from stiffness of mooring lines
V_q = PotentialEnergyVariation(q);

% Calculate D_f
%D_f = DampingVariation(q,f);
D_f = zeros(n,1);

% Calculate e (other forces)
Force_Gravity = addGravity(q);
Force_Buoy = addBuoyancy(q);
Force_Fluid = addDrag(q,f,t);
Force_Applied = addApplied(q,t);

e = Force_Gravity + Force_Buoy + Force_Fluid + Force_Applied;

% Evaluate equality constraint equations
[phi_BodyConstraint,phi_q_BodyConstraint] = constrainBody(q);

[psi_Seafloor,psi_q_Seafloor] = constrainSeafloor(q);
[psi_Slackline,psi_q_Slackline] = constrainSlackline(q);

phi = phi_BodyConstraint;
phi_q = phi_q_BodyConstraint;
psi = [psi_Seafloor;psi_Slackline];
psi_q = [psi_q_Seafloor;psi_q_Slackline];

Phi = [M.*a + V_q + D_f + phi_q'*lambda + psi_q'*mu - e;
    phi;
    psi + nu
    mu + nu - sqrt(mu.^2 + nu.^2 + 2*epsilon)
    q_dot - f;
    f_dot - a;];
    