package vfe;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XMLGenerator {

	public static void generateXMLdfhmdf(File f, int i, int j, String name,
			String pos, String length, String attrb, String color,
			String picin, String picout, String initial) {
		/* i is the variable storing the map number */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			Document xdoc = fxml.parse(f);
			xdoc.normalize();
			NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
			Element dfhmdf = xdoc.createElement("DFHMDF");
			dfhmdf.setAttribute("Name", name);
			Node current = dfhmdi.item(i);
			current.appendChild(dfhmdf);
			current = xdoc.getElementsByTagName("DFHMDF").item(j);
			Element dfhmdf_val;
			dfhmdf_val = xdoc.createElement("POS");
			dfhmdf_val.setTextContent(pos);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("LENGTH");
			dfhmdf_val.setTextContent(length);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("ATTRB");
			dfhmdf_val.setTextContent(attrb);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("COLOR");
			dfhmdf_val.setTextContent(color);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("PICIN");
			dfhmdf_val.setTextContent(picin);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("PICOUT");
			dfhmdf_val.setTextContent(picout);
			current.appendChild(dfhmdf_val);
			dfhmdf_val = xdoc.createElement("INITIAL");
			dfhmdf_val.setTextContent(initial);
			current.appendChild(dfhmdf_val);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);
			System.out.println("Done");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
