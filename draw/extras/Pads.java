package draw.extras;
import commons.X;
import java.awt.*;

public abstract class Pads extends Container 
{
	protected int W,H;
	protected boolean visi=false;
	protected Pads() {}
	protected void setWidth(int w)
	{
		W=w;
		this.setSize(W,H);
	}
	protected void setHeight(int h)
	{
		H=h;
		this.setSize(W,H);
	}
	protected void setDims(int w,int h)
	{
		W=w; H=h;
		this.setSize(W,H);
	}
	public int getWidth()
	{
		return W;
	}
	public int getHeight()
	{
		return H;
	}
	public void setPoint(Point pt)
	{
		super.setBounds(pt.x,pt.y,W,H);
	}
	public void setPoint(int x,int y)
	{
		this.setPoint(new Point(x,y));
	}
	public void setVisible(boolean b)
	{
		visi=b;
		super.setVisible(b);
	}
}
