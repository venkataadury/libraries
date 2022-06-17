package jtools.rendering;
import maths.geom3D.Point3D;
import maths.Maths;

public class GamePoint extends Point3D
{
	public GamePoint() {super(0,0,0);}
	public GamePoint(int x,int y,int z) {super(x,y,z);}
	public GamePoint(int x,int y) {super(x,y,0);}
	public GamePoint(Point3D pt) {super(pt);}
}
