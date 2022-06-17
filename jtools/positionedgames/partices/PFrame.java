package jtools.positionedgames.particle;
import commons.X;
import maths.geom3D.Point3D;
import java.awt.*;
import java.awt.image.BufferedImage;
import draw.RefreshingFrame;
import draw.ImageWorks;

public class PFrame extends RefreshingFrame
{
	private BufferedImage img;
	public PFrame(int w,int h) {this(w,h,RefreshingFrame.FREQ);}
	public PFrame(int w,int h,int r)
	{
		super(w,h,r);
		img=ImageWorks.getEmptyImg(W,H);
	}
	
	public void refresh() {paint(this.getGraphics());}
	
	public void paint(Graphics g)
	{
		g.drawImage(img,0,0,null);
	}
}
