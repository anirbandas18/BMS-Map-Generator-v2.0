package universalparser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class arraylistToFile {
	
			    ArrayList<StringBuffer> a;
			    String filePath;
			    public arraylistToFile(ArrayList<StringBuffer> al,String f)
			    {
			        filePath=f;
			        a=new ArrayList<StringBuffer>();
			        a.addAll(al);
			    }
			    public void toFile()throws IOException
			    {
			        BufferedWriter bw=new BufferedWriter(new FileWriter(filePath));
			        try
			        {
			            for(StringBuffer s:a)
			            {
			            	String sp=new String(s);
			                bw.write(sp);
			                bw.write("\n");
			            }
			        }
			        catch(Exception e)
			        {
			            System.out.println(e);
			        }
			       finally
			        {
			            bw.close();
			        }
			    }
			    }
			






