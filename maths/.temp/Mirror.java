package maths;
import commons.*;

public class Mirror implements Operatable
{
	char vN=(char)0;
	public Mirror() {}
	public Mirror(char v)
	{
		vN=v;
	}
    
	public double getVal(double v) {return v;}
	public double getVal(char[] vars,double[] vals)
	{
		if(vN==(char)0)
			return getVal(vals[0]);
		for(int i=0;i<vars.length;i++)
		{
			if(vN==vars[i])
				return vals[i];
		}
		return 1;
	}
	public char getVar()
	{
		return vN;
	}
	public void setVar(char v) {vN=v;}
	public void printExpr(String col) {X.sopln(vN+"",col);}
	public void printExpr() {printExpr("white");}
	public void typeExpr(String col) {X.sop(vN+"",col);}
	public void typeExpr() {typeExpr("white");}
	public void addCoef(double co)throws InconsistantCoefException
	{
		throw new InconsistantCoefException("Mirror does not allow coefficients. Use 'Term' class");
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
		return false;
	}
	public Operatable differentiate(char resp)
	{
		if(vN!=resp)
			return Constant.ZERO;
		return Constant.UNITY;
	}
	public Operatable integrate(char resp)throws IntegrationException
	{
		return new Term(this.getClone(),new Reciprocal(2),new Constant(2));
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
		return (vN==v);
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
	public Mirror getClone()
	{
		return new Mirror(vN);
	}
	public Operatable replace(char v,double val)
	{
		if(vN==v || vN==(char)0)
			return new Constant(val);
		else
			return this.getClone();
	}
	public Constant getCoef(char c, double val)
	{
		if(val!=1)
			return Constant.ZERO;
		else
		{
			if(c==vN || vN==(char)0)
				return Constant.UNITY;
			else
				return Constant.ZERO;
		}
	}
	public Constant getCoef(char[] cs,double val[])
	{
		return Constant.ZERO;
	}
	public double[] getPwr(char c)
	{
		if(c==vN)
			return new double[] {1};
		else 
			return new double[] {0};
	}
}