package jtools.gaming;
import commons.X;
import maths.*;

public class GameVector
{
	protected double xC=0,yC=0,zC=0; //Components
	protected GameDirection dir=null;
	//By Default, angles in degrees
	
	//public static final GameVector NULL=new GameVector(0,0,0);
	
	public GameVector() {}
	public GameVector(double x,double y,double z) {xC=x;yC=y;zC=z;dir=new GameDirection(xC,yC,zC);}
	public GameVector(double x,double y) {this(x,y,0);}
	public GameVector(double mag,double aXY,double aXZ,boolean angleInDegrees) 
	{
		if(angleInDegrees)
		{
			aXY=Math.toRadians(aXY);
			aXZ=Math.toRadians(aXZ);
		}
		yC=mag*Math.sin(aXY);
		xC=mag*Math.cos(aXY)*Math.sin(aXZ);
		zC=mag*Math.cos(aXY)*Math.cos(aXZ);
		dir=new GameDirection(aXY,aXZ,angleInDegrees);
	}
	public GameVector(double mag,double aXY,boolean angleInDegrees)
	{
		this(mag,aXY,0,angleInDegrees);
	}
	public GameVector(double mag,GameDirection dir)
	{
		this(mag,dir.getAngleXY(),dir.getAngleXZ(),false);
	}
	public GameVector(GamePoint pt)
	{
		this(pt.getX(),pt.getY(),pt.getZ());
	}
	public GameVector(java.awt.Point pt)
	{
		this(pt.x,pt.y,0);
	}
	public GameVector(GameVector ext)
	{
		this(ext.xC,ext.yC,ext.zC);
	}
	
	/*private void setDirection() //To Check
	{
		dir=new GameDirection(Math.atan(yC/xC),Math.atan(xC/zC),false); //aXY and aXZ
	}*/
	
	public double getMag()
	{
		return Math.sqrt(xC*xC+yC*yC+zC*zC);
	}
	public double getComponentX() {return xC;}
	public double getComponentY() {return yC;}
	public double getComponentZ() {return zC;}
	
	public double getXComponent() {return xC;}
	public double getYComponent() {return yC;}
	public double getZComponent() {return zC;}
	
	public double dot(GameVector gv2)
	{
		return dotProduct(this,gv2);
	}
	public GameVector cross(GameVector gv2)
	{
		return crossProduct(this,gv2);
	}
	public GameVector getUnitVector()
	{
		if(this.getMag()<0)
			return this;
		GameVector gv=new GameVector(this);
		gv.divi(this.getMag());
		return gv;
	}
	public String toString()
	{
		return xC+"i + "+yC+"j + "+zC+"k";
	}
	
	public void mul(double v)
	{
		xC*=v; yC*=v; zC*=v;
	}
	
	public GameVector getMultiplied(double v)
	{
		GameVector r=new GameVector(this);
		r.mul(v);
		return r;
	}
	public GameVector getDivided(double v)
	{
		GameVector r=new GameVector(this);
		r.divi(v);
		return r;
	}
	public void divi(double v) {xC/=v; yC/=v; zC/=v;}
	
	public GameVector add(GameVector gv2)
	{
		GameVector gv=new GameVector(this);
		gv.xC+=gv2.xC;
		gv.yC+=gv2.yC;
		gv.zC+=gv2.zC;
		return gv;
	}
	public GameVector sub(GameVector gv2)
	{
		GameVector gv=new GameVector(this);
		gv.xC-=gv2.xC;
		gv.yC-=gv2.yC;
		gv.zC-=gv2.zC;
		return gv;
	}
	public GameVector neg()
	{
		return new GameVector(-xC,-yC,-zC);
	}
	
	public void setXComponent(double v) {xC=v;}
	public void setYComponent(double v) {yC=v;}
	public void setZComponent(double v) {zC=v;}
	
	public void addX(double v) {xC+=v;}
	public void addY(double v) {yC+=v;}
	public void addZ(double v) {zC+=v;}
	
	public static final double dotProduct(final GameVector gv1,final GameVector gv2)
	{
		return gv1.xC*gv2.xC+gv1.yC*gv2.yC+gv1.zC*gv2.zC;
	}
	public static final GameVector crossProduct(final GameVector gv1,final GameVector gv2)
	{
		return new GameVector(gv1.yC*gv2.zC-gv2.yC*gv1.zC,gv1.zC*gv2.xC-gv1.xC*gv2.zC,gv1.xC*gv2.yC-gv2.xC*gv1.yC);
	}
	public static final GameVector getNull()
	{
		return new GameVector(0,0,0);
	}
}