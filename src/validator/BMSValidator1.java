/**
 * 
 */
package validator;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Sounak,Prameet
 * 
 */
public class BMSValidator1 {
	/**
	 * 
	 */
	File f;

	public BMSValidator1(String s) {
		f = new File(s);
		new utils.SetLAF();
	}

	public boolean isvalid() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xdoc = db.parse(f);
			/*
			 * if (!(dfhmdi.getParentNode().equals(dfhmsd) && dfhmdf
			 * .getParentNode().equals(dfhmdi))) {
			 * JOptionPane.showMessageDialog(null, "Invalid File", "Error " +
			 * "in hierarchy", JOptionPane.ERROR_MESSAGE); return false; }
			 */
			if (xdoc.getElementsByTagName("DFHMSD").getLength() > 1) {
				JOptionPane.showMessageDialog(null, "Invalid File", "Mapsets"
						+ "exceeding one", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			NodeList dfhmsdList = xdoc.getElementsByTagName("DFHMSD");
			NodeList dfhmdiList = xdoc.getElementsByTagName("DFHMDI");
			NodeList dfhmdfList = xdoc.getElementsByTagName("DFHMDF");
			int i, j;
			Node dfhmsd = dfhmsdList.item(0);
			if (checkDFHMSD(dfhmsd)) {
				for (i = 0; i < dfhmdiList.getLength(); i++) {
					Node dfhmdi = dfhmdiList.item(i);
					if (checkDFHMDI(dfhmdi)) {
						for (j = 0; j < dfhmdfList.getLength(); j++) {
							Node dfhmdf = dfhmdfList.item(j);
							if (!checkDFHMDF(dfhmdf))
								return false;
						}
					} else
						return false;
				}
			} else
				return false;

			JOptionPane.showMessageDialog(null, "Valid File", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"Invalid File :" + e.toString(), "Unsupported format",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private boolean checkDFHMSD(Node dfhmsd) {
		NodeList nodeList_dfhmsd = dfhmsd.getChildNodes();
		int i, j;
		String dfhmsd_nodes[] = { "TYPE", "LANG", "MODE", "TERM", "CTRL",
				"STORAGE", "DSATTS", "TIOAPFX" };
		for (i = 1; i < nodeList_dfhmsd.getLength(); i += 2) {
			int count = 0;
			Node childNode = nodeList_dfhmsd.item(i);
			if (childNode.getNodeName().equals("DFHMDI"))
				break;
			for (j = 0; j < dfhmsd_nodes.length; j++)
				if (dfhmsd_nodes[j].equals(childNode.getNodeName()))
					count++;
			if (count != 1) {
				JOptionPane.showMessageDialog(null, "Invalid File",
						"Unsupported format", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private boolean checkDFHMDI(Node dfhmdi) {
		int i, j;
		String dfhmdi_nodes[] = { "SIZE", "MAPATTS", "LINE", "COLUMN" };
		NodeList nodeList_dfhmdi = dfhmdi.getChildNodes();
		for (i = 1; i < nodeList_dfhmdi.getLength(); i += 2) {
			int count = 0;
			Node childNode = nodeList_dfhmdi.item(i);
			if (childNode.getNodeName().equals("DFHMDF"))
				break;
			for (j = 0; j < dfhmdi_nodes.length; j++)
				if (dfhmdi_nodes[j].equals(childNode.getNodeName()))
					count++;
			if (count != 1 && !childNode.getNodeName().equals("DATE")
					&& !childNode.getNodeName().equals("TIME")) {
				JOptionPane.showMessageDialog(null, "Invalid File",
						"Unsupported format", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private boolean checkDFHMDF(Node dfhmdf) {
		int i, j;
		String dfhmdf_nodes[] = { "POS", "LENGTH", "ATTRB", "COLOR", "INITIAL" };
		NodeList nodeList_dfhmdf = dfhmdf.getChildNodes();
		for (i = 1; i < nodeList_dfhmdf.getLength(); i += 2) {
			int count = 0;
			Node childNode = nodeList_dfhmdf.item(i);
			for (j = 0; j < dfhmdf_nodes.length; j++)
				if (dfhmdf_nodes[j].equals(childNode.getNodeName()))
					count++;
			if (count != 1 && !childNode.getNodeName().equals("PICIN")
					&& !childNode.getNodeName().equals("PICOUT")) {
				JOptionPane.showMessageDialog(null, "Invalid File",
						"Unsupported format", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		BMSValidator1 bv = new BMSValidator1("E:\\INQSET1.xml");
		bv.isvalid();
	}
}
