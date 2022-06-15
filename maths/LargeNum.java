package maths;
import commons.*;

public class LargeNum extends java.lang.Number
{
	private double mVal=0;
	private int pwr=0;
	
	
	public LargeNum() {}
	public LargeNum(double Val)
	{
		mVal=Val;
		fixVal();
	}
	public LargeNum(double Val,int p)
	{
		mVal=Val;
		pwr=p;
		fixVal();
	}
	
	private void fixVal()
	{
		while(mVal>=10)
		{
			mVal/=10.0;
			pwr++;
		}
		while(mVal<1)
		{
			mVal*=10.0;
			pwr--;
		}
	}
	public String toString()
	{
		return Maths.perfect(mVal)+((pwr==0)?"":" x10^"+pwr);
	}
	
	public int intValue()
	{
		return (int)(mVal*Math.pow(10,pwr));
	}
	public float floatValue()
	{
		return (float)(mVal*Math.pow(10,pwr));
	}
	public long longValue()
	{
		return (long)(mVal*Math.pow(10,pwr));
	}
	public double doubleValue()
	{
		return (mVal*Math.pow(10,pwr));
	}
	
	public LargeNum NEG()
	{
		return new LargeNum(-mVal,pwr);
	}
	public void ADD(LargeNum n)
	{
		mVal=mVal*Math.pow(10,pwr-n.pwr)+n.mVal;
		pwr=n.pwr;
		fixVal();
	}
	public void ADD(Number n)
	{
		mVal=n.doubleValue();
		fixVal();
	}
	public void SUB(LargeNum n)
	{
		this.ADD(n.NEG());
	}
	public void SUB(Number n)
	{
		this.ADD(-n.doubleValue());
	}
	public void MUL(LargeNum n)
	{
		mVal*=n.mVal;
		pwr+=n.pwr;
		fixVal();
	}
	public void MUL(Number n)
	{
		mVal*=n.doubleValue();
		fixVal();
	}
	public void DIVI(LargeNum n)
	{
		mVal/=n.mVal;
		pwr-=n.pwr;
		fixVal();
	}
	
	public void printNum(String col)
	{
		typeNum();
		X.sopln();
	}
	public void typeNum(String col)
	{
		X.sop(this.toString(),col);
	}
	public void printNum()
	{
		printNum("white");
	}
	public void typeNum()
	{
		typeNum("white");
	}
}