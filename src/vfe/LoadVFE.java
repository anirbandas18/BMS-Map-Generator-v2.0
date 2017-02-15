package vfe;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vfe.VFE_Dialog;

/*
 * Anirban
 */
public class LoadVFE 
{
	private NodeList dfhmdi;
	private JComboBox<String> mapNames;
	private int index;
	private final String d[],ret2[];
	public LoadVFE(JComboBox<String> mapNames,NodeList dfhmdi)
	{
		this.mapNames=mapNames;
		this.dfhmdi=dfhmdi;
		d=new String[2];
		ret2=new String[2];
		ret2[0]="";
		ret2[1]="";
		new utils.SetLAF();
	}
	public void selectMap()
	{
		JOptionPane.showMessageDialog(null, mapNames, "Select Map",JOptionPane.QUESTION_MESSAGE);
		for (index = 0; index < dfhmdi.getLength(); index++) 
		{
			Element ele = (Element) dfhmdi.item(index);
			if (ele.getAttribute("Name").equals(mapNames.getSelectedItem().toString()))
			{
				otf.OTF.mapName=mapNames.getSelectedItem().toString();
				break;
			}
		}
	}
	public void setUpOTF()
	{
		otf.OTF.lastMap = dfhmdi.getLength();
		otf.OTF.num = index;
		Node node = dfhmdi.item(otf.OTF.num);
		String str =getValue("SIZE", node);
		str = str.substring(1, str.length() - 1);
		d[0] = str.substring(0, str.lastIndexOf(','));
		d[1] = str.substring(str.lastIndexOf(',') + 1);
		otf.OTF.otf.setEnabled(false);
	}
	public String[] showVFE()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try 
				{
					otf.OTF.VFE_Diag  = new VFE_Dialog(otf.OTF.otf, Integer.parseInt(d[0]), Integer.parseInt(d[1]));
					otf.OTF.VFE_Diag.buildUI();
				}
				catch (NumberFormatException e)
				{
					ret2[4]="false";
					ret2[5]="Error :"+e.toString();
				}
			}
		});
		return ret2;
	}
	public String getValue(String tag, Node node) 
	{
		try 
		{
			NodeList childNodes = node.getChildNodes();
			int i;
			for (i = 0; i < childNodes.getLength(); i++)
			{
				if (childNodes.item(i).getNodeName().equals(tag))
				{
					break;
				}
			}
			String nodeValue = "";
			nodeValue = childNodes.item(i).getTextContent();
			return nodeValue;
		}
		catch (NullPointerException a)
		{
			return "";
		}
	}
}
