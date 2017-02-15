/**
 * 
 */
package validator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author Sounak
 * 
 */
public class BMSValidator {
	/**
	 * 
	 */
	File f;

	public BMSValidator(String s) {
		f = new File(s);
	}

	public boolean isvalid() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xdoc = db.parse(f);
			Node dfhmsd = xdoc.getElementsByTagName("DFHMSD").item(0);
			Node dfhmdi = xdoc.getElementsByTagName("DFHMDI").item(0);
			Node dfhmdf = xdoc.getElementsByTagName("DFHMDF").item(0);
			if (dfhmdi.getParentNode().equals(dfhmsd)
					&& dfhmdf.getParentNode().equals(dfhmdi))
				return true;
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Parsing XML :" + e.toString());
			return false;
		}
	}

	public static void main(String args[]) {
		BMSValidator bv = new BMSValidator("F:\\RM-BMS Map Generator\\"
				+ "Rough\\Project1.xml");
		System.out.println(bv.isvalid());
	}
}
