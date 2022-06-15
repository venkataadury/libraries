package maths;
import commons.*;

public class Logarithm implements Operatable
{
	protected Number base=Math.E;
	protected Operatable val=new Mirror(); 
	private char var=(char)0;
    
	public Logarithm() {}
	public Logarithm(Operatable v) {val=v;}
	public Logarithm(Operatable v,Number b) {val=v;base=b;}
	public Logarithm(Operatable v,Number b,char va) {this(v,b); var=va;}
	public Logarithm(String in)
	{
		this.parseString(in);
	}
		
	public double getVal(double v)
	{
		return Maths.log(val.getVal(v),base.doubleValue());
	}
	public double getVal(char[] vars,double[] vals)
	{
		if(var==(char)0)
			return getVal(vals[0]);
    for(int i=0;i<vars.length;i++)
		{
			if(var==vars[i])
				return getVal(vals[i]);
		}
		return 0;
	}
	
	private void parseString(String i)throws InvalidLogarithmStringInputException
	{
		//log4(16), log10(100), log2(256), log(100) --> log10(100), log5(x), log5(2x +3)
		i=i.trim(); 
		if(!i.startsWith("log"))
			throw new InvalidLogarithmStringInputException();
		i=i.replace("log",""); 
		if(i.startsWith("("))
			base=10;
		else if(i.startsWith("e") || i.startsWith("E"))
			base=Math.E;
		else
		{
			X.sopln(Y.cut(i,'(',1).trim());
			try {base=X.dpd(Y.cut(i,'(',1).trim());}
			catch(NumberFormatException e) {throw new InvalidLogarithmStringInputException();}
		}
		val=new Function(i.substring(i.indexOf('(')+1).replace(")",""));
		var=val.getVar();
	}
	
	public char getVar() {return var;}
	public void setVar(char v) {var=v;}
	public void addCoef(double c)throws InconsistantCoefException
	{
		throw new InconsistantCoefException("Logarithm does not support coefficients. Use 'Term' class");
	}
	public double getPwr()
	{
		return 1;
	}
	public double getCoef()
	{
		return 1;
	}
	public boolean isConst()
	{
		return val.isConst();
	}
	public Operatable differentiate(char resp)
	{
    Constant c=new Constant(Maths.log(Math.E,base.doubleValue()));
		return c.mul(val.getClone().differentiate(resp).mul(new Reciprocal(val.getClone())));
  }
  public Operatable integrate(char resp)throws IntegrationException
	{
		if(!val.hasVar(resp) || val.isConst())
			return new Term(this.getClone(),new Mirror(resp));
		// logb(f(x))f'(x)/f'(x)
		Term t1=new Term(val.getClone(),new Logarithm(val.getClone()));
		Term t2=new Term(val.getClone(),Constant.MINUS);
		return new Function(new Operatable[] {t1,t2});
	}
	public Operatable integrate(char resp,double k)throws IntegrationException
	{
		Function r=new Function();
		r.append(integrate(resp));
		r.append(new Constant(k));
		return r;
	}
	public Constant integrate(char resp,double i,double f)throws IntegrationException
	{
		Operatable o=this.integrate(resp);
		return new Constant(o.getVal(new char[] {resp},new double[] {f}) - o.getVal(new char[] {resp},new double[] {i}));
	}
	public Operatable add(Operatable o)
	{
		return new Function(new Operatable[] {this,o});
	}
	public Operatable sub(Operatable o)
	{
		return new Function(new Operatable[] {this,new Term(o,Constant.MINUS)});
	}
	public Operatable mul(Operatable o)
	{
		return new Term(this.getClone(),o,Constant.UNITY);
	}
	public Operatable divi(Operatable o)
	{
		return new Term(this.getClone(),new Reciprocal(o),Constant.UNITY);
	}
	public boolean hasVar(char v)
	{
		return val.hasVar(v);
	}
	public void typeExpr(String col)
	{
		X.sop("log"+Maths.perfect(base.doubleValue())+"(",col);
		val.typeExpr(col);
		X.sop(")");
	}
	public void typeExpr() {typeExpr("white");}
	public void printExpr(String col) {typeExpr(col);X.sopln();}
	public void printExpr() {printExpr("white");}
	public Logarithm getClone()
	{
		return new Logarithm(val.getClone(),base,var);
	}
	public Constant getCoef(char c,double val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public Constant getCoef(char[] c,double[] val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public Logarithm replace(char c,double valu)
	{
		return new Logarithm(val.replace(c,valu),base);
	}
	public double[] getPwr(char c)
	{
		if(!val.hasVar(c))
			return new double[] {0};
		else
			throw new InconsistantCoefException();
	}
}
class InvalidLogarithmStringInputException extends RuntimeException
{
	InvalidLogarithmStringInputException()
	{
		X.sopln("Invalid input for logarithmic function.","Red");
	}
}