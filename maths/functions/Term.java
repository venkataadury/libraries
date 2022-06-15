package maths.functions;
import maths.Maths;
import commons.X;
import commons.ArrayFx;

public class Term implements Calculus
{
	FuncBlock base=new Mirror(),mul=Constant.ONE,divi=Constant.ONE,pwr=Constant.ONE;
	
	public Term() {}
	public Term(FuncBlock bs) {base=bs;}
	public Term(FuncBlock bs,FuncBlock mu) {base=bs;mul=mu;}
	public Term(FuncBlock bs,FuncBlock mu,FuncBlock di) {this(bs,mu);divi=di;}
	public Term(FuncBlock bs,FuncBlock mu,FuncBlock di,FuncBlock pr) {this(bs,mu,di);pwr=pr;}
	
	
	public void setBase(FuncBlock fb) {base=fb;}
	public void setDivi(double d) {divi=new Constant(d);}
	public void setDivi(FuncBlock f) {divi=f;}
	public void setMul(double m) {mul=new Constant(m);}
	public void setMul(FuncBlock f) {mul=f;}
	public void setPower(double pr) {pwr=new Constant(pr);}
	public void setPower(FuncBlock f) {pwr=f;}
	public double getVal(Number v)
	{
		double V=v.doubleValue();
		double B=base.getVal(V);
		double M=mul.getVal(V);
		double D=divi.getVal(V);
		double P=pwr.getVal(V);
		return valueOf(B,M,D,P);
	}
	public double getVal(char[] vars,double[] vals)throws VariableMismatchException
	{
		double B=base.getVal(vars,vals);
		double M=mul.getVal(vars,vals);
		double D=divi.getVal(vars,vals);
		double P=pwr.getVal(vars,vals);
		return valueOf(B,M,D,P);
	}
	public double getVal(java.util.HashMap<Character,? extends Number> hm)throws VariableMismatchException
	{
		double B=base.getVal(hm);
		double M=mul.getVal(hm);
		double D=divi.getVal(hm);
		double P=pwr.getVal(hm);
		return valueOf(B,M,D,P);
	}
	public char getVar()throws NoSingleVariableException
	{
		char[] vrs=getVars();
		if(vrs.length>1)
			throw new NoSingleVariableException(vrs);
		return vrs[0];
	}
	public char[] getVars()
	{
		char[] vs=new char[0];
		vs=selectiveAppend(vs,base.getVars());
		vs=selectiveAppend(vs,mul.getVars());
		vs=selectiveAppend(vs,divi.getVars());
		vs=selectiveAppend(vs,pwr.getVars());
		return vs;
	}
	public void setVar(char ch)
	{
		base.setVar(ch);
		mul.setVar(ch);
		divi.setVar(ch);
		pwr.setVar(ch);
	}
	public void setVar(char v1,char v2)
	{
		base.setVar(v1,v2);
		mul.setVar(v1,v2);
		divi.setVar(v1,v2);
		pwr.setVar(v1,v2);
	}
	public boolean isMultiTermed()
	{
		return false;
	}
	public boolean isConstant()
	{
		return (mul.isConstant() && base.isConstant() && divi.isConstant() && pwr.isConstant());
	}
	public Term negate()
	{
		return new Term(base,mul.negate(),divi,pwr);
	}
	public boolean hasVar(char ch)
	{
		char[] aVs=getVars();
		for(char c : aVs)
		{
			if(ch==c)
				return true;
		}
		return false;
	}
	public FuncBlock differentiate(char ch)throws NonDifferentiableException
	{
		if(!hasVar(ch))
			return new Constant(0);
		if(!pwr.isConstant())
		{
			//TODO: Edit
			throw new NonDifferentiableException("Power Variable in 'Term' class");
		}
		Term t1,t2;
		Function ret=new Function();
		if(divi.isConstant())
		{
			t1=new Term(base,new Term(base.differentiate(ch),mul.MUL(pwr.getVal(1))),Constant.ONE,new Constant(pwr.getVal(1)-1));
			ret.addTerm(t1);
			t2=new Term(base,mul.differentiate(ch),Constant.ONE,new Constant(pwr.getVal(1)));
			ret.addTerm(t2);
			return ret.DIVI(divi.getVal(1));
		}
		else
		{
			Term dsq=new Term(divi,Constant.ONE,Constant.ONE,new Constant(2));
			Term num=new Term(base,mul,Constant.ONE,new Constant(pwr.getVal(1)));
			
			t1=new Term(num.differentiate(ch),divi,dsq,Constant.ONE);
			ret.addTerm(t1);
			t2=new Term(num,divi.differentiate(ch).negate(),dsq,Constant.ONE);
			ret.addTerm(t2);
			return ret;
		}
	}
	
	@Override
	public String toString()
	{
		String bT=getFormString(base);
		String mT=getFormString(mul);
		String dT=getFormString(divi);
		String pT=getFormString(pwr);
		if(pT.equals("1"))
			pT="";
		if(dT.equals("1"))
			dT="";
		if(mT.equals("1"))
			mT="";
		String r=bT;
		if(mT.length()>0)
			r=mT+r;
		if(pT.length()>0)
			r+="^"+pT;
		if(dT.length()>0)
			r+="/"+dT;
		return r;
	}
	private void setZero()
	{
		base=new Constant(0);
		mul=new Constant(1);
		pwr=new Constant(1);
		divi=new Constant(1);
	}
	public void reduce() //TODO: Modify
	{
		base.reduce();mul.reduce();divi.reduce();pwr.reduce();
		if(base.isConstant())
		{
			base=new Constant(base.getVal(1));
			if(base.getVal(1)==0)
				setZero();
		}
		if(mul.isConstant())
		{
			mul=new Constant(mul.getVal(1));
			if(mul.getVal(1)==0)
				setZero();
		}
		if(divi.isConstant())
			divi=new Constant(divi.getVal(1));
		if(pwr.isConstant())
		{
			if(pwr.getVal(1)==0)
			{base=new Constant(1);pwr=new Constant(1); this.reduce();}
			pwr=new Constant(pwr.getVal(1));
		}
	}
	/*public void mul(FuncBlock fb)
	{
		if(mul==null)
		{
			mul=fb;
			return;
		}
		mul=new Term(mul,fb);
		//this.reduce();
	}
	public void divi(FuncBlock fb)
	{
		if(divi==null)
		{
			divi=fb;
			return;
		}
		divi=new Term(divi,fb);
		//this.reduce();
	}*/
	
	public Term MUL(double v) {return new Term(base,mul.MUL(v),divi,pwr);}
	public Term DIVI(double v) {return new Term(base,mul,divi.MUL(v),pwr);}
	public Term getMultiplierTerm() {return new Term(new Constant(1),mul.clone(),divi.clone());}
	public Term clone() {return new Term(base.clone(),mul.clone(),divi.clone(),pwr.clone());}
	public Term partialPut(char[] ch,FuncBlock[] fa) {return new Term(base.partialPut(ch,fa),mul.partialPut(ch,fa),divi.partialPut(ch,fa),pwr.partialPut(ch,fa));}
	public Function getCoeff(char var,int pwr)//TODO: Change after defining derivatives.
	{
		Function co=new Function();
		double DX=1e-12;
		double v1,v2;
		int pw;
		int acc=Function.accuracy;
		Function.accuracy=16;
		FuncBlock f=this;
		if(!f.hasVar(var))
		{
			if(pwr==0)
				return new Function(this.clone());
			else
				return new Function(Constant.ZERO);
		}
		v1=f.getVal(1);
		v2=f.getVal(var,2,1)/v1;
		v2=Maths.log(v2,2);
		/*if(Math.abs(Math.round(v2)-v2)>=1e-10)
			continue;*/
		pw=(int)Math.round(v2);
		if(pw==pwr)
			co.append(f.partialPut(var,Constant.ONE));
		Function.accuracy=acc;
		return co;
	}
	
	public double getVal(char var,double val,double def)
	{
		double B=base.getVal(var,val,def);
		double M=mul.getVal(var,val,def);
		double D=divi.getVal(var,val,def);
		double P=pwr.getVal(var,val,def);
		return valueOf(B,M,D,P);
	}
	
	//Calculus
	public Calculus getDifferentiation(char resp,char[] otV,FuncBlock[] odvs)
	{
		if(!pwr.isConstant())
			X.sopln("Warning. Power may not be a constant. However for the operation of differentiation, it will be considered so.","red");
	}
	
	//Static Methods
	public static final String getFormString(FuncBlock fb)
	{
		String r="",s="";
		if(fb.isMultiTermed())
		{
			r="(";
			s=")";
		}
		return r+fb.toString().trim()+s;
	}
	public static final double valueOf(double b,double m,double d,double p)
	{
		return Math.pow(b,p)*(m/d);
	}
	public static final char[] selectiveAppend(char[] ia,char[] na)
	{
		char[] aa=ia;
		for(char ch : na)
		{
			if(ch==(char)0)
				continue;
			if(ArrayFx.Lsearch(aa,ch)[0]==-1)
				aa=append(aa,ch);
		}
		return aa;
	}
	public static final char[] append(char[] cha,char c)
	{
		char[] ca=new char[cha.length+1];
		for(int i=0;i<cha.length;i++)
			ca[i]=cha[i];
		ca[cha.length]=c;
		return ca;
	}
}
