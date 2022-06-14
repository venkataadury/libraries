package commons;
import java.io.*;
import java.util.*;
public class ArrayFx extends X//Merge Insert Flip
{ // create (I/O) searches sorting printing swapping assigning Split Join Convert
	static int[] fail=new int[] {-1};
	static float[] failF=new float[] {(float)-1.00};
	static long[] failL=new long[] {-1};
	static double[] failD=new double[] {-1.00};
	static char[] failC=new char[] {(char)0};
	static String[] failS=new String[] {""};
	public static int[] arrayInt(int a, String st)throws IOException
	{
		int m[]=new int[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.ipi(X.rL());
		}
		return m;
	}
	public static char[] arrayChar(int a, String st)throws IOException
	{
		char m[]=new char[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.rL().charAt(0);
		}
		return m;
	}
	public static String[] arrayStr(int a, String st)throws IOException
	{
		String m[]=new String[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.rL();
		}
		return m;
	}
	public static float[] arrayFloat(int a, String st)throws IOException
	{
		float m[]=new float[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.fpf(X.rL());
		}
		return m;
	}
	public static long[] arrayLong(int a, String st)throws IOException
	{
		long m[]=new long[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.lpl(X.rL());
		}
		return m;
	}
	public static double[] arrayDouble(int a, String st)throws IOException
	{
		double m[]=new double[a];
		for(int b=0;b<a;b++)
		{
			X.sop(st);
			m[b]=X.dpd(X.rL());
		}
		return m;
	}
	public static int[] Lsearch(int[] m,int v)
	{
		int l=m.length;
		boolean flag=false;
		int[] pos=new int[l+1];
		int j=0;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int[] Lsearch(float[] m,float v)
	{
		int l=m.length;
		int[] pos=new int[l+1];
		int j=0;
		boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int[] Lsearch(double[] m,double v)
	{
		int l=m.length;
		int[] pos=new int[l+1];
		int j=0;
		boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int[] Lsearch(long[] m,long v)
	{
		int l=m.length;
		int[] pos=new int[l+1];
		int j=0;
		boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int[] Lsearch(char[] m,char v)
	{
		int l=m.length;
		int[] pos=new int[l+1];
		int j=0;
		boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int[] Lsearch(String[] m,String v)
	{
		int l=m.length;
		int[] pos=new int[l+1];
		int j=0;
		boolean flag=false;
		for(int i=0;i<l;i++)
		{
			if(m[i].trim().equals(v.trim()))
			{
				pos[j]=i;
				j++;
				flag=true;
			}
		}
		if(flag)
			return pos;
		else
			return fail;
	}
	public static int Bsearch(int[] m,int v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid]==v)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if(m[mid]>v)
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static int Bsearch(float[] m,float v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid]==v)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if(m[mid]>v)
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static int Bsearch(double[] m,double v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid]==v)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if(m[mid]>v)
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static int Bsearch(long[] m,long v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid]==v)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if(m[mid]>v)
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static int Bsearch(char[] m,char v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid]==v)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if((int)m[mid]>(int)v)
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static int Bsearch(String[] m,String v)
	{
		int l=m.length;
		int hl,ll,mid;
		hl=l;
		ll=0;
		int ans=-1;
		boolean flag=false;
		while(ll<=hl)
		{
			mid=(hl+ll)/2;
			if(m[mid].compareTo(v)==0)
			{
				ans=mid;
				flag=true;
				break;
			}
			else if(m[mid].compareTo(v)>0) //H B
				hl=mid-1;
			else
				ll=mid+1;
		}
		if(flag)
			return ans;
		else
			return -1;
	}
	public static void printArray(int[] m)
	{
		if(m.length<=0)
		{
			X.sopln("{}");
			return;
		}
		int l=m.length;
		X.sop("{"+m[0]);
		for(int i=1;i<l;i++)
			X.sop(","+m[i]);
		X.sopln("}");
	}
	public static void printArray(float[] m)
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			X.sopln(m[i]);
	}
	public static void printArray(double[] m)
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			X.sopln(m[i]);
	}
	public static void printArray(long[] m)
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			X.sopln(m[i]);
	}
	public static void printArray(String[] m)
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			X.sopln(m[i]);
	}
	public static void printArray(char[] m)
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			X.sopln(m[i]);
	}
	public static void printArray(int[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static void printArray(String[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static void printArray(char[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static void printArray(float[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static void printArray(long[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static void printArray(double[] m,int s)
	{
		int l=m.length;
		int d=1;
		for(int i=0;i<l;i++)
		{
			X.sop(m[i]+" ");
			if(d++>=s)
			{
				X.sopln();
				d=1;
			}
		}
	}
	public static int[] swap(int[] m,int p1,int p2)
	{
		int c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static float[] swap(float[] m,int p1,int p2)
	{
		float c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static long[] swap(long[] m,int p1,int p2)
	{
		long c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static double[] swap(double[] m,int p1,int p2)
	{
		double c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static char[] swap(char[] m,int p1,int p2)
	{
		char c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static String[] swap(String[] m,int p1,int p2)
	{
		String c=m[p1];
		m[p1]=m[p2];
		m[p2]=c;
		return m;
	}
	public static int[] Ssort(int[] m) // 5 9 7 2 10
	{
		int l=m.length;
		int min=0;
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if(m[j]<min)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static float[] Ssort(float[] m) // 5 9 7 2 10
	{
		int l=m.length;
		float min=(float)0.0;
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if(m[j]<min)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static double[] Ssort(double[] m) // 5 9 7 2 10
	{
		int l=m.length;
		double min=0.00;
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if(m[j]<min)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static long[] Ssort(long[] m) // 5 9 7 2 10
	{
		int l=m.length;
		long min=0;
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if(m[j]<min)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static char[] Ssort(char[] m) // 5 9 7 2 10
	{
		int l=m.length;
		char min=(char)0;
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if((int)m[j]<(int)min)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static String[] Ssort(String[] m) // 5 9 7 2 10
	{
		int l=m.length;
		String min="";
		int swapp=-1;
		for(int i=0;i<l;i++)
		{
			min=m[i];
			swapp=i;
			for(int j=i;j<l;j++)
			{
				if(m[j].compareTo(min)<0)
				{
					swapp=j;
					min=m[j];
				}
			}
			//X.sopln(swapp+"<->"+i);
			m=swap(m,swapp,i);
		}
		return m;
	}
	public static int[] Bsort(int[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=(m[i]>m[i+1])?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static double[] Bsort(double[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=(m[i]>m[i+1])?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static float[] Bsort(float[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=(m[i]>m[i+1])?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static long[] Bsort(long[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=(m[i]>m[i+1])?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static char[] Bsort(char[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=((int)m[i]>(int)m[i+1])?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static String[] Bsort(String[] m)
	{
		int j=0;
		int l=m.length;
		int i=0;
		while(j<l)
		{
			for(i=0;i<l-1-j;i++)
				m=(m[i].compareTo(m[i+1])>0)?swap(m,i,i+1):m;
			j++;
		}
		return m;
	}
	public static int[] splitInt(String str,char del)
	{
		int a;
		int l=str.length();
		int[] res=new int[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=X.ipi(s);
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return fail;
				}
			}
			else
				s+=c;
		}
		res[j++]=X.ipi(s);
		return res;
	}
	public static float[] splitFloat(String str,char del)
	{
		float a;
		int l=str.length();
		float[] res=new float[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=X.fpf(s);
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return failF;
				}
			}
			else
				s+=c;
		}
		res[j++]=X.fpf(s);
		return res;
	}
	public static long[] splitLong(String str,char del)
	{
		long a;
		int l=str.length();
		long[] res=new long[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=X.lpl(s);
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return failL;
				}
			}
			else
				s+=c;
		}
		res[j++]=X.lpl(s);
		return res;
	}
	public static double[] splitDouble(String str,char del)
	{
		double a;
		int l=str.length();
		double[] res=new double[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=X.dpd(s);
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return failD;
				}
			}
		
			else
				s+=c;
		}
		res[j++]=X.dpd(s);
		return res;
	}
	public static char[] splitChar(String str,char del)
	{
		char a;
		int l=str.length();
		char[] res=new char[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=s.charAt(0);
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return failC;
				}
			}
			else
				s+=c;
		}
		res[j++]=s.charAt(0);
		return res;
	}
	public static String[] splitString(String str,char del)
	{
		String a;
		int l=str.length();
		String[] res=new String[X.countchar(str,del)+1];
		char c;
		String s="";
		int j=0;
		for(int i=0;i<l;i++)
		{
			c=str.charAt(i);
			if(c==del)
			{
				try
				{
					a=s;
					res[j++]=a;
					s="";
				}
				catch(Exception e)
				{
					X.sepln("Parsing error.");
					e.printStackTrace();
					return failS;
				}
			}
			else
				s+=c;
		}
		res[j++]=s;
		return res;
	}
	public static int[] merge(int[] a,int[] b)
	{
		int l1=a.length;
		int l2=b.length;
		int[] c=new int[l1+l2];
		for(int i=0;i<l1;i++)
			c[i]=a[i];
		for(int i=0;i<l2;i++)
			c[i+l1]=b[i];
		return c;
	}
	public static double[] merge(double[] a,double[] b)
	{
		int l1=a.length;
		int l2=b.length;
		double[] c=new double[l1+l2];
		for(int i=0;i<l1;i++)
			c[i]=a[i];
		for(int i=0;i<l2;i++)
			c[i+l1]=b[i];
		return c;
	}
	public static String join(int[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static String join(long[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static String join(float[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static String join(double[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static String join(char[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static String join(String[] m,char c)
	{
		int l=m.length;
		String an="";
		for(int i=0;i<l;i++)
			an=an+m[i]+c;
		return an.substring(0,an.length()-1);
	}
	public static int[] flip(int[] m)
	{
		int[] n=new int[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static long[] flip(long[] m)
	{
		long[] n=new long[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static float[] flip(float[] m)
	{
		float[] n=new float[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static double[] flip(double[] m)
	{
		double[] n=new double[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static char[] flip(char[] m)
	{
		char[] n=new char[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static String[] flip(String[] m)
	{
		String[] n=new String[m.length];
		int l=m.length;
		int j=0;
		for(int i=l-1;i>=0;i++)
		{
			n[j]=m[i];
			j++;
		}
		return n;
	}
	public static int count(int[] m,int v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
				c++;
		}
		return c;
	}
	public static int count(float[] m,float v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
				c++;
		}
		return c;
	}
	public static int count(long[] m,long v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
				c++;
		}
		return c;
	}
	public static int count(double[] m,double v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
				c++;
		}
		return c;
	}
	public static int count(char[] m,char v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i]==v)
				c++;
		}
		return c;
	}
	public static int count(String[] m,String v)
	{
		int c=0,l=m.length;
		for(int i=0;i<l;i++)
		{
			if(m[i].equals(v))
				c++;
		}
		return c;
	}
	public static int[] append(int[] g, int v)
	{
		int[] h=new int[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	public static long[] append(long[] g, long v)
	{
		long[] h=new long[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	public static float[] append(float[] g, float v)
	{
		float[] h=new float[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	public static double[] append(double[] g, double v)
	{
		double[] h=new double[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	public static char[] append(char[] g, char v)
	{
		char[] h=new char[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	public static String[] append(String[] g, String v)
	{
		String[] h=new String[g.length+1];
		for(int i=0;i<g.length;i++)
			h[i]=g[i];
		h[g.length]=v;
		return h;
	}
	//END
}
class IntConv extends ArrayFx
{
	int[] array;
	int l;
	IntConv(int[] m)
	{
		array=m;
		l=m.length;
	}
	int[] Valueof()
	{
		return array;
	}
	float[] toFloat()
	{
		float[] fa=new float[l];
		for(int i=0;i<l;i++)
			fa[i]=(float)array[i];
		return fa;	
	}
	long[] toLong()
	{
		long[] la=new long[l];
		for(int i=0;i<l;i++)
			la[i]=(long)array[i];
		return la;
	}
	double[] toDouble()
	{
		double[] da=new double[l];
		for(int i=0;i<l;i++)
			da[i]=(double)array[i];
		return da;
	}
	char[] toChar()
	{
		char[] da=new char[l];
		for(int i=0;i<l;i++)
			da[i]=(char)array[i];
		return da;
	}
	String[] toStr()
	{
		String[] da=new String[l];
		for(int i=0;i<l;i++)
			da[i]=""+array[i];
		return da;
	}
}
class FloatConv extends ArrayFx
{
	float[] array;
	int l;
	FloatConv(float[] m)
	{
		array=m;
		l=m.length;
	}
	float[] Valueof()
	{
		return array;
	}
	int[] toInt()
	{
		int[] fa=new int[l];
		for(int i=0;i<l;i++)
			fa[i]=(int)array[i];
		return fa;	
	}
	long[] toLong()
	{
		long[] la=new long[l];
		for(int i=0;i<l;i++)
			la[i]=(long)array[i];
		return la;
	}
	double[] toDouble()
	{
		double[] da=new double[l];
		for(int i=0;i<l;i++)
			da[i]=(double)array[i];
		return da;
	}
	char[] toChar()
	{
		char[] da=new char[l];
		for(int i=0;i<l;i++)
			da[i]=(char)array[i];
		return da;
	}
	String[] toStr()
	{
		String[] da=new String[l];
		for(int i=0;i<l;i++)
			da[i]=""+array[i];
		return da;
	}
}
class LongConv extends ArrayFx
{
	long[] array;
	int l;
	LongConv(long[] m)
	{
		array=m;
		l=m.length;
	}
	long[] Valueof()
	{
		return array;
	}
	int[] toInt()
	{
		int[] fa=new int[l];
		for(int i=0;i<l;i++)
			fa[i]=(int)array[i];
		return fa;	
	}
	float[] toFloat()
	{
		float[] la=new float[l];
		for(int i=0;i<l;i++)
			la[i]=(float)array[i];
		return la;
	}
	double[] toDouble()
	{
		double[] da=new double[l];
		for(int i=0;i<l;i++)
			da[i]=(double)array[i];
		return da;
	}
	char[] toChar()
	{
		char[] da=new char[l];
		for(int i=0;i<l;i++)
			da[i]=(char)array[i];
		return da;
	}
	String[] toStr()
	{
		String[] da=new String[l];
		for(int i=0;i<l;i++)
			da[i]=""+array[i];
		return da;
	}
}
class DoubleConv extends ArrayFx
{
	double[] array;
	int l;
	DoubleConv(double[] m)
	{
		array=m;
		l=m.length;
	}
	double[] Valueof()
	{
		return array;
	}
	int[] toInt()
	{
		int[] fa=new int[l];
		for(int i=0;i<l;i++)
			fa[i]=(int)array[i];
		return fa;	
	}
	long[] toLong()
	{
		long[] la=new long[l];
		for(int i=0;i<l;i++)
			la[i]=(long)array[i];
		return la;
	}
	float[] toFloat()
	{
		float[] da=new float[l];
		for(int i=0;i<l;i++)
			da[i]=(float)array[i];
		return da;
	}
	char[] toChar()
	{
		char[] da=new char[l];
		for(int i=0;i<l;i++)
			da[i]=(char)array[i];
		return da;
	}
	String[] toStr()
	{
		String[] da=new String[l];
		for(int i=0;i<l;i++)
			da[i]=""+array[i];
		return da;
	}
}
class CharConv extends ArrayFx
{
	char[] array;
	int l;
	CharConv(char[] m)
	{
		array=m;
		l=m.length;
	}
	char[] Valueof()
	{
		return array;
	}
	int[] toInt()
	{
		int[] fa=new int[l];
		for(int i=0;i<l;i++)
			fa[i]=(int)array[i];
		return fa;	
	}
	long[] toLong()
	{
		long[] la=new long[l];
		for(int i=0;i<l;i++)
			la[i]=(long)array[i];
		return la;
	}
	float[] toFloat()
	{
		float[] da=new float[l];
		for(int i=0;i<l;i++)
			da[i]=(float)array[i];
		return da;
	}
	double[] toDouble()
	{
		double[] da=new double[l];
		for(int i=0;i<l;i++)
			da[i]=(char)array[i];
		return da;
	}
	String[] toStr()
	{
		String[] da=new String[l];
		for(int i=0;i<l;i++)
			da[i]=""+array[i];
		return da;
	}
}
class StringConv extends ArrayFx
{
	String[] array;
	int l;
	StringConv(String[] m)
	{
		array=m;
		l=m.length;
	}
	String[] Valueof()
	{
		return array;
	}
	int[] toInt()
	{
		int[] fa=new int[l];
		for(int i=0;i<l;i++)
			fa[i]=X.ipi(array[i]);
		return fa;	
	}
	long[] toLong()
	{
		long[] la=new long[l];
		for(int i=0;i<l;i++)
			la[i]=X.lpl(array[i]);
		return la;
	}
	float[] toFloat()
	{
		float[] da=new float[l];
		for(int i=0;i<l;i++)
			da[i]=X.fpf(array[i]);
		return da;
	}
	char[] toChar()
	{
		char[] da=new char[l];
		for(int i=0;i<l;i++)
			da[i]=array[i].charAt(0);
		return da;
	}
	double[] toDouble()
	{
		double[] da=new double[l];
		for(int i=0;i<l;i++)
			da[i]=X.dpd(array[i]);
		return da;
	}
}
