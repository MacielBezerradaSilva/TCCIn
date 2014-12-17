/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ExploratoryTestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rparaujo
 */
public class XLSFileManagerFactory {

    private static XLSFileManagerFactory xLSFileManagerFactory;

    public static XLSFileManagerFactory getInstance() {

        if (xLSFileManagerFactory == null) {
            xLSFileManagerFactory = new XLSFileManagerFactory();
        }

        return xLSFileManagerFactory;
    }

    public List<ExploratoryTestCase> readSpreadsheet(String filename, String tab, int offset, int testRowLength) {

        List<ExploratoryTestCase> testCases = new ArrayList<ExploratoryTestCase>();
        
        try {

            InputStream file = new FileInputStream(filename);
            Workbook workbook = WorkbookFactory.create(file);

            Sheet sheet = workbook.getSheet(tab);
            Iterator rows = sheet.rowIterator();

            int count = 0;

            while (rows.hasNext()) {

                int auxiliary = 0;
                ExploratoryTestCase etc = new ExploratoryTestCase();

                while (count < testRowLength) {

                    Row row = (Row) rows.next();

                    for (int i = 0; i < offset; i++) {

                        if (row.getCell(i) != null) {

                            if (row.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
                                
                                auxiliary++;

                                if (count == 0) {
                                    if (auxiliary == 1) {
                                        etc.setId(row.getCell(i).getStringCellValue());
                                    }
                                    if (auxiliary == 3) {
                                        etc.setAbstractString(row.getCell(i).getStringCellValue());
                                    }
                                }

                                if (count == 1) {
                                    if (auxiliary == 2) {
                                        etc.setObjectiveString(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 5) {
                                    if (auxiliary == 2) {
                                        etc.setRequirements(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 6) {
                                    if (auxiliary == 2) {
                                        etc.setSetup(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 8) {
                                    if (auxiliary == 1) {
                                        etc.setNotes(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 8 ) {
                                    if (auxiliary == 3) {
                                        etc.setConcentrationAreas(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 9) {
                                    if (auxiliary == 2) {
                                        etc.setIssues(row.getCell(i).getStringCellValue());
                                    }
                                }
                                
                                if (count == 10) {
                                    if (auxiliary == 2) {
                                        etc.setExecutionNotes(row.getCell(i).getStringCellValue());
                                    }
                                }


                            } else if (row.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {

                                auxiliary++;
                                
                                if (count == 2) {
                                    if (auxiliary == 3) {
                                        etc.setMinimum((int) row.getCell(i).getNumericCellValue());
                                    }
                                }

                                if (count == 3) {
                                    if (auxiliary == 2) {
                                        etc.setMaximum((int) row.getCell(i).getNumericCellValue());
                                    }
                                }
                                
                                if (count == 4) {
                                    if (auxiliary == 2) {
                                        etc.setCreatedDate(Double.toString(row.getCell(i).getNumericCellValue()));
                                    }
                                }

                            }

                        }
                    }

                    auxiliary = 0;
                    count++;
                }
                
                testCases.add(etc);
                count = 0;

            }

        } catch (IOException ex) {
            Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return testCases;
        
    }

    /**
     *
     * Faz a leitura de uma planilha desejada e retorna os dados obtidos
     * através de uma String.
     * <p>
     * Leitura feita em {@code EntryPoint}.
     * 
     * @param filename Nome do arquivo Microsoft Excel 2007 (.xls)
     * @param tab Nome da aba desejada no arquivo descrito por {@code filename}
     * @deprecated Substituído por {@code readSpreadsheet(String filename,
     * String tab, int offset)}.
     *
     */
    @Deprecated
    public void readXLS(String filename, String tab) {

        InputStream file = null;
        try {

            file = new FileInputStream(filename);
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheet(tab);
            HSSFRow row;
            HSSFCell cell;

            Iterator rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {

                row = (HSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    cell = (HSSFCell) cellIterator.next();

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.println(cell.getNumericCellValue());
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        System.out.println(cell.getStringCellValue());
                    }

                }

            }


        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * Faz a leitura de uma planilha desejada e retorna os dados obtidos
     * através de uma String.
     * <p>
     * Leitura feita em {@code EntryPoint}.
     * 
     * @param filename Nome do arquivo Microsoft Excel XML Format Spreadsheet (.xlsx)
     * @param tab Nome da aba desejada no arquivo descrito por {@code filename}
     * @deprecated Substituído por {@code readSpreadsheet(String filename,
     * String tab, int offset)}.
     *
     */
    @Deprecated
    public void readXLSX(String filename, String tab) {
        InputStream file = null;
        try {
            
            file = new FileInputStream(filename);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(tab);
            XSSFRow row;
            XSSFCell cell;

            Iterator rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {

                row = (XSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    cell = (XSSFCell) cellIterator.next();

                    if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.println(cell.getNumericCellValue());
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        System.out.println(cell.getStringCellValue());
                    }

                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        finally {
            try {            
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(XLSFileManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
