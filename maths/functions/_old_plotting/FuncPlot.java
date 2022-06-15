package maths.functions.plotting;
import maths.Maths;
import maths.functions.*;
import commons.X;
import draw.AWT;
import draw.GraphFrame;
import java.awt.*;
import java.util.HashMap;

public class FuncPlot extends GraphFrame
{
	public static final int WIDTH=800,HEIGHT=600;
	private Color bgcol=Color.black,fgcol=Color.white,defcol=Color.red;
	HashMap<Function,Integer[]> funcs=new HashMap<Function,Integer[]>();
	HashMap<Function,Color> colors=new HashMap<Function,Color>();
	HashMap<Function,Double> steps=new HashMap<Function,Double>();
	
	private int XA=HEIGHT/2,YA=0;
	private Scale sc=null;
	double[][] funcVals;
	public FuncPlot()
	{
		super(WIDTH,HEIGHT);
		//this.setResizable(false);
		this.setVisible(true);
	}
	
	public void addPlot(Function f,int st,int en,Color c)
	{
		Integer[] a1=new Integer[] {st,en};
		funcs.put(f,a1);
		colors.put(f,c);
	}
	public void addPlot(Function f,int[] ste,Color c) {addPlot(f,ste[0],ste[1],c);}
	public void addPlot(Function f,Integer[] ste,Color c) {addPlot(f,ste[0].intValue(),ste[1].intValue(),c);}
	public void addPlot(Function f,int st,int en) {addPlot(f,st,en,defcol);}
	public void addPlot(Function f,int[] ste) {addPlot(f,ste,defcol);}
	public void addPlot(Function f,Integer[] ste) {addPlot(f,ste,defcol);}
	public void autopaint(Graphics g)
	{
		if(g==null)
		{
			X.sopln("NULL","red");
			return;
		}
		g.setColor(bgcol);
		g.fillRect(0,0,WIDTH,HEIGHT);
		scaleAndFix(funcs);
		g.setColor(fgcol);
		
		plot(g);
		/*for(Function f : funcs)
			plotFunction(f);*/
	}
	private void plot(Graphics g)
	{
		if(funcs==null)
			return;
		int L=funcs.size(),i=0;
		double[] pt=new double[] {0,0};
		double stV;
		double cFX=WIDTH/(double)sc.getXRange(),cFY=HEIGHT/(double)sc.getYRange();
		/*
		 *  xRange -> WIDTH
		 * pt[0] -> ?
		 * ?WIDTH
		 */
		X.halt(1);
		g.drawLine(0,XA,WIDTH,XA);
		g.drawLine(YA,0,YA,HEIGHT);
		g.setColor(Color.red);
		g.drawLine(400,300,0,0);
		/*for(Function f : colors.keySet())
		{
			//X.sopln(f);
			stV=steps.get(f);
			X.sopln(stV,"red");
			pt=new double[] {funcs.get(f)[0].intValue(),funcVals[0][i]};
			g.setColor(colors.get(f));
			for(int j=0;j<WIDTH;j++)
			{
				pt[0]+=stV;
				//if (((int)((pt[0]*cFX)))==((int)pt[0]-stV))
				//	continue;
				g.drawLine((int)(pt[0]*cFX)+YA,(int)(pt[1]*cFY)+XA,(int)((pt[0]+stV)*cFX+YA),(int)(funcVals[j][i]*cFY)+XA);
				pt[1]=funcVals[j][i];
				X.sopln(((int)(pt[0]*cFX)+YA)+","+((int)(pt[1]*cFY)+XA)+","+((int)((pt[0]+stV)*cFX+YA))+","+((int)(funcVals[j][i]*cFY)+XA));
			}
		}*/
	}
	
	public void update() {X.sopln("new update"); autopaint(this.getGraphics()); super.update();}
	
	private final void scaleAndFix(HashMap<Function,Integer[]> hm)
	{
		if(funcs==null)
			return;
		int lbX=0,ubX=0,K=0,L=0;
		int tlbX=0,tubX=10,tm;
		double tlbY=0,tubY=0;
		double stv,tv;
		Integer[] temp;
		funcVals=new double[WIDTH+1][hm.size()];
		steps=new HashMap<Function,Double>();
		
		for(Function f : hm.keySet())
		{
			temp=hm.get(f);
			tlbX=temp[0].intValue(); tubX=temp[1].intValue();
			if(tlbX<lbX)
				lbX=tlbX;
			if(tubX>ubX)
				ubX=tubX;
		}
		
		for(Function f : hm.keySet())
		{
			temp=hm.get(f);
			stv=(double)(temp[1].intValue()-temp[0].intValue())/WIDTH;
			steps.put(f,new Double(stv));
			L=0;
			for(double d=tlbX;d<tubX;d+=stv)
			{
				tv=f.getVal(d);
				funcVals[L++][K]=tv;
				if(tv<tlbY)
					tlbY=tv;
				if(tv>tubY)
					tubY=tv;
			}
			K++;
		}
		sc=new Scale(tlbX,tubX,(int)tlbY,(int)tubY);
		XA=sc.getXAxis(HEIGHT);
		YA=sc.getYAxis(WIDTH);
		//X.sopln();
	}
}
