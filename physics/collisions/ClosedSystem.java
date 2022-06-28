package physics.collisions;
import java.awt.*;
import java.awt.event.*;
import jtools.time.*;
import commons.X;
import maths.Maths;
import maths.geom3D.Point3D;
import maths.geom3D.Vector;
import maths.geom3D.Direction;
import upgrade.ArrayFx;
import java.awt.image.*;

public abstract class ClosedSystem extends java.awt.Frame
{
	int K=0;
	public BufferedImage img;
	public Color bgcol=Color.black;
	protected int width,height;
	protected Particle[] particles=new Particle[0];
	private MotionAlgorithm[] ALGs=new MotionAlgorithm[0];
	protected double range=10;
	public ClosedSystem(int w,int h) {width=w;height=h;setup();}
	
	private void setup()
	{
		this.setSize(width,height);
		this.addWindowListener(draw.AWT.WINPROPS);
		this.setVisible(true);
		img=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	}
	
	public Particle[] getParticles() {return particles;}
	void setBGColor(Color c) {bgcol=c;}
	Color getBGColor() {return bgcol;}
	
	public void addMotionAlgorithm(MotionAlgorithm ma)  {ALGs=ArrayFx.append(ALGs,ma);}
	//public MotionAlgorithm getMotionAlgorithm() {return ALG;}
	Vector randVect(){return new Vector(Math.random()-0.5,Math.random()-0.5,0);}
	void addParticle(Particle p) {particles=ArrayFx.append(particles,p);}
	public void setRange(double r) {range=r;}
	public double getRange() {return range;}
	
	public void move(double time)
	{
		for(Particle p : particles)
		{
			p.translate(time);
			boundary(p);
			for(int i=0;i<ALGs.length;i++)
			{
				if(ALGs[i]!=null)
					ALGs[i].execute(p,this,range);
			}
		}
	}
	public abstract void boundary(Particle p);
	public abstract double dist(Particle p1,Particle p2);
	
	public Particle generateParticle() {return generateParticle(100);}
	public Particle generateParticle(double vel) 
	{
		Vector v=randVect();
		v.setMag(vel);
		Particle p=new Particle(Maths.randInt(width-5)+2,Maths.randInt(height-5)+2,0,v);
		addParticle(p);
		return p;
	}
	
	public void paint(Graphics g)
	{
		final Graphics gr=img.createGraphics();
		gr.setColor(bgcol);
		gr.fillRect(0,0,width,height);
		for(Particle p : particles)
			p.draw(gr);
		g.drawImage(img,0,0,null);
	}
	
	public void update() {paint(this.getGraphics());}
}
