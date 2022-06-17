package jtools.time;
import java.awt.*;

public abstract class TimerGUI extends Container
{
  TimeLine tl=null;
  Label l=new Label();
  boolean running=false;
  
  public TimerGUI(int x,int y) {this.setLocation(x,y);setup();}
  public TimerGUI(int x,int y,int t) {this(x,y); setTime(t);}
  
  private void setup()
  {
	setSize(100,40);
	l.setBounds(10,10,60,20);
	l.setVisible(true);
	this.add(l);
  }
  public void setTime(int t)
  {
	tl=new TimeLine(t)
	{
	  public Object onCompletion() {return doThis();}
	  public void perIteration() {
		l.setText((tl.getTotalTime()/tl.iterationLength-getCurrentTime())+"");
	  }
	};
	tl.setDominance(true);
	l.setText(t/tl.iterationLength+"");
  }
  public void startTime()
  {
	if(!running)
	{
	  running=true;
	  tl.start();
	}
  }
  public TimeLine getTimeLine()
  {
    return tl;
  }
  public void interrupt()
  {
    tl.interrupt();
	running=false;
  }
  public void reset()
  {
	try{tl.interrupt();running=false;}catch(Exception e) {}
	setTime(tl.getTotalTime());
  }
  public void setInterval(int ms)
  {
	tl.setIterationLength(ms);
  }
  public abstract Object doThis();
}
