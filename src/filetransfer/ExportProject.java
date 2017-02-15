package filetransfer;

import java.io.File;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author Sounak
 * 
 */
public class ExportProject {

	/**
	 * 
	 */
	private FTPClient ftp;
	private File f;
	private String ret[];

	public ExportProject(FTPClient ftp, File f) {
		super();
		this.f = f;
		this.ftp = ftp;
	}

	public String[] showUI() {
		FTPUpDown ob = new FTPUpDown(ftp);
		ret=ob.upload(f);
		return ret;
	}
}
