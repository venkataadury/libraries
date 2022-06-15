package maths.functions.plotting;
import maths.Maths;
import maths.functions.*;
import commons.X;

public class Scale
{
	private int minX=0,maxX=10,minY=0,maxY=10;
	
	public Scale() {}
	public Scale(int xR,int yR) {maxX=xR; maxY=yR;}
	public Scale(int lx,int ux,int ly,int uy)
	{
		minX=lx; minY=ly;
		maxX=ux; maxY=uy;
	}
	
	public int getXRange() {return maxX-minX;}
	public int getYRange() {return maxY-minY;}
	
	public int getMinX() {return minX;}
	public int getMinY() {return minY;}
	public double stepXValue(int size) {return ((double)(getXRange()))/size;}
	public double stepYValue(int size) {return ((double)(getYRange()))/size;}
	public int getXAxis(int size)
	{
		return (int)(size*(double)(maxY)/(maxY-minY));
	}
	public int getYAxis(int size)
	{
		return (int)(size*(double)(maxX)/(maxX-minX));
	}
}
