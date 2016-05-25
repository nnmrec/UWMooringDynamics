function [OPTIONS] = updateSolverCFD(OPTIONS,iteration)
%updateSolverCFD updates some of the convergence criteria for the CFD
%solver, and updates the Virtual Disks to read latest output files
%   Detailed explanation goes here

%% this is a way to replace a "line of code" within a STAR-CCM+ macro (or any text file)
macroFile = [pwd filesep 'macros' filesep 'updateConvergenceCriteria.java'];

%%
switch iteration
    case 'first'
        % dont iteratively update the criteria yet, use the first user input in OPTIONS
%         firstTime = 'true';
        
    case 'update'
%         firstTime = 'false';
        
        % reason for doing this is to add more iterations if the mooring
        % model needs more outer iterations
        factorMaxIter    = 1.1; % (may want to adjust these values depending on how difficult/quickly to converge)
        factorContinuity = 0.1; % (may want to adjust these values depending on how difficult/quickly to converge)
        
        % how to read this from the JAVA macro?
        OPTIONS.max_iter         = min(    ceil(factorMaxIter   * OPTIONS.max_iter), 1000);         
        OPTIONS.limit_continuity = max( factorContinuity * OPTIONS.limit_continuity, 1e-6);
      
end

% note: this is not a good way to modify the .java file because git will continuosly think this file should be commited
% s0 = ['boolean             firstTime 	     	   = ' firstTime ';'];
s1 = ['static final int    iter_max                = ' num2str(OPTIONS.max_iter) ';'];
s2 = ['static final double limit_continuity        = ' num2str(OPTIONS.limit_continuity) ';'];

  
%% replace max iterations and continuity
c      = textread(macroFile,'%s','delimiter','\n');
% lines0 = find(~cellfun(@isempty,strfind(c,'firstTime')));
lines1 = find(~cellfun(@isempty,strfind(c,'iter_max')));
lines2 = find(~cellfun(@isempty,strfind(c,'limit_continuity')));

% c{lines0(1)} = s0;  % replace certain lines in the file
c{lines1(1)} = s1;
c{lines2(1)} = s2;

fid = fopen(macroFile, 'w');   % rewrite the entire file
fprintf(fid, '%s\n', c{:});
fclose(fid);
        
% now that STAR-CCM+ macro has been modified, run the macro to update
% system(['starccm+ -batch macros/' macro1 ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@flex.cd-adapco.com -power -podkey $myPODkey -batch-report runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);

%%DEBUG:
fprintf(1, ['Matlab DEBUG: updated max_iter = ' num2str(OPTIONS.max_iter) '\n']);
fprintf(1, ['Matlab DEBUG: updated limit_continuity = ' num2str(OPTIONS.limit_continuity) '\n']);

end % function

