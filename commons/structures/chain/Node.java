package commons.structures.chain;
import commons.structures.Data;
import commons.X;
import upgrade.ArrayFx;
import java.awt.*;
import maths.Maths;
import java.util.HashMap;
import commons.logic.Condition;

public class Node
{
	public Data myDat;
	public final int ID;
	public static int idC=0;
	public int pX=-100,pY=-100;
	protected Bond[] bonds=new Bond[0];
	public static final int angleX=0;
	public static final int angleY=90;
	public static final double bLen=360*45.0,KbLen=75;
	public static Color bondColor=Color.magenta;
	public static final double MBONDSHIFT=5;
	Color fgCol=Color.yellow;
	Condition<Node> cond;
	//static Node sn,ln;
	boolean iterated=false;
	static
	{
		Maths.setRadians(false);
	}
	public Node() {ID=idC++;}
	public Node(Data d)
	{
		this();
		myDat=d;
	}
	
	public Data getData() {return myDat;}
	public void setData(Data d) {myDat=d;}
	public void setData(Object d) {myDat=new Data(d);}
	
	public void addBond(Bond b) {bonds=ArrayFx.append(bonds,b);}
	
	public void draw(Graphics g,int xC,int yC)
	{
		draw(g,xC,yC,-1);
	}
	public void draw(Graphics g,int xC,int yC,int ang)
	{
		iterated=true;
		if(xC<0 || yC<0)
			return;
		int angC=ang;
		ang=correctAngle(ang);
		Color temp=g.getColor();
		commonDraw(g,xC,yC);
		double aR=360.0/getEBondCount();
		int K=-1,aC=getAntiAngle(ang);
		if(angC==-1)
			aC=0;
		Node n;
		for(int i=0;i<bonds.length;i++)
		{
			if(bonds[i].isTerminalBond() || bonds[i].getSecond(this).isDrawn())
				continue;
			n=bonds[i].getSecond(this);
			K++;
			if(Math.abs((aR*K+aC)-ang)<=1 && angC>0)
				K++;
			Point pt=drawBond(bonds[i],g,xC,yC,aR,K,aC,false);
			n.draw(g,pt.x,pt.y,getAntiAngle((int)Math.round(aR*K+aC)));
		}
		drawSelf(g,xC,yC);
		g.setColor(temp);
	}
	public void drawCycle(Graphics g,int xC,int yC,int ang,int N,Node lN)
	{
		X.sopln("Start: "+this.ID);
		int pS=getAntiAngle(ang);
		if(ang==-1)
			pS=180;
		pS=1+(pS*N)/360; pS=N-pS;
		drawCycleBG(g,xC,yC,ang,N,pS,this,lN);
		Bond b=this.getBondWith(lN);
		g.setColor(bondColor);
		drawBond(b,g,xC,yC,lN.pX,lN.pY);
	}
	public void drawCycleBG(Graphics g,int xC,int yC,int ang,int N,int no,Node sn,Node ln)
	{
		iterated=true;
		if(xC<0 || yC<0)
			return;
		int fA=360/N;
		int angC=ang;
		ang=correctAngle(ang);
		Color temp=g.getColor();
		commonDraw(g,xC,yC);
		double aR=fA;
		boolean L=true;
		Node n;
		int K=no;
		for(int i=0;i<bonds.length;i++)
		{
			n=bonds[i].getSecond(this);
			if(bonds[i].isTerminalBond() || bonds[i].getSecond(this).isIterated())
			{
				if(n==null || sn==null)
					X.sopln("NULL: "+i+" "+ID,"red");
				if(n.equals(sn) && this.equals(ln))
				{
					X.sopln("Back to Start");
					K++;
					no++;
					L=false;
				}
				continue;
			}
			X.sopln(aR*K-aR*no+"\t"+K+"\t"+L);
			while(!L && (Math.abs(correctAngle(aR*K)-correctAngle(aR*no))<=fA))
				K++;
			if(Math.abs(correctAngle(aR*K)-ang)<=1 && angC>=0)
				K++;
			//X.sopln(K+"\t"+ID+"\t"+ang+"\t"+correctAngle(aR*K));
			Point pt=drawBond(bonds[i],g,xC,yC,aR,K,true);
			if(L)
				n.drawCycleBG(g,pt.x,pt.y,getAntiAngle((int)Math.round(aR*K)),N,++K,sn,ln);
			else
				n.draw(g,pt.x,pt.y,getAntiAngle((int)Math.round(aR*K)));
			L=false;
			K++;
		}
		drawSelf(g,xC,yC);
		g.setColor(temp);
	}
	
	public static java.awt.Point drawBond(Bond b,Graphics g,int xC,int yC,double aR,int K,boolean fL) {return drawBond(b,g,xC,yC,aR,K,0,fL);}
	public static java.awt.Point drawBond(Bond b,Graphics g,int xC,int yC,double aR,int K,int aC,boolean fL)
	{
		double ang=aR*K+aC;
		double r=bLen/aR;
		if(aR==360)
			r=bLen/180;
		if(fL)
			r=KbLen;
		int xN=xC+(int)Math.round(r*Maths.cos(ang));
		int yN=yC+(int)Math.round(r*Maths.sin(ang));
		drawBond(b,g,xC,yC,xN,yN);
		return new Point(xN,yN);
	}
	public static void drawBond(Bond b,Graphics g,int xC,int yC,int xN,int yN)
	{
		double ang;
		if(xN!=xC)
			ang=Math.toDegrees(Math.atan((yN-yC)/(xN-xC)));
		else
			ang=90;
		ang=correctAngle(ang);
		int L1=0,L2=0;
		for(int j=0;j<b.getOrder();j++)
		{
			g.drawLine(xC+L1,yC+L2,xN+L1,yN+L2);
			L1-=(int)Math.round(MBONDSHIFT*Maths.sin(ang));
			L2+=(int)Math.round(MBONDSHIFT*Maths.cos(ang));
		}
	}
	public void drawSelf(Graphics g, int xC,int yC)
	{
		g.setColor(fgCol);
		HashMap<String,Integer> dr=getDrawHM();
		g.drawString(this.toString(),xC-10,yC+10);
		int xP=xC+8;
		for(String s : dr.keySet())
		{
			g.drawString(s,xP,yC+10);
			xP+=16*s.length();
			if(dr.get(s)==1)
				continue;
			g.setFont(draw.AWT.SmallFont);
			g.drawString(""+dr.get(s),xP,yC+20);
			xP+=10;
			g.setFont(draw.AWT.MediumFont);
		}
	}
	public void commonDraw(Graphics g,int xC,int yC)
	{
		pX=xC; pY=yC;
		g.setFont(draw.AWT.MediumFont);
		g.setColor(bondColor);
	}
	public static int getAntiAngle(int a) {return correctAngle(a+180);}
	public static int correctAngle(int a)
	{
		a%=360;
		if(a<0)
			a+=360;
		return a;
	}
	public static int correctAngle(double a) {return correctAngle((int)Math.round(a));}
	public static void setBondColor(Color c) {bondColor=c;}
	public static Color getBondColor() {return bondColor;}
	public boolean isTerminal() {return (bonds.length<=1);}
	public int getBondCount() {return bonds.length;}
	public int getEBondCount() //Extended bond count
	{
		int c=0;
		for(int i=0;i<bonds.length;i++)
		{
			if(bonds[i].getSecond(this).isTerminal())
				continue;
			c++;
		}
		return c;
	}
	public int getTotalBonds()
	{
		int n=0;
		for(Bond b : bonds)
			n+=b.getOrder();
		return n;
	}
	
	public Node unBond(Node n)
	{
		Node t=null;
		for(Bond b : bonds)
		{
			t=b.getSecond(this);
			if(t.equals(n))
			{
				t.removeBond(b);
				this.removeBond(b);
				b.destroy();
				break;
			}
		}
		return t;
	}
	public void removeBond(Bond bo)
	{
		bonds=ArrayFx.remove(bonds,bo);
	}
	
	public boolean isBondedTo(Node n2)
	{
		for(Bond b : bonds)
		{
			if(b.getSecond(this).equals(n2))
				return true;
		}
		return false;
	}
	public Bond getBondWith(Node n)
	{
		for(Bond b : bonds)
		{
			if(b.getSecond(this).equals(n))
				return b;
		}
		return null;
	}
	public<T> T getDataAs(T obj)
	{
		return ((T)getData().getData());
	}
	public void setCondition(Condition<Node> n) {cond=n;}
	public Condition<Node> getCondition() {return cond;}
	
	public String getDataString() {return myDat.toString();}
	public HashMap<String,Integer> getDrawHM()
	{
		String[] txt=new String[0];
		for(int i=0;i<bonds.length;i++)
		{
			if(!bonds[i].isTerminalBond())
				continue;
			txt=ArrayFx.append(txt,bonds[i].getSecond(this).toString());
		}
		return ArrayFx.count(txt);
	}
	public String getDrawString()
	{
		String str=this.toString();
		HashMap<String,Integer> sDat=getDrawHM();
		for(String s : sDat.keySet())
			str+=s+((sDat.get(s)>1)?sDat.get(s)+"":"");
		return str;
	}
	public boolean isDrawn()
	{
		return (pX>0 && pY>0);
	}
	public boolean isIterated() {return iterated;}
	public String toString() {return myDat.toString();}
	public Bond[] getBonds() {return bonds;}
	public void undraw()
	{
		this.uniterate();
		pX=-100;pY=-100;
		for(Bond b : getBonds())
		{
			if(b.getSecond(this).isDrawn())
				b.getSecond(this).undraw();
		}
	}
	public void uniterate()
	{
		iterated=false;
		for(Bond b : getBonds())
		{
			if(b.getSecond(this).isIterated())
				b.getSecond(this).uniterate();
		}
	}
	public boolean equals(Node n2) {return ID==n2.ID;}
}
