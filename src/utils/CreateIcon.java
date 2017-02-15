package utils;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.net.URL;
/*
 * Anirban Das
 */
public class CreateIcon
{
	private String path;
	private URL imgURL;
	public CreateIcon()
	{
		this.path="/icons/";
	}
	public ImageIcon createImageIcon(String fileName)
	{
		imgURL = getClass().getResource(path+fileName);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Could not find file: " + path,"Error",JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}
