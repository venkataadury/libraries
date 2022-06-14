package commons.sisystem;
import commons.X;

public class Current extends ScalarUnit
{
	public static final String unit="A";
	public static final Type TYPE=new Type(unit);
	
	public Current(double V)
	{
		super(V+" "+unit);
	}
	public Current(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Current(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}