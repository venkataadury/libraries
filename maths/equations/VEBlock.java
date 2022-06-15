package maths.equations;
import commons.*;
import maths.*;
import java.io.IOException;

public class VEBlock extends Object
{
	double coef;
	char var;
	boolean cons=false;
	String inS;
	public VEBlock() {coef=0.0D;var=(char)0;cons=true;}
	
	public VEBlock(double cf)
	{
		coef=cf;
		var=(char)0;
		cons=true;
		inS=Double.toString(cf);
	}
	public VEBlock(char c,double v)
	{
		var=c;
		coef=v;
		inS=(""+v)+c;
	}
	public VEBlock(String bk,boolean ps)
	{
		bk=bk.trim().replace(" ","");
		if(Character.isDigit(bk.charAt(bk.length()-1)))
		{
			coef=X.dpd(bk);
			var=(char)0;
			cons=true;
		}
		else
		{
			if(bk.length()==1)
				bk="+"+bk;
			if(!Character.isDigit(bk.charAt(bk.length()-2)))
				bk=bk.replace("+","+1").replace("-","-1");
			coef=X.dpd(bk.substring(0,bk.length()-1));
			var=bk.charAt(bk.length()-1);
			cons=false;
		}
		if(!ps)
			coef*=-1;
	}
	public VEBlock(String bk)
	{
		this(bk,true);
	}
	public void print(String col)
	{
		X.sopln(Maths.perfect(coef)+""+var,col);
	}
	public void print()
	{
		print("white");
	}
	
	public void type(String col)
	{
		type(col,true);
	}
	public void type(String col,boolean sym)
	{
		X.sop(((sym && coef>0)?"+":"")+Maths.perfect(coef)+""+var,col);
	}
	public void type()
	{
		type("white");
	}
	
	public void mul(double v)
	{
		coef*=v;
	}
	public void divi(double v)
	{
		coef/=v;
	}
}
