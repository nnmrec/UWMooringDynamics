function F = AppliedForceGlobal(t)

F = [0;
    0;
    1000*sin(2*pi*t/6)];