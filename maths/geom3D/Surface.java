package maths.geom3D;
import commons.X;
import maths.Maths;
import maths.functions.Function;
import java.awt.Color;
import static maths.geom3D.FunctionVector.genpt;

public class Surface //implements java.awt.Shape
{
	FunctionVector normal=null;
	Function sFunc;
	
	
	public Surface() {sFunc=new Function("-1");normal=new FunctionVector(new Function("0"));}
	public Surface(Function sf,FunctionVector fv) {sFunc=sf;normal=fv;}
	public Surface(Function sf,Function xC,Function yC,Function zC) {sFunc=sf;normal=new FunctionVector(xC,yC,zC);}
	public Surface(Function sF) {sFunc=sF;buildNormal();}
	
	private void buildNormal()
	{
		Function xC,yC,zC;
		xC=sFunc.clone().differentiate('x');
		yC=sFunc.clone().differentiate('y');
		zC=sFunc.clone().differentiate('z');
		normal=new FunctionVector(xC,yC,zC);
	}
	
	public Function getSurfaceFunction() {return sFunc;}
	public FunctionVector getSurfaceVector() {return normal;}
	public Vector getNormal(double x,double y,double z) {return normal.getVector(x,y,z);}
	public Vector getNormal(Point3D pt) {return getNormal((int)pt.x,(int)pt.y,(int)pt.z);}
	
	public boolean contains(double x,double y,double z) {double vl=sFunc.getVal(genpt,new double[] {x,y,z}); return (vl<1);}
	public boolean contains(Point3D pt) {return contains(pt.x,pt.y,pt.z);}
	public boolean contains(FunctionPoint3D pt)
	{
		return false;
	}
	public double getValueAt(double x,double y,double z) {return sFunc.getVal(genpt,new double[] {x,y,z});}
	public double getValueAt(Point3D pt) {return getValueAt(pt.x,pt.y,pt.z);}
	public boolean intersects(Shape3D shp)
	{
		Cuboid bounds=shp.getBounds();
		Point3D[] ret=new Point3D[0];
		for(int i=(int)bounds.x;i<=bounds.x+bounds.w;i++)
		{
			for(int j=(int)bounds.y;j<=bounds.y+bounds.h;j++)
			{
				for(int k=(int)bounds.z;k<=bounds.z+bounds.d;k++)
				{
					if(this.contains(i,j,k) && shp.contains(i,j,k))
						return true;
				}
			}
		}
		return false;
	}
	
	public String toString()
	{
		String str="Surface: "+sFunc.toString()+" = 0";
		return str;
	}
}

class PointOutOfSurfaceException extends RuntimeException
{
	public PointOutOfSurfaceException(Surface s)
	{
		X.sopln("Err: Point requested does not exist on surface.");
		s.getSurfaceFunction().printFx();
	}
}
