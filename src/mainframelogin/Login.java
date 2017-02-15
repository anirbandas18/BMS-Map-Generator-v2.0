package mainframelogin;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import org.javatuples.Pair;

import otf.OTF;

public class Login 
{
	private boolean loginStatus;
	private int replyCode;
	private String tso_id,password,message[];
	private FTPClient ftp;
	private Pair<FTPClient,String[]> p;
	
	public Login(FTPClient ftp)
	{
		this.ftp=ftp;
		this.tso_id=otf.OTF.tso_id;
		this.password=otf.OTF.password;
		loginStatus=false;
		replyCode=0;
		message=new String[2];
		for(int i=0;i<2;i++)
		{
			message[i]="";
		}
	}
	public Pair<FTPClient,String[]> loginFTP()
	{
		try 
		{
			loginStatus=ftp.login(tso_id,password);
			message[1]=ftp.getReplyString();
			message[1]=message[1].substring(4,message[1].length());
			replyCode = ftp.getReplyCode();
			if(loginStatus==true)
			{
				ftp.enterLocalPassiveMode();
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				if (FTPReply.isPositiveCompletion(replyCode)) 
				{
					OTF.log.info(otf.OTF.cal.getTime() + "  " + tso_id
						+ " logged in to mainframe");	
					ftp.setSoLinger(true, 0);
					message[0]="true";
				}
				else
				{
					message[1]="Login Failed "+"Invalid User Name/Password";
					message[0]="false";
				}
			}
			else
			{
				message[1]="Login Error : "+message[1];
				message[0]="false";
			}
		} 
    	catch (Exception e1)
    	{
			// TODO Auto-generated catch block
    		message[1]="Error "
					+ "establishing session : "+e1.toString();
			e1.printStackTrace();
			message[0]="false";
		}
		p=Pair.with(ftp, message);
		return p;
	}
}
