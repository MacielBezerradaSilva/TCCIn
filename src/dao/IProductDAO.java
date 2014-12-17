/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Product;

/**
 *
 * @author rparaujo
 */
public interface IProductDAO {
    
    public void saveProduct(Product product, String filename);
    public Product[] getProducts(String filename);
    public String[] getProductNames(String filename);
    public Product getProductByName(String name, String filename);
    public Product getProductByID(String id, String filename);
    public Product getProductByIndex(String index, String filename);
}
