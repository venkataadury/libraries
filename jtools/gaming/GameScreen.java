package jtools.gaming;
import java.awt.*;
import java.awt.event.*;
import draw.*;

public abstract class GameScreen extends Frame implements MouseListener
{
  private GameDimension SIZE=null;
  public final GameGraphics g;
  
  public GameScreen(int x,int y,int z)
  {
	SIZE=new GameDimension(x,y,z);
	g=new GameGraphics(this,SIZE);
	setup();
  }
  public GameScreen(int x,int y) {this(x,y,0);}
  
  private void setup()
  {
	Dimension d=SIZE.toDimension2D();
	this.setSize(d);
	this.addWindowListener(AWT.WINPROPS);
	this.addMouseListener(this);
	this.setVisible(true);
	g.start();
  }
  
  public void updateScreen(Image gfx)
  {
	(super.getGraphics()).drawImage(gfx,0,SIZE.getHeight(),gfx.getWidth(null),-gfx.getHeight(null),null);
  }
  
  public abstract void onClick(MouseEvent e);
  public abstract void onPressed(MouseEvent e);
  public abstract void onReleased(MouseEvent e);
  
  public void mousePressed(MouseEvent e) {onPressed(e);}
  public void mouseReleased(MouseEvent e) {onReleased(e);}
  public void mouseClicked(MouseEvent e) {onClick(e);}
  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  
  public GameGraphics getGameGraphics()
  {
	return g;
  }
}
