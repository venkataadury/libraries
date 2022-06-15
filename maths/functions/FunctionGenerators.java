package maths.functions;
import commons.X;
import maths.Maths;
import maths.BasicPolynomial;

public final class FunctionGenerators
{
	private FunctionGenerators() {}
	public static BasicPolynomial LaguerrePolynomial(int nl,int l21) // n+l and 2l+1
	{
		int deg=nl-l21;
		double[] coeffs=new double[deg+1];
		for(int q=0;q<=deg;q++)
			coeffs[q]=Math.pow(-1,q)*(double)Maths.factorial(nl)/(double)(Maths.factorial(deg-q)*Maths.factorial(l21+q));
		return new BasicPolynomial(coeffs,'r');
	}
}
