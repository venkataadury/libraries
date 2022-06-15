package maths.geom3D;
import commons.X;
import upgrade.ArrayFx;

public final class Path3D
{
	private PathPoint[] points;
	private int pI=0;
	
	public Path3D() {points=new PathPoint[0];}
	public Path3D(PathPoint[] pts) {points=pts;}
	public Path3D(Point3D[] pts)
	{
		points=new PathPoint[pts.length];
		for(int i=0;i<pts.length;i++)
			points[i]=new PathPoint(pts[i],PathPoint.CONNECTED);
	}
	
	public void resetPointerPosition() {pI=0;}
	public int getPointerPosition() {return pI;}
	public PathPoint next() {return points[pI++];}
	public boolean hasNext() {return (pI<points.length);}
	
	public void addPoint(Point3D pt,boolean cont) {points=ArrayFx.append(points,new PathPoint(pt,cont));}
	public void addPoint(int x,int y,int z,boolean cont) {addPoint(new Point3D(x,y,z),cont);}
	
}
