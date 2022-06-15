package maths;
import commons.*;
import java.io.*;
import maths.functions.Constant;
public final class Frac extends Constant
{
	protected long p=0,q=1;
	public static final Frac HALF=new Frac(1,2);
	public Frac() {}
	public Frac(double v)
	{
		this(""+v);
	}
	public Frac(long a,long b)
	{
		p=a;
		if(q==0)
			throw new NumberFormatException("Zero in denominator");
		q=b;
		check();
	}
	public Frac(String in)
	{
		if(X.hasChar(in,'/'))
		{
			p=X.ipi(Y.cut(in,'/',1).trim());
			q=X.ipi(Y.cut(in,'/',2).trim());
		}
		else
		{
			double v=X.dpd(in.trim());
			p=X.lpl(Y.cut(String.format("%.12f",v),'.',1)+Y.cut(String.format("%.12f",v),'.',2));
			q=(long)Math.pow(10,Y.cut(String.format("%.12f",v),'.',2).length());
		}
		check();
	}
	
	public void assign(Frac f)
	{
		p=f.p; q=f.q;
		check();
	}
	
	private void check()
	{
		if(p==0)
			return;
		long hcf=Maths.gcd(p,q);
		p/=hcf; q/=hcf;
	}
	public String toString()
	{
		return p+"/"+q;
	}
	public boolean equals(Frac f2)
	{
		return (p==f2.p && q==f2.q);
	}
	public double doubleValue()
	{
		return (double)p/(double)q;
	}
	public double getVal()
	{
		return doubleValue();
	}
	public float floatValue()
	{
		return (float)p/(float)q;
	}
	public long longValue()
	{
		return (long)getVal();
	}
	public int intValue()
	{
		return (int)getVal();
	}
	public double getVal(double v)
	{
		return getVal();
	}
	public double getVal(char[] vars,double[] vals)
	{
		return getVal();
	}
	public char getVar()
	{
		return (char)0;
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
		//this.add(co);
	}
	public boolean isConst()
	{
		return true;
	}
	public void setVar(char C)
	{
		return;
	}
	
	public void typeExpr(String col)
	{
		if(q!=1)
			X.sop(p+"/"+q,col);
		else
			X.sop(p+"",col);
	}
	public void printExpr(String col)
	{
		typeExpr(col);
		X.sopln();
	}
	public void typeExpr()
	{
		typeExpr("white");
	}
	public void printExpr()
	{
		printExpr("white");
	}
	//Operations
	public Frac sum(Frac f)
	{
		Frac r=new Frac();
		r.p=p*f.q+q*f.p;
		r.q=f.q*q;
		r.check();
		return r;
	}
	public Frac sum(double v)
	{
		return sum(new Frac(v));
	}
	public Frac minus(Frac f)
	{
		Frac r=new Frac();
		r.p=p*f.q-q*f.p;
		r.q=f.q*q;
		r.check();
		return r;
	}
	public Frac minus(double v)
	{
		return minus(new Frac(v));
	}
	public Frac prod(Frac f)
	{
		Frac r=new Frac();
		r.p=p*f.p;
		r.q=q*f.q;
		r.check();
		return r;
	}
	public Frac prod(double v)
	{
		return prod(new Frac(v));
	}
	public Frac ratio(Frac f)
	{
		Frac r=new Frac();
		r.p=p*f.q;
		r.q=q*f.p;
		r.check();
		return r;
	}
	public Frac ratio(double v)
	{
		return ratio(new Frac(v));
	}
	public void add(Frac f)
	{
		this.assign(sum(f));
	}
	public void sub(Frac f)
	{
		this.assign(minus(f));
	}
	public void mul(Frac f)
	{
		this.assign(prod(f));
	}
	public void divi(Frac f)
	{
		this.assign(ratio(f));
	}
	public void add(double f)
	{
		this.assign(sum(f));
	}
	public void sub(double f)
	{
		this.assign(minus(f));
	}
	public void mul(double f)
	{
		this.assign(prod(f));
	}
	public void divi(double f)
	{
		this.assign(ratio(f));
	}
	
	public long getNum() {return p;}
	public long getDen() {return q;}
}
class InvalidFractionException extends NumberFormatException
{
	InvalidFractionException()
	{
		X.sopln("Invalid Fraction input","Red");
	}
}
