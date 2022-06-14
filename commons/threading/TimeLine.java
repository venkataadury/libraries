package commons.threading;
import commons.*;

public class TimeLine extends Thread
{
	private int interval=1000; //millisecs
	private TimeLine sideThread=null;
	private Object comm=new Object();
	private int cTime=0;
	Thread thread=null;
	Object targ=null;
	private int upUnits=1,unitC=0;
	private boolean running=false;
	public TimeLine(int tm)
	{
		interval=tm;
		upUnits=1;
	}
	public void startTime()
	{
		cTime=0;
		unitC=0;
		running=true;
		start();
	}
	public void start()
	{
		super.start();
	}
	@Override
	public void run()
	{
		while(true)
		{
			while(running)
			{
				X.halt(interval/1000D); //4.5 secs
				cTime++;
				unitC++;
				if(targ!=null)
				{	
					if(unitC>=upUnits)
					{
						synchronized(targ) {
						targ.notify();}
						unitC=0;
					}
				}
			}
			synchronized(comm) {try{comm.wait();}catch(InterruptedException ex) {System.err.println("Interrupted TimeLine");}}
			sideThread=null;
		}
	}
	
	public int getTime()
	{
		return cTime;
	}
	public int getInterval()
	{
		return interval;
	}
	public void resetTime()
	{
		cTime=0;
		unitC=0;
	}
	public void pauseTime()
	{
		running=false;
	}
	public void continueTime()
	{
		running=true;
		synchronized(comm) {comm.notify();}
	}
	public void setTarget(Object o)
	{
		targ=o;
	}
	public void link(Object o)
	{
		setTarget(o);
	}
	public void link(Object o,int units)
	{
		setTarget(o);
		upUnits=units;
	}
	public boolean isPaused()
	{
		return (!running || sideThread!=null);
	}
	public void flip()
	{
		if(isPaused())
			continueTime();
		else
			pauseTime();
	}
	public void endTime()
	{
		super.interrupt();
	}
	public void tempPauseTime(int millisecs)
	{
		pauseTime();
		X.halt(interval/1000D);
		sideThread=new TimeLine(millisecs);
		sideThread.link(comm);
		running=true;
		sideThread.startTime();
	}
}