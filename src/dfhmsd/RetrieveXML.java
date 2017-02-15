package dfhmsd;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RetrieveXML extends DFHMSD_UI {

	/**
	 * This class retrieves various parameters from the XML file Sounak
	 */

	private Document xdoc;
	
	public RetrieveXML(JFrame parent) {
		// TODO Auto-generated constructor stub
		super(parent, true);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			xdoc = fxml.parse(otf.OTF.xmlFile);
			xdoc.normalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"XML file load error" + e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void retreive() {
		showUI(getMapsetName());
		NodeList dfhmsd = xdoc.getElementsByTagName("DFHMSD");
		Node node = dfhmsd.item(0);
		Element element = (Element) node;
		if (getValue("TYPE", element) != "")
			TYPE.setSelectedItem(getValue("TYPE", element));
		if (getValue("LANG", element) != "")
			LANG.setSelectedItem(getValue("LANG", element));
		if (getValue("MODE", element) != "")
			MODE.setSelectedItem(getValue("MODE", element));
		if (getValue("TERM", element) != "")
			TERM.setSelectedItem(getValue("TERM", element));
		if (getValue("CTRL", element) != "")
			CTRL.setSelectedItem(getValue("CTRL", element));
		if (getValue("STORAGE", element) != "")
			STORAGE.setSelectedItem(getValue("STORAGE", element));
		if (getValue("TIOAPFX", element) != "")
			TIOAPFX.setSelectedItem(getValue("TIOAPFX", element));
	}

	private String getMapsetName() {
		NodeList dfhmsd = xdoc.getElementsByTagName("DFHMSD");
		Node node = dfhmsd.item(0);
		Element e = (Element) node;
		return e.getAttribute("Name");
	}

	private String getValue(String tag, Element element) {
		try {
			NodeList nodes = element.getElementsByTagName(tag).item(0)
					.getChildNodes();
			Node node = nodes.item(0);
			return node.getNodeValue();
		} catch (NullPointerException e) {
			return "";
		}
	}
}
