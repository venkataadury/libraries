package maths;
import commons.*;

public final class Reciprocal extends Maths implements Operatable
{
	protected Operatable t=new Constant(1);
	Constant coef=new Constant(1);
	public Reciprocal() {}
	public Reciprocal(double v)
	{
		t=new Constant(v);
	}
	public Reciprocal(Operatable o)
	{
		t=o;
	}

	public void setVar(char v)
	{
		t.setVar(v);
	}
	public char getVar()
	{
		return t.getVar();
	}
	public double getVal(double v)
	{
		return coef.getVal()/t.getVal(v);
	}
	public double getVal(char[] vars,double[] vals)
	{
		return coef.getVal()/t.getVal(vars,vals);
	}
	public void printExpr(String col)
	{
		this.typeExpr(col);
		X.sopln();
	}
	public void printExpr()
	{
		printExpr("white");
	}
	public void typeExpr(String col)
	{
		X.sop(Maths.perfect(coef.getVal()),col);
		X.sop("/",col);
		t.typeExpr(col);
	}
	public void typeExpr()
	{
		typeExpr("White");
	}
	public void addCoef(double c)
	{
		coef=new Constant(coef.getVal()+c);
	}
	public double getCoef()
	{
		return coef.getVal();
	}
	public double getPwr()
	{
		return 1;
	}
	public boolean isConst()
	{
		return t.isConst();
	}
	public Operatable differentiate(char resp)
	{
            Operatable o1=new Term(t,Constant.MINUS,new Constant(2));
            Reciprocal r=new Reciprocal(o1);
            return r.mul(t.differentiate(resp));
	}
	public Operatable integrate(char resp)throws IntegrationException
	{
		return null;
		
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
	public boolean hasVar(char v)
	{
            return t.hasVar(v);
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
	public Reciprocal getClone()
	{
		return new Reciprocal(t.getClone());
	}
	public Operatable replace(char v,double val)
	{
		Reciprocal r=new Reciprocal(t.replace(v,val));
		r.coef=this.coef;
		if(r.isConst())
			return new Constant(r.getVal(0));
		else
			return r;
	}
	public Constant getCoef(char ch,double val)
	{
		return new Frac(1d/t.getCoef(ch,-val).doubleValue());
	}
	public Constant getCoef(char[] ch,double[] val)
	{
		for(int i=0;i<val.length;i++)
			val[i]*=-1;
		return new Frac(1d/t.getCoef(ch,val).doubleValue());
	}
	public double[] getPwr(char c)
	{
		double[] ans=t.getPwr(c);
		for(int i=0;i<ans.length;i++)
			ans[i]*=-1;
		return ans;
	}
}
