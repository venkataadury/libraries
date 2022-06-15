package maths.matrices;
import commons.*;

public class DiagonalMatrix extends SquareMatrix
{
	public DiagonalMatrix()
	{
		super(2);
		mat[0][0]=mat[1][1]=UNIT;
	}
	public DiagonalMatrix(int Sq)
	{
		super(Sq);
		for(int i=0;i<Sq;i++)
			mat[i][i]=UNIT;
	}
	public DiagonalMatrix(int[] diags)
	{
		this(diags.length);
		for(int i=0;i<diags.length;i++)
			mat[i][i]=diags[i];
	}
	public DiagonalMatrix(double[] diags)
	{
		this(diags.length);
		for(int i=0;i<diags.length;i++)
			mat[i][i]=diags[i];
	}
	public DiagonalMatrix(int[][] mtx)
	{
		super(mtx);
		clear();
		for(int i=0;i<side;i++)
			mat[i][i]=mtx[i][i];
	}
	public DiagonalMatrix(DiagonalMatrix ext)
	{
		super.assign(ext);
		side=ext.side;
	}
}
