package maths;
import commons.*;

public class Term extends Maths implements Operatable
{
	Operatable mul,pwr;
	Operatable base;
	char vN=(char)0;
	boolean pri=true; //PWR first
	
	public static final Term BASE=new Term(new Mirror());
	public static final Term SQR=new Term(BASE,Constant.UNITY,new Constant(2));
	public static final Term CUBE=new Term(BASE,Constant.UNITY,new Constant(3));
	
	public Term() 
	{
		mul=new Constant(1);
		pwr=new Constant(1);
		base=new Mirror();
		vN=(char)0;
	}
	public Term(char v)
	{
            this();
            vN=v;
	}
	public Term(double v)
	{
		this();
		base=new Constant(v);
	}
	public Term(double v,double m)
	{
		this(v);
		mul=new Constant(m);
	}
	public Term(double v,double m,double p)
	{
		this(v,m);
		pwr=new Constant(p);
	}
	public Term(Operatable v)
	{
		this();
		base=v;
		vN=v.getVar();
	}
	public Term(Operatable v,Operatable m)
	{
		this(v);
		/*if(mul.getVar()!=(char)0 && mul.getVar()!=v.getVar() && v.getVar()!=(char)0)
			throw new RuntimeException("Multiple variables in one term");*/
		mul=m;
		vN=v.getVar();
	}
	public Term(Operatable v,Operatable m,Operatable p)
	{
		this(v,m);
		/*if(!match(v,m,p))
			throw new RuntimeException("Multiple variables in one term");*/
		pwr=p;
	}
	public Term(Operatable v,Operatable m,Operatable p,char va)
	{
		this(v,m,p);
		vN=va;
	}
	public Term(Operatable v,Operatable m,Operatable p,boolean pr)
	{
            this(v,m,p);
            pri=pr;
	}
	private boolean match(Operatable o1,Operatable o2,Operatable o3)
	{
		char c1,c2,c3;
		c1=o1.getVar();
		c2=o2.getVar();
		c3=o3.getVar();
		if(c1!=(char)0)
		{
			if(c2!=c1 && c2!=(char)0)
				return false; 
			if(c3!=c1 && c3!=(char)0)
				return false;
		}
		if(c2!=(char)0)
		{
			if(c2!=c3 && c3!=(char)0)
				return false;
		}
		return true;
	}
	public double getVal(double v)
	{
		double K=Math.pow(base.getVal(v),pwr.getVal(v));
		K*=mul.getVal(v);
		return K;
	}
	public double getVal(char[] vars,double[] vals)
	{
		double K=Math.pow(base.getVal(vars,vals),pwr.getVal(vars,vals));
		K*=mul.getVal(vars,vals);
		return K;
	}
	public char getVar()
	{
		return vN;
	}
	public boolean isConst()
	{
		if(pwr.isConst() && pwr.getVal(0)==0)
			return mul.isConst();
		return (base.isConst() && mul.isConst() && pwr.isConst());
	}
	public void addCoef(double v)throws InconsistantCoefException
	{
		if(mul.isConst())
			mul=new Constant(mul.getVal(1)+v);
		throw new InconsistantCoefException();
	}
	public double getPwr()throws InconsistantPowerException
	{
		if(pwr.isConst())
			return pwr.getVal(1);
		throw new InconsistantPowerException();
	}
	public double getCoef()throws InconsistantCoefException
	{
		if(mul.isConst())
			return mul.getVal(1);
		throw new InconsistantCoefException();
	}
	public void typeExpr(String col)
	{
		X.sTerm(col);
		boolean b1=false,b2=false;
		if(mul.isConst() && mul.getVal(0)==1) {}
		else
		{
			b1=true;
			if(!mul.isConst())
			{
				X.sop("(");
				mul.typeExpr(col);
				X.sop(")");
			}
			else
				mul.typeExpr(col);
		}
		if(base.isConst() && base.getVal(0)==1 ) {}
		else
		{
			if((!b1 && (pwr.isConst() && pwr.getVal(0)==1)))
				base.typeExpr(col);
			else
			{
				X.sop("(");
				base.typeExpr(col);
				X.sop(")");
			}
			if(pwr.isConst() && pwr.getVal(0)==1) {}
			else
			{
				if(pwr.isConst())
				{
					X.sop("^");
					pwr.typeExpr(col);
				}
				else
				{
					X.sop("^(");
					pwr.typeExpr(col);
					X.sop(")");
				}
			}
		}
		X.sTerm("white");
	}
	public void printExpr(String col)
	{
		typeExpr(col);
		X.sopln();
	}
	public void printExpr()
	{
		printExpr("white");
	}
	public void typeExpr()
	{
		typeExpr("white");
	}
	public void setVar(char c)
	{
		base.setVar(c);
		mul.setVar(c);
		pwr.setVar(c);
	}
	public boolean hasVar(char v)
	{
            return (base.hasVar(v) || mul.hasVar(v) || pwr.hasVar(v));
	}
	public Operatable differentiate(char resp)
	{
            if(!this.hasVar(resp))
                return Constant.ZERO;
            final Function ret=new Function(),ret2=new Function();
            final Term tD1;
            final Operatable pwrD;
						if(this.isConst())
							return Constant.ZERO;
						Term proc=(Term)this.getClone();
            if(pwr.isConst() || !pwr.hasVar(resp))
            {
                pwrD=new Function(new Operatable[] {proc.pwr,new Constant(-1)});
                if(mul.isConst() || !mul.hasVar(resp))
                {
                    // 2(x^2 + 2x)^3
										if(pwrD.isConst() && pwrD.getVal(0)==0)
											tD1=new Term((proc.pwr.mul(mul)),Constant.UNITY);
										else
											tD1=new Term((proc.pwr.mul(mul)).mul(new Term(proc.base,Constant.UNITY,pwrD)),Constant.UNITY);
										final Term RE=new Term(tD1,base.differentiate(resp));
                    return RE;
                }
                else
                {
                    //(2x +3)(x^2 +2x)^4
                    tD1=new Term(proc.base,Constant.UNITY,proc.pwr);
                    ret.append((proc.mul.differentiate(resp)).mul(tD1));
                    ret.append(tD1.differentiate(resp).mul(proc.mul));
                    return ret;
                }
            }
            else
            {
                // (2x +3)^(2x)
                return null;
            }
	}
	public Operatable integrate(char resp)throws IntegrationException
	{
		
		 /*final Function ret=new Function();
		final Operatable pwrI;
		final Term MUL;
		Term proc=(Term)this.getClone();
		if(pwr.isConst() || !pwr.hasVar(resp))
		{
			pwrI=new Function(new Operatable[] {pwrI.getClone(),Constant.UNITY});
			if(pwr.getVal(new char[] {resp},new double[] {0})==-1)
			{
				if(mul.isConst() || !mul.hasVar(resp))
				{
					MUL=new Term(mul.getClone(),new Reciprocal(base.differentiate(resp)));
					return new Term(new Logarithm(base.getClone(),Math.E),MUL);
				}
				else
					return null;
			}
			if(mul.isConst() || !mul.hasVar(resp))
			{
				MUL=new Term(mul.getClone(),new Reciprocal(base.differentiate(resp)));
				return new Term(new Term(base.getClone(),base.differentiate(resp),pwr.getClone()),MUL).integrate(resp);
			}
			else
			{
				return mul.getClone().mul(base.getClone().integrate(resp)).sub(new Term(mul.getClone().differentiate(resp),base.getClone().integrate(resp)).integrate(resp));
			}
		}
		else
		{
			return null;
		}*/
		//2x^3
		// 2(x^2 + 2x)^3
		//(2x +3)(x^2 +2x)^4
		/*
		 * f(x) = (x)^3
		 * g(x)=x^2 + 2x
		 * f(g(x)) = (x^2 + 2x)^3
		 * Inte. f(g(x))dx = 1/g'(x) * Inte. f(g(x))g'(x)dx = 1/g'(x) * Inte. f(y) -> y=g(x)
		 * 
		 * 2(x^2 + 2x)^3
		 * 2/(2x + 2) * (2x+2)(x^2 + 2x)^3
		 * Integration by Parts.. 
		 */
		if(!this.hasVar(resp) || this.isConst())
			return new Term(this.getClone(),new Mirror(resp));
		if(pwr.isConst() || !pwr.hasVar(resp))
		{
			return null;
		}
		else
			return null;
	}
	public Operatable integrate(char resp,double k)throws IntegrationException
	{
		Function r=new Function();
		r.append(integrate(resp));
		r.append(new Constant(k));
		return r;
	}
	public Constant integrate(char resp,double i,double f)throws IntegrationException
	{
		Operatable o=this.integrate(resp);
		return new Constant(o.getVal(new char[] {resp},new double[] {f}) - o.getVal(new char[] {resp},new double[] {i}));
	}
	
	public Operatable add(Operatable o)
	{
            return new Function(new Operatable[] {this,o});
	}
	public Operatable sub(Operatable o)
	{
            return new Function(new Operatable[] {this,new Term(o,Constant.MINUS)});
	}
	public Operatable mul(Operatable o)
	{
            return new Term(this.getClone(),o,Constant.UNITY);
	}
	public Operatable divi(Operatable o)
	{
            return new Term(this.getClone(),new Reciprocal(o),Constant.UNITY);
	}
	public Operatable getClone()
	{
		/*X.sopln("Cloning: ");
		base.typeExpr("cyan");
		X.sop("\t");
		mul.typeExpr("cyan");
		X.sop("\t");
		pwr.typeExpr("cyan");*/
		if(this.isConst())
			return new Constant(mul.getVal(0)*Math.pow(base.getVal(0),pwr.getVal(0)));
		return new Term(base.getClone(),mul.getClone(),pwr.getClone());
	}
	public Operatable replace(char c,double d)
	{
		Term t= new Term(base.replace(c,d),mul.replace(c,d),pwr.replace(c,d));
		if(t.isConst())
			return new Constant(t.getVal(0));
		else
			return t;
	}
	public Constant getCoef(char[] c,double[] val)throws InconsistantCoefException
	{
		if(!pwr.isConst())
			throw new InconsistantCoefException();
		for(int i=0;i<c.length;i++)
		{
			if(this.getPwr(c[i])[0]!=val[i])
				return Constant.ZERO;
		}
		return new Constant(mul.getCoef());
	}
	public Constant getCoef(char c,double val)throws InconsistantCoefException
	{
		if(!pwr.isConst())
			throw new InconsistantCoefException();
		double[] mult=mul.getPwr(c);
		boolean b1=false;
		if( !mul.isConst())
			return Constant.ZERO;
		//X.sopln(base.getCoef(c,val/pwr.getVal(0)).doubleValue());
		if(base.getPwr(c)[0]*pwr.getVal(0)==val)
			return new Constant(mul.getVal(0)*Math.pow(base.getCoef(c,val/pwr.getVal(0)).doubleValue(),pwr.getVal(0)));
		else
			return Constant.ZERO;
		/*for(double d : mult)
		{
			if(d==val)
			{
				b1=true;
				break;
			}
		}
		double KO=pwr.getVal(0);
		if(b1)
		{
		}
		else
		{
			double[] bpwrs=base.getPwr(c);
			double cO=0;
			for(int i=0;i<bpwrs.length;i++)
			{
				if(bpwrs[i]*KO==val)
				{
					cO+=
				}
			}*/
		}
		
	public double[] getPwr(char c)throws InconsistantPowerException
	{
		if(pwr.isConst())
		{
			double[] res=base.getPwr(c);
			double[] res2=mul.getPwr(c);
			double[] res3=new double[res.length*res2.length];
			int K=0;
			double KO=pwr.getVal(0);
			for(int i=0;i<res.length;i++)
			{
				for(int j=0;j<res2.length;j++)
					res3[K++]=Math.pow(res[i],KO)+res2[j];
			}
			for(int i=0;i<res3.length;i++)
				res3[i]*=pwr.getVal(0);
			return res3;
		}
		else
			throw new InconsistantPowerException();
	}
}
