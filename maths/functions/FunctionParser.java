package maths.functions;
import commons.*;
import maths.Maths;

public class FunctionParser
{
	Function fx=null;
	Term tT=new Term(Constant.ONE);
	public FunctionParser(String str)
	{
		fx=new Function();
		parse(str);
	}
	
	private void parse(String str)
	{
		str=str.replace("  "," ").trim();
		int n=Strings.countChar(str,' ');
		String t=null;
		int K=1;
		FuncBlock pS;
		while(K<=n+1)
		{
			t=Y.cut(str,' ',K++);
			pS=processStep(t);
			if(pS!=null)
				fx.addTerm(pS);
		}
	}
	
	private FuncBlock processStep(String t)
	{
		t=t.trim();
		if(t==null || t.equals(""))
			return new Term(Constant.ONE);
		try {return new Constant(Maths.stringArithmetic(t));}
		catch(Exception e) {}
		int i1=t.indexOf('/');
		int i2=t.indexOf('(');
		int i3=-1;
		int i4=t.indexOf('^');
		if(i2!=-1)
		{
			i3=Strings.getMatchingBracket(t,'(',')',i2);
			String ss=t.substring(i2+1,i3);
			tT.mul(processStep(ss));
			tT.mul(processStep(t.replace(ss,"")));
			return tT;
		}
		
		/*if(i4!=-1)
		{
			
		}*/
		if(i1!=-1)
		{
			String num=t.substring(0,i1);
			String den=t.substring(i1+1);
			tT=tT.mul(processStep(num));
			tT=tT.divi(processStep(den));
			return tT;
		}
		
		if(Maths.isNumber(t))
			return new Term(new Constant(X.dpd(t)));
		
		int i5=t.indexOf('[');
		if(i5!=-1)
		{
			return null;
		}
		Term ret=new Term();
		int lc=0;
		String n="";
		while(lc<t.length())
		{
			if(Character.isDigit(t.charAt(lc)) || t.charAt(lc)=='.' || (lc==0 && t.charAt(lc)=='-' || t.charAt(lc)=='+'))
				n+=t.charAt(lc);
			else
				break;
			lc++;
		}
		if(lc==0)
			n="1";
		if(lc==1 && !Character.isDigit(n.charAt(0)))
			n+="1";
		t=t.substring(lc);
		ret.setMul(X.dpd(n));
		if(Character.isLetter(t.charAt(0)))
			ret.setBase(new Mirror(t.charAt(0)));
		else
			throw new RuntimeException("Corrupt Expression part: "+t);
		t=t.substring(1);
		if(t.length()<2)
			t+="^1";
		if(t.charAt(0)=='^')
			ret.setPower(processStep(t.substring(1)));
		else
			ret.setPower(Constant.ONE);
		return ret;
	}
	public Function getFunction() {return fx;}
}
