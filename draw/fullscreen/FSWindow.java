package draw.fullscreen;
import commons.X;
import maths.Maths;
import draw.AWT;
import java.awt.*;
import java.awt.event.*;

public abstract class FSWindow extends Frame implements MouseListener
{
	private final Button EXIT=new Button("X");
	public static final int WIDTH=1600,HEIGHT=900;
	public final int resX,resY;
	public Color bgcol=Color.black;
	
	public FSWindow() {this(WIDTH,HEIGHT);}
	public FSWindow(int x) {this(x,(HEIGHT*x)/WIDTH);}
	public FSWindow(int w,int h)
	{
		resX=w; resY=h;
		this.setSize(w,h);
		this.setUndecorated(true);
		this.addWindowListener(AWT.WINPROPS);
		this.addMouseListener(this);
		setup();
	}
	
	private void setup()
	{
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(new DisplayMode(WIDTH,HEIGHT,DisplayMode.BIT_DEPTH_MULTI,60));
		this.setVisible(true);
		X.halt(0.5);
		EXIT.setBounds(resX-40,40,20,20);
		EXIT.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {quitAction();}});
		EXIT.setVisible(true);
		this.add(EXIT);
		this.add(AWT.Lempty);
		
		
	}
	
	public void paint(Graphics g)
	{
		if(g==null)
		{
			X.sopln("NULL Graphics","red");
			return;
		}
		g.setColor(bgcol);
		g.fillRect(0,0,resX,resY);
		draw(g);
	}
	
	public void update()
	{
		paint(this.getGraphics());
	}
	
	//Overridable Methods
	protected void quitAction() {this.dispose();}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {update();}
	
	//Abstract Methods
	protected abstract void draw(Graphics g);
}
