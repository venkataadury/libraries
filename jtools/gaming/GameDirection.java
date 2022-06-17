package jtools.gaming;
import maths.Maths;
import commons.X;

public class GameDirection
{
	protected double i=1,j=0,k=0; //Components
	public static final int PLANECODE_XY=0,PLANECODE_YZ=1,PLANECODE_XZ=2;
	public static final GameDirection XAXIS=new GameDirection(1,0,0),YAXIS=new GameDirection(0,1,0),ZAXIS=new GameDirection(0,0,1);
	
	public GameDirection() {}
	public GameDirection(double x,double y,double z) {i=x;j=y;k=z;}
	public GameDirection(double x,double y) {this(x,y,0);}
	public GameDirection(double x) {this(x,0,0);}
	public GameDirection(double an1,int pC1,double an2,int pC2,boolean angleInDegrees)
	{
		if(angleInDegrees)
		{
			an1=Math.toRadians(an1);
			an2=Math.toRadians(an2);
		}
		if(pC1==pC2)
			throw new RuntimeException("Error in GameDirection.<init>(): Same plane has two angles.");
		i=j=k=0;
		switch(pC1)
		{
			case 0:
				j+=Math.sin(an1);
				i+=Math.cos(an1);
				break;
			case 1:
				j+=Math.cos(an1);
				k+=Math.sin(an1);
				break;
			case 2:
				i+=Math.sin(an1);
				k+=Math.cos(an1);
				break;
			default:
				throw new RuntimeException("Error in GameDirection.<init>(): Unknown PlaneCode: "+pC1);
		}
		switch(pC2)
		{
			case 0:
				j+=Math.sin(an2);
				i+=Math.cos(an2);
				break;
			case 1:
				j+=Math.cos(an2);
				k+=Math.sin(an2);
				break;
			case 2:
				i+=Math.sin(an2);
				k+=Math.cos(an2);
				break;
			default:
				throw new RuntimeException("Error in GameDirection.<init>(): Unknown PlaneCode: "+pC2);
		}
	}
	public GameDirection(double an1,double an2,boolean angleInDegrees)
	{
		this(an1,0,an2,2,angleInDegrees);
	}
	
	//Angles returned in Radians
	public double getAngleXY() {return Math.atan(j/i);}
	public double getAngleYZ() {return Math.atan(k/j);}
	public double getAngleXZ() {return Math.atan(i/k);}
	public double getAngle(int PC)
	{
		switch(PC)
		{
			case 0:
				return getAngleXY();
			case 1:
				return getAngleYZ();
			case 2:
				return getAngleXZ();
			default:
				throw new RuntimeException("Error in GameDirection.getAngle(int): Invalid PlaneCode: "+PC);
		}
	}
	
	public boolean equals(GameDirection gd2)
	{
		double mag1=Math.sqrt(i*i+j*j+k*k);
		double mag2=Math.sqrt(gd2.i*gd2.i+gd2.j*gd2.j+gd2.k*gd2.k);
		return (i/mag1==gd2.i/mag2 && j/mag1==gd2.j/mag2 && k/mag1==gd2.k/mag2);
	}
}