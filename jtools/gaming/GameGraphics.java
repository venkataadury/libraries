package jtools.gaming;
import java.awt.*;
import jtools.time.Pulse;
import java.awt.image.BufferedImage;

public class GameGraphics extends Thread
{
  Dimension dim=null;
  private Pulse p;
  private BufferedImage img;
  private BufferedImage bimg,fbimg;
  Graphics gfx;
  private Color bgCol=Color.white;
  final GameScreen GS;
  private int freq=40;
  Terrain ter=null;

  public GameGraphics(GameScreen g) {GS=g;}
  public GameGraphics(GameScreen g,int x,int y)
  {
	   dim=new Dimension(x,y);
	    img();
	     GS=g;
  }
  public GameGraphics(GameScreen g,GameDimension gd) {this(g,gd.toDimension2D());}
  public GameGraphics(GameScreen g,Dimension ext) {dim=ext; img(); GS=g;}

  private void img()
  {
	   img=new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),BufferedImage.TYPE_INT_ARGB);
	    bimg=new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),BufferedImage.TYPE_INT_ARGB);
	     Graphics gx=bimg.createGraphics();
	      //((Graphics2D)gx).setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
	       fbimg=new BufferedImage(bimg.getColorModel(),bimg.copyData(null),bimg.getColorModel().isAlphaPremultiplied(),null);
	        gfx=img.createGraphics();
  }

  public void start() {super.start();}
  public void run()
  {
	 p=new Pulse(freq) {
	  public void objective()
	  {
		GS.updateScreen(img);
		gfx.setColor(Color.white);
		gfx.fillRect(0,0,(int)dim.getWidth(),(int)dim.getHeight());
		gfx.drawImage(fbimg,0,0,null);
	  }
	  public Object onExit()
	  {
		try{this.finalize();}catch(Throwable e) {}
		return null;
	  }
	};
	p.start();
  }

  public void setFrequency(int f) {freq=f;}
  public void setBackgroundColor(Color c) {bgCol=c;}
  public void stopAnim()
  {
	p.interrupt();
	p=null;
  }
  public Image getImage()
  {
	return img;
  }
  public void drawObject(GameObject obj)
  {
	   Point pt=obj.getPosition();
	    gfx.drawImage(obj.getImage(),pt.x,pt.y+obj.getImage().getHeight(null),obj.getImage().getWidth(null),-obj.getImage().getHeight(null),null);
  }
  public void drawImage(Image img,int x,int y)
  {
	gfx.drawImage(img,x,y+img.getHeight(null),img.getWidth(null),-img.getHeight(null),null);
  }
  public void pasteImage(Image img,int x,int y)
  {
	gfx.drawImage(img,x,y,null);
  }
  public void setTerrain(Terrain t)
  {
	ter=t;
	bimg=new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),BufferedImage.TYPE_INT_ARGB);
	Graphics gx=bimg.createGraphics();
	gx.drawImage(t.toImage(),0,0,null);
	fbimg=new BufferedImage(bimg.getColorModel(),bimg.copyData(null),bimg.getColorModel().isAlphaPremultiplied(),null);
  }
  public void updateTerrain()
  {
	bimg=new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),BufferedImage.TYPE_INT_ARGB);
	Graphics gx=bimg.createGraphics();
	gx.drawImage(ter.toImage(),0,0,null);
	fbimg=new BufferedImage(bimg.getColorModel(),bimg.copyData(null),bimg.getColorModel().isAlphaPremultiplied(),null);
  }
}
