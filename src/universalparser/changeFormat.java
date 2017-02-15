package universalparser;

import java.util.ArrayList;
import java.util.Iterator;

public class changeFormat {
	
	    
	        public changeFormat()
	        {
	            
	        }
	        public boolean checkPresence(ArrayList<StringBuffer> p)
	                        {
	                    boolean flag=false;
	                    StringBuffer t=new StringBuffer(p.get(0));
	                    if(t.equals("PRINT ON,NOGEN")==true)
	                    {
	                        flag=true;
	                    }
	                    else
	                    {
	                        flag=false;
	                    }
	                    return flag;
	                }
	        public ArrayList<StringBuffer> format(ArrayList<StringBuffer> p)
	        {
	            int pos1,pos2,pos3,i,l,t=0;
	            pos1=pos2=pos3=-9999;
	            ArrayList<StringBuffer> a=new ArrayList<StringBuffer>();
	            Iterator<StringBuffer> it=p.iterator();
	            if(checkPresence(p)==true)
	            {
	                StringBuffer sd=new StringBuffer("         ");
	                sd.append(it.next());
	                a.add(sd);
	            }
	            while(it.hasNext())
	            {
	                StringBuffer line=new StringBuffer("");
	                StringBuffer sbr=new StringBuffer(it.next());
	                //array index out of bounds below at 0
	                if(sbr.charAt(0)!='*')
	                {
	                    pos1=sbr.indexOf("DFHMSD");
	                    pos2=sbr.indexOf("DFHMDI");
	                    pos3=sbr.indexOf("DFHMDF");
	                    if(pos1!=-1||pos2!=-1||pos3!=-1)
	                    {
	                    
	                        if(pos1==0||pos2==0||pos3==0)
	                        {
	                        	
	                            line.append("         ");
	                            
	                        }
	                       
	                        line.append(sbr);
	                        l=line.length();
	                        if(pos1==0)
                        	{
                        		t=l;
                        	}
	                        
	                        for(i=0;i<71-l;i++)
	                        {
	                            line.append(' ');
	                        }
	                        line.append('*');
	                        if(pos1==0)
	                        {
	                       while(t<line.length())
	                       {
	                    	   line.deleteCharAt(line.length()-1);
	                    	   
	                       }
	                       
	                        }
	                    }
	                    else
	                    {   
	                        line.append("               ");
	                        if(sbr.charAt(0)==',')
	                        {
	                            sbr.deleteCharAt(0);
	                        }
	                        line.append(sbr);
	                        if(line.charAt(line.length()-1)!=',')
	                        {
	                             l=line.length();
	                        }
	                        else
	                        {
	                        l=line.length();
	                        for(i=0;i<71-l;i++)
	                        {
	                            line.append(' ');
	                        }
	                        line.append('*');
	                        }
	                    }
	                }
	                else
	                {
	                	String h=new String(sbr);
	                	h=h.trim();
	                	line.append(new StringBuffer(h));
	                    
	                }
	                
	                a.add(line);
	                
	            }
	            return a;
	        }
	    }



