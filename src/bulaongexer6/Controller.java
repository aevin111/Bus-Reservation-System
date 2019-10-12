package bulaongexer6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Controller
{
    public void create(String busCode, String route, String status, String dateTime, String driver)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        
        try
        {
            //Document builder Setup
            builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElementNS("http://www.bus-company.ph", "BusRouteList");
            document.appendChild(rootElement);
            
            //Create nodes
            XMLStringCreator creator = new XMLStringCreator();
            rootElement.appendChild(creator.getData(document, busCode, route, status, dateTime, driver));
            
            //Output to XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("bus/" + busCode + ".xml"));
            transformer.transform(source, result);
            System.out.println("Successfully added bus!");
            
            //Add to bus database
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            
            try
            {
                File file = new File("list.txt");
                
                if (!file.exists())
                {
                    file.createNewFile();
                }
                
                fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(busCode + "\n");
            }
            
            catch (IOException ea)
            {
                ea.printStackTrace();
            }
            
            finally
            {
                try
                {
                    if (bufferedWriter != null)
                    {
                        bufferedWriter.close();
                    }
                    
                    if (fileWriter != null)
                    {
                        fileWriter.close();
                    }
                }
                
                catch (IOException eb)
                {
                    eb.printStackTrace();
                }
            }
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void read(String busCode)
    {
        try
        {
            //Load XML Data
            File file = new File("bus/" + busCode + ".xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            String route = document.getElementsByTagName("route").item(0).getTextContent();
            String status = document.getElementsByTagName("status").item(0).getTextContent();
            String dateTime = document.getElementsByTagName("schedule").item(0).getTextContent();
            String driver = document.getElementsByTagName("driver").item(0).getTextContent();
            
            //Print data
            System.out.println("Bus code: " + busCode);
            System.out.println("Route: " + route);
            System.out.println("Status: " + status);
            System.out.println("Schedule: " + dateTime);
            System.out.println("Driver: " + driver);
        }
        
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
        
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void update(String busCode) throws ParserConfigurationException, TransformerException
    {
        System.out.printf("What do you want to update([r]oute, [s]tatus, [d]river): ");
        String element = new Scanner(System.in).nextLine();
        XMLUpdater updater = new XMLUpdater();
        
        switch (element)
        {
            case "r":
                System.out.printf("Write new route name: ");
                String routeName = new Scanner(System.in).nextLine();
                updater.update(busCode, element, routeName);
                break;
                
            case "s":
                System.out.printf("Choose new status (parked, traveling, repair): ");
                String status = new Scanner(System.in).nextLine();
                updater.update(busCode, element, status);
                break;
                
            case "d":
                System.out.printf("Write new driver name: ");
                String driver = new Scanner(System.in).nextLine();
                updater.update(busCode, element, driver);
                
            default:
                break;
        }
    }
    
    public void delete(String busCode) throws FileNotFoundException, IOException
    {
        //Load file and delete
        File bus = new File("bus/" + busCode + ".xml");
        bus.delete();
        
        //Open database and delete the bus
        File oldFile = new File("list.txt");
        File newFile = new File("list_temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        String currentLine = null;
        
        while ((currentLine = reader.readLine()) != null)
        {
            String trimmedLine = currentLine.trim();
            
            if (trimmedLine.equals(busCode))
            {
                continue;
            }
            
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        
        writer.close();
        newFile.renameTo(oldFile);
        reader.close();
        System.out.println("Bus record successfully deleted!");
    }
    
    public void append(String firstBusCode, String secondBusCode) throws SAXException, IOException, ParserConfigurationException
    {
        //Load first XML data
        File firstFile = new File("bus/" + firstBusCode + ".xml");
        DocumentBuilderFactory firstDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder firstDocumentBuilder = firstDocumentBuilderFactory.newDocumentBuilder();
        Document firstDocument = firstDocumentBuilder.parse(firstFile);
        String firstRoute = firstDocument.getElementsByTagName("route").item(0).getTextContent();
        String firstStatus = firstDocument.getElementsByTagName("status").item(0).getTextContent();
        String firstDriver = firstDocument.getElementsByTagName("driver").item(0).getTextContent();
        String firstSchedule = firstDocument.getElementsByTagName("schedule").item(0).getTextContent();
        
        //Load second XML data
        File secondFile = new File("bus/" + secondBusCode + ".xml");
        DocumentBuilderFactory secondDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder secondDocumentBuilder = secondDocumentBuilderFactory.newDocumentBuilder();
        Document secondDocument = secondDocumentBuilder.parse(secondFile);
        String secondRoute = secondDocument.getElementsByTagName("route").item(0).getTextContent();
        String secondStatus = secondDocument.getElementsByTagName("status").item(0).getTextContent();
        String secondDriver = secondDocument.getElementsByTagName("driver").item(0).getTextContent();
        String secondSchedule = secondDocument.getElementsByTagName("schedule").item(0).getTextContent();
        
        //Write to text
        try (PrintWriter writer = new PrintWriter(firstBusCode + "_" + secondBusCode + ".txt"))
        {
            writer.println("Bus code: " + firstBusCode);
            writer.println("Route: " + firstRoute);
            writer.println("Status: " + firstStatus);
            writer.println("Driver: " + firstDriver);
            writer.println("");
            writer.println("Bus code: " + secondBusCode);
            writer.println("Route" + secondRoute);
            writer.println("Status: " + secondStatus);
            writer.println("Driver: " + secondDriver);
            writer.println("Schedule: " + secondSchedule);
        }
    }
    
    public void browse() throws FileNotFoundException, IOException, SAXException, ParserConfigurationException
    {
        //Initialize file
        BufferedReader bufferedReader = new BufferedReader(new FileReader("list.txt"));
        ArrayList<String> list = new ArrayList<String>();
        String line = null;
        
        while ((line = bufferedReader.readLine()) != null)
        {
            list.add(line);
        }
        
        //Formating of bus lsit
        System.out.printf("| Bus ID |" + "\t");
        System.out.printf("| Route |" + "\t");
        System.out.printf("| Status |" + "\t");
        System.out.printf("| Driver | " + "\t");
        System.out.printf("| Schedule |");
        System.out.println("");

        //initalize counter
        int i = 0;
        
        //Read file
        for (String bus : list)
        {
            //Initialize file
            File file = new File("bus/" + list.get(i) + ".xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            
            //Get Elements
            String busCode = list.get(i);
            String route = document.getElementsByTagName("route").item(0).getTextContent();
            String status = document.getElementsByTagName("status").item(0).getTextContent();
            String driver = document.getElementsByTagName("driver").item(0).getTextContent();
            String schedule = document.getElementsByTagName("schedule").item(0).getTextContent();
            
            //print it nicely
            System.out.printf(busCode + "\t" + "\t");
            System.out.printf(route + "\t");
            System.out.printf(status + "\t");
            System.out.printf("\t" + driver + "\t" + "\t");
            System.out.printf(schedule);
            System.out.println("");
            
            //Go to next file
            i++;
        }
    }
    
    public void saveToFile(String busCode, String extension) throws FileNotFoundException, IOException
    {
        //Load strings
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        
        //Read file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("bus/" + busCode + ".xml")))
        {
            while ((line = bufferedReader.readLine()) != null) 
            {
                stringBuilder.append(line);
            }
        }
        
        //Save to new extension
        String content = stringBuilder.toString();
        
        try (PrintWriter writer = new PrintWriter("bus/" + busCode + "." + extension))
        {
            writer.println(content);
        }
        
        System.out.println("Successfully saved as ." + extension);
    }
}
