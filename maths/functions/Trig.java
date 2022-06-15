package maths.functions;
import maths.Maths;

public class Trig implements Calculus
{
	int ID;
	FuncBlock R=null;
	boolean inv=false;
	
	//ID: 0-sin,1-cos,2-tan,3-cot,4-sec,5-cosec
	
	public Trig() {R=new Mirror();ID=-1;}
	public Trig(int id) {R=new Mirror();ID=id;}
	public Trig(int id,FuncBlock f) {ID=id; R=f;}
	
	public Function getCoeff(char ch,int pwr) {return new Function(Constant.ZERO);}
	public double getVal(Number V)
	{
		double val=R.getVal(V.doubleValue());
		return processByID(val);
	}
	protected double processByID(double n)
	{
		switch(ID)
		{
			case 0:
				if(!inv)
					return Maths.sin(n);
				else
					return Maths.asin(n);
			case 1:
				if(!inv)
					return Maths.cos(n);
				else
					return Maths.acos(n);
			case 2:
				if(!inv)
					return Maths.tan(n);
				else
					return Maths.atan(n);
			case 3:
				if(!inv)
					return Maths.cot(n);
				else
					return Maths.acot(n);
			case 4:
				if(!inv)
					return Maths.sec(n);
				else
					return Maths.asec(n);
			case 5:
				if(!inv)
					return Maths.cosec(n);
				else
					return Maths.acosec(n);
			default:
				return 0;
		}
	}

	public void setInverse(boolean b) {inv=b;}
	public void setID(int i) {ID=i;}
	public double getVal(char[] vars,double[] vals)throws VariableMismatchException
	{
		double val=R.getVal(vars,vals);
		return processByID(val);
	}
	public double getVal(java.util.HashMap<Character,? extends Number> hm)throws VariableMismatchException
	{
		double val=getVal(hm);
		return processByID(val);
	}
	public double getVal(char ch,double val,double def)
	{
		double valu=R.getVal(ch,val,def);
		return processByID(valu);
	}
	public char getVar()throws NoSingleVariableException
	{
		return R.getVar();
	}
	public char[] getVars()
	{
		return R.getVars();
	}
	public void setVar(char c)
	{
		R.setVar(c);
	}
	public void setVar(char c1,char c2)
	{
		R.setVar(c1,c2);
	}
	public boolean isMultiTermed(){return false;}
	public boolean isConstant() {return R.isConstant();}
	public boolean hasVar(char ch) {return R.hasVar(ch);}
	public String toString()
	{
		String r="";
		switch(ID)
		{
			case 0:
				r=(inv)?"asin":"sin";
				break;
			case 1:
				r=(inv)?"acos":"cos";
				break;
			case 2:
				r=(inv)?"atan":"tan";
				break;
			case 3:
				r=(inv)?"acot":"cot";
				break;
			case 4:
				r=(inv)?"asec":"sec";
				break;
			case 5:
				r=(inv)?"acosec":"cosec";
				break;
			default:
				return "<Unidentified Trig Function, ID: "+ID+">";
		}
		return r+"["+R.toString()+"]";
	}
	public void reduce() {}
	public FuncBlock negate()
	{
		return new Term(this,new Constant(-1));
	}
	public Term MUL(double d)
	{
		return new Term(this,new Constant(d));
	}
	public Term DIVI(double d) {return MUL(1/d);}
	
	public Trig clone()
	{
		return new Trig(ID,R.clone());
	}
	public Trig partialPut(char[] ch,FuncBlock[] vals) {return new Trig(ID,R.partialPut(ch,vals));}
	public FuncBlock differentiate(char ch)throws NonDifferentiableException
	{
		switch(ID)
		{
			case 0:
				if(!inv)
					return new Term(new Trig(1,R),R.differentiate(ch));
				else
				{
					Term sT=new Term(R,new Constant(-1),Constant.ONE,new Constant(2));
					Function bF=new Function(new Constant(1),sT);
					Term rT=new Term(Constant.ONE,R.differentiate(ch),new Term(bF,Constant.ONE,Constant.ONE,new Constant(0.5)),Constant.ONE);
					return rT;
				}
			case 1:
				if(!inv)
					return new Term(new Trig(0,R),R.differentiate(ch).negate());
				else
				{
					Term sT=new Term(R,new Constant(-1),Constant.ONE,new Constant(2));
					Function bF=new Function(new Constant(1),sT);
					Term rT=new Term(new Constant(-1),R.differentiate(ch),new Term(bF,Constant.ONE,Constant.ONE,new Constant(0.5)),Constant.ONE);
					return rT;
				}
			case 2:
				if(!inv)
					return new Term(new Trig(4,R),R.differentiate(ch),Constant.ONE,new Constant(2));
				else
				{
					Term sT=new Term(R,Constant.ONE,Constant.ONE,new Constant(2));
					Function bF=new Function(new Constant(1),sT);
					Term rT=new Term(Constant.ONE,R.differentiate(ch),bF,Constant.ONE);
					return rT;
				}
			case 3:
				if(!inv)
					return new Term(new Trig(5,R),R.differentiate(ch).negate(),Constant.ONE,new Constant(2));
				else
				{
					Term sT=new Term(R,Constant.ONE,Constant.ONE,new Constant(2));
					Function bF=new Function(new Constant(1),sT);
					Term rT=new Term(new Constant(-1),R.differentiate(ch),bF,Constant.ONE);
					return rT;
				}
			case 4:
				if(!inv)
					return new Term(new Trig(4,R),new Term(new Trig(2,R),R.differentiate(ch)));
				else
				{
					Term sT=new Term(R,Constant.ONE,Constant.ONE,new Constant(4));
					Term tT=new Term(R,new Constant(-1),Constant.ONE,new Constant(2));
					Function sF=new Function(sT,tT);
					Term rT=new Term(Constant.ONE,R.differentiate(ch),new Term(sF,Constant.ONE,Constant.ONE,new Constant(0.5)));
					return rT;
				}
			case 5:
				if(!inv)
					return new Term(new Trig(5,R),new Term(new Trig(3,R),R.differentiate(ch).negate()));
				else
				{
					Term sT=new Term(R,Constant.ONE,Constant.ONE,new Constant(4));
					Term tT=new Term(R,new Constant(-1),Constant.ONE,new Constant(2));
					Function sF=new Function(sT,tT);
					Term rT=new Term(new Constant(-1),R.differentiate(ch),new Term(sF,Constant.ONE,Constant.ONE,new Constant(0.5)));
					return rT;
				}
			default:
				return new Constant(0);
		}
	}
	
	//Calculus
	public Calculus getDifferentiation(char resp,char[] otV,FuncBlock[] arr)
	{
		Calculus der=((Calculus)R).getDifferentiation(resp,otV,arr);
		switch(ID)
		{
			case 0:
				if(!inv)
					return new Term(new Trig(1,R.clone()),der);
				else
				{
					Function f1=new Function(new Constant(1));
					f1.append(R.negate().mul(R));
					return new Term(der,new Constant(1),f1,new Constant(0.5));
				}
			case 1:
				if(!inv)
					return new Term(new Trig(0,R.clone()),der);
				else
				{
					Function f1=new Function(new Constant(1));
					f1.append(R.negate().mul(R));
					return new Term(der,new Constant(-1),f1,new Constant(0.5));
				}
			case 2:
				if(!inv)
					return (new Term(new Trig(4,R.clone()),der,new Constant(1),new Constant(2)));
				else
				{
					Function f1=new Function(new Constant(1));
					f1.append(R.mul(R));
					return new Term(der,new Constant(1),f1);
				}
			case 3:
				if(!inv)
					return (new Term(new Trig(5,R.clone()),der,new Constant(-1),new Constant(2)));
				else
				{
					Function f1=new Function(new Constant(1));
					f1.append(R.mul(R));
					return new Term(der,new Constant(-1),f1);
				}
			case 4:
				if(!inv)
					return (new Term(this.clone(),new Trig(2,R.clone()))).mul(der);
				else
				{
					Function f1=new Function(R.clone());
					Function f2=new Function(R.mul(R)); f2.append(new Constant(-1));
					Term f3=f1.mul(new Term(f2,Constant.ONE,Constant.ONE,new Constant(0.5)));
					return new Term(der,new Constant(1),f3);
				}
			case 5:
				if(!inv)
					return (new Term(this.clone(),new Trig(3,R.clone()),new Constant(-1))).mul(der);
				else
				{
					Function f1=new Function(R.clone());
					Function f2=new Function(R.mul(R)); f2.append(new Constant(-1));
					Term f3=f1.mul(new Term(f2,Constant.ONE,Constant.ONE,new Constant(0.5)));
					return new Term(der,new Constant(-1),f3);
				}
			default:
				return new Constant(0);
		}
	}
}
