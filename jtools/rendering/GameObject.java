package jtools.rendering;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.*;
import commons.X;
import maths.Maths;
import maths.geom3D.Vector;

public class GameObject
{
	public boolean rigid=true;
	public double eC=1,fC=0; //Coefficient of elasticity and Coefficient of Friction
	public double density=10; // 1 for water (relative)
	public int obID=0; // 0 - Rod ; 1-Sphere 2-Ring;
	
	public double M=1; //Mass
	public double len=1; //rad for sphere len for rod
	
	public boolean canRoll() {return (obID==1 || obID==2);}
}
