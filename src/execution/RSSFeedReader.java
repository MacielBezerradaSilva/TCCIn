package execution;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RSSFeedReader {

   private static RSSFeedReader instance = null;

   private URL rssURL;

   private RSSFeedReader() {}

   public static RSSFeedReader getInstance() {
      if (instance == null)
         instance = new RSSFeedReader();
      return instance;
   }

   public void setURL(URL url) {
      rssURL = url;
   }

   public void writeFeed() {
      try {
         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Document doc = builder.parse(rssURL.openStream());

         NodeList items = doc.getElementsByTagName("item");

         for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element)items.item(i);
            System.out.println(getValue(item, "title"));
            System.out.println(getValue(item, "description"));
            System.out.println(getValue(item, "link"));
            System.out.println();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public String getValue(Element parent, String nodeName) {
      return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
   }

   public static void main(String[] args) {
      try {
         RSSFeedReader reader = RSSFeedReader.getInstance();
         reader.setURL(new URL("http://jconline.ne10.uol.com.br/rss/rss.xml"));
         reader.writeFeed();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}