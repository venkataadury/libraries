package draw;
import java.awt.*;
import commons.*;
public class Screen extends Canvas
{
	int w,h;
	
	public Screen() {}
	public Screen(int w,int h)
	{
		this(0,0,w,h);
	}
	public Screen(int x,int y,int w,int h)
	{
		super.setBounds(0,0,w,h);
	}
	public Graphics myG()
	{
		return this.getGraphics();
	}
	public void setC(Color c)
	{
		myG().setColor(c);
	}
	public void setBounds(int x,int y,int w,int h)
	{
		this.w=w;
		this.h=h;
		super.setBounds(x,y,w,h);
	}
	public Color getC()
	{
		return myG().getColor();
	}
	public void fillMe(Color c)
	{
		fillMe(myG(),c);
	}
	public void fillMe(Graphics g,Color c)
	{
		g.setColor(c);
		g.fillRect(0,0,w,h);
	}
	public static void drawHollowRect(Graphics g,Rectangle r1,Rectangle r2,Color c)throws InvalidPointPositionsException
	{
		if(!r1.contains(r2.getX(),r2.getY()) || !r1.contains(r2.getX()+r2.getWidth(),r2.getY()) || !r1.contains(r2.getX()+r2.getWidth(),r2.getY()+r2.getHeight()) || !r1.contains(r2.getX(),r2.getY()+r2.getHeight()))
			throw new InvalidPointPositionsException();
		g.setColor(c);
		int x1=(int)r1.getX(),x2=(int)r2.getX(),y1=(int)r1.getY(),y2=(int)r2.getY();
		int h1=(int)r1.getHeight(),w1=(int)r1.getWidth(),h2=(int)r2.getHeight(),w2=(int)r2.getWidth();
		g.fillPolygon(new int[] {x1,x2,x2+w2,x1+w1},new int[] {y1,y2,y2,y1},4);
		g.fillPolygon(new int[] {x1+w1,x2+w2,x2+w2,x1+w1},new int[] {y1,y2,y2+h2,y1+h1},4);
		g.fillPolygon(new int[] {x1,x2,x2+w2,x1+w1},new int[] {y1+h1,y2+h2,y2+h2,y1+h1},4);
		g.fillPolygon(new int[] {x1,x2,x2,x1},new int[] {y1,y2,y2+h2,y1+h1},4);
	}
	public void drawHollowRect(int x,int y,int w,int h,int th,Color c)throws InvalidPointPositionsException //th=thickness
	{
		Rectangle rA=new Rectangle(x,y,w,h);
		Rectangle rB=new Rectangle(x+th,y+th,w-th,h-th);
		drawHollowRect(rA,rB,c);
	}
	public void drawHollowRect(int x,int y,int w,int h,int th)throws InvalidPointPositionsException //th=thickness
	{
		drawHollowRect(x,y,w,h,th,getC());
	}
	public void drawHollowRect(Rectangle r1,Rectangle r2,Color c)throws InvalidPointPositionsException
	{
		drawHollowRect(myG(),r1,r2,c);
	}
	public void drawHollowRect(Rectangle r1,Rectangle r2)
	{
		drawHollowRect(r1,r2,getC());
	}
	private void WARN(String s)
	{
		X.sopln("/****************************/","yellow");
		X.sopln(s,"red");
		X.sopln("/****************************/","yellow");
	}
}
class InvalidPointPositionsException extends RuntimeException
{
	InvalidPointPositionsException()
	{
		X.sopln("Inaccurate point positions seem to have been given. Please check.","Red");
	}
	InvalidPointPositionsException(String str)
	{
		X.sopln(str,"red");
	}
}
