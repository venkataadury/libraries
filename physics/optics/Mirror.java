package physics.optics;
import commons.X;
import maths.Maths;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Mirror implements Optical
{
	private double rR=0; // Reciprocal of Radius of Curvature
	int loc=0;
	
	public Mirror() {}
	public Mirror(double rad) {rR=1/rad;}
	
	public void setX(int x) {loc=x;} public int getX() {return loc;}
	public Color getColor() {return null;} public void setColor(Color c) {}
	
	public void draw(BufferedImage img,int offset,int yL,double eO) {draw(img.getGraphics(),offset,yL,eO);}
	public void draw(java.awt.Graphics g,int offset,int yL,double eO)
	{
		double r=1/rR;
		final int hgt=160;
		int ra=(int)Math.round(r);
		if(Maths.isInf(r))
			g.drawLine(loc-offset,yL-hgt/2,loc-offset,yL+hgt/2);
		else
		{
			X.sopln((loc+ra*2)+","+(yL-Math.abs(ra))+","+(-2*ra)+","+Math.abs(ra)*2);
			if(ra<0)
			{
				ra=-100;
				g.drawArc(loc+ra*2,yL-Math.abs(ra),-2*ra,Math.abs(ra)*2,30,-60);
			}
			else
			{
				ra=100;
				g.drawArc(loc,yL-Math.abs(ra),2*ra,Math.abs(ra)*2,150,60);
			}
		}
		g.drawString("r="+r+" px",(int)Math.round(loc-(r*20)/Math.abs(r)),yL+100);
	}
	public void draw(Graphics g,int offset,int yL) {draw(g,offset,yL,1);}
	
	public double imageDistance(double u) {return imageDistance(u,1);}
	public double imageDistance(double u,double rI)
	{
		double rF=2*rR;
		// 1/v + 1/u = 1/f
		return 1/(rF-1/u);
	}
}
