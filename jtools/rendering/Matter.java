package jtools.rendering;
import commons.X; import commons.Y;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.*;
import commons.X;
import maths.Maths;
import maths.geom3D.Vector;
import maths.geom3D.Point3D;

public class Matter
{
	public static final int templateCol=Color.black.getRGB();
	public GamePoint pos=new GamePoint(); //position of COM
	public int pNum=0; //No of pixels in image (Density*pNum=mass)
	public final BufferedImage represent;
	public double I=1; //Moment of Inertia
	public final int W,H;
	public java.awt.Point COM=new java.awt.Point(0,0); // Position of COM wrt image
	public double MOI=1; //Moment of Inertia
	Color replace=new Color(templateCol); // To replace template color
	
	public Matter(Image img)
	{
		represent=(BufferedImage)img;
		W=represent.getWidth(null);
		H=represent.getHeight(null);
		processImg();
	}
	
	public void processImg()
	{
		if(represent==null)
			return;
		X.sopln(represent.getRGB(0,0));
		int tR=0;
		int xC=0,yC=0,pC=0;
		for(int i=0;i<W;i++)
		{
			for(int j=0;j<H;j++)
			{
				tR=represent.getRGB(i,j);
				if(tR==0)
					continue;
				if(tR==templateCol)
					represent.setRGB(i,j,replace.getRGB());
				xC+=i; yC+=j; pC++;
			}
		}
		pNum=pC;
		COM=new java.awt.Point(Math.round(xC/pC),Math.round(yC/pC));
		MOI=0;
		for(int i=0;i<W;i++)
		{
			for(int j=0;j<H;j++)
			{
				if(represent.getRGB(i,j)==0)
					continue;
				MOI+=(i-COM.x)*(i-COM.x)+(j-COM.y)*(j-COM.y);
			}
		}
		
	}
	
	public void setPosition(Point3D pt) {pos=new GamePoint(pt);}
	public GamePoint getPosition() {return pos;}
}
