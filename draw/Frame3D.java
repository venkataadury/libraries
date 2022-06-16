package draw;
import commons.X;
import java.awt.*;
import maths.Maths;
import maths.geom3D.*;

public class Frame3D extends Frame
{
	protected Graphics3D g3d;
	public Frame3D() {this(800,600);}
	public Frame3D(int w,int h) {super();super.setSize(w,h);super.addWindowListener(AWT.WINPROPS);g3d=new Graphics3D(w,h);setup();}
	
	private void setup()
	{
		g3d.setLocation(0,0);
		this.add(g3d);
		X.halt(0.75);
	}
	
	public void setVisible(boolean b)
	{
		super.setVisible(b);
		if(b)
			g3d.drawInit();
	}
	public Graphics3D getGraphics3D() {return g3d;}
	public void setGraphics3D(Graphics3D g) {g3d=g;setup();}
}
