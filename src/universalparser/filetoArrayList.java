package universalparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class filetoArrayList {

			public filetoArrayList()
				    {
				       a=new ArrayList<StringBuffer>();
				    }
				    ArrayList<StringBuffer> a;
				    String filePath;
				    public ArrayList<StringBuffer> toArrayList(ArrayList<StringBuffer> al)throws IOException
				    {
				    	
				        int j;
				        boolean flag=false;
				        Iterator<StringBuffer> i1=al.iterator();
				        try
				        {   
				            String str=null;
				            while(i1.hasNext())
				            {
				                str=new String(i1.next());
				                if(str.charAt(0)!='*')
				                {
				                    if(str.charAt(str.length()-1)=='*')
				                        flag=true;
				                    else
				                        flag=false;
				                    for(j=str.length()-2;;j--)
				                    {
				                    	
				                        if(str.charAt(j)==' ')
				                        {
				                        	//z=j;
				                        }
				                        else
				                            break;
				                    }
				                    str=str.substring(0,j+1);//last space
				                    if(str.length()>17)
				                    {
				                        if(str.charAt(0)==' ' && str.charAt(9)==' ')
				                            str=str.substring(15,str.length());//first space
				                        if(str.charAt(0)==' ' && str.charAt(9)!='D')
				                            str=str.substring(9,str.length());
				                        if((str.charAt(0)==' ' && str.charAt(9)=='D'))
				                            str=str.substring(9,str.length());
				                    }
				                    else
				                    {
				                        if(str.charAt(0)==' ' && str.charAt(9)!='D')
				                        {
				                            str=str.substring(9,str.length());
				                        }
				                    }
				                }
				                if(flag)
				                {
				                    str=str.concat("*");
				                }
				                StringBuffer u=new StringBuffer(str);
				                a.add(u);
				            }
				        }
				        catch (Exception e1)
				        {
				            e1.printStackTrace();
				        }
				        return a;
				    }
				}

