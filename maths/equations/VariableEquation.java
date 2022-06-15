package maths.equations;
import commons.*;
import maths.*;
import java.io.IOException;
import java.util.HashMap;

public class VariableEquation //implements Balanced
{
	VEUnit[] LHS,RHS;
	VEUnit LHSF,RHSF;
	int nVar;
	public static int totNVar=0;
	static char[] vars=new char[0];
	protected static VariableEquation[] pool=new VariableEquation[0],pool2;
	protected static HashMap<Character,Double> varVals=new HashMap<Character,Double>();
	String inpEqn;
	
	public VariableEquation() {}
	public VariableEquation(String eqn)
	{
		parseEqn(eqn.replace("+ ","+").replace("- ","-").replace("+-","-"));
	}
	public VariableEquation(VariableEquation ext)
	{
		this.assign(ext);
	}
	
	public void assign(VariableEquation ext)
	{
		nVar=ext.nVar;
		vars=ext.vars;
		LHS=ext.LHS; RHS=ext.RHS;
	}
	
	private void parseEqn(String eq)
	{
		inpEqn=eq.trim();
		String LHSQ=Y.cut(inpEqn,'=',1).trim();
		String RHSQ=Y.cut(inpEqn,'=',2).trim();
		int spc1,spc2;
		spc1=X.countchar(LHSQ,' ');
		LHS=new VEUnit[spc1+1];
		for(int i=0;i<LHS.length;i++)
			LHS[i]=new VEUnit(Y.cut(LHSQ,' ',i+1));
		
		spc2=X.countchar(RHSQ,' ');
		RHS=new VEUnit[spc2+1];
		for(int i=0;i<RHS.length;i++)
			RHS[i]=new VEUnit(Y.cut(RHSQ,' ',i+1));
		
		nVar=0;
		for(char c : eq.toCharArray())
		{
			if(Character.isLetter(c))
			{
				ArrayFx.append(vars,c);
				nVar++;
			}
		}
		
		this.setVarData();
		this.addToPool(this);
	}
	public void typeEqn(String col)
	{
		LHS[0].typeUnit(col,false);
		for(int i=1;i<LHS.length;i++)
			LHS[i].typeUnit(col,true);
		X.sop(" = ",col);
		RHS[0].typeUnit(col,false);
		for(int i=1;i<RHS.length;i++)
			RHS[i].typeUnit(col,true);
	}
	public void setVarData()
	{
		String vrs=new String(vars);
		char[] ex;
		for(int i=0;i<LHS.length;i++)
		{
			ex=LHS[i].getVars();
			for(char ch : ex)
			{
				if(X.hasChar(vrs,ch))
				{
					vrs+=ch;
					totNVar++;
				}
			}
		}
		for(int i=0;i<RHS.length;i++)
		{
			ex=RHS[i].getVars();
			for(char ch : ex)
			{
				if(X.hasChar(vrs,ch))
				{
					vrs+=ch;
					totNVar++;
				}
			}
		}
		vars=vrs.toCharArray();
	}
	public void printEqn()
	{
		printEqn("white");
	}
	public void printEqn(String col)
	{
		typeEqn(col);
		X.sopln();
	}
	public void typeEqn()
	{
		typeEqn("white");
	}
	
	public int findVarCoef(char va, boolean lor) //lor = lhs or rhs
	{
		return 0;
	}
	public void simplify()
	{
		calcFs();
		LHSF.mul(RHSF.getDen());
		RHSF.mul(LHSF.getDen());
		RHSF.setDen(1);
		LHSF.setDen(1);
		LHSF.sumUp();
		RHSF.sumUp();
	}
	public void updateEqn()
	{
		simplify();
		LHS=new VEUnit[] {LHSF};
		RHS=new VEUnit[] {RHSF};
	}
	protected void calcFs()
	{
		LHSF=LHS[0];
		RHSF=RHS[0];
		
		for(int i=1;i<LHS.length;i++)
			LHSF.add(LHS[i]);
		for(int i=1;i<RHS.length;i++)
			RHSF.add(RHS[i]);
	}
	
	
	public void mul(double v)
	{
		updateEqn();
		LHSF.mul(v);
		RHSF.mul(v);
	}
	public void divi(double v)
	{
		updateEqn();
		LHSF.divi(v);
		RHSF.divi(v);
	}
	//Pools
	private static void addToPool(VariableEquation veq)
	{
		VariableEquation[] vea=new VariableEquation[pool.length+1];
		for(int i=0;i<pool.length;i++)
			vea[i]=pool[i];
		vea[pool.length]=veq;
		pool=vea;
	}
	public static void printPoolEqns(VariableEquation[] pl,String col)
	{
		for(VariableEquation veq : pl)
			veq.printEqn(col);
	}
	public static void printPoolEqns(String col)
	{
		printPoolEqns(pool,col);
	}
	public static void printPoolEqns(VariableEquation[] pl)
	{
		printPoolEqns(pl,"white");
	}
	public static void printPoolEqns()
	{
		printPoolEqns("white");
	}
	public static void resetPool()
	{
		pool=new VariableEquation[0];
		totNVar=0;
		vars=new char[0];
	}
	
	public void fixToZero()
	{
		this.updateEqn();
		RHSF.mul(-1);
		VEUnit veu=LHSF;
		LHS=new VEUnit[2];
		LHS[0]=veu;
		LHS[1]=RHSF;
		RHS=new VEUnit[] {new VEUnit("0")};
		RHSF=RHS[0];
		this.updateEqn();
	}
	
	public static void solveAllEqns(String col,boolean s)throws EquationShortageException
	{
		int eqn;
		pool2=pool;
		VariableEquation[] pool3=new VariableEquation[pool2.length];
		for(int i=0;i<pool3.length;i++)
		{
			eqn=getMinVarEqn(pool2);
			pool3[i]=pool2[eqn];
			pool2=popEqn(pool2,eqn);
		}
		
		for(VariableEquation ve : pool3)
			ve.fixToZero();
		pool=pool3;
		if(s)
		{
			printPoolEqns(pool3,col);
			X.sopln();
		}
		if(totNVar>pool.length)
			throw new EquationShortageException();
		
	}
	public int getVarCount()
	{
		return nVar;
	}
	public static VariableEquation[] popEqn(VariableEquation[] in,int ind)
	{
		if(in.length==1 || ind==-1)
			return new VariableEquation[0];
		VariableEquation[] res=new VariableEquation[in.length-1];
		for(int i=0;i<ind;i++)
			res[i]=in[i];
		for(int i=in.length-1;i>ind;i--)
			res[i-1]=in[i];
		return res;
	}
	public static int getMinVarEqn(VariableEquation[] vep)
	{
		if(vep.length<=0)
			return -1;
		int min=vep[0].getVarCount();
		int ind=0;
		int c;
		for(int i=0;i<vep.length;i++)
		{
			c=vep[i].getVarCount();
			if(c<min)
			{
				min=c;
				ind=i;
			}
		}
		return ind;
	}
}
class EquationShortageException extends RuntimeException
{
	EquationShortageException()
	{
		X.sopln("There is a shortage in the number of equations.","red");
	}
}
