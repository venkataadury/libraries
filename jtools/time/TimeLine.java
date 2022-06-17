package jtools.time;
import java.io.IOException;

public abstract class TimeLine extends Thread
{
  private int time=0; //ms
  private int T=0;
  public int iterationLength=1000;
  boolean dom;
  private Object not=null;
  public static TimeLine[] tls=new TimeLine[0];
  
  public TimeLine(int mst) {time=mst; tls=append(tls,this);}
  public TimeLine(int mst,Object n) {this(mst); not=n;}
  
  public abstract Object onCompletion();
  
  public void setDominance(boolean b)
  {
	dom=b;
  }
  public void start()
  {
	if(dom)
	{
	  for(TimeLine tl : tls)
		tl.interrupt();
	}
	super.start();
  }
  public void run()
  {
	T=0;
	int T2=time/iterationLength;
	while(T<T2)
	{
	  try{Thread.sleep(iterationLength); perIteration();} catch(Exception e) {return;}
	  T++;
	}
	if(not!=null)
	{
	synchronized(not)
	{
	  not.notify();
	  
	}
	}
	onCompletion();
  }
  public int getTotalTime()
  {
	return time;
  }
  public int getCurrentTime()
  {
	return T;
  }
  public abstract void perIteration();
  
  public static TimeLine[] append(TimeLine[] tla,TimeLine tl)
  {
	TimeLine[] tle=new TimeLine[tla.length+1];
	for(int i=0;i<tla.length;i++)
	  tle[i]=tla[i];
	tle[tla.length]=tl;
	return tle;
  }
  public void setIterationLength(int ms)
  {
	iterationLength=ms;
  }
  public static final void interruptAll()
  {
	for(TimeLine tl : tls)
	  tl.interrupt();
  }
}
