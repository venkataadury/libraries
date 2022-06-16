package draw.logo;
import commons.*;
import maths.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Sheet 
{
	public static Sheet[] pool=new Sheet[0];
	protected BufferedImage img;
	public static Sheet BASE;
	public int pivX=0,pivY=0;
	private Rectangle myRect;
	boolean set=false;
	final Graphics2D g;
	private final LOGO window;
	
	public static final void initSheet(int xS,int yS,LOGO src)
	{
		BASE=new Sheet(-xS/2,-yS/2,true,src);
	}
	public Sheet(int x,int y,LOGO src)
	{
		pivX=x;
		pivY=y;
		window=src;
		img=new BufferedImage(window.xSize,window.ySize,BufferedImage.TYPE_INT_RGB);
		myRect=new Rectangle(pivX,pivY,window.xSize,window.ySize);
		appendSheet(this);
		g=img.createGraphics();
		g.setColor(window.bgcol);
		g.fillRect(0,0,window.xSize,window.ySize);
	}
	public Sheet(int x,int y,boolean dn,LOGO src)
	{
		this(x,y,src);
		if(dn)
			this.drawn();
	}
	
	public static Sheet getSheet(int xP,int yP,LOGO l)
	{
		for(Sheet s :pool)
		{
			if(s.window!=l)
				continue;
			if(s.contains(xP,yP))
				return s;
		}
		return null;
	}
	public Rectangle getRect()
	{
		return myRect;
	}
	public boolean contains(Rectangle2D r2d)
	{
		return (myRect.intersects((int)r2d.getX(),(int)r2d.getY(),(int)r2d.getWidth(),(int)r2d.getHeight()));
	}
	public boolean contains(int xP,int yP)
	{
		return myRect.contains(xP,yP);
	}
	public BufferedImage getImg()
	{
		return img;
	}
	public static void appendSheet(Sheet x)
	{
		Sheet[] ne=new Sheet[pool.length+1];
		for(int i=0;i<ne.length-1;i++)
			ne[i]=pool[i];
		ne[pool.length]=x;
		pool=ne;
	}
	public static void drawLine(int x1,int y1,int x2,int y2,LOGO l)
	{
		Sheet s=getSheet(x1,y1,l);
		//X.sopln(s.pivX+","+s.pivY,"red");
		if(s!=null && s.contains(x2,y2))
			s.drawLineX(x1,y1,x2,y2);
		else
		{
			if(s!=null)
				s.drawn();
			for(Sheet sh : pool)
				sh.drawLineX(x1,y1,x2,y2);
		}
	}
	public static void setCol(Color c,LOGO l)
	{
		for(Sheet s : pool)
		{
			if(s.window==l)
				s.setColor(c);
		}
		//BASE.setColor(c);
		l.penColor=c;
	}
	public static void setBgColor(Color c,LOGO l)
	{
		for(Sheet s : pool)
		{
			if(s.window==l)
				s.setBg(c);
		}
		l.bgcol=c;
	}
	public void setBg(Color c)
	{
		g.setBackground(c);
	}
	public static void fillPolygon(Polygon pg)
	{
		for(Sheet s : pool)
			s.fillPolygon2(pg);
	}
	public void fillPolygon2(Polygon pg)
	{
		if(pg.intersects(myRect))
			this.drawn();
		else
			return;
		pg.translate(-pivX,-pivY);
		g.fillPolygon(pg);
	}
	public void drawLineX(int x1,int y1,int x2,int y2)
	{
		this.drawn();
		g.setColor(window.penColor);
		//X.sopln(g.getColor(),"red");
		g.drawLine(x1-pivX,y1-pivY,x2-pivX,y2-pivY);
		/*X.sop(pivX+","+pivY+"\t","red");
		X.sopln((x1-pivX)+","+(y1-pivY)+","+(x2-pivX)+","+(y2-pivY));*/
	}
	public void setColor(Color c)
	{
		g.setColor(c);
	}
	public static void drawString(String str,int xC,int yC,LOGO l)
	{
		for(Sheet s : pool)
		{
			if(s.window==l)
				s.drawStringX(str,xC,yC);
		}
	}
	public void drawStringX(String s,int xc,int yc)
	{
		this.drawn();
		g.setColor(window.penColor);
		g.drawString(s,xc-pivX,yc-pivY);
	}
	public static void setFont(Font f)
	{
		for(Sheet s : pool)
			s.setFontX(f);
	}
	public void setFontX(Font f)
	{
		g.setFont(f);
	}
	public static void clear()
	{
		for(Sheet s : pool)
			s.clearScreen();
	}
	public void clearScreen()
	{
		Color c=(img.getGraphics()).getColor();
		g.setColor(window.bgcol);
		g.fillRect(0,0,window.xSize,window.ySize);
	}
	public void setByPosition(int x,int y,Color c)
	{
		/*if(c.getRed()==0)
			X.sopln("Warning: to destination: "+(x-pivX)+","+(y-pivY));*/
		img.setRGB(x-pivX,y-pivY,c.getRGB());
	}
	public Color getByPosition(int x,int y)
	{
		Color co= new Color(img.getRGB(x-pivX,y-pivY));
		/*if(co.getRed()==0)
			X.sopln("Warning: Source "+(x-pivX)+","+(y-pivY));*/
		 return co;
	}
	public static void setUpSheet(Sheet s,LOGO l)
	{
		for(int i=-1;i<=1;i++)
		{
			for(int j=-1;j<=1;j++)
			{
				if(!existsSheet(s.pivX+i*l.xSize,s.pivY+j*l.ySize,l))
					createSheet(s.pivX+i*l.xSize,s.pivY+j*l.ySize,l);
			}
		}
		s.set=true;
	}
	public static void fillRectangle(int x,int y,int w,int h)
	{
		for(int i=0;i<pool.length;i++)
			pool[i].fillRecta(x,y,w,h);
	}
	public static void fillCircle(int x,int y,double rad)
	{
		for(int i=0;i<pool.length;i++)
			pool[i].fillCircle2(x,y,rad);
	}
	public void fillCircle2(int x, int y,double rad)
	{
		if(myRect.intersects((int)(x-rad-pivX),(int)(y-rad-pivY),(int)(2*rad),(int)(2*rad)))
			this.drawn();
		g.fillOval((int)(x-rad-pivX),(int)(y-rad-pivY),(int)(2*rad),(int)(2*rad));
	}
	public void fillRecta(int x,int y,int w,int h)
	{
		if(myRect.intersects(x-pivX,y-pivY,w,h))
			this.drawn();
		g.fillRect(x-pivX,y-pivY,w,h);
	}
	public static boolean existsSheet(int pX,int pY,LOGO l)
	{
		for(Sheet s : pool)
		{
			if(s.window!=l)
				continue;
			if(s.pivX==pX && s.pivY==pY)
				return true;
		}
		return false;
	}
	public static void createSheet(int pX,int pY,LOGO l)
	{
		appendSheet(new Sheet(pX,pY,l));
	}
	private void drawn()
	{
		if(set)
			return;
		else
			setUpSheet(this,window);
	}
	public boolean isDrawn()
	{
		return set;
	}
	public static void resetSheets(LOGO l)
	{
		removeAllSheets(l);
		BASE=new Sheet(-l.xSize/2,-l.ySize/2,true,l);
	}
	public static void removeAllSheets(LOGO l)
	{
		Sheet[] p2=pool;
		pool=new Sheet[0];
		for(Sheet s:p2)
		{
			if(s.window==l)
				continue;
			appendSheet(s);
		}
	}
	
	public static void swapPoint(double x1,double y1,double x2,double y2,LOGO l)
	{
		//y1*=-1; y2*=-1;
		Sheet s1=getSheet((int)Math.round(x1),(int)Math.round(y1),l);
		Sheet s2=getSheet((int)Math.round(x2),(int)Math.round(y2),l);
		Color c1=s1.getByPosition((int)Math.round(x1),(int)Math.round(y1));
		s1.setByPosition((int)Math.round(x1),(int)Math.round(y1),s2.getByPosition((int)Math.round(x2),(int)Math.round(y2)));
		s2.setByPosition((int)Math.round(x2),(int)Math.round(y2),c1);
	}
}
