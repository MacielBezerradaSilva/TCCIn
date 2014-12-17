/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import configuration.CSS;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import model.Issue;
import model.Product;
import model.Session;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author heitor
 */
public class SessionDaoXML implements ISessionDAO {

//    @Override
    public void saveSession1(Session session, String filename) throws FileNotFoundException {
        PrintWriter out = null;
        try {
            String str = session.toString();
            out = new PrintWriter(filename);
            //System.out.println(str);
            out.print(str);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
    
    @Override
    public void saveSession(Session session, String filename) {
        
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            
            Element sessionNode = document.createElement("Session");
            
            Attr idAttribute = document.createAttribute("id");
            idAttribute.setValue("001");

            sessionNode.setAttributeNode(idAttribute);
            
            Element currentTime = document.createElement("currentTime");
            currentTime.setTextContent(String.valueOf(session.getCurrentTime()));
            sessionNode.appendChild(currentTime);
            
            Element duration = document.createElement("sessionDuration");
            duration.setTextContent(String.valueOf(session.getDuration()));
            sessionNode.appendChild(duration);
            
            Element comments = document.createElement("comments");
            String comment = "";
            if (!String.valueOf(session.getComments()).isEmpty()) {
                comment = String.valueOf(comment);
            }
            comments.setTextContent(comment);
            sessionNode.appendChild(comments);
            
            Element charter = document.createElement("Charter");
            
            Element etId = document.createElement("etID");
            etId.setTextContent(session.getCharter().getEtId());
            charter.appendChild(etId);
            
            Element charterName = document.createElement("name");
            charterName.setTextContent(session.getCharter().getName());
            charter.appendChild(charterName);
            
            Element charterObjective = document.createElement("objective");
            charterObjective.setTextContent(session.getCharter().getObjective());
            charter.appendChild(charterObjective);
            
            Element charterRequirements = document.createElement("requirements");
            charterRequirements.setTextContent(session.getCharter().getRequirements());
            charter.appendChild(charterRequirements);
            
            Element charterSetup = document.createElement("setup");
            charterSetup.setTextContent(session.getCharter().getSetup());
            charter.appendChild(charterSetup);
            
            Element charterNotes = document.createElement("notes");
            charterNotes.setTextContent(session.getCharter().getNotes());
            charter.appendChild(charterNotes);
            
            Element charterKeyAreas = document.createElement("keyAreas");
            charterKeyAreas.setTextContent(session.getCharter().getKeyAreas());
            charter.appendChild(charterKeyAreas);
            
            Element charterIssues = document.createElement("issuesToBeAware");
            charterIssues.setTextContent(session.getCharter().getIssuesToBeAware());
            charter.appendChild(charterIssues);
            
            Element charterImportantNotes = document.createElement("importantNotes");
            charterImportantNotes.setTextContent(session.getCharter().getImportantNotes());
            charter.appendChild(charterImportantNotes);
            
            Element charterMaxTime = document.createElement("maxTime");
            charterMaxTime.setTextContent(String.valueOf(session.getCharter().getMaxTime()));
            charter.appendChild(charterMaxTime);
            
            Element charterMinTime = document.createElement("minTime");
            charterMinTime.setTextContent(String.valueOf(session.getCharter().getMinTime()));
            charter.appendChild(charterMinTime);
                    
            sessionNode.appendChild(charter);
            
            //Product
            Element product = document.createElement("Product");
            
            Element id = document.createElement("Id");
            id.setTextContent(session.getProduct().getId());
            product.appendChild(id);

            Element name = document.createElement("Name");
            name.setTextContent(session.getProduct().getName());
            product.appendChild(name);

            Element project = document.createElement("Project");
            project.setTextContent(session.getProduct().getProject());
            product.appendChild(project);

            Element labels = document.createElement("Labels");

            for (String label : session.getProduct().getLabels()) {
                Element l = document.createElement("Label");
                l.setTextContent(label);
                labels.appendChild(l);
            }

            product.appendChild(labels);
            
            sessionNode.appendChild(product);
            
            Element issues = document.createElement("Issues");
            
            for (Issue i : session.getIssues()) {
                Element e = document.createElement("Issue");
                
                Element issueName = document.createElement("issueName");
                issueName.setTextContent(i.getName());
                e.appendChild(issueName);
                
                Element issueType = document.createElement("issueType");
                issueType.setTextContent(i.getType());
                e.appendChild(issueType);
                
                Element issueDescription = document.createElement("issueDescription");
                issueDescription.setTextContent(i.getDescription());
                e.appendChild(issueDescription);
                
                issues.appendChild(e);
            }
            sessionNode.appendChild(issues);
            
            document.appendChild(sessionNode);
                        
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Session loadSession(String filename) throws IOException{

    	Session loadedSession = new Session(new Charter());
        Charter loadedSessionCharter = new Charter();
        Product loadedSessionProduct = new Product();
        List<Issue> loadedSessionIssues = new ArrayList<Issue>();
        
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filename);

            document.getDocumentElement().normalize();

            Node session = document.getElementsByTagName("Session").item(0);
            
            loadedSession.setId(session.getAttributes().item(0).getNodeValue());
            System.out.println("sessionID: " + loadedSession.getId());
            
            NodeList sessionNodes = session.getChildNodes();
            
            for (int i = 0; i < sessionNodes.getLength(); i++) {
                
                Node temp = sessionNodes.item(i);
                
                if (temp.getNodeName().equalsIgnoreCase("currentTime")) {
                    loadedSession.setCurrentTime(Integer.valueOf(temp.getTextContent()));
                }
                
                if (temp.getNodeName().equalsIgnoreCase("sessionDuration")) {
                    loadedSession.setDuration(temp.getTextContent());
                }
                
                if (temp.getNodeName().equalsIgnoreCase("comments")) {
                    loadedSession.setComments(temp.getTextContent());
                }
                
                // =================== START CHARTER NODES ===============
                if (temp.getNodeName().equalsIgnoreCase("Charter")) {
                    
                    NodeList charterNodes = temp.getChildNodes();
                    
                    for (int j = 0; j < charterNodes.getLength(); j++) {
                        Node charterNode = charterNodes.item(j);
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("etID")) {
                            loadedSessionCharter.setEtId(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("name")) {
                            loadedSessionCharter.setName(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("objective")) {
                            loadedSessionCharter.setObjective(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("requirements")) {
                            loadedSessionCharter.setRequirements(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("setup")) {
                            loadedSessionCharter.setSetup(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("notes")) {
                            loadedSessionCharter.setNotes(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("keyAreas")) {
                            loadedSessionCharter.setKeyAreas(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("issuesToBeAware")) {
                            loadedSessionCharter.setIssuesToBeAware(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("importantNotes")) {
                            loadedSessionCharter.setImportantNotes(charterNode.getTextContent());
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("maxTime")) {
                            loadedSessionCharter.setMaxTime(Integer.valueOf(charterNode.getTextContent()));
                        }
                        
                        if (charterNode.getNodeName().equalsIgnoreCase("minTime")) {
                            loadedSessionCharter.setMinTime(Integer.valueOf(charterNode.getTextContent()));
                        }
                        
                    }
                    
                }
                loadedSession.setCharter(loadedSessionCharter);
                System.out.println(loadedSessionCharter.toString());
                // =================== END CHARTER NODES ===============
                
                // =================== START PRODUCT NODES ===============
                if (temp.getNodeName().equalsIgnoreCase("Product")) {
                    
                    NodeList productNodes = temp.getChildNodes();
                    
                    for (int j = 0; j < productNodes.getLength(); j++) {
                        
                        Node productNode = productNodes.item(j);
                        
                        if (productNode.getNodeName().equalsIgnoreCase("Id")) {
                            loadedSessionProduct.setId(productNode.getTextContent());
                        }
                        
                        if (productNode.getNodeName().equalsIgnoreCase("Name")) {
                            loadedSessionProduct.setName(productNode.getTextContent());
                        }
                        
                        if (productNode.getNodeName().equalsIgnoreCase("Project")) {
                            loadedSessionProduct.setProject(productNode.getTextContent());
                        }
                        
                        if (productNode.getNodeName().equalsIgnoreCase("Labels")) {

                            NodeList labelNodes = productNode.getChildNodes();
                            List<String> labels = new ArrayList<String>();
                            
                            for (int k = 0; k < labelNodes.getLength(); k++) {
                                Node ln = labelNodes.item(k);
                                
                                if (ln.getNodeName().equalsIgnoreCase("Label")) {
                                    labels.add(ln.getTextContent());
                                }
                                
                            }
                            
                            loadedSessionProduct.setLabels(labels);
                            
                        }
                        
                    }
                    
                }
                loadedSession.setProduct(loadedSessionProduct);
                
                // =================== END PRODUCT NODES ===============
                
                // =================== START ISSUE NODES ===============
                if (temp.getNodeName().equalsIgnoreCase("Issues")) {
                    
                    NodeList issueNodes = temp.getChildNodes();
                    
                    for (int j = 0; j < issueNodes.getLength(); j++) {
                        
                        Node issueNode = issueNodes.item(j);
                        
                        if (issueNode.getNodeName().equalsIgnoreCase("Issue")) {
                            
                            NodeList eachIssue = issueNode.getChildNodes();
                            Issue tempIssue = new Issue();
                            System.out.println("Creating new temp issue");
                            
                            for (int k = 0; k < eachIssue.getLength(); k++) {
                                
                                Node eachI = eachIssue.item(k);
                                
                                if (eachI.getNodeName().equalsIgnoreCase("issueName")) {
                                    tempIssue.setName(eachI.getTextContent());
                                }

                                if (eachI.getNodeName().equalsIgnoreCase("issueType")) {
                                    tempIssue.setType(eachI.getTextContent());
                                }

                                if (eachI.getNodeName().equalsIgnoreCase("issueDescription")) {
                                    tempIssue.setDescription(eachI.getTextContent());
                                }
                            }
                            System.out.println("Adding issue");

                            loadedSession.addIssue(tempIssue);
                        }
                        
                    }
                    
                }                
                // =================== END ISSUE NODES ===============
                
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loaded session: ");
        System.out.println(loadedSession.getCurrentTime());
        System.out.println(loadedSession.getDuration());
        return loadedSession;
    }

    public void createReport(Session session, String filename) {
        try {
            
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            
            Node html = document.createElement("html");
            Node style = document.createElement("style");
            html.appendChild(style);
            Node body = document.createElement("body");
            html.appendChild(body);
            Element wrapper = document.createElement("div");
            Attr wrapperId = document.createAttribute("id");
            wrapperId.setValue("wrapper");
            wrapper.setAttributeNode(wrapperId);
            body.appendChild(wrapper);
            Node titleH1 = document.createElement("h1");
            wrapper.appendChild(titleH1);
            Node divInfo = document.createElement("div");
            wrapper.appendChild(divInfo);
            Node productP = document.createElement("p");
            divInfo.appendChild(productP);
            Node testerP = document.createElement("p");
            divInfo.appendChild(testerP);
            Node durationP = document.createElement("p");
            divInfo.appendChild(durationP);
            //Node dateP = document.createElement("p");
            //divInfo.appendChild(dateP);
            wrapper.appendChild(document.createElement("hr"));
            Node divContent = document.createElement("div");
            wrapper.appendChild(divContent);
            Node failsList = document.createElement("ul");
            divContent.appendChild(failsList);
            
            List<Issue> list = session.getIssues();
            for(Issue i : list){
                Node li = document.createElement("li");
                Node p1 = document.createElement("p");
                p1.setTextContent("Issue Name: " + i.getName());
                li.appendChild(p1);
                
                Node p2 = document.createElement("p");
                p2.setTextContent("Issue Description: " + i.getDescription());
                li.appendChild(p2);
                
                Node p3 = document.createElement("p");
                p3.setTextContent("Issue Type: " + i.getType());
                li.appendChild(p3);

                failsList.appendChild(li);
            }
            divContent.appendChild(document.createElement("hr"));
            
            Node divComments = document.createElement("div");
            divContent.appendChild(divComments);
            
            document.appendChild(html);
            
            style.setTextContent(CSS.CSS);
            
            DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.US);
            titleH1.setTextContent(session.getProduct().getName()+ " - " + df.format(new Date()));
            productP.setTextContent("Product: "+session.getProduct().getName());
//            testerP.setTextContent("Tester: "+session.getTesterField()+ " - " + session.getIdField());
//            durationP.setTextContent("Duration: "+session.getDurationField());
//            divComments.setTextContent(session.getCommentsField());
            
//            Node node = document.getElementsByTagName("html").item(0);
//
//            Element charterNode = document.createElement("charter");
//
//            Element etID = document.createElement("etId");
//            etID.setTextContent("teste");
//            charterNode.appendChild(etID);
//
//            Element name = document.createElement("name");
//            name.setTextContent(session.getCommentsField());
//            charterNode.appendChild(name);
//
//            node.appendChild(charterNode);
//            System.out.println(html.getTextContent());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(CharterDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(SessionDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(SessionDaoXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
