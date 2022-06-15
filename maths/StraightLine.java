package maths;
import commons.*;
import maths.functions.*;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import draw.Figure;
import draw.UnboundedException;
import draw.UnclosedFigureException;

public class StraightLine extends SecondDegreeCurve implements Figure
{
	protected double M,C;
	private Color col=Color.black;
	
	public StraightLine() {}
	public StraightLine(Point2D p1,Point2D p2)
	{
		M=(p2.getY()-p1.getY())/(p2.getX()-p1.getX());
		C=p2.getY()-M*p2.getX();
		g=M;
		f=-1;
		c=C;
	}
	public StraightLine(Function fx)
	{
		super.assign(new SecondDegreeCurve(fx));
		M=-g/f;
		C=-c/f;
		g=M;
		f=-1;
		c=C;
	}
	public StraightLine(Point2D pt, double slp)
	{
		M=slp;
		C=pt.getY()-M*pt.getX();
	}
	/*public StraightLine(String in)
	{
		Function f1=
	}*/
	public boolean contains(Point2D pt)throws UnclosedFigureException
	{
		throw new UnclosedFigureException();
	}
	public boolean isCurve()
	{
		return false;
	}
	public java.awt.Point getCentre()throws UnboundedException
	{
		throw new UnboundedException();
	}
	public java.awt.Rectangle getBounds()throws UnboundedException
	{
		throw new UnboundedException();
	}
	public void setColor(Color co)
	{
		col=co;
	}
	public Color getColor()
	{
		return col;
	}
	public java.awt.Point[] getAllPoints()throws UnboundedException
	{
		throw new UnboundedException();
	}
	public double getXCo(double y)
	{
		return super.getX(y)[0];
	}
	public double getYCo(double x)
	{
		return super.getY(x)[0];
	}
	public maths.Point getIntersection(StraightLine sl)
	{
		if(M==sl.M)
			return null;
		double xco=(sl.C-C)/(M-sl.M);
		return new maths.Point(xco,this.getYCo(xco));
	}
	public static StraightLine getPerpBisector(Point2D p1,Point2D p2)
	{
		maths.Point p3=maths.Point.midPoint(p1,p2);
		return new StraightLine(p3,-1/(p2.getY()-p1.getY())/(p2.getX()-p1.getX()));
	}
	public static boolean collinear(Point2D p1,Point2D p2,Point2D p3)
	{
		StraightLine sl1=new StraightLine(p1,p2);
		double S=sl1.getS(p3);
		return Maths.round(S,5)==0;
	}
}
