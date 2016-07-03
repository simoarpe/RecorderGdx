package uk.co.sarpex.recorderGdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sarpex.net on 01/07/2016.
 */
public class Recorder
{
    private ExecutorService executor;

  public Recorder(ExecutorService executor) {
      this.executor = executor;

  }

    public Recorder() {
        this.executor = Executors.newFixedThreadPool(25);

    }
    //inside render in the end
    public void record() {
        ScreenShot worker = new ScreenShot();
        worker.prepare();			// grab screenshot
        executor.execute(worker);	// delayed save in other thread
    }

    public void onExit() {
        try {
            executor.awaitTermination(240, TimeUnit.SECONDS); // give it time to finish writing screenshots out
        } catch (InterruptedException ie) {
            executor.shutdownNow();
        }

        long count = ScreenShot.getFileCounter(); // how many frames have been captured
        PixmapIO.PNG ppng = new PixmapIO.PNG(16000000);
        ppng.setFlipY(true);
        while (count > 0) {
            Gdx.app.log("CONVERTING", ""+count);
            try {
                FileHandle file = new FileHandle("tmp/shot_" + String.format("%06d", count) + ".cim");
                Pixmap p = PixmapIO.readCIM(file);
                FileHandle file_png = new FileHandle("tmp/shot_" + String.format("%06d", count) + ".png");
                ppng.write(file_png, p);
            } catch (Exception e) {
            }
            count--;
        }
    }
    //OSX LINUX
    //ffmpeg -framerate 30 -i \tmp\shot_%%06d.png -codec copy output.mp4
    //WIN
    //ffmpeg -framerate 30 -i tmp\shot_%06d.png -codec copy output.mp4

    //this will double the framerate
    //ffmpeg -i output.mp4 -filter:v "setpts=0.5*PTS" output2.mp4
}