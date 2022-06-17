package jtools.positionedgames;
import jtools.gaming.*;
import jtools.time.Pulse;
import java.awt.*;
import java.awt.image.*;
import commons.X;

public abstract class ActiveObject extends GameObject //NOTE: Object must be "packed" before start() is invoked
{
	double facing=0; //--> x-axis
	private double DX=0,DY=0,DZ=0;
	protected double aX=0,aY=0,aZ=0;
	protected double taX=0,taY=0,taZ=0;
	protected double paX=0,paY=0,paZ=0;
	protected double posIX=0,posIY=0,posIZ=0;
	public double density=1;
	
	public ActiveObject(Image img,GameDimension obj)
	{
		super(img,obj);
	}
	public ActiveObject(Image img,double x,double y)
	{
		super(img);
		pt=new GamePoint(x,y,0);
	}
	public ActiveObject(Image img,Dimension dim)
	{
		super(img,new GameDimension((int)dim.getWidth(),(int)dim.getHeight()));
	}
	public ActiveObject(Image img,GameDimension obj,double x,double y)
	{
		this(img,obj);
		pt=new GamePoint(x,y,0);
	}
	public ActiveObject(Image img,Dimension dim,double x,double y)
	{
		this(img,dim);
		pt=new GamePoint(x,y,0);
	}
	
	public void perLoop()
	{
		iterate();
		DX=vX;DY=vY;DZ=vZ;
		posIX=getX(); posIY=getY(); posIZ=getZ();
		taX=taY=taZ=0;
		if(getY()<0)
			return;
		if(LT!=null &&  getX()>=LT.wid-5)
			return;
		sequence();
		aX=paX+taX; aY=paY+taY; aZ=paZ+taZ;
	}
	
	private void sequence()
	{
		//X.sopln(aY+"","red");
		if(Math.abs(vX)<1 && Math.abs(vY)<1 && Math.abs(vZ)<1)
		{
			vX+=aX;
			vY+=aY;
			vZ+=aZ;
			//sequence();
			return;
		}
		//int xD=sgn(vX),yD=sgn(vY),zD=sgn(vZ);
		double xM=1,yM=xM*(vY/vX),zM=xM*(vZ/vX);
		if(vX==0){
			yM=1*(Math.abs(vY)/vY); zM=yM*(vZ/vY);}
		boolean sX=safeMoveX(xM);
		boolean sY=safeMoveY(yM);
		//if(!sX && !sY) {vX=0; vY=0; return;}
		moveZ(zM);
		double R=(yM==0)?Math.abs(xM/vX):Math.abs(yM/vY);
		vX+=R*aX;
		vY+=R*aY;
		vZ+=R*aZ;
		if(!sX)
			vX=0;
		if(!sY)
			vY=0;
		if(!sX && !sY)
			return;
		if((Math.abs(aX)>=1 && sX) || (Math.abs(aY)>=1 && sY))
		{
			if((Math.abs(DY-vY)>=Math.abs(aY) || !sY) && (Math.abs(DX-vX)>=Math.abs(aX) || !sX))
				return;
			else
				sequence();
		}
		else
		{
			if((!sX && Math.abs(getY()-posIY)>vY) || (!sY && Math.abs(getX()-posIX)>vX))
				return;
			else
				sequence();
		}
	}
	
	public boolean safeMoveX(double amt)
	{
		if(LT==null)
		{
			moveX(amt); return true;
		}
		if(pt.xP+amt>LT.wid-1)
			return false;
		if(LT.isClear((int)(pt.xP+amt),(int)pt.yP,this))
			moveX(amt);
		else
			return false;
		return true;
	}
	public boolean safeMoveY(double amt)
	{
		if(pt.yP+amt<0)
			return false;
		if(LT==null)
		{
			moveY(amt); return true;
		}
		//System.out.println(pt.xP+","+(pt.yP+amt)+"\t"+LT.isEmpty((int)pt.xP,(int)(pt.yP+amt)));
		if(LT.isClear((int)(pt.xP),(int)(pt.yP+amt),this))
			moveY(amt);
		else
			return false;
		return true;
	}
	
	public void setAccelerationX(double ac) {paX=ac;}
	public void setAccelerationY(double ac) {paY=ac;}
	public void setAccelerationZ(double ac) {paZ=ac;}
	public void setTempAccelerationX(double ac) {taX=ac;}
	public void setTempAccelerationY(double ac) {taY=ac;}
	public void setTempAccelerationZ(double ac) {taZ=ac;}
	public void setVelX(double v) {vX=v;}
	public void setVelY(double v) {vY=v;}
	public void setVelZ(double v) {vZ=v;}
	public void setDensity(double ds) {density=ds;}
	public double getDensity() {return density;}
	
	public double getAccelerationX() {return paX+taX;}
	public double getAccelerationY() {return paY+taY;}
	public double getAccelerationZ() {return paZ+taZ;}
	
	
	public abstract void iterate();
	public void pack() {aX=paX+taX; aY=paY+taY; aZ=paZ+taZ;}
}
