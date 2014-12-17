/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import factory.XLSFileManagerFactory;
import gui.EntryPoint;
import java.awt.Component;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ExploratoryTestCase;

/**
 *
 * @author rparaujo
 */
public class LoadSheetThread extends Thread {

    private EntryPoint entryPoint;
    private String filename;
    private String tab;
    private int offset;
    private int testRowLength;

    public LoadSheetThread(EntryPoint entryPoint, String filename, String tab,
            int offset, int testRowLength) {
        this.entryPoint = entryPoint;
        this.filename = filename;
        this.tab = tab;
        this.offset = offset;
        this.testRowLength = testRowLength;
    }
    
    @Override
    public void run() {
        execute();
    }
    
    public synchronized void execute() {
        
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadSheetThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XLSFileManagerFactory xLSFileManagerFactory = XLSFileManagerFactory.getInstance();
        List<ExploratoryTestCase> testCases = xLSFileManagerFactory.readSpreadsheet(filename, tab, offset, testRowLength);
        entryPoint.setLoadedTestCases(testCases);
        
        entryPoint.alterContent(testCases);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadSheetThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        entryPoint.setTitle(entryPoint.getTitle() + " (" + filename + ")");
        entryPoint.removeLoadingIcon();

    }
}
