package commons.sisystem;
import commons.X;

public class Length extends ScalarUnit
{
	public static final String unit="m";
	public static final Type TYPE=new Type(unit);
	
	public Length(double V)
	{
		super(V+" "+unit);
	}
	public Length(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Length(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}