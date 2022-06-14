package commons;
import java.io.*;
public class FOps
{
	static FileReader fr=null;
	static BufferedReader br=null;
	public static int nLines(File f)
	{
		int q=0;
		try
		{
			fr=new FileReader(f);
			br=new BufferedReader(fr);
		
		
		String st;
		while((st=br.readLine()) !=null)
		{
			q++;
		}
		}
		catch(Exception e) {e.printStackTrace();return -1;}
		return q;
	}
	public static String getLine(File f,int ln)
	{
		int q=0;
		try
		{
			fr=new FileReader(f);
			br=new BufferedReader(fr);
		String st;
		while((st=br.readLine()) !=null)
		{
			q++;
			if(q==ln)
				return st;
		}
		}
		catch(Exception e) {e.printStackTrace();return "";}
		return "";
	}
	public static void append(File f,String txt)
	{
		FileWriter fw=null;
		BufferedWriter bw=null;
		try
		{
			fw=new FileWriter(f,true);
			bw=new BufferedWriter(fw);
			bw.write(txt+"\n");
			bw.close();
		}
		catch(Exception e) {e.printStackTrace();X.sopln("Error occured","red");return;}
	}
	public static void delLine(File f,int l)
	{
		String[] lns=getLines(f);
		
	}
	public static String[] getLines(File f)
	{
		String[] ln=new String[nLines(f)];
		int q=0;
		try
		{
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			String st;
		while((st=br.readLine()) !=null)
		{
			ln[q]=st;
			q++;
		}
		}
		catch(Exception e) {e.printStackTrace();return new String[0];}
		return ln;
	}
}
