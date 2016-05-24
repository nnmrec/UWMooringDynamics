function pGlobal = trans(pOrigin,pBody)
%pOrigin - Location of body-fixed frame origin in global frame and angles from body
%          fixed frame axes to corresponding global axes using 123 Euler angles [x y z al be ga]
%pBody - Location of point on body in body-fixed frame  [x1 y1 z1]
%pGlobal - Location of point on body in global frame
numPts = size(pBody,1); %Number of points of interest on body

x = pOrigin(1);
y = pOrigin(2);
z = pOrigin(3);
al = pOrigin(4);
be = pOrigin(5);
gam = pOrigin(6);

A = EulerAngles(al,be,gam);
pGlobal = zeros(numPts,3);

for j = 1:numPts
    pGlobal(j,:) = [x;y;z]+A*[pBody(j,1);pBody(j,2);pBody(j,3)];
end