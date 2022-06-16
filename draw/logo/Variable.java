package draw.logo;
import commons.*;
import maths.Maths;

public class Variable
{
	String vName,vVal;
	
	public Variable(String n,String v)
	{
		vName=n;
		vVal=v.replace('!',',');
	}
	
	public String getName()
	{
		return vName;
	}
	public String getVal()
	{
		return vVal;
	}
}
