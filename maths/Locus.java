package maths;
import maths.*;
import commons.*;
import java.awt.*;

public class Locus
{
	public maths.Point[] points; //Path
	public static final Locus STD=new Locus(new maths.Point[] {new maths.Point(0,0),new maths.Point(Maths.Infinite,Maths.Infinite)});
	
	protected Locus() {}
	public Locus(maths.Point[] pts)
	{
		points=pts;
	}
	public Locus(java.awt.Point[] pts)
	{
		this(Maths.switchPoints(pts));
	}
	
	public java.awt.Point[] getPointsAWT()
	{
		return Maths.switchPoints(points);
	}
	public maths.Point[] getPointsMATHS()
	{
		return points;
	}
	public maths.Point[] getPoints()
	{
		return getPointsMATHS();
	}
	public java.awt.Point getFirstPointAWT()
	{
		return Maths.switchPoint(points[0]);
	}
	public maths.Point getFirstPointMATHS()
	{
		return points[0];
	}
	public maths.Point getFirstPoint()
	{
		return getFirstPointMATHS();
	}
	public static int minX(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int min=(int)lc.points[0].x;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.x<min)
				min=(int)pt.x;
		}
		return min;
	}
	public static int minY(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int min=(int)lc.points[0].y;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.y<min)
				min=(int)pt.y;
		}
		return min;
	}
	public static int maxX(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int max=(int)lc.points[0].x;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.x>max)
				max=(int)pt.x;
		}
		return max;
	}
	public static int maxY(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int max=(int)lc.points[0].y;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.y>max)
				max=(int)pt.y;
		}
		return max;
	}
	public static int rangeX(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int max=(int)lc.points[0].x;
		int min=(int)lc.points[0].x;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.x>max)
				max=(int)pt.x;
			if((int)pt.x<min)
				min=(int)pt.x;
		}
		return max-min;
	}
	public static int rangeY(Locus lc)
	{
		if(lc.points.length<=0)
			return 0;
		int max=(int)lc.points[0].y;
		int min=(int)lc.points[0].y;
		for(maths.Point pt : lc.points)
		{
			if((int)pt.y>max)
				max=(int)pt.y;
			if((int)pt.y<min)
				min=(int)pt.y;
		}
		return max-min;
	}
	public Rectangle getBounds()
	{
		return new Rectangle(minX(this),minY(this),rangeX(this),rangeY(this));
	}
}
