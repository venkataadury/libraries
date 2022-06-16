package draw;
import commons.X;
import java.awt.*;
import jtools.time.*;

public abstract class RefreshingFrame extends Frame
{
	protected int W,H;
	private Pulse ref=null;
	public static final int WID=800,HEI=600,FREQ=100;
	private int f=FREQ;
	
	public RefreshingFrame() {this(WID,HEI,FREQ);}
	public RefreshingFrame(int r) {this(WID,HEI,r);}
	public RefreshingFrame(int w,int h) {this(w,h,FREQ);}
	public RefreshingFrame(int w,int h,int r)
	{
		super();
		W=w; H=h;
		f=r;
		setup();
	}
	
	private void setup()
	{
		this.setSize(W,H);
		this.addWindowListener(AWT.WINPROPS);
		this.setResizable(false);
		ref=new Pulse(f)
		{
			public void objective() {refresh();}
			public Object onExit() {return null;}
		};
	}
	public void start() {ref.start();}
	
	public abstract void refresh();
}
