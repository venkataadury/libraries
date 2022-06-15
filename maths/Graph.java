package maths;
import java.io.*;
import commons.*;
import java.util.Arrays;
import java.util.ArrayList;
public class Graph // Point types: double[], int[], Point
{
	public char[][] graph;
	public static final double[] origin=new double[] {0,0};;
	private int xl=0,yl=0;
	public int xsc=1,ysc=1;
	public Graph()
	{
		graph=new char[0][0];
		xl=yl=0;
	}
	public Graph(int a,int b,int scalex,int scaley)
	{
		graph=new char[a+1][b+1];
		xsc=scalex; ysc=scaley;
		xl=a*xsc; yl=b*ysc;
	}
	public Graph(int a,int b)
	{
		graph=new char[a+1][b+1]; 
		xl=a; yl=b; 
	}
	public static double dist(double[] p1,double[] p2)
	{
		if(p1.length !=2 || p2.length!=2)
		{
			X.sepln("One or more points inaccurate");
			return 0.00;
		}
		double x1=p1[0],y1=p1[1];
		double x2=p2[0],y2=p2[1];
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		return ans;
	}
	public static String distSq(double[] p1,double[] p2)
	{
		if(p1.length !=2 || p2.length!=2)
		{
			X.sepln("One or more points inaccurate");
			return "0.00";
		}
		double x1=p1[0],y1=p1[1];
		double x2=p2[0],y2=p2[1];
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		if(X.isint(ans))
			return Integer.toString((int)ans);
		else
			return "sqrt <"+((int)(z1+z2))+">";
	}
	public static double dist(float[] p1,float[] p2)
	{
		if(p1.length !=2 || p2.length!=2)
		{
			X.sepln("One or more points inaccurate");
			return 0.00;
		}
		float x1=p1[0],y1=p1[1];
		float x2=p2[0],y2=p2[1];
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		return ans;
	}
	public static String distSq(float[] p1,float[] p2)
	{
		if(! isValidPoint(p1) || ! isValidPoint(p2))
		{
			X.sepln("One or more points inaccurate");
			return "0.00";
		}
		double x1=p1[0],y1=p1[1];
		double x2=p2[0],y2=p2[1];
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		if(X.isint(ans))
			return Integer.toString((int)ans);
		else
			return "sqrt <"+((int)(z1+z2))+">";
	}
	public static double dist(Point p1,Point p2)
	{
		double x1=p1.x,y1=p1.y;
		double x2=p2.x,y2=p2.y;
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		return ans;
	}
	public static String distSq(Point p1,Point p2)
	{
		double x1=p1.x,y1=p1.y;
		double x2=p2.x,y2=p2.y;
		double z1=Math.pow(x2-x1,2);
		double z2=Math.pow(y2-y1,2);
		double ans=Math.sqrt(z1+z2);
		if(X.isint(ans))
			return Integer.toString((int)ans);
		else
			return "sqrt <"+((int)(z1+z2))+">";
	}
	public static double dist(String p1,String p2)
	{
		double[] p11=mkPoint(p1);
		double[] p12=mkPoint(p2);
		return dist(p11,p12);
	}
	public static boolean isValidPoint(double[] p1)
	{
		if(p1.length ==2)
			return true;
		else
			return false;
	}
	public static boolean isValidPoint(float[] p1)
	{
		if(p1.length ==2)
			return true;
		else
			return false;
	}
	public static boolean isValidPoint(int[] p1)
	{
		if(p1.length ==2)
			return true;
		else
			return false;
	}
	public static boolean isValidPoint(long[] p1)
	{
		if(p1.length ==2)
			return true;
		else
			return false;
	}
	public boolean ingraph(double[] p1)
	{
		if(! isValidPoint(p1))
		{
			X.sepln("Inaccurate point");
			return false;
		}
		if(p1[0] <= xl && p1[1]<=yl)
			return true;
		else
			return false;
	}
	public boolean ingraph(float[] p1)
	{
		if(! isValidPoint(p1))
		{
			X.sepln("Inaccurate point");
			return false;
		}
		if(p1[0] <= xl && p1[1]<=yl && p1[0]>=0 && p1[1]>=0)
			return true;
		else
			return false;
	}
	public boolean ingraph(Point p1)
	{
		if(p1.x <= xl && p1.y<=yl && p1.x>=0 && p1.y>=0)
			return true;
		else
			return false;
	}
	public boolean ingraph(int[] p1)
	{
		if(! isValidPoint(p1))
		{
			X.sepln("Inaccurate point");
			return false;
		}
		if(p1[0] <= xl && p1[1]<=yl && p1[0]>=0 && p1[1]>=0)
			return true;
		else
			return false;
	}
	public void draw(String color)throws IOException
	{
		int j=0,k=0;
		double val;
		X.sopln("\nY");
		X.sopln("^");
		X.sopln("|\n\n");
		for(int i=(yl)/ysc;i>=0;i--) //11
		{
			X.sop((i*ysc)+"|\t");
			for(j=0;j<((xl)/xsc);j++) //5
			{ 
				if((int)graph[j][i]==0) // 
					X.sop(".\t",color);
				else
					X.sop(graph[j][i]+"\t","red");
			}
			X.sopln();
			for(k=2;k<=Integer.toString(i).trim().length()-1;k--)
				X.sop(" ");
			X.sopln(" |");
		}
		X.sop(" \t");
		for(j=0;j<(xl/xsc);j++)
			X.sop("________");
		X.sopln();
		X.sop(" \t");
		for(j=0;j<(xl/xsc);j++)
			X.sop((j*xsc)+"\t");
		X.sopln(" -->  X");
	}
	public void draw()throws IOException
	{
		draw("white");
	}
	public static double[] mkPoint(String p1)
	{
		String poiS1=p1;
		String temp;
		double[] point=new double[] {0,0};
		point[0]=X.ipi(Y.cut(poiS1,',',1).substring(1).trim());
		temp=Y.cut(poiS1,',',2);
		point[1]=X.ipi(temp.substring(0,temp.length()-1).trim());
		return point;
	}
	public static String wrtPoint(double[] p1)
	{
		if(isValidPoint(p1))
		{
			String ans="(";
			int p11=0,p12=0;
			boolean f1=false,f2=false;
			if(X.isint(p1[0]))
			{
				p11=(int)p1[0];
				f1=true;
			}
			if(X.isint(p1[1]))
			{
				p12=(int)p1[1];
				f2=true;
			}
			String s1=(f1)?""+p11:""+p1[0];
			String s2=(f2)?""+p12:""+p1[1];
			ans=ans+s1+","+s2+")";
			return ans;
		}
		else
			return "";
	}
	public static String wrtPoint(Point p1)
	{
		String ans="(";
		int p11=0,p12=0;
		boolean f1=false,f2=false;
		if(X.isint(p1.x))
		{
			p11=(int)p1.x;
			f1=true;
		}
		if(X.isint(p1.y))
		{
			p12=(int)p1.y;
			f2=true;
		}
		String s1=(f1)?""+p11:""+p1.x;
		String s2=(f2)?""+p12:""+p1.y;
		ans=ans+s1+","+s2+")";
		return ans;
	}
	public void plot(double[] p1,char name)
	{
		if(isValidPoint(p1) && ingraph(p1))
		{
			graph[(int)(p1[0]/xsc)][(int)(p1[1]/ysc)]=name;
		}
		else
			X.sepln("Not a valid point or is out of graph-span");
	}
	public void plot(Point p1,char name)
	{
		if(ingraph(p1))
		{
			graph[(int)(p1.x/xsc)][(int)(p1.y/ysc)]=name;
		}
		else
			X.sepln("Point out of graph-span");
	}
	public void plot(int xco,int yco, char name)
	{
		int[] p1=new int[] {xco,yco};
		if(isValidPoint(p1) && ingraph(p1))
		{
			graph[(xco/xsc)][(yco/ysc)]=name;
		}
		else
			X.sepln("Not a valid point or is out of graph-span");
	}
	public void setPlot(int init,int endv,char op,int yse)
	{
		int step=1;
		int y=0;
		for(int x=init;x<=endv;x+=step)
		{
			if(op=='+')
				y=x+yse;
			else if(op=='-')
				y=x-yse;
			else if(op=='*' || op=='x' || op=='X')
				y=x*yse;
			else if(op=='/')
				y=x/yse;
			else if(op=='^')
				y=(int)Math.pow(x,yse);
			plot(x,y,'x');
		}
	}
	public void setPlot(int init,int endv,char op,double yse)
	{
		int step=1;
		int y=0;
		for(int x=init;x<=endv;x+=step)
		{
			if(op=='+')
				y=(int)(x+yse);
			else if(op=='-')
				y=(int)(x-yse);
			else if(op=='*' || op=='x' || op=='X')
				y=(int)(x*yse);
			else if(op=='/')
				y=(int)(x/yse);
			else if(op=='^')
				y=(int)Math.pow(x,yse);
			plot(x,y,'x'); 
		}
	}
	public void setPlot(int init,int step,int endv,char op,double yse)
	{
		int y=0;
		for(int x=init;x<=endv;x+=step)
		{
			if(op=='+')
				y=(int)(x+yse);
			else if(op=='-')
				y=(int)(x-yse);
			else if(op=='*' || op=='x' || op=='X')
				y=(int)(x*yse);
			else if(op=='/')
				y=(int)(x/yse);
			else if(op=='^')
				y=(int)Math.pow(x,yse);
			plot(x,y,'x');
		}
	}
	public void setPlot(int init,int step,int endv,char op,int yse)
	{
		int y=0;
		for(int x=init;x<=endv;x+=step)
		{
			y=process1(op,x,yse);
			plot(x,y,'x');
		}
	}
	public int process1(char op,double x,double yse)
	{
		int y=0;
		if(op=='+')
				y=(int)(x+yse);
			else if(op=='-')
				y=(int)(x-yse);
			else if(op=='*' || op=='x' || op=='X')
				y=(int)(x*yse);
			else if(op=='/')
				y=(int)(x/yse);
			else if(op=='^')
				y=(int)Math.pow(x,yse);
		return y;
	}
	public void mark(int[] p1,char val)
	{
		plot(p1[0],p1[1],val);
	}
	public void unmark(int[] p1)
	{
		char val=(char)0;
		plot(p1[0],p1[1],val);
	}
	public void mark(double[] p1,char val)
	{
		plot((int)p1[0],(int)p1[1],val);
	}
	public void unmark(double[] p1)
	{
		char val=(char)0;
		plot((int)p1[0],(int)p1[1],val);
	}
	public void mark(Point p1,char val)
	{
		plot((int)p1.x,(int)p1.y,val);
	}
	public void unmark(Point p1)
	{
		char val=(char)0;
		plot((int)p1.x,(int)p1.y,val);
	}
	public static double[] midPoint(int[] p1,int[] p2)
	{
		double[] pnt=new double[] {0,0};
		pnt[0]=(p1[0]+p2[0])/2;
		pnt[0]=(p1[1]+p2[1])/2;
		return pnt;
	}
	public static double[] midPoint(double[] p1,double[] p2)
	{
		double[] pnt=new double[] {0,0};
		pnt[0]=(p1[0]+p2[0])/2;
		pnt[1]=(p1[1]+p2[1])/2;
		return pnt;
	}
	public static double[] midPoint(Point p1,double[] p2)
	{
		double[] pnt=new double[] {0,0};
		pnt[0]=(p1.x+p2[0])/2;
		pnt[1]=(p1.y+p2[1])/2;
		return pnt;
	}
	public static double[] midPoint(Point p1,Point p2)
	{
		double[] pnt=new double[] {0,0};
		pnt[0]=(p1.x+p2.x)/2;
		pnt[1]=(p1.y+p2.y)/2;
		return pnt;
	}
	public static Point createPoint(double[] p1)
	{
		Point pt=new Point(p1);
		return pt;
	}
	public static double[] section(double[] p1,double[] p2,int sec)
	{
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-1;
		int m2=1;
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2[0]+m2*p1[0])/(m1+m2);
		ans[1]=(double)(m1*p2[1]+m2*p1[1])/(m1+m2);
		return ans;
	}
	public static double[] section(Point p1,double[] p2,int sec)
	{
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-1;
		int m2=1; //Assuming
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2[0]+m2*p1.x)/(m1+m2);
		ans[1]=(double)(m1*p2[1]+m2*p1.y)/(m1+m2);
		return ans;
	}
	public static double[] section(Point p1,double[] p2,int sec,int nth)throws PointIndexOutOfBoundsException
	{
		if(nth>=sec)
			throw new PointIndexOutOfBoundsException();
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-nth;
		int m2=nth;
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2[0]+m2*p1.x)/(m1+m2);
		ans[1]=(double)(m1*p2[1]+m2*p1.y)/(m1+m2);
		return ans;
	}
	public static double[] section(Point p1,Point p2,int sec)
	{
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-1;
		int m2=1;
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2.x+m2*p1.x)/(m1+m2);
		ans[1]=(double)(m1*p2.y+m2*p1.y)/(m1+m2);
		return ans;
	}
	public static double[] section(Point p1,Point p2,int sec,int nth)throws PointIndexOutOfBoundsException
	{
		if(nth>=sec)
			throw new PointIndexOutOfBoundsException();
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-nth;
		int m2=nth;
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2.x+m2*p1.x)/(m1+m2);
		ans[1]=(double)(m1*p2.y+m2*p1.y)/(m1+m2);
		return ans;
	}
	public static double[] section(double[] p1,double[] p2,int sec,int nth)throws PointIndexOutOfBoundsException
	{
		if(nth>=sec)
			throw new PointIndexOutOfBoundsException();
		if(sec==2)
			return midPoint(p1,p2);
		int m1=sec-nth;
		int m2=nth;
		double ans[]=new double[] {0,0};
		ans[0]=(double)(m1*p2[0]+m2*p1[0])/(m1+m2);
		ans[1]=(double)(m1*p2[1]+m2*p1[1])/(m1+m2);
		return ans;
	}
	public static void printPoints(Point[] plist)
	{
		int l=plist.length;
		for(int i=0;i<l;i++)
			plist[i].printPoint();
	}
	public static Point[] section(Point p1,Point p2,long sect)
	{
		int sec=(int)sect;
		Point[] pset=new Point[sec-1];
		int m1=0,m2=0;
		double ans[]=new double[] {0,0};
		for(int i=1;i<sec;i++)
		{
			m1=sec-i;
			m2=i;
			ans[0]=(double)(m1*p2.x+m2*p1.x)/(m1+m2);
			ans[1]=(double)(m1*p2.y+m2*p1.y)/(m1+m2);
			pset[i-1]=new Point(ans);
		}
		return pset;
	}
}
class PointIndexOutOfBoundsException extends RuntimeException
{
	PointIndexOutOfBoundsException() {}
	PointIndexOutOfBoundsException(String s)
	{
		X.sopln(s);
	}
}
