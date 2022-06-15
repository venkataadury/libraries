package maths.geom3D;
import commons.X;
import maths.Maths;
import maths.functions.*;

public class Cuboid extends Object3D
{
	double x,y,z;
	public double w,h,d;
	Surface[] mySurfaces;
	
	public Cuboid() {x=y=z=0;w=h=d=0;}
	public Cuboid(double wi,double he,double de)  {this(); w=wi;h=he;d=de;setup();}
	public Cuboid(Point3D vert,double wi,double he,double de) {this(wi,he,de);x=vert.x; y=vert.y; z=vert.z;setup();}
	public Cuboid(double xC,double yC,double zC,double wi,double he,double de) {this(new Point3D(xC,yC,zC),wi,he,de);}
	
	private void setup()
	{
		mySurfaces=setAllSurfaces();
	}
	public double getVolume() {return w*h*d;}
	public boolean contains(double pX,double pY,double pZ)
	{
		return (Maths.between(x,x+w,pX) && Maths.between(y,y+h,pY) && Maths.between(z,z+d,pZ));
	}
	public boolean contains(Point3D pt) {return contains(pt.x,pt.y,pt.z);}
	public Cuboid getBounds() {return this;}
	public Path3D getPath()
	{
		int x=(int)this.x,y=(int)this.y,z=(int)this.z;
		int w=(int)this.w,h=(int)this.h,d=(int)this.d;
		Path3D path=new Path3D();
		path.addPoint(x,y,z,true);
		path.addPoint(x+w,y,z,true);
		path.addPoint(x+w,y+h,z,true);
		path.addPoint(x,y+h,z,true);
		path.addPoint(x,y,z,true);
		path.addPoint(x,y,z+d,true);
		path.addPoint(x+w,y,z+d,true);
		path.addPoint(x+w,y,z,true);
		path.addPoint(x,y,z,true);
		path.addPoint(x,y+h,z,true);
		path.addPoint(x,y+h,z+d,true);
		path.addPoint(x,y,z+d,false);
		path.addPoint(x+w,y,z+d,true);
		path.addPoint(x+w,y+h,z+d,true);
		path.addPoint(x+w,y+h,z,false);
		path.addPoint(x,y+h,z+d,true);
		path.addPoint(x+w,y+h,z+d,false);
		return path;
	}
	
	public Surface[] setAllSurfaces()
	{
		Surface[] ret=new Surface[6];
		
		/*ret[0]=new Surface((new Function("y -"+y)),new FunctionVector(Function.ZERO,new Function(new Constant(-h)),Function.ZERO));
		ret[1]=new Surface((new Function("x -"+x)),new FunctionVector(new Function(new Constant(-w)),Function.ZERO,Function.ZERO));
		ret[2]=new Surface((new Function("z -"+z)),new FunctionVector(Function.ZERO,Function.ZERO,new Function(new Constant(-d))));
		ret[3]=new Surface((new Function("x -"+(x+w))),new FunctionVector(new Function(new Constant(w)),Function.ZERO,Function.ZERO));
		ret[4]=new Surface((new Function("z -"+(z+d))),new FunctionVector(Function.ZERO,Function.ZERO,new Function(new Constant(d))));
		ret[5]=new Surface((new Function("y -"+(y+h))),new FunctionVector(Function.ZERO,new Function(new Constant(h)),Function.ZERO));*/
		
		ret[0]=new Surface((new Function("y "+(-y))));
		ret[1]=new Surface((new Function("x "+(-x))));
		ret[2]=new Surface((new Function("z "+(-z))));
		ret[3]=new Surface((new Function("x "+(-x-w))));
		ret[4]=new Surface((new Function("z "+(-z-d))));
		ret[5]=new Surface((new Function("y "+(-y-h))));
		return ret;
	}
	public Surface[] getAllSurfaces() {return mySurfaces;}
	
	public boolean onSurface(double x,double y,double z)
	{
		if(!this.contains(x,y,z))
			return false;
		Surface[] sA=getAllSurfaces();
		for(int i=0;i<sA.length;i++)
		{
			if(sA[i].contains(x,y,z))
				return true;
		}
		return false;
	}
	public boolean onSurface(Point3D pt) {return onSurface(pt.x,pt.y,pt.z);}
	
	public Surface getSurface(double x,double y,double z)
	{
		if(!this.contains(x,y,z))
			return null;
		Surface[] sa=getAllSurfaces();
		for(int i=0;i<sa.length;i++)
		{
			if(sa[i].contains(x,y,z))
				return sa[i];
		}
		return null;
	}
	public Surface getSurface(Point3D pt) {return getSurface(pt.x,pt.y,pt.z);}
	public Point3D getCentre() {return new Point3D(x+w/2,y+h/2,z+d/2);}
	
	public boolean intersects(Shape3D sh)
	{
		Point3D mC=getCentre();
		Point3D sC=sh.getCentre();
		if(this.contains(sC) || sh.contains(mC))
			return true;
		Vector dV=new Vector(mC,sC); dV=dV.getUnitVector();
		double mMag=mC.distance(sC);
		Point3D temp;
		for(int i=1;i<mMag;i++)
		{
			temp=mC.getPoint(dV.mul(i));
			if(this.contains(temp) && sh.contains(temp))
				return true;
		}
		return false;
	}
	
	public void translate(double tx,double ty,double tz) {x+=tx;y+=ty;z+=tz;setAllSurfaces();}
	public void moveTo(double px,double py,double pz) {x=px;y=py;z=pz; setup();}
	public void moveTo(Point3D pt) {moveTo(pt.x,pt.y,pt.z);}
	public Point3D getLocation() {return new Point3D(x,y,z);}
}
