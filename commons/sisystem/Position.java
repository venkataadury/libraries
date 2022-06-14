package commons.sisystem;
import commons.X;

public class Position extends VectorUnit
{
	public static final String unit="m";
	public static final Type TYPE=new Type(unit);
	
	public Position(double V)
	{
		super(V+"i "+unit);
	}
	public Position(double xC,double yC)
	{
		super(xC+"i +"+yC+"j "+unit);
	}
	public Position(double xC,double yC,double zC)
	{
		super(xC+"i +"+yC+"j "+zC+"k "+unit);
	}
	public Position(String in)
	{
		super(in);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
	public Position(VectorUnit ext)
	{
		super(ext);
		if(!type.equals(TYPE))
			throw new RuntimeException("Units do not match: Needed "+TYPE+", but found "+type);
	}
}