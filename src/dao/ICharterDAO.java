package dao;

import java.io.IOException;
import java.util.List;
import model.Charter;

/**
 *
 * @author heitor
 */
public interface ICharterDAO {
    public void saveCharter(Charter charter, String filename) throws IOException;
    public Charter getCharter(String id, String filename);
    public List<Charter> getCharters(String filename);
    public String getCharterElementValueByName(String id, String element, String filename);
}
