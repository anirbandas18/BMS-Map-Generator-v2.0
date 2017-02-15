package dfhmdi;

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

public class DFHMDI
{
	/**
	 * 
	 */
	private String size, date, time, color, mapName;
	private int line, column;
	private boolean mapatts;
	private DocumentBuilderFactory builderFactory;
	private DocumentBuilder docBuilder;
	Document xdoc;

	public DFHMDI() 
	{
		builderFactory = DocumentBuilderFactory.newInstance();
		try 
		{
			docBuilder = builderFactory.newDocumentBuilder();
			xdoc = docBuilder.parse(otf.OTF.xmlFile);
		} 
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	public void setMapatts(boolean mapatts) {
		this.mapatts = mapatts;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int row, int col) {
		this.size = "(" + row + "," + col + ")";
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int dd, int mm, int yyyy) {
		this.date = dd + "-" + mm + "-" + yyyy;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int hr, int min, int sec) {
		this.time = hr + ":" + min + ":" + sec;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DFHMDI [size=" + size + ", date=" + date + ", time=" + time
				+ ", color=" + color + "]";
	}

	public void generate() 
	{
		NodeList dfhmsdList = xdoc.getElementsByTagName("DFHMSD");
		Node dfhmsd = dfhmsdList.item(0);//0
		System.out.println("DFHMDI CurrentMap is : "+otf.OTF.currentMap);
		Element dfhmdi = xdoc.createElement("DFHMDI");
		dfhmsd.appendChild(dfhmdi);
		dfhmdi.setAttribute("Name", mapName);
		Element element = xdoc.createElement("SIZE");
		dfhmdi.appendChild(element);
		element.setTextContent(size);
		if (mapatts) 
		{
			element = xdoc.createElement("MAPATTS");
			dfhmdi.appendChild(element);
			element.setTextContent("(COLOR,HILIGHT)");
		}
		element = xdoc.createElement("LINE");
		dfhmdi.appendChild(element);
		element.setTextContent(line + "");
		element = xdoc.createElement("COLUMN");
		dfhmdi.appendChild(element);
		element.setTextContent(column + "");
		try
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(otf.OTF.xmlFile);
			transformer.transform(source, result);
		} 
		catch (Exception ex)
		{
			// TODO Auto-generated catch block
			otf.OTF.log.fatalError(otf.OTF.cal.getTime() + " " + ex.toString());
			JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	public void modify()
	{
		try 
		{
			NodeList dfhmdiList = xdoc.getElementsByTagName("DFHMDI");
			Node node = dfhmdiList.item(otf.OTF.num);
			Element header = (Element) node;
			header.setAttribute("Name", mapName);
			NodeList dfhmdi = node.getChildNodes();
			for (int i = 1; i < dfhmdi.getLength(); i += 2)
			{
				Node e = dfhmdi.item(i);
				if (!mapatts && e.getNodeName().equals("MAPATTS"))
					e.setTextContent("");
				if (e.getNodeName().equals("SIZE"))
					e.setTextContent(size);
				else if (e.getNodeName().equals("LINE"))
					e.setTextContent(line + "");
				else if (e.getNodeName().equals("COLUMN"))
					e.setTextContent(column + "");
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(otf.OTF.xmlFile);
			transformer.transform(source, result);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
