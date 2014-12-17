package dao;

import gui.EntryPoint;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author heitor
 */
public class CharterDaoXML implements ICharterDAO {

    public synchronized void saveCharter1(Charter charter) throws IOException {

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Charters.xml", true)));
        out.println(charter.toString());
        out.close();

    }

    @Override
    public synchronized void saveCharter(Charter charter, String filename) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            Node node = document.getElementsByTagName("Charters").item(0);

            int charterCount = document.getElementsByTagName("charter").getLength() + 1;
            charter.setEtId(String.valueOf(charterCount));

            Element charterNode = document.createElement("charter");

            Element etID = document.createElement("etId");
            etID.setTextContent(charter.getEtId());
            charterNode.appendChild(etID);

            Element name = document.createElement("name");
            name.setTextContent(charter.getName());
            charterNode.appendChild(name);

            Element objective = document.createElement("objective");
            objective.setTextContent(charter.getObjective());
            charterNode.appendChild(objective);

            Element requirements = document.createElement("requirements");
            requirements.setTextContent(charter.getRequirements());
            charterNode.appendChild(requirements);

            Element setup = document.createElement("setup");
            setup.setTextContent(charter.getSetup());
            charterNode.appendChild(setup);

            Element notes = document.createElement("notes");
            notes.setTextContent(charter.getNotes());
            charterNode.appendChild(notes);

            Element keyAreas = document.createElement("keyAreas");
            keyAreas.setTextContent(charter.getKeyAreas());
            charterNode.appendChild(keyAreas);

            Element issuesToBeAware = document.createElement("issuesToBeAware");
            issuesToBeAware.setTextContent(charter.getIssuesToBeAware());
            charterNode.appendChild(issuesToBeAware);

            Element importantNotes = document.createElement("importantNotes");
            importantNotes.setTextContent(charter.getImportantNotes());
            charterNode.appendChild(importantNotes);

            Element maxTime = document.createElement("maxTime");
            maxTime.setTextContent(String.valueOf(charter.getMaxTime()));
            charterNode.appendChild(maxTime);

            Element minTime = document.createElement("minTime");
            minTime.setTextContent(String.valueOf(charter.getMinTime()));
            charterNode.appendChild(minTime);

            node.appendChild(charterNode);

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
    public Charter getCharter(String id, String filename) {
        Charter result = null;

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("charter");

            for (int i = 0; i < nodes.getLength(); i++) {
                NodeList list = nodes.item(i).getChildNodes();
                Node e = list.item(1);

                if (e.getTextContent().equalsIgnoreCase(id)) {

                    result = new Charter();
                    result.setEtId(e.getTextContent());

                    for (int j = 0; j < list.getLength(); j++) {

                        Node temp = list.item(j);

                        if (temp.getNodeName().equalsIgnoreCase("name")) {
                            result.setName(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("objective")) {
                            result.setObjective(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("requirements")) {
                            result.setRequirements(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("setup")) {
                            result.setSetup(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("notes")) {
                            result.setNotes(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("keyAreas")) {
                            result.setKeyAreas(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("issuesToBeAware")) {
                            result.setIssuesToBeAware(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("importantNotes")) {
                            result.setImportantNotes(temp.getTextContent());
                        }

                        if (temp.getNodeName().equalsIgnoreCase("maxTime")) {
                            result.setMaxTime(Integer.valueOf(temp.getTextContent()));
                        }

                        if (temp.getNodeName().equalsIgnoreCase("minTime")) {
                            result.setMinTime(Integer.valueOf(temp.getTextContent()));
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
    public String getCharterElementValueByName(String id, String element, String filename) {

        String result = null;
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("charter");

            for (int i = 0; i < nodes.getLength(); i++) {
                NodeList list = nodes.item(i).getChildNodes();
                Node e = list.item(1);

                if (e.getTextContent().equalsIgnoreCase(id)) {

                    for (int j = 0; j < list.getLength(); j++) {

                        Node n = list.item(j);

                        if (n.getNodeName().equalsIgnoreCase(element)) {
                            result = n.getTextContent();
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
    public List<Charter> getCharters(String filename) {

        List<Charter> charterList = new ArrayList<Charter>();

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("charter");

            for (int i = 0; i < nodes.getLength(); i++) {

                //Charter element
                NodeList list = nodes.item(i).getChildNodes();
                Charter result = new Charter();

                for (int j = 0; j < list.getLength(); j++) {

                    Node temp = list.item(j);

                    if (temp.getNodeName().equalsIgnoreCase("etId")) {
                        result.setEtId(temp.getTextContent());
                    }
                    
                    if (temp.getNodeName().equalsIgnoreCase("name")) {
                        result.setName(temp.getTextContent());
                    }
                    
                    if (temp.getNodeName().equalsIgnoreCase("objective")) {
                        result.setObjective(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("requirements")) {
                        result.setRequirements(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("setup")) {
                        result.setSetup(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("notes")) {
                        result.setNotes(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("keyAreas")) {
                        result.setKeyAreas(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("issuesToBeAware")) {
                        result.setIssuesToBeAware(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("importantNotes")) {
                        result.setImportantNotes(temp.getTextContent());
                    }

                    if (temp.getNodeName().equalsIgnoreCase("maxTime")) {
                        result.setMaxTime(Integer.valueOf(temp.getTextContent()));
                    }

                    if (temp.getNodeName().equalsIgnoreCase("minTime")) {
                        result.setMinTime(Integer.valueOf(temp.getTextContent()));
                    }

                }
                
                charterList.add(result);

            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return charterList;

    }
}
