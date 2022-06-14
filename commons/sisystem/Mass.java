package commons.sisystem;
import commons.X;

public class Mass extends ScalarUnit
{
	public static final String unit="kg";
	public static final Type TYPE=new Type(unit);
	
	public Mass(double V)
	{
		super(V+" "+unit);
	}
	public Mass(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Mass(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}