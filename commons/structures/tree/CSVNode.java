package commons.structures.tree;
import java.io.*;
import commons.X;
import commons.Strings;
import commons.Y;
import java.util.HashMap;

public class CSVNode extends MyNode
{
	/*protected CSVNode()
	{
		super();
	}*/
	protected HashMap<String,String> data=new HashMap<String,String>();
	public static HashMap<Integer,String> cols=new HashMap<>();
	public static HashMap<String,String> defaults=null;
	public static String src=null,tar=null;
	private static String nameCol=null;
	
	public CSVNode(String colData)
	{
		super();
		this.loadData(colData);
	}
	public CSVNode(File fl)
	{
		if(!fl.getPath().toLowerCase().endsWith(".csv"))
			throw new RuntimeException("Please only use for csv files");
		processCSV(fl);
	}
	private void setLinkParent(CSVNode lp)
	{
		if(lp==null)
		{
			parent=null;
			return;
		}
		String sD=src,tD=this.data.get(tar);
		X.sopln(sD+","+tD,"blue");
		CSVNode tN=lp.searchByValue(sD,tD);
		if(tN==null)
			X.sopln("Could not find Values");
		tN.addChild(this);
	}
	
	public CSVNode searchByValue(String cN,String dat)
	{
		CSVNode cvn=(CSVNode)this.getTopNode();
		if(cvn.satisfies(cN,dat))
			return cvn;
		CSVNode r=null;
		for(MyNode mn : cvn.children)
		{
			r=((CSVNode)mn).searchTree(cN,dat);
			if(r!=null)
				return r;
		}
		return null;
	}
	public CSVNode searchTree(String c,String d)
	{
		if(this.satisfies(c,d))
			return this;
		CSVNode t=null;
		for(MyNode mn : children)
		{
			t=((CSVNode)mn).searchTree(c,d);
			if(t!=null)
				return t;
		}
		return null;
	}
	public boolean satisfies(String c,String d)
	{
		
		if(data.get(c)==null)
			return false;
		return data.get(c).equals(d);
	}
	public void printData(String col)
	{
		for(String s : data.values())
			X.sop(s+", ",col);
		X.sopln("\b");
	}
	public void printData() {printData("white");}
	private void processCSV(File f)
	{
		FileReader fr=null;
		BufferedReader br=null;
		String str;
		try 
		{
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			String l1=getNonemptyCSVLine(br);
			if(l1==null)
				throw new BadCSVException("Invalid or no opening statements to CSV.");
			X.sopln(l1,"red");
			setColsBy(l1);
			/*for(String s : cols.values())
				X.sopln(s,"cyan");*/
			l1=getNonemptyCSVLine(br);
			if(l1==null)
				return;
			X.sopln(l1,"green");
			setDefsBy(l1);
			l1=getNonemptyCSVLine(br);
			if(l1==null)
				throw new BadCSVException("No links to parents");
			setLinkBy(l1);
			l1=getNonemptyCSVLine(br);
			if(l1==null)
				nameCol=cols.get(1);
			else
				setNCBy(l1);
			l1=getNonemptyCSVLine(br);
			if(l1==null)
			{
				X.sopln("Data ends");
				return;
			}
			X.sopln(l1,"cyan");
			this.loadData(l1);
			CSVNode tempN;
			while((str=getNonemptyCSVLine(br))!=null)
			{
				X.sopln(str,"yellow");
				tempN=new CSVNode(str);
				tempN.setLinkParent(this);
			}
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//'Set' functions
	private void setColsBy(String col)
	{
		int n=Strings.countChar(col,',');
		for(int i=0;i<=n;i++)
			cols.put(i+1,Y.cut(col,',',i+1).trim());
	}
	private void setDefsBy(String col)
	{
		defaults=new HashMap<>();
		int n=Strings.countChar(col,',');
		String d=null;
		for(int i=0;i<=n;i++)
		{
			d=Y.cut(col,',',i+1).trim();
			if(d.equalsIgnoreCase("null") || d.equals(""))
				defaults.put(cols.get(i+1),null);
			else
				defaults.put(cols.get(i+1),d);
		}
	}
	private void setLinkBy(String col)
	{
		if(col==null)
			throw new RuntimeException("Null value passed to setLinkBy(String)");
		int in=col.indexOf("->");
		if(in==-1)
			throw new BadCSVException("Bad Link format: "+col);
		String sc=col.substring(0,in).trim();
		String dest=col.substring(in+2).trim();
		if(!hasCol(sc) || !hasCol(dest))
			throw new BadCSVException("Source: "+sc+" or Target: "+dest+" in CSV are not in column list");
		src=sc;tar=dest;
	}
	private void setNCBy(String col)
	{
		if(col==null)
			throw new RuntimeException("Null value passed to setCNBy(String)");
		if(!hasCol(col))
			throw new BadCSVException("Column: "+col+" not in column list");
		nameCol=col;
	}
	private void loadData(String cData)
	{
		int n=Strings.countChar(cData,',');
		String tDat;
		for(int i=0;i<=n;i++)
		{
			tDat=Y.cut(cData,',',i+1).trim();
			if(tDat.equals(""))
			{
				if(isNecessary(cols.get(i+1)))
					throw new BadCSVException("Compulsary Column: "+cols.get(i+1)+" has no value for input:> "+cData);
				else
					data.put(cols.get(i+1),defaults.get(cols.get(i+1)));
				continue;
			}
			data.put(cols.get(i+1),tDat.trim());
		}
		if(nameCol!=null)
			Name=data.get(nameCol);
	}
	
	public String getNonemptyCSVLine(BufferedReader br)throws IOException
	{
		String s=br.readLine();
		if(s==null)
			return null;
		if(s.indexOf('#')!=-1)
			s=s.substring(0,s.indexOf('#'));
		if(s.trim().equals(""))
			return getNonemptyCSVLine(br);
		return s;
	}
	
	//Data functions
	public String getColumnName(int i)
	{
		return cols.get(i);
	}
	public int getColumnNo(String cName)
	{
		if(cols.containsValue(cName))
		{
			for(int i : cols.keySet())
			{
				if(cols.get(i).equals(cName))
					return i;
			}
		}
		return -1;
	}
	public String getData(String cN)
	{
		return data.get(cN);
	}
	public String get(String cN) {return getData(cN);}
	public boolean isNecessary(String cN)
	{
		String s=defaults.get(cN);
		if(s==null)
			return false;
		if(s.trim().equals("*"))
			return true;
		return false;
	}
	public boolean hasCol(String co) {return !cols.containsKey(co);}
}

class BadCSVException extends RuntimeException
{
	public BadCSVException()
	{
		X.sopln("Bad CSV file sent as input","red");
	}
	public BadCSVException(String msg)
	{
		//super(msg);
		X.sopln(msg,"red");
	}
}
