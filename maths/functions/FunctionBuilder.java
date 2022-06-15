package maths.functions;
import commons.*;
import maths.matrices.*;
import maths.Maths;

public class FunctionBuilder extends Maths
{
	double[][] mtx,tmtx,tmtx2;
	double[] vals;
	public FunctionBuilder() {}
	public FunctionBuilder(int n)
	{
		mtx=new double[n][n];
		tmtx=tmtx2=mtx;
		vals=new double[0];
	}
	public FunctionBuilder(double[] in,double[] out)
	{
		testInput(in);
		buildMTX(in);
		vals=out;
	}
	
	private void testInput(double[] in)throws RuntimeException
	{
		for(int i=0;i<in.length;i++)
		{
			for(int j=i+1;j<in.length;j++)
			{
				if(in[i]==in[j])
					throw new RuntimeException("A function cannot have same input with different output. Please check input for duplicates too.");
			}
		}
		return;
	}
	
	private void buildMTX(double[] in)
	{
		mtx=new double[in.length][in.length];
		for(int i=0;i<in.length;i++)
		{
			for(int j=0;j<mtx.length;j++)
				mtx[i][j]=Math.pow(in[i],j);
		}
	}
	
	private final Function solveMTX()
	{
		if(mtx.length==0 || mtx[0].length==0)
			throw new RuntimeException();
		double[][] V=new double[vals.length][1];
		for(int i=0;i<vals.length;i++)
			V[i][0]=vals[i];
		Matrix C=new Matrix(V);
		Matrix A=new Matrix(mtx);
		Matrix B=A.getInverse();
		Matrix X=B;
		X.MUL(C);
		return new Function(X,'x');
	}
	
	public Function solve()
	{
		return solveMTX();
	}
	public void printMTX()
	{
		Matrix m=new Matrix(mtx);
		m.printMatrix();
	}
}
