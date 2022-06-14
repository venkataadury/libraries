package commons.sisystem;
import commons.X;

public class Intensity extends ScalarUnit
{
	public static final String unit="Cd";
	public static final Type TYPE=new Type(unit);
	
	public Intensity(double V)
	{
		super(V+" "+unit);
	}
	public Intensity(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Intensity(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}