package maths.equations;
import maths.Maths;
import commons.*;
public class InEquationResult
{
	public InEquation ineq;
	public String op=null;
	public int opid;
	public double FVal;
	public boolean include=false;
	InEquationResult() {}
	InEquationResult(InEquation ieqn)
	{
		ineq=ieqn;
	}
	public void setOP(String oper)throws InvalidOperatorException
	{
		oper=oper.trim();
		for(int i=0;i<4;i++)
		{
			if(InEquation.ops[i].equals(oper))
			{
				op=oper;
				opid=i;
				include=Strings.hasChar(op,'=');
			}
		}
		if(op==null)
			throw new InvalidOperatorException();
	}
	public void setOper(String oper)throws InvalidOperatorException
	{
		setOP(oper);
	}
}

