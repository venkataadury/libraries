package jtools.positionedgames.particle;
import commons.X;
import java.awt.*;
import maths.geom3D.*;

public class Particle
{
	Color c;
	Point3D p;
	Vector vel,acc; //pixels/sec , pixels/sec^2
	public final double mass=1;
	public PTerrain terr=null;
	public final long ID;
	private static long cN=0;
	
	public static final Color COLOR_DEFAULT=Color.white;
	public Particle() {this(0,0,COLOR_DEFAULT);}
	public Particle(int x,int y) {this(x,y,COLOR_DEFAULT);}
	public Particle(int x,int y,Color col)
	{
		ID=cN++;
		p=new Point3D(x,y);
		c=col;
	}
	
	public boolean equals(Particle p2) {return ID==p2.ID;}
	
	public Vector getVelocity() {return vel;}
	public double getSpeed() {return vel.getMag();}
	public Vector getAcceleration() {return acc;}
	
	public void setTerrain(PTerrain pt) {terr=pt;}
	public PTerrain getTerrain() {return terr;}
	
	public Color getColor() {return c;}
	public void setColor(Color clr) {c=clr;}
	public Point3D getPos() {return p;}
	public void setPos(int x,int y) {p=new Point3D(x,y);}
	public void setPos(Point3D pt) {p=pt;}
	
	public static void move(double time,PTerrain t) //time in sec
	{
		
	}
}
