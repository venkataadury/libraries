package jtools.gaming;
import jtools.time.*;
import java.awt.*;
import commons.X;

public abstract class PhysicsObject extends GameObject
{
	protected GameVector vel=GameVector.getNull(),acc=GameVector.getNull();
	protected GameVector tVel=GameVector.getNull(),tAcc=GameVector.getNull(),eVel=GameVector.getNull(),eAcc=GameVector.getNull();
	protected GamePoint tPos,fPos;
	protected double density=1;

	public PhysicsObject(Image img) {super(img);}
	public PhysicsObject(Image img,GameDimension obj)
	{
		super(img,obj);
	}
	public PhysicsObject(Image img,double x,double y)
	{
		super(img);
		pt=new GamePoint(x,y,0);
	}
	public PhysicsObject(Image img,Dimension dim)
	{
		super(img,new GameDimension((int)dim.getWidth(),(int)dim.getHeight()));
	}
	public PhysicsObject(Image img,GameDimension obj,double x,double y)
	{
		this(img,obj);
		pt=new GamePoint(x,y,0);
	}
	public PhysicsObject(Image img,Dimension dim,double x,double y)
	{
		this(img,dim);
		pt=new GamePoint(x,y,0);
	}

	public void perLoop()
	{
		iterate();
		eVel=new GameVector(vel);
		//eVel=eVel.add(tVel);
		tPos=new GamePoint(this.getGamePosition());
		//tAcc=new GameVector(0,0,0);
		if(getY()<0)
			return;
		if(LT!=null &&  getX()>=LT.wid-5)
			return;
		sequence();
		eAcc=new GameVector(acc);
		eAcc=eAcc.add(tAcc);
		tAcc=new GameVector(0,0,0);
	}

	private void sequence()
	{
		//X.sopln(aY+"","red");
		if(vel.getMag()<1)
		{
			//sequence();
			vel=vel.add(eAcc);
			return;
		}
		GameVector GV=vel.getUnitVector();
		boolean sX=safeMoveX(GV.getXComponent());
		boolean sY=safeMoveY(GV.getYComponent());
		X.sopln(GV.getYComponent()+"","green");

		moveZ(GV.getZComponent());
		//vel=vel.add(eAcc.getMultiplied(1/vel.getMag()));
		if(!sX)
			vX=0;
		if(!sY)
			vY=0;
		if(!sX && !sY)
			return;
		if(sX)
			vel.addX(eAcc.getXComponent()*(1/vel.getMag()));
			//vel.addX(eAcc.getXComponent());
		if(sY)
			vel.addY(eAcc.getYComponent()*(1/vel.getMag()));
			//vel.addY(eAcc.getYComponent());
		//X.sopln((new GameVector(pt)).sub(new GameVector(tPos)).getMag()+">"+vel.getMag());
		if((new GameVector(pt)).sub(new GameVector(tPos)).getMag()>eVel.getMag())
		{
			X.sopln("End of Loop","red");
			return;
		}
		else
			sequence();

		/*if((Math.abs(eAcc.getXComponent())>=1 && sX) || (Math.abs(eAcc.getYComponent())>=1 && sY))
		{
			if(eVel.sub(vel).getMag()>=eAcc.getMag())
				return;
			else
				sequence();
		}
		else
		{
			//if((!sX && Math.abs(getY()-posIY)>vY) || (!sY && Math.abs(getX()-posIX)>vX))
			if((!sX && (getY()-tPos.getY())>eVel.getYComponent()) || (!sY && Math.abs(getX()-tPos.getX())>eVel.getXComponent()))
				return;
			else
				sequence();
		}*/
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

	public abstract void iterate();

	public void setAccelerationX(double ac) {acc.setXComponent(ac);}
	public void setAccelerationY(double ac) {acc.setYComponent(ac);}
	public void setAccelerationZ(double ac) {acc.setZComponent(ac);}
	public void setAcceleration(GameVector gv) {acc=new GameVector(gv);}
	public void setTempAccelerationX(double ac) {tAcc.setXComponent(ac);}
	public void setTempAccelerationY(double ac) {tAcc.setYComponent(ac);}
	public void setTempAccelerationZ(double ac) {tAcc.setZComponent(ac);}
	public void setVelX(double v) {vel.setXComponent(v);}
	public void setVelY(double v) {vel.setYComponent(v);}
	public void setVelZ(double v) {vel.setZComponent(v);}
	public void setDensity(double ds) {density=ds;}
	public double getDensity() {return density;}

	/*Special*/
	public void addAccelerationX(double v) {acc.addX(v);}
	public void addAccelerationY(double v) {acc.addY(v);}
	public void addAccelerationZ(double v) {acc.addZ(v);}
	public void addAcceleration(GameVector gv) {acc=acc.add(gv);}

	public double getAccelerationX() {return acc.getXComponent()+tAcc.getXComponent();}
	public double getAccelerationY() {return acc.getYComponent()+tAcc.getYComponent();}
	public double getAccelerationZ() {return acc.getZComponent()+tAcc.getZComponent();}

	public void pack() {eAcc=new GameVector(acc); eAcc.add(tAcc);}
}
