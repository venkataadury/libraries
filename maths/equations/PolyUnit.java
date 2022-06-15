package maths.equations;
import maths.*;
import commons.*;
import java.io.*;
	/*
		x^2/2
		3x^4/2
		3x^2+2/4
		4x^12+8/33
		2x^2+3x^3/8
	*/
public class PolyUnit extends Calculator
{
	public String unit;
	public double DEN=0;
	public Xpc[] pcs=new Xpc[0];
	
	public PolyUnit() {}
	public PolyUnit(String str)
	{
		unit=str.trim();
		parseCos();
	}
	public PolyUnit(Xpc[] given)
	{
		this(given,1);
	}
	public PolyUnit(Xpc[] given,double DENOM)
	{
		DEN=DENOM;
		pcs=given;
		unit=toShow();
	}
	public void parseCos()
	{
		String uni=unit;
		if(!Strings.hasChar(uni,'/'))
			uni=uni+"/1";
		String numer=Y.cut(uni,'/',1).trim();
		String denom=Y.cut(uni,'/',2).trim();
		if(uni.indexOf("x^")==-1)
		{
			if(!Strings.hasChar(uni,'x'))
			{
				DEN+=X.dpd(denom);
				pcs=appendX(pcs,new Xpc(X.dpd(numer)));
				return;
			}
			else
				uni=uni.replace("x","x^1");
		}
		numer=Y.cut(uni,'/',1).trim();
		denom=Y.cut(uni,'/',2).trim();
		int sp=0,sp2;
		String str1,str2;
		do
		{
			sp2=Strings.parseBrokenNumber(numer,sp);
			if(sp2>=numer.length())
			{
				pcs=appendX(pcs,new Xpc(Strings.getParsedBrokenNumber(numer,sp)));
				break;
			}
			else if(numer.charAt(sp2)=='x')
			{
				double dv=Strings.getParsedBrokenNumber(numer,sp);
				str1=Maths.perfect(dv)+numer.substring(sp2);
				str1=Y.cut(str1,operators,1);
				pcs=appendX(pcs,new Xpc(str1));
				sp=sp+str1.length();
				if(dv>=0)
					sp++;
				if(sp<numer.length())
					continue;
				else
					break;
			}
			else if(numer.charAt(sp2)=='+' || numer.charAt(sp2)=='-')
			{
				pcs=appendX(pcs,new Xpc(Strings.getParsedBrokenNumber(numer,sp)));
				sp=sp2+1;
				if(sp<numer.length())
					continue;
				else
					break;
			}
			else
				throw new EquationUnitParserException(uni);
		}
		while(sp<numer.length());
		sp=0;
		sp2=0;
		do
		{
			sp2=Strings.parseBrokenNumber(denom,sp);
			if(sp2>=denom.length())
			{
				DEN+=Strings.getParsedBrokenNumber(denom,sp);
				break;
			}
			else if(numer.charAt(sp2)=='+' || numer.charAt(sp2)=='-')
			{
				DEN+=Strings.getParsedBrokenNumber(denom,sp);
				sp=sp2+1;
				continue;
			}
			else
				throw new EquationUnitParserException(uni);
		}
		while(sp<denom.length());
		for(Xpc xp : pcs)
			xp.fix();
		if(DEN==0)
			DEN=1;
	}
	public static Xpc[] appendX(Xpc[] m,Xpc p)
	{
		Xpc[] xpcs=new Xpc[m.length+1];
		for(int i=0;i<m.length;i++)
			xpcs[i]=m[i];
		xpcs[m.length]=p;
		return xpcs;
	}
	public String toShow()
	{
		return toShowWithSymbol().substring(1);
	}
	public String toShowWithSymbol()
	{
		String res="";
		for(Xpc V : pcs)
		{
			res+=(V.pos)?"+":"-";
			res+=(V.co==1 || V.co==0)?"":""+Maths.perfect(V.co);
			res+=(V.Xpo==0)?"":(V.Xpo==1)?"x":"x^"+V.Xpo;
		}
		res+=(DEN==1)?"":"/"+Maths.perfect(DEN);
		if(res.trim().equals(""))
			return "0 ";
		else
			return res.trim()+" ";
	}
	public boolean equates(double v,boolean s,String col)
	{
		double val=valueWith(v,s,col);
		return val==0;
	}
	public boolean equates(double v)
	{
		return equates(v,false,"white");
	}
	public double valueWith(double v,boolean s,String col)
	{
		double val=0;
		for(Xpc unX : pcs)
		{
			val+=unX.substitute(v);
			if(s)
				X.sop(unX.represent(v),col);
		}
		return val/DEN;
	}
	public double valueWith(double v,boolean s)
	{
		return valueWith(v,s,"white");
	}
	public double valueWith(double v)
	{
		return valueWith(v,false);
	}
	public double divideBy(double dv) // x-dv
	{
		return 0D;
	}
}
class Xpc implements Arithmetical<Xpc>
{
	int Xpo=0;
	double co=0;
	boolean pos=true;
	Xpc() {}
	Xpc(double c,int p)
	{
		co=c;
		Xpo=p;
	}
	Xpc(double v)
	{
		co=v;
		Xpo=0;
	}
	Xpc(String str)
	{
	 //3x^2
	 str=str.trim();
	 if(!Strings.hasChar(str,'^'))
	 {
	 	if(!Strings.hasChar(str,'x'))
	 	{
	 		co=X.dpd(str);
	 		Xpo=0;
	 		return;
	 	}
	 	else
	 		str=str.replace("x","x^1").trim();
	 }
	 co=Strings.getParsedBrokenNumber(str,0);
	 Xpo=(int)X.dpd(Y.cut(str,'^',2).trim());
	}
	public void fix()
	{
		if(Xpo<0)
			Xpo=Math.abs(Xpo);
	}
	public void MUL(double v)
	{
		co*=v;
	}
	public void DIVI(double v)
	{
		co/=v;
	}
	public void ADD(double v)
	{
		co+=v;
	}
	public void SUB(double v)
	{
		co-=v;
	}
	public void ADD(Xpc ex)throws TypeMismatchException
	{
		if(ex.Xpo!=this.Xpo)
			throw new TypeMismatchException();
		else
			this.co+=ex.co;
		/*if(co<0)
			pos=false;*/
	}
	public void SUB(Xpc ex)throws TypeMismatchException
	{
		if(ex.Xpo!=this.Xpo)
			throw new TypeMismatchException();
		else
			this.co-=ex.co;
		/*if(co<0)
			pos=false;*/
	}
	public Xpc diviX()
	{
		if(Xpo>0)
			return new Xpc(co,Xpo-1);
		else
			X.sopln("Division error. X goes to denominator.","red");
		return null;
	}
	public double substitute(double v)
	{
		return co*Math.pow(v,Xpo);
	}
	public String represent(double su)
	{
		String res="";
		if(co==0)
			return "0 ";
		res+=(co==1)?"":Maths.perfect(co);
		res+=(Xpo==0 || co==1)?"":"*";
		res+=(Xpo==0)?"":(Xpo==1)?""+Maths.perfect(su):Maths.perfect(su)+"^"+Xpo;
		return res.trim()+" ";
	}
}
class InvalidDivisionPracticeException extends RuntimeException
{
	InvalidDivisionPracticeException()
	{
		X.sopln("Invalid Polynomial division. Please check input. (Problem may still persist).","Red");
	}
	InvalidDivisionPracticeException(int v)
	{
		X.sopln("Invalid Polynomial division with "+v+" terms. Please check input. (Problem may still persist).","red");
	}
}
