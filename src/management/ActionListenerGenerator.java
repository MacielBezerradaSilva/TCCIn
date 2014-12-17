/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import gui.EntryPoint;
import gui.NewCharterPanelForm;
import gui.NewSessionPanelForm;
import gui.SessionPanelForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Session;

/**
 *
 * @author rparaujo
 */
public class ActionListenerGenerator {

    private EntryPoint entryPoint;
    
    public ActionListenerGenerator(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public ActionListener generateNewSessionBySpreadSheetListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //entryPoint.newSessionPopup();
                entryPoint.restoreSession();
            }
        };

        return actionListener;
    }

    public ActionListener generateExitListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        return actionListener;
    }
    
    public ListSelectionListener generateListSelectionListener(JList list, int index) {
        
        ListSelectionListener listSelectionListener = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
//                index = e.
            }
        };
        
        return listSelectionListener;
    }

    public ActionListener generateNewSessionListener(final EntryPoint frame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.switchToPanel(new NewSessionPanelForm(frame));
            }
        };
    }
    
    public ActionListener generateNewCharterListener(final EntryPoint frame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.switchToPanel(new NewCharterPanelForm());
            }
        };
    }
}
