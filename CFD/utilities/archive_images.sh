#!/bin/sh
# this script archives a set of images
# it will move each file into a directory with similar name
# then increment the filenames numerically

# ext="jpg"
ext="*.jpg"
# ext="*.png"

# execute from within the directory of images
# cd ../outputs/scenes/
cd outputs/scenes/

echo "testing"

# Some Linux tools does not properly work with files which include spaces in their names. 
# this simple loop will remove white space from file names and rename/move for all files in the given directory
for f in *; do 
	# mv "$f" `echo $f | tr ' ' '_'`
	mv "$f" `echo $f | tr ' ' '_'` > /dev/null 2>&1
done

# loop over the filenames
for thefile in ${ext} ; do
	# echo $thefile

	# # get the full name with extension, then remove the extension
	filename=$(basename "$thefile")
	fname="${filename%.*}"
	# echo $fname

    # make a directory if it does not exist, for each file basename
    mkdir -p $fname

    # count the number of files in the directory (saved from previous checkpoints)
    # this will tell how to name the file
    numberFiles=$(ls $fname -1 | wc -l)
    # echo $numberFiles

    newName=$fname/$fname"__"$numberFiles$ext
    # echo $newName

    # add zero padding on filenames
    # cp $filename `../../utilities/zeropad.sh $newName`
    mv $filename `../../utilities/zeropad.sh $newName`


done
