package mainframelogin;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;

public class Disconnect 
{
	private FTPClient ftp;
	
	public Disconnect(FTPClient ftp)
	{
		this.ftp=ftp;
	}
	public boolean disconnectFTP()
	{
		try
		{
			if(ftp.isConnected())
			{
				ftp.disconnect();
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
}
