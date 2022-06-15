package maths.equations;
import maths.*;
import commons.*;
import java.io.*;

public class InEquation extends Equation implements Balanced
{
	public static final String[] ops=new String[] {">",">=","<","<="};
	public String cop;
	int opid;
	public InEquation()
	{
		equa="";
		eqn1=eqn2=new EquationUnit[0];
	}
	public InEquation(String eqan)
	{
		equa=eqan;
		equa=eqan=equa.replace("-x","-1x").replace("+ ","+").replace("+-"," -");
		guessCOP(equa);
		this.parseEqn(equa); //Overwrite
	}
	public InEquation(EquationUnit[] units1,EquationUnit[] units2,String op)
	{
		eqn1=units1;
		eqn2=units2;
		equa=representEqn(eqn1,eqn2);
		cop=op;
		setOPID();
	}
	public InEquation(InEquation ext)
	{
		this.assign(ext);
	}
	public void assign(InEquation ext)
	{
		this.eqn1=ext.eqn1;
		this.eqn2=ext.eqn2;
		this.equa=ext.equa;
		this.vars=ext.vars;
		this.vnames=ext.vnames;
	}
	public void guessCOP(String EQ)
	{
		for(String ope : ops)
		{
			if(EQ.indexOf(ope)!=-1)
				cop=ope;
		}
	}
	public void setOPID()
	{
		cop=cop.trim();
		for(int i=0;i<ops.length;i++)
		{
			if(cop.equals(ops[i]))
			{
				opid=i;
				return;
			}
		}
		opid=-1;
	}
	public static int getOPID(String Op)
	{
		Op=Op.trim();
		for(int i=0;i<ops.length;i++)
		{
			if(Op.equals(ops[i]))
				return i;
		}
		return -1;
	}
	public void parseEqn(String EQ)
	{
		EQ=EQ.trim();
		String str1,str2;
		int i1=EQ.indexOf(cop);
		str1=EQ.substring(0,i1).trim();
		str2=EQ.substring(i1+cop.length()).trim();
		int s=Strings.countChar(str1,' ');
		eqn1=new EquationUnit[s+1];
		for(int i=1;i<=s+1;i++)
			eqn1[i-1]=new EquationUnit(Strings.cut(str1,' ',i));
		s=Strings.countChar(str2,' ');
		eqn2=new EquationUnit[s+1];
		for(int i=1;i<=s+1;i++)
			eqn2[i-1]=new EquationUnit(Strings.cut(str2,' ',i));
		super.totalate();
		setOPID();
	}
	public static String representEqn(EquationUnit[] units1,EquationUnit[] units2,String cop)
	{
		String rep=units1[0].getUnit();
		for(int i=1;i<units1.length;i++)
			rep+=units1[i].getUnitWithSign(true);
		rep+=cop+" ";
		rep+=units2[0].getUnit();
		for(int i=1;i<units2.length;i++)
			rep+=units2[i].getUnitWithSign(true);
		if(Y.cut(rep,cop,1).trim().equals(""))
			return "0 "+cop+" "+Y.cut(rep,'=',2).trim().replace("+-"," -");
		else if(Y.cut(rep,cop,2).trim().equals(""))
			return Y.cut(rep,cop,1).trim()+" "+cop+" 0".replace("+-"," -");
		else
			return rep.trim().replace("+-"," -");
	}
	public static String representEqnHumanReadable(EquationUnit[] units1,EquationUnit[] units2,String cop)
	{
		String rep=units1[0].getUnit();
		for(int i=1;i<units1.length;i++)
			rep+=units1[i].getUnitWithSign(false);
		rep+=cop+" ";
		rep+=units2[0].getUnit();
		for(int i=1;i<units2.length;i++)
			rep+=units2[i].getUnitWithSign(false);
		if(Y.cut(rep,'=',1).trim().equals(""))
			return "0 "+cop+" "+Y.cut(rep,'=',2).trim().replace("+ -"," -").replace("+-"," -");
		else if(Y.cut(rep,'=',2).trim().equals(""))
			return Y.cut(rep,'=',1).trim()+" = 0".replace("+ -"," -").replace("+-"," -");
		else
			return rep.trim().replace("+ -"," -").replace("+-"," -");
	}
	public static boolean areSwappedOps(String op1,String op2)
	{
		return ((Strings.hasChar(op1,'>') && Strings.hasChar(op2,'<')) || (Strings.hasChar(op1,'<') && Strings.hasChar(op2,'>')));
	}
	public void printEqnSimplified(String col)
	{
		X.sopln((LHS.getUnitWhole()+cop+" "+RHS.getUnitWhole()).replace("+-"," -"));
	}
	public InEquationResult solveIneq(boolean s,String col)throws InvalidEqnForSolverException //s=steps
	{
		InEquationResult IEQR=new InEquationResult(this);
		if(s)
		{
			X.sopln("This solver shows steps following a certain algorithm. There may be other (and some certainly easier) methods to solve this inequation.","Red");
			X.sopln();
			printEqnSimplified(col);
		}
		if(!(Dp1==Dp2 && Dp2==1))
		{
			EquationUnit[] calc1=Calculator.cross(LHS,RHS);
			if(s)
			{
				X.sopln("\tBy Cross-multiplication: ",col);
				printEqn(calc1[0],calc1[1],col);
			}
			LHS=calc1[0]; RHS=calc1[1];
		}
		double toSub;
		boolean lhsx=LHSX();
		if(lhsx)
			toSub=RHS.xCoN;
		else
			toSub=LHS.xCoN;
		this.subV(toSub);
		if(s && toSub!=0)
		{
			if(toSub>0)
				X.sopln("\tSubtracting "+toSub+"x on both sides",col);
			else
				X.sopln("\tAdding "+Math.abs(toSub)+"x on both sides",col);
			this.printEqnSimplified(col);
		}
		if(lhsx)
			toSub=LHS.nCoN;
		else
			toSub=RHS.nCoN;
		this.sub(toSub);
		if(s && toSub!=0)
		{
			if(toSub>0)
				X.sopln("\tSubtracting "+toSub+" on both sides",col);
			else
				X.sopln("\tAdding "+Math.abs(toSub)+" on both sides",col);
			this.printEqnSimplified(col);
		}
		if(lhsx)
			toSub=LHS.xCoN; //Here To-divi
		else
			toSub=RHS.xCoN;
		this.divi(toSub);
		if(s && toSub!=1)
		{
			X.sopln("\tDividing both sides by "+toSub,col);
			this.printEqnSimplified(col);
			X.sop(Symbols.IPLS+"\t",col);
		}
		double ans;
		ans=(lhsx)?(RHS.nCoN)/RHS.nCoD:LHS.nCoN/LHS.nCoD;
		String FOP=(lhsx)?cop:switchOper(cop);
		if(s)
			X.sopln("x "+FOP+" "+ans);
			//Add to the InEq Result
		IEQR.setOper(FOP);
		IEQR.FVal=ans;
		return IEQR;
	}
	public InEquationResult solveIneq(boolean s)throws InvalidEqnForSolverException
	{
		return solveIneq(s,"white");
	}
	public InEquationResult solveIneq()throws InvalidEqnForSolverException
	{
		return solveIneq(false,"white");
	}
	public InEquationResult solveIneq(String col)throws InvalidEqnForSolverException
	{
		return solveIneq(true,col);
	}
	public void solveForX()throws InvalidEqnForSolverException
	{
		solveIneq(true,"white");
	}
	public void solveForX(String col)
	{
		solveIneq(true,col);
	}
	public static String switchOper(String oper)
	{
		return (Strings.hasChar(oper,'>'))?oper.replace('>','<'):oper.replace('<','>');
	}
}
class InvalidOperatorException extends RuntimeException
{
	InvalidOperatorException()
	{
		X.sopln("Invalid operator was found. Please check the input.","red");
	}
}
