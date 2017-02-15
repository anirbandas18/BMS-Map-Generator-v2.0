package mainframelogin;


import org.apache.commons.net.ftp.FTPClient;
import org.javatuples.Pair;

public class Connect
{
	private String host,message[];
	private FTPClient ftp;
	private Pair<FTPClient,String[]> p;
	
	public Connect()
	{
		this.host=otf.OTF.host;
		message=new String[2];
		for(int i=0;i<2;i++)
		{
			message[i]="";
		}
		ftp=new FTPClient();
	}
	public Connect(FTPClient ftp)
	{
		this.host=otf.OTF.host;
		message=new String[2];
		for(int i=0;i<2;i++)
		{
			message[i]="";
		}
		this.ftp=ftp;
	}
	public Pair<FTPClient,String[]> connectFTP()throws Exception
	{
		int y=0;
		ftp.connect(host);
		String x[]=ftp.getReplyStrings();
		message[1]=x[0];
		y=ftp.getReplyCode();
		if(y==220)
		{
			message[0]="true";
		}
		else
		{
			message[1]=host+" : Connection Failed";
			message[0]="false";
		}
		p=Pair.with(ftp, message);
		//JOptionPane.showMessageDialog(null, "FTP Connection Time out : "+ftp.getConnectTimeout()+"\n FTP Default TIMEOUT : "+ftp.getDefaultTimeout()+"\n FTP Control keep alive reply  timeout : "+ftp.getControlKeepAliveReplyTimeout()+"\n FTP Control keep alive timeout : "+ftp.getControlKeepAliveTimeout());
		return p;
	}
}
