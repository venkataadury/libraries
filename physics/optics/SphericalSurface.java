package physics.optics;
import commons.X;
import maths.Maths;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SphericalSurface implements Optical
{
	int loc=0;
	private double rR=0; // rR = Reciprocal of Radius of Curvature
	private double rI=1; //Refractive Index
	private Color clr=Color.blue;
	
	
	public SphericalSurface() {}
	public SphericalSurface(double r) {rR=1/r;}
	public SphericalSurface(double r,double n) {this(r);rI=n;}
	
	public void setColor(Color c) {clr=c;}
	public Color getColor() {return clr;}
	public void setX(int x) {loc=x;} public int getX() {return loc;}
	
	public double imageDistance(double u,double eRI)
	{
		// n2/v - n1/u = (n2-n1)/r
		double rV= (rI-eRI)*rR + eRI/u;
		return 1/rV;
	}
	public double imageDistance(double u) {return imageDistance(u,1);}
	
	@Override
	public void draw(BufferedImage img,int offset,int yL,double eO) {draw(img.getGraphics(),offset,yL,eO);}
	public void draw(Graphics g,int offset,int yL,double eO)
	{
		X.sopln(clr);
		int A=(int)(Math.abs(1-(double)eO/(double)rI)*255);
		X.sop(clr.getRed()+","+clr.getGreen()+","+clr.getBlue()+",");
		X.sopln(A,"red");
		Color fc=new Color(clr.getRed(),clr.getGreen(),clr.getBlue(),A);
		Color cT=g.getColor();
		g.setColor(clr);
		double r=1/rR;
		int ra=(int)Math.round(r);
		g.fillOval(loc-offset,yL-ra,2*ra,2*ra);
		g.setColor(cT);
	}
	public void draw(java.awt.Graphics g,int offset,int yL) {draw(g,offset,yL,1);}
}
