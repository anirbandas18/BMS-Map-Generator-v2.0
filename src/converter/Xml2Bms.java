/**
 * 
 */
package converter;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Sounak
 * 
 */
public class Xml2Bms {

	/**
	 * @param args
	 * @throws Exception
	 */

	File f1;
	File f2;/* Destination BMS file */
	int max_char, spaces;
	public String source, dest,ret[];
	Document xdoc;/* XML file */
	PrintWriter f;

	public Xml2Bms(String source, String dest) {
		// TODO Auto-generated constructor stub
		String path = System.getProperty("user.dir") + "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\";
		this.source = source;
		this.dest = path + dest;
		f1 = new File(this.source);
		f2 = new File(this.dest);
		max_char = 9;
		spaces = max_char + 7;
		ret=new String[2];
		
	}

	public void convert2BMS() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			xdoc = fxml.parse(f1);
			xdoc.normalize();
			f = new PrintWriter(f2);
			NodeList dfhmsdList = xdoc.getElementsByTagName("DFHMSD");
			NodeList dfhmdiList = xdoc.getElementsByTagName("DFHMDI");
			int j;
			Node dfhmsd = dfhmsdList.item(0);
			write2file(dfhmsd);
			for (int i = 0; i < dfhmdiList.getLength(); i++) {
				Node dfhmdi = dfhmdiList.item(i);
				write2file(dfhmdi);
				for (j = 1; j < dfhmdi.getChildNodes().getLength(); j += 2) {
					if (dfhmdi.getChildNodes().item(j).getNodeName()
							.equals("DFHMDF")) {
						Node dfhmdf = dfhmdi.getChildNodes().item(j);
						write2file(dfhmdf);
					}
				}
			}
			endfile();
			f.close();
			ret[0]="true";
			ret[1]="XML to BMS Conversion Successful";
		} catch (Exception e) {
			ret[0]="false";
			ret[1]="XML to BMS Conversion Failed : "+e.toString();
		}
	}

	void endfile() {
		int i;
		for (i = 1; i <= max_char; i++)
			f.print(" ");
		f.println("DFHMSD TYPE=FINAL");
		for (i = 1; i < spaces; i++)
			f.print(" ");
		f.print("END");

	}

	void write2file(Node n) throws Exception {
		String str, line = "";
		NodeList list = n.getChildNodes();
		
		if(n.getNodeName().equalsIgnoreCase("DFHMDF"))
		{
			Node comment = list.item(list.getLength()-2);
			if(comment.getNodeName().equalsIgnoreCase("COMMENT")==true)
			{
				f.println("*"+comment.getTextContent());
			}
		}
		
		str = n.getAttributes().getNamedItem("Name").getNodeValue();
		line += str;
		int i;
		for (i = 1; i <= max_char - str.length(); i++)
			line += " ";
		str = n.getNodeName();
		line += str + " ";
		for (i = 1; i < list.getLength(); i += 2) {
			Node child = list.item(i);
			str = child.getTextContent();
			if (str != "") {
				if (child.getNodeName().equals("INITIAL")) {
					line += child.getNodeName() + "=";
					str = "'" + str + "'";
					line += str;
				} else if (child.getNodeName().equals("COMMENT"))
				{
					
				}
				else
				{
					line += child.getNodeName() + "=";
					line += str;
				}
				if (list.item(i + 2) == null
						|| list.item(i + 2).getNodeName().equals("DFHMDI")
						|| list.item(i + 2).getNodeName().equals("DFHMDF")
						|| list.item(i + 2).getNodeName().equals("DFHMSD")) {
					f.print(line);
					f.println();
					return;
				} else {
					if (!list.item(i + 2).getNodeName().equals("COMMENT")) {
						line += ",";
						f.print(line);
						for (int j = line.length(); j < 71; j++)
							f.print(" ");
						f.print("*");
						f.println();
						line = "";
						for (int j = 1; j < spaces; j++)
							line += " ";
					} else {
						/*f.println(line);
						line = "";*/
					}
				}
			}
		}
	}
}
