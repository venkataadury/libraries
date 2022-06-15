package maths.geom3D;
import commons.X;

public class PathPoint
{
	public final Point3D pt;
	public final boolean connected;
	
	public static final boolean CONNECTED=true,DISCONNECTED=false;
	
	public PathPoint(Point3D p,boolean cont) {pt=p;connected=cont;}
	public PathPoint(int x,int y,int z,boolean cont) {this(new Point3D(x,y,z),cont);}
	
	public Point3D getPoint() {return pt;}
	public boolean getCont() {return connected;}
}
