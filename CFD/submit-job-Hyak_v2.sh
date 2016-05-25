#!/bin/bash
## --------------------------------------------------------
## NOTE: to submit jobs to Hyak use
##       qsub -v FileNameOfSimfile.sim <script.sh>
##
## #PBS is a directive requesting job scheduling resources
## and ALL PBS directives must be at the top of the script,
## standard bash commands can follow afterwards.
## --------------------------------------------------------

## RENAME for your job
## --------------------------------------------------------
#PBS -N batch

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
#PBS -l nodes=1:ppn=16,mem=62gb,feature=intel
## PBS -l nodes=2:ppn=16,mem=60gb,feature=intel
## PBS -l nodes=3:ppn=16,mem=150gb,feature=intel
## PBS -l nodes=6:ppn=16,mem=350gb,feature=intel
## PBS -l nodes=8:ppn=16,mem=440gb,feature=intel
## PBS -l nodes=16:ppn=16,mem=60gb,feature=intel

## WALLTIME (defaults to 1 hour, always specify for longer jobs)
## --------------------------------------------------------
#PBS -l walltime=00:30:00

## LOG the (stderr and stdout) job output in the directory
## --------------------------------------------------------
#PBS -j oe -o /gscratch/fluids/danny
## PBS -j oe -o /gscratch/stf/abb91/Hyak

## --------------------------------------------------------
## EMAIL to send when job is aborted, begins, and terminates
## --------------------------------------------------------
##PBS -m abe -M sale.danny@gmail.com
#PBS -m abe -M abb91@uw.edu

## END of PBS commmands ... only BASH from here and below
## --------------------------------------------------------



# change to directory where qsub was run
echo 'qsub called from this directory'
echo $PBS_O_WORKDIR
cd $PBS_O_WORKDIR

#starSimFile="*.sim"
starSimFile=$1
echo 'star .sim file is'
echo $1
echo $starSimFile
caseName=$(echo $starSimFile | cut -f 1 -d '.')

## DIR where starccm+ will run
mkdir $caseName

## MOVE sim file into directory structure (do not overwrite in case of checkpointing)
#mv $starSimFile $dirName
cp --no-clobber $starSimFile $caseName

## DIR where to run starccm+
cd $caseName


## KEEP copy of the initial cleared solution (small file size),
## and rename file used for restart after checkpointing (this file gets big)
cp --no-clobber $starSimFile runs.$caseName.sim
#rm log.*
#rm *.sim~


# check
echo 'current directory'
pwd


## RESTART if running in the backfill, account for checkpointing and restarting
# by checking if a logfile exists
#logFile="log.*"
#if [ "$(ls -A $logFile)" ]; then
#    echo "Do not re-create mesh because a logfile exists"
#    echo "Attempt to continue solver from last iteration auto-save"
#    starMacros="../run.java"
#else
#    echo "A log file was not found, that is okay ..."
#    echo "The logfile is empty or not found"
#    echo "Starting from a clean state, re-mesh and initialize"
    starMacros="../main.java"
#fi

## LOAD modules needed
module load contrib/starccm_11.02.010-R8
module load matlab_2015b


## RUN my simulation file in batch mode from an interactive session on Hyak
matlab -nodisplay -nojvm < mezzanine.m
starccm+ -batch $starMacros -np ${PBS_NP} -machinefile ${PBS_NODEFILE} -licpath 1999@mgmt2.hyak.local -batch-report runs.$caseName.sim 2>&1 | tee log.$caseName

## REPORT
echo ' '
echo 'starccm+ has finished, job should stop now'
