package dfhmdi;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RetrieveDFHMDI extends DFHMDI_UI1 {

	/**
	 * 
	 */
	private Document xdoc;

	public RetrieveDFHMDI( boolean modal) {
		// TODO Auto-generated constructor stub
		super(modal);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			xdoc = fxml.parse(otf.OTF.xmlFile);
			xdoc.normalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
				e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public RetrieveDFHMDI(JFrame parent, boolean modal) {
		// TODO Auto-generated constructor stub
		super(parent, modal);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			xdoc = fxml.parse(otf.OTF.xmlFile);
			xdoc.normalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public synchronized void retreive(int i) {
		mapNameTextField.setText(getMapName(i));
		NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
		otf.OTF.lastMap = dfhmdi.getLength();
		Node node = dfhmdi.item(i);
		String str = getValue("SIZE", node);
		str = str.substring(1, str.length() - 1);
		rowTextField.setText(str.substring(0, (str.lastIndexOf(','))));
		colTextField.setText(str.substring(str.lastIndexOf(',') + 1));
		if (getValue("MAPATTS", node) != "")
			enable.setSelected(true);
		else
			enable.setSelected(false);
		lineTextField.setText(getValue("LINE", node));
		columnTextField.setText(getValue("COLUMN", node));
		buildUI();
	}

	private String getMapName(int i) {
		NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
		Node node = dfhmdi.item(i);
		Element e = (Element) node;
		return e.getAttribute("Name");
	}

	private String getValue(String tag, Node node) {
		try {
			NodeList childNodes = node.getChildNodes();
			int i;
			for (i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeName().equals(tag))
					break;
			}
			String nodeValue = "";
			nodeValue = childNodes.item(i).getTextContent();
			return nodeValue;
		} catch (NullPointerException a) {
			return "";
		}

	}
}
