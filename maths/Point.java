package maths;
import commons.*;
import java.io.*;
import java.awt.geom.Point2D;
public class Point extends java.awt.geom.Point2D
{
	public double x,y;
	public static int[] origin=new int[] {0,0};
	public char value=(char)0;
	public static Graph g1=new Graph(100,100);
	public Point() {x=origin[0]; y=origin[1];};
	public Point(int xco,int yco)
	{
		x=(double)xco; y=(double)yco;
	}
	public Point(double xco,double yco)
	{
		x=xco; y=yco;
	}
	public Point(Point2D ext)
	{
		x=ext.getX();
		y=ext.getY();
	}
	public Point(String s) // (x,y)
	{
		x=X.dpd(Y.cut(s,',',1).substring(1));
		y=X.dpd(Y.cut(Y.cut(s,',',2),')',1));
	}
	public Point(double[] p)
	{
		if(g1.isValidPoint(p))
		{
			x=p[0];
			y=p[1];
		}
		else
		{
			X.sepln("Invalid Point given");
			x=0;y=0;
		}
	}
	public Point(int[] p)
	{
		if(g1.isValidPoint(p))
		{
			x=p[0];
			y=p[1];
		}
		else
		{
			X.sepln("Invalid Point given");
			x=0;y=0;
		}
	}
	public double[] getPoint()
	{
		double[] pset=new double[] {x,y};
		return pset;
	}
	public void setLocation(double Xv,double Yv)
	{
		x=Xv;y=Yv;
	}
	public double getY()
	{
		return y;
	}
	public double getX()
	{
		return x;
	}
	public String pointValue()
	{
		String anss="(";
		boolean f1=false,f2=false;
		int x1=0,y1=0;
		String ans="";
		if(X.isint(x))
		{
			f1=true;
			x1=(int)x;
		}
		if(X.isint(y))
		{
			f2=true;
			y1=(int)y;
		}
		ans=(f1)?""+x1:""+x;
		ans+=',';
		ans+=(f2)?""+y1:""+y;
		anss+=(ans);
		anss+=")";
		return anss;
	}
	public String toString()
	{
		return pointValue();
	}
	public void printPoint()
	{
		X.sop("(");
		boolean f1=false,f2=false;
		int x1=0,y1=0;
		String ans="";
		if(X.isint(x))
		{
			f1=true;
			x1=(int)x;
		}
		if(X.isint(y))
		{
			f2=true;
			y1=(int)y;
		}
		ans=(f1)?""+x1:""+x;
		ans+=',';
		ans+=(f2)?""+y1:""+y;
		X.sop(ans);
		X.sopln(")");
	}
	public boolean approxEquals(Point p2)
	{
		return ((int)x==(int)p2.x && (int)y==(int)p2.y);
	}
	public static Point midPoint(Point2D p1,Point2D p2)
	{
		return new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2);
	}
	public double distanceFrom(Point2D p2)
	{
		return Math.sqrt((x-p2.getX())*(x-p2.getX())+(y-p2.getY())*(y-p2.getY()));
	}
}
