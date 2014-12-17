/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    public Image getBackgroundImage() {
        return this.image;
    }

    public void setBackgroundImage(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

//        int h = image.getHeight(null);
//        int w = image.getWidth(null);
//
//        if (w > this.getWidth()) {
//            image = image.getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT);
//            h = image.getHeight(null);
//        }
//        
//        if (h > this.getHeight()) {
//            image = image.getScaledInstance(-1, getHeight(), Image.SCALE_DEFAULT);
//        }
//        
//        int x = (getWidth() - image.getWidth(null)) / 2;
//        int y = (getHeight() - image.getHeight(null)) / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
//        g2d.drawImage("Sunset.jpg");
//        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}