package maths.geom3D;
import maths.functions.Function;
import commons.X;

public class FunctionVector
{
	private Function fX,fY,fZ;
	public static final char[] genpt=new char[] {'x','y','z'};
	
	public FunctionVector() {fX=Function.ONE;fY=Function.ONE;fZ=Function.ONE;}
	public FunctionVector(Function fx) {this(); fX=fx;}
	public FunctionVector(Function fx,Function fy) {this(); fX=fx;fY=fy;}
	public FunctionVector(Function fx,Function fy,Function fz) {this(); fX=fx;fY=fy;fZ=fz;}
	
	public Function getFuncX() {return fX;}
	public Function getFuncY() {return fY;}
	public Function getFuncZ() {return fZ;}
	
	public Vector getVector(double x,double y,double z)
	{
		double[] xyz=new double[] {x,y,z};
		return new Vector(fX.getVal(genpt,xyz),fY.getVal(genpt,xyz),fZ.getVal(genpt,xyz));
	}
}
