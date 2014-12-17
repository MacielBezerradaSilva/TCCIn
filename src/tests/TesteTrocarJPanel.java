package tests;

import gui.SessionPanelForm;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Charter;
import model.Session;

/**
 *
 * @author heitor
 */
public class TesteTrocarJPanel {

    public static void main(String[] args) throws InterruptedException {
        Session session = new Session(new Charter("Charter de teste"));
        SessionPanelForm panel = new SessionPanelForm(session);
        final JFrame f = new JFrame();
        f.setContentPane(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setVisible(true);
        Thread.sleep(1000);
        System.out.print("vai");
        f.setContentPane(new JPanel());
        f.validate();
    }
}