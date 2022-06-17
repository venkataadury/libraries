package jtools.time;

public abstract class Pulse extends Thread
{
  protected int freq=1000; //ms (Time b/w simultaneous loops)
  public Object res=null;
  private int iterNum=0;
  private boolean running=false;
  
  public Pulse() {}
  public Pulse(int f) {freq=f;}
  
  public abstract void objective();
  public abstract Object onExit(); 
  
  public void run()
  {
	running=true;
	while(running)
	{
	  try{Thread.sleep(freq);}catch(InterruptedException e) {break;}
	  objective();
	  iterNum++;
	}
	res=onExit();
	return;
  }
  
  public int getRunningTime()
  {
	return freq*iterNum; //Milliseconds (Accurate upto one iteration time)
  
  }
  public int getFreq() {return freq;}
  public void start()
  {
	super.start();
  }
  public void halt() {running=false;}
  
}
