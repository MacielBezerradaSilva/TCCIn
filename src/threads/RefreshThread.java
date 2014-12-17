/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import gui.EntryPoint;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rparaujo
 */
public class RefreshThread extends Thread {
    
    private EntryPoint entryPoint;
    
    public RefreshThread(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Override
    public void run() {
        cycle();
    }
    
    public void cycle() {
        
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        
        while (true) {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RefreshThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            entryPoint.repaint(0, 0, width, height);
            
//            for (Component c : entryPoint.getComponents()) {
//                c.setVisible(true);
//                c.repaint(0, 0, c.getWidth(), c.getHeight());
//                c.update(entryPoint.getGraphics());
//            }
        }
        
    }
    
}
