function eCFD = addCFD(CFD_raw)

global Mooring

bodies = Mooring.bodies;

n = Mooring.TotalDOF;

eCFD = zeros(n,1);
for i = 1:size(CFD_raw,1)
    RowIndices = bodies(i).RowIndices;
    eCFD(RowIndices) = CFD_raw(i,:);
end