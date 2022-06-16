package draw.logo;
import commons.*;
import maths.Maths;
import maths.functions.Function;

public class RetFunc extends Func
{
	private Variable[] funcspace=new Variable[0];
	private LOGO link=null;
	public RetFunc(String n,String parl,String s)
	{
		super(n,parl,s);
	}
	public RetFunc(String in)
	{
		super(in);
	}
	public RetFunc(String n,Function f)
	{
		super(n,"x","callMathFunction("+n+",$x)");
	}
	
	/*public String getOutput(String ext) //Call eg. pwr(2,5) --> 32
	{
		String cl=super.call(ext);
		int ind=0,ind2=0;
		// pwr(a,b) = set(c,1) & $b: set(c,%c*$a) ; print($c) & return(%c)
		//sqr(a) = set(d,$c*$c) ; return(%d)
		while(ind<cl.length())
		{
			
		}
		return "";
	}*/
	
	/*public String call(String ext)
	{
		return "print(The function returns a value. Do not use directly.)";
	}*/
}
