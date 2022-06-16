package draw;
import java.awt.*;
import java.awt.geom.*;
import commons.X;
public interface Figure
{
	public java.awt.geom.Point2D[] getAllPoints()throws UnboundedException;
	public Color getColor();
	public void setColor(Color c);
	public Rectangle2D getBounds()throws UnboundedException;
	public Point2D getCentre()throws UnboundedException;
	public boolean isCurve();
	public boolean contains(Point2D pt)throws UnclosedFigureException;
	//public boolean intersects(Figure f2);
}

