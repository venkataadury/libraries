package maths;
import commons.*;

public class Constant extends java.lang.Number implements Operatable
{
	double k;
	public static final Constant UNITY=new Constant(1);
	public static final Constant MINUS=new Constant(-1);
	public static final Constant ZERO=new Constant(0);
	public Constant() {k=0;}
	public Constant(double v) {k=v;}
	
	public void setVal(double v) {k=v;}
	public double getVal() {return k;}
	public double getVal(double v) {return k;}
	public char getVar()
	{
		return (char)0;
	}
	public double getVal(char[] vrs,double[] vls)
	{
		return getVal();
	}
	public void printExpr(String col)
	{
		X.sop(Maths.perfect(getVal()),col);
		X.sopln();
	}
	public void typeExpr(String col)
	{
		X.sop(Maths.perfect(getVal()),col);
	}
	public void printExpr()
	{
		printExpr("white");
	}
	public void typeExpr()
	{
		typeExpr("white");
	}
	public double getPwr()
	{
		return 0;
	}
	public double getCoef()
	{
		return getVal();
	}
	public void addCoef(double co)
	{
		k+=co;
	}
	public boolean isConst()
	{
		return true;
	}
	public void setVar(char C)
	{
		return;
	}
	public int intValue()
	{
		return (int)k;
	}
	public long longValue()
	{
		return (long)k;
	}
	public float floatValue()
	{
		return (float)k;
	}
	public double doubleValue()
	{
		return k;
	}
	public Operatable differentiate()
	{
            return new Constant(0);
	}
	public Operatable differentiate(char resp)
	{
           return differentiate();
	}
	public Operatable integrate(char resp)throws IntegrationException
	{
		return new Term(new Mirror(resp),this.getClone());
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
            return false;
	}
	public Constant getClone()
	{
			return new Constant(k);
	}
	public Constant replace(char v,double val)
	{
		return new Constant(k);
	}
	public Constant getCoef(char c,double val)
	{
		if(val!=0)
			return ZERO;
		else
			return this.getClone();
	}
	public Constant getCoef(char[] c,double[] val)
	{
		for(double d : val)
		{
			if(d!=0)
				return ZERO;
		}
		return this.getClone();
	}
	public double[] getPwr(char c)
	{
		return new double[] {0};
	}
}
