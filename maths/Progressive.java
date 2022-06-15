package maths;
import commons.*;

public interface Progressive
{
	public double sum(int n); //Sn
	public double prod(int n); //Pn
	public double getTerm(int n); //Term
	public double getFirstTerm(); //a
	public boolean isAP();
	public boolean isGP();
	public Progressive getProgression();
	public void printSeries(int n,String col);
	public boolean order(); //true= rising
	public boolean fixed();
	public boolean hasTerm(double t);
	public void present(char v);
}
