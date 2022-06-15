package maths.equations;
import commons.*;
import maths.*;
public class Calculator
{
	//Add and subtract equations
	//Other features too
	public static final char[] operators=new char[] {'+','-','*','/'};
	public static String breakByOp(String str,int field)
	{
		int[] inds=new int[4];
		if(hasOperator(str))
			return Y.cut(str,operators,field);
		else
			return str;
	}
	public static boolean hasOperator(String str)
	{
		for(char ch : str.toCharArray())
		{
			for(char ch2 : operators)
			{
				if(ch==ch2)
					return true;
			}
		}
		return false;
	}
	public static EquationUnit[] append(EquationUnit[] eua,EquationUnit eu)
	{
		EquationUnit[] ar=new EquationUnit[eua.length+1];
		for(int i=0;i<eua.length;i++)
			ar[i]=eua[i];
		ar[eua.length]=eu;
		return ar;
	}
	public static EquationUnit addEUs(EquationUnit eu1,EquationUnit eu2)
	{
		double xCo=0,nCo=0,coD=0;
		if(eu1.pos && eu2.pos)
		{
			xCo=(eu1.xCoN)*eu2.nCoD+(eu2.xCoN)*eu1.nCoD;
			nCo=(eu1.nCoN)*eu2.nCoD+(eu2.nCoN)*eu1.nCoD;
			coD=eu1.nCoD*eu2.nCoD;
			return new EquationUnit(xCo,nCo,coD);
		}
		else if(!eu1.pos && !eu2.pos)
		{
			xCo=(eu1.xCoN)*eu2.nCoD+(eu2.xCoN)*eu1.nCoD;
			nCo=(eu1.nCoN)*eu2.nCoD+(eu2.nCoN)*eu1.nCoD;
			coD=eu1.nCoD*eu2.nCoD;
			EquationUnit EU=new EquationUnit(xCo,nCo,coD);
			EU.pos=false;
			return EU;
		}
		else
		{
			if(eu1.pos)
			{
				xCo=(eu1.xCoN)*eu2.nCoD-(eu2.xCoN)*eu1.nCoD;
				nCo=(eu1.nCoN)*eu2.nCoD-(eu2.nCoN)*eu1.nCoD;
				coD=eu1.nCoD*eu2.nCoD;
			}
			else
			{
				xCo=(eu2.xCoN)*eu1.nCoD-(eu1.xCoN)*eu2.nCoD;
				nCo=(eu2.nCoN)*eu1.nCoD-(eu1.nCoN)*eu2.nCoD;
				coD=eu1.nCoD*eu2.nCoD;
			}
			return new EquationUnit(xCo,nCo,coD);
		}
	}
	public static int highestPower(PolyUnit[] pus)
	{
		int max=0;
		for(PolyUnit pu : pus)
		{
			for(Xpc pc : pu.pcs)
			{
				if(pc.Xpo>max)
					max=pc.Xpo;
			}
		}
		return max;
	}
	public static PolyUnit add(PolyUnit p1,PolyUnit p2)
	{
		for(Xpc unX : p1.pcs)
			unX.MUL(p2.DEN);
		for(Xpc unX : p2.pcs)
			unX.MUL(p1.DEN);
		Xpc[] PCs=p1.pcs;
		Xpc cWo=null;
		boolean flag=false;
		for(Xpc unX1 : PCs)
		{
			flag=false;
			for(Xpc unX2 : p2.pcs)
			{
				cWo=unX2;
				if(unX2.Xpo==unX1.Xpo)
				{
					unX1.ADD(unX2.co);
					flag=true;
					break;
				}
			}
			if(!flag)
				PCs=PolyUnit.appendX(PCs,cWo);
		}
		return new PolyUnit(PCs,p1.DEN*p2.DEN);
	}
	/*public static PolyUnit merge(PolyUnit p1,PolyUnit p2)throws XPowerMismatchException
	{
		if(p1.Xpo!=p2.Xpo)
			throw new XPowerMismatchException();
		double x=p1.co;
		x+=p2.co;
		return new PolyUnit(x,p1.Xpo);
	}*/
	public static EquationUnit[] cross(EquationUnit eu1,EquationUnit eu2)
	{
		EquationUnit[] euaa=new EquationUnit[2];
		euaa[0]=new EquationUnit(eu1.xCoN*eu2.nCoD,eu1.nCoN*eu2.nCoD,1);
		euaa[1]=new EquationUnit(eu2.xCoN*eu1.nCoD,eu2.nCoN*eu1.nCoD,1);
		return euaa;
	}
}
class XPowerMismatchException extends RuntimeException
{
	XPowerMismatchException()
	{
		X.sopln("The power of 'x' does not match for a certain given input","red");
	}
}
