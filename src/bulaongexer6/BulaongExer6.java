package bulaongexer6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class BulaongExer6
{
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException
    {
        while (true)
        {
            InputStream in = null;
            
            //This loads the menu text
            OUTER:
            try
            {
                //Load file
                in = new FileInputStream("menu.txt");
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder fileContent = new StringBuilder();
                String line = null;
                
                //insert text into String variable
                while ((line = buffReader.readLine()) != null)
                {
                    fileContent.append(line).append("\n");
                }
                
               //print text and close menu.txt
               String content = fileContent.toString();
               System.out.println(content);
               in.close();
            }

            catch (IOException e)
            {
                System.out.println(e.toString());
            }
            
            /*
                "This is where the fun begins"
                            - Anakin Skywalker
            */
            System.out.printf("Enter your choice: ");
            int choice = new Scanner(System.in).nextInt();
            Controller controller = new Controller();
            TextDataPrinter printer = null;
            Scanner stringScanner = new Scanner(System.in);
            System.out.println("");
            System.out.println("");

            while (true)
            {
                switch (choice)
                {
                    case 1:
                        System.out.printf("Enter new bus code: ");
                        String busCode = stringScanner.nextLine();
                        System.out.printf("Enter new route: ");
                        String route = stringScanner.nextLine();
                        System.out.printf("Enter schedule: ");
                        String dateTime = stringScanner.nextLine();
                        System.out.printf("Enter driver name: ");
                        String driver = stringScanner.nextLine();
                        controller.create(busCode, route, "Parked", dateTime, driver);
                        break;

                    case 2:
                        System.out.println("Existing bus codes:");
                        printer = new TextDataPrinter();
                        printer.fetch("list.txt");
                        System.out.printf("Enter existing bus code: ");
                        controller.read(stringScanner.nextLine());
                        break;

                    case 3:
                        System.out.println("Existing bus codes:");
                        printer = new TextDataPrinter();
                        printer.fetch("list.txt");
                        System.out.printf("Enter existing bus code: ");
                        controller.update(stringScanner.nextLine());
                        break;

                    case 4:
                        System.out.println("Existing bus codes:");
                        printer = new TextDataPrinter();
                        printer.fetch("list.txt");
                        System.out.printf("Enter existing bus code: ");
                        controller.delete(stringScanner.nextLine());
                        break;

                    case 5:
                        System.out.println("Existing bus codes:");
                        printer = new TextDataPrinter();
                        printer.fetch("list.txt");
                        System.out.printf("Enter first bus code: ");
                        String firstBusCode = stringScanner.nextLine();
                        System.out.printf("Enter second bus code: ");
                        String secondBusCode = stringScanner.nextLine();
                        controller.append(firstBusCode, secondBusCode);
                        break;

                    case 6:
                        controller.browse();
                        break;
                        
                    case 7:
                        System.out.println("Existing bus codes:");
                        printer = new TextDataPrinter();
                        printer.fetch("list.txt");
                        System.out.printf("Enter existing bus code: ");
                        String code = stringScanner.nextLine();
                        System.out.printf("Enter new extension: ");
                        String extension = stringScanner.nextLine();
                        controller.saveToFile(code, extension);
                        break;
                        
                    case 8:
                        System.out.printf("Qutting application...");
                        System.exit(0);

                    default:
                        System.out.printf("Invalid! Quitting....");
                }

                break;
            }
            
            System.out.println("");
            System.out.println("");
            System.out.printf("Repeat [y/n]: ");
            String rep = stringScanner.nextLine();
            
            if (rep.equals("y"))
            {
                continue;
            }
            
            else
            {
                break;
            }
        }
    }
}
