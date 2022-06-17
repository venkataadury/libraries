package jtools.gaming;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import jtools.time.Pulse;
import javax.imageio.ImageIO;
import jtools.positionedgames.ActiveObject;

public abstract class Terrain extends Thread
{
	public static final double g=9.8;
	protected char[][] T=new char[0][0]; //terrain char-0 --> Empty, char->E --> Earth, char->W --> Water, char->G --> Grass
	public int wid=0,hei=0;
	protected BufferedImage img=null;
	protected Pulse pl=null;
	protected GameGraphics GFX=null;
	public static char[] liq=new char[] {'W'};
	public static final int fre=25;
	/*
	* Codes: 
	* 	G - Grass
	* 	E - Earth
	* 	0 - Empty (char)0
	* 	W - Water
	*	Y - Oil
	*/
  
	public Terrain(int W,int H) {T=new char[W][H]; wid=W;hei=H;}
	public Terrain(char[][] ext) {T=ext;
		wid=ext[0].length;
		hei=ext.length;
	}
	public Terrain(File fl) {readFromFile(fl);}
	public Terrain(File fl,int noL) {readFromFile(fl,noL);}
	
	public char getTerrainAt(int x,int y)throws ArrayIndexOutOfBoundsException
	{
		return T[x][y];
	}
	private void readFromFile(File fl,int noL)
	{
		FileReader fr=null;
		BufferedReader br=null;
		String ln;
		try
		{
			fr=new FileReader(fl); br=new BufferedReader(fr);
			int[] nLo=countLinesInFile(br);
			if(noL==-1)
			{
				wid=nLo[0]; hei=nLo[1];
			}
			else
			{
				wid=nLo[0]; hei=noL;
			}
			T=new char[hei][wid];
			fr=new FileReader(fl);
			br=new BufferedReader(fr);
			int K=0;
			while((ln=br.readLine())!=null)
			{
				T[K++]=ln.toCharArray();
			}
		}
		catch(Exception e) {e.printStackTrace();return;}
	}
	private void readFromFile(File fl)
	{
		readFromFile(fl,-1);
	}
	
	private int[] countLinesInFile(BufferedReader br)throws IOException
	{
		int dimX=0;
		int K=0;
		String s=br.readLine();
		dimX=(s!=null)?(s.length()):0;
		while((br.readLine())!=null)
			K++;
		return new int[]{dimX,K+1};
	}
	
	public abstract Image toImage();
	
	public abstract Color getColorOf(char ch);
	
	public void run()
	{
		pl.start();
	}
	public void start()
	{
		pl=new Pulse(fre) {
			public void objective()
			{
				if(GFX!=null)
				{
					GFX.pasteImage(toImage(),0,0);
				}
			}
			public Object onExit() {return null;}
		};
		super.start();
	}
	
	public void setScreen(GameScreen gs)
	{
		gs.getGameGraphics().setTerrain(this);
	}
	public boolean isEmpty(int x,int y)
	{
		return (T[y][x]=='0' || T[y][x]==(char)0);
	}
	public abstract boolean isClear(int x,int y,GameDimension OD);
	public abstract boolean isClear(int x,int y,ActiveObject obj);
	public abstract boolean isClear(int x,int y,PhysicsObject obj);
	/*public boolean intersects(int x,int y,GameDimension OD)
	{
		
	}*/
}