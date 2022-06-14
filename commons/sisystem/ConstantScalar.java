package commons.sisystem;
import commons.X;

public final class ConstantScalar extends ScalarUnit
{
	public ConstantScalar(double ma,String un)
	{
		super(ma,un);
	}
	public ConstantScalar(String in)
	{
		super(in);
	}
	public ConstantScalar(double m,Type tp)
	{
		super(m,tp);
	}
	public ConstantScalar(ScalarUnit ext)
	{
		super(ext);
	}
	public ConstantScalar(ConstantScalar ext)
	{
		super(ext);
	}
	
	@Override
	public void setMag(double m)
	{
		return;
	}
}