package dfhmdf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/*
 * Piyali Banerjee, Anirban Das
 */
public class OrganizeDFHMDF 
{
	private ArrayList<DFHMDF> a,rl;
	private HashMap<Integer,ArrayList<DFHMDF>> hm;
	public OrganizeDFHMDF(ArrayList<DFHMDF> a)
	{
		this.a = a;
		rl = new ArrayList<DFHMDF>();
		hm = new HashMap<Integer, ArrayList<DFHMDF>>();
	}
	public ArrayList<DFHMDF> organizeList()
	{
		createHashMap();
		arrayListToHashMap();
		organize();
		hashMapToArrayList();
		return rl;
	}
	private int getPosX(String pos)
	{
		pos = pos.substring(1, pos.length() - 1);
		int x = Integer.parseInt(pos.split(",")[0]);
		return x;
	}
	private int getPosY(String pos)
	{
		pos = pos.substring(1, pos.length() - 1);
		int y = Integer.parseInt(pos.split(",")[1]);
		return y;
	}
	private void createHashMap()
	{
		for(DFHMDF i:a)
		{
			int x = getPosX(i.getPos());
			if(!hm.containsKey(x))
			{
				hm.put(x,null);
			}
		}
	}
	private void arrayListToHashMap()
	{
		for(Map.Entry<Integer, ArrayList<DFHMDF>> me : hm.entrySet())
		{
			ArrayList<DFHMDF> temp = new ArrayList<DFHMDF>();
			for(DFHMDF i:a)
			{
				int x = getPosX(i.getPos());
				if(me.getKey().intValue()==x)
				{
					temp.add(i);
				}
			}
			me.setValue(temp);
		}
	}
	private void organize()
	{
		for(Map.Entry<Integer, ArrayList<DFHMDF>> me : hm.entrySet())
		{
			ArrayList<DFHMDF> temp = me.getValue();
			Collections.sort(temp, new Comparator<DFHMDF>() 
			{
				public int compare(DFHMDF f1,DFHMDF f2)
		        {
					int y_f1 = getPosY(f1.getPos());
					int y_f2 = getPosY(f2.getPos());
					return y_f1-y_f2;
		        }
		    });
			me.setValue(temp);
		}
	}
	private void hashMapToArrayList()
	{
		for(Map.Entry<Integer, ArrayList<DFHMDF>> me : hm.entrySet())
		{
			ArrayList<DFHMDF> temp = me.getValue();
			rl.addAll(temp);
		}
	}
}
