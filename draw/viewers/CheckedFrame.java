package draw.viewers;
import java.awt.*;
import java.io.IOException;
import draw.AWT;

public class CheckedFrame extends Frame
{
	private final int SIZE;
	public int W,H;
	public Color bgcol=Color.black;
	
	
	public CheckedFrame(int w,int h)
	{
		W=w; H=h;
		SIZE=10;
		setup();
	}
	public CheckedFrame(int w,int h,int size)
	{
		W=w; H=h;
		SIZE=size;
		setup();
	}
	
	private void setup()
	{
		this.setSize(W,H);
		this.addWindowListener(AWT.WINPROPS);
	}
}
