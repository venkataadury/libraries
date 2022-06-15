package maths;
import commons.*;
import maths.functions.*;
import java.util.HashMap;
//import 

public class BasicPolynomial implements FuncBlock//Calculus
{
	private double[] coefs=new double[0];
	protected char var=(char)0;
	
	public BasicPolynomial() {}
	public BasicPolynomial(double[] co)
	{
		coefs=co;
	}
	public BasicPolynomial(int[] co)
	{
		coefs=new double[co.length];
		for(int i=0;i<coefs.length;i++)
			coefs[i]=co[i];
	}
	public BasicPolynomial(double[] co,char v)
	{
		this(co);
		var=v;
	}
	public void setVar(char v)
	{
		var=v;
	}
	public char getVar()
	{
		return var;
	}
	
	public Function getFx()
	{
		Function fx=new Function();
		for(int i=0;i<coefs.length;i++)
			fx.append(new Term(new Mirror(var),new Constant(coefs[i]),new Constant(i)));
		return fx;
	}
	
	public double getVal(Number val)
	{
		return getFx().getVal(val);
	}
	public double getVal(HashMap<Character,? extends Number> hm)
	{
		return getFx().getVal(hm);
	}
	public double getVal(char[] vars,double[] vals)
	{
		return getFx().getVal(vars,vals);
	}
	public void printExpr(String col)
	{
		typeExpr(col);
		X.sopln();
	}
	public void typeExpr()
	{
		typeExpr("White");
	}
	public void typeExpr(String col) {X.sop(toString(),col);}
	public String toString()
	{
		String str="";
		if(coefs.length==0)
			return "0";
		str+=(coefs[coefs.length-1]+(var+"^")+(coefs.length-1));
		for(int i=coefs.length-2;i>=0;i--)
			str+=("+ "+(coefs[i]+(var+"^")+i));
		return str;
	}
	public void addCoef(double co)throws InconsistantCoefException
	{
		if((int)co!=co)
			throw new InconsistantCoefException();
		int cof=(int)co;
		for(int i=0;i<coefs.length;i++)
			coefs[i]+=cof;
	}
	public double getPwr()throws InconsistantPowerException
	{
		return getMaxPwr();
	}
	public int getMaxPwr()
	{
		return  (coefs.length-1);
	}
	public double getCoef()
	{
		return 1;
	}
	public Constant getCoef(char[] ch,double[] da)
	{
		return Constant.ONE;
	}
	public Constant getCoef(char c,double pwr)
	{
		if(var!=c || !X.isInt(pwr))
			return Constant.ZERO;
		return new Constant(coefs[(int)pwr]);
	}
	public boolean isConst()
	{
		if(coefs.length==1)
			return true;
		for(int i=1;i<coefs.length;i++)
		{
			if(coefs[i]!=0)
				return false;
		}
		return true;
	}
	public BasicPolynomial differentiate(char resp)throws DifferentiationException
	{
		double[] ncoefs=new double[coefs.length-1];
		for(int i=1;i<coefs.length;i++)
			ncoefs[i-1]=coefs[i]*i;
		return new BasicPolynomial(ncoefs);
	}
	public boolean hasVar(char v)
	{
		return var==v;
	}
	public BasicPolynomial add(BasicPolynomial o)
	{
		if(o.coefs.length>this.coefs.length)
			return o.add(this);
		BasicPolynomial bp=this.clone();
		for(int i=0;i<coefs.length;i++)
			coefs[i]+=o.coefs[i];
		return bp;
	}
	public BasicPolynomial sub(BasicPolynomial o)
	{
		if(o.coefs.length>this.coefs.length)
			return o.add(this);
		BasicPolynomial bp=this.clone();
		for(int i=0;i<coefs.length;i++)
			coefs[i]+=o.coefs[i];
		return bp;
	}
	public Function getCoeff(char ch,int pwr)
	{
		if(var==ch && pwr<coefs.length)
			return new Function(new Constant(coefs[pwr]));
		else
			return new Function(Constant.ZERO);
	}
	public Function getCoeff(char[] ch,int[] pwr)
	{
		return new Function(Constant.ZERO);
	}
	public double getVal(char ch,double val,double def) {return ((var==ch)?getVal(val):0);}

/*	public Calculus getClone()
	{
		return new BasicPolynomial(coefs,var);
	}*/
	public void reduce() {return;}
	public boolean isConstant()
	{
		if(coefs.length<=1)
			return true;
		for(int i=1;i<coefs.length;i++)
		{
			if(coefs[i]!=0)
				return false;
		}
		return true;
	}
	public char[] getVars() {return new char[] {getVar()};};
	public void setVar(char ch,char rep)
	{
		if(var==ch)
			var=rep;
	}
	public boolean isMultiTermed()
	{
		if(coefs.length<=1)
			return false;
		return true;
	}
	public BasicPolynomial DIVI(double d) {return MUL(1/d);}
	public BasicPolynomial negate() {return MUL(-1);}
	public BasicPolynomial MUL(double d)
	{
		BasicPolynomial ret=this.clone();
		for(int i=0;i<ret.coefs.length;i++)
			ret.coefs[i]*=d;
		return ret;
	}
	public BasicPolynomial clone() {return new BasicPolynomial(coefs,var);}
	public FuncBlock partialPut(char[] vars,FuncBlock[] vals)
	{
		for(int i=0;i<vars.length;i++)
		{
			if(vars[i]==var)
			{
				Function ret=new Function();
				for(int j=0;j<coefs.length;j++)
					ret.append(new Term(vals[i].clone(),new Constant(coefs[j]),Constant.ONE,new Constant(j)));
				return ret;
			}
		}
		return this.clone();
	}
	public Term partialPut(char c,double v)
	{
		if(var==c)
			return new Term(new Constant(getVal(v)));
		else
			return new Term(this.clone());
	}
	
	public double[] getPwr(char c)
	{
		if(c!=var)
			return new double[] {0};
		double[] pwrs=new double[coefs.length];
		for(int i=0;i<pwrs.length;i++)
			pwrs[i]=i;
		return pwrs;
	}
	public BasicPolynomial getIntegration(char resp)throws IntegrationException
	{
		if(var!=resp)
			throw new IntegrationException("Error: Variable does not match polynomial.");
		double[] ncoefs=new double[coefs.length+1] ;
		for(int i=0;i<coefs.length;i++)
			ncoefs[i+1]=coefs[i]/(i+1);
		return new BasicPolynomial(ncoefs,var);
	}
	public BasicPolynomial getIntegration(char resp,double cons)throws IntegrationException
	{
		if(var!=resp)
			throw new IntegrationException("Error: Variable does not match polynomial.");
		double[] ncoefs=new double[coefs.length+1] ;
		for(int i=0;i<coefs.length;i++)
			ncoefs[i+1]=coefs[i]/(i+1);
		ncoefs[0]=cons;
		return new BasicPolynomial(ncoefs,var);
	}
	public Constant getIntegration(char resp,int ul,int ll)throws IntegrationException
	{
		if(var!=resp)
			throw new IntegrationException("Error: Variable does not match polynomial.");
		double[] ncoefs=new double[coefs.length+1] ;
		for(int i=0;i<coefs.length;i++)
			ncoefs[i+1]=coefs[i]/(i+1);
		BasicPolynomial bpa=new BasicPolynomial(ncoefs,var);
		return new Constant(bpa.getVal(ul)-bpa.getVal(ll));
	}
}
