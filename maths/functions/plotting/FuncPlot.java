package maths.functions.plotting;
import maths.functions.Function;
import maths.Maths;
import commons.X;
import draw.logo.LOGO;
import draw.logo.RetFunc;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Point;
import draw.BasicMouseListener;

public class FuncPlot extends LOGO implements BasicMouseListener
{
	private HashMap<Function,Color> funcs;
	public static final Color defaultPlotColor=Color.red;
	public double scaleX=1,scaleY=1;
	
	public FuncPlot() {this(800,600);}
	public FuncPlot(int x,int y) {this(x,y,new Function[0],new Color[0]);}
	public FuncPlot(int x,int y,Function[] fxs,Color... clrs)
	{
		super(x,y,true);
		this.addMouseListener(this);
		generateHM(fxs,clrs);
	}
	
	private void generateHM(Function[] fxs,Color[] clrs)
	{
		funcs=new HashMap<Function,Color>();
		for(int i=0;i<fxs.length;i++)
		{
			if(clrs.length<=i)
				funcs.put(fxs[i],defaultPlotColor);
			else
				funcs.put(fxs[i],clrs[i]);
		}
	}
	
	public void plotGraphs(int st,int en)
	{
		int[] rng=evalRange(st,en);
		setup(st,en,rng[0],rng[1]);
		Color c;
		for(Function f : funcs.keySet())
		{
			c=funcs.get(f);
			this.execCommand("setColor("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
			plotGraphOf(f,st,en);
		}
	}
	private void plotGraphOf(Function f,int st,int en)
	{
		this.addMathFunction("f",f);
		this.execCommand("rFxs()");
		RetFunc RF=new RetFunc("f",f);
		super.appendRF(RF,false);
		this.execCommand(""+st+"|"+en+"|"+1.0/scaleX+"~ goto($$*"+scaleX+",-f($$))");
		this.execCommand("penup(); goto(0,0); pendown()");
	}
	private void setup(int xmin,int xmax,int ymin,int ymax)
	{
		this.execCommand("clear()");
		scaleX=xSize/(double)(xmax-xmin);
		scaleY=((double)ySize)/(ymax-ymin);
		Color c;
		HashMap<Function,Color> hm2=new HashMap<Function,Color>();
		for(Function f : funcs.keySet())
		{
			c=funcs.get(f);
			hm2.put(new Function(f.MUL(scaleY)),c);
		}
		funcs=hm2;
		this.execCommand("setColor(black)");
		int pD=(int)(ymax*scaleY),pXD=(int)(xmax*scaleX);
		this.execCommand("penup(); goto(0,-"+pD+"); face(-90); pendown(); forward(%height)");
		this.execCommand("penup(); goto(0,0); face(0); pendown(); forward("+pXD+"); back(%width); penup(); goto(0,0); pendown()");
		this.execCommand("offsetX(-10); offsetY(-%height+30)");
	}
	public int[] evalRange(int xS,int xE)
	{
		if(funcs.size()<=0)
			return new int[] {0,0};
		int min=(int)((Function)(funcs.keySet().toArray()[0])).getVal(xS),max=(int)((Function)funcs.keySet().toArray()[0]).getVal(xS);
		double tmp;
		for(Function f: funcs.keySet())
		{
			for(int i=xS;i<=xE;i++)
			{
				tmp=f.getVal(i);
				if(tmp<min)
					min=(int)tmp;
				if(tmp>max)
					max=(int)tmp;
			}
		}
		return new int[] {min,max};
	}
	
	public void update() {super.update();}
	
	public void mouseClicked(java.awt.event.MouseEvent e) {X.sopln("Clicked"); update();}
}
