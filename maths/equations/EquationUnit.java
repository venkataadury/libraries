package maths.equations;
import commons.*;
import maths.*;
import java.io.*;

public final class EquationUnit extends Calculator
{
	public String unit="";
	public String num,den;
	public double xCoN,nCoN,nCoD=1;
	boolean pos=true;
	public static final EquationUnit NULL=new EquationUnit();
	public EquationUnit()
	{
		num="0";
		den="1";
		unit="0/1";
		xCoN=nCoN=0;
		nCoD=1;
	}
	public EquationUnit(double v)
	{
		unit=v+"/1";
		num=""+v;
		den="1";
		xCoN=0;
		nCoN=v;
		nCoD=1;
	}
	public EquationUnit(String un)
	{
		unit=un.trim();
		if(unit.equals(""))
		{
			this.assign(NULL);
			return;
		}
		un=un.trim();
		if(Strings.hasChar(un,'/'))
		{
			num=Strings.cut(un,'/',1);
			den=Strings.cut(un,'/',2);
		}
		else
		{
			num=un;
			den="1";
		}
		parseCos();
	}
	public EquationUnit(double a,double b,double c) //x,n,d
	{
		xCoN=a;
		nCoN=b;
		nCoD=c;
		unit+=(a==1)?"x":(a==0)?"":a+"x";
		unit+=((b<0 || a==0)?""+b:"+"+b);
		unit+="/";
		unit+=c;
		num=Strings.cut(unit,'/',1);
		den=Strings.cut(unit,'/',2);
	}
	public void assign(EquationUnit ext)
	{
		this.xCoN=ext.xCoN;
		this.nCoN=ext.nCoN;
		this.nCoD=ext.nCoD;
		this.num=ext.num;
		this.den=ext.den;
		this.unit=ext.unit;
	}
	public String getUnit() //To be modified
	{
		String res="";
		if(nCoD==0)
			return "Infinity";
		res+=(pos)?"":"-";
		res+=(xCoN==0)?"":(xCoN==1)?"x":Maths.perfect(xCoN)+"x";
		res+=(nCoN==0)?"":(xCoN>0)?"+"+Maths.perfect(nCoN):""+Maths.perfect(nCoN);
		res+=(nCoD==1)?"":"/"+Maths.perfect(nCoD);
		return res+" ";
		//return unit+" ";
	}
	public String getUnitWhole()
	{
		String res="";
		if(nCoD==0)
			return "Infinity";
		res+=(pos)?"":"-";
		res+=(xCoN==0)?"":(xCoN==1)?"x":Maths.perfect(xCoN)+"x";
		res+=(nCoN==0)?"":(xCoN>0)?"+"+Maths.perfect(nCoN):""+Maths.perfect(nCoN);
		res+=(nCoD==1)?"":"/"+Maths.perfect(nCoD);
		if(res.trim().equals("") || (Strings.hasChar(res,'/') && Y.cut(res,'/',1).trim().equals("")))
			return "0";
		else
			return res+" ";
	}
	public String getUnitWithSign(boolean trf)
	{
		String res="";
		if(nCoD==0)
			return "Infinity";
		res+=(pos)?"+":"-";
		if(!trf) //Human reading - false
			res+=" ";
		res+=(xCoN==0)?"":(xCoN==1)?"x":Maths.perfect(xCoN)+"x";
		res+=(nCoN==0)?"":(xCoN>0)?"+"+Maths.perfect(nCoN):""+Maths.perfect(nCoN);
		res+=(nCoD==1)?"":"/"+Maths.perfect(nCoD);
		return res+" ";
		/*
		if(!den.trim().equals("1") && !den.trim().equals("1.0"))
			return ((pos)?"+ ":"- ")+Maths.perfect(Y.cut(unit,'/',1))+"/"+Y.cut(unit,'/',2)+" ";
		else
			return ((pos)?"+ ":"- ")+Maths.perfect(Y.cut(unit,'/',1));*/
	}
	private void parseCos()
	{
		//Changed for the better :D
		String uni=unit.trim();
		int sp=0,sp2;
		String numer,denom;
		if(uni.trim().equals("=") || uni.trim().equals("+"))
		{
			this.assign(NULL);
			return;
		}
		if(Strings.hasChar(uni,'/'))
		{
			numer=Y.cut(uni,'/',1).trim();
			denom=Y.cut(uni,'/',2).trim();
		}
		else
		{
			numer=uni;
			denom="1";
		}
		boolean pos=true;
		do
		{
			sp2=Strings.parseBrokenNumber(numer,sp);
			if(sp2>=numer.length())
			{
				nCoN+=Strings.getParsedBrokenNumber(numer,sp);
				break;
			}
			else if(numer.charAt(sp2)=='x')
			{
				xCoN+=Strings.getParsedBrokenNumber(numer,sp);
				sp=sp2+1;
				continue;
			}
			else if(numer.charAt(sp2)=='+' || numer.charAt(sp2)=='-')
			{
				nCoN+=Strings.getParsedBrokenNumber(numer,sp);
				sp=sp2+1;
				continue;
			}
			else
			{
				throw new EquationUnitParserException(unit);
			}
		}
		while(sp<numer.length());
		sp=0;
		nCoD=0;
		do
		{
			sp2=Strings.parseBrokenNumber(denom,sp);
			if(sp2>=denom.length())
			{
				nCoD+=Strings.getParsedBrokenNumber(denom,sp);
				break;
			}
			else if(denom.charAt(sp2)=='+' || denom.charAt(sp2)=='-')
			{
				nCoD+=Strings.getParsedBrokenNumber(denom,sp);
				sp=sp2+1;
				continue;
			}
			else
			{
				throw new EquationUnitParserException("Denom: "+unit);
			}
		}
		while(sp<denom.length());
		if(nCoD==0)
			nCoD=1;
	}
	public void multiply(double V)
	{
		this.assign(new EquationUnit(xCoN*V,nCoN*V,nCoD));
	}
	public void divide(double V)
	{
		this.assign(new EquationUnit(xCoN,nCoN,nCoD*V));
	}
}
class EquationUnitParserException extends RuntimeException
{
	public EquationUnitParserException()
	{
			X.sopln("An error occured while parsing a certain Equation Unit.","red");
	}
	public EquationUnitParserException(String eqn)
	{
		X.sopln("Error occured, while parsing Equation Unit: '"+eqn+"'. Please check it.","red");
	}
}
