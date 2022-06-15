package maths.equations;
import commons.*;
import maths.*;
import maths.functions.*;
import java.io.IOException;

public class Polynomial //implements Balanced
{
	public int maxP=0; //Highest power of x
	public PolyUnit[] uns=new PolyUnit[0];
	public PolyUnit FINAL=null;
	public static final double K=1000D; //3 dec places
	String eqn;
	public static final char var='x';
	
	public Polynomial() {}
	public Polynomial(int mPow)
	{
		maxP=mPow;
		uns=new PolyUnit[mPow];
	}
	public Polynomial(Polynomial ext)
	{
		this.assign(ext);
	}
	public Polynomial(PolyUnit[] pus)
	{
		uns=pus;
		maxP=Calculator.highestPower(pus);
		calcF();
	}
	public Polynomial(String str)
	{
		str=str.trim();
		str=str.replace("-x","-1x").replace("+ ","+").replace("+-"," -").replace("- ","-").replace("+x","+1x");
		eqn=str;
		int c=X.countchar(str,' ');
		uns=new PolyUnit[c+1];
		for(int i=0;i<=c;i++)
			uns[i]=new PolyUnit(Y.cut(str,' ',i+1));
		maxP=Calculator.highestPower(uns);
		calcF();
	}
	public Polynomial(Function fx,int deg)
	{
		uns=new PolyUnit[deg+1];
		for(int i=0;i<deg;i++)
			uns[i]=new PolyUnit(fx.getCoeff('x',i)+"x^"+i);
		maxP=Calculator.highestPower(uns);
		calcF();
	}
	public void assign(Polynomial ext)
	{
		uns=ext.uns;
		maxP=ext.maxP;
		eqn=ext.eqn;
		FINAL=ext.FINAL;
	}
	private void calcF()
	{
		PolyUnit r=uns[0];
		for(int i=1;i<uns.length;i++)
			r=Calculator.add(r,uns[i]);
		FINAL=r;
	}
	public static PolyUnit[] appendP(PolyUnit[] pua,PolyUnit pu)
	{
		PolyUnit[] pua2=new PolyUnit[pua.length+1];
		for(int i=0;i<pua.length;i++)
			pua2[i]=pua[i];
		pua2[pua.length]=pu;
		return pua2;
	}
	
	public void printEqn(String col)
	{
		X.sop(uns[0].toShow(),col);
		for(int i=1;i<uns.length;i++)
			X.sop(uns[i].toShowWithSymbol(),col);
		X.sopln();
	}
	public boolean testWith(double v,boolean s,String col)
	{
		return FINAL.equates(v,s,col);
	}
	public boolean testWith(double v,boolean s)
	{
		return testWith(v,s,"white");
	}
	public boolean testWith(double v)
	{
		return testWith(v,false);
	}
	public void printEqn()
	{
		printEqn("white");
	}
	public void printSimplifiedEqn(String col)
	{
		X.sopln(FINAL.toShow(),col);
	}
	public void printSimplifiedEqn()
	{
		printSimplifiedEqn("white");
	}
	public double valueAt0()
	{
		for(Xpc unX : FINAL.pcs)
		{
			if(unX.Xpo==0)
				return unX.co;
		}
		return 0;
	}
	public double valueAt0(double d)
	{
		double a=valueAt0();
		return a-FINAL.DEN*d;
	}
	public double[] solve(boolean s,String col,double val)
	{
		double unVal=valueAt0(val);
		unVal*=K;
		unVal=Math.abs(unVal);
		int[] fcts=Maths.allFactorsOf((int)unVal);
		double cV=0D;
		double[] solns=new double[0];
		boolean equ=false;
		for(int f : fcts)
		{
			cV=(double)f/K;
			if(s)
				X.sopln("Trying with: "+Symbols.POM+Maths.perfect(cV));
			equ=FINAL.equates(cV,s,col);
			X.sopln();
			if(equ)
			{
				if(s)
				{
					X.sopln(Maths.perfect(cV)+" = "+Maths.perfect(cV),col);
					X.sopln("true",col);
				}
				solns=ArrayFx.append(solns,cV);
				break;
			}
			else if(FINAL.equates(-cV,s,col))
			{
				if(s)
				{
					X.sopln(Maths.perfect(-cV)+" = "+Maths.perfect(-cV),col);
					X.sopln("true",col);
				}
				solns=ArrayFx.append(solns,-cV);
				break;
			}
			else
			{
				X.sopln(Maths.perfect(FINAL.valueWith(cV))+" = "+Maths.perfect(cV),col);
				X.sopln("false",col);
			}
		}
		if(solns.length==0)
		{
			X.sopln("Sorry. No answer could be found by factorization method","red");
			return new double[0];
		} //Change ahead
		return solns;
		/*FINAL.divide
		if(s)
		{
			X.sopln();
			X.sopln(
		}*/
	}
}
