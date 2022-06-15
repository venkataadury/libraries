package maths;
import commons.*;
import java.awt.*;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.util.NoSuchElementException;

public class CirclePathIterator implements PathIterator
{
	private Circle circle;
	private java.awt.Point centre;
	private Locus lc;
	AffineTransform at;
	public double deg=0.0;
	public CirclePathIterator(Circle cl)
	{
		circle=cl;
		centre=cl.centre;
		lc=cl.path;
	}
	public CirclePathIterator(Circle cl,AffineTransform a)
	{
		this(cl);
		at=a;
	}
	public void next()
	{
		deg+=0.1;
	}
	public boolean isDone()
	{
		return deg>360.0;
	}
	public int getWindingRule()
	{
		return PathIterator.WIND_NON_ZERO;
	}
	public int currentSegment(double[] coords)
	{
		if(isDone())
			throw new NoSuchElementException("Iterator out of bounds");
		if(deg==360)
			return SEG_CLOSE;
		if(deg%0.2==0)
			return SEG_MOVETO;
		else
			return SEG_LINETO;
	}
	public int currentSegment(float[] coords)
	{
		return currentSegment(new double[] {coords[0],coords[1]});
	}
}
