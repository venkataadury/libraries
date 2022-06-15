package maths.functions;
import commons.X;
import maths.Maths;

public class Mirror implements Calculus
{
	char var=FuncBlock.default_var;
	
	public Mirror() {}
	public Mirror(char ch) {var=ch;}
	
	public double getVal(Number V) {return V.doubleValue();}
	public double getVal(char[] ch,double[] vals)throws VariableMismatchException
	{
		for(int i=0;i<ch.length;i++)
		{
			if(var==ch[i])
				return vals[i];
		}
		throw new VariableMismatchException(ch,getVars());
	}
	public double getVal(java.util.HashMap<Character,? extends Number> hm)throws VariableMismatchException
	{
		for(char c : hm.keySet())
		{
			if(var==c)
				return hm.get(c).doubleValue();
		}
		throw new VariableMismatchException(FuncBlock.getVarArray(hm),getVars());
	}
	public char getVar() {return var;}
	public char[] getVars() {return new char[] {var};}
	public void setVar(char ch) {var=ch;}
	public void setVar(char c1,char c2) 
	{
		if(var==c1)
			var=c2;
	}
	public boolean isMultiTermed() {return false;}
	public boolean isConstant() {return false;}
	public boolean hasVar(char c) {return (var==c);}
	public double getVal(char ch,double val,double def)
	{
		if(var==ch)
			return val;
		return def;
	}
	public void reduce() {}
	public FuncBlock negate()
	{
		return new Term(new Mirror(var),new Constant(-1));
	}
	/*public Term MUL(double d)
	{
		return new Term(this,new Constant(d));
	}
	public Term DIVI(double d)
	{
		return new Term(this,new Constant(1),new Constant(d));
	}*/
	public Mirror clone() {return new Mirror(var);}
	public FuncBlock partialPut(char[] ch,FuncBlock[] val)
	{
		for(int i=0;i<ch.length;i++)
		{
			if(var==ch[i])
				return val[i].clone();
		}
		return this.clone();
	}
	public Function getCoeff(char ch,int pwr)
	{
		if(ch==var)
		{
			if(pwr!=1)
				return new Function(Constant.ZERO);
			else
				return new Function(Constant.ONE);
		}
		if(pwr==0)
			return new Function(this.clone());
		return new Function(Constant.ZERO);
	}
	
	public Constant differentiate(char ch)
	{
		if(var==ch)
			return new Constant(1);
		else
			return new Constant(0);
	}
	
	//Calculus
	public Function getDifferentiation(char resp,char[] otV,FuncBlock[] otvs)
	{
		if(resp==var)
			return new Function(new Constant(1));
		else
		{
			for(int i=0;i<otV.length;i++)
			{
				if(var==otV[i])
					return new Function(otvs[i]);
			}
			return new Function(new Constant(0));
		}
	}
	public Function getIntegration(char resp,double con)
	{
		Function ret=new Function();
		if(var==resp)
			ret.append(new Term(new Mirror(var),new Constant(0.5),Constant.ONE,new Constant(2)));
		else
			ret.append(new Term(new Mirror(var),new Mirror(resp)));
		ret.append(new Constant(con));
		return ret;
	}
	@Override
	public String toString()
	{
		return ""+var;
	}
}
