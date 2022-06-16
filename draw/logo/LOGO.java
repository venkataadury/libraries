package draw.logo;
import commons.*;
import maths.*;
import maths.functions.Function;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import draw.*;
import java.util.HashMap;
import upgrade.ArrayFx;

public class LOGO extends Frame implements ActionListener //implements MouseListener
{
	// LOGO V5.0 (Interactive)
	TextField eTF=new TextField();
	Font myFont=AWT.MediumFont;
	
	private static double XAXIS=0;
	public final int xSize,ySize,dCon=8;
	private double pivotX,pivotY;
	public Color bgcol=Color.white,penColor=Color.black;
	private double posX=0,posY=0;
	private double direction=90;
	private Variable funcVar=new Variable("$$","");
	private boolean penup=false,penShown=true,ftype=true,rFx=false;//ftype->true-Void & false-Return
	//private HashMap<String,String> funcs=new HashMap<String,String>();
	private String[] recallComms=new String[0];
	private int[] recallInds=new int[0];
	private Func[] fns=new Func[0];
	private Func[] bfns=new Func[0];
	//private RetFunc[] rfxs=new RetFunc[0];
	private RetFunc[] rfxs=new RetFunc[] {new RetFunc("sqr(a)=return($a*$a)"),new RetFunc("pwr(a,b)=return($a^$b)")};
	private FileReader fr=null;
	boolean rad=false,termwrite=true;
	private int focusState=0;
	private BufferedReader br=null;
	private boolean file=false;
	public static boolean YAXIS=false;
	private Variable[] vars;
	private Variable[] tvariabs=new Variable[0];
	private Variable[] fVars=new Variable[0];
	private String[] history=new String[0];
	private List lb=new List();
	private String[] tHist=new String[0];
	String pending=null,fResult="0";
	private File folder;
	private TextArea console=new TextArea("Console\n\n");
	private Button histButt=new Button("History"),cancB=new Button("Cancel"),actButton=new Button("To Command");
	boolean written=true;
	private HashMap<String,Function> mathfunchm=new HashMap<String,Function>();
	//public Image pImg=null;
	public LOGO() {this(800,600);}
	public LOGO(boolean interactive) {this(800,600,interactive);}
	public LOGO(int w,int h,boolean... interactive)
	{
		xSize=w; ySize=h;
		pivotX=-xSize/2;pivotY=-ySize/2;
		init();
		if(interactive.length>0 && interactive[0])
		{
			this.setSize(xSize,ySize);
			eTF.setVisible(false);
			termwrite=false;
		}
	}
	
		
	private void init()
	{
		S.init();
		Sheet.initSheet(xSize,ySize,this);
		Sheet.pool[0].clearScreen();
		
		eTF.setBounds(0,ySize-60,xSize,55);
		eTF.addActionListener(this);
		eTF.setVisible(true);
		
		console.setEditable(false);
		console.setBounds(xSize+10,ySize-250,130,200);
		console.setVisible(true);
		
		lb.setBounds(xSize+10,50,120,220);
		lb.setVisible(false);
		
		cancB.setBounds(xSize+90,ySize-50,60,30);
		cancB.addActionListener(this);
		cancB.setVisible(false);
		
		histButt.setBounds(xSize+10,ySize-50,80,30);
		histButt.addActionListener(this);
		histButt.setVisible(true);
		
		actButton.setBounds(xSize+10,280,100,30);
		actButton.addActionListener(this);
		actButton.setVisible(false);
		//this.addActionListener(this);
		this.addWindowListener(AWT.WINPROPS);
		this.setSize(xSize+150,ySize);
		this.setResizable(false);
		this.setVisible(true);
		/*Graphics g=this.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0,0,xSize,ySize);*/
		X.halt(1);
		this.add(eTF);
		this.add(console);
		this.add(histButt);
		this.add(actButton);
		this.add(cancB);
		this.add(lb);
		
		//Default variables
		vars=new Variable[] {new Variable("width",""+xSize),new Variable("height",""+ySize),new Variable("PI",""+Math.PI),new Variable("E",""+Math.E)};
	}
	
	public void paint(Graphics2D g)
	{
		g.setColor(bgcol);
		g.fillRect(0,0,xSize,ySize);
		//g.drawImage(bgImg,0,0,null);
		for(Sheet s : Sheet.pool)
		{
			if(s.contains(new Rectangle((int)pivotX,(int)pivotY,xSize,ySize)))
				g.drawImage(s.getImg(),s.pivX-(int)pivotX,s.pivY-(int)pivotY,null);
			//try{ImageIO.write(s.getImg(),"png",new java.io.File("/home/pi/img"+s.pivX+"-"+s.pivY+".png"));}catch(Exception e) {e.printStackTrace(); return;}
		}
		Color bak=penColor;
		if(penColor==bgcol)
		{
			if(bgcol==Color.black)
				penColor=Color.white;
			else
				penColor=Color.black;
		}
		if(penShown)
		{
			double[] poss=getChangedPoint(posX,posY);
			Draw.drawCircle(g,new Circle(new maths.Point(poss[0]-pivotX,poss[1]-pivotY),dCon),penColor);
			g.setColor(penColor);
			//g.drawLine((int)Math.round(poss[0]-pivotX),(int)Math.round(poss[1]-pivotY),(int)(posX-pivotX+dCon*Math.cos(Math.toRadians(direction+XAXIS))),(int)(posY-pivotY-dCon*Math.sin(Math.toRadians(direction+XAXIS))));
		}
		g.setColor(penColor);
		g.drawString("Direction: "+Maths.round((rad)?Math.toRadians(direction):direction,4),xSize-175,130);
		g.drawString("Position: ("+Math.round(posX)+","+Math.round(posY)+")",xSize-175,160);
		g.drawString("Offset: ("+Math.round(pivotX)+","+Math.round(pivotY)+")",xSize-175,190);
		g.drawString("X-axis: "+Maths.round((rad)?Math.toRadians(XAXIS):XAXIS,4)+" from horizontal",xSize-175,220);
		penColor=bak;
		g.clearRect(xSize,0,150,ySize);
	}
	public void update()
	{
		if(pending!=null)
		{
			if(!pending.equals(""))
				history=append(history,pending);
			written=true;
			pending=null;
		}
		else
			written=true;
		paint((Graphics2D)this.getGraphics());
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==actButton)
		{
			if(lb.getSelectedIndex()!=-1)
				eTF.setText(lb.getSelectedItem());
			else
				return;
			lb.setVisible(false);
			cancB.setVisible(false);
			actButton.setVisible(false);
			histButt.setLabel("History");
		}
		if(e.getSource()==cancB)
		{
			lb.setVisible(false);
			cancB.setVisible(false);
			actButton.setVisible(false);
			histButt.setLabel("History");
		}
		if(e.getSource()==histButt)
		{
			if(lb.isShowing())
			{
				int selInd=lb.getSelectedIndex();
				String[] hist=history;
				if((hist.length-1)!=selInd && selInd!=-1)
				{
					reset();
					for(int i=0;i<=selInd;i++)
						execCommand(history[i]);
					history=new String[selInd+1];
					for(int i=0;i<=selInd;i++)
						history[i]=hist[i];
					pending=null;
					update();
				}
				lb.setVisible(false);
				cancB.setVisible(false);
				actButton.setVisible(false);
				histButt.setLabel("History");
			}
			else
			{
				updateList();
				lb.setVisible(true);
				lb.select(-1);
				cancB.setVisible(true);
				actButton.setVisible(true);
				histButt.setLabel("FallBack");
			}
		}
		if(e.getSource()!=eTF)
			return;
		String comm=eTF.getText().trim();
		execCommand(processCommandText(comm));
		update();
	}
	private void inFileExec(String comm)
	{
		execCommand(comm);
		tHist=ArrayFx.append(tHist,comm);
	}
	public void setBaseFolder(String f) {folder=new File(f);}
	public void setBaseFolder(File f) {folder=f;}
	
	//Interactive Part start
	public String processCommandText(String str)  {return str;}
	public void showPrompt() {eTF.setVisible(true);}
	public void hidePrompt() {eTF.setVisible(false);}
	public void showConsole() {this.setSize(xSize+150,ySize);}
	public void hideConsole() {this.setSize(xSize,ySize);}
	public void verbose() {termwrite=true;} //Allows writing each command executed onto terminal.
	public void quiet() {termwrite=false;} //No written output to terminal.
	//Interactive Part End
	
	public void execCommand(String comm)
	{
		int i1=comm.indexOf(':');
		int i2=comm.indexOf('(');
		int i3=comm.indexOf(';');
		int i4=comm.indexOf('=');
		int i5=comm.indexOf('~');
		int i6=comm.indexOf('&');
		if(!file)
		{
			if(written)
			{
				pending=comm;
				written=false;
			}
			fVars=new Variable[0];
		}
		else
			pending=null;
		if(!file && tHist.length!=0)
			tHist=new String[0];
		
		int i7=-1;
		if(termwrite)
			X.sopln(comm,"cyan");
		for(int i=0;i<comm.length();i++)
		{
			if(comm.charAt(i)=='(')
			{
				i7=Strings.getMatchingBracket(comm,'(',')',i);
				if(i<i4 && i4<i7)
					i4=-1;
				if(i<i1 && i1<i7)
					i1=-1;
				if(i<i5 && i5<i7)
					i5=-1;
			}
		}
		if(i4!=-1)
		{
			String nam=Y.cut(comm,'=',1).trim();
			if(!ftype)
			{
				RetFunc nF=new RetFunc(comm.trim());
				appendRF(nF);
				return;
			}
			Func nF=new Func(comm.trim());
			Func[] tfu=((file)?bfns:fns);
			Func[] fl2=new Func[tfu.length+1];
			int mx=-1;
			for(int i=0;i<tfu.length;i++)
			{
				if(tfu[i].matches(nF))
				{
					mx=i;
					break;
				}
				fl2[i]=tfu[i];
			}
			fl2[tfu.length]=nF;
			if(mx==-1)
				tfu=fl2;
			else
			{
				tfu[mx]=nF;
				console.append("WARN: Overwrote function: "+nF.name+"("+nF.parlist+")\n");
			}
			if(file)
				bfns=tfu;
			else
				fns=tfu;
			return;
		}
		if(i6!=-1)
		{
			int mB=Strings.getMatchingForebracket(comm,'~','&',i6);
			int mB2=Strings.getMatchingForebracket(comm,':','&',i6);
			if((mB==i5 && i5!=-1) || (mB2==i1 && i1!=-1) || i1==i5 || ((i6<i1 && i5==-1) || (i6<i5  && i1==-1) || (i6<i5 && i6<i1)))
			{
				String c2=comm.substring(i6+1);
				X.sopln(comm.substring(0,i6).trim()+"\t"+c2+"\t"+mB2+" vs "+i1,"red");
				execCommand(comm.substring(0,i6).trim());
				execCommand(c2.trim());
				return;
			}
		}
		X.sopln(comm+"\t"+i3+" vs "+i5,"green");
		if(i5!=-1 && (i3>i5 || i3==-1))
		{
			String lps=Y.cut(comm,'~',1).trim();
			String stmt=comm.substring(i5+1).trim();
			int indn=comm.indexOf('|');
			double start=getNumeric(Strings.getNumericBefore(comm,indn));
			double end=getNumeric(Y.cut(lps,'|',2).trim());
			double step=getNumeric(Y.cut(lps,'|',3).trim());
			boolean m=start>=end,n;
			if(start==end)
			{
				execCommand(stmt.replace("$$",""+start));
				return;
			}
			for(double i=start;;i+=step)
			{
				funcVar=new Variable("$$",""+i);
				execCommand(stmt.trim());
				n=(i>=end);
				if(m!=n)
					break;
			}
			eTF.setText(null);
			return;
		}
		
		if(i3!=-1 && (i3<i1 || i1==-1))
		{
			String c3=comm.substring(i3+1);
			execCommand(comm.substring(0,i3).trim());
			execCommand(c3.trim());
			return;
		}
		if(i1!=-1)
		{
			String iters=Y.cut(comm,':',1).trim();
			try
			{
				int its=(int)getNumeric(iters);
				String mCom=comm.substring(i1+1);
				for(int it=0;it<its;it++)
					execCommand(mCom);
			}
			catch(NumberFormatException ex)
			{
				String mCom=comm.substring(i1+1);
				while(getBoolean(replaceSpecVars(iters).trim()))
					execCommand(mCom);
			}
		}
		else
		{
			if(i2==-1) {execCommand(comm.trim()+"()");}
			else
			{
				String cmd=comm.substring(0,i2);
				String cmdVal=comm.substring(i2+1,Strings.getMatchingBracket(comm,'(',')',i2));
				runCommand(cmd,cmdVal);
			}	
		}
		eTF.setText(null);
	}
	public void runCommand(String cName,String cVal)
	{
		cName=cName.trim();
		if(termwrite)
		{
			X.sop(cName+"\t","red");
			X.sopln(cVal);
		}
		if(cName.equalsIgnoreCase("Exit") || cName.equalsIgnoreCase("quit"))
		{
			if(file)
			{
				//br.close();
				file=false;
				return;
			}
			this.dispose();
			System.exit(0);
		}
		if(cName.equalsIgnoreCase("justprint"))
		{
			console.append(cVal);
			return;
		}
		else if(cName.equalsIgnoreCase("justPrintln"))
		{
			console.append(cVal+"\n");
			return;
		}
			
		Sheet.setCol(penColor,this);
		java.awt.Point oPt=new java.awt.Point((int)Math.round(posX),(int)Math.round(posY));
		cVal=replaceSpecVars(cVal.trim());
		cVal=replaceAllNormVars(cVal);
		cVal=cVal.replace("$$",funcVar.getVal().trim());
		cVal=escaseSeq(cVal);
		if(cName.equalsIgnoreCase("Forward") || cName.equalsIgnoreCase("Backward") || cName.equalsIgnoreCase("Back"))
		{
			double[] oPtp=getChangedPoint(oPt);
			double[] nPtp;
			double val=getNumeric(cVal.trim());
			if(cName.equalsIgnoreCase("Forward"))
			{
				posX+=Math.cos(Math.toRadians(direction))*val;
				posY-=Math.sin(Math.toRadians(direction))*val;
			}
			else
			{
				posX-=Math.cos(Math.toRadians(direction))*val;
				posY+=Math.sin(Math.toRadians(direction))*val;
			}
			//java.awt.Point nPt=new java.awt.Point(Math.round(posX),Math.round(posY));
			nPtp=getChangedPoint(posX,posY);
			if(!penup)
				Sheet.drawLine((int)Math.round(oPtp[0]),(int)Math.round(oPtp[1]),(int)Math.round(nPtp[0]),(int)Math.round(nPtp[1]),this);
			//Draw.drawCircle(g,new Circle(new java.awt.Point((int)posX,(int)posY),7),penColor);
		}
		else if(cName.equalsIgnoreCase("Left") || cName.equalsIgnoreCase("Right"))
		{
			double val=getNumeric(cVal.trim());
			if(rad)
				val=Math.toDegrees(val);
			if(cName.equalsIgnoreCase("Left"))
				direction+=val;
			else
				direction-=val;
			direction%=360;
			if(direction<0)
				direction+=360;
		}
		else if(cName.equalsIgnoreCase("LeftDegs") || cName.equalsIgnoreCase("RightDegs"))
		{
			double val=getNumeric(cVal.trim());
			if(cName.equalsIgnoreCase("LeftDegs"))
				direction+=val;
			else
				direction-=val;
			direction%=360;
			if(direction<0)
				direction+=360;
		}
		else if(cName.equalsIgnoreCase("LeftRads") || cName.equalsIgnoreCase("RightRads"))
		{
			double val=Math.toDegrees(getNumeric(cVal.trim()));
			if(cName.equalsIgnoreCase("LeftDegs"))
				direction+=val;
			else
				direction-=val;
			direction%=360;
			if(direction<0)
				direction+=360;
		}
		else if(cName.equalsIgnoreCase("toRadians"))
		{
			rad=true;
			Maths.setRadians(rad);
		}
		else if(cName.equalsIgnoreCase("toDegrees"))
		{
			rad=false;
			Maths.setRadians(rad);
		}
		else if(cName.equals("")) {}
		else if(cName.equalsIgnoreCase("scrollX"))
			pivotX+=getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("scrollY"))
			pivotY+=getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("offsetX"))
			pivotX=getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("offsetY"))
			pivotY=getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("offset"))
		{
			double pix=getNumeric(Y.cut(cVal.trim(),',',1).trim());
			double piy=getNumeric(Y.cut(cVal.trim(),',',2).trim());
			pivotX=pix;pivotY=piy;
		}
		else if(cName.equalsIgnoreCase("Face"))
		{
			double val=getNumeric(cVal.trim());
			if(rad)
				val=Math.toDegrees(val);
			direction=val;
			direction%=360;
			if(direction<0)
				direction+=360;
		}
		else if(cName.equalsIgnoreCase("setColor"))
		{
			Color c=null;
			if(cVal.indexOf(',')==-1)
				c=S.getColor(cVal.trim());
			else
				c=new Color((int)getNumeric(Y.cut(cVal,',',1).trim()),(int)getNumeric(Y.cut(cVal,',',2).trim()),(int)getNumeric(Y.cut(cVal,',',3).trim()));
			if(c!=null)
			{
				penColor=c;
				Sheet.setCol(penColor,this);
			}
		}
		else if(cName.equalsIgnoreCase("setBackground"))
		{
			Color c=null;
			if(cVal.indexOf(',')==-1)
				c=S.getColor(cVal.trim());
			else
				c=new Color((int)getNumeric(Y.cut(cVal,',',1).trim()),(int)getNumeric(Y.cut(cVal,',',2).trim()),(int)getNumeric(Y.cut(cVal,',',3).trim()));
			if(c!=null)
			{
				bgcol=c;
				Sheet.setBgColor(bgcol,this);
				execCommand("penwrite()");
				execCommand("clear()");
			}
		}
		/*else if(cName.equals("setFontSize"))
			myFont.setFontSize((int)getNumeric(cVal));*/
		else if(cName.equalsIgnoreCase("penup"))
			penup=true;
		else if(cName.equalsIgnoreCase("pendown"))
			penup=false;
		else if(cName.equalsIgnoreCase("faceUp"))
			direction=90;
		else if(cName.equalsIgnoreCase("faceDown"))
			direction=270;
		else if(cName.equalsIgnoreCase("faceDegs"))
			direction=getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("faceRads"))
			direction=Math.toDegrees(getNumeric(cVal.trim()));
		else if(cName.equalsIgnoreCase("hidepen"))
			penShown=false;
		else if(cName.equalsIgnoreCase("showpen"))
			penShown=true;
		else if(cName.equalsIgnoreCase("clear"))
			Sheet.clear();
		else if(cName.equalsIgnoreCase("position"))
			eTF.setText("("+posX+","+posY+")");
		else if(cName.equalsIgnoreCase("wait"))
			X.halt(getNumeric(cVal.trim()));
		else if(cName.equalsIgnoreCase("rFxs"))
			ftype=false;
		else if(cName.equalsIgnoreCase("vFxs") || cName.equalsIgnoreCase("Fxs"))
			ftype=true;
		else if(cName.equalsIgnoreCase("Return") && rFx)
			fResult=""+getNumeric(cVal.trim());
		else if(cName.equalsIgnoreCase("callMathFunction") && rFx)
		{
			String p1=Y.cut(cVal,',',1),p2=Y.cut(cVal,',',2);
			fResult=""+(mathfunchm.get(p1.trim()).getVal(X.dpd(p2.trim())));
		}
		else if(cName.equalsIgnoreCase("print"))
			console.append(getOutput(cVal));
		else if(cName.equalsIgnoreCase("println"))
			console.append(getOutput(cVal)+"\n");
		else if(cName.equalsIgnoreCase("autofocus"))
			focusState=1;
		else if(cName.equalsIgnoreCase("followPen"))
			focusState=2;
		else if(cName.equalsIgnoreCase("staticScreen"))
			focusState=0;
		else if(cName.equalsIgnoreCase("rotateAxis"))
		{
			double amt=getNumeric(cVal.trim());
			if(rad)
				XAXIS+=Math.toDegrees(amt);
			else
				XAXIS+=amt;
			XAXIS%=360;
			if(XAXIS<0)
				XAXIS+=360;
			return;
		}
		else if(cName.equalsIgnoreCase("setXAxis"))
		{
			XAXIS=getNumeric(cVal.trim());
			if(rad)
				XAXIS=Math.toDegrees(XAXIS);
			XAXIS%=360;
			if(XAXIS<0)
				XAXIS+=360;
			return;
		}
		else if(cName.equalsIgnoreCase("setYAxis"))
			YAXIS=X.bpb(cVal.trim());
		else if(cName.equalsIgnoreCase("reflectBy"))
		{
			double incl=getNumeric(Y.cut(cVal.trim(),',',1));
			double cst=getNumeric(Y.cut(cVal.trim(),',',2));
			GLine leqn=new GLine(incl,cst,"inc","yi");
			Line2D l2d=(leqn).getAWTLine();
			maths.Point temp;
			X.sopln(leqn.getValue(400,300)+"\t"+leqn.getValue(-400,-300),"yellow");
			for(Sheet s : Sheet.pool)
			{
				if(l2d.intersects(s.getRect()) && s.isDrawn())
				{
					for(int i=0;i<xSize;i++)
					{
						for(int j=0;j<ySize;j++)
						{
							if(leqn.getValue(i+s.pivX,j+s.pivY)<0)
								continue;
							temp=getReflectedPoint(i+s.pivX,j+s.pivY,incl,cst);
							Sheet.swapPoint(i+s.pivX,((YAXIS)?1:-1)*(j+s.pivY),temp.x,YAXIS?temp.y:-temp.y,this);
						}
					}
					continue;
				}
				else
				{
					if(leqn.getValue(s.pivX,s.pivY)<0 || !s.isDrawn())
						continue;
					else
					{
						for(int i=0;i<xSize;i++)
						{
							for(int j=0;j<ySize;j++)
							{
								temp=getReflectedPoint(i+s.pivX,j+s.pivY,incl,cst);
								Sheet.swapPoint(i+s.pivX,j+s.pivY,temp.x,temp.y,this);
							}
						}
					}
				}
			}
		}
		else if(cName.equalsIgnoreCase("focus"))
		{
			double[] posit=getChangedPoint(posX,posY);
			pivotX=Math.round(posit[0])-xSize/2;
			pivotY=Math.round(posit[1])-ySize/2;
		}
		else if(cName.equalsIgnoreCase("penerase"))
		{
			penColor=bgcol;
			Sheet.setCol(penColor,this);
		}
		
		else if(cName.equalsIgnoreCase("penwrite"))
		{
			penColor=new Color(255-bgcol.getRed(),255-bgcol.getGreen(),255-bgcol.getBlue());
			Sheet.setCol(penColor,this);
		}
		else if(cName.equalsIgnoreCase("goto"))
		{
			double xC=getNumeric(Y.cut(cVal,',',1).trim().replace(",",""));
			double yC=getNumeric(Y.cut(cVal,',',2).trim().replace(",",""));
			double[] fPos=getChangedPoint(xC,yC);
			double[] sPos=getChangedPoint((int)Math.round(posX),(int)Math.round(posY));
			posX=xC;
			posY=yC;
			if(!penup)
				Sheet.drawLine((int)Math.round(sPos[0]),(int)Math.round(sPos[1]),(int)Math.round(fPos[0]),(int)Math.round(fPos[1]),this);
		}
		else if(cName.equalsIgnoreCase("save"))
		{
			if(cVal.indexOf(',')==-1)
				saveThis(cVal.trim(),false);
			else
				saveThis(Y.cut(cVal,',',1),X.bpb(Y.cut(cVal,',',2).trim()));
		}
		else if(cName.equalsIgnoreCase("open"))
		{
			if(folder!=null)
				cVal.replace("./",folder.getAbsolutePath());
			File op=new File(cVal.trim());
			if(!op.exists() && folder!=null)
				op=new File(folder,cVal.trim());
			boolean fstat=file;
			File tempf=folder;
			if(!op.exists()) {console.append("File: "+cVal.trim()+" does not exist.\n");}
			else
			{
				file=true;
				folder=op.getParentFile();
				try 
				{
					fr=new FileReader(op);
					br=new BufferedReader(fr);
					String cmd="";
					while((cmd=br.readLine())!=null && file)
					{
						cmd=cmd.trim();
						if(cmd=="")
							continue;
						inFileExec(cmd);
					}
				}
				catch(Exception e) {e.printStackTrace();file=false;return;}
				file=fstat;
				folder=tempf;
				if(!fstat)
					history=append(history,"open("+cVal+")");
			}
		}
		else if(cName.equalsIgnoreCase("localopen"))
		{
			if(folder!=null)
				cVal.replace("./",folder.getAbsolutePath());
			File op=new File(cVal.trim());
			if(!op.exists() && folder!=null)
				op=new File(folder,cVal.trim());
			
			if(!op.exists()) {console.append("File: "+cVal.trim()+" does not exist.\n");}
			else
			{
				try 
				{
					fr=new FileReader(op);
					br=new BufferedReader(fr);
					String cmd="";
					while((cmd=br.readLine())!=null)
					{
						cmd	=cmd.trim();
						if(cmd=="")
							continue;
						inFileExec(cmd);
					}
				}
				catch(Exception e) {e.printStackTrace();return;}
				history=append(history,"localopen("+cVal+")");
			}
		}
		else if(cName.equalsIgnoreCase("set"))
		{
			String v1=Y.cut(cVal,',',1).trim();
			String v2=Y.cut(cVal,',',2).trim();
			double v3=0;
			try {v3=Maths.stringArithmetic(v2.trim()); v2=Maths.perfect(v3);} catch(Exception e) {}
			boolean fnd=false;
			Variable[] vars=(rFx)?tvariabs:((file)?fVars:this.vars);
			for(int i=0;i<vars.length;i++)
			{
				if(vars[i].getName().equals(v1))
				{
					vars[i]=new Variable(v1,v2);
					fnd=true;
					break;
				}
			}
			if(!fnd)
			{
				if(!rFx)
				{
					if(!file)
						this.vars=appendVar(this.vars,new Variable(v1,v2));
					else
						fVars=appendVar(fVars,new Variable(v1,v2));
				}
				else
					tvariabs=appendVar(tvariabs,new Variable(v1,v2));
			}
			else
			{
				if(!rFx)
				{
					if(!file)
						this.vars=vars;
					else
						fVars=vars;
				}
				else
					this.tvariabs=vars;
			}
		}
		else if(cName.equalsIgnoreCase("recall") || cName.equals("history"))
		{
			String[] history=(file)?tHist:this.history;
			int lim1=0,lim2=0;
			cVal=cVal.trim();
			try 
			{
				lim2=X.ipi(cVal);
				if(lim2<0)
				{
					lim1=history.length+lim2;
					if(lim1<0)
						throw new RuntimeException("History not so far back");
					lim2=lim1+1;
				}
				
			} catch(Exception e) {lim1=X.ipi(Y.cut(cVal,',',1).trim());lim2=X.ipi(Y.cut(cVal,',',2).trim());}
			if(lim1<0 && lim2>=0)
			{
				lim1=history.length+lim1;
				if(lim1<0)
					throw new RuntimeException("History not so far back");
				lim2=lim1+lim2;
			}
			else if(lim1<0 && lim2<0)
			{
				lim1=history.length+lim1;
				lim2=history.length+lim2+1;
				if(lim1<0 || lim2<0)
					throw new RuntimeException("History not so far back");
				if(lim1>lim2)
				{
					int temp=lim2;
					lim2=lim1;
					lim1=temp;
				}
			}
			recallComms=ArrayFx.append(recallComms,cName+"("+cVal+")");
			recallInds=ArrayFx.append(recallInds,history.length);
			file=true;
			for(int i=lim1;i<lim2;i++)
				execCommand(history[i]);
			file=false;
		}
		else if(cName.equalsIgnoreCase("write"))
		{
			String tx=Y.cut(cVal,',',1).trim();
			double xC=getNumeric(Y.cut(cVal,',',2).trim().replace(",",""));
			double yC=getNumeric(Y.cut(cVal,',',3).trim().replace(",",""));
			Sheet.setFont(myFont);
			Sheet.drawString(tx,(int)Math.round(xC),(int)Math.round(yC),this);
		}
		else if(cName.equalsIgnoreCase("setPenState"))
		{
			String tx=Y.cut(cVal,',',1).trim();
			if(tx.equalsIgnoreCase("Up"))
				penup=true;
			else if(tx.equalsIgnoreCase("Down"))
				penup=false;
			else
				console.append("Unknown Pen-state: "+tx+"\n");
		}
		else if(cName.equalsIgnoreCase("use"))
		{
			File op=new File(cVal.trim());
			file=true;
			FileReader fr2=null;
			BufferedReader br2=null;
			if(!op.exists()) {console.append("File: "+cVal.trim()+" does not exist.\n");}
			else
			{
				try 
				{
					fr2=new FileReader(op);
					br2=new BufferedReader(fr2);
					String cmd="";
					while((cmd=br2.readLine())!=null)
					{
						cmd=cmd.trim();
						if(cmd=="")
							continue;
						execCommand(cmd);
					}
				}
				catch(Exception e) {e.printStackTrace();return;}
			}
		}
		else if(cName.equalsIgnoreCase("drawFilledRect"))
		{
			if(penup)
				return;
			if(Strings.countChar(cVal.trim(),',')==1)
			{
				execCommand("drawFilledRect("+posX+","+posY+","+cVal.trim()+")");
				return;
			}
			double xP=getNumeric(Y.cut(cVal,',',1).trim());
			double yP=getNumeric(Y.cut(cVal,',',2).trim());
			double[] ptr=getChangedPoint(xP,yP);
			double W=getNumeric(Y.cut(cVal,',',3));
			double H=getNumeric(Y.cut(cVal,',',4));
			double[] v2=getChangedPoint(xP+W,yP),v3=getChangedPoint(xP+W,yP+H),v4=getChangedPoint(xP,yP+H);
			Sheet.fillPolygon(new Polygon(new int[] {(int)Math.round(ptr[0]),(int)Math.round(v2[0]),(int)Math.round(v3[0]),(int)Math.round(v4[0])},new int[] {(int)Math.round(ptr[1]),(int)Math.round(v2[1]),(int)Math.round(v3[1]),(int)Math.round(v4[1])},4));
			/*if(Strings.countChar(cVal,',')>2)
			{
				int W=(int)getNumeric(Y.cut(cVal,',',3));
				int H=(int)getNumeric(Y.cut(cVal,',',4));
				Sheet.fillRectangle(xP,yP,W,H);
			}
			else
				Sheet.fillRectangle((int)(posX-pivotX),(int)(posY-pivotY),xP,yP);*/
			
		}
		else if(cName.equalsIgnoreCase("drawFilledCircle"))
		{
			if(penup)
				return;
			double xP=getNumeric(Y.cut(cVal,',',1).trim());
			if(Strings.countChar(cVal,',')>1)
			{
				int yP=(int)getNumeric(Y.cut(cVal,',',2));
				double rad=getNumeric(Y.cut(cVal,',',3));
				double[] ctr=getChangedPoint(xP,yP);
				Sheet.fillCircle((int)ctr[0],(int)ctr[1],rad);
			}
			else
			{
				double[] ctr=getChangedPoint(posX,posY);
				Sheet.fillCircle((int)ctr[0],(int)ctr[1],xP);
			}
		}
		else
		{
			String ts=null;
			boolean exec=false;
			Func[] af=(file)?bfns:fns;
			for(Func f : af)
			{
				ts=f.call(cName+"("+cVal+")");
				if(ts==null)
					continue;
				else
				{
					exec=true;
					execCommand(ts);
					break;
				}
			}
			if(!exec)
			{
				pending=null;
				console.append("ERR: No such function: "+cName+"("+cVal.trim()+")\n");
			}
			//if exec is 'false' , No command found
		}
		if(oPt.x!=posX || oPt.y!=posY)
		{
			if(focusState==1)
			{
				if(!(new Rectangle((int)pivotX,(int)pivotY,(int)xSize,(int)ySize).contains(posX,posY)))
				{
					pivotX=posX-xSize/2;
					pivotY=posY-ySize/2;
				}
			}
			if(focusState==2)
			{
				pivotX=posX-xSize/2;
				pivotY=posY-ySize/2;
			}
		}
	}
	
	public static String[] append(String[] stra,String str)
	{
		String[] res=new String[stra.length+1];
		for(int i=0;i<stra.length;i++)
			res[i]=stra[i];
		res[stra.length]=str;
		return res;
	}
	private String replaceSpecVars(String comm)
	{
		comm=comm.replace("@CENTRE",(pivotX+xSize/2)+","+(pivotY+ySize/2));
		comm=comm.replace("@XO",Integer.toString((int)pivotX));
		comm=comm.replace("@YO",Integer.toString((int)pivotY));
		comm=comm.replace("@X",Integer.toString((int)posX));
		comm=comm.replace("@Y",Integer.toString((int)posY));
		comm=comm.replaceAll("(?i)@PenState",(penup)?"up":"down");
		comm=comm.replaceAll("(?i)!up","down").replaceAll("(?i)!down","up");
		comm=comm.replaceAll("(?i)!true","false").replaceAll("(?i)!false","true");
		comm=comm.replace("!0","1").replace("!1","0");
		return comm;
	}
	public double getNumeric(String in)
	{
		// pwr(2,5)-1 --> 31
		in=replaceAllNormVars(in);
		int inX=in.indexOf('(');
		if(inX==-1)
			return Maths.stringArithmetic(in);
		String fN=Strings.getAlphaNumericBefore(in,inX).trim();
		if(fN.equals(""))
			throw new NumberFormatException();
		int in2=Strings.getMatchingBracket(in,'(',')',inX);
		String mtc=in.substring(inX,in2+1);
		in=in.replace(fN+mtc,getFuncVal(fN,in.substring(inX+1,in2)));
		return getNumeric(in);
		//return Maths.stringArithmetic(in);
	}
	public boolean getBoolean(String in)
	{
		in=replaceAllNormVars(in);
		int inX=in.indexOf('(');
		X.sopln("Cond: "+in,"red");
		if(inX==-1)
			return Maths.stringCondition(in);
		String fN=Strings.getAlphaNumericBefore(in,inX).trim();
		if(fN.equals(""))
			throw new NumberFormatException();
		int in2=Strings.getMatchingBracket(in,'(',')',inX);
		String mtc=in.substring(inX,in2+1);
		in=in.replace(fN+mtc,getFuncVal(fN,in.substring(inX+1,in2)));
		return getBoolean(in);
	}
	public String replaceAllNormVars(String cVal)
	{
		if(rFx)
			cVal=replaceNormVars(cVal,tvariabs);
		if(file)
			cVal=replaceNormVars(cVal,fVars);
		cVal=replaceNormVars(cVal,vars);
		return cVal;
	}
	public String replaceNormVars(String cVal,Variable[] fVars)
	{
		int cRep=1;
		while(cRep!=0)
		{
			cRep=0;
			for(Variable va : fVars)
			{
				if(cVal.indexOf("%"+va.getName())!=-1)
					cRep++;
				cVal=cVal.replace("%"+va.getName(),va.getVal().trim());
			}
		}
		return cVal;
	}
	public  String getOutput(String in)
	{
		try {return ""+Maths.perfect(Maths.round(getNumeric(in),4));}catch(Exception e) {return in.replace(""+((char)9),"(").replace(""+((char)10),")");}
	}
	public void addMathFunction(String name,Function f) {mathfunchm.put(name,f);}
	public static Variable[] appendVar(Variable[] v1,Variable v2)
	{
		Variable[] vars2=new Variable[v1.length+1];
		for(int i=0;i<v1.length;i++)
			vars2[i]=v1[i];
		vars2[v1.length]=v2;
		return vars2;
	}
	public String getFuncVal(String fNam,String par)
	{
		if(par.indexOf('(')!=-1)
			par=getOutput(par.trim()).trim();
		par=(fNam+"("+par+")");
		String tmps;
		for(RetFunc rf : rfxs)
		{
			tmps=rf.call(par.trim());
			if(tmps!=null)
			{
				rFx=true;
				try{
					execCommand(tmps.trim());
					rFx=false;
					return fResult;
				}
				catch(Throwable e) {e.printStackTrace();rFx=false;}
				rFx=false;
			}
			else
				continue;
		}
		return "0";
	}
	public void appendRF(RetFunc rfn,boolean... warn)
	{
		RetFunc rf=null;
		for(int i=0;i<rfxs.length;i++)
		{
			rf=rfxs[i];
			if(rf.matches(rfn) && warn.length>0 && warn[0])
			{
				console.append("WARN: Overwrote function: "+rf.getName()+"("+rf.parlist+")\n");
				rfxs[i]=rfn;
				return;
			}
		}
		RetFunc[] nrfa=new RetFunc[rfxs.length+1];
		for(int i=0;i<rfxs.length;i++)
			nrfa[i]=rfxs[i];
		nrfa[rfxs.length]=rfn;
		rfxs=nrfa;
	}
	private void saveThis(String loc,boolean ow)
	{
		File file=new File(loc.trim());
		if(file.exists())
		{
			if(!ow)
			{
				console.append("File: "+file+" already exists.\n");
				return;
			}
			else
			{
				console.append("Overwriting file: "+file+"\n");
				file.delete();
			}
		}
		BufferedWriter bw=null;
		try {file.createNewFile();
		bw=new BufferedWriter(new FileWriter(file));
		String s;
		for(int i=0; i<history.length;i++)
		{
			s=history[i];
			bw.write(s.trim()+"\n");
			for(int j=0;j<recallInds.length;j++)
			{
				if(recallInds[j]==i+1)
					bw.write(recallComms[j].trim()+"\n");
			}
		}
		bw.close();
		}catch(Exception e) {e.printStackTrace();return;}
		console.append("Written to: "+file);
		pending=null;
	}
	public static String escaseSeq(String in)
	{
		in=in.replace("%\\","%").replace("$\\","$");
		in=in.replace(""+((char)11),",");
		return in;
	}
	public static double[] getChangedPoint(double x,double y)
	{
		if(x==0 && y==0)
			return new double[] {0,0};
		double R=Math.sqrt(x*x+y*y);
		return new double[] {R*Math.cos(Math.acos(x/R)+Math.toRadians(XAXIS)),((YAXIS)?1:-1)*R*Math.sin(Math.asin(y/R)-Math.toRadians(XAXIS+180))};
	}
	public static double[] getChangedPoint(Point2D p2d)
	{
		return getChangedPoint(p2d.getX(),p2d.getY());
	}
	
	private void updateList()
	{
		lb.removeAll();
		for(String s : history)
			lb.add(s);
	}
	private void reset()
	{
		bgcol=Color.white;
		penColor=Color.black;
		Sheet.resetSheets(this);
		direction=90;
		rad=false;
		XAXIS=0; YAXIS=false;
		pivotX=-400; pivotY=-300;
		posX=0; posY=0;
		
	}
	
	public static maths.Point getReflectedPoint(int x1,int y1,double ang,double c)
	{
		maths.Point pt2=new maths.Point(x1,y1);
		GLine l1=new GLine(ang,c,"inc","yi");
		GLine l2=new GLine("("+x1+","+y1+")",Double.toString((ang+90.0)%180),"pt","inc");
		maths.Point mp=l1.intersect(l2);
		double xPt=2*mp.getX()-x1,yPt=2*mp.getY()-y1;
		return new maths.Point(xPt,yPt);
	}
}

