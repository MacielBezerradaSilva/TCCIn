/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.CharterDaoXML;
import factory.XMLFileManagerFactory;
import java.io.File;
import model.Charter;

public class CharterSaving {

    public static void main(String args[]) {
        
        XMLFileManagerFactory xmlFactory = XMLFileManagerFactory.getInstance();
        Charter c1 = new Charter("Teste no CharterDaoXML 11111111111111111111111111111111");
        c1.setMinTime(10);
        c1.setMaxTime(20);
        c1.setObjective("Objective");
        c1.setNotes("Notes");
        c1.setEtId("01");
        c1.setIssuesToBeAware("Issues");
        c1.setSetup("SET1");
        
        CharterDaoXML cdx = new CharterDaoXML();
        if (!new File("Charters.xml").exists()) {
            System.out.println("Creation");
            xmlFactory.createCharterXMLFile("Charters.xml");
        }
        cdx.saveCharter(c1, "Charters.xml");
        
    }
}
