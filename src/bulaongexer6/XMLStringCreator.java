package bulaongexer6;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLStringCreator
{
    public Node getElements(Document document, Element element, String name, String value)
    {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }
    
    public Node getData(Document document, String busCode, String route, String status, String dateTime, String driver)
    {
        Element data = document.createElement("code");
        data.setAttribute("bus_code", busCode);
        data.appendChild(getElements(document, data, "route", route));
        data.appendChild(getElements(document, data, "status", status));
        data.appendChild(getElements(document, data, "schedule", dateTime));
        data.appendChild(getElements(document, data, "driver", driver));
        return data;
    }
}
