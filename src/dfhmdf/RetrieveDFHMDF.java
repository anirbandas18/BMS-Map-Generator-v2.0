package dfhmdf;

import java.util.ArrayList;

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

public class RetrieveDFHMDF 
{

	private Document xdoc;
	String fieldName, pos, length,occurs, attrb, color, picin, picout, initial,
			comment;
	public String ret[];

	public RetrieveDFHMDF() 
	{
		ret = new String[2];
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder fxml = dbf.newDocumentBuilder();
			xdoc = fxml.parse(otf.OTF.xmlFile);
			xdoc.normalize();
		}
		catch (Exception e)
		{
			ret[0]="false";
			ret[1]= e.toString();
			e.printStackTrace();
		}
	}

	public ArrayList<DFHMDF> retrieve()
	{
		color = "green";
		NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
		Node dfhmdiNode;
		//Node dfhmdiNode = dfhmdi.item(otf.OTF.currentMap);//otf.OTF.num
		if(otf.OTF.isNew)
		{
			dfhmdiNode = dfhmdi.item(otf.OTF.currentMap);
		}
		else
		{
			dfhmdiNode = dfhmdi.item(otf.OTF.num);
		}
		NodeList dfhmdiChildren = dfhmdiNode.getChildNodes();
		Node dfhmdf = null;
		int i, j;
		ArrayList<DFHMDF> list = new ArrayList<>();
		for (i = 0; i < dfhmdiChildren.getLength(); i++)
			if (dfhmdiChildren.item(i).getNodeName().equals("DFHMDF")) {
				fieldName = pos = length = occurs = attrb = picin = picout = initial = comment = "";
				color = "green";
				dfhmdf = dfhmdiChildren.item(i);
				fieldName = dfhmdf.getAttributes().getNamedItem("Name")
						.getNodeValue();
				NodeList dfhmdfChildren = dfhmdf.getChildNodes();
				for (j = 0; j < dfhmdfChildren.getLength(); j++) {
					Node dfhmdfChild = dfhmdfChildren.item(j);
					if (dfhmdfChild.getNodeName().equals("POS"))
						pos = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("LENGTH"))
						length = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("OCCURS"))
						occurs = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("ATTRB"))
						attrb = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("COLOR"))
						color = dfhmdfChild.getTextContent().toLowerCase();
					else if (dfhmdfChild.getNodeName().equals("INITIAL"))
						initial = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("COMMENT"))
						comment = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("PICIN"))
						picin = dfhmdfChild.getTextContent();
					else if (dfhmdfChild.getNodeName().equals("PICOUT"))
						picout = dfhmdfChild.getTextContent();
				}
				pos = pos.substring(1, pos.length() - 1);
				int row = Integer.parseInt(pos.substring(0,
						pos.lastIndexOf(',')));
				int column = Integer.parseInt(pos.substring(pos
						.lastIndexOf(',') + 1));
				DFHMDF d = new DFHMDF();
				d.setPos(row, column);
				d.setName(fieldName);
				d.setAttrb(attrb);
				d.setPicin(picin);
				d.setPicout(picout);
				d.setColor(color);
				d.setInitial(initial);
				d.setComment(comment);
				if (!length.equals(""))
					d.setLength(Integer.parseInt(length));
				if (!occurs.equals(""))
					d.setOccur(Integer.parseInt(occurs));
				list.add(d);
			}
		return list;
	}

	public void clear() 
	{
		NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
		//dfhmdiNode = dfhmdi.item(otf.OTF.num);
		Node dfhmdiNode;
		//Node dfhmdiNode = dfhmdi.item(otf.OTF.currentMap);//otf.OTF.num
		if(otf.OTF.isNew)
		{
			dfhmdiNode = dfhmdi.item(otf.OTF.currentMap);
		}
		else
		{
			dfhmdiNode = dfhmdi.item(otf.OTF.num);
		}
		Node dfhmdf = null;
		for (int i = 0; i < dfhmdiNode.getChildNodes().getLength(); i++) 
		{
			if (dfhmdiNode.getChildNodes().item(i).getNodeName().equals("DFHMDF"))
			{
				dfhmdf = dfhmdiNode.getChildNodes().item(i);
				dfhmdiNode.removeChild(dfhmdf);
			}
		}
		try 
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(otf.OTF.xmlFile);
			transformer.transform(source, result);
			ret[0]="true";
			ret[1]="Project Saved Successfully";
		} 
		catch (Exception ex) 
		{
			ret[0]="false";
			ret[1]="Error : "+ex.toString();
			ex.printStackTrace();
		}
	}
}
