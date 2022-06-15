package maths;
import commons.*;
import maths.equations.*;
public class NumberSet
{
	public double init, end;
	public boolean fill=false,given=false;
	public boolean ii=false,ei=false;
	int[] ns=new int[0];
	public NumberSet() {}
	public NumberSet(String set)
	{
	}
	public NumberSet(int[] ns)
	{
		ns=ArrayFx.Ssort(ns);
		given=true;
		fill=false;
		init=ns[0];
		end=ns[ns.length-1];
	}
	public NumberSet(double i,double e)
	{
		init=i;
		end=e;
		fill=true;
		given=false;
	}
	public NumberSet(double i,double e,boolean f)
	{
		this(i,e);
		fill=f;
	}
	public NumberSet(NumberSet ext)
	{
		this.assign(ext);
	}
	public NumberSet(InEquationResult ier1,InEquationResult ier2,boolean trf)
	{
		if(InEquation.areSwappedOps(ier1.op,ier2.op))
		{
			if(Strings.hasChar(ier1.op,'>'))
			{
				init=ier1.FVal;
				ii=ier1.include;
				end=ier2.FVal;
				ei=ier2.include;
			}
			else
			{
				init=ier2.FVal;
				ii=ier2.include;
				end=ier1.FVal;
				ei=ier1.include;
			}
		}
		else
		{
			if(Strings.hasChar(ier1.op,'>'))
			{
				if(ier1.FVal>ier2.FVal)
				{
					init=ier1.FVal;
					ii=ier1.include;
				}
				else
				{
					init=ier2.FVal;
					ii=ier2.include;
				}
				end=Maths.Infinite;
				ei=false;
			}
			else
			{
				init=Maths.nInfinite;
				ii=false;
				if(ier1.FVal<ier2.FVal)
				{
					end=ier1.FVal;
					ei=ier1.include;
				}
				else
				{
					end=ier2.FVal;
					ei=ier2.include;
				}
			}
		}
		fill=trf;
	}
	public NumberSet(InEquationResult ier1,InEquationResult ier2)
	{
		this(ier1,ier2,true);
	}
	public void assign(NumberSet ext)
	{
		init=ext.init;
		end=ext.end;
		fill=ext.fill;
		given=ext.given;
		ns=ext.ns;
	}
	public void represent(String col)
	{
		X.sop("{ x : x ",col);
		X.sop(">"+((ii)?"=":" "),col);
		X.sop(init+"; x ",col);
		X.sop("<"+((ei)?"=":" "),col);
		X.sop(end+"; x ",col);
		X.sop(Symbols.BNGS+" "+((fill)?"R":"I"),col);
		X.sopln(" }",col);
		if((init>end) || (init==end) && (!ii || !ei))
			X.sopln("There seems to be no possible solution to this.","red");
		if(init==end && ei && ii)
			X.sopln("The only one possible solution is "+init,"red");
	}
	public NumberLine drawLine()
	{
		NumberLine nl=new NumberLine(this);
		return nl;
	}
	public void represent()
	{
		represent("white");
	}
}
