package draw;
import maths.Maths;
import commons.X;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public abstract class GraphFrame extends Frame implements MouseListener
{
	private BufferedImage mainImg;
	public  Graphics imgg;
	private final int H,W;
	
	public GraphFrame(int x,int y)
	{
		W=x; H=y;
		mainImg=new BufferedImage(W,H,BufferedImage.TYPE_INT_ARGB);
		imgg=mainImg.createGraphics();
	}
	
	private void setup()
	{
		/*this.setSize(WIDTH,HEIGHT);
		c=new Canvas();
		c.setBounds(0,0,W,H);
		c.setVisible(true);
		X.halt(1);
		this.addWindowListener(AWT.WINPROPS);
		this.addMouseListener(this);
		//this.add(c);
		this.setVisible(true);*/
		
	}
	
	public abstract void autopaint(Graphics g);
	public void paint(Graphics g)
	{
		if(g==null)
			return;
		//g.clearRect(0,0,W,H);
		g.drawImage(mainImg,0,0,null);
		try {ImageIO.write(mainImg,"PNG",new java.io.File("/home/venkata/img.png"));}  catch(Exception e) {e.printStackTrace(); return;}
		//g.drawImage(mainImg,0,H,W,-H,null);
	}
	
	public void update() {paint(super.getGraphics()); X.sopln("Updated");}
	public Graphics getGraphics() {return imgg;}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e)
	{
		update();
		X.sopln("Updated");
	}
}
