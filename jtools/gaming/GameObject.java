package jtools.gaming;
import jtools.time.*;
import java.awt.image.*;
import java.awt.*;
import draw.S;
import java.util.HashMap;
import javax.naming.NameAlreadyBoundException;

public abstract class GameObject extends Thread
{
  protected Image myImage=null;
  protected GameDimension occupy=new GameDimension(1,1,0);
  protected double vX=0,vY=0,vZ=0;
  protected GamePoint pt=null;
  protected Pulse pul=null,dP=null;
  protected int freq=30;
  protected boolean visi=true;
  protected GameGraphics GFX=null;
  protected boolean isSolid=true;
  protected Terrain LT=null;
  public static boolean autopack=false;
  public GameObject[] dependencies=new GameObject[0];
  public boolean drawStatus=false;

  public static HashMap<String,GameObject> stored=new HashMap<String,GameObject>();

  public GameObject(GameDimension gd)
  {
    this(new BufferedImage(gd.getWidth(),gd.getHeight(),BufferedImage.TYPE_INT_ARGB));
  }
  public GameObject(Image img)
  {
	this(img,new GameDimension(img.getWidth(null),img.getHeight(null)));
  }
  public GameObject(Image img,GameDimension gd)
  {
	occupy=gd;
	myImage=img;
	//autorun();
  }
  public GameObject(Image img,GamePoint pti)
  {
	myImage=img;
	pt=pti;
	//autorun();
  }
  public GameObject(Image img,Point pti)
  {
	myImage=img;
	pt=new GamePoint(pti.x,pti.y,0);
	//autorun();
  }

  public void run()
  {
	final GameObject temp=this;
	dP=new Pulse(freq/2) {
		public void objective()
		{
      drawStatus=false;
			if(visi && GFX!=null)
				GFX.drawObject(temp);
      drawStatus=true;
		}
		public Object onExit() {return null;}
	};
	pul=new Pulse(freq) {
	  public void objective()
	  {
		perLoop();
	  }
	  public Object onExit()
	  {
		return onExit();
	  }
	};
	dP.start();
	pul.start();
  }
  public void start()
  {
	if(autopack)
		this.pack();
	super.start();
  }

  public void setDimension(int x,int y,int z)
  {
	occupy=new GameDimension(x,y,z);
  }
  public void setDimension(int x,int y)
  {
	occupy=new GameDimension(x,y);
  }
  public void setDimension(GameDimension gd) {occupy=gd;}
  public void setDimension(Dimension dm) {occupy=new GameDimension((int)dm.getWidth(),(int)dm.getHeight());}

  public void setScreen(GameScreen GS)
  {
	   GFX=GS.getGameGraphics();
  }

  public GamePoint getGamePosition()
  {
	return pt;
  }
  public GameDimension getDimension()
  {
	return occupy;
  }
  public java.awt.Point getPosition()
  {
	return pt.get2DPoint();
  }
  public double getX()
  {
	return pt.getX();
  }
  public double getY()
  {
	return pt.getY();
  }
  public double getZ()
  {
    if(pt==null)
      return 0;
    else
	   return pt.getZ();
  }

  public void moveX(double amt) {pt.xP+=amt;}
  public void moveY(double amt) {pt.yP+=amt;}
  public void moveZ(double amt) {pt.zP+=amt;}

  public void setVel(double v) {vX=vY=vZ=v;}

  public boolean safeMoveX(double amt)
  {
	if(LT==null)
	{
		moveX(amt); return true;
	}
	if(pt.xP+amt>LT.wid-1)
		return false;
	if(LT.isClear((int)(pt.xP+amt),(int)pt.yP,occupy))
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
	if(LT.isClear((int)(pt.xP),(int)(pt.yP+amt),occupy))
		moveY(amt);
	else
		return false;
	return true;
  }
  public boolean safeMoveZ(double amt)
  {
	moveZ(amt); return true;
  }
  public void setLocation(double a,double b,double c) {pt=new GamePoint(a,b,c);}
  public void setLocation(double a,double b) {pt=new GamePoint(a,b,getZ());}
  public void setUpdateTime(int t) //ms
  {
	freq=t;
  }

  public Image getImage()
  {
	return myImage;
  }

  public abstract void perLoop();
  public abstract Object onExit();
  public abstract void pack();

  public static void identify(String n,GameObject go)throws NameAlreadyBoundException
  {
	if(stored.containsKey(n))
	  throw new NameAlreadyBoundException();
	else
	  stored.put(n,go);
  }
  public static GameObject getObject(String n)
  {
	return stored.get(n);
  }
  public static void unidentify(String n)
  {
	stored.remove(n);
  }
  public void hide() {visi=false;}
  public void show() {visi=true;}

  public void setTerrain(Terrain t) {LT=t;}
  public double getVelX() {return vX;}
  public double getVelY() {return vY;}
  public double getVelZ() {return vZ;}

  public static void setAutoPack(boolean ap) {autopack=ap;}
}
