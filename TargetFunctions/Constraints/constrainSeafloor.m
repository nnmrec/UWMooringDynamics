function [psi,psi_q] = constrainSeafloor(q)

% Impenetrability included as a constraint applied to the z-displacement of
% each line node and body-fixed frame origin (I know, this doesn't account
% for the shape of the body, but it will be refined at some point... maybe)

% What's the deal with psi? Well, it looks like this:
% psi = (some equation) <= 0
% Say q(3) is constrained to always have a value greater than 5, then the
% equation that goes there is: psi = -q(3) + 5

global Mooring
lines = Mooring.lines;
bodies = Mooring.bodies;

Num_Body = Mooring.NumBody;
Num_Node = Mooring.NumNode;
Num_Line = Mooring.NumLine;
LineDOF = Mooring.LineDOF;

psi = zeros(Num_Body+LineDOF/3, 1);
psi_q = zeros(Num_Body+LineDOF/3, size(q,1));

for i = 1:Num_Body
    BodyIndex = bodies(i).RowIndices;
    psi(i) = -q(BodyIndex(3));
    psi_q(i,BodyIndex(3)) = -1;
end

psicount = Num_Body+1;
for i = 1:Num_Line
    InternalNodes = lines(i).InternalNodes;
    for j = 1:lines(i).NumSegments - 1
        LineNodeIndex = InternalNodes(j).RowIndices;
        psi(psicount) = -q(LineNodeIndex(3));
        psi_q(psicount,LineNodeIndex(3)) = -1;
        psicount = psicount + 1;
    end
end
    