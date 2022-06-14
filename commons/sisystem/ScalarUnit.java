package commons.sisystem;
import commons.X;
import jtools.gaming.GameVector;

public class ScalarUnit extends Unit
{
	
	public ScalarUnit(double ma,String un)
	{
		super(ma+" "+un.trim());
	}
	public ScalarUnit(String in)
	{
		super(in.trim());
	}
	public ScalarUnit(double m,Type tp)
	{
		super(m+" "+tp);
	}
	public ScalarUnit(ScalarUnit ext)
	{
		super(ext.getMag()+" "+ext.type);
	}
	public ScalarUnit(Unit ext)
	{
		super(ext.getMag()+" "+ext.type);
	}
	
	public boolean isScalar() {return true;}
	
	public ScalarUnit ADD(Unit u2)
	{
		if(!u2.isScalar())
			throw new DimensionMismatchException();
		if(!u2.getUnitType().equals(type))
			throw new UnitMismatchException(type,u2.getUnitType());
		ScalarUnit su=new ScalarUnit(this);
		su.mag+=u2.getMag();
		return su;
	}
	public ScalarUnit SUB(Unit u2)
	{
		return ADD(u2.NEG());
	}
	public ScalarUnit NEG()
	{
		ScalarUnit su=new ScalarUnit(this);
		su.mag*=-1;
		return su;
	}
	public ScalarUnit MUL(double K)
	{
		ScalarUnit su=new ScalarUnit(this);
		su.mag*=K;
		return su;
	}
	public ScalarUnit DIVI(double K)
	{
		return MUL(1/K);
	}
	public Unit CROSS(Unit u2)throws UnsupportedOperationException
	{
		throw new OperationMismatchException();
	}
	public Unit DOT(Unit u2)throws UnsupportedOperationException
	{
		throw new OperationMismatchException();
	}
	public Unit MUL(Unit u2)throws UnsupportedOperationException
	{
		Type tnew=new Type(type,u2.getUnitType());
		if(u2.isScalar())
			return new ScalarUnit(mag*u2.getMag(),tnew);
		else
			return ((VectorUnit)u2).MUL(this);
	}
	public ScalarUnit DIVI(Unit u2)throws UnsupportedOperationException
	{
		Type tnew=new Type(type,u2.getUnitType().invert());
		u2.getUnitType().invert();
		if(u2.isScalar())
			return new ScalarUnit(mag/u2.getMag(),tnew);
		else
			throw new VectorDivisionException();
	}
}