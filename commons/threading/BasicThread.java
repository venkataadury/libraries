package commons.threading;
import commons.*;

public abstract class BasicThread implements Runnable
{
	protected boolean running=false;
	protected long startTime=0;
	protected Thread thread=null;
	protected void startThread()
	{
		if(thread==null)
			thread=new Thread(this);
		startTime=System.currentTimeMillis();
		thread.run();
	}
	
	public abstract void run();
	
}