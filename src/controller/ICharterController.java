package controller;

import java.io.IOException;
import java.util.List;
import model.Charter;

/**
 *
 * @author Heitor
 */
public interface ICharterController {
    public void createCharter(Charter charter, String filename) throws IOException;
    public List<Charter> getCharters(String filename) throws IOException;
}
