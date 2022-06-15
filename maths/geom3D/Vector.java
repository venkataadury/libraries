package maths.geom3D;
import commons.*;
import maths.*;
//import physics.*;

public class Vector implements Plottable3D
{
	Direction dir;
	double i,j,k;
	double val;
	public boolean[] frame=new boolean[] {true,true,true};
	boolean unit=false;
	String name="Vector";

	public Vector() {}
	public Vector(Vector ext)
	{
		this.assign(ext);
	}
	public Vector(double v)
	{
		val=v;
		if(val==1)
			unit=true;
		dir=Direction.ZERO;
		breakV();
		fixFrame();
	}
	public Vector(Direction d) {this(1,d);}
	public Vector(double v,Direction d)
	{
		val=v;
		dir=d;
		if(v==1)
			unit=true;
		breakV();
		fixFrame();
	}
	public Vector(double a,double b)
	{
		this(a,b,0);
	}
	public Vector(double a,double b,double c)
	{
		this(new Point3D(a,b,c));
	}
	public Vector(Vector v1,Vector v2,Vector v3)
	{
		this(v1.add(v2.add(v3)));
	}
	public Vector(Point3D pt)
	{
		this(Point3D.O,pt);
	}
	public Vector(Point3D p1,Point3D p2)
	{
		dir=new Direction(p1,p2);
		val=Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y)+(p2.z-p1.z)*(p2.z-p1.z));
		if(val==1)
			unit=true;
		breakV();
		fixFrame();
	}
	public Vector(String in)
	{
		in=in.trim().replace("  "," ");
		if(in.indexOf('(')!=-1)
		{
			Point3D pt=new Point3D(in);
			i=pt.x;j=pt.y;k=pt.z;
		}
		else
		{
			int sC=Strings.countChar(in,' ');
			String temp="";
			this.i=0; this.j=0; this.k=0;
			for(int i=0;i<=sC;i++)
			{
				temp+=Y.cut(in,' ',i+1).trim();
				if(temp.length()==1 && Character.isJavaIdentifierPart(temp.charAt(0)))
					temp="1"+temp;
				if(temp.endsWith("i"))
				{
					this.i+=Maths.stringArithmetic(temp.substring(0,temp.length()-1));
					temp="";
				}
				else if(temp.endsWith("j"))
				{
					this.j+=Maths.stringArithmetic(temp.substring(0,temp.length()-1));
					temp="";
				}
				else if(temp.endsWith("k"))
				{
					this.k+=Maths.stringArithmetic(temp.substring(0,temp.length()-1));
					temp="";
				}
			}
		}
		makeV();
		fixFrame();
		if(val==1)
			unit=true;
	}
	public Vector(Point p)
	{
		this(new Point3D(p.x,p.y,0));
	}


	public void assign(Vector ext)
	{
		this.val=ext.val;
		this.dir=ext.dir;
		breakV();
		checkVal();
		fixFrame();
	}
	public Vector neg()
	{
		breakV();
		return new Vector(-i,-j,-k);
	}
	public Vector mul(double d)
	{
		return new Vector(i*d,j*d,k*d);
	}
	public Vector divi(double d)
	{
		return new Vector(i/d,j/d,k/d);
	}

	public Vector getUnitVector()
	{
		return new Vector(i/val,j/val,k/val);
	}

	public Vector add(Vector v)
	{
		v.breakV();
		this.breakV();
		return new Vector(i+v.i,j+v.j,k+v.k);
	}
	public Vector sub(Vector v)
	{
		return this.add(v.neg());
	}

	private void checkVal()
	{
		if(Maths.round(val,3)==1) unit=true;
	}
	public void setMag(double m)
	{
		val=m;
		this.val=m;
		breakV();
	}
	public void setDir(Direction d)
	{
		dir=d;
		breakV();
	}
	private void breakV()
	{
		/*
			Angle x -axis*m= ico
		*/
		if(Double.isNaN(val)) val=0;
		i=Math.cos(dir.getX())*val; //Conversion to radians is implicit in "Direction" class in the get[X/Y/Z]() function.
		j=Math.cos(dir.getY())*val;
		k=Math.cos(dir.getZ())*val;
		if(Double.isNaN(i)) i=0;
		if(Double.isNaN(j)) j=0;
		if(Double.isNaN(k)) k=0;
		checkVal();
		this.val=val;
	}
	public double getX() {return i;}
	public double getY() {return j;}
	public double getZ() {return k;}
	public Vector getXComponent()
	{
		return new Vector(i,Direction.I);
	}
	public Vector getYComponent()
	{
		return new Vector(j,Direction.J);
	}
	public Vector getZComponent()
	{
		return new Vector(k,Direction.K);
	}
	public Vector getComponent(Direction dir)
	{
		Direction d2=this.getDir();
		double an=d2.getAngleWith(dir);
		double m=this.getMag();
		return new Vector(m*Maths.cos(an),dir);
	}
	public Vector getPerpComponent(Direction dir)
	{
		Vector vc=this.getComponent(dir);
		return this.sub(vc);
	}
	private void makeV()
	{
		dir=new Direction(new Point3D(i,j,k));
		val=Math.sqrt(i*i+j*j+k*k);
		this.val=val;
		checkVal();
	}
	public void printVector(String col)
	{
		X.sopln(toString(),col);
	}
	public void negate(Direction di)
	{
		this.assign(this.add(this.getComponent(dir).neg().mul(2)));
	}
	public void printVector()
	{
		printVector("white");
	}
	public double getMag()
	{
		return val;
	}
	public double dot(Vector v)
	{
		//return (v.val*this.val)*(Math.cos(Math.toRadians(this.getAngleWith(v))));
		return i*v.i+j*v.j+k*v.k;
	}
	public Vector cross(Vector v)
	{
		double aX,aY,aZ;
		aX=this.j*v.k-this.k*v.j;
		aY=this.k*v.i-this.i*v.k;
		aZ=this.i*v.j-this.j*v.i;
		return new Vector(aX,aY,aZ);
	}
	public double pureGetAngleWith(Vector v)
	{
		double aV=i*v.i+j*v.j+k*v.k;
		return Math.toDegrees(Math.acos(aV/(val*v.val)));
	}
	public double getAngleWith(Vector v)
	{
		return pureGetAngleWith(v);
	}
	public Point3D getEndPoint(Point3D st)
	{
		return new Point3D(st.x+i,st.y+j,st.z+k);
	}
	public Vector getVector(double mg,double Res)
	{
		/*
			(val+mg*cosO)^2 +(mgsinO)^2 = Res^2
			mg^2+val^2 + 2*val*mgcosO=Res^2F
		*/
		double mep=Res*Res-mg*mg-val*val;
		mep/=2;
		mep/=val;
		mep/=mg;
		return new Vector(mg,new Direction(Math.toDegrees(Math.acos(mep))));
	}
	public void printAngles()
	{
		X.sopln(dir.ax+" in x");
		X.sopln(dir.ay+" in y");
		X.sopln(dir.az+" in z");
	}
	public void fixFrame()
	{
		breakV();
		frame=new boolean[] {i!=0,j!=0,k!=0};
	}
	public Direction getDir()
	{
		return dir;
	}
	// Plottable3D
	public Point3D[] getPoints()
	{
		return new Point3D[] {Point3D.O,new Point3D(i,j,k)};
	}
	public boolean isClosed()
	{
		return false;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String na)
	{
		name=na;
	}
	public int getDims()
	{
		return 1;
	}
	public Point3D getTextPoint()
	{
		return (Point3D.O).midPoint(new Point3D(i,j,k));
	}
	public boolean[] getFrame()
	{
		return frame;
	}

	public String toString()
	{
		String str="";
		if(i==0 && j==0 && k==0)
			return "0";
		boolean sym=false;
		if(i!=0)
		{
			str+=(Maths.perfect(i)+"i ");
			sym=true;
		}
		if(j!=0)
		{
			if(sym)
				str+=((j>0)?"+":"");
			str+=(Maths.perfect(j)+"j ");
			sym=true;
		}
		if(k!=0)
		{
			if(sym)
				str+=((k>0)?"+":"");
			str+=(Maths.perfect(k)+"k ");
		}
		return str;
	}
}
