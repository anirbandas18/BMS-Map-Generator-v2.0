package dfhmsd;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXML extends DFHMSD_UI 
{
	public CreateXML()
	{
		super(otf.OTF.otf, true);
	}

	/**
	 * 
	 */
	public static File xmlFile;

	void generate(String path) 
	{
		try 
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
			Document xdoc = docBuilder.newDocument(); /* Destination XML file */
			Element dfhmsd = xdoc.createElement("DFHMSD");
			xdoc.appendChild(dfhmsd);
			dfhmsd.setAttribute("Name", mapSetName);
			Element element = xdoc.createElement("TYPE");
			dfhmsd.appendChild(element);
			element.setTextContent(TYPE.getSelectedItem().toString());
			element = xdoc.createElement("LANG");
			dfhmsd.appendChild(element);
			element.setTextContent(LANG.getSelectedItem().toString());
			element = xdoc.createElement("MODE");
			dfhmsd.appendChild(element);
			element.setTextContent(MODE.getSelectedItem().toString());
			element = xdoc.createElement("TERM");
			dfhmsd.appendChild(element);
			element.setTextContent(TERM.getSelectedItem().toString());
			element = xdoc.createElement("CTRL");
			dfhmsd.appendChild(element);
			element.setTextContent(CTRL.getSelectedItem().toString());
			element = xdoc.createElement("STORAGE");
			dfhmsd.appendChild(element);
			element.setTextContent(STORAGE.getSelectedItem().toString());
			element = xdoc.createElement("TIOAPFX");
			dfhmsd.appendChild(element);
			element.setTextContent(TIOAPFX.getSelectedItem().toString());
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			xmlFile = new File(path + mapSetName + ".xml");
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
			otf.OTF.xmlFile = xmlFile;
		} 
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
