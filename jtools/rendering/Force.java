package jtools.rendering;
import maths.geom3D.Vector;
import maths.Maths;
import commons.X;

public class Force extends Vector
{
	GameObject obj1,obj2;
	GamePoint p1=new GamePoint(),p2=new GamePoint(); //with respect to COM of each object
	
	public Force(Vector v,GameObject go1)
	{
		super(v);
		obj1=go1;
	}
	public Force(Vector v,GameObject go1,GamePoint pt)
	{
		this(v,go1);
		p1=pt;
	}
	public Force(Vector v,GameObject go1,GameObject go2)
	{
		super(v);
		obj1=go1;
		obj2=go2;
	}
	public Force(Vector v,GameObject go1,GamePoint pt1,GameObject go2)
	{
		this(v,go1,go2);
		p1=pt1;
	}
	public Force(Vector v,GameObject go1,GameObject go2,GamePoint pt2)
	{
		this(v,go1,go2);
		p2=pt2;
	}
	public Force(Vector v,GameObject go1,GamePoint pt1,GameObject go2,GamePoint pt2)
	{
		this(v,go1,go2);
		p1=pt1; p2=pt2;
	}
}
