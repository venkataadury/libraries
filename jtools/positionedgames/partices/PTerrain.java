package jtools.positionedgames.particle;
import commons.X;
import java.awt.*;
import maths.geom3D.*;
import upgrade.ArrayFx;
import jtools.time.*;
import static draw.RefreshingFrame.*;

public class PTerrain
{
	private final int wid,hei;
	private Particle[] particles;
	private Graphics target=null;
	public Color bgcol=Color.black;
	private Pulse PULSE;
	public int RATE=FREQ;
	
	public PTerrain() {this(WID,HEI);}
	public PTerrain(int w) {this(w,HEI);}
	public PTerrain(int w,int h)
	{
		wid=w; hei=h;
		particles=new Particle[0];
	}
	
	public void setBGCol(Color c) {bgcol=c;}
	public Color getBGCol() {return bgcol;}
	public void drawOn(Graphics g,int x,int y)
	{
		if(g==null)
			return;
		g.setColor(bgcol);
		g.fillRect(x,y,wid,hei);
		Color temp=g.getColor();
		Particle p;
		for(int i=0;i<particles.length;i++)
		{
			p=particles[i];
			if(!onScreen(p))
				continue;
			g.setColor(p.getColor());
			g.drawLine((int)p.getPos().x+x,(int)p.getPos().y+y,(int)p.getPos().x+x,(int)p.getPos().y+y);
		}
		g.setColor(temp);
	}
	
	public void setRate(int r) {RATE=r;}
	public int getRate() {return RATE;}
	public void setTargetGraphics(Graphics g) {target=g;}
	public Graphics getGraphics() {return target;}
	public void start()
	{
		PULSE=new Pulse(RATE)
		{
			public void objective() {moveParticles();drawOn(target,0,0);} // TODO: Add particle move
			public Object onExit() {X.sopln("PTerrain terminated","yellow"); return null;}
		};
		PULSE.start();
	}
	
	private void moveParticles()
	{
		Particle.move(PULSE.getFreq()/1000.0,this);
	}
	
	public boolean onScreen(Point3D pt) {return (pt.x<wid && pt.y<hei);}
	public boolean onScreen(Particle p) {return onScreen(p.getPos());}
	public void addParticle(Particle p) {p.setTerrain(this);particles=ArrayFx.append(particles,p);}
	public void removeParticle(Particle p) {particles=ArrayFx.remove(particles,p);}
}
