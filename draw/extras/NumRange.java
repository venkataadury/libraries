package draw.extras;
import commons.X;
import java.awt.*;
import java.awt.event.*;

public final class NumRange extends Pads implements InputPads,ActionListener
{
	public final Button bP=new Button("+"),bM=new Button("-");
	public static final int mW=100,mH=30;
	int VAL=0;
	private int jumpValue=1,minValue=0,maxValue=100;
	private final Label l=new Label(Integer.toString(VAL));
	
	public NumRange()
	{
		this(0);
	}
	public NumRange(int inV)
	{
		this(inV,1,0,0);
	}
	public NumRange(int inV,int jV)
	{
		this(inV,jV,0,0);
	}
	public NumRange(int inV,int jV,int sV,boolean mm)
	{
		this(inV,jV,sV,sV);
		if(mm) //Min Value set
			maxValue=0;
		else
			minValue=0;
	}
	public NumRange(int inV,int jV,int minV,int maxV)
	{
		W=mW; H=mH;
		VAL=inV; minValue=minV; maxValue=maxV;
		jumpValue=jV;
		l.setText(Integer.toString(VAL));
		this.setDims(mW,mH);
		setObjects();
		this.setVisible(false);
	}
	
	private void setObjects()
	{
		bP.setBounds(0,0,30,30);
		bP.setVisible(true);
		this.add(bP);
		bM.setBounds(70,0,30,30);
		bM.setVisible(true);
		this.add(bM);
		l.setBounds(30,0,40,30);
		l.setVisible(true);
		this.add(l);
	}
	public void addAutoActionListener()
	{
		bP.addActionListener(this);
		bM.addActionListener(this);
	}
	public void addActionListener(ActionListener ac)
	{
		bP.addActionListener(ac);
		bM.addActionListener(ac);
	}
	public Component[] getComponents()
	{
		return new Component[] {bP,l,bM};
	}
	public Button[] getButtons()
	{
		return new Button[] {bP,bM};
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==bP)
			VAL+=jumpValue;
		else if(e.getSource()==bM)
			VAL-=jumpValue;
		if(VAL<minValue)
			VAL+=jumpValue;
		if(VAL>maxValue)
			VAL-=jumpValue;
		updateLabel();
	}
	private final void updateLabel()
	{
		l.setText(Integer.toString(VAL));
	}
	public void setJumpValue(int jV)
	{
		jumpValue=jV;
	}
	public void setMinValue(int mv)
	{
		minValue=mv;
	}
	public void setMaxValue(int mv)
	{
		maxValue=mv;
	}
	public void setRange(int miv,int mav)
	{
		minValue=miv; maxValue=mav;
	}
	public int getValue()
	{
		return VAL;
	}
}
