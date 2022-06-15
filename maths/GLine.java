package maths;
import commons.*;
import java.io.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
public class GLine extends Graph
{
	//y=mx+c
	public double x,y;
	public double c; //
	public double m; //
	String inm1="",inc1="";
	public boolean sqrdm=false,sqrdc=false;
	public int yco=1; 
	public static final int[] origin=new int[] {0,0};
	public final double inclin; //
	public boolean pty=false; //
	public double Xpty=0; //
	public static double slope(Point p1,Point p2)
	{
		double ans=(p2.y-p1.y)/(p2.x-p1.x); 
		return ans;
	}
	public GLine()
	{
		m=1;
		c=0;
		inclin=45;
	}
	public GLine(GLine in,Point pss,int agl) // +/- 180 angle
	{
		agl%=180;
		inclin=(in.inclin+agl)%180;
		m=Math.tan(inclin);
		c=pss.y-m*pss.x;
		if(Maths.isInf(m))
		{
			pty=true;
			Xpty=pss.x;
		}
	}
	public GLine(String eqn)
	{
		//Format y=mx+c x+y=c
		X.sopln(eqn,"red");
		String ys="",xs="",cst="";
		ys=Y.cut(eqn,'=',1).trim();
		xs=Y.cut(eqn,'=',2).trim();
		char d;
		int lc=X.countchar(ys,' ');
		String temp1="";
		int multip=1;
		boolean foundx=false,foundy=false,foundm=false,foundc=false;
		for(int i=0;i<=lc;i++)
		{
			temp1=Y.cut(ys,' ',i+1).trim();
			if(X.haschar(temp1,'y'))
			{
				foundy=true;
				if(temp1.length()>1)
					yco=multip*(X.ipi(Y.cut(temp1,'y',1)));
				else
					yco=multip;
			}
			else if(temp1.charAt(0)=='-')
				multip=-1;
		}
		multip=1;
		for(int i=0;i<=lc;i++)
		{
			temp1=Y.cut(ys,' ',i+1).trim();
			if(X.haschar(temp1,'x'))
			{
				foundx=true;
				foundm=true;
				if(temp1.length()>1)
					m=(-1*X.dpd(Y.cut(temp1,'x',1)));
				else
					m=-1.00;
			}
			else if(X.haschar(temp1,'y'))
				X.sop("");
			else if(temp1.charAt(0)=='+' || temp1.charAt(0)=='-')
				X.sop("");
			else
			{
				foundc=true;
				c=X.dpd(temp1.trim());
			}
		}
		lc=X.countchar(xs,' ');
		multip=1;
		for(int j=0;j<=lc;j++)
		{
			temp1=Y.cut(xs,' ',j+1).trim();
			if(X.haschar(temp1,'x'))
			{
				foundx=true;
				foundm=true;
				if(temp1.length()>1)
					m=X.dpd(Y.cut(temp1,'x',1));
				else
					m=1.00;
			}
			else if(X.haschar(temp1,'y'))
				X.sop("");
			else if(temp1.charAt(0)=='-')
				multip=-1;
			else if(temp1.charAt(0)=='+')
				multip=1;
			else
			{
				foundc=true;
				c=(multip*X.dpd(temp1.trim()));
			}
		}
		if(foundm)
			inclin=Math.toDegrees(Math.atan(m));
		else
			throw new SlopeUnavailableException();
	}
	public GLine(Point p1,Point p2)
	{
		m=slope(p1,p2);
		if(Maths.isInf(m))
		{
			pty=true;
			Xpty=p1.x;
		}
		double d=p1.x*m;
		c=(p1.y - d);
		inclin=Math.toDegrees(Math.atan(m));
		
	}
	public GLine(int yin,double slp)
	{
		m=slp;
		if(Maths.isInf(m))
			throw new InvalidLineEquationException();
		c=yin;
		inclin=Math.toDegrees(Math.atan(m));
		
	}
	public GLine(double inc,int yin)
	{
		inclin=inc;
		c=yin;
		m=Maths.tan(inc);
		inm1=""+((int)Math.pow(m,2)+1);
		sqrdm=true;
		if(Maths.isInf(m))
			throw new InvalidLineEquationException();
	}
	public GLine(int yin,Point p)
	{
		c=yin; //0,c
		m=(p.y-c)/p.x;
		if(Maths.isInf(m))
		{
			pty=true;
			Xpty=p.x;
		}
		inclin=Math.toDegrees(Math.atan(m));
	}
	public GLine(Frac slp, Point p)
	{
		m=slp.p;
		double d=p.x*m;
		c=((p.y - d)*slp.q);
		yco=(int)slp.q;
		inclin=Math.toDegrees(Math.atan(m));
		if(Maths.isInf(m))
		{
			pty=true;
			Xpty=p.x;
		}
	}
	public GLine(double inc,Point p)
	{
		m=Maths.tan(inc);
		inm1=""+((int)Math.pow(m,2)+1);
		sqrdm=true;
		double d=p.x*m;
		c=(p.y - d);
		inclin=Math.toDegrees(Math.atan(m));
		if(Maths.isInf(m))
		{
			pty=true;
			Xpty=p.x;
		}
	}
	public GLine(Point p,Angle a)
	{
		if(a.v==0)
		{
			m=Maths.tan(90);
			inclin=90-(a.v%180);
			c=7/0D;
			if(Maths.isInf(m))
			{
				pty=true;
				Xpty=p.x;
			}
			return;
		}
		m=Maths.tan(90-(a.v%180));
		c=p.y-p.x*m;
		inclin=Math.toDegrees(Math.atan(m));
	}
	public GLine(double a,double b,String t1,String t2) // yi xi slp inc
	{
		if(t1.equals("yi"))
			c=a;
		else if(t1.equals("slp"))
			m=a;
		else if(t1.equals("inc"))
			m=Maths.tan(a);
		if(t2.equals("yi"))
			c=b;
		else if(t2.equals("slp"))
			m=b;
		else if(t2.equals("inc"))
			m=Maths.tan(b);
		else if(t2.equals("xi")) //(x,0)
		{
			if(t1.equals("yi"))
				m=(0-c)/b;
			else if(t1.equals("slp"))
				c=(-1*m)*b;
			else if(t1.equals("inc"))
				c=(-1*m)*b;
		}
		if(t1.equals("xi")) //(x,0)
		{
			if(t2.equals("yi"))
				m=(0-c)/a;
			else if(t2.equals("slp"))
				c=(-1*m)*a;
			else if(t2.equals("inc"))
				c=(-1*m)*a;
		}
		if(Maths.isInf(m))
		{
			pty=true;
			inclin=90;
		}
		else
			inclin=Math.toDegrees(Math.atan(m));
	}
	
	public GLine(String a,String b,String t1,String t2) // yi xi slp inc fr pt >> Ex: (xi, 7, yi, 12)
	{
		a=a.trim();
		b=b.trim();
		if(t1.equalsIgnoreCase(t2) && ! t1.equals("pt"))
		{
			X.sepln("GLine.<init> ==> Illegal call to constructor of Line Equation with two values of the same type: "+t1);
			throw new InvalidLineEquationException();
		}
		Point ps=null,pr=null;
		Frac fr=null,fs=null;
		if(t1.equals("yi"))
			c=X.dpd(a);
		else if(t1.equals("slp"))
			m=X.dpd(a);
		else if(t1.equals("inc"))
			m=Maths.tan(X.dpd(a));
		else if(t1.equals("pt"))
			ps=new Point(a);
		else if(t1.equals("fr"))
		{
			fr=new Frac(a);
			yco=(int)fr.q;
			m=fr.p;
		}
		if(t2.equals("yi"))
			c=X.dpd(b);
		else if(t2.equals("slp"))
			m=X.dpd(b);
		else if(t2.equals("inc"))
			m=Maths.tan(X.dpd(b));
		else if(t2.equals("pt"))
		{
			pr=new Point(b);
			if(t1.equals("pt"))
			{
				m=(pr.y-ps.y)/(pr.x-ps.x);
				c=pr.y-(pr.x*m);
			}
			else if(t1.equals("slp") || t1.equals("inc"))
				c=pr.y-(pr.x*m);
			else if(t1.equals("yi"))
				m=(pr.y-c)/pr.x;
		}
		else if(t2.equals("fr"))
			fs=new Frac(b);
		else if(t2.equals("xi")) //(x,0)
		{
			if(t1.equals("yi"))
				m=(0-c)/X.dpd(b);
			else if(t1.equals("slp"))
				c=(-1*m)*X.dpd(b);
			else if(t1.equals("inc"))
				c=(-1*m)*X.dpd(b);
			else if(t1.equals("pt"))
				gSet(ps,new Point(X.dpd(b),0.00));
			else if(t1.equals("fr"))
				c=-1.00*(m*X.dpd(b));
		}
		if(t1.equals("xi")) //(x,0)
		{
			if(t2.equals("yi"))
				m=(0-c)/X.dpd(a);
			else if(t2.equals("slp"))
				c=(-1*m)*X.dpd(a);
			else if(t2.equals("inc"))
				c=(-1*m)*X.dpd(a);
			else if(t2.equals("pt"))
				gSet(new Point(X.dpd(a),0.00),pr);
			else if(t2.equals("fr"))
				c=-1.00*(m*X.dpd(a));
		}
		else if(t1.equals("pt"))
		{
			if(t2.equals("pt"))
			{
				m=(pr.y-ps.y)/(pr.x-ps.x);
				c=pr.y-(pr.x*m);
			}
			else if(t2.equals("slp") || t2.equals("inc"))
				c=ps.y-(ps.x*m);
			else if(t2.equals("yi"))
				m=(ps.y-c)/ps.x;
		}
		else if(t1.equals("fr"))
		{
			if(t2.equals("pt"))
				c=(pr.y)-(m*pr.x);
			else if(t2.equals("yi"))
				c=X.dpd(b);
		}
		if(Maths.isInf(m))
		{
			pty=true;
			inclin=90;
		}
		else
			inclin=Math.toDegrees(Math.atan(m));
	}
	public void gSet(Point p1,Point p2)
	{
		m=slope(p1,p2);
		double d=p1.x*m;
		c=(p1.y - d);
	}
	public double getValue(double xV,double yV)
	{
		return yV-(m*xV+c);
	}
	public void printEqn(String col)
	{
		if(pty || Maths.isInf(m))
		{
			X.sopln("Line x="+Xpty,col);
			return;
		}
		double d=m;
		double e=yco;
		//00 01 10 11
		int mi=0,ycoi=0,ci=0;
		boolean flag=false,f1=false,f2=false;
		if(X.isint(m))
		{
			flag=true;
			mi=(int)m;
		}
		if(X.isint(c))
		{
			f1=true;
			ci=(int)c;
		}
		if(yco>=0 && d>=0)
		{
			if(yco!=1)
				X.sop(yco);
			X.sopln("y = "+Null1(((flag)?""+mi:""+m))+"x "+Maths.signOf((double)c)+' '+((c!=0)?((f1)?""+ci:""+c):""),col);
		}
		else if(yco>=0 && d<0)
		{
			if(yco!=1)
				X.sop(yco);
			X.sopln("y + "+((flag)?""+(mi*-1):""+(m*-1))+"x = "+((c!=0)?((f1)?""+ci:""+c):""),col);
		}
		else if(yco<0 && d>=0)
		{
			if(yco!=1)
				X.sop((yco*-1));
			X.sopln("y + "+((flag)?""+mi:""+m)+"x = "+((f1)?""+(ci*-1):""+(c*-1)),col);
		}
		else if(yco<0 && d<0)
		{
			X.sop(((flag)?""+(mi*-1):""+(m*-1))+"x = ",col);
			if(yco!=1)
				X.sop((yco*-1));
			X.sopln("y + "+((f1)?""+ci:""+c),col);
		}
	}
	public void printEqn()
	{
		printEqn("white");
	}
	public String getEqn()
	{
		double d=m;
		double e=yco;
		//00 01 10 11
		int mi=0,ycoi=0,ci=0;
		boolean flag=false,f1=false,f2=false;
		if(X.isint(m))
		{
			flag=true;
			mi=(int)m;
		}
		if(X.isint(c))
		{
			f1=true;
			ci=(int)c;
		}
		if(yco>=0 && d>=0)
		{
			if(yco!=1)
				X.sop(yco);
			return("y = "+Null1(((flag)?""+mi:""+m))+"x "+Maths.signOf((double)c)+' '+((c!=0)?((f1)?""+ci:""+c):""));
		}
		else if(yco>=0 && d<0)
		{
			if(yco!=1)
				X.sop(yco);
			return("y + "+((flag)?""+(mi*-1):""+(m*-1))+"x = "+((c!=0)?((f1)?""+ci:""+c):""));
		}
		else if(yco<0 && d>=0)
		{
			if(yco!=1)
				X.sop((yco*-1));
			return("y + "+((flag)?""+mi:""+m)+"x = "+((f1)?""+(ci*-1):""+(c*-1)));
		}
		else if(yco<0 && d<0)
		{
			X.sop(((flag)?""+(mi*-1):""+(m*-1))+"x = ");
			if(yco!=1)
				X.sop((yco*-1));
			return("y + "+((f1)?""+ci:""+c));
		}
		else
			return "y = x";
	}
	public boolean onLine(Point p)
	{
		return pointOnLine(p);
	}
	public static String Null1(String a)
	{
		if(X.dpd(a)==1)
			return "";
		else
			return a;
	}
	public Line2D getAWTLine()
	{
		Point p1=pointOnLineX(0);
		Point[] pts=pointsOnLine(p1,1);
		return new Line2D.Double(pts[0].x,pts[0].y,pts[1].x,pts[1].y);
	}
	public boolean pointOnLine(Point p)
	{
		double s1=p.y*yco;
		double s2=(p.x*m)+c;
		if(s1==s2)
			return true;
		else
			return false;
	}
	public Point pointOnLine()
	{
		if(pty)
			return new Point((int)Xpty,0);
		return new Point(-c/m,0); // X-intercept
	}
	public Point pointOnLineX(int x) //X given
	{
		//y=mx+c
		if(Maths.isInf(m))
		{
			if(x==Xpty)
				return new Point(x,0);
			else
				return null;
		}
		return new Point(x,m*x+c);
	}
	public Point pointOnLineY(int y) //Y given
	{
		if(pty)
			return new Point((int)Xpty,y);
		return new Point(-c/m,y);
	}
	public String toString()
	{
		return getEqn();
	}
	public static Point intersect(GLine l1,GLine l2)
	{
		if(l2==null || l1==null)
			X.sopln("NULL","Red");
		if(l1.m==l2.m)
			throw new ParallelLinesException();
		if(l1.pty)
			X.sopln((int)l1.Xpty);
		if(l2.pty)
			return l1.pointOnLineX((int)l2.Xpty);
		Point p=null;
		double xcl,ycl;
		double c11=l1.c/(double)l1.yco; //3.5
		double c12=l2.c/(double)l2.yco; 
		double m11=l1.m/(double)l1.yco; //0.5
		double m12=l2.m/(double)l2.yco;
		xcl=(c11-c12)/(m12-m11);
		ycl=(m11*xcl)+c11;
		p=new Point(xcl,ycl);
		return p;
	}
	public Point[] pointsOnLine(Point p1,double range)
	{
		Point[] res=new Point[2];
		if(pty)
			return new Point[] {new Point(p1.x,p1.y-range),new Point(p1.x,p1.y+range)};
		if(m==0)
			return new Point[] {new Point(p1.x-range,p1.y),new Point(p1.x+range,p1.y)};
		double xr,yr;
		//sinT = Yr/range
		yr=range*Maths.sin(inclin);
		xr=range*Maths.cos(inclin);
		return new Point[] {new Point(p1.x-xr,p1.y-yr),new Point(p1.x+xr,p1.y+yr)};
	}
	public Point intersect(GLine l2)
	{
		return intersect(this,l2);
	}
	public static double distance(Point p1,Point p2)
	{
		return Graph.dist(p1,p2);
	}
	public static String distanceSq(Point p1,Point p2)
	{
		return Graph.distSq(p1,p2);
	}
	public static Point[] sect(Point p1,Point p2,int sec)
	{
		return Graph.section(p1,p2,(long)sec);
	}
	public static Angle angleWith(GLine gl1,GLine gl2)
	{
		if(gl1.pty && !gl2.pty)
			return new Angle(90-gl2.inclin);
		if(gl2.pty && !gl1.pty)
			return new Angle(90-gl1.inclin);
		if(gl1.pty && gl2.pty)
			return new Angle(180);
		if(gl1.m==gl2.m)
			return new Angle(180);
		int l1=(int)Math.toDegrees(Math.atan(gl1.m));
		int l2=(int)Math.toDegrees(Math.atan(gl2.m));
		return new Angle(l2-l1);
	}
	public Angle angleWith(GLine gl2)
	{
		return angleWith(this,gl2);
	}
}
class ParallelLinesException extends ArithmeticException
{
	ParallelLinesException()
	{
		X.sopln("Calculation Error: Parallel Lines","red");
	}
}
class SlopeUnavailableException extends InvalidLineEquationException
{
	SlopeUnavailableException()
	{
		X.sopln("Calculation Error: Line slope unavailable","red");
	}
}
class InvalidLineEquationException extends RuntimeException
{
	InvalidLineEquationException()
	{
		X.sopln("Error occured in calculations regarding an equation of a line.","red");
	}
}
