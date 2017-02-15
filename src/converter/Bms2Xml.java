/**
 * 
 */
package converter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;





import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;





import org.w3c.dom.Document;
import org.w3c.dom.Element;

import universalparser.*;

/**
 * @author Sounak, Anirban, Piyali, Raktim
 * 
 */
public class Bms2Xml {

	/**
	 * @param args
	 */
	String source, dest, tempf,str[]; /* str stores the tag,elements and values */
	
	File f1, f2;
	BufferedReader br;
	String ret[]=new String[2];
	Document bdoc;
	int max_char;
	boolean flag;
	Element comment;
	public Bms2Xml(String source, String dest) throws FileNotFoundException 
	{
		super();
		File t=new File(source);
		String path= System.getProperty("user.dir") + "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\";
		this.tempf=System.getProperty("user.dir") + "\\Workspace\\Temp\\"+t.getName().substring(0,t.getName().length()-4)+"_temp.txt";
		this.source = source;
		this.dest = path+dest;
		String p=source;
		removeLineNumbers rln=new removeLineNumbers(p);
		filetoArrayList ftal=new filetoArrayList();
		mergeArr ma=new mergeArr();
		changeFormat cf=new changeFormat();
		ArrayList<StringBuffer> sbf=new ArrayList<StringBuffer>();
		try
		{
			sbf.addAll(cf.format(sta.perform(ma.merge(ftal.toArrayList(rln.checkAndDelete())))));
		} 
		catch (Exception e1) 
		{
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			arraylistToFile ob2=new arraylistToFile(sbf,tempf);
			ob2.toFile();
		}
		catch(Exception e)
		{
			//JOptionP
			e.printStackTrace();
		}
		f1 = new File(tempf);
		f2 = new File(this.dest);
		max_char = 9;
		str = new String[4];
		ret[0]="";
		ret[1]="";
	}
	public String[] convert2XML()
	{
		String line;
		try 
		{
			BufferedReader br=new BufferedReader(new FileReader(f1));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xdoc = db.newDocument();
			Element dfhmsd = xdoc.createElement("DFHMSD");
			Element dfhmdi = xdoc.createElement("DFHMDI");
			Element dfhmdf = xdoc.createElement("DFHMDF");
			line = br.readLine();
			if (line.length() == 72)
			{
				line = line.substring(0, line.lastIndexOf(','));
			}
			getSubstrings(line);
			xdoc.appendChild(dfhmsd);
			dfhmsd.setAttribute("Name", str[0]);
			while (line != null)
			{
				Element e = xdoc.createElement(str[2]);
				e.setTextContent(str[3]);
				dfhmsd.appendChild(e);
				line = br.readLine();
				if (line == null)
				{
					break;
				}
				if (line.length() == 72)
				{
					line = line.substring(0, line.lastIndexOf(','));
				}
				getSubstrings(line);
				if (str[1].equals("DFHMDI")) 
				{
					while (!str[1].equals("DFHMSD") && line != null) 
					{
						if (str[1].equals("DFHMDI")) 
						{
							dfhmdi = xdoc.createElement("DFHMDI");
							dfhmsd.appendChild(dfhmdi);
							dfhmdi.setAttribute("Name", str[0]);
						}
						e = xdoc.createElement(str[2]);
						e.setTextContent(str[3]);
						dfhmdi.appendChild(e);
						line = br.readLine();
						if (line.charAt(0)!='*')
						{
							if( line.length() == 72)
							{
								line = line.substring(0, line.lastIndexOf(','));
							}
							getSubstrings(line);
							flag=false;
						}
						else
						{
							comment = xdoc.createElement("COMMENT");
							comment.setTextContent(extractComment(line));
							flag = true;
							line = br.readLine();
							line = line.substring(0,line.lastIndexOf(','));
							getSubstrings(line);
						}
						if (str[1].equals("DFHMDF")) 
						{
							while (!str[1].equals("DFHMSD") && !str[1].equals("DFHMDI") && line != null) 
							{
								if (str[1].equals("DFHMDF")) 
								{
									dfhmdf = xdoc.createElement("DFHMDF");
									dfhmdi.appendChild(dfhmdf);
									dfhmdf.setAttribute("Name", str[0]);
									e = xdoc.createElement(str[2]);
									e.setTextContent(str[3]);
									dfhmdf.appendChild(e);
									
									if(flag)
									{
										dfhmdf.appendChild(comment);
										flag=false;
									}
									line = br.readLine();
									if(line.length() == 72)
									{
										line = line.substring(0,line.lastIndexOf(','));
									}
									getSubstrings(line);
								}
								else
								{
									if (line.charAt(0)!='*')
									{
										e = xdoc.createElement(str[2]);
										e.setTextContent(str[3]);	
										dfhmdf.appendChild(e);
									}
									else
									{
										comment = xdoc.createElement("COMMENT");
										comment.setTextContent(extractComment(line));
										flag=true;
									}
									line = br.readLine();
									if(line.length() == 72)
									{
										line = line.substring(0,line.lastIndexOf(','));
									}
									getSubstrings(line);
								}

							}
						}

						}
					}
					if (str[1].equals("DFHMSD"))		
					{
						break;
					}
				}
				br.close();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(f2);
			transformer.transform(source, result);
			otf.OTF.xmlFile = f2;
			f1.delete();//DELETE TEMPORARY FILE
			ret[0]="true";
			ret[1]="BMS to XML Conversion Successful";
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			ret[0]="false";
			ret[1]=e.toString();
			e.printStackTrace();
		}
		return ret;
	}

	private void getSubstrings(String str1) {
		int i, n;
		if(str1.charAt(0)!='*')
		{
			n = str1.length();
			String temp;
			for (i = 0; i < 4; i++)
			{
				str[i] = "";
			}
			for (i = 0; i < n; i++) 
			{
				char ch = str1.charAt(i);
				temp = "";
				int j, index = 0;
				if (ch != ' ' && ch != '=' && ch != 39) 
				{
					for (j = i; j < n; j++) 
					{
						ch = str1.charAt(j);
						if (ch == ' ' || ch == '=' || (ch == ',' && str1.charAt(j + 1) == ' '))
						{
							break;
						}
						if (ch != '"' && ch != '&')
						{
							temp += ch;
						}
					}
					if (ch == '=')
					{
						index = 2;
					}
					else if (j == n || (j == n - 1 && str1.charAt(j) == ','))
					{
						index = 3;
					}
					else if (temp.equals("DFHMSD") || temp.equals("DFHMDF") || temp.equals("DFHMDI"))
					{
						index = 1;
					}
					i=j;
					str[index] = temp;
				}
				else if (ch == 39) 
				{
					index = 3;
					for (j = i + 1; j < n; j++) 
					{
						ch = str1.charAt(j);
						if (ch == 39)
						{	
							break;
						}
						temp += ch;
					}
					i = j;
					str[index] = temp;
					
				}
			}
			for(i = 0; i < 4; i++)
			{
				if(str[i] == null)
				{
					str[i] = "";
				}
			}
		}
		else
		{
			for (i = 0; i < 4; i++)
			{
				str[i] = "";
			}
		}
	}

	private String extractComment(String line) {
		String r = line.substring(1, line.length());
		for (int i = 0; i < 4; i++)
			str[i] = "";
		return r.trim();
	}
}
