package jtools.gaming;
import commons.X;
import java.awt.*;
import maths.geom3D.Vector;
import java.awt.*;
import java.awt.image.*;

public abstract class Particle extends DynamicObject
{
  final double mass;
  public Color col=Color.black;
  Vector vel=new Vector(0,0,0);
  public static final double g=9.8;
  double rad=2;

  public Particle(double ra) {this(ra,0);}
  public Particle(double ra, double m) {super(new GameDimension((int)(2*ra),(int)(2*ra))); rad=ra; mass=m;}
  public Particle(Color c) {this(2); col=c;}
  public Particle(double m,Color c) {super(new GameDimension(4,4)); rad=2;mass=m; col=c;}
  public Particle(double ra, double m,Color c) {this(ra,m); col=c;}

  public void setVelocity(Vector v) {vel=v;}
  public Vector getVelocity() {return vel;}

  public Object onExit() {return null;}
  public void perLoop()
  {
    moveX(vel.getX());
    moveY(vel.getY());
    moveZ(vel.getZ());
    vel=new Vector(vel.getX(),vel.getY()-mass*g,vel.getZ());
    inLoop();
    if(terminateCondition())
      this.stop();
  }

  public Image getDrawable()
  {
    BufferedImage bi=new BufferedImage((int)(2*rad),(int)(2*rad),BufferedImage.TYPE_INT_ARGB);
    for(int i=0;i<2*rad;i++)
    {
      for(int j=0;j<2*rad;j++)
      {
        if(i*i+j*j<=rad*rad)
          bi.setRGB(i,j,col.getRGB());
      }
    }
    return bi;
  }
  public void pack() {}
  public abstract boolean terminateCondition();
  public void inLoop() {}
}
