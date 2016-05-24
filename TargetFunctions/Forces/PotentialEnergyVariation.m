function V_q = PotentialEnergyVariation(q)
% Calculates the Variation in Potential Energy term of Lagrange's Equation
% Which is equal/opposite to the spring force, amiright?

global Mooring

lines = Mooring.lines;

% Initialize Spring Force vector F_spring
F_spring = zeros(size(q,1),1);

for i = 1:Mooring.NumLine % Do once for each mooring line:
    % Get the necessary details about line i from global struct 'lines' 
    segLength = lines(i).segLength;
    segK = lines(i).segK;

    % Location of LineStart/End in global coordinates
    LineStartGlobal = findNodeGlobalPosition(q,lines(i).StartNode);
    LineEndGlobal = findNodeGlobalPosition(q,lines(i).EndNode);

    % Find tensions in each line segment. Convert tension in a line
    % segment to vectored forces applied to the ends (nodes) of the segment
    if lines(i).NumSegments == 1 % Mooring line i has one segment
        SegmentVector = LineEndGlobal - LineStartGlobal;
        ForceVectorAtNodeA = findForceVectorFromTension(SegmentVector,segK,segLength);
        
        % Apply force to first node
        F_spring = F_spring + includeForceOnNode(ForceVectorAtNodeA,lines(i).StartNode,q);
        
        % Apply equal/opposite force to second node
        F_spring = F_spring + includeForceOnNode(-ForceVectorAtNodeA,lines(i).EndNode,q);

    else % Mooring line i has >1 segment
        InternalNodes = lines(i).InternalNodes;
        % Find tension in first line segment
        SegmentVector = q(InternalNodes(1).RowIndices) - LineStartGlobal;
        ForceVectorAtNodeA = findForceVectorFromTension(SegmentVector,segK,segLength);
        
        % Apply force to first node
        F_spring = F_spring + includeForceOnNode(ForceVectorAtNodeA,lines(i).StartNode,q);
        
        % Apply equal/opposite force to second node
        F_spring(InternalNodes(1).RowIndices) = F_spring(InternalNodes(1).RowIndices) - ForceVectorAtNodeA;

        % Find tension in interior line segments
        for j = 1:lines(i).NumSegments - 2
            SegmentVector = q(InternalNodes(j+1).RowIndices) - q(InternalNodes(j).RowIndices);
            ForceVectorAtNodeA = findForceVectorFromTension(SegmentVector,segK,segLength);
            % Apply force to first node
            F_spring(InternalNodes(j).RowIndices) = F_spring(InternalNodes(j).RowIndices) + ForceVectorAtNodeA;
            % Apply equal/opposite force to second node
            F_spring(InternalNodes(j+1).RowIndices) = F_spring(InternalNodes(j+1).RowIndices) - ForceVectorAtNodeA;
        end

        % Find tension in last line segment
        SegmentVector = LineEndGlobal - q(InternalNodes(end).RowIndices);
        ForceVectorAtNodeA = findForceVectorFromTension(SegmentVector,segK,segLength);
        % Apply force to first node
        F_spring(InternalNodes(end).RowIndices) = F_spring(InternalNodes(end).RowIndices) + ForceVectorAtNodeA;
        % Apply equal/opposite force to second node
        F_spring = F_spring + includeForceOnNode(-ForceVectorAtNodeA,lines(i).EndNode,q);
    end
end

V_q = -F_spring;

end

function ForceVector = findForceVectorFromTension(SegmentVector,segK,segLength)
    InstantSegLength = norm(SegmentVector);
    InstantSegTension = segK*(InstantSegLength - segLength);
    ForceVector = InstantSegTension*(SegmentVector/InstantSegLength);
end