package draw.logo;
import commons.*;
import maths.Maths;
//import 

public class Func 
{
	String name="",parlist="",stmt="";
	
	public Func(String n,String p,String s)
	{
		name=n;
		parlist=p;
		stmt=s;
	}
	public Func(String st)
	{
		int s1=st.indexOf('=');
		String p1=Y.cut(st,'=',1);
		String p2=st.substring(s1+1);
		stmt=p2.trim();
		if(p1.indexOf('(')==-1)
			p1+="()";
		name=p1.substring(0,p1.indexOf('(')).trim();
		parlist=p1.substring(p1.indexOf('(')+1,p1.indexOf(')')).replace(" ","").trim();
	}
	public String getName()
	{
		return name.trim();
	}
	public boolean matches(String ext)
	{
		String p1=ext.trim();
		if(p1.indexOf('(')==-1)
			p1+="()";
		String nam=p1.substring(0,p1.indexOf('(')).trim();
		if(!nam.equals(name))
			return false;
		String plis=p1.substring(p1.indexOf('(')+1,p1.indexOf(')')).replace(" ","");
		if(Strings.countChar(plis,',') == Strings.countChar(parlist,','))
		{
			
			if(parlist.indexOf(',')==-1)
			{
				String p2=p1.replace(nam,"").replace("(","").replace(")","");
				if(parlist.equals("") && p2.equals(""))
					return true;
				if(!p2.equals("") && !parlist.equals(""))
					return true;
				return false;
			}
			else
				return true;
		}
		else
			return false;
	}
	public boolean matches(Func ext)
	{
		return matches(""+ext.name+"("+ext.parlist+")");
	}
	public String call(String ext) //rectangle(5,10)
	{
		if(!matches(ext))
			return null;
		if(parlist.equals(""))
			return stmt.trim();
		if(parlist.indexOf(',')==-1)
			return stmt.replace("$"+parlist.trim(),getParList(ext).trim()).trim();
		int cnt=Strings.countChar(parlist,',');
		X.sopln(cnt+"","cyan");
		String cpy=stmt;
		String pl2=getParList(ext);
		String temp=null,temp2=null;
		for(int i=0;i<=cnt;i++)
		{
			temp=Y.cut(parlist,',',i+1).trim();
			temp2=Y.cut(pl2,',',i+1).trim();
			cpy=cpy.replace("$"+temp,temp2);
		}
		return cpy;
	}
	public static String getParList(String ext)
	{
		String p1=Y.cut(ext,'(',2).trim();
		return p1.replace(")","").trim();
	}
}
