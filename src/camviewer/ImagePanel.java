/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camviewer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author coder
 */
public class ImagePanel extends  JPanel{
    
    private final BufferedImage image;
    
    public ImagePanel(BufferedImage image){
        this.image = image;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 1, 1, getWidth()-1, getHeight()-1, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    
}
