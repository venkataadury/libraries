package physics.collisions;
import maths.Maths;
import commons.X;
import maths.geom3D.Vector;
import maths.geom3D.Direction;

public class GravityAlgorithm extends MotionAlgorithm
{
	double rate;
	public GravityAlgorithm() {this(9.8);}
	public GravityAlgorithm(double r) {rate=r;}
	
	public void algorithm(Particle p1,Particle p2,double dist) {}
	/*{
		Vector dir=p1.getVector(p2);
		dir.setMag(rate);
		double s1=p1.vel.getMag();
		double s2=p2.vel.getMag();
		double d1=dir.getAngleWith(p1.vel);
		double d2=dir.getAngleWith(p2.vel);
		if(d1<=ANGLE || d1>=180-ANGLE)
		{
			p1.vel=p1.vel.add(dir);
			//p1.vel=dir;
			p1.vel.setMag(s1);
		}
		if(d2<=ANGLE || d2>=180-ANGLE)
		{
			p2.vel=p2.vel.add(dir);
			//p2.vel=dir.neg();
			p2.vel.setMag(s1);
		}
	}*/
	public void singleParticleAlgorithm(Particle p1) {p1.vel=p1.vel.add(new Vector(0,rate*p1.mass,0));}
}
