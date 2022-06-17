package jtools.positionedgames;
import jtools.gaming.*;
import jtools.time.Pulse;
import commons.DataTable;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import commons.X;

public class ActiveTerrain extends Terrain
{
	public static final Color TRANSP=new Color(1.0f,1.0f,1.0f,0.0f);
	public static Character[] liq=new Character[] {'W','Y','0',(char)0};
	public static Character[] allTerr=new Character[] {'G','E','W','Y','0',(char)0}; //6 in list
	public static Color[] terrCols=new Color[] {Color.green,new Color(139,69,19),Color.cyan,new Color(0.8f,0.8f,0.0f,0.7f),TRANSP,TRANSP}; //6 in list
	public static int terrNum=allTerr.length,liqNum=liq.length;
	public static DataTable<Character,Double> densities=new DataTable<Character,Double>(liq,new Double[] {1.0,0.9,0.0,0.0},liqNum);
	public static DataTable<Character,Color> colours=new DataTable<Character,Color>(allTerr,terrCols,terrNum);
	public static final double K=2.5;

	public ActiveTerrain(int w,int h)
	{
		super(w,h);
	}
	public ActiveTerrain(char[][] ter)
	{
		super(ter);
	}
	public ActiveTerrain(java.io.File fl) {super(fl);}
	public ActiveTerrain(java.io.File fl, int lNo) {super(fl,lNo);}

	public final Color getColorOf(char ch)
	{
		return (Color)colours.get(ch);
	}
	public boolean isClear(int x,int y,GameDimension OD)
	{
		int ol=x+(int)OD.getWidth();
		int od=y;
		for(int i=x;i<ol;i++)
		{
			if(i>wid-1)
				return true;
			if(!densities.containsKey(T[od][i]))
				return false;
		}
		return true;
	}
	public double[] coveredForce(int x,int y,GameDimension OD)
	{
		int tot=OD.getAreaXY();
		double force=0;
		int frac=0;
		int ol=x+(int)OD.getWidth();
		int od=y;
		for(int i=x;i<ol;i++)
		{
			if(i>wid-1 || od>hei-1)
				break;
			if(!densities.containsKey(T[od][i]))
				return new double[] {-1,-1};
			force+=K*(Double)densities.get(T[od][i])/tot;
			if((Double)densities.get(T[od][i])>0.3)
				frac++;
		}
		return new double[] {force*Terrain.g*10,(double)frac/tot};
	}
	public boolean isClear(int x,int y,ActiveObject ob)
	{
		double[] cFi=coveredForce(x,y,ob.getDimension());
		double cF=cFi[0];
		if(cF==-1)
			return false;
		System.out.println("Buoyancy: "+cF/ob.getDensity());
		ob.setTempAccelerationY(cF/ob.getDensity());
		if(cFi[1]<1 && cFi[1]>0.01) {
			ob.setVelY(0.01); ob.setVelX(0.001);}
		return true;
	}

	public boolean isClear(int x,int y,PhysicsObject ob)
	{
		double[] cFi=coveredForce(x,y,ob.getDimension());
		double cF=cFi[0];
		if(cF==-1)
			return false;
		System.out.println("Buoyancy: "+cF/ob.getDensity());
		ob.setTempAccelerationY(cF/ob.getDensity());
		return true;
	}

	public Image toImage()
	{
		img=new BufferedImage(wid+2,hei+2,BufferedImage.TYPE_INT_ARGB);
		Graphics g=img.createGraphics();
		((Graphics2D)g).setBackground(TRANSP);
		for(int i=0;i<T.length;i++)
		{
			for(int j=0;j<T[i].length;j++)
			{
				img.setRGB(j,i,this.getColorOf(T[i][j]).getRGB());
			}
		}
		try {ImageIO.write(img,"PNG",new java.io.File("/home/venkata/testimg.png"));} catch(Exception e ){e.printStackTrace();}
		return img;
	}
	public Image getImage() {return toImage();}

}
