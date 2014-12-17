/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.IProductDAO;
import dao.ProductDaoXML;
import factory.XMLFileManagerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author rparaujo
 */
public class ProductSaving {

    
    public static void main(String[] args) {
        
        IProductDAO dao = new ProductDaoXML();
        XMLFileManagerFactory factory = XMLFileManagerFactory.getInstance();
        
        
        Product p = new Product();
        p.setName("G");
        p.setProject("IK");
        
        List<String> labels = new ArrayList<String>();
        labels.add("gs");
        labels.add("gs_p");
        
        p.setLabels(labels);
        
        if (!new File("products.xml").exists()) {
            System.out.println("Creation");
            factory.createProductXMLFile("products.xml");
        }
        
        dao.saveProduct(p, "products.xml");
    
    }
    
    
}
