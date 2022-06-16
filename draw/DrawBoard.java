package draw;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import draw.AWT;

public abstract class DrawBoard extends Frame implements MouseListener,MouseMotionListener
{
  protected int W,H,dw,dh;
  protected Color bgcol=Color.white,fgcol=Color.black;
  protected BufferedImage img;
  protected Graphics imgg=null;
  public DrawBoard(int w,int h,int mw,int mh)
  {
    dw=w; dh=h;
    W=dh+mw; H=dh+mh;
    setup();
  }
  private void setup()
  {
    img=new BufferedImage(dw,dh,BufferedImage.TYPE_INT_ARGB);
    imgg=img.createGraphics();
    super.setSize(W,H);
    super.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.addWindowListener(AWT.WINPROPS);
    super.setVisible(true);
  }

  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}

  public void setBackgroundColor(Color c) {bgcol=c;}
  public void setForegroundColor(Color c) {fgcol=c;}

  public void paint(Graphics g)
  {
    g.setColor(bgcol);
    g.fillRect(0,0,dw,dh);
    g.clearRect(dw,0,W-dw,H);
    g.clearRect(0,dh,W,H-dh);
    g.drawImage(img,0,0,null);
    autoDraw(g);
  }
  public void update() {paint(this.getGraphics());}
  public abstract void autoDraw(Graphics g);
}
