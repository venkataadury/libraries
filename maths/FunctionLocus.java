package maths;
import commons.*;
import java.awt.Point;
import maths.functions.*;

public class FunctionLocus
{
	protected Function fxy=new Function(new Constant(0));
	protected FunctionLocus() {}
	public FunctionLocus(maths.Point[] sat)
	{
		double[] ins=new double[sat.length];
		double[] outs=new double[sat.length];
		int K=0;
		for(maths.Point p : sat)
		{
			ins[K]=p.x;
			outs[K]=p.y;
		}
		FunctionBuilder fb=new FunctionBuilder(ins,outs);
		fxy=fb.solve();
		fxy.append(new Term(new Constant(-1),new Mirror('y')));
	}
	public FunctionLocus(java.awt.Point[] sat)
	{
		this(Maths.switchPoints(sat));
	}
	public FunctionLocus(Function f)
	{
		fxy=f;
	}
	public FunctionLocus(String in)
	{
		this(new Function(in));
	}
	
	public void printLocus()
	{
		fxy.printFx(new char[] {'x','y'});
	}
	
	public Locus getBasicLocus(int limX1,int limX2)
	{
		java.awt.Point[] locs=new java.awt.Point[0];
		for(int i=limX1;i<=limX2;i++)
			locs=appendPoints(locs,i,getY(i));
		return new Locus(locs);
	}
	
	public double[] getY(double xV)
	{
		double lim=Math.abs(fxy.getConstant().getVal());
		double lim2=Math.abs(xV); lim2=(lim2>1)?Math.pow(lim2,3):lim2;
		lim=Math.max(lim,lim2);
		return fxy.getAllRoots(new char[] {'x'},new double[] {xV},'y',-lim,+lim,Function.accuracy);
	}
	public double[] getX(double yV)
	{
		double lim=Math.abs(fxy.getConstant().getVal());
		double lim2=Math.abs(yV); lim2=(lim2>1)?Math.pow(lim2,3):lim2;
		lim=Math.max(lim,lim2);
		return fxy.getAllRoots(new char[] {'y'},new double[] {yV},'x',-lim,+lim,Function.accuracy);
		//return fd.getAllRoots(new char[0],new double[0],'x',);
	}
	
	public boolean satisfies(maths.Point pt)
	{
		return (fxy.getVal(new char[] {'x','y'},new double[] {pt.x,pt.y})==0);
	}
	
	public static java.awt.Point[] appendPoints(java.awt.Point[] pa,int xP,double[] yP)
	{
		java.awt.Point[] ret=new java.awt.Point[pa.length+yP.length];
		for(int i=0;i<pa.length;i++)
			ret[i]=pa[i];
		for(int i=0;i<yP.length;i++)
			ret[i+pa.length]=new java.awt.Point(xP,(int)yP[i]);
		return ret;
	}
}
