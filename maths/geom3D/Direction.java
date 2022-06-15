package maths.geom3D;
import commons.*;
import maths.*;

public class Direction
{
	double ax=90,ay=90,az=90; //Angles with coordinate axes in degrees
	public static final Direction ZERO=new Direction(0,90,90);
	public static final Direction I=new Direction(new Point3D(1,0,0)),J=new Direction(new Point3D(0,1,0)),K=new Direction(new Point3D(0,0,1));
	public Direction() {}
	public Direction(double a)
	{
		this(Point3D.O,new Point3D(Math.cos(Math.toRadians(a)),Math.sin(Math.toRadians(a)),0));
	}
	public Direction(double a,double b)
	{
		ax=a; ay=b;
		double dv=Math.pow(Math.cos(Math.toRadians(ax)),2)+Math.pow(Math.cos(Math.toRadians(ay)),2);
		az=Math.toDegrees(Math.acos(Math.sqrt(1-dv)));
	}
	public Direction(double a,double b,double c)
	{
		ax=a; ay=b; az=c;
	}
	public Direction(Point3D pt)
	{
		this(Point3D.O,pt);
	}
	public Direction(Point3D p1,Point3D p2)
	{
		double dx=p2.x-p1.x,dy=p2.y-p1.y,dz=p2.z-p1.z; //0,1,0
		double MAG=Math.sqrt(dx*dx+dy*dy+dz*dz); //1
		ax=Math.toDegrees(Math.acos(dx/MAG));
		ay=Math.toDegrees(Math.acos(dy/MAG));
		az=Math.toDegrees(Math.acos(dz/MAG));
	}
	public Direction(Point p1,Point p2)
	{
		this(new Point3D(p1.x,p1.y,0),new Point3D(p2.x,p2.y,0));
	}
	public Direction(Direction dir)
	{
		this.assign(dir);
	}

	public double getX()
	{
		return Math.toRadians(ax);
	}
	public double getY()
	{
		return Math.toRadians(ay);
	}
	public double getZ()
	{
		return Math.toRadians(az);
	}
	public double getAngleWith(Direction d)
	{
		return new Vector(1,this).pureGetAngleWith(new Vector(1,d));
	}
	public void assign(Direction d2)
	{
		ax=d2.ax;
		ay=d2.ay;
		az=d2.az;
	}
	public boolean equals(Direction d)
	{
		double ax=this.ax,ay=this.ay,az=this.az;
		ax%=360;
		if(ax<0)
			ax+=360;
		ay%=360;
		if(ay<0)
			ay+=360;
		az%=360;
		if(az<0)
			az+=360;
		double ax2=d.ax,ay2=d.ay,az2=d.az;
		ax2%=360;
		if(ax2<0)
			ax2+=360;
		ay2%=360;
		if(ay2<0)
			ay2+=360;
		az2%=360;
		if(az2<0)
			az2+=360;
		return (ax==ax2 && ay==ay2 && az==az2);
	}

	public static Direction getNormalToPlane(Direction d1,Direction d2)
	{
		Vector v1=new Vector(d1),v2=new Vector(d2);
		Vector v3=v1.cross(v2);
		return v3.getDir();
	}

	public void rotate(Direction normal,double ang) //angle in degrees
	{
		Vector vpar=new Vector(this),vstat=new Vector(normal),fixedcomp=vstat.mul(vstat.dot(vpar));;
		Vector vrot=vpar.sub(fixedcomp);
		Direction planeV2=getNormalToPlane(normal,vrot.getDir()); //Check direction
		Vector vperp=new Vector(planeV2),parcomp=vrot.mul(Math.cos(Math.toRadians(ang))),perpcomp=vperp.mul(Math.sin(Math.toRadians(ang)));
		this.assign(parcomp.add(perpcomp).add(fixedcomp).getDir());
	}

	public Direction getNegative() {return new Direction(180-ax,180-ay,180-az);}

	public String toString()
	{
		return "Direction object with angles: "+ax+","+ay+","+az;
	}
}
