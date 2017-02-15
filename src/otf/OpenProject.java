package otf;

import javax.swing.JOptionPane;
import filetransfer.MapLoadUnload;
import utils.InputDialogUI;

public class OpenProject 
{
	private String mapsetName;
	private InputDialogUI ob;
	
	public OpenProject() 
	{
		ob=new InputDialogUI();
	}

	public void showUI()
	{
		this.mapsetName=ob.showInputUI("Enter Mapset Name : ","Input - Open Project","MAPSET NAME");
		if(this.mapsetName.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Enter a valid Mapset Name", "Error",JOptionPane.ERROR_MESSAGE);
			showUI();
		}
		else
		{
			if(this.mapsetName.equals("cancel"))
			{
				//JOptionPane.showMessageDialog(null, "Enter a valid Mapset Name", "Warning",JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				otf.OTF.mapsetName=this.mapsetName;
				MapLoadUnload ob=new MapLoadUnload(false, null);
				ob.startUI();
			}
		}
	}
}
