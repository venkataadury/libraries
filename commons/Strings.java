package commons;
import maths.Maths;
public class Strings
{
	public static String flip(String in)
	{
		String r="";
		for(char ch : in.toCharArray())
			r=ch+r;
		return r;
	}
	public static char lastLetter(String in)
	{
		return in.charAt(in.length()-1);
	}
	public static String cut(String str,char delim,int field)
	{
		return Y.cut(str,delim,field);
	}
	public static String cut(String str,char[] delims,int field)
	{
		return Y.cut(str,delims,field);
	}
	public static int countChar(String str,char ch)
	{
		int count=0;
		for(char c : str.toCharArray())
		{
			if(c==ch)
				count++;
		}
		return count;
	}
	public static boolean hasChar(String s,char ch)
	{
		for(char c : s.toCharArray())
		{
			if(c==ch)
				return true;
		}
		return false;
	}
	public static boolean isEmpty(String str)
	{
		return (str.trim().equals(""));
	}
	public static char closest(String str,char[] chs)
	{
		int[] inds=new int[chs.length];
		int ind;
		for(int i=0;i<chs.length;i++)
		{
			ind=str.indexOf(chs[i]);
			inds[i]=(ind<0)?str.length():ind;
		}
		int fInd=Maths.min(inds);
		if(fInd>=str.length())
			return (char)0;
		else
			return str.charAt(fInd);
	}
	public static int countChars(String str,char[] chrs)
	{
		int tot=0;
		for(char ch : str.toCharArray())
		{
			for(char c : chrs)
			{
				if(ch==c)
					tot++;
			}
		}
		return tot;
	}
	public static int parseBrokenNumber(String str,int ind) //return the index upto where it is parseable
	{
		char[] st=str.toCharArray();
		String ps="";
		for(int i=ind;i<st.length;i++)
		{
			ps+=st[i];
			if(ps.trim().equals("-"))
				continue;
			if(ps.trim().equals("+"))
				continue;
			try
			{
				X.dpd(ps.trim());
			}
			catch(NumberFormatException e)
			{
				return i;
			}
		}
		return Math.max(str.length(),ind);
	}
	public static double getParsedBrokenNumber(String str,int ind) //return the number upto where it is parseable
	{
		char[] st=str.toCharArray();
		String ps="";
		for(int i=ind;i<st.length;i++)
		{
			ps+=st[i];
			if(ps.trim().equals("-"))
				continue;
			if(ps.trim().equals("+"))
				continue;
			try
			{
				X.dpd(ps.trim());
			}
			catch(NumberFormatException e)
			{
				if(!ps.substring(0,ps.length()-1).equals(""))
					return X.dpd(ps.substring(0,ps.length()-1).trim());
				else
					return 1; //Mathamatically eg. x ==> 1x
			}
		}
		return X.dpd(str.substring(ind).trim());
	}
	public static int indexOfFirst(String str,char[] vls)
	{
		char c;
		for(int i=0;i<str.length();i++)
		{
			c=str.charAt(i);
			for(char ch : vls)
			{
				if(ch==c)
					return i;
			}
		}
		return -1;
	}
	public static char firstOccurance(String str,char[] vls)
	{
		char c;
		for(int i=0;i<str.length();i++)
		{
			c=str.charAt(i);
			for(char ch : vls)
			{
				if(ch==c)
					return ch;
			}
		}
		return (char)0;
	}
	public static int getMatchingBracket(String in,char oB,char cB,int sI)
	{
		int count=0;
		if(in.charAt(sI)!=oB)
			return -1;
		char ch;
		for(int i=sI+1;i<in.length();i++)
		{
			ch=in.charAt(i);
			if(ch==oB)
				count++;
			if(ch==cB)
			{
				if(count==0)
					return i;
				count--;
			}
		}
		return -1;
	}
	public static int getMatchingForebracket(String in,char oB,char cB,int sI)
	{
		int count=0;
		if(in.charAt(sI)!=cB)
			return -1;
		char ch;
		for(int i=sI-1;i>=0;i--)
		{
			ch=in.charAt(i);
			if(ch==cB)
				count++;
			if(ch==oB)
			{
				if(count==0)
					return i;
				count--;
			}
		}
		return -1;
	}
	public static String getNumericBefore(String in,int ind)
	{
		String op="";
		for(int i=ind-1;i>=0;i--)
		{
			if(Character.isDigit(in.charAt(i)) || in.charAt(i)=='.')
				op=in.charAt(i)+op;
			else
				break;
		}
		return op;
	}
	public static String getAlphaNumericBefore(String in,int ind)
	{
		String op="";
		for(int i=ind-1;i>=0;i--)
		{
			if(Character.isLetterOrDigit(in.charAt(i)))
				op=in.charAt(i)+op;
			else
				break;
		}
		return op;
	}
}
