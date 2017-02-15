package utils;

import java.io.File;


import utils.InputStreamToFile;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

/*
 * Anirban Das
 */

public class DirectoryHandler
{
	private File Workspace,Profiles,UserDir,PDS,Help;
	public File Temp;
	public static final String helpPath=System.getProperty("user.dir")+"\\Help", workspacePath=System.getProperty("user.dir")+"/Workspace",profilesPath=System.getProperty("user.dir")+"/Profiles";
	public DirectoryHandler()
	{
		Workspace=new File(workspacePath);
		Temp=new File(workspacePath+"/Temp");
		Profiles=new File(profilesPath);
		Help=new File(helpPath);
	}
	public boolean makeWorkspace()
	{
		if (!Workspace.exists()) 
		{
			boolean res=Workspace.mkdir();
			if(res==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace could not be created", "Error", JOptionPane.ERROR_MESSAGE);  
			}
			return res;
		}
		else
		{
			return true;
		}
	}
	public boolean makeTemp()
	{
		if (!Temp.exists()) 
		{
			boolean res=Temp.mkdir();
			if(res==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace/Temp could not be created", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return res;
		}
		else
		{
			return true;
		}
	}
	public boolean removeWorkspace()
	{
		if(Workspace.exists())
		{
			return Workspace.delete();
		}
		else
		{
			return true;
		}
	}
	public boolean removeTemp()
	{
		if(Temp.exists())
		{
			//if(Temp.listFiles().length>0)
			//{
				/*for (File file : Temp.listFiles()) 
				{
				    try 
				    {
						FileDeleteStrategy.FORCE.delete(file);
					} 
				    catch (IOException e) 
				    {
						JOptionPane.showMessageDialog(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
				    	e.printStackTrace();
					}*/
				try
				{
					FileUtils.deleteDirectory(Temp);
					return true;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
			    	e.printStackTrace();
			    	return false;
				}
			//}
		}
		else
		{
			return true;
		}
	}
	public boolean removeProfiles()
	{
		if(Profiles.exists())
		{
			return Profiles.delete();
		}
		else
		{
			return true;
		}
	}
	public boolean makeUserDir(String tso_id,String bms_pds)
	{
		UserDir=new File(workspacePath+"/"+tso_id.toUpperCase());
		PDS=new File(workspacePath+"/"+tso_id.toUpperCase()+"/"+bms_pds.toUpperCase());
		if(!UserDir.exists())
		{
			boolean res1=UserDir.mkdir();
			if(res1==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace/"+tso_id.toUpperCase()+" could not be created", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
			{
				return makePDS(PDS);
			}
		}
		else
		{
			return makePDS(PDS);
		}
	}
	private boolean makePDS(File PDS)
	{
		if(!PDS.exists())
		{
			boolean res2=PDS.mkdir();
			if(res2==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : "+PDS.getAbsolutePath()+" could not be created", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	public File makeProfiles()
	{
		if(!Profiles.exists())
		{
			boolean res=Profiles.mkdir();
			if(res==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Profiles could not be created", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			else
			{
				return Profiles;
			}
		}
		else
		{
			return Profiles;
		}
	}
	public boolean copyHelpChm()
	{
		if(!Help.exists())
		{
			boolean res=Help.mkdir();
			if(res==true)
			{
				String paths[]={"/help/helpFile.chm",System.getProperty("user.dir")+"\\Help\\helpFile.chm"};
				return InputStreamToFile.copy(paths);
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
		
	}
	public boolean copyHelpChw()
	{
		String paths[]={"/help/helpFile.chw",System.getProperty("user.dir")+"\\Help\\helpFile.chw"};
		return InputStreamToFile.copy(paths);
	}
}
