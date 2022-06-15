package maths;
import java.io.*;
import commons.*;
public class Angle extends Maths
{
	public double v; //Practically int
	private boolean lock=true; //Disallow angle>360 ?
	public static final double MAX=360D;
	public static final char SYMBOL='º';
	private boolean max180=false;
	private boolean rbool=false;
	public Angle(int i)
	{
		this((double)i);
	}
	public Angle(float i)
	{
		this((double)i);
	}
	public Angle(long i)
	{
		this((double)i);
	}
	public Angle(double i)
	{
		v=i;
		lock=true;
		adjust();
	}
	public Angle(int i,boolean l)
	{
		this((double)i,l);
	}
	public Angle(float i,boolean l)
	{
		this((double)i,l);
	}
	public Angle(long i,boolean l)
	{
		this((double)i,l);
	}
	public Angle(double i,boolean l)
	{
		v=i;
		lock=l;
		adjust();
	}
	private void adjust()
	{
		if(lock)
			v%=360;
		if(rbool && (v>=180 || v<0))
			v=Math.abs(v-180);
		if(max180)
			v%=180;
		if(v<0)
		{
			v+=360;
			adjust();
		}
	}
	public void unlock()
	{ 
		lock=false;
		adjust();
	}
	public void lock()
	{
		lock=true;
		adjust();
	}
	public void set(int ang)
	{
		set((double)ang);
	}
	public void set(long ang)
	{
		set((double)ang);
	}
	public void set(float ang)
	{
		set((double)ang);
	}
	public void set(double ang)
	{
		v=ang;
		adjust();
	}
	public String displayText()
	{
		return Double.toString(v)+SYMBOL;
	}
	public void incr(int va)
	{
		incr((double)va);
	}
	public void incr(long va)
	{
		incr((double)va);
	}
	public void incr(float va)
	{
		incr((double)va);
	}
	public void incr(double va)
	{
		v+=va;
		adjust();
	}
	public void decr(int va)
	{
		decr((double)va);
	}
	public void decr(long va)
	{
		decr((double)va);
	}
	public void decr(float va)
	{
		decr((double)va);
	}
	public void decr(double va)
	{
		v-=va;
		adjust();
	}
	public void max180()
	{
		max180=!max180;
	}
	public void halveIt()
	{
		rbool=true;
	}
}
