package filetransfer;

import java.io.File;
import java.util.Arrays;

import javax.swing.JComboBox;

import org.apache.commons.net.ftp.FTPClient;
import org.javatuples.Triplet;
import org.w3c.dom.NodeList;

import utils.NotificationUI;
import utils.SetLAF;
import vfe.LoadVFE;

public class MapLoadUnload
{
	private File f;
	private boolean upload,status[];
	private String messages[],ret[],title;
	private JComboBox<String> mapNames;
	private Triplet<NodeList,JComboBox<String>,String[]> t;
	private NodeList dfhmdi;
	private LoadVFE lvfe;
	private NotificationUI nui;
	private FTPClient ftp;
	
	public MapLoadUnload(boolean upload,File f)
	{
		this.upload=upload;
		this.f=f;
		this.ftp=otf.OTF.ftp;
		messages=new String[3];
		status=new boolean[3];
		ret=new String[6];
		title="";
		Arrays.fill(messages, "");
		new SetLAF();
	}
	public MapLoadUnload(boolean upload,File f,String ret[])
	{
		this.upload=upload;
		this.f=f;
		this.ret=ret;
		this.ftp=otf.OTF.ftp;
		messages=new String[3];
		status=new boolean[3];
		ret=new String[6];
		title="";
		Arrays.fill(messages, "");
		new SetLAF();
	}
	public void startUI()
	{
			if(this.upload)
			{
				title="Notification - Save Project";
				initExport();
			}
			else
			{
				title="Notification - Open Project";
				initImport();
			}
			nui=new NotificationUI(otf.OTF.otf,title,messages,status,500);
			nui.showUI();
			nui.start.doClick();
	}
	private void initExport()
	{
		try
		{	
			boolean success = ftp.changeWorkingDirectory("'"+ otf.OTF.tso_id + "." + otf.OTF.bms_pds + "'");
			if (!success)
			{
				status[0]=false;
				messages[0]="Cannot find PDS CICS.MAPLIB. Error shifting to working directory";
			}
			else
			{
				ExportProject ob = new ExportProject(ftp, f);
				String intRet[];
				intRet=ob.showUI();
				ret[4]=intRet[0];
				ret[5]=intRet[1];
				if(ret[0].equals("true"))
				{
					messages[0]=ret[1];
					status[0]=true;
					if(ret[2].equals("true"))
					{
						messages[1]=ret[3];
						status[1]=true;
						if(ret[4].equals("true"))
						{
							messages[2]=ret[5];
							status[2]=true;
							//title=title+" : Sucess";
						}
						else
						{
							messages[2]=ret[5];
							status[2]=false;
						}
					}
					else
					{
						messages[1]=ret[3];
						status[1]=false;
					}
				}
				else
				{
					messages[0]=ret[1];
					status[0]=false;
				}
			}
		}
		catch(Exception e)
		{
			status[0]=false;
			messages[0]=e.toString();
			e.printStackTrace();
		}
	}
	private void initImport()
	{
		ImportProject ob = new ImportProject(ftp, otf.OTF.tso_id);
		String ret[],intRet[];
		t=ob.showUI(otf.OTF.mapsetName,otf.OTF.bms_pds);
		this.dfhmdi=t.getValue0();
		ret=t.getValue2();
		this.mapNames=t.getValue1();
		lvfe=new LoadVFE(mapNames,dfhmdi);
		if(ret[0].equals("true"))
		{
			messages[0]=ret[1];
			status[0]=true;
			if(ret[2].equals("true"))
			{
				messages[1]=ret[3];
				status[1]=true;
				if(ret[4].equals("true"))
				{
					messages[2]=ret[5];
					status[2]=true;
					lvfe.selectMap();
					lvfe.setUpOTF();
					intRet=lvfe.showVFE();
					if(intRet[0].equals("false"))
					{
						messages[2]=intRet[1];
						status[2]=false;
						//title=title+" : Failure ";
					}
				}
				else
				{
					messages[2]=ret[5];
					status[2]=false;
					//title=title+" : Failure ";
				}
			}
			else
			{
				messages[1]=ret[3];
				status[1]=false;
			}
		}
		else
		{
			messages[0]=ret[1];
			status[0]=false;
		}
	}
}
