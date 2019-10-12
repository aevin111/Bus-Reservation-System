package bulaongexer6;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUpdater
{
    public void update(String busCode, String elem, String value) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        try
        {
            //Prepare XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("bus/" + busCode + ".xml");
            
            //Find elements
            Node element = document.getElementsByTagName("code").item(0);
            NodeList list = element.getChildNodes();
            
            OUTER:
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                
                switch (elem)
                {
                    case "r":
                        if ("route".equals(node.getNodeName()))
                        {
                            node.setTextContent(value);
                        }   
                        
                        break;
                        
                    case "s":
                        if ("status".equals(node.getNodeName()))
                        {
                            node.setTextContent(value);
                        }
                        
                        break;
                        
                    case "d":
                        if ("driver".equals(node.getNodeName()))
                        {
                            node.setTextContent(value);
                        }
                        
                        break;
                        
                    default:
                        break OUTER;
                }
            }
            
            //Write to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("bus/" + busCode + ".xml"));
            transformer.transform(source, result);
        }
        
        catch (IOException | SAXException e)
        {
            System.out.printf(e.toString());
        }
    }
}
