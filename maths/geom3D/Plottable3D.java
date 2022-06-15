package maths.geom3D;
import commons.*;
import maths.Maths;
import java.awt.*;
public interface Plottable3D extends Framed3D
{
	public Point3D[] getPoints();
	public boolean isClosed();
	public String getName();
	public void setName(String name);
	public int getDims();
	public Point3D getTextPoint();
}
