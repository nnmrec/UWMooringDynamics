function makeVideo(MovieName,FrameStruct,fps)

makeVideo = VideoWriter(MovieName);
makeVideo.FrameRate = fps;
makeVideo.Quality = 100;
open(makeVideo);
writeVideo(makeVideo,FrameStruct);
close(makeVideo);