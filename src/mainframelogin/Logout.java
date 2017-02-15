package mainframelogin;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;

public class Logout 
{
	private FTPClient ftp;
	
	public Logout(FTPClient ftp)
	{
		this.ftp=ftp;
	}
	public FTPClient logoutFTP()
	{
		try
		{
			if(ftp.isConnected())
			{
				ftp.logout();
				return ftp;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}
}
