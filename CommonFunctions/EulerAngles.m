% X_al-Y_be-Z_gam direction cosine matrix A, Fabien Pg. 61
function A = EulerAngles(alpha,beta,gamma)

A11 = cos(beta)*cos(gamma);
A12 = -cos(beta)*sin(gamma);
A13 = sin(beta);
A21 = sin(alpha)*sin(beta)*cos(gamma)+cos(alpha)*sin(gamma);
A22 = -sin(alpha)*sin(beta)*sin(gamma)+cos(alpha)*cos(gamma);
A23 = -sin(alpha)*cos(beta);
A31 = -cos(alpha)*sin(beta)*cos(gamma)+sin(alpha)*sin(gamma);
A32 = cos(alpha)*sin(beta)*sin(gamma)+sin(alpha)*cos(gamma);
A33 = cos(alpha)*cos(beta);

A = [A11 A12 A13;A21 A22 A23;A31 A32 A33];