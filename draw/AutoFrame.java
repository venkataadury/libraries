package draw;
import commons.X;
import maths.Graphed;
import java.awt.*;

public class AutoFrame extends Frame
{
	public static final int stdW=800,stdH=600;
	public AutoFrame()
	{
		this.setSize(stdW,stdH);
		this.addWindowListener(AWT.WINPROPS);
		this.setVisible(false);
	}
	public static void drawGraph(Frame f,Graphed gp,Color c,int mult)
	{
		java.awt.Point[] fpd=Z.horizFlip(gp.getAllPoints(),(int)f.getHeight());
		if(mult!=1 && mult!=0)
		{
		for(int i=0;i<fpd.length;i++)
		{
			fpd[i].x*=mult; fpd[i].y*=mult;
		}
		}
		Draw.tracePath((f.getGraphics()),fpd,c);
	}
	public void drawGraph(Graphed gp,Color c,int mult)
	{
		drawGraph(this,gp,c,mult);
	}
	public void fillBackground(Color c)
	{
		(this.getGraphics()).setColor(c);
		(this.getGraphics()).fillRect(0,0,(int)getWidth(),(int)getHeight());
	}
	public void drawGraph(Graphed gp,Color c)
	{
		drawGraph(gp,c,1);
	}
}
