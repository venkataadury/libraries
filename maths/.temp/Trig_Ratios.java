package maths;
import commons.*;

public class Trig_Ratios extends Object implements Operatable
{
	int id; //1-sin,2-cos,3-tan,4-cot,5-sec,6-cosec
	char v=(char)0;
	Operatable cval=new Mirror();
	public static final Trig_Ratios SIN=new Trig_Ratios("sin",Symbols.THETA);
	public static final Trig_Ratios COS=new Trig_Ratios("cos",Symbols.THETA);
	public static final Trig_Ratios TAN=new Trig_Ratios("tan",Symbols.THETA);
	public static final Trig_Ratios COT=new Trig_Ratios("cot",Symbols.THETA);
	public static final Trig_Ratios SEC=new Trig_Ratios("sec",Symbols.THETA);
	public static final Trig_Ratios COSEC=new Trig_Ratios("cosec",Symbols.THETA);
	
	public Trig_Ratios() {}
	public Trig_Ratios(int i,char c)
	{
		this(i);
		v=c;
		cval=new Mirror(c);
	}
	public Trig_Ratios(int i,Operatable f)
	{
		this(i);
		cval=f;
		v=cval.getVar();
	}
	public Trig_Ratios(int i) {id=i;}
	public Trig_Ratios(String i)
	{
		if(i.indexOf("sin")!=-1)
			id=1;
		else if(i.indexOf("cos")!=-1 && i.indexOf("cosec")==-1)
			id=2;
		else if(i.indexOf("tan")!=-1)
			id=3;
		else if(i.indexOf("cot")!=-1)
			id=4;
		else if(i.indexOf("sec")!=-1 && i.indexOf("cosec")==-1)
			id=5;
		else if(i.indexOf("cosec")!=-1)
			id=6;
		else
			throw new RuntimeException();
		if(i.indexOf('(')!=-1)
		{
			String f=Y.cut(i,'(',2).replace(")","");
			cval=new Function(f);
		}
		else
			cval=new Mirror((char)0);
	}
	public Trig_Ratios(String i,char c)
	{
		this(i);
		v=c;
	}
	public double getValue(double d)
	{
		d=Math.toRadians(d);
		switch(id)
		{
			case 1:
				return Math.sin(d);
			case 2:
				return Math.cos(d);
			case 3:
				return Math.tan(d);
			case 4:
				return 1/(Math.tan(d));
			case 5:
				return 1/(Math.cos(d));
			case 6:
				return 1/(Math.sin(d));
			default:
				throw new RuntimeException();
		}
	}
	public double getVal(double d)
	{
		return getValue(cval.getVal(d));
	}
	public double getVal(char[] vrs,double[] vls)
	{
		return getValue(cval.getVal(vrs,vls));
	}
	public char getVar()
	{
		return cval.getVar();
	}
	public void typeExpr(String col)
	{
		switch(id)
		{
			case 1:
				X.sop("sin (",col);
				break;
			case 2:
				X.sop("cos (",col);
				break;
			case 3:
				X.sop("tan (",col);
				break;
			case 4:
				X.sop("cot (",col);
				break;
			case 5:
				X.sop("sec (",col);
				break;
			case 6:
				X.sop("cosec (",col);
				break;
			default:
				throw new RuntimeException();
		}
		cval.typeExpr(col);
		X.sop(")",col);
	}
	public boolean isConst()
	{
		return false;
	}
	public double getPwr()
	{
		return 1;
	}
	public double getCoef()
	{
		return 1;
	}
	public void addCoef(double co)
	{
		return;
	}
	public void printExpr(String col)
	{
		typeExpr(col);
		X.sopln();
	}
	public void printExpr()
	{
		printExpr("white");
	}
	public void typeExpr()
	{
		typeExpr("white");
	}
	public void setVar(char C)
	{
		v=C;
		cval.setVar(C);
	}
	public Operatable differentiate(char resp)
	{
		if(cval.getVar()!=resp && cval.getVar()!=(char)0)
			return Constant.ZERO;
		/*X.sopln((new Trig_Ratios(2,cval)).getVal(30));
		X.sopln((cval.differentiate(resp)).getVal(0));*/
		Operatable cval=(this.cval).getClone();
    switch(id)
    {
			case 1:
				Term r= new Term(new Trig_Ratios(2,cval),cval.differentiate(resp));
				return r;
			case 2:
				return (new Term(new Trig_Ratios(1,cval),new Constant(-1))).mul(cval.differentiate(resp));
			case 3:
				return new Term(new Trig_Ratios(5,cval),Constant.UNITY,new Constant(2)).mul(cval.differentiate(resp));
			case 4:
				return new Term(new Trig_Ratios(6,cval),new Constant(-1),new Constant(2)).mul(cval.differentiate(resp));
			case 5:
				return new Term(new Trig_Ratios(5,cval),new Trig_Ratios(3,cval)).mul(cval.differentiate(resp));
			case 6:
				Term cs=(new Term(new Trig_Ratios(6,cval),new Constant(-1)));
				return new Term(cs,new Trig_Ratios(4,cval)).mul(cval.differentiate(resp));
			default:
				return null;
		}
	}
	public Operatable integrate(char resp)
	{
		if(cval.getVar()!=resp && cval.getVar()!=(char)0)
			return new Term(this.getClone(),new Mirror(resp));
		switch(id)
    {
			case 1:
				return new Term(new Term(new Trig_Ratios(2,cval.getClone()),new Constant(-1)),new Reciprocal(cval.getClone().differentiate(resp)));
			case 2:
				return new Term(new Trig_Ratios(1,cval.getClone()),new Reciprocal(cval.getClone().differentiate(resp)));
			case 3:
				return new Term(new Logarithm(new Trig_Ratios(5,cval.getClone())),new Reciprocal(cval.getClone().differentiate(resp))); //Modulus
			case 4:
				return new Term(new Logarithm(new Trig_Ratios(1,cval.getClone())),new Reciprocal(cval.getClone().differentiate(resp))); //Modulus
			case 5:
				return new Term(new Logarithm(new Function(new Operatable[] {new Trig_Ratios(5,cval.getClone()),new Trig_Ratios(3,cval.getClone())})),new Reciprocal(cval.getClone().differentiate(resp))); //Modulus
			case 6:
				return new Term(new Logarithm(new Function(new Operatable[] {new Trig_Ratios(6,cval.getClone()),new Term(new Trig_Ratios(4,cval.getClone()),Constant.MINUS)})),new Reciprocal(cval.getClone().differentiate(resp))); //Modulus
			default:
				return null;
		}
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
	public boolean hasVar(char va)
	{
            return cval.hasVar(va);
	}
	public Trig_Ratios getClone()
	{
		return new Trig_Ratios(id,cval.getClone());
	}
	public Trig_Ratios replace(char c,double v)
	{
		return new Trig_Ratios(id,cval.replace(c,v));
	}
	public Constant getCoef(char c,double val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public Constant getCoef(char[] c,double[] val)throws InconsistantCoefException
	{
		throw new InconsistantCoefException();
	}
	public double[] getPwr(char c)
	{
		if(v!=c)
			return new double[] {0};
		else
			throw new InconsistantCoefException();
	}
}
