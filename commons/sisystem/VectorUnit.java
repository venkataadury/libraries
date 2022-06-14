package commons.sisystem;
import commons.X;
import commons.Y;
import commons.Strings;
import jtools.gaming.GameVector;
import jtools.gaming.GameDirection;

public class VectorUnit extends Unit
{
	protected GameVector uv=null;
	
	public VectorUnit(double mag,String un,GameDirection d)
	{
		super(mag+" "+un.trim());
		uv=new GameVector(super.getMag(),d);
	}
	public VectorUnit(GameVector gv,String un)
	{
		super(gv.getMag()+" "+un.trim());
		uv=new GameVector(gv);
	}
	public VectorUnit(GameVector gv,Type tp)
	{
		super(gv.getMag()+" "+tp);
		uv=new GameVector(gv);
	}
	public VectorUnit(VectorUnit ext)
	{
		super(ext.getMag()+" "+ext.type);
		uv=new GameVector(ext.uv);
	}
	public VectorUnit(String in)
	{
		super(1, new Type(in.trim().substring(in.trim().lastIndexOf(' ')).trim()));
		String in2=in;
		in=in.replace("  "," ");
		double xC=0,yC=0,zC=0;
		in=in.substring(0,in.lastIndexOf(' ')).trim();
		int C=Strings.countChar(in,' ');
		String T;
		for(int i=1;i<=C+1;i++)
		{
			T=Y.cut(in,' ',i);
			if(T.endsWith("i"))
				xC+=X.dpd(T.substring(0,T.length()-1));
			else if(T.endsWith("j"))
				yC+=X.dpd(T.substring(0,T.length()-1));
			else if(T.endsWith("k"))
				zC+=X.dpd(T.substring(0,T.length()-1));
			else
				throw new RuntimeException("Unknown Vector: "+T);
		}
		uv=new GameVector(xC,yC,zC);
		double mg=Type.getUnitValue(uv.getMag()+" "+in2.trim().substring(in2.trim().lastIndexOf(' ')).trim());
		mag=mg;
		uv=uv.getUnitVector().getMultiplied(mag);
	}
	public VectorUnit(Unit ext)
	{
		super(ext.getMag()+" "+ext.type);
		if(ext.isScalar())
		{
			X.sopln("Warning: Forcing definition of scalar as Vector. Default direction: x-axis");
			uv=new GameVector(mag,0,0);
		}
		else
			uv=new GameVector(((VectorUnit)ext).uv);
	}
	
	public boolean isScalar() {return false;}
	public VectorUnit getUnitVector()
	{
		return new VectorUnit(uv.getUnitVector(),type);
	}
	
	@Override
	public double getMag()
	{
		return uv.getMag();
	}
	public void setMag(double MG)
	{
		mag=MG;
		if(uv!=null)
			uv=uv.getUnitVector().getMultiplied(mag);
	}
	
	public GameVector getVector()
	{
		return new GameVector(uv);
	}
	
	
	//Operations
	public VectorUnit NEG()
	{
		VectorUnit ret=new VectorUnit(this);
		ret.uv=ret.uv.neg();
		return ret;
	}
	public VectorUnit ADD(VectorUnit u2)throws UnsupportedOperationException
	{
		if(!type.equals(u2.getUnitType()))
			throw new UnitMismatchException(type,u2.getUnitType());
		VectorUnit vu=new VectorUnit(this);
		vu.uv=vu.uv.add(u2.uv);
		vu.mag=vu.uv.getMag();
		return vu;
	}
	public VectorUnit ADD(Unit u2)throws UnsupportedOperationException
	{
		if(u2.isScalar())
			throw new DimensionMismatchException();
		else
			return ADD((VectorUnit)u2);
	}
	public VectorUnit SUB(Unit u2)throws UnsupportedOperationException
	{
		return ADD(u2.NEG());
	}
	public VectorUnit MUL(double k)
	{
		VectorUnit vu=new VectorUnit(this);
		vu.uv=vu.uv.getMultiplied(k);
		vu.update();
		return vu;
	}
	public VectorUnit DIVI(double K)
	{
		return MUL(1/K);
	}
	public void update()
	{
		mag=uv.getMag();
	}
	public VectorUnit CROSS(VectorUnit u2)
	{
		Type tnew=new Type(type,u2.getUnitType());
		GameVector gR=u2.uv.cross(uv);
		return new VectorUnit(gR,tnew);
	}
	public ScalarUnit DOT(VectorUnit u2)
	{
		Type tnew=new Type(type,u2.getUnitType());
		double M=uv.dot(u2.uv);
		return new ScalarUnit(M,tnew);
	}
	public VectorUnit CROSS(Unit u2)
	{
		if(u2.isScalar())
			throw new OperationMismatchException();
		else
			return CROSS((VectorUnit)u2);
	}
	public ScalarUnit DOT(Unit u2)
	{
		if(u2.isScalar())
			throw new OperationMismatchException();
		else
			return DOT((VectorUnit)u2);
	}
	public VectorUnit MUL(Unit u2)
	{
		if(u2.isScalar())
		{
			Type tnew=new Type(type,u2.getUnitType());
			GameVector gR=uv.getMultiplied(u2.getMag());
			return new VectorUnit(gR,tnew);
		}
		else
			return CROSS((VectorUnit)u2);
		
	}
	public Unit DIVI(Unit u2)throws UnsupportedOperationException
	{
		if(u2.isScalar())
		{
			Type tnew=new Type(type,u2.getUnitType().invert());
			u2.getUnitType().invert();
			GameVector gR=uv.getMultiplied(1/u2.getMag());
			return new VectorUnit(gR,tnew);
		}
		else
			throw new VectorDivisionException();
	}
	public String toString()
	{
		String r=uv.toString();
		r+=" "+type;
		return r;
	}
	
}