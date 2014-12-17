/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.IProductDAO;
import dao.ProductDaoXML;
import model.Product;

/**
 *
 * @author rparaujo
 */
public class ProductLoad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IProductDAO dao = new ProductDaoXML();
        
        Product[] products = dao.getProducts("products.xml");
        
        for (Product product : products) {
            System.out.println(product.toString());
        }
        
        String[] temp = dao.getProductNames("products.xml");
        for (String string : temp) {
            System.out.println("String: " + string);
        }
        
    }
}
