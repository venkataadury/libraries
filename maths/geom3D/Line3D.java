package maths.geom3D;
import commons.X;
import maths.Maths;
import maths.AmbiguousArgumentException;
import draw.S;
import upgrade.ArrayFx;
//import maths.functions.*;

public class Line3D extends Core3D
{
	protected Point3D initP=null;
	protected Vector dirV=null;
	
	// r = (a,b,c) + k<x,y,z>
	
	public Line3D() {this(new Point3D(0,0,0),new Vector(1,1,1));}
	public Line3D(Vector v) {this(Point3D.O,v);}
	public Line3D(Point3D pt,Vector v) {initP=pt;dirV=v;}
	public Line3D(Point3D p1,Point3D p2) {this(p1,new Vector(p1,p2));}
	
	
	public boolean contains(Point3D pt)
	{
		if(initP.equals(pt))
			return true;
		double pX=(initP.x-pt.x)/dirV.getXComponent().getMag();
		double pY=(initP.y-pt.y)/dirV.getYComponent().getMag();
		double pZ=(initP.z-pt.z)/dirV.getZComponent().getMag();
		if(Double.isNaN(pX) && Double.isNaN(pY) && Double.isNaN(pZ))
			return true;
		
		if(Double.isNaN(pZ))
			pZ=pX;
		if(Double.isNaN(pY))
			pY=pZ;
		if(Double.isNaN(pX))
			pX=pY;
		
		return (pX==pY && pY==pZ);
	}
 	/*public Function getEquation()
 	{
 		Function f=new Function();
 		
 	}*/
	public String toString()
	{
		String str="r = ";
		str+=initP.toString()+" k("+dirV.toString()+")";
		return str;
	}
	public Point3D[] getIntersection(Shape3D shp)
	{
		Cuboid bounds=shp.getBounds();
		Point3D[] ret=new Point3D[0];
		for(int i=(int)bounds.x;i<=bounds.x+bounds.w;i++)
		{
			for(int j=(int)bounds.y;j<=bounds.y+bounds.h;j++)
			{
				for(int k=(int)bounds.z;k<=bounds.z+bounds.d;k++)
				{
					if(shp.onSurface(i,j,k))
					{
						if(this.dist(i,j,k)<1)
							ret=ArrayFx.append(ret,new Point3D(i,j,k));
					}
				}
			}
		}
		return ret;
	}
	
	public double dist(double x,double y,double z) 
	{
		return dist(new Point3D(x,y,z));
	}
	public double dist(Point3D pt) 
	{
		Vector da=new Vector(pt,initP);
		return da.cross(dirV.getUnitVector()).getMag(); 
	}
	
	public Point3D getPointByX(double x)
	{
		if(dirV.getXComponent().getMag()==0)
			throw new AmbiguousArgumentException("Cannot find unique point for x="+x);
		double LHS=(x-initP.x)/dirV.getXComponent().getMag();
		double y=LHS*dirV.getYComponent().getMag()+initP.y;
		double z=LHS*dirV.getZComponent().getMag()+initP.z;
		return new Point3D(x,y,z);
	}
	
	public Point3D getPointByY(double y)
	{
		if(dirV.getYComponent().getMag()==0)
			throw new AmbiguousArgumentException("Cannot find unique point for y="+y);
		double LHS=(y-initP.y)/dirV.getYComponent().getMag();
		double x=LHS*dirV.getXComponent().getMag()+initP.x;
		double z=LHS*dirV.getZComponent().getMag()+initP.z;
		return new Point3D(x,y,z);
	}
	
	public Point3D getPointByZ(double z)
	{
		if(dirV.getZComponent().getMag()==0)
			throw new AmbiguousArgumentException("Cannot find unique point for z="+z);
		double LHS=(z-initP.z)/dirV.getZComponent().getMag();
		double y=LHS*(dirV.getYComponent()).getMag()+initP.y;
		double x=LHS*(dirV.getXComponent()).getMag()+initP.x;
		return new Point3D(x,y,z);
	}
}
