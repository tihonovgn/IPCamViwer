/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camviewer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class VideoThread implements Runnable
{
    protected volatile boolean runnable = false;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    Graphics graph;
    int width;
    int height;
    JPanel panel;
    
    public  VideoThread(String url, JPanel p) {
        webSource = new VideoCapture(url);
        panel = p;
    }
    
    public void setRunnable(boolean r){
        runnable = r;
    }
    
    @Override
    public  void run()
    {
        synchronized(this)
        {
            while(runnable)
            {
                if(webSource.grab())
                {
                    try
                    {
                        webSource.retrieve(frame);
                        Imgcodecs.imencode(".bmp", frame, mem);
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                        BufferedImage buff = (BufferedImage) im;
                        Graphics g=panel.getGraphics();
                        
                        if (g.drawImage(buff, 1, 1, panel.getWidth()-1, panel.getHeight()-1, 0, 0, buff.getWidth(), buff.getHeight(), null))
                            if(runnable == false)
                            {
                                System.out.println("Going to wait()");
                                this.wait();
                            }
                        g.drawRect(0, 0, panel.getWidth()-1, panel.getHeight()-1);
                    }
                    catch(IOException | InterruptedException ex)
                    {
                        System.out.println("Error"+ex);
                    }
                }
            }
        }
    }
}