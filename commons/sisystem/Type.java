package commons.sisystem;
import commons.X;
import commons.Y;
import commons.Strings;
import java.io.*;
import maths.functions.*;

public class Type
{
	//private int pwrs M=0,L=0,T=0,A=0,K=0,C=0,n=0;
	public static final char[] types=new char[] {'M','L','T','A','K','C','n'};
	public static final String[] SIFUND=new String[] {"kg","m","s","A","K","Cd","mol"};
	/*
		M = mass
		L = length
		T = time
		A = current
		K = temp
		C = candela
		n = moles
	*/
	
	
	// ** Way to enter unit ** //
	/*
		value unit
		unit ==> kg.m/s^2
		eg.
		5 kg.m/s^2
		10 kg.m/s.s ==> 10 kg.m/s^2
	*/
	public static int STDUNITS=7;
	private int[] pwrs= new int[] {0,0,0,0,0,0,0};
	public Type() {}
	public Type(int[] vals)
	{
		this();
		for(int i=0;i<pwrs.length && i<vals.length;i++)
			pwrs[i]=vals[i];
	}
	public Type(Type ext)
	{
		this();
		pwrs=ext.pwrs;
	}
	public Type(Type t1,Type t2)
	{
		this();
		for(int i=0;i<STDUNITS;i++)
			pwrs[i]=t1.pwrs[i]+t2.pwrs[i];
	}
	public Type(String uN)
	{
		this();
		assignUnit(uN);
	}
	
	
	public boolean isDimensionless()
	{
		for(int i : pwrs)
		{
			if(i!=0)
				return false;
		}
		return true;
	}
	public boolean equals(Type t2)
	{
		for(int i=0;i<STDUNITS;i++)
		{
			if(t2.pwrs[i]!=this.pwrs[i])
				return false;
		}
		return true;
	}
	public Type invert()
	{
		Type t = new Type(this);
		for(int i=0;i<STDUNITS;i++)
			t.pwrs[i]*=-1;
		return t;
	}
	
	public static double getUnitValue(String in)
	{
		String val=Y.cut(in,' ',1);
		String unit=Y.cut(in,' ',2);
		
		//Convert other units to raw ones:- Adding to process method
		//Assuming all raw units
		
		double v=X.dpd(val);
		int div=unit.indexOf('/');
		int dot=unit.indexOf('.');
		if(div==-1 && dot==-1)
		{
			v=applyConversion(v,unit);
			return v;
		}
		String up1=unit,up2=null;
		if(div!=-1)
		{
			up1=Y.cut(unit,'/',1);
			up2=Y.cut(unit,'/',2);
		}
		else {}
		v=process(v,up1,true);
		v=process(v,up2,false);
		return v;
	}
	
	public static double process(double num,String up,boolean ud)
	{
		if(up==null || up.trim().equals(""))
			return num;
		//num=processOtherUnits(num,up,ud);
		if(Unit.isDerived(up.trim()))
		{
			num=Unit.getConversion(num,up.trim());
			return getUnitValue(num+" "+Unit.getSIUnit(up.trim()));
		}
		if(up.indexOf('.')==-1)
		{
			double ret=applyConversion(num,up,ud);
			return ret;
		}
		double n=num;
		int C=Strings.countChar(up,'.');
		for(int i=1;i<=C+1;i++)
			n=applyConversion(n,Y.cut(up,'.',i).trim(),ud);
		return n; //TODO: Change
	}
	public static double applyConversion(double vin,String un) {return applyConversion(vin,un,true);}
	public static double applyConversion(double vin,String un,boolean eff)
	{
		un=un.trim();
		java.io.File fl=new java.io.File("commons/sisystem/.dat/search");
		FileReader fr; BufferedReader br=null;
		String input=null,fn=null,trm=null;
		int val=1;
		if(un.indexOf('^')!=-1)
		{
			val=X.ipi(Y.cut(un,'^',2).trim());
			un=Y.cut(un,'^',1).trim();
		}	
			
		try
		{
			fr=new FileReader(fl);
			br=new BufferedReader(fr);
			while((input=br.readLine())!=null)
			{
				trm=Y.cut(input,'-',1).trim()+" ";
				if(trm.indexOf(un)!=-1)
				{
					fn=Y.cut(input,'-',2).trim();
					break;
				}
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		fl=new File("commons/sisystem/.dat/"+fn);
		String func="x";
		Function fx;
		try
		{
			fr=new FileReader(fl);
			br=new BufferedReader(fr);
			while((input=br.readLine())!=null)
			{
				if(input.startsWith(un+"~"))
				{
					func=Y.cut(input,'~',2);
					break;
				}
			}
			br.close();
			fx=new Function(func);
		}
		catch(Exception e)
		{
			fx=Unit.CONV.get(un.trim());
			double r1=Math.pow(fx.getVal(1),val);
			if(eff)
				return getUnitValue((vin*r1)+" "+Unit.getSIUnit(un.trim()));
			else
				return getUnitValue((vin/r1)+" "+Unit.getSIUnit(un.trim()));
		}
		
		//Function fy=new Function(new Term(fx,new Constant(1),new Constant(val)));
		double r=Math.pow(fx.getVal(1),val);
		//double valu=fx.getPureVal(vin);
		if(eff)
			return vin*r;
		else
			return vin/r;
	}
	
	/*public static String replaceUnits(String in)
	{
		java.io.File fl=new java.io.File("commons/sisystem/.dat/search");
		return in;
	}*/
	/*public static double processOtherUnits(double iV,String un)
	{
		if(un.)
	}*/
	private void assignUnit(String un)
	{
		un=un.trim();
		String num=un,den="";
		if(num.indexOf('/')!=-1)
		{
			num=Y.cut(un,'/',1);
			den=Y.cut(un,'/',2);
		}
		setUnits(num,true);
		setUnits(den,false);
	}
	private void setUnits(String unS,boolean ad)
	{
		if(unS.equals("") || unS==null)
			return;
		int C=Strings.countChar(unS,'.');
		String uN,pw="1",unT=null;
		for(int i=1;i<=C+1;i++)
		{
			/*if(unS.indexOf('^')==-1)
				uN=unS;
			else
			{
				uN=Y.cut(unS,'^',1);
				pw=Y.cut(unS,'^',2);
			}*/
			pw="1";
			unT=Y.cut(unS,'.',i);
			if(unT.indexOf('^')==-1)
				uN=unT;
			else
			{
				uN=Y.cut(unT,'^',1);
				pw=Y.cut(unT,'^',2);
			}
			
			int id=getDimensionID(uN);
			if(id!=-1)
			{
				if(ad)
					pwrs[id]+=X.ipi(pw);
				else
					pwrs[id]-=X.ipi(pw);
			}
			else
				assignUnit(Unit.getSIUnit(uN));
		}
	}
	
	public static int getDimensionID(String un)
	{
		java.io.File fl=new java.io.File("commons/sisystem/.dat/search");
		un=un.trim();
		FileReader fr; BufferedReader br=null;
		String str=null;
		int K=-1,L=-1;
		try
		{
			fr=new FileReader(fl);
			br=new BufferedReader(fr);
			K=0;
			while((str=br.readLine())!=null)
			{
				if(str.indexOf(un+" ")!=-1)
				{
					L=K;
					break;
				}
				K++;
			}
			return L;
		}
		catch(Exception e) {e.printStackTrace(); return -1;}
	}
	public String toString(double v)
	{
		return v+" "+getUnit();
	}
	public String toString()
	{
		return getUnit().trim();
	}
	public String getUnit()
	{
		String UN_num="",UN_den="";
		
		for(int i=0;i<pwrs.length;i++)
		{
			if(pwrs[i]==0)
				continue;
			if(pwrs[i]>0)
				UN_num+=SIFUND[i]+((pwrs[i]==1)?"":"^"+pwrs[i])+".";
			else
				UN_den+=SIFUND[i]+((Math.abs(pwrs[i])==1)?"":"^"+pwrs[i]*-1)+".";
		}
		if(UN_num.endsWith("."))
			UN_num=UN_num.substring(0,UN_num.length()-1);
		if(UN_den.endsWith("."))
			UN_den=UN_den.substring(0,UN_den.length()-1);
		if(UN_den.equals(""))
			return UN_num;
		String res=UN_num+"/"+UN_den;
		return res;
	}
	public String getDimension()
	{
		String dim="";
		for(int i=0;i<pwrs.length;i++)
		{
			if(pwrs[i]==0)
				continue;
			dim+=types[i]+((pwrs[i]==1)?"":"^"+pwrs[i]);
		}
		return dim;
	}
	public int[] getPowers()
	{
		return pwrs;
	}
}
