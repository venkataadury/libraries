package maths.equations;
import commons.*;
import java.io.*;
import maths.*;

public class Equation implements Balanced
{
	protected EquationUnit[] eqn1,eqn2;
	public String equa;
	public int vars=1; //Single Variable
	public String vnames="x";
	public double Np1X,Np1N,Np2X,Np2N,Dp1,Dp2;
	public EquationUnit LHS,RHS;
	public Equation()
	{
		equa="";
		eqn1=eqn2=new EquationUnit[0];
	}
	public Equation(String eqan)
	{
		equa=eqan;
		parseEqn(equa);
	}
	public Equation(EquationUnit[] units1,EquationUnit[] units2)
	{
		eqn1=units1;
		eqn2=units2;
		equa=representEqn(eqn1,eqn2);
	}
	public Equation(Equation ext)
	{
		this.assign(ext);
	}
	
	public void assign(Equation ext)
	{
		this.eqn1=ext.eqn1;
		this.eqn2=ext.eqn2;
		this.equa=ext.equa;
		this.vars=ext.vars;
		this.vnames=ext.vnames;
	}
	public static String representEqn(EquationUnit[] units1,EquationUnit[] units2)
	{
		String rep=units1[0].getUnit();
		for(int i=1;i<units1.length;i++)
			rep+=units1[i].getUnitWithSign(true);
		rep+="= ";
		rep+=units2[0].getUnit();
		for(int i=1;i<units2.length;i++)
			rep+=units2[i].getUnitWithSign(true);
		if(Y.cut(rep,'=',1).trim().equals(""))
			return "0 = "+Y.cut(rep,'=',2).trim().replace("+-"," -");
		else if(Y.cut(rep,'=',2).trim().equals(""))
			return Y.cut(rep,'=',1).trim()+" = 0".replace("+-"," -");
		else
			return rep.trim().replace("+-"," -");
	}
	public static String representEqnHumanReadable(EquationUnit[] units1,EquationUnit[] units2)
	{
		String rep=units1[0].getUnit();
		for(int i=1;i<units1.length;i++)
			rep+=units1[i].getUnitWithSign(false);
		rep+="= ";
		rep+=units2[0].getUnit();
		for(int i=1;i<units2.length;i++)
			rep+=units2[i].getUnitWithSign(false);
		if(Y.cut(rep,'=',1).trim().equals(""))
			return "0 = "+Y.cut(rep,'=',2).trim().replace("+ -"," -").replace("+-"," -");
		else if(Y.cut(rep,'=',2).trim().equals(""))
			return Y.cut(rep,'=',1).trim()+" = 0".replace("+ -"," -").replace("+-"," -");
		else
			return rep.trim().replace("+ -"," -").replace("+-"," -");
	}
	public void parseEqn(String st)
	{
		String str1,str2;
		str1=Y.cut(st,'=',1).trim();
		str2=Y.cut(st,'=',2).trim();
		int s=Strings.countChar(str1,' ');
		eqn1=new EquationUnit[s+1];
		for(int i=1;i<=s+1;i++)
			eqn1[i-1]=new EquationUnit(Strings.cut(str1,' ',i));
		s=Strings.countChar(str2,' ');
		eqn2=new EquationUnit[s+1];
		for(int i=1;i<=s+1;i++)
			eqn2[i-1]=new EquationUnit(Strings.cut(str2,' ',i));
		totalate();
	}
	public void printEqn(String col)
	{
		X.sopln(this.representEqn(eqn1,eqn2),col);
	}
	public void printEqnSimplified(String col)
	{
		X.sopln((LHS.getUnitWhole()+"= "+RHS.getUnitWhole()).replace("+-"," -"));
	}
	public void printEqnSimplified()
	{
		printEqnSimplified("white");
	}
	public void printEqnHumanReadable(String col)
	{
		X.sopln(this.representEqnHumanReadable(eqn1,eqn2),col);
	}
	public void printEqnHumanReadable()
	{
		printEqnHumanReadable("white");
	}
	public static void printEqn(EquationUnit eu1,EquationUnit eu2,String col)
	{
		X.sopln((eu1.getUnitWhole()+"= "+eu2.getUnitWhole()).replace("+-"," -"),col);
	}
	public void printEqn()
	{
		printEqn("white");
	}
	protected String representEqn()
	{
		return representEqn(eqn1,eqn2);
	}
	protected String representEqnHumanReadable()
	{
		return representEqnHumanReadable(eqn1,eqn2);
	}
	public void totalate()
	{
		EquationUnit eu=EquationUnit.NULL;
		for(EquationUnit e : eqn1)
			eu=Calculator.addEUs(eu,e);
		LHS=eu;
		eu=EquationUnit.NULL;
		for(EquationUnit e : eqn2)
			eu=Calculator.addEUs(eu,e);
		RHS=eu;
		retotalate();
	}
	public void retotalate()
	{
		EquationUnit eu;
		
		eu=LHS;
		Np1X=eu.xCoN;
		Np1N=eu.nCoN;
		Dp1=eu.nCoD;
		eu=RHS;
		Np2X=eu.xCoN;
		Np2N=eu.nCoN;
		Dp2=eu.nCoD;
	}
	public void add(double v)
	{
		EquationUnit toAdd=new EquationUnit(v);
		eqn1=Calculator.append(eqn1,toAdd);
		eqn2=Calculator.append(eqn2,toAdd);
		LHS=Calculator.addEUs(LHS,toAdd);
		RHS=Calculator.addEUs(RHS,toAdd);
		retotalate();
	}
	public void sub(double v)
	{
		add(-v);
	}
	public void mul(double v)
	{
		for(EquationUnit eu : eqn1)
			eu.multiply(v);
		for(EquationUnit eu : eqn2)
			eu.multiply(v);
		LHS.multiply(v);
		RHS.multiply(v);
		retotalate();
	}
	public void divi(double v)
	{
		for(EquationUnit eu : eqn1)
			eu.divide(v);
		for(EquationUnit eu : eqn2)
			eu.divide(v);
		LHS.divide(v);
		RHS.divide(v);
		retotalate();
	}
	public void addV(double coeF)
	{
		EquationUnit toAdd=new EquationUnit(coeF+"x/1");
		eqn1=Calculator.append(eqn1,toAdd);
		eqn2=Calculator.append(eqn2,toAdd);
		LHS=Calculator.addEUs(LHS,toAdd);
		RHS=Calculator.addEUs(RHS,toAdd);
		retotalate();
	}
	public void subV(double coeF)
	{
		addV(-coeF);
	}
	public double solve(boolean s,String col)throws InvalidEqnForSolverException //s=steps
	{
		//public double Np1X,Np1N,Np2X,Np2N,Dp1,Dp2;
		if(s)
		{
			X.sopln("This solver shows steps following a certain algorithm. There may be other (and some certainly easier) methods to solve this equation.","Red");
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
		}
		X.sop(Symbols.IPLS+"\t",col);
		double ans;
		ans=(lhsx)?(RHS.nCoN)/RHS.nCoD:LHS.nCoN/LHS.nCoD;
		X.sopln("x = "+ans,col);
		return ans;
	}
	public boolean LHSX()throws InvalidEqnForSolverException
	{
		if(LHS.xCoN==RHS.xCoN)
			throw new InvalidEqnForSolverException(this.representEqn());
		return LHS.xCoN>RHS.xCoN;
	}
	public double solve(boolean s)throws InvalidEqnForSolverException
	{
		return solve(s,"white");
	}
	public double solve()throws InvalidEqnForSolverException
	{
		return solve(false,"white");
	}
	public double solve(String col)throws InvalidEqnForSolverException
	{
		return solve(true,col);
	}
	public void solveForX()throws InvalidEqnForSolverException
	{
		solve(true,"white");
	}
	public void solveForX(String col)
	{
		solve(true,col);
	}
	
	public static boolean isCompatible(String eqan)
	{
		return (X.hasChar(eqan,'=') && X.hasChar(eqan,'x'));
	}
	public static void main(String[] args)throws IOException
	{
		X.sop("Enter Equation: ");
		Equation eq=new Equation(X.rL().trim());
		eq.printEqn();
	}
}
class InvalidEqnForSolverException extends RuntimeException
{
	InvalidEqnForSolverException()
	{
		X.sopln("Seemingly, an invalid equation was given to solver. Please check it","red");
	}
	InvalidEqnForSolverException(String eqn)
	{
		X.sopln(eqn+" seems to be an insolvable equation. Please check it","red");
	}
}
