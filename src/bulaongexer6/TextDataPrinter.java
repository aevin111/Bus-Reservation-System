package bulaongexer6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextDataPrinter
{
    public void fetch(String fileName) throws FileNotFoundException
    {
        Scanner dataScanner = new Scanner(new File(fileName));
        ArrayList<String> content = new ArrayList<String>();
            
        while (dataScanner.hasNextLine())
        {
            content.add(dataScanner.nextLine());
        }

        for (String data : content)
        {
            System.out.println("- " + data);
        }
        
        System.out.println("");
    }
}
