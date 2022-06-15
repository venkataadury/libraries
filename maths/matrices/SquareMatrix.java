package maths.matrices;
import maths.TypeMismatchException;
import commons.*;

public class SquareMatrix extends Matrix
{
	protected int side;
	public SquareMatrix()
	{
		this(2);
	}
	public SquareMatrix(int Sq)
	{
		super(Sq,Sq);
		side=Sq;
	}
	public SquareMatrix(double[][] mtx)
	{
		if(mtx.length<=0)
		{
			super.assign(new Matrix(false));
			return;
		}
		int l=mtx.length;
		if(mtx.length!=mtx[0].length)
		{
			X.sopln("Warning: Trimming to fit as Square Matrix","red");
			l=Math.min(mtx.length,mtx[0].length);
		}
		super.assign(new Matrix(l,l));
		for(int i=0;i<l;i++)
		{
			for(int j=0;j<l;j++)
				mat[i][j]=mtx[i][j];
		}
		side=l;
	}
	public SquareMatrix(int[][] mtx)
	{
		if(mtx.length<=0)
		{
			super.assign(new Matrix(false));
			return;
		}
		int l=mtx.length;
		if(mtx.length!=mtx[0].length)
		{
			X.sopln("Warning: Trimming to fit as Square Matrix","red");
			l=Math.min(mtx.length,mtx[0].length);
		}
		super.assign(new Matrix(l,l));
		for(int i=0;i<l;i++)
		{
			for(int j=0;j<l;j++)
				mat[i][j]=mtx[i][j];
		}
		side=l;
	}
	public SquareMatrix(SquareMatrix ext)
	{
		super.assign(ext);
		side=ext.side;
	}
}
