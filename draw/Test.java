import maths.*;
import commons.X;
import java.awt.*;
import java.io.IOException;
public class Test extends Canvas
{ 
	Frame f1;
	Test()
	{
		this.setBounds(0,0,500,500);
		f1=new Frame();
		f1.add(this);
		f1.add(new Label());
		f1.setSize(500,500);
		setVisible(true);
		f1.setVisible(true);
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0,0,500,500);
		g.setColor(Color.red);
		g.drawArc(250,250,50,50,90,90);
		g.drawArc(250,300,50,50,90,90);
		g.drawArc(250,350,50,50,90,90);
	}
	public static void main(String[] args)throws IOException
	{
		Test test=new Test();
	}
}
