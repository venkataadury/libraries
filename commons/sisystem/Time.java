package commons.sisystem;
import commons.X;

public class Time extends ScalarUnit
{
	public static final String unit="s";
	public static final Type TYPE=new Type(unit);
	
	public Time(double V)
	{
		super(V+" "+unit);
	}
	public Time(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Time(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}