/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author rparaujo
 */
public class RequestManager {

    private JFrame frame;
    private Properties properties = new Properties();
    private String propertiesPath = "../configuration/language.properties";

    public RequestManager(JFrame frame) {
        this.frame = frame;
    }

    public String loadProperty(String property) {

        String result = "";

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesPath));
            result = properties.getProperty(property);

        } catch (IOException ex) {
            Logger.getLogger(RequestManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
