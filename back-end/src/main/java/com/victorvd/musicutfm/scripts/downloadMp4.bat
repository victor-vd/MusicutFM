@echo off
if not exist "src\main\java\com\victorvd\musicutfm\media\temp\cover\lastfm-%1.png" (
    echo ERROR: Cover image not found: lastfm-%1.png
    exit /b 1
)
if not exist "src\main\java\com\victorvd\musicutfm\media\temp\audio\yt-%1.mp3" (
    echo ERROR: Audio file not found: yt-%1.mp3
    exit /b 1
)
ffmpeg -y -loop 1 -i "src/main/java/com/victorvd/musicutfm/media/temp/cover/lastfm-%1.png" -i "src/main/java/com/victorvd/musicutfm/media/temp/audio/yt-%1.mp3" -c:v libx264 -tune stillimage -c:a aac -b:a 192k -pix_fmt yuv420p -shortest "src/main/java/com/victorvd/musicutfm/media/temp/video/musicutfm-%1.mp4"
y
