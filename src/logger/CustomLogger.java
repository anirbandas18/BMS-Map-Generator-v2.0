/**
 * 
 */
package logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import utils.SetLAF;

/**
 * @author SOUNAK
 * 
 */
public class CustomLogger {

	// PrintWriter p;
	File f;
	FileWriter p;

	public CustomLogger(String path) 
	{
		new SetLAF();
		f = new File(path);
		try {
			// p=new PrintWriter(f);
			p = new FileWriter(f, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(otf.OTF.otf,
					e.toString(), "Log File Creation Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(otf.OTF.otf,
					e.toString(), "Log File Creation Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void info(String message) {
		try {
			p.write("INFO - " + message + "\n");
			p.flush();
			// p.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(otf.OTF.otf,
					"Exception :" + e.toString(), "Log File Write Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void error(String message) {
		try {
			p.write("ERROR - " + message + "\n");
			p.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(otf.OTF.otf,
					"Exception :" + e.toString(), "Log File Write Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fatalError(String message) {
		try {
			p.write("FATAL ERROR - " + message + "\n");
			p.flush();
			p.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(otf.OTF.otf,
					"Exception :" + e.toString(), "Log File Write Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
