function Merit = EvaluateMerit(F,q)

Phi = F(q);
Merit = 0.5*(Phi'*Phi);