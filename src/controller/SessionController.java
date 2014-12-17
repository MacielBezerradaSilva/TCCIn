package controller;

import dao.SessionDaoXML;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import model.Session;

/**
 *
 * @author heitor
 */
public class SessionController {
    
    private static SessionController instance;
    private SessionDaoXML dao;
    
    private SessionController(){
        dao = new SessionDaoXML();
    }
    
    public static synchronized SessionController getInstance(){
        if(instance == null){
            instance = new SessionController();
        }
        return instance;
    }
    
    public void saveSession(Session session, String filename) throws FileNotFoundException{
        dao.saveSession(session, filename);
    }
    
    public Session loadSession(String filename) throws IOException{
        return dao.loadSession(filename);
    }

    public void generateReport(Session session, String name) {
        dao.createReport(session, name + ".html");
    }
}
