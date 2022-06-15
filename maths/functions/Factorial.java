package maths.functions;
import commons.*;
import maths.*;

public class Factorial extends Maths implements FuncBlock
{
	char vN=(char)0;
	FuncBlock fact=new Constant(1);
	FuncBlock coef=Constant.ONE;
	public Factorial() {}
	public Factorial(FuncBlock o)
	{
		fact=o;
		vN=o.getVar();
	}
	
	public double getVal(Number v)
	{
		return coef.getVal(v)*Maths.factorial((int)fact.getVal(v));
	}
	public double getVal(char[] vars,double[] vals)
	{
		return coef.getVal(vars,vals)*factorial((int)fact.getVal(vars,vals));
	}
	public double getVal(char ch,double val,double def)
	{
		return coef.getVal(ch,val,def)*Maths.factorial((int)fact.getVal(ch,val,def));
	}
	public double getVal(java.util.HashMap<Character,? extends Number> hm)
	{
		return coef.getVal(hm)*Maths.factorial((int)fact.getVal(hm));
	}
	public Factorial partialPut(char[] ch,FuncBlock[] fb)
	{
		Factorial ret=this.clone();
		ret.fact=ret.fact.partialPut(ch,fb);
		ret.coef=ret.coef.partialPut(ch,fb);
		return ret;
	}
	public char getVar()
	{
		return vN;
	}
	public void setVar(char v)
	{
		vN=v;
		fact.setVar(v);
	}
	public void printExpr(String col)
	{
		typeExpr();
		X.sopln();
	}
	public void printExpr()
	{
		printExpr("white");
	}
	public void typeExpr(String col)
	{
		X.sop("(",col);
		X.sop(fact,col);
		X.sop(")!",col);
	}
	public void typeExpr()
	{
		typeExpr("white");
	}
	public Factorial clone() {Factorial f=new Factorial(fact.clone()); f.coef=coef.clone(); return f;}
	public void addCoef(double co)throws InconsistantCoefException
	{
		if(coef.isConstant())
			coef=new Constant(coef.getVal(0)+co);
		else
			throw new InconsistantCoefException();
	}
	public double getCoef()throws InconsistantCoefException
	{
		if(coef.isConstant())
			return coef.getVal(0);
		else
			throw new InconsistantCoefException();
	}
	public boolean isConstant()
	{
		return (coef.isConstant() && fact.isConstant());
	}
	public Term differentiate(char resp)throws NonDifferentiableException
	{
		throw new NonDifferentiableException();
	}
	/*public Term integrate(char resp)throws IntegrationException
	{
		throw new IntegrationException();
	}
	public Term integrate(char resp,double k)throws IntegrationException
	{
		throw new IntegrationException();
	}
	public Constant integrate(char resp,double i,double f)throws IntegrationException
	{
		throw new IntegrationException();
	}*/
	public Function add(FuncBlock o)
	{
            return new Function(new FuncBlock[] {this,o});
	}
	public Function sub(FuncBlock o)
	{
            return new Function(new FuncBlock[] {this,new Term(o,Constant.MINUS_ONE)});
	}
	public boolean hasVar(char v)
	{
            return (vN==v);
	}
	public Factorial getClone() {return this.clone();}
	public Function getCoeff(char c,int val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public double[] getPwr(char c)
	{
		if(!fact.hasVar(c))
			return new double[] {0};
		else
			throw new InconsistantCoefException();
	}
	public void reduce() {coef.reduce(); fact.reduce();}
	public boolean isMultiTermed() {return true;}
	public void setVar(char ch,char rep) {fact.setVar(ch,rep); coef.setVar(ch,rep); vN=fact.getVar();}
	public char[] getVars() {char[] vars=coef.getVars(); vars=Term.selectiveAppend(vars,fact.getVars()); return vars;}
}
