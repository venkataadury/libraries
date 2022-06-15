package maths.matrices;
import commons.*;

public class NullMatrix extends Matrix
{
	public NullMatrix()
	{
		this(2,2);
	}
	public NullMatrix(int Sq)
	{
		this(Sq,Sq);
	}
	public NullMatrix(int dx,int dy)
	{
		super(dx,dy);
		clear();
	}
	public NullMatrix(NullMatrix ext)
	{
		super.assign(ext);
	}
}
