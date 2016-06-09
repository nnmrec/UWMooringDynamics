#!/bin/bash
## --------------------------------------------------------
## NOTE: to submit jobs to Hyak use
##		  qsub submit.sh -F FileNameOfSimfile.sim
## --------------------------------------------------------

## RENAME for your job
## --------------------------------------------------------
#PBS -N mooring

## DIRECTORY where this job is run
## --------------------------------------------------------
## PBS -d ./

## GROUP to run under, or run under backfill
## --------------------------------------------------------
#PBS -W group_list=hyak-motley
## PBS -W group_list=hyak-stf
## PBS -q bf
## PBS -W group_list=hyak-fluids

## NUMBER nodes, CPUs per node, and MEMORY
## --------------------------------------------------------
#PBS -l nodes=1:ppn=16,mem=50gb
## PBS -l nodes=2:ppn=16,mem=100gb

## WALLTIME (defaults to 1 hour, always specify for longer jobs)
## --------------------------------------------------------
#PBS -l walltime=00:30:00

## LOG the (stderr and stdout) job output in the directory
## --------------------------------------------------------
#PBS -j oe -o /gscratch/fluids/danny

## EMAIL to send when job is aborted, begins, and terminates
## --------------------------------------------------------
#PBS -m abe -M sale.danny@gmail.com

## END of PBS commmands ... only BASH from here and below
## --------------------------------------------------------

# change to directory where qsub was run
echo 'qsub called from this directory:'
echo $PBS_O_WORKDIR
cd $PBS_O_WORKDIR
echo 'current directory is:'
pwd


## LOAD modules needed
module load contrib/starccm_11.02.010-R8
module load matlab_2015b


## RUN my simulation file in batch mode from an interactive session on Hyak
matlab -nodisplay -nojvm < UWMooringDynamics_Chapter4.m


## FINAL REPORT
echo ' '
echo 'all has finished, job should stop now'
