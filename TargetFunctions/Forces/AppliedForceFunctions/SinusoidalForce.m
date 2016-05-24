function Force = SinusoidalForce(x,y,z,Time)
T = 5; % Period
A = 5000; % Magnitude


Fx = 0;
Fy = 0;
Fz = A*sin(Time*2*pi/T);
Force = [Fx;Fy;Fz];
            
    