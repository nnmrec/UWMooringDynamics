function [psi,psi_q] = constrainSlackline(q)

% What's the deal with psi? Well, it looks like this:
% psi = (some equation) <= 0
% Say q(3) is constrained to always have a value greater than 5, then the
% equation that goes there is: psi = -q(3) + 5

global Mooring

lines = Mooring.lines;
nodes = Mooring.nodes;
bodies = Mooring.bodies;

Num_Line = Mooring.NumLine;
n = Mooring.TotalDOF;
Num_Segments = [lines(:).NumSegments];

psi = zeros(sum(Num_Segments), 1);
psi_q = zeros(size(psi,1), n);

psicount = 1;

for i = 1:Num_Line
    % Find start and end node positions in global frame
    % Location of LineStart in global coordinates
    LineStartGlobal = findNodeGlobalPosition(q,lines(i).StartNode);
    
    % Location of LineEnd in global coordinates
    LineEndGlobal = findNodeGlobalPosition(q,lines(i).EndNode);
    
    if lines(i).NumSegments == 1 % Mooring line i has one segment
        %TODO: Write code for single segment line case
        psicount = psicount + 1;
        pause
    else % Mooring line i has >1 segment
        InternalNodes = lines(i).InternalNodes;
        SegmentVector = q(InternalNodes(1).RowIndices) - LineStartGlobal;
        
        psi(psicount) = lines(i).segLength^2 - SegmentVector'*SegmentVector; % psi = L0^2 - L^2 <= 0
        
        % populate psi_q
        if strcmpi(nodes(lines(i).StartNode).Type,'BodyFixed')
            % Currently assumes StartNode is coincident with body COM
            % TODO: Update to allow non-coincident StartNode, full range of motion
            Index = bodies(nodes(lines(i).StartNode).ParentBody).RowIndices;
            psi_q(psicount,Index) = 2*SegmentVector;
        elseif strcmpi(nodes(lines(i).StartNode).Type,'Free')
            Index = nodes(lines(i).StartNode).RowIndices;
            psi_q(psicount,Index) = 2*SegmentVector;
        end
        psi_q(psicount,InternalNodes(1).RowIndices) = -2*SegmentVector;
        
        psicount = psicount + 1;
        for j = 1:Num_Segments(i) - 2
            SegmentVector = q(InternalNodes(j+1).RowIndices) - q(InternalNodes(j).RowIndices);
            psi(psicount) = lines(i).segLength^2 - SegmentVector'*SegmentVector; % psi <= 0
            psi_q(psicount,InternalNodes(j).RowIndices) = 2*SegmentVector;
            psi_q(psicount,InternalNodes(j+1).RowIndices) = -2*SegmentVector;
            psicount = psicount + 1;
        end
        SegmentVector = LineEndGlobal - q(InternalNodes(end).RowIndices);
        psi(psicount) = lines(i).segLength^2 - SegmentVector'*SegmentVector; % psi <= 0
        
        % populate psi_q
        psi_q(psicount,InternalNodes(end).RowIndices) = 2*SegmentVector;
        if strcmpi(nodes(lines(i).EndNode).Type,'BodyFixed')
            % Currently assumes StartNode is coincident with body COM
            % TODO: Update to allow non-coincident StartNode, full range of motion
            Index = bodies(nodes(lines(i).EndNode).ParentBody).RowIndices;
            psi_q(psicount,Index(1:3)) = -2*SegmentVector;
        elseif strcmpi(nodes(lines(i).EndNode).Type,'Free')
            Index = nodes(lines(i).EndNode).RowIndices;
            psi_q(psicount,Index) = -2*SegmentVector;
        end
        psicount = psicount + 1;
    end
end