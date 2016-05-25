%% [body, node] = function mezzanine(optionsFile)
% structure body contains 3 forces and 3 moments at the center of mass of bodies
% structure nodes contains 3 forces at the mooring line segments


%% STARTUP - start from a clean slate, and add any dependencies to the path
clear all
close all
clc

addpath(genpath([pwd filesep 'utilities']));


% name of options "input file"
% optionsFile = 'options_Mezzanine_small_propeller.m';      
optionsFile = 'Mezzanine_small.m';                
% optionsFile = 'options_Mezzanine_supersmall.m';      
% optionsFile = 'options_Mezzanine.m';            



% read the main user options (read/initialize "user inputs" ... note: some of the java macros also have "user inputs")
[OPTIONS,filesIO,probes,rotors] = initMain(optionsFile);

%% WRITE the initial conditions to "input files" for the CFD model 
writeInputsProbes(filesIO,probes);  % writes a file probes.csv in the CFD output directory
writeInputsRotors(filesIO,rotors);  % writes a file rotors.csv in the CFD output directory

%% RUN the CFD model for first time
% OPTIONS = runSTARCCM(OPTIONS,'main.java');
OPTIONS = runSTARCCM(OPTIONS);
% note: for adaptive mesh refinement should create a similar but different runSTARCCM.m function

%% READ the output from the CFD model
% note: this only reports 1D velocity, 1D forcesand moments thrust and torque, but
% should add 3 components of velocity, 3 forces, and 3 moments about the endpoint mooring node in local coordinate system 
% (as opposed to the body center-of-mass)
[probes, rotors] = readOutputs(filesIO,probes,rotors);
        
%% RE-MESH Adaptive-Mesh-Refinement (AMR) ... should only need this on the first iteration of the CFD model; because movement from mooring model is expected to be "small"
%  or "small" meaning some estimated/pre-definecd watch circle
% runSTARCCM_adaptiveMeshRefine(OPTIONS,'remeshAMR.java');




%% now need to collect the ouputs in a format that is easy to read by the mooring model












%% ITERATE between running the CFD and Mooring models
for n = 1:OPTIONS.nUpdateMooring
    
    % RUN STAR-CCM+, and as the velocity field update ... the rotor speed
    % should also update to perform at target tip-speed-ratio, this call
    % also updates the positions of the rotors (from the mooring model)
    [rotors] = adjustRotorSpeeds(OPTIONS,filesIO,rotors);
    
    % READ the output from the CFD model
    [probes, rotors] = readOutputs(filesIO,probes,rotors);
    
    % RUN the MOORING model to calculate new positions after the CFD model has "converged"
    [probes, rotors] = mooring(OPTIONS, probes, rotors);
    
    % WRITE the updated xyz coordinates for next iteration of CFD model
    writeInputsProbes(filesIO,probes);
    writeInputsRotors(filesIO,rotors);

end

