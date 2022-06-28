package physics.collisions;
import maths.Maths;
import commons.X;
import maths.geom3D.Vector;
import maths.geom3D.Direction;

public class LJAlgorithm extends MotionAlgorithm
{
	double rate;
	public static final double rm=1;
	public LJAlgorithm() {this(1);}
	public LJAlgorithm(double r) {rate=r;}
	
	public void algorithm(Particle p1,Particle p2,double dist)
	{
		Vector dir=p1.getVector(p2);
		double mag=0.12*rate*(Math.pow(rm,6)*(1/Math.pow(dist,5))-Math.pow(rm,12)*(1/Math.pow(dist,11)));
		dir.setMag(mag);
		p1.vel=p1.vel.add(dir);
		p2.vel=p2.vel.add(dir.neg());
	}
	
}
