package maths.geom3D;
import commons.X;

public interface Shape3D
{
	public boolean contains(double x,double y,double z);
	public boolean contains(Point3D pt);
	public Cuboid getBounds();
	public Path3D getPath();
	public Surface[] getAllSurfaces();
	public boolean onSurface(double x,double y,double z);
	public boolean onSurface(Point3D pt);
	public Surface getSurface(double x,double y,double z);
	public Surface getSurface(Point3D pt);
	public void translate(double x,double y,double z);
	public void moveTo(double x,double y,double z);
	public void moveTo(Point3D pt);
	public Point3D getLocation();
	public Point3D getCentre();
	public boolean intersects(Shape3D s);
	public boolean intersects(Surface sf);
}
