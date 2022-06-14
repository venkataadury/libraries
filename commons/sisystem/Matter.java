package commons.sisystem;
import commons.X;

public class Matter extends ScalarUnit
{
	public static final String unit="mol";
	public static final Type TYPE=new Type(unit);
	
	public Matter(double V)
	{
		super(V+" "+unit);
	}
	public Matter(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Matter(ScalarUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}