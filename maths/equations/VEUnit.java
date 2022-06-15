package maths.equations;
import maths.*;
import commons.*;
import java.io.IOException;
import java.util.HashMap;

public class VEUnit extends Calculator
{
	VEBlock[] num,den;
	public static final VEBlock NULLBLOCK=new VEBlock();
	String inpS;
	
	protected VEUnit() {}
	public VEUnit(String vu)
	{
		inpS=vu=vu.trim();
		parseUnit(inpS);
	}
	public VEUnit(VEUnit vu)
	{
		this.assign(vu);
	}
	
	public void assign(VEUnit ext)
	{
		num=ext.num; den=ext.den;
		inpS=ext.inpS;
	}
	
	private void parseUnit(String un)
	{
		den=num=new VEBlock[0];
		boolean hasDen=true;
		if(!X.hasChar(un,'/'))
			hasDen=false;
		String nS,dS;
		if(hasDen)
		{
			nS=Y.cut(un,'/',1).trim();
			dS=Y.cut(un,'/',2).trim();
		}
		else
		{
			nS=un.trim();
			dS="1";
		}
		int nCs1,nCs2;
		nCs1=Strings.countChars(nS,operators)+1;
		String s2;
		int lc;
		for(int i=1;i<=nCs1;i++)
		{
			s2=Y.cut(nS,operators,i);
			if(s2.length()!=0)
			{
				lc=Strings.indexOfFirst(nS,operators);
				if(lc!=-1)
					num=append(num,new VEBlock(s2,nS.charAt(lc)=='+'));
				else
				{
					num=append(num,new VEBlock(s2));
					break;
				}
			}
			else
			{
				s2=nS;
				num=append(num,new VEBlock(s2));
				break;
			}
			nS=nS.substring(lc+1);
		}
		nCs2=Strings.countChars(dS,operators)+1;
		for(int i=1;i<=nCs2;i++)
		{
			s2=Y.cut(dS,operators,i);
			if(s2.length()!=0)
			{
				lc=Strings.indexOfFirst(dS,operators);
				if(lc!=-1)
					den=append(den,new VEBlock(s2,dS.charAt(lc)=='+'));
				else
				{
					den=append(den,new VEBlock(s2));
					break;
				}
			}
			else
			{
				s2=dS;
				den=append(den,new VEBlock(s2));
				break;
			}
			dS=dS.substring(lc+1);
		}
	}
	
	public void printUnit(String col)
	{
		typeUnit(col);
		X.sopln();
	}
	public void printUnit()
	{
		printUnit("white");
	}
	public void typeUnit(String col)
	{
		typeUnit(col,true);
	}
	public void typeUnit(String col,boolean sym)
	{
		if(num[0].coef>=0 && sym)
			X.sop("+ ",col);
		num[0].type(col,false);
		for(int i=1;i<num.length;i++)
			num[i].type(col,true);
		if(den.length!=1 || den[0].coef!=1)
		{
			X.sop("/",col);
			for(VEBlock bk : den)
				bk.type(col);
		}
		X.sop(" ");
	}
	public void typeUnit()
	{
		typeUnit("white");
	}
	
	public static VEBlock[] append(VEBlock[] ba,VEBlock bk)
	{
		VEBlock[] a1=new VEBlock[ba.length+1];
		for(int i=0;i<ba.length;i++)
			a1[i]=ba[i];
		a1[ba.length]=bk;
		return a1;
	}
	public static VEBlock[] append(VEBlock[] ba,VEBlock[] bks)
	{
		VEBlock[] a1=new VEBlock[ba.length+bks.length];
		for(int i=0;i<ba.length;i++)
			a1[i]=ba[i];
		for(int i=0;i<bks.length;i++)
			a1[ba.length+i]=bks[i];
		return a1;
	}
	public void add(VEUnit ve)
	{
		ve.mul(this.getDen());
		this.mul(ve.getDen());
		this.num=append(num,ve.num);
		this.setDen(this.getDen()*ve.getDen());
	}
	public void mul(double v)
	{
		for(VEBlock vb : num)
			vb.mul(v);
	}
	public void divi(double v)
	{
		for(VEBlock vb : den)
			vb.divi(v);
	}
	
	public void setDen(double d)
	{
		den=new VEBlock[] {new VEBlock(""+d)};
	}
	public double getDen()
	{
		double sum=0;
		for(VEBlock vb : den)
			sum+=vb.coef;
		return sum;
	}
	public char[] getVars()
	{
		char[] res=new char[0];
		for(int i=0;i<num.length;i++)
		{
			if(ArrayFx.Lsearch(res,num[i].var)[0]==-1)
				res=ArrayFx.append(res,num[i].var);
		}
		return res;
	}
	public void sumUp()
	{
		HashMap<Character,Double> varset=new HashMap<Character,Double>();
		VEBlock cbk;
		double vl;
		for(int i=0;i<num.length;i++)
		{
			cbk=num[i];
			if(varset.get(cbk.var)==null)
				varset.put(cbk.var,cbk.coef);
			else
			{
				vl=cbk.coef+varset.get(cbk.var);
				varset.remove(cbk.var);
				varset.put(cbk.var,vl);
			}
		}
		num=new VEBlock[0];
		for(char ch : varset.keySet())
			num=append(num,new VEBlock(ch,varset.get(ch)));
	}
}
