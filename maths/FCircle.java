package maths;
import commons.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import draw.Figure;
import maths.functions.Function;

public class FCircle extends SecondDegreeCurve implements Figure //,Shape
{
	protected double r=0;
	protected maths.Point O=new maths.Point(0,0);
	private Color  col=Color.black;
	public FCircle() {}
	public FCircle(SecondDegreeCurve sdc)
	{
		if(!sdc.isCircle())
			throw new RuntimeException("Equation is not valid for a circle");
		super.assign(sdc);
		r=Math.sqrt(g*g+f*f-c);
		O=new maths.Point(-g/2,-f/2);
	}
	public FCircle(Function fx)
	{
		this(new SecondDegreeCurve(fx));
	}
	public FCircle(Point2D cent,double rad)
	{
		O=(maths.Point)cent;
		r=rad;
		Function fx=new Function();
		String func="x^2 +y^2 ";
		if(cent.getX()!=0)
		{
			if(cent.getX()>0)
				func+="-"+2*cent.getX()+"x ";
			else
				func+="+"+(-2*cent.getX())+"x ";
		}
		if(cent.getY()!=0)
		{
			if(cent.getY()>0)
				func+="-"+2*cent.getY()+"y ";
			else
				func+="+"+(-2*cent.getY())+"y ";
		}
		double Con=r*r-cent.getX()*cent.getX()-cent.getY()*cent.getY();
		func+=Con+"";
		X.sopln(func,"cyan");
		fx=new Function(func.trim());
		super.assign(new SecondDegreeCurve(fx));
	}
	public FCircle(Point2D p1,Point2D p2,Point2D p3)
	{
		StraightLine sl1=StraightLine.getPerpBisector(p1,p2);
		StraightLine sl2=StraightLine.getPerpBisector(p2,p3);
		O=sl1.getIntersection(sl2);
		r=O.distanceFrom(p1);
	}
	public boolean contains(Point2D pt)
	{
		double V=super.getFunction().getVal(new char[] {'x','y'},new double[] {pt.getX(),pt.getY()});
		return Maths.round(V,4)<0;
	}
	public boolean isCurve()
	{
		if(a!=0 || b!=0)
			return true;
		return false;
	}
	public maths.Point getCentre()
	{
		return O;
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)(O.getX()-r),(int)(O.getY()-r),(int)(2*r),(int)(2*r));
	}
	public java.awt.Point[] getAllPoints()
	{
		return super.getBasicLocus((int)(O.getX()-r),(int)(O.getX()+r)).getPointsAWT();
	}
	public Color getColor()
	{
		return col;
	}
	public void setColor(Color co)
	{
		col=co;
	}
	public double getRadius()
	{
		return r;
	}
}
