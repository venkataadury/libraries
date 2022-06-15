package maths;
import commons.*;

public interface Operatable extends Cloneable
{
	public double getVal(double v);
	public double getVal(char[] vars,double[] vals);
	public char getVar();
	public void setVar(char v);
	public void printExpr(String col);
	public void typeExpr(String col);
	public void addCoef(double co)throws InconsistantCoefException;
	public double getPwr()throws InconsistantPowerException;
	public double getCoef()throws InconsistantCoefException;
	public boolean isConst();
	public Operatable differentiate(char resp)throws DifferentiationException;
	/*public Operatable integrate(char resp)throws IntegrationException; //Unlimited (Constant = 0) Integration
	public Operatable integrate(char resp,double k)throws IntegrationException; //Unlimited (Constant = k) Integration
	public Number integrate(char resp,double iv,double fv)throws IntegrationException; //Limited Integeration from 'iv' to 'fv'*/
	public boolean hasVar(char v);
	public Operatable mul(Operatable o);
	public Operatable divi(Operatable o);
	public Operatable add(Operatable o);
	public Operatable sub(Operatable o);
	public Operatable getClone();
	public Operatable replace(char c,double v);
	public double[] getPwr(char c)throws InconsistantPowerException;
	public Constant getCoef(char c,double pwr)throws InconsistantCoefException;
	public Constant getCoef(char[] c,double[] pwr)throws InconsistantCoefException;
}
class InconsistantPowerException extends RuntimeException
{
	InconsistantPowerException()
	{
		X.sopln("The Operatable value has a varying power","red");
	}
}
class InconsistantCoefException extends RuntimeException
{
	InconsistantCoefException()
	{
		X.sopln("The Operatable value has a varying coefficient","red");
	}
	InconsistantCoefException(String msg)
	{
            X.sopln(msg,"red");
	}
}
