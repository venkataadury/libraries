package draw.extras;
import java.awt.*;
import java.awt.event.*;
import commons.X;
import maths.Maths;

public class Numpad extends Pads implements InputPads
{
	private final Button[] theButtons;
	private final boolean incZero;
	public final int bWid,bHei;
	public Numpad()
	{
		this(300,400);
	}
	public Numpad(int sd)
	{
		this(sd,sd);
	}
	public Numpad(int w,int h)
	{
		super.setDims(w,h);
		incZero=true;
		bWid=w/3;
		bHei=h/4;
		theButtons=new Button[10];
		setAllButtons();
		commonMethod();
	}
	public Numpad(boolean v)
	{
		this(300,400,v);
	}
	public Numpad(int sd,boolean v)
	{
		this(sd,sd,v);
	}
	public Numpad(int w,int h,boolean v)
	{
		incZero=v;
		this.setSize(w,h);
		if(v)
		{
			bWid=w/3;
			bHei=h/4;
			theButtons=new Button[10];
			setAllButtons();
		}
		else
		{
			bWid=w/3;
			bHei=h/3;
			theButtons=new Button[9];
			setNonzeroButtons();
		}
		commonMethod();
	}
	
	private void setAllButtons()
	{
		int n=1,xc=0,yc=0;
		for(int i=0;i<theButtons.length;i++)
		{
			if(n==0)
				break;
			theButtons[i]=new Button(Integer.toString(n++));
			if(n==10)
				n=0;
			theButtons[i].setBounds(xc,yc,bWid,bHei);
			theButtons[i].setVisible(true);
			this.add(theButtons[i]);
			xc+=bWid;
			if(xc==3*bWid)
			{
				xc=0;
				yc+=bHei;
			}
		}
		theButtons[9]=new Button("0");
		theButtons[9].setBounds(bWid,3*bHei,bWid,bHei);
		theButtons[9].setVisible(true);
		this.add(theButtons[9]);
	}
	private void setNonzeroButtons()
	{
		int xc=0,yc=0;
		for(int i=0;i<theButtons.length;i++)
		{
			theButtons[i]=new Button(Integer.toString((i++)+1));
			theButtons[i].setBounds(xc,yc,bWid,bHei);
			theButtons[i].setVisible(true);
			this.add(theButtons[i]);
			xc+=bWid;
			if(xc==3*bWid)
			{
				xc=0;
				yc+=bHei;
			}
		}
	}
	private void commonMethod()
	{
		this.setVisible(false);
	}
	public void addActionListener(ActionListener ac)
	{
		for(Button b : theButtons)
			b.addActionListener(ac);
	}
	public Component[] getComponents()
	{
		return theButtons;
	}
	public Button[] getButtons()
	{
		return theButtons;
	}
	public Button getButtonFor(int nu)throws ZeroNotIncludedException
	{
		if(nu>theButtons.length)
			return null;
		if(nu==0)
		{
			if(incZero)
				return theButtons[9];
			else
			 	throw new ZeroNotIncludedException();
		}
		return theButtons[nu-1];
	}
	public boolean isValue(Object o,int x)
	{
		return (o==getButtonFor(x));
	}
}
class ZeroNotIncludedException extends RuntimeException
{
	ZeroNotIncludedException()
	{
		X.sopln("Zero not included in numpad","Red");
	}
}
