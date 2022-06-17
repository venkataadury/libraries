package jtools.gaming;
import draw.S;
import java.awt.Dimension;

public class GameDimension
{
  protected int xL,yL,zL=0;

  public GameDimension() {}
  public GameDimension(int x) {xL=x;}
  public GameDimension(int x,int y) {this(x); yL=y;}
  public GameDimension(int x,int y,int z) {this(x,y); zL=z;}
  public GameDimension(Dimension dim) {this((int)dim.getWidth(),(int)dim.getHeight());}

  public Dimension toDimension2D()
  {
	return new Dimension(xL+(int)Math.abs(zL*GamePoint.ADJ),yL+(int)Math.abs(zL*GamePoint.ADJ));
  }

  public int getWidth() {return xL;}
  public int getHeight() {return yL;}
  public int getDepth() {return zL;}
  public int getAreaXY() {return xL*yL;}
  public int getAreaYZ() {return yL*zL;}
  public int getAreaZX() {return zL*xL;}
}
