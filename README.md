# RecorderGdx

A recorder for LibGdx

change gdxVersion in build.gradle with the version used

HowTo:

      class Example extends ApplicationAdapter {
       Recorder recorder;
       ......
       
       @Override
       public void create () {
              recorder = new Recorder();
              ......
              }
        @Override
        public void render() {
              .....
              recorder.record();
        }  
        
        @Override
        public void dispose() {
              ......
              recorder.onExit
        }
      }


For the postprocessing ffmpeg is needed
 - OSX/LINUX
 
        ffmpeg -framerate 30 -i \tmp\shot_%%06d.png -codec copy output.mp4
 - WIN
 
        ffmpeg -framerate 30 -i tmp\shot_%06d.png -codec copy output.mp4

To double the framerate:

    ffmpeg -i output.mp4 -filter:v "setpts=0.5*PTS" output2.mp4


further info can be found here: https://www.reddit.com/r/libgdx/comments/4qhtm5/how_to_make_hd_video_of_libgdx_game_my_experience/

Visit my website: sarpex.co.uk
