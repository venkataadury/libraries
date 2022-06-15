package maths.matrices;
import commons.*;

public class UnitMatrix extends DiagonalMatrix
{
	public UnitMatrix()
	{
		this(2);
	}
	public UnitMatrix(int Sq)
	{
		super(Sq);
		fix();
	}
	public UnitMatrix(UnitMatrix ext)
	{
		super.assign(ext);
		side=ext.side;
	}
	
	public void fix()
	{
		for(int i=0;i<mat.length;i++)
			mat[i][i]=UNIT;
	}
}
