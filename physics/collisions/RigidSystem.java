package physics.collisions;
import maths.geom3D.Vector;
import maths.Maths;

public class RigidSystem extends ClosedSystem
{
	public RigidSystem() {this(800,800);}
	public RigidSystem(int w,int h) {super(w,h);}
	public void boundary(Particle p)
	{
		if(p.getX()>width)
		{
			p.loc.x=width-(p.loc.x%width);
			p.vel=p.vel.add(p.vel.getXComponent().neg().mul(2));
		}
		if(p.getX()<0)
		{
			p.loc.x=-p.loc.x;
			p.vel=p.vel.add(p.vel.getXComponent().neg().mul(2));
		}
		if(p.getY()>height)
		{
			p.loc.y=height-(p.loc.y%height);
			p.vel=p.vel.add(p.vel.getYComponent().neg().mul(2));
		}
		if(p.getY()<0)
		{
			p.loc.y=-p.loc.y;
			p.vel=p.vel.add(p.vel.getYComponent().neg().mul(2));
		}
	}
	public double dist(Particle p1,Particle p2) {return p1.dist(p2);}
}
