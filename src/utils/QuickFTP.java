package utils;

import javax.swing.JOptionPane;

import mainframelogin.Connect;
import mainframelogin.Login;
import mainframelogin.Disconnect;
import mainframelogin.Logout;

import org.apache.commons.net.ftp.FTPClient;
import org.javatuples.Pair;

public class QuickFTP
{
	public boolean connectAndLogin(FTPClient ftp)
	{
		Connect ob1=new Connect(ftp);
		Pair<FTPClient,String[]> p1=null;
		String message1[]={"",""};
		try
		{
			p1=ob1.connectFTP();
			message1=p1.getValue1();
			if(message1[0].equals("true"))
			{
				Login ob2=new Login(ftp);
				Pair<FTPClient,String[]> p2=ob2.loginFTP();
				ftp=p2.getValue0();
				String message2[]=p2.getValue1();
				if(message2[0].equals("true"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	public boolean logoutAndDisconnect(FTPClient ftp)
	{
		Logout  ob3=new Logout(ftp);
		ftp=ob3.logoutFTP();
		if(ftp!=null)
		{
			Disconnect ob4=new Disconnect(ftp);
			return ob4.disconnectFTP();
		}
		else
		{
			return false;
		}
	}
}
