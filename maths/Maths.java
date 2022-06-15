package maths;
import java.awt.Point;
import java.io.*;
import commons.*;
import maths.functions.Function;
import maths.functions.Constant;
import maths.functions.FunctionParser;
public class Maths
{
	//Updated as of 29th September 2017
	public static int roundK=3;
	public static final double PI=219911.0000D/70000.000D;
	public static final double practicalPI=22D/7D;
	public static final double Infinite=1D/0D;
	public static final double nInfinite=-1D/0D;
	public static final java.awt.Point originA=new java.awt.Point(0,0);
	public static final maths.Point originM=new maths.Point(0,0);
	public static final double D = 0.0000000001;
	public static final char[] operators=new char[] {'+','-','*','%','/'};
	public static boolean rad=false;

	public static double getInf()
	{
		return Infinite;
	}
	public static int addInt(String a,String b)
	{
		return X.ipi(a)+X.ipi(b);
	}
	public static float addFloat(String a,String b)
	{
		return X.fpf(a)+X.fpf(b);
	}
	public static double addDouble(String a,String b)
	{
		return X.dpd(a)+X.dpd(b);
	}
	public static long addLong(String a,String b)
	{
		return X.lpl(a)+X.lpl(b);
	}
	public static int subInt(String a,String b)
	{
		return X.ipi(a)-X.ipi(b);
	}
	public static float subFloat(String a,String b)
	{
		return X.fpf(a)-X.fpf(b);
	}
	public static double subDouble(String a,String b)
	{
		return X.dpd(a)-X.dpd(b);
	}
	public static long subLong(String a,String b)
	{
		return X.lpl(a)-X.lpl(b);
	}

	public static void setRadians(boolean r)
	{
		rad=r;
	}
	public static boolean getRad()
	{
		return rad;
	}
	public static double log(double n,double b)
	{
		return Math.log(n)/Math.log(b);
	}
	public static int Sum(int[] a)
	{
		int sum=0,l=a.length;
		for(int i=0;i<l;i++)
			sum+=a[i];
		return sum;
	}
	public static float Sum(float[] a)
	{
		float sum=0;
		int l=a.length;
		for(int i=0;i<l;i++)
			sum+=a[i];
		return sum;
	}
	public static double Sum(double[] a)
	{
		double sum=0;
		int l=a.length;
		for(int i=0;i<l;i++)
			sum+=a[i];
		return sum;
	}
	public static long Sum(long[] a)
	{
		long sum=0;
		int l=a.length;
		for(int i=0;i<l;i++)
			sum+=a[i];
		return sum;
	}
	public static double avg(int[] a)
	{
		double S=Sum(a);
		return S/a.length;
	}
	public static double avg(long[] a)
	{
		double S=Sum(a);
		return S/a.length;
	}
	public static double avg(float[] a)
	{
		double S=Sum(a);
		return S/a.length;
	}
	public static double avg(double[] a)
	{
		double S=Sum(a);
		return S/a.length;
	}
	public static double round(double no,int n)
	{
		double res = ((double) Math.round(no * Math.pow(10,n))) / Math.pow(10,n);
		return res;
	}
	public static double sin(double angle)
	{
		return (rad)?Math.sin(angle):Math.sin(Math.toRadians(angle));
	}
	public static double sin(String angle) {return sin(X.dpd(angle.trim()));}

	public static double cos(double angle)
	{
		return (rad)?Math.cos(angle):Math.cos(Math.toRadians(angle));
	}
	public static double cos(String angle) {return cos(X.dpd(angle.trim()));}

	public static double tan(double angle)
	{
		return (rad)?Math.tan(angle):Math.tan(Math.toRadians(angle));
	}
	public static double tan(String angle) {return tan(X.dpd(angle.trim()));}

	public static double cot(double angle)
	{
		return 1/((rad)?Math.tan(angle):Math.tan(Math.toRadians(angle)));
	}
	public static double cot(String angle) {return cot(X.dpd(angle.trim()));}

	public static double sec(double angle)
	{
		return 1/((rad)?Math.cos(angle):Math.cos(Math.toRadians(angle)));
	}
	public static double sec(String angle) {return sec(X.dpd(angle.trim()));}

	public static double cosec(double angle)
	{
		return 1/((rad)?Math.sin(angle):Math.sin(Math.toRadians(angle)));
	}
	public static double cosec(String angle) {return cosec(X.dpd(angle.trim()));}

	public static double asin(double val)
	{
		return ((rad)?Math.asin(val):Math.toDegrees(Math.asin(val)));
	}
	public static double asin(String val) {return asin(X.dpd(val.trim()));}

	public static double acos(double val)
	{
		return ((rad)?Math.acos(val):Math.toDegrees(Math.acos(val)));
	}
	public static double acos(String val) {return acos(X.dpd(val.trim()));}

	public static double atan(double val)
	{
		return ((rad)?Math.atan(val):Math.toDegrees(Math.atan(val)));
	}
	public static double atan(String val) {return atan(X.dpd(val.trim()));}

	public static double acot(double val)
	{
		return ((rad)?Math.atan(1/val):Math.toDegrees(Math.atan(1/val)));
	}
	public static double acot(String val) {return acot(X.dpd(val.trim()));}

	public static double asec(double val)
	{
		return ((rad)?Math.acos(1/val):Math.toDegrees(Math.acos(1/val)));
	}
	public static double asec(String val) {return asec(X.dpd(val.trim()));}

	public static double acosec(double val)
	{
		return ((rad)?Math.asin(1/val):Math.toDegrees(Math.asin(1/val)));
	}
	public static double acosec(String val) {return acosec(X.dpd(val.trim()));}

	public static int sgn(double v) {if(v==0) return 0; return (int)(v/Math.abs(v));}

	public static double[] attach(double[] a1,double[] a2)
	{
		double[] a3=new double[a1.length+a2.length];
		for(int i=0;i<a1.length;i++)
			a3[i]=a1[i];
		int K=0;
		for(int i=a1.length;i<a3.length;i++)
			a3[i]=a2[K++];
		return a3;
	}
	public static long hcf(long a,long b)
	{
		long c=Math.min(a,b);
		if(a%c==0 && b%c==0)
			return c;
		for(long i=c;i>=1;i--)
		{
			if(a%i==0 && b%i==0)
				return i;
		}
		return 1;
	}
	public static long lcm(long n1,long n2)
	{
		return n1*n2/hcf(n1,n2);
	}
	public static long lcm(long[] vals)
	{
		long lcm=1;
		for(long i : vals)
		{
			lcm=lcm(lcm,i);
		}
		return lcm;
	}
	public static long lcm(int[] vals)
	{
		long lcm=1;
		for(int i : vals)
		{
			lcm=lcm(lcm,i);
		}
		return lcm;
	}

	public static char signOf(double n)
	{
		if(n>0)
			return '+';
		else
			return (char)0;
	}
	public static String sqrt(double val)
	{
		double ans=Math.sqrt(val);
		if(X.isint(val))
			return Integer.toString((int)val);
		else
			return "sqrt <"+((int)val)+">";
	}
	public static double QuadSolve(double a,double b,double c,boolean pos)
	{
		double p1=Math.sqrt(b*b-4*a*c);
		double ans=((-1*b)+p1)/2D*a;
		if(ans<0)
			return ((-1*b)-p1)/2D*a;
		else
			return ans;
	}
	public static long gcd(long i,long j)
	{
		return hcf(i,j);
	}
	public static int randInt(int m)
	{
		return ((int)(Math.random()*m))+1;
	}
	public static long randLong(long m)
	{
		return ((long)(Math.random()*m))+1;
	}
	public static int randInt(int ll,int ul)
	{
		int i=randInt(ul);
		if(i<ll)
			return randInt(ll,ul);
		else
			return i;
	}
	public static GLine gLine(java.awt.Point p1,java.awt.Point p2)
	{
		return gLine(switchPoint(p1),switchPoint(p2));
	}
	public static GLine gLine(maths.Point p1,maths.Point p2)
	{
		return new GLine(p1,p2);
	}
	public static maths.Point switchPoint(java.awt.Point p)
	{
		return new maths.Point(p.x,p.y);
	}
	public static java.awt.Point switchPoint(maths.Point p)
	{
		return new java.awt.Point((int)p.x,(int)p.y);
	}
	public static boolean isInf(int i)
	{
		return isInf((double)i);
	}
	public static boolean isInf(long i)
	{
		return isInf((double)i);
	}
	public static boolean isInf(float i)
	{
		return isInf((double)i);
	}
	public static boolean isInf(double i)
	{
		return (i==Infinite || i==nInfinite);
	}
	public static maths.Point toPoint(double[] pt)
	{
		if(pt.length!=2)
			X.sopln("Warning: Invalid Point","red");
		return new maths.Point(pt[0],pt[1]);
	}
	public static maths.Point[] toPoint(double[][] pts)
	{
		maths.Point[] ans=new maths.Point[pts.length];
		for(int i=0;i<pts.length;i++)
			ans[i]=toPoint(pts[i]);
		return ans;
	}
	public static java.awt.Point[] switchPoints(maths.Point[] pts)
	{
		java.awt.Point[] ans=new java.awt.Point[pts.length];
		for(int i=0;i<pts.length;i++)
			ans[i]=switchPoint(pts[i]);
		return ans;
	}
	public static maths.Point[] switchPoints(java.awt.Point[] pts)
	{
		maths.Point[] ans=new maths.Point[pts.length];
		for(int i=0;i<pts.length;i++)
			ans[i]=switchPoint(pts[i]);
		return ans;
	}
	public static boolean between(double ll,double ul,double val)
	{
		if(ll<=ul)
			return (val>=ll && val<=ul);
		else
			return (val>=ul && val<=ll);
	}
	public static int min(int[] vs)
	{
		if(vs.length==0)
			return -1;
		int min=vs[0];
		for(int i : vs)
		{
			if(min>i)
				min=i;
		}
		return min;
	}
	public static int max(int[] vs)
	{
		if(vs.length==0)
			return -1;
		int max=vs[0];
		for(int i : vs)
		{
			if(max<i)
				max=i;
		}
		return max;
	}
	public static double min(double[] vs)
	{
		if(vs.length==0)
			return -1;
		double min=vs[0];
		for(double i : vs)
		{
			if(min>i)
				min=i;
		}
		return min;
	}
	public static double max(double[] vs)
	{
		if(vs.length==0)
			return -1;
		double max=vs[0];
		for(double i : vs)
		{
			if(max<i)
				max=i;
		}
		return max;
	}
	public static boolean isInt(double d)
	{
		return ((int)d)==d;
	}
	public static boolean isInt(float f)
	{
		return ((int)f)==f;
	}
	public static String perfect(String no) {return perfect(no,roundK);}
	public static String perfect(String no,int prec)
	{
		no=no.trim();
		double v;
		try
		{
			v=X.dpd(no);
		}
		catch(NumberFormatException e)
		{
			return no;
		}
		if(isInt(v))
			return Integer.toString((int)v);
		else
			return Double.toString(Maths.round(v,prec));
	}
	public static String perfect(double no) {return perfect(no,roundK);}
	public static String perfect(double no,int prec)
	{
		if(isInt(no))
			return Integer.toString((int)no);
		else
			return Double.toString(Maths.round(no,prec));
	}
	public static int[] allFactorsOf(int z)
	{
		if(z<0)
			z=-z;
		if(z==0)
			return new int[] {0};
		int[] f=new int[0];
		for(int i=1;i<=z;i++)
		{
			if(z%i==0)
				f=ArrayFx.append(f,i);
		}
		return f;
	}
	public static int factorial(int i)throws NumberFormatException
	{
		if(i<0)
			throw new NumberFormatException("Factorial of negative number");
		if(i<=0)
			return 1;
		else
			return i*factorial(i-1);
	}
	public static long factorial(long i)throws NumberFormatException
	{
		if(i<0)
			throw new NumberFormatException("Factorial of negative number");
		if(i<=0)
			return 1;
		else
			return i*factorial(i-1);
	}
	public static boolean toss()
	{
		return (Maths.randInt(2)%2==0);
	}
	public static int nPr(int n,int r)throws RuntimeException
	{
		if(n<r)
			throw new RuntimeException("Invalid input for nPr");
		return factorial(n)/factorial(n-r);
	}
	public static int nCr(int n,int r)throws RuntimeException
	{
		if(n<r)
			throw new RuntimeException("Invalid input for nPr");
		return factorial(n)/(factorial(n-r)*factorial(r));
	}
	public static boolean isNumeric(Object o)
	{
		try
		{
			Double d=(Double)o;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public static boolean isNumber(Object o)
	{
		try {X.dpd(o.toString()); return true;}
		catch(NumberFormatException e) {return false;}
	}
	public static Function getFunction(String input)
	{
			Function fx=new Function();
      			FunctionParser fp=new FunctionParser(input);
			return fp.getFunction();
	}
	public static double stringArithmetic(String in)throws NumberFormatException
	{
		in=in.replace("PI",""+Math.PI);
		String nu1="",nu2="",tnu1="",tnu2="";
		try
		{double d=X.dpd(in.trim()); return d;} catch(NumberFormatException ex) {}
		String in2=in.replace(" ","");
		int ind=in2.indexOf('/');
		double n1=1,n2=1,n3;
		boolean succ=false;
//		X.sopln(in2);

		ind=in2.indexOf('{');
		int ind2;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'{','}',ind);
			String temp=in2.substring(ind,ind2+1);
			in2=in2.replace(temp,perfect(stringArithmetic(temp.substring(1,temp.length()-1))));
			ind=in2.indexOf('{');
		}
		in2=eliminateTrigs(in2);
		try
		{double d=X.dpd(in2.trim()); return d;} catch(NumberFormatException ex) {}

		ind=in2.indexOf('/');
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='-' && in2.charAt(i)!='E')
					break;
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='-' && in2.charAt(i)!='E')
					break;
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=n1/n2;
			//X.sopln(((Maths.perfect(n1)+"/"+Maths.perfect(n2)).trim()).equals(in2.trim())+"","yellow");
			in2=in2=in2.replace((nu1+"/"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('/');
		}
		ind=in2.indexOf('^');
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='E')
					break;
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='-' && in2.charAt(i)!='E')
					break;
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=Math.pow(n1,n2);
			//X.sopln(((Maths.perfect(n1)+"/"+Maths.perfect(n2)).trim()).equals(in2.trim())+"","yellow");
			in2=in2.replace((nu1+"^"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('^');
		}
		ind=in2.indexOf('%');
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='E')
					break;
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='-' && in2.charAt(i)!='E')
					break;
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=n1%n2;
			//X.sopln(((Maths.perfect(n1)+"/"+Maths.perfect(n2)).trim()).equals(in2.trim())+"","yellow");
			in2=in2=in2.replace((nu1+"%"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('%');
		}
		ind=in2.indexOf('*');
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='E')
					break;
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.' && in2.charAt(i)!='-' && in2.charAt(i)!='E')
					break;
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=n1*n2;
			in2=in2.replace((nu1+"*"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('*');
		}


		ind=in2.indexOf('+');
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				/*if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.')
					break;*/
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				/*if(!Character.isLetterOrDigit(in2.charAt(i)) && in2.charAt(i)!='.')
					break;*/
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=n1+n2;
			in2=in2.replace((nu1+"+"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('+');
		}

		try
		{double d=X.dpd(in2.trim()); return d;} catch(NumberFormatException ex) {}
		ind=in2.indexOf('-',1);
		while(ind!=-1 && in2.charAt(ind-1)=='E')
			ind=in2.indexOf('-',ind+1);
		while(ind!=-1)
		{
			succ=false;
			for(int i=ind-1;i>=0;i--)
			{
				try {tnu1=in2.substring(i,ind).trim(); n1=X.dpd(tnu1); nu1=tnu1; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			succ=false;
			for(int i=ind+1;i<in2.length();i++)
			{
				try {tnu2=in2.substring(ind+1,i+1).trim(); n2=X.dpd(tnu2); nu2=tnu2; succ=true;}catch(NumberFormatException e) {continue;}
			}
			if(!succ)
				throw new NumberFormatException();
			n3=n1-n2;
			in2=in2.replace((nu1+"-"+nu2).trim(),Maths.perfect(n3));
			ind=in2.indexOf('-',1);
			while(ind!=-1 && in2.charAt(ind-1)=='E')
				ind=in2.indexOf('-',ind+1);
		}

		try{return X.dpd(in2.trim());}catch(NumberFormatException e) {}
		throw new NumberFormatException("Finally throwing NFE for "+in2);
	}
	public static String eliminateTrigs(String in2)
	{
		in2=in2.trim();
		int ind=in2.indexOf("sin[");
		int ind2=0;
		double v;

		//Inverse trigonometrical functions.
		ind=in2.indexOf("asin[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+4);
			v=stringArithmetic(in2.substring(ind+5,ind2));
			v=Math.asin(v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("asin[");
		}
		ind=in2.indexOf("acos[");
// 		X.sopln(ind+"","red");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+4);
			v=stringArithmetic(in2.substring(ind+5,ind2));
			v=Math.acos(v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("acos[");
		}
		ind=in2.indexOf("atan[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+4);
			v=stringArithmetic(in2.substring(ind+5,ind2));
			v=Math.atan(v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("atan[");
		}
		ind=in2.indexOf("acot[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+4);
			v=stringArithmetic(in2.substring(ind+5,ind2));
			v=Math.atan(1/v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("acot[");
		}
		ind=in2.indexOf("asec[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+4);
			v=stringArithmetic(in2.substring(ind+5,ind2));
			v=Math.acos(1/v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("asec[");
		}
		ind=in2.indexOf("acosec[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+6);
			v=stringArithmetic(in2.substring(ind+7,ind2));
			v=Math.asin(1/v);
			if(!rad)
				v=Math.toDegrees(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("acosec[");
		}

		//Trigonometic functions
		ind=in2.indexOf("sin[");
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+3);
			v=stringArithmetic(in2.substring(ind+4,ind2));
			v=sin(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("sin[");
		}
		ind=in2.indexOf("cos[");
		ind2=0;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+3);
			v=stringArithmetic(in2.substring(ind+4,ind2));
			v=cos(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("cos[");
		}
		ind=in2.indexOf("tan[");
		ind2=0;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+3);
			v=stringArithmetic(in2.substring(ind+4,ind2));
			v=tan(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("tan[");
		}
		ind=in2.indexOf("cot[");
		ind2=0;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+3);
			v=stringArithmetic(in2.substring(ind+4,ind2));
			v=cot(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("cot[");
		}
		ind=in2.indexOf("cosec[");
		ind2=0;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+5);
			v=stringArithmetic(in2.substring(ind+6,ind2));
			v=cosec(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("cosec[");
		}
		ind=in2.indexOf("sec[");
		ind2=0;
		while(ind!=-1)
		{
			ind2=Strings.getMatchingBracket(in2,'[',']',ind+3);
			v=stringArithmetic(in2.substring(ind+4,ind2));
			v=sec(v);
			in2=in2.replace(in2.substring(ind,ind2+1),Maths.perfect(v));
			ind=in2.indexOf("sec[");
		}
		return in2;
	}
	public static boolean stringCondition(String in)
	{
		in=in.trim();
		try {return X.bpb(in);} catch(NumberFormatException e) {}


		String bp1,bp2;
		int ind=-1;

		ind=in.indexOf('{');
		if(ind!=-1)
		{
			int ind2=Strings.getMatchingBracket(in,'{','}',ind);
			bp1=in.substring(ind+1,ind2);
			in=in.replace("{"+bp1+"}",stringCondition(bp1)+"");
			return stringCondition(in);
		}

		ind=in.indexOf('!');
		if(ind!=-1 && in.charAt(ind+1)!='=')
			in=in.replace(in.substring(ind),""+(!stringCondition(in.substring(ind+1).trim())));
		try {return X.bpb(in);} catch(NumberFormatException e) {}

		ind=in.indexOf("&&");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			return (stringCondition(bp1) && stringCondition(bp2));
		}

		ind=in.indexOf("||");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			return (stringCondition(bp1) || stringCondition(bp2));
		}

		double n1,n2;
		//double temp;

		ind=in.indexOf("==");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			try {bp1=stringArithmetic(bp1)+"";} catch(Exception e) {}
			try {bp2=stringArithmetic(bp2)+"";} catch(Exception e) {}
			return bp1.equals(bp2);
		}

		ind=in.indexOf("!=");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			try {bp1=stringArithmetic(bp1)+"";} catch(Exception e) {}
			try {bp2=stringArithmetic(bp2)+"";} catch(Exception e) {}
			return !bp1.equals(bp2);
		}

		ind=in.indexOf(">=");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			try
			{
				n1=stringArithmetic(bp1);
				n2=stringArithmetic(bp2);
				return n1>=n2;
			} catch(Exception e) {return bp2.compareTo(bp1)<=0;}
		}

		ind=in.indexOf("<=");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+2).trim();
			try
			{
				n1=stringArithmetic(bp1);
				n2=stringArithmetic(bp2);
				return n1<=n2;
			} catch(Exception e) {return bp2.compareTo(bp1)>=0;}
		}

		ind=in.indexOf("<");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+1).trim();
			try
			{
				n1=stringArithmetic(bp1);
				n2=stringArithmetic(bp2);
				return n1<n2;
			} catch(Exception e) {return bp2.compareTo(bp1)>0;}
		}

		ind=in.indexOf(">");
		if(ind!=-1)
		{
			bp1=in.substring(0,ind).trim();
			bp2=in.substring(ind+1).trim();
			try
			{
				n1=stringArithmetic(bp1);
				n2=stringArithmetic(bp2);
				return n1>n2;
			} catch(Exception e) {return bp2.compareTo(bp1)<0;}
		}


		throw new NumberFormatException("Finally throwing NFE in stringCondition() for string: "+in);
	}
}
