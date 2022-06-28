package physics.optics;
import java.awt.Color;

public interface Drawable
{
	public void draw(java.awt.Graphics g,int offset,int yL,double eO);
	public void draw(java.awt.Graphics g,int offset,int yL);
	public void draw(java.awt.image.BufferedImage img,int offset,int yL,double eO);
	public Color getColor(); public void setColor(Color c);
}
