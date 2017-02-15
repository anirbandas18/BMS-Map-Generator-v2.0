package filetransfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import org.apache.commons.net.ftp.FTPClient;



public class FTPUpDown 
{

	private FTPClient ftp;
	private String ret[]=new String[2];

	public FTPUpDown(FTPClient ftp) 
	{
		this.ftp = ftp;
		for(int i=0;i<2;i++)
			ret[i]="";
	}

	public String[] upload(File f)
	{
		try 
		{
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			boolean done = ftp.storeFile(f.getName().substring(0, f.getName().length() - 4), in);
			int code=ftp.getReplyCode();
			in.close();
			if (done) 
			{
				ret[0]="true";
				ret[1]="File Exported Successfully";
				otf.OTF.log.info(otf.OTF.cal.getTime() + "  Mapset "+ f.getName().substring(0, f.getName().length() - 4)+ " exported to host");
			} 
			else 
			{
				ret[0]="false";
				ret[1]="File Export Failed";
				if(code==451)
				{
					ret[1]=ret[1]+" : PDS is full";
				}
			}
		} 
		catch (Exception e) 
		{
			ret[0]="false";
			ret[1]="File Export Failed : "+e.toString();
			e.printStackTrace();
		}
		return ret;
	}

	public String[] download(String remoteFile, File downloadFile) 
	{
		try 
		{
			OutputStream out = new BufferedOutputStream(new FileOutputStream(downloadFile));
			boolean success = ftp.retrieveFile(remoteFile, out);
			out.close();
			if (success) 
			{
				ret[0]="true";
				ret[1]="File Imported Successfully";
				otf.OTF.log.info(otf.OTF.cal.getTime() + "  Mapset " + remoteFile+ " imported from host.");
			} 
			else
			{
				ret[0]="false";
				ret[1]="File Import Failed";
				
			}
		} 
		catch(Exception e)
		{
			ret[0]="false";
			ret[1]="File Import Failed: "+e.toString();
			e.printStackTrace();
		}
		return ret;
	}
}
