package commons.structures.chain;
import commons.structures.Data;
import commons.X;
import upgrade.ArrayFx;
import java.awt.*;
import java.awt.event.*;
import draw.AWT;

public class ChainViewer extends Frame implements MouseListener
{
	Node st=null;
	public static final int W=800,H=600;
	private int offsetX=0,offsetY=0;
	
	public ChainViewer() {}
	public ChainViewer(Node n)
	{
		st=n;
		setup();
	}
	
	private void setup()
	{
		this.setSize(W,H);
		this.setResizable(false);
		this.addWindowListener(AWT.WINPROPS);
		this.addMouseListener(this);
		this.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		if(g==null)
		{
			X.sopln("Graphics NULL","red");
			return;
		}
		st.undraw();
		g.setColor(Color.black);
		g.fillRect(0,0,W,H);
		g.setColor(Color.white);
		st.draw(g,100-offsetX,200-offsetY);
	}
	
	public void update() {X.sopln("Reload","yellow");paint(this.getGraphics());}
	
	//From MouseListener
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) 
	{
		java.awt.Point pt=e.getPoint();
		if(pt.y>5*H/6)
			offsetY+=30;
		if(pt.x>7*W/8)
			offsetX+=30;
		if(pt.y<H/6)
			offsetY-=30;
		if(pt.x<W/8)
			offsetX-=30;
		update();
	}
}
