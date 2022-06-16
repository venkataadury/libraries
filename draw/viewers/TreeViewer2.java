package draw.viewers;
import draw.AWT;
import commons.X;
import maths.Maths;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import commons.structures.tree.MyNode;
import java.io.File;
import javax.imageio.ImageIO;

public class TreeViewer2 extends TreeViewer
{
	private boolean stop=false;
	public TreeViewer2()
	{
		super();
	}
	
	@Override
	public void draw(MyNode mn)
    {
		g.setColor(Color.white);
		g.fillRect(0,0,WID,HEI);
		X.sop("Levels: ","yellow");
		int l=mn.levels();
		X.sopln(l,"red");
		int span=mn.getSpan();
		MyNode[] lv;
		int Y=ST-offsetY;
		mn.printLevelledTree("yellow");
		for(int i=1;i<=l;i++)
		{
			lv=MyNode.getLevel(mn,i);
			drawLevel(g,lv,Y,true);
			Y+=SPACE+HGT;
			if(Y>HEI)
				break;
		}
    }
    
    protected void drawLevel(Graphics g,MyNode[] lv,int Y,boolean crop)
    {
		final int C=30,D=10;
		BufferedImage temp;
		double xP;
		int xP2,yP1;
		double span;
		for(int i=0;i<lv.length;i++)
		{
			temp=getImage(lv[i].getName());
			xP=MyNode.getPosition2(lv[i]);
			g.drawImage(temp,(int)(xP*(SIZE+GAP))+C-offsetX,Y-offsetY,null);
			g.setColor(Color.black);
			xP2=(int)(xP*(SIZE+GAP)+(3*SIZE/4)+5-offsetX);
			if(crop && xP2>WID+SIZE)
				break;
			if(crop && xP2<=-SIZE)
				continue;
			yP1=Y-SPACE/2-offsetY;
			if(lv[i].getParent()!=null)
				g.drawLine(xP2,yP1+SPACE/2,xP2,yP1);
			if(lv[i].hasChildren())
				g.drawLine(xP2,Y+HGT-offsetY,xP2,Y+HGT+SPACE/2-offsetY);
			if(lv[i].hasPreviousSibling())
			{
				span=lv[i].getLeftSpan()+0.5;
				g.drawLine(xP2,yP1,(int)(xP2-span*(SIZE+GAP)+((lv[i].getPreviousSibling().hasPreviousSibling())?D:0)),yP1);
			}
			if(lv[i].hasNextSibling())
			{
				span=lv[i].getRightSpan()+0.5;
				g.drawLine(xP2,yP1,(int)(xP2+span*(SIZE+GAP)+((lv[i].getNextSibling().hasNextSibling())?D:0)),yP1);
			}
			if(!crop)
				X.sop(lv[i].getName()+", ");
		}
		X.sopln("\b");
    }
    private void drawTo(Graphics g,int W,int H)
    {
		int poX=offsetX,poY=offsetY;
		offsetX=offsetY=0;
		int l=mainNode.levels();
		X.sopln(l+" levels");
		g.setColor(Color.white);
		g.fillRect(0,0,W,H);
		MyNode[] lv;
		int Y=ST;
		for(int i=1;i<=l;i++)
		{
			lv=MyNode.getLevel(mainNode,i);
			drawLevel(g,lv,Y,false);
			Y+=SPACE+HGT;
		}
		offsetX=poX;offsetY=poY;
    }
    public void saveToFile(File f)
    {
		final int C=50;
		BufferedImage tDraw=new BufferedImage((mainNode.getSelfSpan()+1)*(SIZE+GAP)+C,mainNode.levels()*(GAP+HGT)*2,BufferedImage.TYPE_INT_RGB);
		Graphics gen=tDraw.createGraphics();
		drawTo(gen,tDraw.getWidth(),tDraw.getHeight());
		try{ImageIO.write(tDraw,"JPG",f);}catch(IOException e){e.printStackTrace();return;}
    }
    
}
