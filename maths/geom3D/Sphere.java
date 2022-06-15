package maths.geom3D;
import commons.X;
import maths.Maths;
import maths.functions.*;

public class Sphere extends Object3D
{
	double x,y,z;
	double rad;
	Surface sEq=null;
	public static final Constant TWO=new Constant(2);
	
	public Sphere() {x=y=z=0;rad=0;setup();}
	public Sphere(double r) {this();rad=r;setup();}
	public Sphere(Point3D ctr,double r) {x=ctr.x;y=ctr.y;z=ctr.z;rad=r;setup();}
	public Sphere(double xC,double yC,double zC,double r) {this(new Point3D(xC,yC,zC),r);}
	
	private void setup() {setSurface();}
	private void setSurface()
	{
		Function x1=new Function("x "+(-x));
		Function y1=new Function("y "+(-y));
		Function z1=new Function("z "+(-z));
		Constant cst=new Constant(-(rad*rad));
		Function fx=new Function(new FuncBlock[] {new Term(x1,Constant.ONE,Constant.ONE,TWO),new Term(y1,Constant.ONE,Constant.ONE,TWO),new Term(z1,Constant.ONE,Constant.ONE,TWO),cst});
		sEq=new Surface(fx);
	}
	
	public double getVolume() {return Math.PI*rad*rad;}
	public Surface[] getAllSurfaces() {return new Surface[] {sEq};}
	public Point3D getLocation() {return new Point3D(x-rad,y-rad,z-rad);}
	public void moveTo(Point3D pos) {moveTo(pos.x,pos.y,pos.z);}
	public void moveTo(double pX,double pY,double pZ)
	{
		x=(pX+rad);
		y=(pY+rad);
		z=(pZ+rad);
		setup();
	}
	public void translate(double tX,double tY,double tZ) {x+=tX;y+=tY;z+=tZ;setup();}
	public boolean contains(Point3D pt) {return contains(pt.x,pt.y,pt.z);}
	public boolean contains(double xC,double yC,double zC) {return (sEq.getValueAt(xC,yC,zC)<=0);}
	public Cuboid getBounds() {return new Cuboid(x-rad,y-rad,z-rad,2*rad,2*rad,2*rad);}
	public Surface getSurface(Point3D pt) {if(sEq.contains(pt))return sEq;else return null;}
	public Surface getSurface(double xC,double yC,double zC) {return getSurface(new Point3D(xC,yC,zC));}
	public boolean onSurface(Point3D pt) {return sEq.contains(pt);}
	public boolean onSurface(double xC,double yC,double zC) {return sEq.contains(new Point3D(xC,yC,zC));}
	
	public Path3D getPath()
	{
		Path3D pth=new Path3D();
		double step=90/rad;
		for(double angle=0;angle<360;angle+=step)
			pth.addPoint(new Point3D(x+rad*Maths.cos(angle),y+rad*Maths.sin(angle),z),true);
		pth.addPoint((int)(x+rad),(int)y,(int)z,false);
		/*for(double angle=0;angle<360;angle+=step)
			pth.addPoint(new Point3D(x,y+rad*Maths.cos(angle),z+rad*Maths.sin(angle)),true);*/
		return pth;
	}
	public Point3D getCentre() {return new Point3D(x,y,z);}
	
	public boolean intersects(Shape3D sh)
	{
		Point3D mC=getCentre();
		Point3D sC=sh.getCentre();
		if(this.contains(sC) || sh.contains(mC))
			return true;
		Vector v =new Vector(mC,sC);
		v.setMag(rad);
		return sh.contains((new Point3D(x,y,z)).getPoint(v));
	}
}
