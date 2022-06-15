package maths.sets;
import commons.*;
import maths.*;

public class Rule
{
	Progressive prog;
	Double[] val=new Double[0];
	Operation op;
	//Comparison cp=null;
	int cap=10000000;
	boolean uncapped=true;
	
	public ESet s1,s2;
	public boolean[] inc=new boolean[] {false,false}; //{true,true} = Intersection {false,false} Union. {false,true} = B-A {true,false}= A-B
	boolean setb=false;
	
	public Rule() {op=null; prog=null;}
	public Rule(Progressive p)
	{
		prog=p;
		op=new Operation();
	}
	public Rule(Progressive p,int c)
	{
		this(p);
		cap=c;
	}
	public Rule(Operation o)
	{
		op=o;
	}
	public Rule(Operation o,int c)
	{
		this(o);
		cap=c;
		uncapped=false;
	}
	public Rule(Progressive p,Operation o)
	{
		prog=p; op=o;
	}
	public Rule(Progressive p,Operation o,int c)
	{
		this(p,o);
		cap=c;
		uncapped=false;
	}
	public Rule(ESet es1,ESet es2,boolean[] prio)
	{
		s1=es1; s2=es2;
		inc=prio;
		setb=true;
	}
	
	public void capAt(int c)
	{
		cap=c;
		uncapped=false;
	}
	
	public ESet getSet()
	{
		return new ESet(this);
	}
	public T[] getEls<T>(double capp)
	{
		T[] res=(T[])(new Object[0]);
		T[] ELs;
		if(setb)
		{
			if(inc[0])
			{
				ELs=(T[])s1.getElements();
				for(T ob : ELs)
				{
					if(inc[1] && s2.belongs(ob))
						res=append(res,(T)ob);
					if(!inc[1] && !s2.belongs(ob))
						res=append(res,(T)ob);
				}
			}
			else if(inc[1])
			{
				ELs=(T[])s2.getElements();
				for(T ob : ELs)
				{
					if(!s1.belongs(ob))
						res=append(res,(T)ob);
				}
			}
			else
			{
				ELs=(T[])s2.getElements();
				res=(T[])ELs;
				T[] ELs2=(T[])s1.getElements();
				boolean ia=true;
				for(T ob : ELs2)
				{
					ia=true;
					for(T ob2 : ELs)
					{
						if(ob2.equals(ob))
						{
							ia=false;
							break;
						}
					}
					if(ia)
						res=append(res,(T)ob);
				}
			}
			return res;
		}
		if(prog.fixed())
			return new T[] {(T)prog.getFirstTerm()};
		double ve=prog.getFirstTerm();
		boolean ord=prog.order();
		int i=1;
		while(i<=capp)
		{
			ve=prog.getTerm(i);
			if(op!=null)
				ve=op.getVal(ve);
			res=append(res,ve);
			i++;
		}
		return res;
	}
	public T[] getEls<T>()
	{
		return getEls<T>(cap);
	}
	public static Double[] append(Double[] da,double d)
	{
		Double[] re=new Double[da.length+1];
		for(int i=0;i<da.length;i++)
			re[i]=da[i];
		re[da.length]=d;
		return re;
	}
	public boolean allows(double val)
	{
		double co=val;
		if(op!=null)
			co=op.getVal(val);
		if(!setb)
			return prog.hasTerm(Maths.round(val,4));
		else
		{
			if(inc[0] && inc[1])
				return (s1.belongs(val) && s2.belongs(val));
			if(inc[0])
				return (s1.belongs(val) && !s2.belongs(val));
			if(inc[1])
				return (!s1.belongs(val) && s2.belongs(val));
			return (s1.belongs(val) || s2.belongs(val));
		}
	}
	public void present()
	{
		if(!setb)
		{
		X.sop("{ x: x=");
		if(op!=null)
			op.present('n');
		else
			X.sop("n");
		X.sop(" ; n= ");
		prog.present('m');
		X.sop(" ; m "+Symbols.BNGS+" N }");
		}
		else
		{
			X.sop("{ x : x ");
			if(inc[0])
			{
				X.sop(Symbols.BNGS+"  A &  x ");
				if(inc[1])
					X.sop(Symbols.BNGS+" B");
				else
					X.sop(Symbols.NBNGS+" B");
			}
			else
			{
				if(inc[1])
					X.sop(Symbols.NBNGS+"  A &  x "+Symbols.BNGS+" B");
				else
					X.sop(Symbols.BNGS+"  A  or  x "+Symbols.BNGS+" B");
			}
			X.sopln("}");
			X.sopln("Where");
			X.sop("A=");
			s1.printSet();
			X.sop("B=");
			s2.printSet();
		}
	}
}
