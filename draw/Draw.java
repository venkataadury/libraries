package draw;
import java.awt.*;
import java.io.*;
import commons.X;
import maths.*;
public class Draw extends AWT
{
	public static Color COL(Graphics g)
	{
		return g.getColor();
	}
	public static void drawLine(Graphics g,java.awt.Point p1,java.awt.Point p2)
	{
		drawLine(g,p1,p2,g.getColor());
	}
	public static void drawLine(Graphics g,java.awt.Point p1,java.awt.Point p2,Color c)
	{
		g.setColor(c);
		g.drawLine(p1.x,p1.y,p2.x,p2.y);
	}
	public static void drawLine(Graphics g,maths.Point p1,maths.Point p2,Color c)
	{
		drawLine(g,Maths.switchPoint(p1),Maths.switchPoint(p2),c);
	}
	public static void drawLine(Graphics g,maths.Point p1,maths.Point p2)
	{
		drawLine(g,Maths.switchPoint(p1),Maths.switchPoint(p2));
	}
	public static void drawDashedLine(Graphics g,java.awt.Point p1,java.awt.Point p2,int dash1)
	{
		drawDashedLine(g,p1,p2,COL(g),dash1,dash1/2);
	}
	public static void drawDashedLine(Graphics g,java.awt.Point p1,java.awt.Point p2,int dash1,int dash2)
	{
		drawDashedLine(g,p1,p2,COL(g),dash1,dash2);
	}
	public static void drawDashedLine(Graphics g,java.awt.Point p1,java.awt.Point p2,Color c,int dash1)
	{
		drawDashedLine(g,p1,p2,c,dash1,dash1/2);
	}
	public static void drawDashedLine(Graphics g,java.awt.Point p1,java.awt.Point p2,Color c,int dash1,int dash2)
	{
		maths.Point p3=Maths.switchPoint(p1);
		maths.Point p4=Maths.switchPoint(p2);
		double d=GLine.distance(p3,p4);
		int subs=(int)d/(dash1+dash2);
		g.setColor(c); 
		//0,0 ==> 0,6 1,1    | 3
		java.awt.Point[] bposs=Maths.switchPoints(GLine.sect(Maths.switchPoint(p1),Maths.switchPoint(p2),subs));
		java.awt.Point oldp1,oldp2=p2,npt;
		//1
		java.awt.Point Pt;
		for(int i=0;i<bposs.length;i++)
		{
			Pt=bposs[i];
			oldp1=Maths.switchPoint(Maths.toPoint(Graph.section(Maths.switchPoint(Pt),Maths.switchPoint(oldp2),dash1+dash2,1)));
			drawLine(g,Pt,oldp1);
			oldp2=Pt;
		}
	}
	public static void drawDashedLine(Graphics g,java.awt.Point p1,java.awt.Point p2)
	{
		drawDashedLine(g,p1,p2,g.getColor(),3,1);
	}
	public static void drawDashedLine(Graphics g,maths.Point p1,maths.Point p2)
	{
		drawDashedLine(g,Maths.switchPoint(p1),Maths.switchPoint(p2),g.getColor(),3,1);
	}
	public static void surfaceFill(Canvas gp,Graphics g,java.awt.Point p1,java.awt.Point p2,Angle ang,Color c,char side)
	{
		int an=(int)ang.v;
		java.awt.Point c1,c2; //Corners
		int xm=gp.getWidth(),ym=gp.getHeight();
		g.setColor(c);
		if(side=='r')
		{
			if(an<=45)
			{
				g.fillPolygon(new int[] {p1.x,p2.x,xm,xm},new int[] {0,ym,ym,0},4);
				return;
			}
			if(an<=90)
			{
				g.fillPolygon(new int[] {0,xm,xm,0},new int[] {p1.y,p2.y,0,0},4);
				return;
			}
			if(an<=135)
			{
				g.fillPolygon(new int[] {0,xm,xm,0},new int[] {p1.y,p2.y,ym,ym},4);
				return;
			}
			if(an>135)
			{
				g.fillPolygon(new int[] {p1.x,p2.x,xm,xm},new int[] {ym,0,0,ym},4);
				return;
			}
		}
		if(side=='l')
		{
			if(an<=45)
			{
				g.fillPolygon(new int[] {p1.x,p2.x,0,0},new int[] {0,ym,ym,0},4);
				return;
			}
			if(an<=90)
			{
				g.fillPolygon(new int[] {0,xm,xm,0},new int[] {p1.y,p2.y,0,0},4);
				return;
			}
			if(an<=135)
			{
				g.fillPolygon(new int[] {0,xm,xm,0},new int[] {p1.y,p2.y,ym,ym},4);
				return;
			}
			if(an>135)
			{
				g.fillPolygon(new int[] {p1.x,p2.x,xm,xm},new int[] {ym,0,0,ym},4);
				return;
			}
		}
	}
	public static void drawThickLine(Graphics g,java.awt.Point ex,java.awt.Point ex2,Color c)
	{
		g.setColor(c);
		g.drawLine(ex.x,ex.y,ex2.x,ex2.y);
		g.drawLine(ex.x+1,ex.y+1,ex2.x+1,ex2.y+1);
		g.drawLine(ex.x+1,ex.y+1,ex2.x+1,ex2.y-1);
		g.drawLine(ex.x+1,ex.y+1,ex2.x-1,ex2.y-1);
		g.drawLine(ex.x+1,ex.y-1,ex2.x+1,ex2.y+1);
		g.drawLine(ex.x-1,ex.y-1,ex2.x-1,ex2.y-1);
		g.drawLine(ex.x-1,ex.y-1,ex2.x-1,ex2.y+1);
		g.drawLine(ex.x-1,ex.y-1,ex2.x+1,ex2.y+1);
		g.drawLine(ex.x-1,ex.y+1,ex2.x+1,ex2.y+1);
		g.drawLine(ex.x+1,ex.y-1,ex2.x+1,ex2.y-1);
		g.drawLine(ex.x-1,ex.y+1,ex2.x-1,ex2.y+1);
	}
	public static void drawLine(Graphics g,java.awt.Point p1,java.awt.Point p2,int width,Color c)
	{
		int w2=width/2;
		g.setColor(c);
		if(p2.y<=p1.y)
			g.fillPolygon(new int[] {p1.x-w2,p2.x-w2,p2.x+w2,p1.x+w2},new int[] {p1.y-w2,p2.y-w2,p2.y+w2,p1.y+w2},4);
		else
			g.fillPolygon(new int[] {p1.x-w2,p2.x-w2,p2.x+w2,p1.x+w2},new int[] {p1.y+w2,p2.y+w2,p2.y-w2,p1.y-w2},4);
	}
	public static void drawLine(Graphics g,java.awt.Point p1,java.awt.Point p2,int width)
	{
		drawLine(g,p1,p2,width,g.getColor());
	}
	public static void drawThickLine(Graphics g,java.awt.Point ex,java.awt.Point ex2)
	{
		drawThickLine(g,ex,ex2,g.getColor());
	}
	public static void surfaceFill(Canvas gp,Graphics g,java.awt.Point p1,java.awt.Point p2,Angle ang,Color c)
	{
		surfaceFill(gp,g,p1,p2,ang,c,'r');
	}
	public static void surfaceFill(Canvas g,Graphics gp,java.awt.Point p1,java.awt.Point p2,Angle ang,char side)
	{
		surfaceFill(g,gp,p1,p2,ang,gp.getColor(),side);
	}
	public static void surfaceFill(Canvas g,Graphics gp,java.awt.Point p1,java.awt.Point p2,Angle ang)
	{
		surfaceFill(g,gp,p1,p2,ang,gp.getColor(),'r');
	}
	public static void drawAngleArc(Graphics g,GLine l1,GLine l2,Color c)
	{
		g.setColor(c);
		Angle arca=l1.angleWith(l2);
		int v=(int)arca.v;
		v-=184;
		v%=180;
		if(v>0)
			v=180-v;
		else
			v=-180-v;
		X.sopln("Angle: "+v,"yellow");
		maths.Point ins=l1.intersect(l2);
		maths.Point cut=l1.pointsOnLine(ins,60)[0];
		// 1-6 : 3=4="60" 6=-arca.v +/-?
	}
	public static void drawAngleArc(Graphics g,GLine l1,GLine l2)
	{
		drawAngleArc(g,l1,l2,g.getColor());
	}
	public static void drawAngleArc(Graphics g,java.awt.Point p1,java.awt.Point p2,java.awt.Point p3,java.awt.Point p4,Color c)
	{
		GLine l1=Maths.gLine(p1,p2);
		GLine l2=Maths.gLine(p3,p4);
		drawAngleArc(g,l1,l2,c);
	}
	public static void drawAngleArc(Graphics g,java.awt.Point p1,java.awt.Point p2,java.awt.Point p3,java.awt.Point p4)
	{
		drawAngleArc(g,p1,p2,p3,p4);
	}
	public static void fillRect(Graphics g,Rectangle r,Color c)
	{
		g.setColor(c);
		g.fillRect((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
	}
	public static void fillRect(Graphics g,Rectangle r)
	{
		fillRect(g,r,g.getColor());
	}
	public static void drawCircle(Graphics g,Circle c,Color col)
	{
		AWT.tracePath(g,c.path,col);
	}
	public static void drawCircle(Graphics g,Circle c)
	{
		AWT.tracePath(g,c.path);
	}
	public static void drawHollowCircle(Graphics g,Circle c,int er,Color col)
	{
		g.setColor(col);
		Circle cur=c;
		for(double i=0;i<er;i+=0.5)
		{
			cur=new Circle(c.centre,c.rad+i);
			AWT.tracePath(g,cur.path);
		}
	}
	public static void drawHollowCircle(Graphics g,Circle c,int ir)
	{
		drawHollowCircle(g,c,ir,g.getColor());
	}
	public static void fillCircle(Graphics g,Circle c,Color col)
	{
		g.setColor(col);
		Rectangle re=c.getBounds();
		g.fillOval((int)re.getX(),(int)re.getY(),(int)re.getWidth(),(int)re.getHeight());
	}
	public static void fillCircle(Graphics g,Circle c)
	{
		fillCircle(g,c,g.getColor());
	}
}
