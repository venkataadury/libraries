package commons.structures.binarytree;
import commons.X;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.awt.*;
import java.awt.event.*;
import draw.AWT;
import commons.structures.Data;
import commons.structures.Process;

public class TreeBuilder extends Frame implements MouseListener,ActionListener
{
	
	private File fl;
	BNode mnode=null;
	BNode curNode=null;
	public static final int W=800,H=600,MSIZE=40,LSIZE=5,GSIZE=30;
	public static final int XM=200,YM=200;
	public static final Color bgcol=Color.black,fgcol=Color.white;
	
	Rectangle mnR,pnR,lnR,rnR;
	private BNode selected;
	TextArea ta=new TextArea();
	TextField tf=new TextField();
	Button sB=new Button("OK"),saveB=new Button("SAVE");
	Button eL=new Button("Extend Left"),eR=new Button("Extend Right");
	
	//public TreeBuilder() {} (New tree)
	public TreeBuilder(String str)
	{
		fl=new File(str);
		loadFile();
		setup();
	}
	public TreeBuilder(File f)
	{
		fl=f;
		loadFile();
		setup();
	}
	
	public void loadFile()
	{
		try {mnode=new BNode(fl);}
		catch(IOException e) {e.printStackTrace(); return;}
		curNode=mnode;
	}
	
	private void setup()
	{
		mnR=pnR=lnR=rnR=new Rectangle();
		this.setSize(W,H);
		this.addWindowListener(AWT.WINPROPS);
		this.addMouseListener(this);
		this.setVisible(true);
		X.halt(0.5);
		
		sB.setBounds(W-130,360,100,30);
		sB.addActionListener(this);
		sB.setVisible(true);
		this.add(sB);
		
		eL.setBounds(W-140,400,120,30);
		eL.addActionListener(this);
		eL.setVisible(true);
		this.add(eL);
		eR.setBounds(W-140,430,120,30);
		eR.addActionListener(this);
		eR.setVisible(true);
		this.add(eR);
		
		saveB.setBounds(W-130,480,100,30);
		saveB.addActionListener(this);
		saveB.setVisible(true);
		this.add(saveB);
		
		tf.setBounds(W-140,320,120,30);
		tf.setVisible(true);
		this.add(tf);
		
		ta.setBounds(W-140,100,120,200);
		ta.setVisible(true);
		this.add(ta);
		
		
		update();
	}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e)
	{
		Point pt=e.getPoint();
		X.sopln(pt);
		if(mnR.contains(pt)) 
			selectNode(curNode);
		else if(pnR.contains(pt))
		{
			if(isSelected(curNode.up()))
			{
				curNode=curNode.up();
				unselect();
				return;
			}
			selectNode(curNode.up());
		}
		else if(lnR.contains(pt))
		{
			if(isSelected(curNode.left()))
			{
				curNode=curNode.left();
				unselect();
				return;
			}
			selectNode(curNode.left());
		}
		else if(rnR.contains(pt))
		{
			if(isSelected(curNode.right()))
			{
				curNode=curNode.right();
				unselect();
				return;
			}
			selectNode(curNode.right());
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==sB)
		{
			selected.setData(new Data(ta.getText().trim().replace("\n"," ")));
			selected.setLabel(tf.getText().trim());
		}
		else if(e.getSource()==saveB)
		{
			if(curNode==null)
				return;
			curNode.saveTo(fl);
		}
		else if(e.getSource()==eL)
		{
			if(curNode==null || (curNode.left()!=null && !curNode.left().isEmptyNode()))
				return;
			curNode.extendLeft(null,"New Node");
		}
		else if(e.getSource()==eR)
		{
			if(curNode==null || (curNode.right()!=null && !curNode.right().isEmptyNode()))
				return;
			curNode.extendRight(null,"New Node");
		}
		update();
	}
	
	private void selectNode(BNode n)
	{
		selected=n;
		ta.setText(selected.getData().toString());
		tf.setText(selected.getLabel());
		update();
	}
	private void unselect()
	{
		selected=null;
		ta.setText("");
		tf.setText("");
		update();
	}
	public void paint(Graphics g)
	{
		mnR=pnR=lnR=rnR=new Rectangle();
		if(g==null)
			return;
		g.setFont(AWT.SmallFont);
		g.setColor(Color.white);
		g.fillRect(0,0,W,H);
		g.setColor(bgcol);
		g.drawLine(W-150,0,W-150,H);
		
		if(curNode==null)
		{
			X.sopln("NULL","red");
			return;
		}
		mnR=drawNode(g,curNode,XM,YM);
		if(curNode.up()!=null)
		{
			g.drawLine(XM+50,YM,XM+50,YM-170);
			pnR=drawNode(g,curNode.up(),XM,YM-160);
		}
		if(curNode.isTerminal())
			return;
		
		if(curNode.left()!=null)
		{
			g.setColor(bgcol);
			g.drawLine(XM+50,YM,XM,YM+120);
			lnR=drawNode(g,curNode.left(),XM-100,YM+120);
		}
		if(curNode.right()!=null)
		{
			g.setColor(bgcol);
			g.drawLine(XM+50,YM,XM+200,YM+120);
			rnR=drawNode(g,curNode.right(),XM+150,YM+120);
		}
		
	}
	
	public boolean isSelected(BNode n)
	{
		if(selected==null)
			return false;
		else
			return selected.equals(n);
	}
	private Rectangle drawNode(Graphics g,BNode curNode,int x,int y)
	{
		if(curNode==null)
			return new Rectangle();
		int l=(curNode.getData()!=null)?curNode.getData().toString().length()/MSIZE+1:1;
		String[] lines=new String[l];
		int w=MSIZE*LSIZE;
		int h=l*GSIZE;
		
		g.setColor(bgcol);
		g.fillRect(x,y,w,h);
		String sT=(curNode.getData()!=null)?curNode.getData().toString():"";
		for(int i=0;i<lines.length;i++)
			lines[i]=(i==lines.length-1)?sT.substring(i*MSIZE):sT.substring(i*MSIZE,(i+1)*MSIZE);
		int yC=y+15;
		g.setColor(fgcol);
		for(String s : lines)
		{
			g.drawString(s,x+10,yC);
			yC+=GSIZE;
		}
		return new Rectangle(x,y,w,h);
	}
	
	public void update() {paint(this.getGraphics());}
}
