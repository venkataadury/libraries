package draw;
import java.awt.*;
import java.awt.event.*;
import commons.*;
import maths.Point;
import maths.Locus;
public class AWT extends S
{
	public static final Label lempty=new Label();
	public static final Label Lempty=new Label();
	public static final Button OK=new Button("OK");
	public static final Button CANCEL=new Button("Cancel");
	public static final Button CLX=new Button("X"); //20x20
	public static final Font tinyfont=new Font("Times new roman",6,6);
	public static final Font SmallFont=new Font("Times new roman",10,10);
	public static final Font MediumFont=new Font("Times new roman",25,25);
	public static final Font LargeFont=new Font("Times new roman",40,40);
	public static final Font EXTREMEFont=new Font("Times new roman",60,60);
	public static final Font EXTREMEFONT=EXTREMEFont;
	public static final WindowAdapter WINPROPS=new WindowAdapter()
	                                               {
                                                            public void windowClosing(WindowEvent we)
                                                            {
                                                              System.exit(0);
                                                            }
                                                 };
	public static final Button CLOSE=new Button("Close"); //80x30

	public static maths.Point switchPoint(java.awt.Point p)
	{
		return new maths.Point(p.x,p.y);
	}
	public static java.awt.Point switchPoint(maths.Point p)
	{
		return new java.awt.Point((int)p.x,(int)p.y);
	}
	public static void init(ActionListener al)
	{
		OK.addActionListener(al);
		CLOSE.addActionListener(al);
		CLX.addActionListener(al);
		CANCEL.addActionListener(al);
		OK.setVisible(true);
		CLOSE.setVisible(true);
		CLX.setVisible(true);
		CANCEL.setVisible(true);
	}
	public static Polygon rectangleToPolygon(Rectangle rect) {
    int[] xpoints = {rect.x, rect.x + rect.width, rect.x + rect.width, rect.x};
    int[] ypoints = {rect.y, rect.y, rect.y + rect.height, rect.y + rect.height};
    return new Polygon(xpoints, ypoints, 4);
	}
	public static java.awt.Point[] appendPoint(java.awt.Point[] pa,java.awt.Point p)
	{
		java.awt.Point[] par=new java.awt.Point[pa.length+1];
		for(int i=0;i<pa.length;i++)
			par[i]=pa[i];
		par[pa.length]=p;
		return par;
	}
	public static maths.Point[] appendPoint(maths.Point[] pa,maths.Point p)
	{
		maths.Point[] par=new maths.Point[pa.length+1];
		for(int i=0;i<pa.length;i++)
			par[i]=pa[i];
		par[pa.length]=p;
		return par;
	}
	public static void tracePath(Graphics g,Locus l,Color c)
	{
		if(l.getPoints().length<2)
			return;
		g.setColor(c);
		java.awt.Point P1=l.getFirstPointAWT();
		for(java.awt.Point p : l.getPointsAWT())
		{
			g.drawLine(P1.x,P1.y,(int)p.x,(int)p.y);
			P1=p;
		}
	}
	public static void tracePath(Graphics g,Locus l)
	{
		tracePath(g,l,g.getColor());
	}
	public static void tracePath(Graphics g,java.awt.Point[] pts,Color c)
	{
		tracePath(g,new Locus(pts),c);
	}
	public static void tracePath(Graphics g,java.awt.Point[] pts)
	{
		tracePath(g,new Locus(pts),g.getColor());
	}
	public static void tracePath(Graphics g,maths.Point[] pts,Color c)
	{
		tracePath(g,new Locus(pts),c);
	}
	public static void tracePath(Graphics g,maths.Point[] pts)
	{
		tracePath(g,new Locus(pts),g.getColor());
	}
	public static void plotPoint(Graphics g,java.awt.Point pt,Color c)
	{
		g.setColor(c);
		g.drawLine(pt.x-10,pt.y,pt.x+10,pt.y);
		g.drawLine(pt.x,pt.y-10,pt.x,pt.y+10);
	}
}
