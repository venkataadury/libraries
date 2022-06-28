package physics.collisions;
import commons.X;
import maths.geom3D.*;
import maths.Maths;

public abstract class MotionAlgorithm
{
	public void execute(Particle p,ClosedSystem cs) {execute(p,cs,p.rad*4);}
	public void execute(Particle p,ClosedSystem cs,double vc)//vc=vicinity const (distance to consider)
	{
		singleParticleAlgorithm(p);
		Particle[] ps=cs.getParticles();
		double d;
		for(int i=0;i<ps.length;i++)
		{
			if(ps[i]==p)
				continue;
			d=cs.dist(ps[i],p);
			if(d<vc)
				algorithm(ps[i],p,d);
		}
	}
	public void singleParticleAlgorithm(Particle p1) {}
	public abstract void algorithm(Particle p1,Particle p2,double dist);
}
