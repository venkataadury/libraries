package maths;
import commons.X;
import maths.*;


public interface Arithmetical<tp>
{
	public void ADD(tp x)throws TypeMismatchException;
	public void SUB(tp x)throws TypeMismatchException;
	public void MUL(double v);
	public void DIVI(double v);
}
