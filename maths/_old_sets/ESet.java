package maths.sets;
import commons.*;
import maths.*;
import upgrade.ArrayFx;

public class ESet<T> //implements Serializable
{
	public static final ESet N=new ESet(new Rule(new AP(1,1),new Operation()));
	public static final ESet W=new ESet(new Rule(new AP(0,1),new Operation()));
	
	Rule rule;
	T[] sels;
	int n;
	boolean roster=true; //finite
	String rep="";
	boolean nullset=false;
	public ESet() {}
	public ESet(int l)
	{
		n=l;
		sels=(T[])(new Object[i]);
	}
	public ESet(T[] el)
	{
		n=el.length;
		sels=el;
		rep="{ ";
		for(int i=0;i<el.length;i++)
			rep+=el[i].toString()+",";
		rep+=" }";
		if(el.length<=0)
		{
			rep=Symbols.PHI+"";
			nullset=true;
		}
	}
	public ESet(String nv)
	{
	}
	//public ESet(ESet se,Rule r)
	public ESet(Rule r)
	{
		rule=r;
		roster=false;
	}
	
	public T[] getElements()
	{
		if(roster)
			return sels;
		else
			return (T[])rule.getEls<T>(100);
	}
	
	public boolean belongs(Object ob)
	{
		if(roster)
		{
			for(Object el : sels)
			{
				if(el.equals(ob))
					return true;
			}
			return false;
		}
		else
			return rule.allows((double)((Double)ob));
	}
	public ESet<T> union(ESet<? extends T> es2)
	{
		if(this.roster && es2.roster)
		{
			ESet<T> ret=new ESet(aMerge(this.sels,es2.sels));
			return ret;
		}
		return new ESet<T>(new Rule(this,es2,new boolean[] {false,false}));
	}
	public ESet<T> intersection(ESet<? extends T> es2)
	{
		if(this.roster && es2.roster)
			return new ESet(aRefine(this.sels,es2.sels));
		return new ESet(new Rule(this,es2,new boolean[] {true,true}));
	}
	public Object[] aRefine(Object[] o1,Object[] o2)
	{
		Object[] res=new Object[0];
		boolean add=false;
		for(Object o : o1)
		{
			add=false;
			for(Object oe : o2)
			{
				if(oe.equals(o))
					add=true;
			}
			if(add)
				res=append(res,o);
		}
		return res;
	}
	public ESet minus(ESet<? extends T> es2)
	{
		return new ESet<T>(new Rule(this,es2,new boolean[] {true,false}));
	}
	public Object[] aMerge(Object[] o1,Object[] o2)
	{
		Object[] res=new Object[o1.length];
		for(int i=0;i<o1.length;i++)
			res[i]=o1[i];
		boolean add=true;
		for(int i=0;i<o2.length;i++)
		{
			add=true;
			for(Object ob : res)
			{
				if(o2[i].equals(ob))
				{
					add=false;
					break;
				}
				
			}
			if(add)
				res=append(res,o2[i]);
		}
		return res;
	}
	public Object[] append(Object[] oba,Object o)
	{
		Object[] res=new Object[oba.length+1];
		for(int i=0;i<oba.length;i++)
			res[i]=oba[i];
		res[oba.length]=o;
		return res;
	}
	
	public void printSet()
	{
		Object obj;
		if(roster)
		{
			if(nullset)
				X.sopln(Symbols.PHI);
			else
			{
				X.sop("{");
				if(Maths.isNumeric(sels[0]))
					X.sop(Maths.perfect((Double)sels[0]));
				else
					X.sopln(sels[0].toString());
				for(int i=1;i<sels.length;i++)
				{
					obj=sels[i];
					if(Maths.isNumeric(obj))
						X.sop(","+Maths.perfect((Double)sels[i]));
					else
						X.sop(","+sels[i].toString());
				}
				X.sopln("}");
			}
		}
		else
		{
			rule.present();
			X.sopln();
		}
	}
	public void printSet(String col)
	{
		X.sTerm(col);
		printSet();
		X.sTerm("white");
	}
}
