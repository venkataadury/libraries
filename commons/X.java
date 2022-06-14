package commons;
import java.io.*;
public class X
{
	static InputStreamReader isr=new InputStreamReader(System.in);
	static BufferedReader br=new BufferedReader(isr);
	public static String CLEAR="\033[H\033[2J";
	public static String[] empty=new String[] {"",""};
	public static String HOME=System.getProperty("user.home");
	public static String RED="\u001B[31m";
	public static String GREEN="\u001B[32m";
	public static String WHITE="\033[0m";
	public static String YELLOW = "\u001B[33m";
	public static String BLUE = "\u001B[34m";
	public static String PURPLE = "\u001B[35m";
	public static String CYAN="\u001B[36m";
	public static void sof()
	{
		System.out.flush();
	}
	public static void clear()
	{
		sop(CLEAR);
		sof();
	}
	public static void sTerm(String color)
	{
		if(color.equalsIgnoreCase("red"))
			sop(RED);
		else if(color.equalsIgnoreCase("green"))
			sop(GREEN);
		else if(color.equalsIgnoreCase("white"))
			sop(WHITE);
		else if(color.equalsIgnoreCase("cyan"))
			sop(CYAN);
		else if(color.equalsIgnoreCase("yellow"))
			sop(YELLOW);
		else if(color.equalsIgnoreCase("blue"))
			sop(BLUE);
		else if(color.equalsIgnoreCase("purple"))
			sop(PURPLE);
		sof();
	}
	public static void sop(String a)
	{
		System.out.print(a);
	}
	public static void sop(Object... a)
	{
		for(Object x : a)
			System.out.print(a.toString()+" ");
	}
	public static void sopln(Object... a) {sop(a);sopln();}
	public static void sopln(int[] a)
	{
		ArrayFx.printArray(a);
	}
	public static void sop(Object[] oa,String col)
	{
		if(oa.length<=0)
		{
			X.sop("{}",col);
			return;
		}
		X.sop("{"+oa[0].toString(),col);
		for(int i=1;i<oa.length;i++)
			X.sop(","+oa[i].toString());
		X.sop("}",col);
	}
	public static void sop(Object o)
	{
		sop(o,"white");
	}
	public static void sop(Object o,String col)
	{
		sop(o.toString(),col);
	}
	public static void sopln(Object[] oa,String col)
	{
		sop(oa,col);
		X.sopln();
	}
	public static void sop(String a,String color)
	{
		sTerm(color);
		System.out.print(a);
		sTerm("white");
	}
	public static void sopln(String a,String color)
	{
		sTerm(color);
		System.out.println(a);
		sTerm("white");
	}
	public static void sopln(Object o)
	{
		sopln(o,"white");
	}
	public static void sopln(Object o,String col)
	{
		sopln(o.toString(),col);
	}
	public static void sopln(String a)
	{
		System.out.println(a);
	}
	public static void sop(int a)
	{
		System.out.print(a);
	}
	public static void sopln(int a)
	{
		System.out.println(a);
	}
	public static void sop(char a)
	{
		System.out.print(a);
	}
	public static void sopln(char a)
	{
		System.out.println(a);
	}
	public static void sop(long a)
	{
		System.out.print(a);
	}
	public static void sopln(long a)
	{
		System.out.println(a);
	}
	public static void sop(double a)
	{
		System.out.print(a);
	}
	public static void sopln(double a)
	{
		System.out.println(a);
	}
	public static void sopln(InputStream a)
	{
		System.out.println(a);
	}
	public static void sop(InputStream a)
	{
		System.out.print(a);
	}
	public static void sop(float a)
	{
		System.out.print(a);
	}
	public static void sopln(float a)
	{
		System.out.println(a);
	}
	public static void sop(boolean a)
	{
		System.out.print(a);
	}
	public static void sopln(boolean a)
	{
		System.out.println(a);
	}
	public static void sopln()
	{
		System.out.println();
	}
	
	public static void sep(String a)
	{
		System.err.print(a);
	}
	public static void sepln(String a)
	{
		System.err.println(a);
	}
	public static void sep(int a)
	{
		System.err.print(a);
	}
	public static void sepln(int a)
	{
		System.err.println(a);
	}
	public static void sep(double a)
	{
		System.err.print(a);
	}
	public static void sepln(double a)
	{
		System.err.println(a);
	}
	
	
	public static int ipi(String a)
	{
		return Integer.valueOf(a);
	}
	public static long lpl(String a)
	{
		return Long.valueOf(a);
	}
	public static double dpd(String a)
	{
		return Double.valueOf(a);
	}
	public static float fpf(String a)
	{
		return Float.valueOf(a);
	}
	public static boolean bpb(String a)
	{
		try {return (X.ipi(a)==1);}catch(Exception e) {}
		a=a.trim().toLowerCase();
		if(a.equals("t") || a.equals("true"))
			return true;
		else if(a.equals("f") || a.equals("false"))
			return false;
		else
			throw new NumberFormatException();
	}
	public static boolean bpb(int i) {return toBoolean(i);}
	public static boolean toBoolean(int i) {return (i!=0);}
	public static String rL()throws IOException
	{
		return br.readLine();
	}
	public static String rL(String col)throws IOException
	{
		sTerm(col);
		String s=br.readLine();
		sTerm("White");
		return s;
	}
	public static char rC()throws IOException
	{
		return rL().charAt(0);
	}
	public static int[] arswap(int m[],int a, int b)
	{
		try
		{
			int c=m[a];
			m[a]=m[b];
			m[b]=c;
		}
		catch (Exception e)
		{
			sopln("Error: "+e);
		}
		finally
		{
			return m;
		}
	}
	public static char[] arswap(char m[],int a, int b)
	{
		try
		{
			char c=m[a];
			m[a]=m[b];
			m[b]=c;
		}
		catch (Exception e)
		{
			sopln("Error: "+e);
		}
		finally
		{
			return m;
		}
	}
	public static String[] arswap(String m[],int a, int b)
	{
		try
		{
			String c=m[a];
			m[a]=m[b];
			m[b]=c;
		}
		catch (Exception e)
		{
			sopln("Error: "+e);
		}
		finally
		{
			return m;
		}
	}
	public static float[] arswap(float m[],int a, int b)
	{
		try
		{
			float c=m[a];
			m[a]=m[b];
			m[b]=c;
		}
		catch (Exception e)
		{
			sopln("Error: "+e);
		}
		finally
		{
			return m;
		}
	}
	
	public static int[] arrayint(int a, String st)throws IOException
	{
		int m[]=new int[a];
		for(int b=0;b<a;b++)
		{
			sop(st);
			m[b]=ipi(rL());
		}
		return m;
	}
	public static char[] arraychar(int a, String st)throws IOException
	{
		char m[]=new char[a];
		for(int b=0;b<a;b++)
		{
			sop(st);
			m[b]=rL().charAt(0);
		}
		return m;
	}
	public static String[] arrayStr(int a, String st)throws IOException
	{
		String m[]=new String[a];
		for(int b=0;b<a;b++)
		{
			sop(st);
			m[b]=rL();
		}
		return m;
	}
	public static float[] arrayfloat(int a, String st)throws IOException
	{
		float m[]=new float[a];
		for(int b=0;b<a;b++)
		{
			sop(st);
			m[b]=fpf(rL());
		}
		return m;
	}
	
	public static int arrlen(int m[])
	{
		int a=0;
		int b;
		for(;;a++)
		{
			try
			{
				b=m[a];
			}
			catch(Exception e)
			{
				break;
			}
		}
		return a;
	}
	public static void arrprint(int m[])
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			sopln(m[i]);
	}
	public static void arrprint(String m[],int side)
	{
		int l=m.length;
		int q=1;
		for(int i=0;i<l;i++)
		{
			sop(m[i]);
			if(q>=side)
			{
				sopln();
				q=1;
			}
			q++;
		}
	}
	public static void arrprint(float m[],int side)
	{
		int l=m.length;
		int q=1;
		for(int i=0;i<l;i++)
		{
			sop(m[i]);
			if(q>=side)
			{
				sopln();
				q=1;
			}
			q++;
		}
	}
	public static void arrprint(double m[])
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			sopln(m[i]);
	}
	public static void arrprint(long m[])
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			sopln(m[i]);
	}
	public static void arrprint(char m[])
	{
		int l=m.length;
		for(int i=0;i<l;i++)
			sopln(m[i]);
	}
	public static String[] arrayToString(int m[])
	{
		int l=m.length;
		String narr[]=new String[500];
		for(int i=0;i<l;i++)
			narr[i]=""+m[i];
		return narr;
	}
	public static String[] arrmerge(String m1[],String m2[],String delim)
	{
		int l1=m1.length;
		int l2=m2.length;
		int l=Math.min(l1,l2);
		int j=0,i=0;
		String nstar[]=new String[(l*2)+1];
		for(i=0;i<l;i++)
		{
			nstar[j]=m1[i]+delim;
			nstar[j+1]=m2[i];
			j+=2;
		}
		return nstar;
	}
	public static int arrlen(char m[])
	{
		int a=0;
		char b;
		for(;;a++)
		{
			try
			{
				b=m[a];
			}
			catch(Exception e)
			{
				break;
			}
		}
		return a;
	}
	public static int arrlen(String m[])
	{
		int a=0;
		String b;
		for(;;a++)
		{
			try
			{
				b=m[a];
			}
			catch(Exception e)
			{
				break;
			}
		}
		return a;
	}
	public static int arrlen(float m[])
	{
		int a=0;
		float b;
		for(;;a++)
		{
			try
			{
				b=m[a];
			}
			catch(Exception e)
			{
				break;
			}
		}
		return a;
	}
	
	public static int arrmax(int m[])
	{
		int max=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]>max)
				max=m[a];
		}
		return max;
	}
	public static char arrmax(char m[])
	{
		char max=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]>max)
				max=m[a];
		}
		return max;
	}
	public static String arrmax(String m[])
	{
		String max="";
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a].compareTo(max)<0)
				max=m[a];
		}
		return max;
	}
	public static float arrmax(float m[])
	{
		float max=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]>max)
				max=m[a];
		}
		return max;
	}
	
	public static int arrmin(int m[])
	{
		int min=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]<min)
				min=m[a];
		}
		return min;
	}
	public static char arrmin(char m[])
	{
		char min=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]<min)
				min=m[a];
		}
		return min;
	}
	public static String arrmin(String m[])
	{
		String min="";
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a].compareTo(min)>0)
				min=m[a];
		}
		return min;
	}
	public static float arrmin(float m[])
	{
		float min=m[0];
		int len=arrlen(m);
		for(int a=0;a<len;a++)
		{
			if(m[a]<min)
				min=m[a];
		}
		return min;
	}
	public static Class getCommonSuperclass(Class c1,Class c2)
	{
		if(c1.isAssignableFrom(c2))
			return c1;
		if(c2.isAssignableFrom(c1))
			return c2;
		Class r=c1;
		while(r!=null)
		{
			r=c1.getSuperclass();
			if(r==null)
				break;
			if(r.isAssignableFrom(c2))
				return r;
		}
		return Object.class;
	}
	public static Class getCommonResultclass(Class c1,Class c2)
	{
		if(c1==null)
			return c2;
		if(c2==null)
			return c1;
		if(c1.isAssignableFrom(c2))
			return c2;
		else
			return c1;
	}
	public static void eQuit(String err)
	{
		sopln(err);
		System.exit(0);
	}
	public static String fLine(File file,int ln)throws IOException
	{
		FileReader fr=null;
		try
		{
			fr=new FileReader(file);
		}
		catch(Exception e)
		{
			return "Error Reading  File;";
		}
		BufferedReader brn=new BufferedReader(fr);
		String line;
		int ct=0;
		while((line=br.readLine()) !=null)
		{
			ct++;
			if(ct==ln)
				return line;
		}
		return null;
	}
	public static boolean conf(String opt)throws IOException
	{
		sTerm("yellow");
		sopln(opt);
		sTerm("green");
		sop("0) Yes\t");
		sTerm("red");
		sopln("1) No");
		sTerm("white");
		char cho=rC();
		int opq=0,fin;
		boolean tf=true;
		try
		{
			opq=ipi(""+cho);
		}
		catch(NumberFormatException e)
		{
			tf=false;
		}
		if(tf)
			fin=opq;
		else
		{
			if(cho=='Y' || cho=='y')
				fin=0;
			else
				fin=1;
		}
		if(fin==0)
			return true;
		else
			return false;
	}
	public static boolean isint(char i)
	{
		int a;
		try
		{
			ipi(""+i);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public static boolean isint(String s)
	{
		int a;
		try
		{
			ipi(s);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public static void halt(int time)
	{
		try
		{
			Thread.sleep(1000*time);
		}
		catch(Exception e)
		{
			Thread.currentThread().interrupt();
		}
	}
	public static void halt(double time)
	{
		try
		{
			Thread.sleep((int)(1000*time));
		}
		catch(Exception e)
		{
			Thread.currentThread().interrupt();
		}
	}
	public static boolean haschar(String s,char c)
	{
		int l=s.length()-1; char d;
		for(int i=0;i<=l;i++)
		{
			d=s.charAt(i);
			if(c==d)
				return true;
		}
		return false;
	}
	public static boolean hasChar(String s,char c)
	{
		int l=s.length()-1; char d;
		for(int i=0;i<=l;i++)
		{
			d=s.charAt(i);
			if(c==d)
				return true;
		}
		return false;
	}
	public static int countchar(String s,char c)
	{
		int l=s.length()-1;
		char d;
		int count=0;
		for(int i=0;i<=l;i++)
		{
			d=s.charAt(i);
			if(c==d)
				count++;
		}
		return count;
	}
	public static int rI()throws IOException
	{
		int i=ipi(rL());
		return i;
	}
	public static int rI(String msg)throws IOException
	{
		if(msg.endsWith(":") || msg.endsWith(" "))
			X.sop(msg);
		else
			X.sop(msg+": ");
		int i=ipi(rL());
		return i;
	}
	public static float rF()throws IOException
	{
		float i=fpf(rL());
		return i;
	}
	public static float rF(String msg)throws IOException
	{
		if(msg.endsWith(":") || msg.endsWith(" "))
			X.sop(msg);
		else
			X.sop(msg+": ");
		float i=fpf(rL());
		return i;
	}
	public static long rLg()throws IOException
	{
		long i=lpl(rL());
		return i;
	}
	public static long rLg(String msg)throws IOException
	{
		if(msg.endsWith(":") || msg.endsWith(" "))
			X.sop(msg);
		else
			X.sop(msg+": ");
		long i=lpl(rL());
		return i;
	}
	public static double rD()throws IOException
	{
		double i=dpd(rL());
		return i;
	}
	public static double rD(String msg)throws IOException
	{
		if(msg.endsWith(":") || msg.endsWith(" "))
			X.sop(msg);
		else
			X.sop(msg+": ");
		double i=dpd(rL());
		return i;
	}
	public static boolean inlist(String mn,String pc,char d,boolean ic)
	{
		int pcs=countchar(mn,d);
		String subs;
		for(int i=0;i<pcs;i++)
		{
			subs=Y.cut(mn,d,i);
			if(ic==true && subs.equalsIgnoreCase(pc))
				return true;
			else if(ic==false && subs.equals(pc))
				return true;
		}
		return false;
	}
	public static int Sadd(String s1,String s2)
	{
		return ipi(s1)+ipi(s2);
	}
	public static int Ssub(String s1,String s2)
	{
		return ipi(s1)-ipi(s2);
	}
	public static int abs(int a)
	{
		if(a<0)
			return a*-1;
		else
			return a;
	}
	public static void pass() // From Python
	{
		sop("");
	}
	public static void pause()throws IOException
	{
		sopln("Press enter to continue ... ");
		br.readLine();
	}
	public static int find(String str,String floc)throws IOException
	{
		File fl1=new File(floc);
		FileReader fr=null;
		try
		{
			fr=new FileReader(fl1);
		}
		catch(Exception e)
		{
			return 0;
		}
		BufferedReader brsp=new BufferedReader(fr);
		String ln1="";
		int i=1;
		while((ln1=brsp.readLine()) !=null)
		{
			if(ln1.startsWith(str))
				return i;
			i++;
		}
		return 0;
	}
	public static void delline(File file1,int lno)
	{
		return;
	}
	public static String fix(float f1,int dec)
	{
		String f2=""+f1;
		int l=f2.length();
		String fixed="";
		int cnt=0;
		boolean ck=false;
		char ch1;
		if(f1>1048576)
			return ""+f1;
		for(int i=0;i<l;i++)
		{
			ch1=f2.charAt(i);
			if(ck)
				cnt++;
			if(ch1=='.')
				ck=true;
			fixed+=ch1;
			if(cnt>=dec)
				break;
		}
		return fixed;
	}
	public static String fix(double f1,int dec)
	{
		String f2=""+f1;
		int l=f2.length();
		String fixed="";
		int cnt=0;
		boolean ck=false;
		char ch1;
		for(int i=0;i<l;i++)
		{
			ch1=f2.charAt(i);
			if(ck)
				cnt++;
			if(ch1=='.')
				ck=true;
			fixed+=ch1;
			if(cnt>=dec)
				break;
		}
		return fixed;
	}
	public static float[] makearray(String n,char d) //delimiter=d
	{
		int l=n.length(); char ch;
		float arrf[]=new float[1000];
		String buff="";
		int count=0;
		for(int i=0;i<l;i++)
		{
			ch=n.charAt(i);
			if(ch==d)
			{
				arrf[count]=fpf(buff);
				count++;
				buff="";
			}
			else
				buff+=ch;
		}
		arrf[count]=X.fpf(buff);
		buff="";
		return arrf;
	}
	public static String[] makearray2(String n,char d) //delimiter=d
	{
		int l=n.length(); char ch;
		String arrf[]=new String[100];
		String buff="";
		int count=0;
		for(int i=0;i<l;i++)
		{
			ch=n.charAt(i);
			if(ch==d)
			{
				arrf[count]=buff; //[0]=3; [1]=+;
				count++; // 1 2
				buff=""; //"",""
			}
			else
				buff+=ch; // 3, +, 5
		}
		arrf[count]=buff;
		buff="";
		return arrf;
	}
	public static int[] aTrim(int m[])
	{
		int l=m.length;
		int n[]=new int[l-1];
		for(int i=0;i<l;i++)
		{
			try
			{
				ipi(""+m[i]);
			}
			catch(Exception e)
			{
				break;
			}
			n[i]=m[i];
		}
		return n;
	}
	public static float[] aTrim(float m[])
	{
		int l=m.length;
		float n[]=new float[l-1];
		for(int i=0;i<l;i++)
		{
			try
			{
				ipi(""+m[i]);
			}
			catch(Exception e)
			{
				break;
			}
			n[i]=m[i];
		}
		return n;
	}
	public static boolean isint(float i)
	{
		if((int)i==i)
			return true;
		else
			return false;
	}
	public static boolean isint(double i)
	{
		if((int)i==i)
			return true;
		else
			return false;
	}
	public static boolean isInt(float i)
	{
		if((int)i==i)
			return true;
		else
			return false;
	}
	public static boolean isInt(double i)
	{
		if((int)i==i)
			return true;
		else
			return false;
	}
	public static int minV(int[] a)
	{
		int min=a[0];
		for(int i=0;i<a.length;i++)
			min=(min>a[i])?a[i]:min;
		return min;
	}
	public static int maxV(int[] a)
	{
		int max=a[0];
		for(int i=0;i<a.length;i++)
			max=(max<a[i])?a[i]:max;
		return max;
	}
	public static String[] testedInput(String[] init,String[] msgs)throws IOException,RuntimeException
	{
		int r=msgs.length-init.length;
		String[] init2=new String[msgs.length];
		int p=init.length;
		if(r==0)
			return init;
		if(r<0)
			throw new RuntimeException();
		for(int i=0;i<r;i++)
		{
			sop(msgs[p+i],"yellow");
			init2[p+i]=X.rL().trim();
		}
		for(int i=0;i<init.length;i++)
			init2[i]=init[i];
		return init2;
	}
	public static int lcm(int[] a)
	{
		int lcm=maxV(a);
		boolean flag=true;
		int i=0;
		for(i=lcm;i>=0;i++)
		{
			for(int j=0;j<a.length;j++)
			{
				if(i%a[j]!=0)
					flag=false;
			}
			if(flag==true)
			{
				break;
			}
			flag=true;
		}
		return i;
	}
}
