#!/bin/sh
# this script will make a movie from a set of images within a directory
# for example, it there exists a set of directories, each containing a series
# of images, it will make a moview within each directory



# execute from within the directory of images
cd ../outputs/scenes/
# cd outputs/scenes/

# loop over the directory names
for dir in ./*/ ; do
    dir=${dir%*/}

    # just the directory name, not the full pathname
    echo ${dir##*/}

    cd $dir

    ffmpeg -pattern_type glob -i '*.jpg' -c:v libvpx -c:a libvorbis -pix_fmt yuv420p -quality good -b:v 2M -crf 5 -vf "scale=trunc(in_w/2)*2:trunc(in_h/2)*2" ${dir##*/}.webm

    cd ..

done
