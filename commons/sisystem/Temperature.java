package commons.sisystem;
import commons.X;

public class Temperature extends ScalarUnit
{
	public static final String unit="K";
	public static final Type TYPE=new Type(unit);
	
	public Temperature(double V)
	{
		super(V+" "+unit);
	}
	public Temperature(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Temperature(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}