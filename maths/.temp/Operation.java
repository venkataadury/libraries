package maths;
import commons.*;

public class Operation extends Maths implements Operatable
{
	double add,mul,pwr; // 1,2,3
	int[] pri=new int[] {3,2,1};
	char v=(char)0;
	
	public Operation() {add=0;mul=1;pwr=1;}
	public Operation(char c) {this(); v=c;}
	public Operation(double a)
	{
		this();
		add=a;
	}
	public Operation(double a,char c) {this(a); v=c;}
	public Operation(double a,double m)
	{
		this();
		add=a; mul=m;
	}
	public Operation(double a,double m,char c) {this(a,m); v=c;}
	public Operation(double a,double m,double p)
	{
		this();
		add=a; mul=m; pwr=p;
	}
	public Operation(double a,double m,double p,char c) {this(a,m,p); v=c;}
	
	public void setPri(int l,int v)
	{
		int ind=-1;
		for(int i=0;i<pri.length;i++)
		{
			if(pri[i]==v)
			{
				ind=i;
				break;
			}
		}
		int a=pri[ind];
		pri[ind]=pri[l];
		pri[l]=v;
	}
	public void setPri(int[] prio)
	{
		pri=prio;
	}
	
	public double getVal(double xv)
	{
		for(int i=0;i<pri.length;i++)
		{
			if(pri[i]==1)
				xv+=add;
			if(pri[i]==2)
				xv*=mul;
			if(pri[i]==3)
				xv=Math.pow(xv,pwr);
		}
		return xv;
	}
	public double getVal(double xv,double powr)
	{
		for(int i=0;i<pri.length;i++)
		{
			if(pri[i]==1)
			{
				xv+=add; //Correction
			}
			if(pri[i]==2)
				xv*=Math.pow(mul,powr);
			if(pri[i]==3)
				xv=Math.pow(xv,pwr); //Corr
		}
		return xv;
	}
	public boolean isConst()
	{
		return false;
	}
	public double getVal(char[] vrs,double[] vls)
	{
		if(v==(char)0)
			return getVal((vls.length!=0)?vls[0]:0);
		for(int i=0;i<vrs.length;i++)
		{
			if(vrs[i]==v)
				return getVal(vls[i]);
		}
		return getVal(0);
	}
	public char getVar()
	{
		return v;
	}
	public double getPwr()
	{
		return pwr;
	}
	public void printExpr(String col,char var)
	{
		X.sTerm(col);
		present(var);
		X.sopln();
		X.sTerm("white");
	}
	public void printExpr(char var)
	{
		printExpr("white",var);
	}
	public void printExpr()
	{
		printExpr("white",(v!=(char)0)?v:'x');
	}
	public void typeExpr(String col)
	{
		X.sTerm(col);
		present((v==(char)0)?'x':v);
		X.sTerm("white");
	}
	public void printExpr(String col)
	{
		printExpr(col,(v!=(char)0)?v:'x');
	}
	public void present(char c)
	{
		String str=""+c;
		for(int i=0;i<pri.length;i++)
		{
			if(pri[i]==1 && add!=0)
				str+="+"+Maths.perfect(add); //.equals("0")?"":Maths.perfect(add));
			if(pri[i]==2 && mul!=1)
				str=Maths.perfect(mul)+"("+str+")";
			if(pri[i]==3 && pwr!=1)
				str="("+str+")^"+Maths.perfect(pwr);
		}
		X.sop(str);
	}
	public void addCoef(double co)
	{
		mul+=co;
	}
	public double getCoef()
	{
		return mul;
	}
	public void present()
	{
		present('x');
	}
	public void setVar(char va)
	{
		v=va;
	}
	public Operatable differentiate(char resp)throws DifferentiationException
	{
		throw new DifferentiationException();
	}
	public Operatable integrate(char resp)throws IntegrationException
	{
		throw new IntegrationException();
	}
	public Operatable integrate(char resp,double k)throws IntegrationException
	{
		throw new IntegrationException();
	}
	public Constant integrate(char resp,double i,double f)throws IntegrationException
	{
		throw new IntegrationException();
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
            return new Term(this,o,Constant.UNITY);
	}
	public Operatable divi(Operatable o)
	{
            return new Term(this,new Reciprocal(o),Constant.UNITY);
	}
	public boolean hasVar(char va)
	{
            return (v==va);
	}
	public Operation getClone()
	{
		Operation r= new Operation(add,mul,pwr);
		r.setPri(pri);
		return r;
	}
	public Operatable replace(char v,double va) {
		X.sopln("replace() not supported in Operation class");
		return null;
	}
	public Constant getCoef(char c,double val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public Constant getCoef(char[] c,double[] val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public double[] getPwr(char c)throws InconsistantCoefException
	{
		/*if(!fact.hasVar(c))
			return new double[] {0};
		else*/
			throw new InconsistantCoefException();
	}
}
