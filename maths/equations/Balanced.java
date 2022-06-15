package maths.equations;
import commons.X;
import maths.Maths;

public interface Balanced
{
	public void add(double x);
	public void sub(double x);
	public void mul(double x);
	public void divi(double x);
	public void addV(double x); //xCo
	public void subV(double x); //xCo
	public void solveForX()throws InvalidEqnForSolverException;
}
