package commons.sisystem;
import commons.X;
import commons.Y;
import java.util.HashMap;
import maths.functions.Function;
//import jtools.gaming.GameVector;

public abstract class Unit extends Object
{
	protected Type type=null;
	protected double mag=0;
	public static HashMap<String,Function> CONV=new HashMap<String,Function>();
	public static HashMap<String,String> TYPES=new HashMap<String,String>();
	
	protected Unit(double M,Type t)
	{
		mag=M;
		type=t;
	}
	protected Unit(String in)
	{
		mag=maths.Maths.round(Type.getUnitValue(in),6);
		type=new Type(Y.cut(in,' ',2).trim());
	}
	
	public double getMag() {return mag;}
	public void setMag(double ma) {mag=ma;}
	public Type getUnitType() {return type;}
	
	public abstract Unit ADD(Unit u2)throws UnsupportedOperationException;
	public abstract Unit SUB(Unit u2)throws UnsupportedOperationException;
	public abstract Unit MUL(double K);
	public abstract Unit DIVI(double K);
	public abstract Unit CROSS(Unit u2)throws UnsupportedOperationException;
	public abstract Unit DOT(Unit u2)throws UnsupportedOperationException;
	public abstract Unit MUL(Unit u2)throws UnsupportedOperationException;
	public abstract Unit DIVI(Unit u2)throws UnsupportedOperationException;
	public abstract Unit NEG();
	public abstract boolean isScalar();
	
	public Type getType()
	{
		return new Type(type);
	}
	public String toString()
	{
		return type.toString(maths.Maths.round(mag,6));
	}
	
	public static final void addConversion(String un,String si,String fx)
	{
		Function f=new Function(fx.trim());
		un=un.trim();
		si=si.trim();
		CONV.put(un,f);
		TYPES.put(un,si);
	}
	public static final boolean isDerived(String un)
	{
		return TYPES.containsKey(un.trim());
	}
	public static final double getConversion(double un,String unt)
	{
		return CONV.get(unt).getVal(un);
	}
	public static final String getSIUnit(String un)
	{
		return TYPES.get(un).trim();
	}
	
}
class UnitMismatchException extends UnsupportedOperationException
{
	public UnitMismatchException() {}
	public UnitMismatchException(Type t1,Type t2)
	{
		X.sopln("Error: Cannot operate on two different unit types: "+t1+" and "+t2,"red");
	}
}
class DimensionMismatchException extends UnsupportedOperationException
{
	public DimensionMismatchException()
	{
		X.sopln("Error: Cannot operate on a vector and a scalar quantity in terms of addition/subtraction","red");
	}
}
class OperationMismatchException extends UnsupportedOperationException
{
	OperationMismatchException()
	{
		X.sopln("Error: Requesting unit to perorm impossible operation. Did you ask Scalar units for Cross/Dot Products?","red");
	}
}
class VectorDivisionException extends UnsupportedOperationException
{
	public VectorDivisionException()
	{
		X.sopln("Error: Attepting to divide by vectors.","red");
	}
}
