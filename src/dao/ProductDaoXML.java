/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Charter;
import model.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author heitor
 */
public class ProductDaoXML implements IProductDAO {

    @Override
    public void saveProduct(Product product, String filename) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            Node node = document.getElementsByTagName("Products").item(0);

            int productCount = document.getElementsByTagName("Product").getLength() + 1;
            product.setId(String.valueOf(productCount));

            Element productNode = document.createElement("Product");

            Element id = document.createElement("Id");
            id.setTextContent(product.getId());
            productNode.appendChild(id);

            Element name = document.createElement("Name");
            name.setTextContent(product.getName());
            productNode.appendChild(name);

            Element project = document.createElement("Project");
            project.setTextContent(product.getProject());
            productNode.appendChild(project);

            Element labels = document.createElement("Labels");

            for (String label : product.getLabels()) {
                Element l = document.createElement("Label");
                l.setTextContent(label);
                labels.appendChild(l);
            }

            productNode.appendChild(labels);

            node.appendChild(productNode);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Product[] getProducts(String filename) {

        List<Product> productList = new ArrayList<Product>();
        Product[] products = null;

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("Product");

            for (int i = 0; i < nodes.getLength(); i++) {

                //Charter element
                NodeList list = nodes.item(i).getChildNodes();
                Product result = new Product();

                for (int j = 0; j < list.getLength(); j++) {

                    Node temp = list.item(j);

                    if (temp.getNodeName().equalsIgnoreCase("Id")) {
                        result.setId(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("Name")) {
                        result.setName(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("Project")) {
                        result.setProject(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("Labels")) {

                        List<String> labels = new ArrayList<String>();
                        NodeList labelList = temp.getChildNodes();

                        for (int k = 0; k < labelList.getLength(); k++) {
                            Node labelTemp = labelList.item(k);
                            labels.add(labelTemp.getTextContent());
                        }

                        result.setLabels(labels);
                    }

                }

                for (int a = 0; a < result.getLabels().size(); a++) {
                    for (int j = 0; j < result.getLabels().size(); j++) {
                        if (result.getLabels().get(j).trim().equalsIgnoreCase("")) {
                            result.getLabels().remove(result.getLabels().get(j));
                        }
                    }
                }
                productList.add(result);

            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!productList.isEmpty()) {
            products = new Product[productList.size()];

            for (int i = 0; i < productList.size(); i++) {
                products[i] = productList.get(i);
            }

        }

        return products;

    }

    @Override
    public String[] getProductNames(String filename) {

        Product[] products = getProducts(filename);
        String[] productNames = new String[products.length];

        for (int i = 0; i < products.length; i++) {
            productNames[i] = products[i].getName();
        }

        return productNames;
    }

    @Override
    public Product getProductByName(String name, String filename) {
        Product p = new Product();
        p.setName(name);
        return p;
    }

    @Override
    public Product getProductByID(String id, String filename) {

        Product result = null;
        int tempId = Integer.valueOf(id) + 1;

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();
            //all products
            NodeList nodes = document.getElementsByTagName("Product");

            //loop all products
            for (int i = 0; i < nodes.getLength(); i++) {
                //Elements of a "i" product 
                NodeList list = nodes.item(i).getChildNodes();

                for (int j = 0; j < list.getLength(); j++) {

                    if (list.item(j).getNodeName().equalsIgnoreCase("Id")) {

                        if (list.item(j).getTextContent().equalsIgnoreCase(String.valueOf(tempId))) {

                            result = new Product();

                            for (int k = 0; k < list.getLength(); k++) {

                                Node temp = list.item(k);

                                if (temp.getNodeName().equalsIgnoreCase("Id")) {
                                    result.setId(temp.getTextContent());
                                }

                                if (temp.getNodeName().equalsIgnoreCase("Name")) {
                                    result.setName(temp.getTextContent());
                                }

                                if (temp.getNodeName().equalsIgnoreCase("Project")) {
                                    result.setProject(temp.getTextContent());
                                }

                                if (temp.getNodeName().equalsIgnoreCase("Labels")) {

                                    List<String> labels = new ArrayList<String>();
                                    NodeList labelList = temp.getChildNodes();

                                    for (int l = 0; l < labelList.getLength(); l++) {
                                        Node labelTemp = labelList.item(l);
                                        labels.add(labelTemp.getTextContent());
                                    }

                                    result.setLabels(labels);
                                }

                                for (int a = 0; a < result.getLabels().size(); a++) {
                                    for (int b = 0; b < result.getLabels().size(); b++) {
                                        if (result.getLabels().get(b).trim().equalsIgnoreCase("")) {
                                            result.getLabels().remove(result.getLabels().get(b));
                                        }
                                    }
                                }

                            }
                        }

                    }

                }

            }




        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    @Override
    public Product getProductByIndex(String index, String filename) {

        System.out.println("Index: " + index);
        Product result = null;

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();
            //all products
            NodeList nodes = document.getElementsByTagName("Product");

            NodeList productElements = nodes.item(Integer.valueOf(index)).getChildNodes();

            result = new Product();

            for (int i = 0; i < productElements.getLength(); i++) {

                Node temp = productElements.item(i);

                if (temp.getNodeName().equalsIgnoreCase("Id")) {
                    result.setId(temp.getTextContent());
                }

                if (temp.getNodeName().equalsIgnoreCase("Name")) {
                    result.setName(temp.getTextContent());
                }

                if (temp.getNodeName().equalsIgnoreCase("Project")) {
                    result.setProject(temp.getTextContent());
                }

                if (temp.getNodeName().equalsIgnoreCase("Labels")) {

                    List<String> labels = new ArrayList<String>();
                    NodeList labelList = temp.getChildNodes();

                    for (int l = 0; l < labelList.getLength(); l++) {
                        Node labelTemp = labelList.item(l);
                        labels.add(labelTemp.getTextContent());
                    }

                    result.setLabels(labels);
                }

            }

            for (int a = 0; a < result.getLabels().size(); a++) {
                for (int b = 0; b < result.getLabels().size(); b++) {
                    if (result.getLabels().get(b).trim().equalsIgnoreCase("")) {
                        result.getLabels().remove(result.getLabels().get(b));
                    }
                }
            }


        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}