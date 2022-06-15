package maths;
import commons.*;
import java.awt.Point;
import maths.functions.*;

public class SecondDegreeCurve extends FunctionLocus implements Curve
{
	protected double a,b,c,f,g,h;
	protected SecondDegreeCurve() {}
	
	public SecondDegreeCurve(Function fx)
	{
		super(fx);
		a=fx.getCoeff('x',2).getVal(0);
		b=fx.getCoeff('y',2).getVal(0);
		h=fx.getCoeff(new char[] {'x','y'},new int[] {1,1}).getVal(0);
		f=fx.getCoeff('y',1).getVal(0)/2;
		g=fx.getCoeff('x',1).getVal(0)/2;
		c=fx.getCoeff('x',0).getVal(0);
	}
	public boolean isLine()
	{
		return (a==0 && b==0 && h==0);
	}
	public boolean isCircle()
	{
		return (a==b && a!=0 && h==0);
	}
	public boolean isParabola()
	{
		return (!(a==0 && b==0) && !(a!=0 && b!=0) && h*h==a*b);
	}
	public boolean isHyperbola() //TODO: Change
	{
		return a*b<0;
	}
	public boolean isEllipse()
	{
		return (!(isCircle() || isParabola() || isHyperbola() || isLine()));
	}
	
	protected void assign(SecondDegreeCurve sdc)
	{
		this.a=sdc.a;
		this.b=sdc.b;
		this.c=sdc.c;
		this.g=sdc.g;
		this.f=sdc.f;
		this.h=sdc.h;
	}
	public FunctionLocus getLocus()
	{
		return new FunctionLocus(getFunction());
	}
	public Function getFunction()
	{
		Function r=new Function();
		if(a!=0)
			r.append(new Term(new Mirror('x'),new Constant(a),new Constant(2)));
		if(b!=0)
			r.append(new Term(new Mirror('y'),new Constant(b),new Constant(2)));
		if(g!=0)
			r.append(new Term(new Mirror('x'),new Constant(2*g),Constant.ONE));
		if(f!=0)
			r.append(new Term(new Mirror('y'),new Constant(2*f),Constant.ONE));
		if(h!=0)
			r.append(new Term(new Constant(2*h).mul(new Mirror('x')),new Mirror('y')));
		if(c!=0)
		r.append(new Constant(c));
		return r;
	}
	
	
	public void printEqn()
	{
		getFunction().printFx();
	}
	public void printType(String col)
	{
		if(isLine())
			X.sopln("Straight Line",col);
		else if(isParabola())
			X.sopln("Parabola",col);
		else if(isHyperbola())
			X.sopln("Hyperbola",col);
		else if(isCircle())
			X.sopln("Circle",col);
		else if(isEllipse())
			X.sopln("Ellipse",col);
		else
			X.sopln("None of these",col);
	}
	public void printType()
	{
		printType("white");
	}
	/*public SecondDegreeCurve reconstruct()
	{
	}*/
	public double getS(java.awt.geom.Point2D pt)
	{
		return getFunction().getVal(new char[] {'x','y'},new double[] {pt.getX(),pt.getY()});
	}
}
