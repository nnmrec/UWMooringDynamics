% xyzProbes: table with x, y, z positions of line segment centers
% xyzBody: table with x, y, z positions of body COMs
% [VelocityAtProbes,ForcesOnBodies] = runStarCCM(xyzProbes,xyzBody,Mooring);
% OPTIONS = Mooring.OptionsCFD;

function OPTIONS = runSTARCCM(OPTIONS)
%runSTARCCM runs the STAR-CCM+ model
%   Detailed explanation goes here

% 1) create a new empty file
% 2) save it ... it will always have the same name
% 3) now rename the empty file to a new file and give a meaningful filename
    
    
%% run STAR-CCM+
% 1) create a new empty file
% 2) save it ... it will always have the same name
% 3) now rename the empty file to a new file and give a meaningful filename
system(['starccm+ -batch macros/saveEmptySimFile.java -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new']);
system(['mv empty_case.sim runs.' OPTIONS.starSimFile]);
    

% if runOnHPC
%    % should this section also modify the PBS script, based on mesh size, etc.  ?
%     system('./submit-job-Hyak.sh');
% else
    % this is the old way for POD key license
%     system(['starccm+ -batch macros/' macro ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@flex.cd-adapco.com -power -podkey $myPODkey -batch-report runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);
    
    % this is the new way for when license is accessed via license server
    % if on your local computer 1999@lmas.engr.washington.edu
    % if on Hyak 1999@mgmt2.hyak.local, which just re-routes to 1999@lmas.engr.washington.edu
%     system(['starccm+ -batch macros/' macro ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@mgmt2.hyak.local -batch-report -new runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);
%     starccm+ -batch $starMacros -np num2str(OPTIONS.nCPUs) -licpath 1999@mgmt2.hyak.local -batch-report runs.$caseName.sim 2>&1 | tee log.$caseName

% end

% can run on your local *nix computer or supercomputer ... sorry Windows is not supported! :-P
% note: there is a PBS script which I think can be used for either case: HPC or local workstation
if OPTIONS.runOnHPC
%     system('submit-job.sh')
    system(['starccm+ -batch macros/main.java -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@mgmt2.hyak.local -batch-report runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);
else
    system(['starccm+ -batch macros/main.java -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -batch-report runs.' OPTIONS.starSimFile ' 2>&1 | tee log.' OPTIONS.starSimFile]);
    
    % so .. starccm+ upon a new file save as a silly filename ... should use a
    % bash command to rename file with a more meaningful filename
%     % 1) create a new empty file
%     % 2) save it ... it will always have the same name
%     % 3) now rename the empty file to a new file and give a meaningful filename
%     system(['starccm+ -batch macros/saveEmptySimFile.java -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new']);
%     system(['mv empty_case.sim runs.' OPTIONS.starSimFile]);
    
    %    filename OPTIONS.starSimFile  ... could optionally delete the empty cases.sim and .sim~ files
    %    to do this the OPTIONS.starSimFile needs to be passes to a .java macro?
%     system(['starccm+ -np -batch ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new Star 1.sim']);   % dont actually do anything except make an empty file to rename later
%     system(['starccm+ -batch -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new']);   % dont actually do anything except make an empty file to rename later
%     system(['starccm+ -batch macros/ -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new']);   % dont actually do anything except make an empty file to rename later
    
%     system(['starccm+ -batch macros/saveEmptySimFile.java -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -batch-report -new']);
    
    
    % note will need to modify the above two lines to run within a PBS script
    
%     system('rm *.html');    % remove the log html file ... it is an empty temporary file so it is not important
    % now we have a unique filename, but still probably do not want to track this file in GitHub 
    
%     system(['starccm+ -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -new runs.case.sim']);
%     system(['starccm+ -batch macros/' macro ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -batch-report -new runs.case.sim']);
%     system(['starccm+ -batch macros/' macro ' -np ' num2str(OPTIONS.nCPUs) ' -licpath 1999@lmas.engr.washington.edu -batch-report -new runs.case 2>&1 | tee log.' OPTIONS.starSimFile]);
    
end

% for debugging on local workstation:
% starccm+ -np 8 -licpath 1999@lmas.engr.washington.edu -new runs.mezzanine.sim
% starccm+ -new runs.mezzanine.sim -np 8 -licpath 1999@lmas.engr.washington.edu 






end

