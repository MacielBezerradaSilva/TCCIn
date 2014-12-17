/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import model.Charter;

public class RendererImpl extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = null;
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Charter ch =(Charter) value;
        setText("1111111111111111111111111111111111"); // where getValue is some method you implement that gets the text you want to render for the component
//        setText(ch.getName()); // where getValue is some method you implement that gets the text you want to render for the component
        return c;
    }
}
