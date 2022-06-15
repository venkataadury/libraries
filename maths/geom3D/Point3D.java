package maths.geom3D;
import commons.X;
import commons.Y;
import commons.Strings;
import maths.Maths;
import maths.GLine;
import maths.Graph;
import java.awt.*;

public class Point3D extends Core3D implements Plottable3D
{
	public double x=0,y=0,z=0;
	public boolean[] frame=new boolean[3]; //x,y,z;
	String nm="P";
	public static final Point3D O = new Point3D(0,0,0);
	public Point3D() {this(0,0,0);}
	public Point3D(double d)
	{
		this(d,0,0);
	}
	public Point3D(double xc,double yc)
	{
		this(xc,yc,0);
	}
	public Point3D(double xc,double yc,double zc)
	{
		x=xc; y=yc; z=zc;
		setFrame();
	}
	public Point3D(String in)
	{
		in=in.replace("(","").replace(")","");
		int c=Strings.countChar(in,',');
		x=X.dpd(Y.cut(in,',',1).trim());
		if(c>=1)
			y=X.dpd(Y.cut(in,',',2).trim());
		if(c>=2)
			z=X.dpd(Y.cut(in,',',3).trim());
	}
	public Point3D(Point3D ext)
	{
		this(ext.x,ext.y,ext.z);
	}
	public void printPoint()
	{
		printPoint("white");
	}
	public void printPoint(String col)
	{
		X.sopln("("+Maths.perfect(x)+","+Maths.perfect(y)+","+Maths.perfect(z)+")",col);
	}
	public void setFrame()
	{
		frame[0]=(x!=0);
		frame[1]=(y!=0);
		frame[2]=(z!=0);
	}
	
	public boolean equals(Point3D p2)
	{
		return (x==p2.x && y==p2.y && z==p2.z);
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double distance(Point3D p2)
	{
		return Math.sqrt(Math.pow(x-p2.x,2)+Math.pow(y-p2.y,2)+Math.pow(z-p2.z,2));
	}
	public double getZ()
	{
		return z;
	}
	public Point3D getPoint(Vector v)
	{
		return new Point3D(x+v.getXComponent().getMag(),y+v.getYComponent().getMag(),z+v.getZComponent().getMag());
	}
	public boolean[] getFrame()
	{
		return NULLFRAME;
	}
	public boolean[] getPlane(Point3D p2)
	{
		boolean[] res=new boolean[3];
		res[0]=(x!=p2.x);
		res[1]=(y!=p2.y);
		res[2]=(z!=p2.z);
		return res;
	}
	
	public Point3D[] getPoints()
	{
		return new Point3D[] {this};
	}
	public boolean isClosed()
	{
		return false;
	}
	public String getName()
	{
		return nm;
	}
	public void setName(String n)
	{
		nm=n;
	}
	
	public Point3D midPoint(Point3D p2)
	{
		return new Point3D((Math.min(x,p2.x)+Math.abs(x-p2.x))/2D,(Math.min(y,p2.y)+Math.abs(y-p2.y))/2D,(Math.min(z,p2.z)+Math.abs(z-p2.z))/2D);
	}
	public int getDims()
	{
		return 0;
	}
	public Point3D getTextPoint()
	{
		return this;
	}
	public String toString()
	{
		return "("+x+","+y+","+z+")";
	}
}
