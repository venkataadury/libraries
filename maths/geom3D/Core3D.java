package maths.geom3D;
import commons.*;
import maths.Maths;
import java.awt.*;
import java.io.IOException;
public class Core3D
{
	public static final boolean[] NULLFRAME=new boolean[] {true,true,true};
	public static Point3D inputPoint(String msg,String col)throws IOException
	{
		X.sop(msg,col);
		return new Point3D(X.rL().trim());
	}
	public static Point3D inputPoint(String msg)throws IOException
	{
		return inputPoint(msg,"white");
	}
	public static Point3D inputPoint()throws IOException
	{
		return inputPoint("Enter a point: ","white");
	}
	
	public static Point get2DPoint(Point3D p,int orientX,int orientY) // orient=angle
	{
		Point pt=new Point();
		// (100,0,0) ==> x^2 + y^2 <= 100
		pt.x=(int)(p.x*Maths.cos(orientX)+Maths.sin(orientX)*p.z);
		pt.y=(int)(p.y*Maths.cos(orientY)+Maths.sin(orientY)*p.z);
		return pt;
	}
	public static FunctionPoint3D get3DPoint(Point pt,int orientX,int orientY) {return null;}
	
	public static Color getColorByFrame(boolean[] frame)
	{
		Color ret=Color.black;
		if(!frame[0])
			ret=Color.green;
		if(!frame[1])
			ret=Color.blue;
		if(!frame[2])
			ret=Color.red;
		return ret;
	}
	public static boolean inSameFrame(Framed3D f1,Framed3D f2)
	{
		boolean[] fr1,fr2;
		fr1=f1.getFrame(); fr2=f2.getFrame();
		boolean[] f3=new boolean[] {(fr1[0]==fr2[0]) && fr1[0],(fr1[1]==fr2[1]) && fr1[1],(fr1[2]==fr2[2]) && fr1[2]};
		return ((f3[0] && f3[1]) || (f3[1] && f3[2]) || (f3[2] && f3[0]));
	}
}
