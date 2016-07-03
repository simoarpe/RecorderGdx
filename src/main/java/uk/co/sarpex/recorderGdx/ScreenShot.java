package uk.co.sarpex.recorderGdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

/**
 * Created by Sarpex.net on 01/07/2016.
 */
class ScreenShot implements Runnable
{
    private static int fileCounter = 0;
    private Pixmap pixmap;

    @Override
    public void run()
    {
        saveScreenshot();
    }

    public void prepare()
    {
        getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void saveScreenshot()
    {
        FileHandle file = new FileHandle("tmp/shot_"+ String.format("%06d",  fileCounter++) + ".cim");
        PixmapIO.writeCIM(file, pixmap);
        pixmap.dispose();
    }

    public void getScreenshot(int x, int y, int w, int h, boolean flipY)
    {
        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
        pixmap = new Pixmap(w, h, Pixmap.Format.RGB888); // this and GL_RGB gives properly composited image, no transparency
        Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, pixmap.getPixels());
    }

    public static int getFileCounter() {
        return fileCounter;
    }
}
