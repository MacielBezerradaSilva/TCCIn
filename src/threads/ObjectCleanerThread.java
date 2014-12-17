/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rparaujo
 */
public class ObjectCleanerThread extends Thread {
    
    private List<Object> objectList;
    
    public ObjectCleanerThread() {
        objectList = new ArrayList<Object>();
    }

    public void addObject(Object o) {
        this.objectList.add(o);
    }
    
    @Override
    public void run() {
        cycle();
    }
    
    public void cycle() {
        
        while (true) {
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObjectCleanerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//            int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//            
//            for (Object object : objectList) {
//                Component c = (Component) object;
//                c.setVisible(true);
//                c.repaint(0, 0, width, height);
//            }
            
        }
        
    }
    
    
    
}
