package maths;
import commons.*;

public class AP implements Progressive
{
	double a,d;
	
	public AP(double[] v)throws IncompleteInformationForAPException
	{
		if(v.length<3)
			throw new IncompleteInformationForAPException();
		a=v[0];
		d=v[1]-v[0];
	}
	public AP(double A,double D)
	{
		a=A; d=D;
	}
	public AP(double n)
	{
		this(1D,n);
	}
	//public AP(AP giv,Operation o)
	
	public double sum(int n)
	{
		double sum=0;
		for(int i=0;i<n;i++)
			sum+=(a+i*d);
		return sum;
	}
	public double prod(int n)
	{
		double prod=1;
		for(int i=0;i<n;i++)
			prod*=(a+i*d);
		return prod;
	}
	public double getTerm(int n)
	{
		return a+(n-1)*d;
	}
	public double getFirstTerm()
	{
		return a;
	}
	public boolean isAP()
	{
		return true;
	}
	public boolean isGP()
	{
		return false;
	}
	public Progressive getProgression()
	{
		return this;
	}
	public void printSeries(int n,String col)
	{
		X.sTerm(col);
		X.sop(Maths.perfect(a));
		for(int i=1;i<n;i++)
			X.sop(", "+Maths.perfect(a+i*d));
		X.sopln();
		X.sTerm("white");
	}
	public void printSeries(int n)
	{
		printSeries(n,"white");
	}
	public boolean order()
	{
		return d>0;
	}
	public boolean fixed()
	{
		return d==0;
	}
	public boolean hasTerm(double t)
	{
		t-=a;
		if(order())
			return (Maths.round(t,4)%Maths.round(d,4)==0 && t+a>=a);
		else
			return (Maths.round(t,4)%Maths.round(d,4)==0 && t+a<=a);
	}
	public void present(char var)
	{
		X.sop(Maths.perfect(a)+"+ ("+var+"-1)d");
	}
}
class IncompleteInformationForAPException extends RuntimeException
{
	public IncompleteInformationForAPException()
	{
		X.sopln("Error occured. Information provided for generation AP was insufficient.","Red");
	}
}
