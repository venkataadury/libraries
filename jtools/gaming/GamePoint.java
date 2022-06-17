package jtools.gaming;
//import 

public class GamePoint
{
  public double xP=0,yP=0,zP=0;
  public static final double ADJ=0.12;
  public GamePoint() {}
  public GamePoint(double x) {xP=x;}
  public GamePoint(double x,double y) {this(x); yP=y;}
  public GamePoint(double x,double y,double z) {this(x,y); zP=z;}
  public GamePoint(GamePoint pt) {xP=pt.xP; yP=pt.yP; zP=pt.zP;}
  
  public java.awt.Point get2DPoint()
  {
	return new java.awt.Point((int)(xP-zP*ADJ),(int)(yP-zP*ADJ));
  }
  public double getX() {return xP;}
  public double getY() {return yP;}
  public double getZ() {return zP;}
}
