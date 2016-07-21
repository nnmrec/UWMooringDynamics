#!/bin/bash

# this is for renaming files with sequential filenames, with zero padding such that
# filename_1.jpg
# becomes
# filename_001.jpg
# this provides easier sorting of files on linux

# from http://stackoverflow.com/questions/5417979/batch-rename-sequential-files-by-padding-with-zeroes
num=`expr match "$1" '[^0-9]*\([0-9]\+\).*'`
# paddednum=`printf "%03d" $num`
paddednum=`printf "%04d" $num`
echo ${1/$num/$paddednum}