package universalparser;

import java.util.ArrayList;
import java.util.Iterator;

public class mergeArr {
	
		        public mergeArr()
		        {
		            a=new ArrayList<StringBuffer>();
		        }
		    ArrayList<StringBuffer> a;

		    public ArrayList<String> merge(ArrayList<StringBuffer> al)
		    {
		        Iterator<StringBuffer> i=al.iterator();
		        StringBuffer r=new StringBuffer();
		        ArrayList<String> y=new ArrayList<String>();
		        
		    while(i.hasNext())
		    {
		        StringBuffer sb=new StringBuffer(i.next());
		        while(sb.charAt(sb.length()-1)=='*')
		        {
		            
		            sb.deleteCharAt(sb.length()-1);
		            r=r.append(sb);
		            StringBuffer m=new StringBuffer(i.next());
		            sb=m;
		            
		        }
		        r=r.append(sb);
		        String   de=new String(r);
		        y.add(de);
		        r.delete(0, r.length());
		        

		    }
		    return y;

		    }
		}
