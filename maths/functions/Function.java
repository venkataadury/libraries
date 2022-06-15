package maths.functions;
import maths.Maths;
import maths.matrices.Matrix;
import commons.X;
import commons.ArrayUtils;
import static commons.ArrayFx.join;
import static commons.ArrayFx.merge;
import upgrade.ArrayFx;
import static maths.functions.Term.selectiveAppend;

public class Function implements Func
{
	FuncBlock[] terms=new FuncBlock[0];
	public static final double MIN_RANGE=Math.pow(10,-4);
	public static final ArrayUtils<FuncBlock> AU=new ArrayUtils<FuncBlock>(FuncBlock.class);
	public static final Function ZERO=new Function(Constant.ZERO);
	public static final Function ONE=new Function(Constant.ONE);
	public static final Function MINUS_ONE=new Function(new Constant(-1));
	public static final Function IDENTITY=new Function(new Mirror());
	public static int accuracy=5;
	
	public Function()
	{
		terms=new FuncBlock[0];
	}
	public Function(FuncBlock b) {this(new FuncBlock[] {b});}
	public Function(FuncBlock[] fx)
	{
		terms=fx;
	}
	public Function(FuncBlock b1,FuncBlock b2) {this(new FuncBlock[] {b1,b2});}
	Function(Matrix m,char var)
	{
		double[][] mt=m.getMatrix();
		terms=new Term[mt.length];
		double V;
		for(int i=0;i<mt.length;i++)
		{
			V=Maths.round(mt[i][0],5);
			if(V==0)
				continue;
			if(i>0)
				terms[terms.length-i-1]=new Term(new Mirror('x'),new Constant(V),Constant.ONE,new Constant(i));
			else
				terms[terms.length-i-1]=new Constant(V);
		}
		terms=ArrayFx.removeAll(terms,null);
	}
	public Function(String in)
	{
		FunctionParser fp=new FunctionParser(in);
		this.assign(fp.getFunction());
	}
	
	private void assign(Function ext)
	{
		terms=ext.terms;
	}
	
	public double getVal(Number v)
	{
		double V=v.doubleValue();
		double r=0;
		for(FuncBlock f : terms)
			r+=f.getVal(V);
		return r;
	}
	public double getVal(char[] vars,double[] vals)throws VariableMismatchException
	{
		double r=0;
		for(FuncBlock f : terms)
			r+=f.getVal(vars,vals);
		return r;
	}
	public double getVal(java.util.HashMap<Character,? extends Number> hm)throws VariableMismatchException
	{
		double r=0;
		for(FuncBlock f : terms)
			r+=f.getVal(hm);
		return r;
	}
	public double getVal(char ch,double val,double def)
	{
		double r=0;
		for(FuncBlock f : terms)
			r+=f.getVal(ch,val,def);
		return r;
	}
	public char getVar()throws NoSingleVariableException
	{
		char[] vars=getVars();
		if(vars.length>1)
			throw new NoSingleVariableException(vars);
		if(vars.length==0)
			return (char)0;
		return vars[0];
	}
	public char[] getVars()
	{
		char[] ret=new char[0];
		for(FuncBlock f : terms)
			ret=selectiveAppend(ret,f.getVars());
		return ret;
	}
	public void setVar(char c)
	{
		for(FuncBlock f : terms)
			f.setVar(c);
	}
	public void setVar(char c1,char c2)
	{
		for(FuncBlock f : terms)
			f.setVar(c1,c2);
	}
	
	public boolean isMultiTermed()
	{
		if(terms.length>1)
			return true;
		return terms[0].isMultiTermed();
	}
	public boolean isConstant()
	{
		for(FuncBlock f : terms)
		{
			if(!f.isConstant())
				return false;
		}
		return true;
	}
	public boolean hasVar(char v)
	{
		for(FuncBlock f : terms)
		{
			if(f.hasVar(v))
				return true;
		}
		return false;
	}
	public String toString()
	{
		String ret="";
		for(FuncBlock f : terms)
			ret=selectedAppend(ret,f.toString());
		ret=ret.trim();
		if(ret.length()==0)
			return ret;
		if(ret.charAt(0)=='-')
			return ret;
		return ret.substring(1);
	}
	public void put(double V,String fN)
	{
		X.sop(fN.trim()+"("+Maths.perfect(V)+") = ","yellow");
		X.sopln(Maths.round(this.getVal(V),accuracy),"red");
	}
	public void put(double v)
	{
		put(v,"F");
	}
	public void printFx(String fN) {printFx(fN,getVars());}
	public void printFx() {printFx("F");}
	public void printFx(char[] vars) {printFx("F",vars);}
	public void printFx(String fN,char[] vars)
	{
		X.sop(fN.trim()+"("+join(vars,',')+") = ","yellow");
		X.sopln(this,"red");
	}
	public void reduce()
	{
		for(int i=0;i<terms.length;i++)
		{
			terms[i].reduce();
			if(terms[i].isConstant())
				terms[i]=new Constant(terms[i].getVal(1));
		}
		for(int i=0;i<terms.length;i++)
		{
			if(terms[i].isConstant() && terms[i].getVal(1)==0)
			{
				dropTerm(i);
				i--;
			}
		}
		if(terms.length==0)
			terms=new FuncBlock[] {new Constant(0)};
	}
	public Function addTerm(FuncBlock fb)
	{
		terms=AU.append(terms,fb);
		return this;
	}
	public void dropTerm(int ind)
	{
		terms=ArrayFx.remove(terms,ind);
	}
	public Function negate()
	{
		Function ret=new Function(terms);
		for(int i=0;i<ret.terms.length;i++)
			ret.terms[i]=ret.terms[i].negate();
		return ret;
	}
	public Term MUL(double d)
	{
		return new Term(this,new Constant(d));
	}
	public Term DIVI(double d) {return MUL(1/d);}
	public Function differentiate(char ch)throws NonDifferentiableException
	{
		Function f=new Function(terms);
		for(int i=0;i<f.terms.length;i++)
			f.terms[i]=f.terms[i].differentiate(ch);
		return f;
	}
	public Function clone()
	{
		Function ret=new Function();
		ret.terms=new FuncBlock[terms.length];
		for(int i=0;i<terms.length;i++)
			ret.terms[i]=terms[i].clone();
		return ret;
	}
	
	
	/** Solving the function for roots (Binary Search Method)**/
	public double getVal(char[] vars,double[] vals,char var,double val)
	{
		vars=ArrayFx.append(vars,var);
		vals=ArrayFx.append(vals,val);
		return getVal(vars,vals);
	}
	public double getSingleRoot(char[] vars,double[] vals,char vr,double min,double max,int dec)throws NoRootsException
	{
		if(min>max)
		{
			double t=min;
			min=max;
			max=t;
		}
		if(min==max)
		{
			if(isRoot(vars,vals,vr,min,dec))
				return min;
			else
				throw new NoRootsException();
		}
		return findRoot(vars,vals,vr,min,max,dec);
	}
	
	private double findRoot(char[] vars,double[] vals,char var,double min,double max,int dec)throws NoRootsException
	{
		if(min>max)
			throw new NoRootsException();
		double err=Math.pow(10,-dec);
		if(isRoot(vars,vals,var,min,dec))
			return min;
		if(isRoot(vars,vals,var,max,dec))
			return max;
		if((max-min)<=MIN_RANGE)
			throw new NoRootsException();
		double minV=getVal(vars,vals,var,min);
		double maxV=getVal(vars,vals,var,max);
		double mid=(min+max)/2;
		int s1=Maths.sgn(minV),s2=Maths.sgn(maxV);
		if(isRoot(vars,vals,var,mid,dec))
			return mid;
		if(s1==s2)
		{
			try {return findRoot(vars,vals,var,min,mid,dec);}
			catch(NoRootsException e) {return findRoot(vars,vals,var,mid,max,dec);}
		}
		else
		{
			double midV=getVal(vars,vals,var,mid);
			int s3=Maths.sgn(midV);
			if(s3==s1)
				return findRoot(vars,vals,var,mid,max,dec);
			else
				return findRoot(vars,vals,var,min,mid,dec);
		}
	}
	
	public double[] getAllRoots(char[] vars,double[] vals,char var,double min,double max,int dec)
	{
		double err=Math.pow(10,-dec);
		double[] roots=new double[] {findRoot(vars,vals,var,min,max,dec)};
		double lR=roots[roots.length-1];
		double[] r1=new double[0],r2=new double[0];
		X.sopln(lR+"\t"+min+","+max);
		try {r1=getAllRoots(vars,vals,var,min,lR-MIN_RANGE,dec);} catch(NoRootsException e) {}
		try {r2=getAllRoots(vars,vals,var,lR+MIN_RANGE,max,dec);} catch(NoRootsException e) {}
		return merge(merge(roots,r1),r2);
	}
	private boolean isRoot(char[] vars,double[] vals,char var,double val,int dec)  //Slight Error (Comparison to error done on y rather than x)
	{
		double err=Math.pow(10,-dec);
		return Math.abs(getVal(vars,vals,var,val))<err; // d(y)<err *Should be d(x) for perfection*
	}
	public void append(FuncBlock fb) {terms=ArrayFx.append(terms,fb);}
	public Function partialPut(char[] ch,FuncBlock[] fa)
	{
		Function r=new Function();
		for(FuncBlock f : terms)
			r.append(f.partialPut(ch,fa));
		return r;
	}
	/** Solving Complete **/
	
	public Constant getConstant() {return new Constant(getCoeff(getVars(),new int[getVars().length]).getVal(0));}
	public Function getCoeff(char var,int pwr)
	{
		Function cf=new Function();
		for(FuncBlock fb : terms)
			cf.append(fb.getCoeff(var,pwr));
		return cf;
	}
	
	//Calculus
	public Function getDifferentiation(char resp,char[] otV,FuncBlock[] ndvs)throws DifferentiationException
	{
		Function ret=new Function();
		for(FuncBlock f : terms)
		{
			if(f.hasCalculus())
				ret.append(((Calculus)f).getDifferentiation(resp,otV,ndvs));
			else
				throw new DifferentiationException("Some terms of the function are not differentiable");
		}
		return ret;
	}
	public Function getIntegration(char resp,double con)throws IntegrationException
	{
		Function ret=new Function();
		for(FuncBlock fb : terms)
		{
			if(fb.hasCalculus())
				ret.append(((Calculus)fb).getIntegration(resp));
			else
				throw new IntegrationException("Some terms of the function are not differentiable");
		}
		return ret;
	}
	
	
	
	
	/*public Function getCoeff(char[] vars,int[] pwrs)
	{
		Function f=this;
		if(pwrs.length<vars.length)
			throw new RuntimeException("powers and variables in input are not equal in number");
		for(int i=0;i<vars.length;i++)
			f=f.getCoeff(vars[i],pwrs[i]);
		return f;
	}*/
	/* Static Methods */
	public static void setAccuracy(int I) {accuracy=I;}
	public static final String selectedAppend(String s1,String s2)
	{
		s2=s2.trim();
		if(s2==null || s1==null)
			return s1;
		if(s2.charAt(0)=='-' || s2.charAt(0)=='+')
			return s1.trim()+" "+s2;
		else
			return s1.trim()+" +"+s2.trim();
	}
	/*public static double[] join(double[] d1,double[] d2)
	{
		double[] ret=new double[d1.length+d2.length];
		
	}*/
}

class NoRootsException extends RuntimeException
{
	public NoRootsException()
	{
	}
}
