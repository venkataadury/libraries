package commons.sisystem;
import commons.X;
import jtools.gaming.*;

public final class ConstantVector extends VectorUnit
{
	public ConstantVector(double mag,String un,GameDirection d)
	{
		super(mag,un,d);
	}
	public ConstantVector(GameVector gv,String un)
	{
		super(gv,un);
	}
	public ConstantVector(GameVector gv,Type tp)
	{
		super(gv,tp);
	}
	public ConstantVector(VectorUnit ext)
	{
		super(ext);
	}
	public ConstantVector(String in)
	{
		super(in);
	}
	public ConstantVector(ConstantVector ext)
	{
		super(ext);
	}
	
	public void setMag(double m) {}
	
}