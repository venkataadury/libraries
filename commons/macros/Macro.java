package commons.macros;
import commons.X;
import commons.Y;
import commons.Strings;
import upgrade.ArrayFx;
import java.io.*;
import maths.Maths;
import java.util.regex.*;

public class Macro //Allows writing scripts to change Java runtime variables (except primitive data types. Instead use Wrapper classes with Data<T>).  **READ the README file in this folder**
{
	public static boolean clearval=false;
	private DataSet myData=null,savedData=new DataSet();
	private Variable[] vars=new Variable[0];
	private Variable[] saved=new Variable[0];
	private int lno=0;
	private final File macro;
	public static final MacroFunction RANDOM=new MacroFunction("random") {
		public String exec(Macro src,String p)
		{
			int nF=Strings.countChar(p,',');
			int rN=Maths.randInt(nF+1);
			return Y.cut(p,',',rN);
		}
	};
	public static final MacroFunction RANGE=new MacroFunction("range") {
		public String exec(Macro src,String p)
		{
			int ll=X.ipi(Y.cut(p,',',1).trim()),ul=X.ipi(Y.cut(p,',',2).trim());
			String ret="";
			for(int i=ll;i<=ul;i++)
				ret+=i+",";
			return ret.substring(0,ret.length()-1);
		}
	};
	public static final MacroFunction FORCE_STRING=new MacroFunction("string") {
		public String exec(Macro src,String p) {return "~!~"+p;}
	};
	
	public static final MacroFunction EXISTS_DATA=new MacroFunction("exists") {
		public String exec(Macro src,String p)
		{
			p=p.trim();
			return src.myData.containsVar(p)+"";
		}
	};
	
	public static final MacroFunction LIKE=new MacroFunction("like") {
		public String exec(Macro src,String p)
		{
			String wrd=Y.cut(p,',',1);
			String expr=Y.cut(p,',',2);
			Pattern pat=Pattern.compile(expr);
			Matcher m=pat.matcher(wrd);
			return m.matches()+"";
		}
	};
	
	private MacroFunction[] myFuncs=new MacroFunction[] {RANGE,RANDOM,FORCE_STRING,EXISTS_DATA,LIKE};
	
	public Macro(File f) {macro=f;}
	public Macro(File f,DataSet d) {this(f); myData=d;}
	public Macro(File f,Data d) {this(f,new DataSet(d));}
	public Macro(String s) {this(new File(s));}
	public Macro(String s,DataSet d) {this(new File(s),d);}
	public Macro(String s,Data d) {this(new File(s),new DataSet(d));}
	
	public void setData(DataSet d) {myData=d;}
	public void setData(Data d) {myData=new DataSet(d);}
	
	public void addFunction(MacroFunction fx) {myFuncs=ArrayFx.append(myFuncs,fx);}
	
	public void exec() {exec(true);}
	public void exec(boolean printWarns)
	{
		if(myData==null && printWarns)
			X.sopln("Warning: Running macro with no data.","yellow");
		
		FileReader fr;
		BufferedReader br;
		String temp;
		String[] whileconds=new String[0];
		boolean b,skip=false;
		int ifc=0,whilec=0,frc=0;
		lno=0;
		Integer[] whilelines=new Integer[0];
		//saved=new V
		savedData=new DataSet();
		try
		{
			fr=new FileReader(macro);
			br=new BufferedReader(fr);
			while((temp=br.readLine())!=null)
			{
				lno++;
				temp=temp.trim();
				if(printWarns)
					X.sopln(temp,"blue");
				if(temp.startsWith("#") || temp.isEmpty())
					continue;
				temp=replaceVars(temp);
				temp=temp.trim();
				if(ifc==0 && whilec==0)
					skip=false;
				if(temp.equals("endif"))
				{
					ifc--;
					continue;
				}
				if(temp.equals("endwhile"))
				{
					whilec--;
					if(whileconds.length>=whilec+1)
					{
						br=gotoLine(whilelines[whilec].intValue());
						whileconds=ArrayFx.remove(whileconds,whilec);
						whilelines=ArrayFx.remove(whilelines,new Integer(whilec));
					}
					continue;
				}
				if(skip)
					continue;
				if(temp.equals("exit") || temp.equals("quit"))
					return;
				if(temp.startsWith("if"))
				{
					ifc++;
					b=Maths.stringCondition(eval(temp.substring(2).trim()));
					if(!b)
						skip=true;
				}
				if(temp.startsWith("while"))
				{
					whilec++;
					if(skip)
						continue;
					b=Maths.stringCondition(eval(temp.substring(5).trim()));
					if(!b)
					{
						skip=true;
						continue;
					}
					whileconds=ArrayFx.append(whileconds,temp.substring(5).trim());
					whilelines=ArrayFx.append(whilelines,new Integer(lno));
				}
				if(temp.startsWith("set"))
				{
					String s1=temp.substring(3).trim();
					String varName=Y.cut(s1,' ',1).trim();
					String val=eval(Y.cut(s1,' ',2).trim());
					if(myData.containsVar(varName))
						myData.set(varName,val);
					else
						savedData.set(varName,val);
					continue;
				}
				if(temp.startsWith("var"))
				{
					String s1=temp.substring(3).trim();
					String varName=Y.cut(s1,' ',1).trim();
					String val=eval(Y.cut(s1,' ',2).trim());
					if(existsVar(varName))
						getVar(varName).setVal(val);
					else
						vars=ArrayFx.append(vars,new Variable(varName,val));
					continue;
				}
				if(temp.startsWith("deletevar"))
				{
					String s1=temp.substring(9).trim();
					if(existsVar(s1))
						vars=ArrayFx.remove(vars,getVar(s1));
					continue;
				}
				if(temp.startsWith("copydata"))
				{
					String s1=temp.substring(8).trim().replace("  "," ");
					String vn1=Y.cut(s1,' ',1).trim();
					String vn2=Y.cut(s1,' ',2).trim();
					savedData.append(myData.get(vn1).copyAs(vn2));
					continue;
				}
				if(temp.startsWith("call"))
				{
					String s1=temp.substring(4).trim();
					Macro cll=new Macro(new File(macro.getParentFile(),s1),myData);
					cll.exec(printWarns);
				}
				if(temp.startsWith("println"))
				{
					X.sopln(temp.substring(7));
					continue;
				}
				if(temp.startsWith("print"))
				{
					X.sop(temp.substring(5));
					continue;
				}
				if(temp.startsWith("%"))
				{
					eval(temp.trim());
					continue;
				}
			}
		}
		catch(Exception e) {e.printStackTrace(); return;}
		
	}
	public BufferedReader gotoLine(int l)throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader(macro));
		for(int i=0;i<l-1;i++)
			br.readLine();
		lno=l;
		return br;
	}
	
	public boolean existsVar(String vn)	
	{
		for(Variable v : vars)
		{
			if(v.getName().equals(vn))
				return true;
		}
		return false;
	}
	public Variable getVar(String vn)
	{
		for(Variable v : vars)
		{
			if(v.getName().equals(vn))
				return v;
		}
		return null;
	}
	
	public final String replaceVars(String str)
	{
		for(Data d : myData.getAllData())
		{
			if(d.getName()==null) //Required? What about {null} in macro for undefined vars?
				continue;
			if(d.get()==null)
				str=str.replace("{"+d.getName()+"}","null");
			else
				str=str.replace("{"+d.getName()+"}",d.get().toString());
		}
		for(Data d : savedData.getAllData())
		{
			if(d.getName()==null) //Required? What about {null} in macro for undefined vars?
				continue;
				if(d.get()==null)
					str=str.replace("{"+d.getName()+"}","null");
				else
					str=str.replace("{"+d.getName()+"}",d.get().toString());
		}
		for(Variable v :vars)
			str=str.replace("$"+v.getName(),v.getVal().trim());
		return str;
	}
	
	//Eval Methods
	public final String eval(String ln)
	{
		ln=ln.trim();
		int in=ln.lastIndexOf('(');
		String fname="";
		while(in!=-1)
		{
			int min=Strings.getMatchingBracket(ln,'(',')',in);
			String params=ln.substring(in+1,min);
			for(int i=in-1;i>=0;i--)
			{
				if(ln.charAt(i)=='%')
					break;
				fname=ln.charAt(i)+fname;
			}
			String res=function(fname,params);
			if(res==null)
				res="";
			ln=ln.replace("%"+fname+"("+params+")",res);
			//X.sopln("%"+fname+"("+params+")","yellow");
			//X.sopln(ln+"\t"+res,"red");
			in=ln.indexOf('(');
			fname="";
		}
		if(ln.startsWith("~!~"))
			return ln.substring(3).trim();
		ln=ln.trim();
		try {return Maths.perfect(Maths.stringArithmetic(ln));} catch(NumberFormatException e) {}
		try {return ""+Maths.stringCondition(ln);} catch(NumberFormatException e) {}
		return ln;
	}
	public final String function(String fnam,String fpar)
	{
		fnam=fnam.trim(); fpar=fpar.trim();
		for(MacroFunction fx : myFuncs)
		{
			if(fx.getName().equals(fnam))
				return fx.exec(this,fpar);
		}
		X.sopln("MATCH NOT FOUND for function: "+fnam,"red");
		return "";
	}
	public static final void setDefaultClearValue(boolean b) {clearval=b;}
}
