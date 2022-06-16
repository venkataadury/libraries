package draw;
import commons.X;
import java.awt.*;

public interface Drawable
{
	public void drawLine(int a,int b,int c, int d);
	public void drawLine(Point x,Point y);
	public void drawImage(Image img,int x,int y);
	public void drawRect(int x,int y,int w,int h);
	public void drawRect(Rectangle r);
	public void drawRect(Point p,int w,int h);
	public void drawPolygon(Polygon p);
	public void drawPolygon(int[] xp,int[] yp,int np);
}
