package otf;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utils.InputDialogUI;
import dfhmsd.DFHMSD_UI;

public class NewProject 
{
	private JFrame f;
	private String mapsetName;
	private InputDialogUI ob;
	public NewProject(JFrame f) 
	{
		this.f=f;
		mapsetName="";
		ob=new InputDialogUI();
	}
	private boolean allBlank(String s)
	{
		boolean f=true;
		for(char x:s.toCharArray())
		{
			if(x!=' ')
			{
				f=false;
				break;
			}
		}
		return f;
	}
	public void showUI()
	{
		this.mapsetName=ob.showInputUI("Enter Mapset Name : ","Input - New Project","MAPSET NAME");
		if(this.mapsetName.equals("")||this.mapsetName.charAt(0)==' '||allBlank(mapsetName))
		{
			JOptionPane.showMessageDialog(null, "Enter a valid Mapset Name", "Error",JOptionPane.ERROR_MESSAGE);
			showUI();
		}
		else
		{
			if(this.mapsetName.equalsIgnoreCase("cancel"))
			{
				//OptionPane.showMessageDialog(null, "Enter a valid Mapset Name", "Warning",JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				if (mapsetName.length() >= 1 && mapsetName.length() <= 7) 
				{
					otf.OTF.mapsetName=this.mapsetName;
					otf.OTF.log.info(otf.OTF.cal.getTime() + "  Project "+ otf.OTF.mapsetName + " Created.");
					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							DFHMSD_UI ob = new DFHMSD_UI(f, true);
							ob.showUI(mapsetName);
						}
					});
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Enter a valid Mapset Name", "Error",JOptionPane.ERROR_MESSAGE);
					showUI();
				}
			}
		}
	}
}
