package universalparser;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;

public class removeLineNumbers {
	BufferedReader br;
	    BufferedWriter bw;
	    ArrayList<StringBuffer> a;
	    String p1,p2;
	    public removeLineNumbers(String path1)
	    {
	        p1=path1;
	        File fl=new File(p1);
	        p2=fl.getParent()+"/temp.txt";
	        a=new ArrayList<StringBuffer>();
	        try
	        {
	            br=new BufferedReader(new FileReader(p1));
	        }
	        catch(Exception e)
	        {
	            System.out.println("Exception in opening : "+e);
	        }
	    }
	    public ArrayList<StringBuffer> checkAndDelete()
	    {
	        String s="";
	        try
	        {   
	            while((s=br.readLine())!=null)
	            {
	            	if(s.length()>72)
	                {
	                	s=s.substring(0,s.length()-8);
	                }
	                else 
	                {
	                	int var=s.length();
	                	for(int i=1;i<=72-var;i++)
	                	{
	                		s=s+" ";
	                	}
	                	
	                	
	                }
	                StringBuffer v=new StringBuffer(s);
	                a.add(v);
	            }
	          br.close();
	            
	        }
	        catch(Exception e)
	        {
	            System.out.println("Exception in check and delete : "+e);
	        }
	        
	        return a;
	    }
	    public String newTempFile()
	    {
	        try
	        {   checkAndDelete();
	            bw=new BufferedWriter(new FileWriter(p2));
	            System.out.println("Finished!");
	            bw.close();
	        }
	        catch(Exception e)
	        {   
	            System.out.println("Exception in writing : "+e);
	        }
	        return p2;
	    }
}


