package maths.geom3D;
import commons.X;
import maths.Maths;
import maths.functions.*;
import static maths.geom3D.FunctionVector.genpt;

public class FunctionPoint3D //3D Locus
{
	private Function x,y,z;
	
	public FunctionPoint3D(Point3D pt) {x=new Function(new Constant(pt.x));y=new Function(new Constant(pt.y));z=new Function(new Constant(pt.z));}
	public FunctionPoint3D(Function pX,Function pY) {x=pX;y=pY;z=new Function(new Constant(0));}
	public FunctionPoint3D(Function pX,Function  pY,Function pZ) {x=pX;y=pY;z=pZ;}
	
	public boolean satisfies(Point3D pt) {return satisfies(pt.x,pt.y,pt.z);}
	public boolean satisfies(double px,double py,double pz)
	{
		boolean b1=x.getVal(genpt,new double[] {px,py,pz})<1,b2=y.getVal(genpt,new double[] {px,py,pz})<1,b3=z.getVal(genpt,new double[] {px,py,pz})<1;
		return (b1 && b2 && b3);
	}
}
