package draw.viewers;
import draw.AWT;
import commons.X;
import commons.NPCs;
import maths.Maths;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import commons.structures.tree.MyNode;

public abstract class TreeViewer extends Frame implements ActionListener,MouseListener
{
	private File xmlFile=null;
    protected MyNode mainNode=null;
    private Document DOC=null;
    public static final int WID=870,HEI=600,ST=60;
    public static final int SIZE=100,GAP=20,HGT=30,SPACE=30,SET=45;
    protected int offsetX=0,offsetY=0;
    private int size=0,lastSpan=-1;
    public Button openB=new Button("Open");
    public Button scrL=new Button(""+NPCs.ARROW_LEFT),scrR=new Button(""+NPCs.ARROW_RIGHT);
    public Button scrU=new Button(""+NPCs.ARROW_UP),scrD=new Button(""+NPCs.ARROW_DOWN);
    public Button saveB=new Button("Save");
    protected final Graphics g;
    
	protected TreeViewer()
	{
		this.addWindowListener(AWT.WINPROPS);
		this.setSize(WID,HEI);
		this.setVisible(true);
		g=this.getGraphics();
		g.setFont(new Font("Lohit Telugu",12,12));
		X.halt(0.5);
		init();
	}
	
	public void init()
	{
		openB.setBounds(20,20,100,30);
		openB.addActionListener(this);
		openB.setVisible(true);
		saveB.setBounds(125,20,100,30);
		saveB.addActionListener(this);
		saveB.setVisible(true);
		scrL.setBounds(20,HEI-45,25,25);
		scrR.setBounds(WID-75,HEI-45,25,25);
		scrU.setBounds(WID-45,20,25,25);
		scrD.setBounds(WID-45,HEI-45,25,25);
		scrL.addActionListener(this);
		scrR.addActionListener(this);
		scrU.addActionListener(this);
		scrD.addActionListener(this);
		scrL.setVisible(true);
		scrR.setVisible(true);
		scrU.setVisible(true);
		scrD.setVisible(true);
		this.add(scrL);
		this.add(scrR);
		this.add(scrU);
		this.add(scrD);
		this.add(openB);
		this.add(saveB);
		this.addMouseListener(this);
		g.setColor(Color.white);
		g.fillRect(0,0,WID,HEI);
		g.setColor(Color.black);
		g.drawLine(0,ST-10,WID,ST-10);
	}
	
	//Action Events
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==openB)
			loadFile();
		if(e.getSource()==scrR)
			offsetX+=SET;
		if(e.getSource()==scrL)
			offsetX-=SET;
		if(e.getSource()==scrU)
			offsetY-=SET;
		if(e.getSource()==scrD)
			offsetY+=SET;
		if(e.getSource()==saveB)
		{
			JFileChooser fc=new JFileChooser();
			int x=fc.showOpenDialog(this);
			File nF;
			if(x==0)
			{
				nF=fc.getSelectedFile();
				saveToFile(nF);
				System.out.println("OK");
				JOptionPane.showMessageDialog(this,"Saved");
			}
		}
		redraw();
	}
	//Mouse Events
	public void mouseClicked(MouseEvent e) {Point p=e.getPoint();X.sopln(p.getX()+","+p.getY());}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	
	private void loadFile()
	{
		JFileChooser fc=new JFileChooser();
        int x=fc.showOpenDialog(this);
        if(x==0)
        {
            xmlFile=fc.getSelectedFile();
            String fn=xmlFile.getAbsolutePath();
            fn=fn.substring(fn.lastIndexOf('.')+1);
            fn=fn.trim();
            if(!(fn.equalsIgnoreCase("xml") || fn.equalsIgnoreCase("csv")))
                JOptionPane.showMessageDialog(this,"Please only select well-formatted XML/CSV files");
            System.out.println("OK");
            mainNode=new MyNode(xmlFile);
        }
	}
    
    protected void draw(MyNode mn) //Obsolete
    {
		g.setColor(Color.white);
		g.fillRect(0,0,WID,HEI);
		X.sop("Levels: ","yellow");
		int l=mn.levels();
		X.sopln(l,"red");
		mn.printTree("yellow");
		int span=mn.getSpan();
		MyNode[] lv;
		int Y=ST;
		for(int i=1;i<=l;i++)
		{
			lv=MyNode.getLevel(mn,i);
			//drawLevel(lv,Y,span,MyNode.getLevelChains(mn,i));
			Y+=SPACE+HGT;
		}
    }
    /*private void drawLevel(MyNode[] lv,int Y,int span,int[] chain)
    {
		final int C=30,L=10;
		X.sopln(chain);
		int b=(span*(SIZE+GAP))/lv.length +C;
		BufferedImage temp;
		int K1=0,K2=0,K3=C-5;
		for(int i=0;i<lv.length;i++)
		{
			temp=getImage(lv[i].getName());
			g.drawImage(temp,b*i-offsetX,Y-offsetY,null);
			g.drawLine(b*i+SIZE/2-offsetX,Y-offsetY,b*i+SIZE/2-offsetX,Y-L-offsetY);
			g.setColor(Color.black);
			K2++;
			if(K2>=chain[K1])
			{
				g.drawLine(K3+SIZE/4-offsetX,Y-L-offsetY,K3+SIZE/4+(K2-1)*b-offsetX,Y-L-offsetY);
				g.drawLine(K3+SIZE/4+(K2-1)*b/2-offsetX,Y-L-offsetY,lastSpan*K1+C+SIZE/4-5-offsetX,Y-SPACE-offsetY);
				K3+=K2*b;
				K2=0;
				K1++;
			}
		}
		lastSpan=b;
    }*/
    public void redraw()
    {
		if(mainNode!=null)
			draw(mainNode);
    }
    public abstract void saveToFile(File fl);
    
    //Static Function
    public static BufferedImage getImage(String n)
    {
        BufferedImage ret=new BufferedImage(SIZE,HGT,BufferedImage.TYPE_INT_ARGB);
        Graphics g=ret.getGraphics();
        g.clearRect(0,0,SIZE,HGT);
        g.drawRect(1,1,SIZE-1,HGT-1);
        if(n==null)
            g.drawString("<No Name>",10,15);
        else
            g.drawString(n,10,15);
        return ret;
    }
    public static MyNode generateMainNode(Document doc)
    {
        MyNode M=null;
        Node n=doc.getChildNodes().item(0);
        if(n==null)
            return null;
        M=new MyNode(n,null);
        return M;
    }
}
