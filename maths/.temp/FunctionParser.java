package maths;
import commons.*;

public class FunctionParser extends Maths
{
    private String in;
    private int pos=0,len;
    private int brks=0;
    /*
            Input Examples
        x +2, x, x -2, 2x, 2x -3, 2x^2 -3x +2, x^2, 2x-3/2x+3 (Division specific), x^2/2 + 1/3
    */
    public FunctionParser(String i) 
    {
        in=i;
        len=in.length()-1;
        brks=Strings.countChar(in,' ');
    }
    
    public Operatable getNextTerm()
		{
			Operatable o1=Constant.UNITY,o2=Constant.UNITY,o3=Constant.UNITY,o4=Constant.UNITY;
			if(pos>brks)
				return null;
			String ps,ps2;
			boolean neg=false;
			if(!X.hasChar(in,' '))
			{
				pos++;
				ps=in.trim();
			}
			else
				ps=Y.cut(in,' ',++pos);
			double v;
			ps2=ps;
			if(ps.startsWith("+"))
				ps2=ps.substring(1);
			if(ps.startsWith("-"))
			{
				ps2=ps.substring(1);
				neg=true;
			}
			if(ps2.startsWith("sin") || ps2.startsWith("cos") || ps2.startsWith("tan") || ps2.startsWith("cot") || ps2.startsWith("sec") || ps2.startsWith("cosec"))
			{
				if(neg)
					return new Term(new Trig_Ratios(ps2.trim()),Constant.MINUS);
				else
					return new Trig_Ratios(ps2.trim());
			}
			if(ps2.startsWith("log"))
			{
				if(neg)
					return new Term(new Logarithm(ps2.trim()),Constant.MINUS);
				else
					return new Logarithm(ps2.trim());
			}
			try {v=X.dpd(ps.trim());return new Constant(v);}
			catch(NumberFormatException e) {}
			try {v=X.dpd(ps.replace("/","").trim());return new Frac(in.trim());}
			catch(NumberFormatException e) {}
			if(X.hasChar(ps,'/'))
			{
				String nu,de;
				nu=Y.cut(ps,'/',1).trim();
				de=Y.cut(ps,'/',2).trim();
				nu=nu.replace("+"," +").replace("-"," -");
				de=de.replace("+"," +").replace("-"," -");
				nu=nu.replace("  "," ");
				de=de.replace("  "," ");
				Function f1=new Function(nu);
				Function f2=new Function(de);
				return f1.divi(f2);
			}
			else
			{
				char c1=ps.charAt(0);
				if(!Character.isDigit(c1) && c1!='+' && c1!='-')
					ps="1"+ps;
				if(!X.hasChar(ps,'^'))
					ps=ps+"^1";
				UnitStringParser usp=new UnitStringParser(ps);
				char c2=usp.getNextChar();
				String b1="",b2="";
				boolean bd=true;
				do
        {
					if(Character.isDigit(c2))
					{
						if(bd)
							b1+=c2;
						else
							b2+=c2;
						if(usp.end())
						{
							if(b2.equals(""))
								b2="1";
							if(o3==Constant.UNITY)
								o3=new Term(new Constant(X.dpd(b2)));
							b2="";
							//if(o4==Constant.UNITY)
								return new Term(o2,o1,o3);
							//else
								//return o4.mul(o1);
						}
						/*if(Character.isLetter(ps.charAt(usp.getPos())))
						{
							if(b2.equals(""))
								b2="1";
							if(o4==Constant.UNITY)
								o4=new Term(o2,o1,o3);
							else
								o4=o4.mul(new Term(o2,Constant.UNITY,o3));
							b2="";
							
						}*/
					}
					if(c2=='.')
					{
						if(bd)
							b1+=c2;
						else
							b2+=c2;
					}
					if(c2=='-')
					{
						if(usp.end()) {}
						else
						{
							if(Character.isLetter(ps.charAt(usp.getPos()))) 
							{
								if(bd)
									b1+=(c2+"1");
								else
									b2+=(c2+"1");
							}
							else
							{
								if(bd)
									b1+=c2;
								else
									b2+=c2;
							}
						}
						
					}
					if(Character.isLetter(c2))
					{
						if(usp.end())
						{
							if(b2.equals(""))
								b2="1";
							if(o3==Constant.UNITY)
								o3=new Term(new Mirror(c2),new Constant(X.dpd(b2)));
							else
								o3=o3.mul(new Mirror(c2));
							b2="";
							//if(o4==Constant.UNITY)
								return new Term(o2,o1,o3);
							//else
								//return o4.mul(o1);
						}
						if(Character.isLetter(ps.charAt(usp.getPos())))
						{
							if(b1.equals(""))
								b1="1";
							if(o1==Constant.UNITY)
								o1=new Term(new Mirror(c2),new Constant(X.dpd(b1)));
							else
								o1=o1.mul(new Mirror(c2));
							b1="";
						}
						else if(ps.charAt(usp.getPos())=='^')
						{
							o2=new Mirror(c2);
							bd=false;
							if(b1.equals(""))
								b1="1";
							if(o1==Constant.UNITY)
								o1=new Term(new Constant(X.dpd(b1)),Constant.UNITY);
							b1="";
						}
						
					}
					c2=usp.getNextChar();
				}
				while(c2!=(char)0);
				return null;
			}
		}
}