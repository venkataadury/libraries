package maths.functions;
import commons.X;
import maths.Maths;

public class Constant extends Number implements Calculus
{
	
	public static final Constant ZERO=new Constant(0),ONE=new Constant(1),MINUS_ONE=new Constant(-1),PI=new PI();
	
	double val=0;
	public Constant() {}
	public Constant(Number v) {val=v.doubleValue();}
	
	//From Number
	public int intValue() {return (int)val;}
	public float floatValue() {return (float)val;}
	public long longValue() {return (long)val;}
	public double doubleValue() {return val;}
	
	//From FuncBlock
	public double getVal() {return val;}
	public double getVal(Number V) {return val;}
	public double getVal(char[] cha,double[] vals) {return val;}
	public double getVal(java.util.HashMap<Character,? extends Number> hm) {return val;}
	public double getVal(char ch,double val,double def) {return val;}
	public char getVar() {return (char)0;}
	public char[] getVars() {return new char[0];}
	public void setVar(char c) {}
	public void setVar(char c1,char c2) {}
	public boolean isMultiTermed() {return false;}
	public boolean isConstant() {return true;}
	public boolean hasVar(char v) {return false;}
	public void reduce() {}
	public Constant negate() {return new Constant(-val);}
	public Constant differentiate(char ch) {return new Constant(0);}
	public Constant MUL(double d) {return new Constant(d*val);}
	public Constant DIVI(double d) {return new Constant(val/d);}
	public Function getCoeff(char ch,int pwr)
	{
		if(pwr==0)
			return new Function(this.clone());
		else
			return new Function(ZERO);
	}
	
	@Override
	public String toString()
	{
		return Maths.perfect(val);
	}
	
	public static PI getPI()
	{
		return new PI();
	}
	public boolean equals(Number o2)
	{
		return val==o2.doubleValue();
	}
	public Constant clone() {return new Constant(val);}
	public Constant partialPut(char[] ch,FuncBlock[] pt) {return this.clone();}
	
	//Calculus
	public Calculus getIntegration(char resp,double con)
	{
		Term t=new Term(new Mirror(resp),this.clone());
		Function f=new Function(t,new Constant(con));
		return f;
	}
	public Constant getDifferentiation(char resp,char[] otV,FuncBlock[] ndvs) {return new Constant(0);}
}
class PI extends Constant
{
	public PI()
	{
		val=Math.PI;
	}
	
	@Override
	public String toString()
	{
		return "Π";
	}
}
