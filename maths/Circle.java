package maths;
import commons.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import draw.AWT;
public class Circle implements Shape
{
	protected int x,y;
	public double rad;
	public double circ;
	public final java.awt.Point centre,ZERO_POINT;
	public final Locus path;
	private int[] ins=new int[6];
	public Circle(java.awt.Point ce,double r)
	{
		centre=ce;
		rad=r;
		x=ce.x-(int)r;
		y=ce.y-(int)r;
		path=new Locus(calcPath());
		circ=2D*Math.PI*rad;
		setup();
		ZERO_POINT=new java.awt.Point(centre.x,centre.y-(int)rad);
	}
	public Circle(maths.Point ce,double r)
	{
		this(Maths.switchPoint(ce),r);
	}
	public maths.Point[] calcPath()
	{
		maths.Point[] ap=new maths.Point[0];
		int a=(int)centre.x,b=(int)centre.y;
		maths.Point P1,P2;
		/*
			p=point current
			rad=dist(p,centre)
			rad=Math.sqrt((x1-x2)^2 + (y1-y2)^2)
			rad*rad=(c.x-p.x)^2 + (c.y-p.y)^2
			rad*rad-K=(c.x-p.x)^2
			
			rad*rad - K=(c.y-p.y)^2
		*/
		double rS=rad*rad,rT,rU;
		for(double i=a;i<=a+rad;i+=0.2)
		{
			rT=rS-Math.pow((a-i),2);
			rU=-Math.sqrt(rT)+b;
			ap=AWT.appendPoint(ap,new maths.Point(i,rU));
		}
		for(double i=b;i<=b+rad;i+=0.2)
		{
			rT=rS-Math.pow((b-i),2);
			rU=Math.sqrt(rT)+a;
			ap=AWT.appendPoint(ap,new maths.Point(rU,i));
		}
		for(double i=a;i>=a-rad;i-=0.2)
		{
			rT=rS-Math.pow((a-i),2);
			rU=Math.sqrt(rT)+b;
			ap=AWT.appendPoint(ap,new maths.Point(i,rU));
		}
		for(double i=b;i>=b-rad;i-=0.2)
		{
			rT=rS-Math.pow((b-i),2);
			rU=-Math.sqrt(rT)+a;
			ap=AWT.appendPoint(ap,new maths.Point(rU,i));
		}
		ap=AWT.appendPoint(ap,new maths.Point(a+rad/100D,b-rad));
		return ap;
	}
	private void setup()
	{
		maths.Point start=path.points[0];
		int j=0;
		for(int i=0;i<path.points.length;i++)
		{
			if((int)GLine.distance(start,path.points[i])==(int)this.rad)
			{
				start=path.points[i];
				ins[j++]=i;
			}
		}
	}
	public Locus getLocus()
	{
		return path;
	}
	public boolean contains(int v1,int v2)
	{
		return this.contains((double)v1,(double)v2);
	}
	public PathIterator getPathIterator(AffineTransform at)
	{
		return new CirclePathIterator(this,at);
	}
	public PathIterator getPathIterator(AffineTransform at,double flatness)
	{
		return new FlatteningPathIterator(this.getPathIterator(at),flatness);
	}
	public boolean contains(Rectangle2D r)
	{
		java.awt.Point pa,pb,pc,pd;
		int xco=(int)r.getX(),yco=(int)r.getY();
		int w=(int)r.getWidth(),h=(int)r.getHeight();
		pa=new java.awt.Point(xco,yco);
		pb=new java.awt.Point(xco+w,yco);
		pc=new java.awt.Point(xco+w,yco+h);
		pd=new java.awt.Point(xco,yco+h);
		return (this.contains(pa) && this.contains(pb) && this.contains(pc) && this.contains(pd));
	}
	public boolean intersects(Rectangle2D r)
	{
		java.awt.Point pa,pb,pc,pd;
		int xco=(int)r.getX(),yco=(int)r.getY();
		int w=(int)r.getWidth(),h=(int)r.getHeight();
		pa=new java.awt.Point(xco,yco);
		pb=new java.awt.Point(xco+w,yco);
		pc=new java.awt.Point(xco+w,yco+h);
		pd=new java.awt.Point(xco,yco+h);
		return (this.contains(pa) || this.contains(pb) || this.contains(pc) || this.contains(pd) || r.contains(centre));
	}
	public boolean contains(double xc,double yc,double wi,double he)
	{
		return this.contains(new Rectangle((int)xc,(int)yc,(int)wi,(int)he));
	}
	public boolean intersects(double xc,double yc,double wi,double he)
	{
		return this.intersects(new Rectangle((int)xc,(int)yc,(int)wi,(int)he));
	}
	public boolean contains(Point2D pt)
	{
		return this.contains(pt.getX(),pt.getY());
	}
	public boolean contains(double v1,double v2)
	{
		maths.Point mp=new maths.Point(v1,v2);
		return GLine.distance(mp,Maths.switchPoint(centre))<=rad;
	}
	public boolean contains(maths.Point pt)
	{
		return this.contains(Maths.switchPoint(pt));
	}
	public Rectangle2D getBounds2D()
	{
		return this.getBounds();
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)(centre.x-rad),(int)(centre.y-rad),(int)(2*rad),(int)(2*rad));
	}
	public java.awt.Point oppositePoint(java.awt.Point pt)
	{
		GLine leq=Maths.gLine(centre,pt);
		double dist=GLine.distance(Maths.switchPoint(centre),Maths.switchPoint(pt)); //300
		maths.Point[] pti=leq.pointsOnLine(Maths.switchPoint(centre),dist);
		if(pti[0].approxEquals(Maths.switchPoint(pt)))
			return Maths.switchPoint(pti[1]);
		else
			return Maths.switchPoint(pti[0]);
	}
	//
	public maths.Point pointAtDegreeMaths(int deg)
	{
		return path.points[(path.points.length*(deg%360))/360];
	}
	//
	public maths.Point oppositePoint(maths.Point pt)
	{
		return Maths.switchPoint(oppositePoint(Maths.switchPoint(pt)));
	}
	public java.awt.Point pointAtDegree(int deg)
	{
		java.awt.Point[] via=path.getPointsAWT();
		int un=via.length/360;
		deg%=360;
		return via[un*deg];
	}
	public java.awt.Point pullBack(java.awt.Point onCirc,int ratx,int raty)
	{
		java.awt.Point[] poss=Maths.switchPoints(GLine.sect(Maths.switchPoint(onCirc),Maths.switchPoint(centre),raty));
		return poss[ratx-1];
	}
	public java.awt.Point accuratePointAtDegree(int deg)
	{
		GLine gl1=Maths.gLine(centre,ZERO_POINT);
		GLine gl2=new GLine(gl1,Maths.switchPoint(centre),deg);
		maths.Point[] pir=gl2.pointsOnLine(Maths.switchPoint(centre),rad);
		if(deg<180)
			return Maths.switchPoint(pir[1]);
		else
			return Maths.switchPoint(pir[0]);
	}
	public maths.Point accuratePointAtDegreeMaths(int deg)
	{
		return Maths.switchPoint(accuratePointAtDegree(deg));
	}
	public void cut(Graphics g,int poL)
	{
		maths.Point pt=path.points[poL];
		cut(g,pt);
	}
	public void cutPoints(Graphics g)
	{
		for(int i : ins)
			cut(g,i);
	}
	public void cut(Graphics g,Point pt)
	{
		if(pt.y>=centre.y)
			g.drawLine((int)pt.x-5,(int)pt.y-5,(int)pt.x+5,(int)pt.y+5);
		else
			g.drawLine((int)pt.x-5,(int)pt.y+5,(int)pt.x+5,(int)pt.y-5);
	}
	public void cutAllPoints(Graphics g)
	{
		for(int i=1;i<=360;i++)
			cut(g,i);
	}
	public void drawDiam(Graphics g,int deg)
	{
		java.awt.Point pt=accuratePointAtDegree(deg);
	}
}

