package physics.optics;
import commons.X;
import upgrade.ArrayFx;
import maths.Maths;
import java.awt.*;
import java.awt.image.BufferedImage;

public class OpticalViewer extends Frame
{
	private Optical[] objects=new Optical[0];
	public static final int W=800,H=600;
	public static final int yL=H/2;
	public double mRI=1;
	Color transp=new Color(0,0,0,(int)0);
	BufferedImage bImg=new BufferedImage(W,H,BufferedImage.TYPE_INT_ARGB);
	
	public OpticalViewer()
	{
		super("Optical Apparatus: Viewer");
		setup();
	}
	public OpticalViewer(double rI)
	{
		this(); mRI=rI;
	}
	
	private void setup()
	{
		this.setSize(W,H);
		this.addWindowListener(draw.AWT.WINPROPS);
		this.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		if(g==null)
		{
			X.sopln("NULL Graphics","red");
			return;
		}
		g.setColor(Color.black);
		g.fillRect(0,0,W,H);
		draw.ImageWorks.makeTransparent(bImg);
		drawOn(bImg.createGraphics());
		g.drawImage(bImg,0,0,null);
	}
	public void drawOn(Graphics g)
	{
		g.setColor(transp);
		g.fillRect(0,0,W,H);
		g.setColor(Color.red);
		for(Optical o : objects)
			o.draw(g,0,yL,mRI);
		g.setColor(Color.white);
		g.drawLine(0,yL,W,yL);
	}
	public void update() {paint(this.getGraphics());}
	
	public void addOptical(Optical o) {objects=ArrayFx.append(objects,o);}
}
