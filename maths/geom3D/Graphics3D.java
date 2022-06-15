package maths.geom3D;
import java.awt.*;
import maths.Maths;
import commons.X;
import java.awt.image.*;

public class Graphics3D extends Canvas
{
	private int W,H;
	private Color fgcol=Color.black;
	private Color bgcol=Color.white;
	private BufferedImage bimg;
	protected Graphics g;
	private int oX=0,oY=0;
	public int offsetX=400,offsetY=300;
	
	private int xL,yL,zL;
	private Vector nV;
	
	public Graphics3D() {this(800,600);}
	public Graphics3D(int w,int h) {super();super.setSize(w,h);super.setVisible(true);setBGCol(Color.white);W=w;H=h;}
	
	public void setBGCol(Color clr) {bgcol=clr;super.setBackground(clr);}
	public void setFGCol(Color clr) {setColor(clr);}
	public void setColor(Color clr) {fgcol=clr;}
	public void plot(Point3D pt)
	{
		Point p=Core3D.get2DPoint(pt,oX,oY);
		g.drawLine(p.x,p.y,p.x,p.y);
	}
	
	public final void drawInit()
	{
		if(g==null)
		{
			bimg=new BufferedImage(W,H,BufferedImage.TYPE_INT_ARGB);
			g=bimg.createGraphics();
			if(g!=null)
			{
				g.setColor(bgcol);
				g.fillRect(0,0,W,H);
			}
			else
				X.sopln("Failed to create graphics");
			return;
		}
		g.setColor(fgcol);
	}

	public void paint(Graphics g) {}
	public void update(Graphics g) {}
	public void update() {(this.getGraphics()).drawImage(bimg,0,0,null);}
	public void drawLine(Point p1,Point p2) {drawInit();g.drawLine(offsetX+p1.x,offsetY+p1.y,offsetX+p2.x,offsetY+p2.y);
		//X.sopln("DrawLine: "+p1.x+","+p1.y+"\t"+p2.x+","+p2.y);
	}
	public void blot(int x,int y,int z)
	{
		drawLine(new Point3D(x,y,z),new Point3D(x,y,z));
	}
	public void blot(int x,int y,int z,Color c)
	{
		this.setColor(c);
		this.blot(x,y,z);
	}
	public void drawLine(Point3D p1,Point3D p2) {drawLine(P2D(p1),P2D(p2));}
	public void drawShape(Shape3D shp) {draw(shp);}
	public void draw(Shape3D shp) {drawOutline(shp);}
	public void drawOutline(Shape3D shp)
	{
		drawInit();
		Path3D pth=shp.getPath();
		pth.resetPointerPosition();
		PathPoint ptp=null;
		Point3D pt1=null,pt2=null;
		while(pth.hasNext())
		{
			ptp=pth.next();
			if(ptp.getCont())
			{
				if(pt1==null)
					pt1=ptp.getPoint();
				else
				{
					pt2=ptp.getPoint();
					drawLine(pt1,pt2);
					pt1=pt2;
				}
			}
			else
			{
				pt2=ptp.getPoint();
				drawLine(pt1,pt2);
				pt1=null;
			}
		}
	}
	public void drawSurface(Surface sf,Cuboid limit)
	{
		drawInit();
		int xP=(int)limit.x,yP=(int)limit.y,zP=(int)limit.z;
		xL=(int)(limit.x+limit.w);yL=(int)(limit.y+limit.h);zL=(int)(limit.z+limit.d);
		for(int i=xP;i<=xL;i++)
		{
			for(int j=yP;j<=yL;j++)
			{
				for(int k=zP;k<=zL;k++)
				{
					if(sf.contains(i,j,k))
						blot(i,j,k);
				}
			}
		}
	}
	public void fillShape(Shape3D shp,Color olC)
	{
		Surface[] sa=shp.getAllSurfaces();
		for(int i=0;i<sa.length;i++)
			this.drawSurface(sa[i],shp.getBounds());
		Color temp=g.getColor();
		this.setColor(olC);
		X.sopln();
		this.drawShape(shp);
		this.setColor(temp);
	}
	public void clearScreen()
	{
		g=null;
		drawInit();
	}
	
	public Point3D LOSCMP(Point3D p1,Point3D p2) //Line of Sight comparison for two overlapping points on 2d plane
	{
		int cmp=(int)((p1.x-p2.x)*Maths.sin(oX)) + (int)(p1.z-p2.z);
		if(cmp<0)
			return p1;
		else
			return p2;
	}
	
	public Graphics getGraphics() {return super.getGraphics();}
	
	public Point P2D(Point3D pt) {return Core3D.get2DPoint(pt,oX,oY);}
	
	public void setXOrient(int xo) {oX=xo;}
	public void setYOrient(int yo) {oY=yo;}
	public void setOrient(int xo,int yo) {oX=xo;oY=yo;}
}
