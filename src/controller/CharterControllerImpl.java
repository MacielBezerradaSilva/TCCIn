/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CharterDaoXML;
import dao.ICharterDAO;
import java.io.IOException;
import java.util.List;
import model.Charter;

public class CharterControllerImpl implements ICharterController{

    private ICharterDAO dao;
    
    public CharterControllerImpl(){
        this.dao = new CharterDaoXML();
    }
    
    @Override
    public void createCharter(Charter charter, String filename) throws IOException {
        dao.saveCharter(charter, "Charters.xml");
    }

    @Override
    public List<Charter> getCharters(String filename) throws IOException {
        return dao.getCharters("Charters.xml");
    }
    
}
