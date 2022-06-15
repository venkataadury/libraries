package maths;
import commons.*;
import maths.functions.Function;
import maths.functions.Constant;

public class Series
{
	Function Tn;
	public static final char[] vs=new char[] {'x','n'};
	public static final double[] v1=new double[] {1,1};
	
	public Series() {Tn=new Function();}
	public Series(double v) {Tn=new Function(new Constant(v));}
	public Series(Function t) {Tn=t;}
	
	public double getFirstTerm()
	{
		return Tn.getVal(vs,v1);
	}
	public double getTerm(int n)
	{
		return Tn.getVal(vs,new double[] {n,n});
	}
	
	public double getSum(int n1,int n2)
	{
		double sum=0;
		for(int i=n1;i<=n2;i++)
			sum+=Tn.getVal(vs,new double[] {i,i});
		return sum;
	}
	public void printSeries(int n1,int n2,String col)
	{
		X.sop(Maths.perfect(getTerm(n1)),col);
		for(int i=n1+1;i<=n2;i++)
			X.sop(Maths.perfect(getTerm(i))+",",col);
		X.sopln();
	}
	public void printSeries(int n,String col)
	{
		printSeries(1,n,col);
	}
	public void printSeries(int n1,int n2)
	{
		printSeries(n1,n2,"white");
	}
	public void printSeries(int n)
	{
		printSeries(1,n);
	}
}
