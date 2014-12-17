/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import gui.EntryPoint;
import gui.NewSessionPanelForm;
import javax.swing.JFrame;

/**
 *
 * @author Heitor
 */
public class NewSessionPanelTest {
    public static void main(String args[]){
        NewSessionPanelForm panel = new NewSessionPanelForm(new EntryPoint());
        JFrame f = new JFrame();
        f.getContentPane().add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setVisible(true);
    }
}
