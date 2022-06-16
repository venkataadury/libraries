package draw;
import commons.X;
import maths.*;
import java.awt.Point;

public class Z
{
	public static java.awt.Point[] horizFlip(java.awt.Point[] pts,int mLen) //Upside down
	{
		for(int i=0;i<pts.length;i++)
			pts[i]=new Point(pts[i].x,mLen-pts[i].y);
		return pts;
	}
	public static maths.Point[] horizFlip(maths.Point[] pts,int mLen)
	{
		return Maths.switchPoints(horizFlip(Maths.switchPoints(pts),mLen));
	}
}
