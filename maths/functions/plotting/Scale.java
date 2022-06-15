package maths.functions.plotting;
import maths.functions.Function;
import maths.Maths;
import commons.X;
import draw.S;
import draw.logo.LOGO;
import draw.logo.RetFunc;
import java.awt.image.*;
import java.awt.Image;

public class Scale
{
	boolean horiz=false; //horizonal or vertical
	public final int w,h;
	BufferedImage img;
	public Scale() {this(true,800);}
	public Scale(int w) {this(true,w);}
	public Scale(boolean h,int wi)
	{
		horiz=h;
		w=(h)?wi:30;
		this.h=(h)?30:wi;
		setup();
	}
	private void setup()
	{
		img=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		img.setBackground(S.trasp);
		
	}
}
