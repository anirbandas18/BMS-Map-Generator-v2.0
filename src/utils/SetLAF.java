package utils;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
/*
 * Anirban Das
 */
public class SetLAF 
{
	public SetLAF()
	{
		try 
		{
			//UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel()");
			
		} 
		catch (Exception ex) 
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				UIManager.setInstalledLookAndFeels(UIManager.getInstalledLookAndFeels());
			}
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
	}
}
