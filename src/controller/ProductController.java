package controller;

import dao.IProductDAO;
import dao.ProductDaoXML;
import model.Product;

/**
 *
 * @author heitor
 */
public class ProductController {

    private static ProductController instance;
    private IProductDAO dao;

    public ProductController() {
        dao = new ProductDaoXML();
    }

    public synchronized static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    public Product[] getProducts() {
        return dao.getProducts("products.xml");
    }

    public Product getProductByName(String name) {
        return dao.getProductByName(name, "products.xml");
    }

    public Product getProductById(String id) {
        return dao.getProductByID(id, "products.xml");
    }
    
    public Product getProductByIndex(String id) {
        return dao.getProductByIndex(id, "products.xml");
    }
    
    public String[] getProductNames() {
        return dao.getProductNames("products.xml");
    }
}
