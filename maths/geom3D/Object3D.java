package maths.geom3D;
import commons.X;

public abstract class Object3D extends Object implements Shape3D
{
	protected Object3D() {}
	
	public boolean intersects(Surface sf) {return sf.intersects(this);}
}
