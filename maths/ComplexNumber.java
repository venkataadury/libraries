package maths;
import commons.*;
import maths.geom3D.*;

public class ComplexNumber implements Arithmetical<ComplexNumber>
{
	double x,y;
	public static final ComplexNumber ZERO=new ComplexNumber(0,0),UNITY=new ComplexNumber(1,0),IOTA=new ComplexNumber(0,1);
	
	public ComplexNumber() {x=y=0;}
	public ComplexNumber(Number n)
	{	
		x=n.doubleValue();
		y=0;
	}
	public ComplexNumber(double a,double b)
	{
		x=a;y=b;
	}
	public ComplexNumber(ComplexNumber ext)
	{
		this.assign(ext);
	}
	public ComplexNumber(String in)
	{
		in=in.trim().replace("  "," ");
		if(in.indexOf('(')!=-1)
		{
			maths.Point pt=new maths.Point(in);
			x=pt.x; y=pt.y;
		}
		else
		{
			int sC=Strings.countChar(in,' ');
			String temp="";
			x=y=0;
			for(int i=0;i<=sC;i++)
			{
				temp+=Y.cut(in,' ',i+1).trim();
				if(temp.endsWith("i"))
				{
					if(temp.length()==1)
						temp="1"+temp;
					y+=Maths.stringArithmetic(temp.substring(0,temp.length()-1));
					temp="";
				}
				else
					try{x+=Maths.stringArithmetic(temp);temp="";}catch(NumberFormatException e) {continue;}
			}
		}
	}
	
	public void assign(ComplexNumber ext)
	{
		x=ext.x;
		y=ext.y;
	}
	
	public double getModulus()
	{
		return Math.sqrt(x*x+y*y);
	}
	public double getArgument() //In degrees
	{
		return Math.toDegrees(Math.asin(y/getModulus()));
	}
	public void ADD(ComplexNumber n)
	{
		x+=n.x;
		y+=n.y;
	}
	public void SUB(ComplexNumber n)
	{
		x-=n.x;
		y-=n.x;
	}
	public void MUL(double K)
	{
		x*=K;
		y*=K;
	}
	public void DIVI(double K)
	{
		x/=K;
		y/=K;
	}
	public void MUL(ComplexNumber n)
	{
		x=this.x*n.x-n.y*this.y;
		y=this.y*n.x+n.y*this.x;
	}
	public void DIVI(ComplexNumber n)
	{
		MUL(n.reciprocal());
	}
	public ComplexNumber add(ComplexNumber n)
	{
		return new ComplexNumber(x+n.x,y+n.y);
	}
	public ComplexNumber sub(ComplexNumber n)
	{
		return new ComplexNumber(x-n.x,y-n.y);
	}
	public ComplexNumber mul(ComplexNumber n)
	{
		return new ComplexNumber(x*n.x-y*n.y,x*n.y+n.x*y);
	}
	public ComplexNumber divi(ComplexNumber n)
	{
		return new ComplexNumber(this.mul(n.reciprocal()));
	}
	public ComplexNumber reciprocal()
	{
		double X=x*x+y*y;
		return new ComplexNumber(x/X,-y/X);
	}
	public ComplexNumber conjugate()
	{
		return new ComplexNumber(x,-y);
	}
	public ComplexNumber square()
	{
		return this.mul(this);
	}
	public ComplexNumber pwr(int v)
	{
		if(v<0)
			pwr(-v).reciprocal();
		if(v==0)
			return UNITY;
		ComplexNumber nu=this;
		for(int i=1;i<=v;i++)
			nu.MUL(this);
		return nu;
	}
	public ComplexNumber sqr()
	{
		return this.square();
	}
	public String toString()
	{
		return x+" "+((y<0)?"":"+ ")+y+"i";
	}
}

