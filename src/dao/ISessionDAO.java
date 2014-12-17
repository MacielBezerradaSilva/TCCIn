package dao;

import java.io.FileNotFoundException;
import model.Session;

/**
 *
 * @author heitor
 */
public interface ISessionDAO {
    public void saveSession(Session session, String filename)throws FileNotFoundException;
}
