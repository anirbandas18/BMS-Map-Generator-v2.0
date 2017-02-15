package universalparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class sta 
{
	private Stack<Character> st;
	private static ArrayList<StringBuffer> g;
	public sta()
	{
		st=new Stack<Character>();
		g=new ArrayList<StringBuffer>();
	}
	private String stackToString(Stack<Character> s)
	{
		String str="";
		for(char x:st)
		{
			str=str+x;
		}
		return str;
	}
	public int getEndPoint(StringBuffer str)
		                    {
		                        int j;
		                        System.out.print(str);
		                        for(j=str.length()-2;j>=0;j--)
		                        {
		                            if(str.charAt(j)==' ')
		                            {
		                            	//z=j;
		                            }
		                            else
		                                break;
		                        }
		                        return j+1;
		                    }
		                    public void extract(String s)
		                    {
		                    	System.out.println("String : " + s);
		                        int k,i,j;
		                        String a="",attr="";
		                        s=s+',';
		                        boolean flag=false;
		                        for(k=0,i=k;i<s.length();i++)
		                        {
		                        	System.out.println("Character read : " + s.charAt(i));
		                            if(s.charAt(i)=='(')
		                            {
		                                a=s.substring(k,i);
		                                for(j=i;s.charAt(j)!=')';j++)
		                                {
		                                    st.push(s.charAt(j));
		                                }
		                                st.push(s.charAt(j));
		                                i=k=j+2;
		                                attr=a+stackToString(st)+",";
		                                st.clear();
		                                flag=true;
		                            }
		                            else
		                            {
		                            	if(s.charAt(i) == '\'')
		                            	{
		                            		a=s.substring(k,i);
		                            		st.push(s.charAt(i));
			                                for(j=i+1;s.charAt(j)!='\'';j++)
			                                {
			                                	//System.out.println("Scanned : " + s.charAt(j));
			                                    st.push(s.charAt(j));
			                                }
			                                st.push(s.charAt(j));
			                                i=k=j+2;
			                                //System.out.println("Stack to String : " + stackToString(st));
			                                attr=a+stackToString(st);
			                                st.clear();
			                                flag=true;
		                            	}
		                            	else
		                            	{
		                            		if(s.charAt(i)==',')
		                            		{
		                            			//problem lies below 
		                            			attr=s.substring(k,i);
		                            			k=i+1;
		                            			attr=attr+",";
		                            			flag=true;
		                            		}
		                            		else
		                            		{
		                            			flag=false;
		                            		}
		                            	}
		                            }
		                            if(flag==true)
		                            {
		                                StringBuffer sbr=new StringBuffer(attr);
		                                g.add(sbr); 
		                            }
		                        }
		                       StringBuffer u=new StringBuffer(g.get(g.size()-1));
		                           u.deleteCharAt(u.length()-1);
		                           g.set(g.size()-1, u);
		                        
		                        }
		                   public static ArrayList<StringBuffer> perform(ArrayList<String> p)
	                        {
	                            //int i=0;
	                            sta st=new sta();
	                            Iterator<String> it=p.iterator();
	                            ArrayList<StringBuffer> sbr=new ArrayList<StringBuffer>();
	                            for(String x:p)
	                            {
	                            	sbr.add(new StringBuffer(x));
	                            }
	                            if(checkPresence(sbr)==true)
	                            {
	                            	
	                            	g.add(new StringBuffer(it.next()));
	                            	
	                            }
	                            while(it.hasNext())
	                            {
	                                st.extract(it.next());
	                            }
	                            return g;
	                        }
		                    public static boolean checkPresence(ArrayList<StringBuffer> p)
	                        {
	                    boolean flag=false;
	                    String t=new String(p.get(0));
	                    t=t.trim();
	                    if(t.equalsIgnoreCase("PRINT ON,NOGEN"))
	                    {
	                    	
	                        flag=true;
	                    }
	                    else
	                    {
	                        flag=false;
	                    }
	                    return flag;
	                }

					}    
					    
