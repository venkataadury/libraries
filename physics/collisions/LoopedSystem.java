package physics.collisions;
import maths.geom3D.Vector;
import maths.Maths;

public class LoopedSystem extends ClosedSystem
{
	public LoopedSystem() {this(800,800);}
	public LoopedSystem(int w,int h) {super(w,h);}
	public void boundary(Particle p)
	{
		if(p.getX()>width)
			p.loc.x=p.loc.x%width;
		if(p.getX()<0)
			p.loc.x=width+p.loc.x%width;
		if(p.getY()>height)
			p.loc.y=p.loc.y%height;
		if(p.getY()<0)
			p.loc.y=height+p.loc.y%height;
	}
	public double dist(Particle p1,Particle p2) {return p1.dist(p2);}
}
