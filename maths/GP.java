package maths;
import commons.*;

public class GP implements Progressive
{
	double a,r;
	
	public GP(double[] v)
	{
		if(v.length<3)
			throw new IncompleteInformationForGPException();
		a=v[0];
		r=v[1]/v[0];
	}
	public GP(double A,double R)
	{
		a=A; r=R;
	}
	public GP(double n)
	{
		this(1D,n);
	}
	
	public double sum(int n)
	{
		double sum=0;
		for(int i=0;i<n;i++)
			sum+=a*Math.pow(r,i);
		return sum;
	}
	public double prod(int n)
	{
		double prod=1;
		for(int i=0;i<n;i++)
			prod*=a*Math.pow(r,i);
		return prod;
	}
	public double getTerm(int n)
	{
		return a*Math.pow(r,n);
	}
	public double getFirstTerm()
	{
		return a;
	}
	public boolean isAP()
	{
		return false;
	}
	public boolean isGP()
	{
		return true;
	}
	public Progressive getProgression()
	{
		return this;
	}
	public double tendsTo()
	{
		return sumToInfinity();
	}
	public double sumToInfinity()
	{
		if(r>=1)
			return Maths.Infinite;
		else
			return Maths.round(a/(1D-r),4);
	}
	public void printSeries(int n,String col)
	{
		X.sTerm(col);
		X.sop(Maths.perfect(a));
		for(int i=1;i<n;i++)
			X.sop(", "+Maths.perfect(a*Math.pow(r,i)));
		X.sopln();
		X.sTerm("white");
	}
	public void printSeries(int n)
	{
		printSeries(n,"white");
	}
	public boolean order()
	{
		return r>1;
	}
	public boolean fixed()
	{
		return r==1;
	}
	public boolean hasTerm(double t)
	{
		double rd=Maths.round(Maths.log(t/a,r),4);
		if((int)rd==rd)
		{
			if(order())
				return Math.abs(t)>=Math.abs(a);
			else
				return Math.abs(t)<=Math.abs(a);
		}
		else
			return false;
	}
	public void present(char var)
	{
		X.sop(a+""+r+"^("+var+"-1)");
	}
}
class IncompleteInformationForGPException extends RuntimeException
{
	public IncompleteInformationForGPException()
	{
		X.sopln("Error occured. Information provided for generation GP was insufficient.","Red");
	}
}
