package maths.functions;
import commons.X;

public interface FuncBlock extends Cloneable
{
	public static final char default_var='x';
	
	public double getVal(Number V);
	public double getVal(char[] vs,double[] vals)throws VariableMismatchException;
	public double getVal(java.util.HashMap<Character,? extends Number> hm)throws VariableMismatchException;
	public char getVar()throws NoSingleVariableException;
	public char[] getVars();
	public void setVar(char v);
	public void setVar(char v1,char v2);
	public boolean hasVar(char v);
	public boolean isMultiTermed();
	public boolean isConstant();
	public void reduce();
	public default FuncBlock negate() {return MUL(-1);}
	public FuncBlock differentiate(char ch)throws NonDifferentiableException; //Partial Differential
	public default FuncBlock MUL(double d) {return new Term(this.clone(),new Constant(d));}
	public default FuncBlock DIVI(double d) {return MUL(1/d);}
	public FuncBlock clone();
	public default Term mul(FuncBlock fb) {return new Term(this,fb);}
	public default Term divi(FuncBlock fb) {return new Term(this,Constant.ONE,fb);}
	
	//Second round of Great Modifications
	public FuncBlock partialPut(char[] vars,FuncBlock[] vals);
	public default FuncBlock partialPut(char ch,FuncBlock val) {return partialPut(new char[] {ch},new FuncBlock[] {val});}
	public double getVal(char ch,double val,double def);
	public Function getCoeff(char ch,int val);
	public default Function getCoeff(char[] vars,int[] val)throws RuntimeException
	{
		FuncBlock f=this;
		if(val.length<vars.length)
			throw new RuntimeException("powers and variables in input are not equal in number");
		for(int i=0;i<vars.length;i++)
			f=f.getCoeff(vars[i],val[i]);
		return new Function(f);
	}
	public default boolean hasCalculus() {return false;}
	
	public static char[] getVarArray(java.util.HashMap<Character,? extends Number> hm)
	{
		Object[] oa=hm.keySet().toArray();
		char[] vals=new char[oa.length];
		for(int i=0;i<oa.length;i++)
			vals[i]=(char)oa[i];
		return vals;
	}
}


class VariableMismatchException extends RuntimeException
{
	public VariableMismatchException(char[] eV,char[] mV)
	{
		X.sop("Variable mismatch in getVal(): Had Variables: ","yellow");
		X.sop(eV,"red");
		X.sop(" , but found values for: ","yellow");
		X.sopln(mV,"red");
		X.sopln("Please add values for missing variables");
	}
	public VariableMismatchException() 
	{
		X.sopln("Variable Mismatch Occuered","red");
	}
}
class NoSingleVariableException extends RuntimeException
{
	public NoSingleVariableException(char[] vars)
	{
		X.sop("Error Occured calling getVar(): More than one variable present. ","yellow");
		X.sopln(vars,"red");
	}
	public NoSingleVariableException()
	{
		X.sopln("Function Block contains more than one variable. Cannot call getVar(). Use getVars()[0]","red");
	}
}
class NonDifferentiableException extends RuntimeException
{
	public NonDifferentiableException()
	{
		X.sopln("Cannot differentiate. Probably Method Error.","red");
	}
	public NonDifferentiableException(String err)
	{
		this();
		X.sopln("Reason: "+err,"yellow");
	}
}
/*
	Modifications:-
		Partial Derivative vs Complete Derivative :  including dy/dx
*/
