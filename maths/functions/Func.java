package maths.functions;

public interface Func extends Calculus
{
	//public Function getFunction(char[] vars,double[] vals);
	public void put(double val,String fN);
	public void put(double val);
	public void printFx(String fN);
	public void printFx();
	public Function addTerm(FuncBlock fb);
	public void append(FuncBlock fb);
}
