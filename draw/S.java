package draw;
import java.awt.Color;
import commons.*;
import java.util.HashMap;
import maths.Maths;
public class S
{
	public static HashMap<String,Color> ref=new HashMap<String,Color>();
	public static boolean init=false;
	public static Color red=Color.red;
	public static Color white=Color.white;
	public static Color yellow=Color.yellow;
	public static Color syellow=new Color(255,255,200);
	public static Color dyellow=new Color(237,237,9);
	public static Color green=Color.green;
	public static Color blue=Color.blue;
	public static Color dblue=Color.blue.darker();
	public static Color pblue=new Color(173,216,230);
	public static Color pred=new Color(203,82,23);
	public static Color black=Color.black;
	public static Color golden=new Color(200,200,40);
	public static Color brown=new Color(102,51,0);
	public static Color rbrown=new Color(200,1,99);
	public static Color pbrown=new Color(180,120,2);
	public static Color dbrown=new Color(82,41,10);
	public static Color pgreen=new Color(200,255,200);
	public static Color dgreen=new Color(0,175,0);
	public static Color gblue=new Color(48,223,255);
	public static Color orange=Color.orange;
	public static Color purple=new Color(53,16,53);
	public static Color ppurple=new Color(196,145,196);
	public static Color pink=Color.pink;
	public static Color ppink=new Color(255,170,185);
	public static Color dpink=new Color(220,70,145);
	public static Color colourless=new Color(140,140,140);
	public static Color grey=new Color(68,68,68);
	public static Color silver=new Color(192,192,192);
	public static Color pwhite=new Color(235,235,235);
	public static Color curdy=new Color(235,240,245);
	public static final Color table=new Color(127,127,127);
	public static final Color whibl=new Color(225,240,255);
	public static final Color trasp=new Color(0,true);

	public static void init()
	{
		ref.put("red",red);
		ref.put("pale_red",pred);
		ref.put("brown",brown);
		ref.put("deep_blue",dblue);
		ref.put("yellow",yellow);
		ref.put("deep_yellow",dyellow);
		ref.put("white",white);
		ref.put("black",black);
		ref.put("gold",golden);
		ref.put("blue_white",whibl);
		ref.put("white_blue",whibl);
		ref.put("pale_blue",pblue);
		ref.put("curdy_white",curdy);
		ref.put("reddish_brown",rbrown);
		ref.put("blue",blue);
		ref.put("green",green);
		ref.put("purple",purple);
		ref.put("orange",orange);
		ref.put("pale_purple",ppurple);
		ref.put("pale_brown",pbrown);
		ref.put("pale_green",pgreen);
		ref.put("deep_green",dgreen);
		ref.put("colorless",colourless);
		ref.put("colourless",colourless);
		ref.put("slight_yellow",syellow);
		ref.put("silver",silver);
		ref.put("deep_pink",dpink);
		ref.put("pink",pink);
		ref.put("pale_white",pwhite);
		ref.put("pale_pink",ppink);
		ref.put("green_blue",gblue);
		ref.put("grey",grey);
		ref.put("gray",grey);
		init=true;
	}
	public static Color getC(String cname)
	{
		if(!init)
			X.sopln("Warning: Not initialized","red");
		cname=cname.trim();
		if(cname.equals(""))
			cname="colorless";
		Color c=ref.get(cname.toLowerCase());
		if(c==null)
			X.sopln("NULL (Color)","red");
		return c;
	}
	public static Color getColor(String cname)
	{
		return getC(cname);
	}
	public static Color mix(Color[] clrs)
	{
		int r=0,g=0,b=0;
		for(Color clr : clrs)
		{
			r+=clr.getRed();
			g+=clr.getGreen();
			b+=clr.getBlue();
		}
		r/=clrs.length;
		g/=clrs.length;
		b/=clrs.length;
		return new Color(r,g,b);
	}
	public static Color mix(Color[] clrs,double[] wts)
	{
		double r=0,g=0,b=0;
		for(int i=0;i<clrs.length;i++)
		{
			if(wts[i]<=0)
				continue;
			r+=clrs[i].getRed()*wts[i];
			g+=clrs[i].getGreen()*wts[i];
			b+=clrs[i].getBlue()*wts[i];
		}
		double fwt=0;
		for(double x : wts) {if(x>0) fwt+=x;}
		r/=fwt;
		g/=fwt;
		b/=fwt;
		return new Color((int)r,(int)g,(int)b);
	}
	public static void sop(java.awt.Point pt,String col)
	{
		X.sop("(",col);
		X.sop(pt.x+","+pt.y,col);
		X.sop(")",col);
	}
	public static void sopln(java.awt.Point pt,String col)
	{
		sop(pt,col);
		X.sopln();
	}
	public static void sop(java.awt.Point pt)
	{
		sop(pt,"white");
	}
	public static void sopln(java.awt.Point pt)
	{
		sopln(pt,"white");
	}
	public static void sop(maths.Point pt,String col)
	{
		sop(Maths.switchPoint(pt),col);
	}
	public static void sop(maths.Point pt)
	{
		sop(Maths.switchPoint(pt),"white");
	}
	public static void sopln(maths.Point pt,String col)
	{
		sopln(Maths.switchPoint(pt),col);
	}
	public static void sopln(maths.Point pt)
	{
		sopln(Maths.switchPoint(pt),"white");
	}
}
