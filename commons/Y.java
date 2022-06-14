package commons;
import java.io.*;
import java.lang.*;
import java.util.*;
public class Y 
{
	static File mainfile=new File(X.HOME+"/.usersjava");
	public static void main(String args[])throws IOException
	{
		X.eQuit("Do not run this directly.");
	}
	public static String userdat(String user)throws IOException
	{
		File file=new File(X.HOME+"/.javausers/"+user);
		if(! file.exists())
		{
			boolean bool=file.createNewFile();
			if(! bool)
				X.eQuit("Error while creating user "+user);
			FileWriter fw=null;
			try
			{
				fw=new FileWriter(file,false);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				X.eQuit("");
			}
			String data=user+"-calc-conv-time-math-run";
			int len=data.length()-1;
			char ci;
			for(int i=0;i<=len;i++)
			{
				ci=data.charAt(i);
				fw.write(ci);
			}
			fw.close();
			return data;
		}
		else
		{
			FileReader fr=null;
			try
			{
				fr=new FileReader(file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				X.eQuit("");
			}
			BufferedReader br=new BufferedReader(fr);
			String datu=br.readLine();
			return datu;
		}
	}
	public static String cut(String str,char d,int f)
	{
		if(f==1)
		{
			int iof=str.indexOf(d);
			if(iof==-1)
				return str;
			else
				return(str.substring(0,str.indexOf(d)));
		}
		else
		{
			return cut(str.substring(str.indexOf(d)+1),d,f-1);
		}
	}
	public static String cut(String str,String d,int f)
	{
		if(f==1)
		{
			int iof=str.indexOf(d);
			if(iof==-1)
				return str;
			else
				return(str.substring(0,str.indexOf(d)));
		}
		else
			return cut(str.substring(str.indexOf(d)+d.length()),d,f-1);
	}
	//
	public static String cut(String str,char[] ds,int f) //Tomorrow e,x,z 2
	{
		char d;
		if(f==1)
		{
			d=Strings.closest(str,ds);
			int iof=str.indexOf(d);
			if(iof==-1)
				return str;
			else
				return(str.substring(0,str.indexOf(d)));
		}
		else
		{
			d=Strings.closest(str,ds);
			if(d==(char)0)
				return str;
			return cut(str.substring(str.indexOf(d)+1),ds,f-1);
		}
	}
	//
	public static void resetTerminal()
	{
		int c=0;
		X.clear();
		X.sTerm("white");
		return;
	}
	public static boolean isadmin(String user)throws IOException
	{
		File file1=new File(X.HOME+"/.usersjava");
		FileReader fr=null;
		try
		{
			fr=new FileReader(file1);
		}
		catch (Exception e)
		{
			return false;
		}
		BufferedReader br=new BufferedReader(fr);
		String udata="";
		boolean flag=false;
		while((udata=br.readLine()) != null)
		{
			if(cut(udata,'-',1).equals(user.trim()))
			{
				char lch=udata.charAt(udata.length()-1);
				if(lch=='0')
					flag=true;
				else
					flag=false;
			}
		}
		return flag;
		
	}
	public static String getuser()throws IOException
	{
		File file2=new File(X.HOME+"/.javalock");
		FileReader fr=null;
		try
		{
			fr=new FileReader(file2);
		}
		catch (Exception e)
		{
			return "";
		}
		BufferedReader br=new BufferedReader(fr);
		String user=br.readLine();
		return user;
	}
	public static boolean hasuser(String uname)throws IOException
	{
		File file3=new File(X.HOME+"/.usersjava");
		FileReader fr=null;
		try
		{
			fr=new FileReader(file3);
		}
		catch(Exception e)
		{
			return true;
		}
		BufferedReader br=new BufferedReader(fr);
		String ln="",un1="";
		while((ln=br.readLine()) !=null)
		{
			un1=cut(ln,'-',1);
			if(un1.equals(uname))
				return true;
		}
		return false;
	}
	public static String pCode(String uname)throws IOException
	{
		String passr="";
		File file3=new File(X.HOME+"/.usersjava");
		FileReader fr=null;
		try
		{
			fr=new FileReader(file3);
		}
		catch(Exception e)
		{
			return "";
		}
		BufferedReader br=new BufferedReader(fr);
		String ln="",un1="",pas1="";
		while((ln=br.readLine()) !=null)
		{
			un1=cut(ln,'-',1);
			if(un1.equals(uname))
				passr=cut(ln,'-',2);
		}
		if(passr!="")
			return passr.substring(0,passr.length()-1);
		else
			return "";
	}
	public static String passRd()throws IOException
	{
		Console csl=System.console();
		char passw[];
		String ret;
		passw=csl.readPassword();
		ret=new String(passw);
		return ret;
	}
}
