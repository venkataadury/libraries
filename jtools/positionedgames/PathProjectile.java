package jtools.positionedgames;
import jtools.gaming.*;
import jtools.time.Pulse;
import java.awt.Image;

public class PathProjectile extends GameObject
{
  private double vX=0,vY=0,vZ=0;
  public static final double g=9.8;
  private double DX=0,DY=0,DZ=0;
  
  public PathProjectile(Image pimg) 
  {
	super(pimg,new GameDimension(pimg.getWidth(null),pimg.getHeight(null)));
  }
  public PathProjectile(Image pimg,double x,double y)
  {
	this(pimg,x,y,0);
	setDimension(new GameDimension(pimg.getWidth(null),pimg.getHeight(null)));
  }
  public PathProjectile(Image pimg,double x,double y,double z)
  {
	super(pimg,new GamePoint(x,y,z));
	setDimension(new GameDimension(pimg.getWidth(null),pimg.getHeight(null)));
  }
  
  /*public void run()
  {
	/*final GameObject temp=this;
	pul=new Pulse(freq) {
	  public void objective()
	  {
		if(visi && GFX!=null)
		  GFX.drawObject(temp);
		perLoop();
	  }
	  public Object onExit()
	  {
		return onExit();
	  }
	};
	pul.start();
	System.out.println("Started");
	super.run();
	GFX.drawObject(this);
	while(true)
	{
	  
	}
  }*/
  public void start()
  {
	super.start();
  }
  private void sequence()
  {
	if(Math.abs(vX)<1 && Math.abs(vY)<1 && Math.abs(vZ)<1)
	{
	  vY-=g; sequence();
	}
	//int xD=sgn(vX),yD=sgn(vY),zD=sgn(vZ);
	double xM=1,yM=xM*(vY/vX),zM=xM*(vZ/vX);
	if(vX==0){
	  yM=1; zM=yM*(vZ/vY);}
	boolean sX=safeMoveX(xM);
	boolean sY=safeMoveY(yM);
	if(!sX && !sY)
		this.interrupt();
	moveZ(zM);
	if(yM==0)
	  vY-=Math.abs(xM/vX)*g;
	else
	  vY-=Math.abs(yM/vY)*g;
	//test()
	if(Math.abs(DY-vY)>=g)
	  return;
	else
	  sequence();
  }
  public Object onExit() {return null;}
  public void perLoop() 
  {
	DX=vX;DY=vY;DZ=vZ;
	sequence();
	if(getY()<0)
	  this.interrupt();
	if(LT!=null &&  getX()>=LT.wid-5)
		this.interrupt();
  }
  public static int sgn(double n)
  {
	if(Math.abs(n)<1)
	  return 0;
	return (int)(n/Math.abs(n));
  }
  
  public void setVelX(int v) {vX=v;}
  public void setVelY(int v) {vY=v;}
  public void setVelZ(int v) {vZ=v;}
  public void pack() {}
}
