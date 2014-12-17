/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import model.Session;

/**
 *
 * @author rparaujo
 */
public class ProgressThread extends Thread {

    private JProgressBar progressBar;
    private int maximum;
    private boolean paused;
    private final Session session;

    public ProgressThread(JProgressBar progressBar, int maximum, Session session) {
        this.progressBar = progressBar;
        this.maximum = maximum;
        //this.progressBar.setValue(this.maximum);
        if(session.getCurrentTime()==0){
            this.progressBar.setValue(this.maximum);
        }else{
            this.progressBar.setValue(session.getCurrentTime());
        }
        this.paused = false;
        this.session = session;
    }

    @Override
    public void run() {
        execute();
    }
    
    public void update() {
        if (!paused) {
            int value = progressBar.getValue() - 1;
            progressBar.setValue(value);
            session.setCurrentTime(value);
        }
    }

    public synchronized void execute() {

        while (progressBar.getValue() > 0) {

            if (!paused) {
                try {

                    Thread.sleep(1000 * 60);
                    update();

                } catch (InterruptedException ex) {
                    Logger.getLogger(ProgressThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProgressThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    public void halt(int value) {
        paused = true;
    }

    public void restore(int value) {
        paused = false;
    }
}
