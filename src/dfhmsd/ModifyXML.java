package dfhmsd;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModifyXML extends DFHMSD_UI {

	/**
	 * 
	 */
	Document xdoc;

	public ModifyXML() {
		super(otf.OTF.otf, true);
		// TODO Auto-generated constructor stub
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

	public void modify() {
		NodeList dfhmsdList = xdoc.getElementsByTagName("DFHMSD");
		Node node = dfhmsdList.item(0);
		NodeList dfhmsd = node.getChildNodes();
		for (int i = 1; i < dfhmsd.getLength(); i += 2) {
			Node e = dfhmsd.item(i);
			if (e.getNodeName().equals("TYPE"))
				e.setTextContent(TYPE.getSelectedItem().toString());
			else if (e.getNodeName().equals("LANG"))
				e.setTextContent(LANG.getSelectedItem().toString());
			else if (e.getNodeName().equals("MODE"))
				e.setTextContent(MODE.getSelectedItem().toString());
			else if (e.getNodeName().equals("TERM"))
				e.setTextContent(TERM.getSelectedItem().toString());
			else if (e.getNodeName().equals("CTRL"))
				e.setTextContent(CTRL.getSelectedItem().toString());
			else if (e.getNodeName().equals("STORAGE"))
				e.setTextContent(STORAGE.getSelectedItem().toString());
			else if (e.getNodeName().equals("TIOAPFX"))
				e.setTextContent(TIOAPFX.getSelectedItem().toString());
		}
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(otf.OTF.xmlFile);
			transformer.transform(source, result);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Exception :" + ex.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
