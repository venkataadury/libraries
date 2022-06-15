package maths;
import commons.*;
import draw.*;
import java.awt.*;
public class NumberLine extends Canvas
{
	public double mi,ma;
	public boolean mii=false,mai=false;
	private Frame f1=null;
	public int bs=1;
	public double tmi,tma;
	public double range;
	public NumberSet Ns=null;
	public NumberLine() {}
	public NumberLine(NumberSet ns)
	{
		mi=ns.init;
		ma=ns.end;
		mii=ns.ii;
		mai=ns.ei;
		Ns=ns;
		drawNumberLine();
	}
	private void drawNumberLine()
	{
		f1=new Frame("Number Line");
		this.setBounds(0,0,500,500);
		f1.add(this);
		f1.add(AWT.lempty);
		f1.setSize(500,500);
		f1.setVisible(true);
		f1.setResizable(false);
		f1.addWindowListener(AWT.WINPROPS);
		update();
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,500,500);
		
		tmi=mi;
		tma=ma;
		if(Maths.isInf(ma))
		{
			range=mi;
			tma=100000000;
		}
		else if(Maths.isInf(mi))
		{
			range=ma;
			tmi=0;
		}
		else
			range=ma-mi;
		double bsa=(range/24);
		if(bsa<=1)
			bs=1;
		else
			bs=((int)bsa)+2;
		
		int xco=10;
		int nus=(int)tmi;
		overDraw(g);
		g.setColor(Color.black);
		Draw.drawLine(g,new java.awt.Point(0,250),new java.awt.Point(500,250),4);
		g.setColor(Color.red);
		g.setFont(AWT.SmallFont);
		while(xco<=490)
		{
			g.drawLine(xco,240,xco,260);
			g.drawString(""+nus,xco-2,270);
			nus+=bs;
			xco+=20;
		}
	}
	public void overDraw(Graphics g)
	{
		g.setColor(Color.blue);
		if(Ns.fill)
		{
			int p1,p2;
			p1=xOnLine(tmi);
			p2=xOnLine(tma);
			Rectangle toF=new Rectangle(p1,240,p2-p1,10);
			g.fillRect((int)toF.getX(),(int)toF.getY(),(int)toF.getWidth(),(int)toF.getHeight());
			g.setColor(Color.white);
			if(!Ns.ii)
				Draw.fillCircle(g,new Circle(new maths.Point((int)toF.getX()-2,245),5),Color.white);
			if(!Ns.ei)
				Draw.fillCircle(g,new Circle(new maths.Point((int)(toF.getX()+toF.getWidth()+2),245),5),Color.white);
		}
		else
		{
			for(int i=(int)tmi;i<=tma;i++)
			{
				if(i==(int)tmi && !Ns.ii)
					continue;
				if(i==(int)tma && !Ns.ei)
					continue;
				this.spot(g,i,Color.blue);
			}
		}
	}
	public void spot(Graphics g,int p,Color c)
	{
		Draw.fillCircle(g,new Circle(new maths.Point((int)xOnLine(p),250),5),c);
	}
	public int xOnLine(double val) //To be fixed
	{
		if(val<tmi)
			return -20;
		if(val>tmi+24*bs)
			return 510;
		//val*100/24=x
		double xx=((val-tmi)*100D/(24D*bs));
		double xy=(xx*480D)/100D;
		return (int)(xy+10);
	}
	public void update()
	{
		paint(this.getGraphics());
	}
}
