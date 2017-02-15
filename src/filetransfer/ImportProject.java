package filetransfer;
import java.io.File;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.javatuples.Triplet;

import converter.Bms2Xml;

public class ImportProject extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FTPClient ftp;
	String userID;
	String ret[],ret1[],ret2[];
	private File downloadFile;
	private JComboBox<String> mapNames;
	private NodeList dfhmdi;
	
	public ImportProject(FTPClient ftp, String userID)
	{
		super();
		this.ftp = ftp;
		this.userID = userID;
		ret=new String[2];
		ret1=new String[2];
		ret2=new String[6];
		mapNames = new JComboBox<String>();
		Arrays.fill(ret2,"");
		Arrays.fill(ret, "");
		Arrays.fill(ret1, "");
	}

	public  Triplet<NodeList,JComboBox<String>,String[]> showUI(String fileName,String dataset) 
	{
		String remoteFile = "'" + userID + "." + dataset + "(" + fileName+ ")'";
		downloadFile = new File(System.getProperty("user.dir")+ "\\Workspace\\" +otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\"+ fileName + ".txt");
		FTPUpDown ob = new FTPUpDown(ftp);
		ret=ob.download(remoteFile, downloadFile);
		if(ret[0].equals("true")==true)
		{
			try 
			{
				String file_path = downloadFile.getAbsolutePath();
				String file_name = downloadFile.getName();
		        file_name = file_name.substring(0, file_name.length() - 4) + ".xml";
				Bms2Xml ob1 = new Bms2Xml(file_path, file_name);
				ret1=ob1.convert2XML();
				if(ret1[0].equals("true")==true)
				{
					otf.OTF.xmlFile = new File(System.getProperty("user.dir")+ "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\" + file_name);
					Document xdoc = null;
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					try
					{
						DocumentBuilder fxml = dbf.newDocumentBuilder();
						xdoc = fxml.parse(otf.OTF.xmlFile);
						xdoc.normalize();
						ret2[4]="true";
						ret2[5]="Project Load Successful";
					} 
					catch (Exception e)
					{
						ret2[4]="false";
						ret2[5]= e.toString();
						e.printStackTrace();		
					}
					dfhmdi = xdoc.getElementsByTagName("DFHMDI");
					int index;
					for (index = 0; index < dfhmdi.getLength(); index++) 
					{
						Element ele = (Element) dfhmdi.item(index);
						mapNames.addItem(ele.getAttribute("Name"));
					}
					dispose();
				}
			} 
			catch (Exception e)
			{
				otf.OTF.log.error(otf.OTF.cal.getTime() + " " + e.toString());
				ret2[4]="false";
				ret2[5]="Error : " + e.toString();
				e.printStackTrace();
			}
		}
		ret2[0]=ret[0];
		ret2[1]=ret[1];
		ret2[2]=ret1[0];
		ret2[3]=ret1[1];
		Triplet<NodeList,JComboBox<String>,String[]> t=Triplet.with(dfhmdi,mapNames,ret2);
		return t;
	}
}
