package physics.collisions;
import maths.geom3D.*;
import maths.Maths;
import commons.X;
import java.awt.Color;
import java.awt.Graphics;

public class Particle
{
	public int rad=4;
	public double mass=0.0;
	public Color myCol=Color.red;
	public Point3D loc=new Point3D(0,0,0);
	public Vector vel=new Vector(0,0,0);
	
	public Particle(double x,double y,double z) {loc=new Point3D(x,y,z);}
	public Particle(double x,double y,double z,Vector v) {this(x,y,z);vel=v;}
	
	Vector getVel() {return vel;}
	public double getX() {return loc.x;}
	public double getY() {return loc.y;}
	public double getZ() {return loc.z;}
	void translate(double time)
	{
		Vector temp=vel.mul(time/1000);
		loc.x+=temp.getX();
		loc.y+=temp.getY();
		loc.z+=temp.getZ();
	}
	void addVel(Vector v) {vel=vel.add(v);}
	public double dist(Particle p2) {return Math.sqrt(Math.pow(getX()-p2.getX(),2)+Math.pow(getY()-p2.getY(),2)+Math.pow(getZ()-p2.getZ(),2));}
	public Vector getVector(Particle p2) {return new Vector(p2.getX()-getX(),p2.getY()-getY(),p2.getZ()-getZ());}
	
	public void draw(Graphics g)
	{
		g.setColor(myCol);
		g.fillOval((int)(loc.x-rad),(int)(loc.y-rad),(int)(2*rad),(int)(2*rad));
	}
}
