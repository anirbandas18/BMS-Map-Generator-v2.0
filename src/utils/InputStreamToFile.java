package utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;
 
public class InputStreamToFile 
{
    public static boolean copy(String[] args)
    {
    	InputStream inputStream = null;
    	OutputStream outputStream = null;
    	try 
    	{
    		inputStream = InputStreamToFile.class.getClass().getResourceAsStream(args[0]);
    		outputStream = new FileOutputStream(new File(args[1]));
    		int read = 0;
    		byte[] bytes = new byte[1024];
    		while ((read = inputStream.read(bytes)) != -1) 
    		{
    			outputStream.write(bytes, 0, read);
    		}
    		return true;
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	finally
    	{
    		if (inputStream != null) 
    		{
    			try 
    			{
    				inputStream.close();
    			}
    			catch (IOException e) 
    			{
    				e.printStackTrace();
    				JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    				return false;
    			}
    		}
    		if (outputStream != null) 
    		{
    			try 
    			{
    				outputStream.close();
    			}
    			catch (IOException e)
    			{
    				e.printStackTrace();
    				JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    				return false;
    			} 
    		}
    	}
    }
}