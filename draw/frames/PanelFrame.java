package draw.frames;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import commons.X;

public abstract class PanelFrame extends JFrame
{
	protected int W=800,H=600,pSize;
	protected final GraphicsConfiguration gCon;
	public static final GraphicsDevice GraphDev=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	protected Container panel;
	private BufferedImage img;
	
	protected Color bgcol=Color.black;
	
	public PanelFrame() {this(800,600);}
	public PanelFrame(int w,int h)
	{
		super(GraphDev.getDefaultConfiguration());
		setSize(w,h);
		setup();
		bgcol=Color.black;
		gCon=getGraphicsConfiguration();
	}
	
	private void setup()
	{
		img=new BufferedImage(W,H,BufferedImage.TYPE_INT_ARGB);
		this.setTitle("Frame with Panel");
		panel=new Container();
		panel.setBounds(W+5,0,pSize-10,H);
		panel.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(panel);
		this.setVisible(true);
	}
	
	public void setSize(int w,int h)
	{
		W=w; H=h;
		pSize=W/5;
		super.setSize(W+pSize,H);
	}
	public void setBackground(Color bg) {bgcol=bg;}
	public Color getBackground() {return bgcol;}
	public void setPanelBackground(Color bg) {panel.setBackground(bg);}
	public Color getPanelBackground() {return panel.getBackground();}
	
	public void paintComponent(Graphics g)
	{
		if(g==null)
		{
			X.sopln("NULL Graphics","red");
			return;
		}
		g.drawImage(img,0,0,null);
		try {ImageIO.write(img,"PNG",new java.io.File("/home/venkata/img.png"));} catch(Exception e) {e.printStackTrace(); return;}
	}
	
	public Graphics getImageGraphics() {return img.getGraphics();}
	public void preDraw(Graphics g)
	{
		g.setColor(bgcol);
		g.fillRect(0,0,W,H);
	}
	public abstract void draw(Graphics g);
	public final void redraw() {paintComponent(this.getGraphics());}
	public final void update()
	{
		preDraw(img.getGraphics());
		draw(img.getGraphics());
		redraw();
	}
}
