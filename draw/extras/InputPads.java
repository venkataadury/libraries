package draw.extras;
import commons.X;
import java.awt.*;
import java.awt.event.*;
public interface InputPads
{
	public void addActionListener(ActionListener ac);
	public Component[] getComponents();
	public Button[] getButtons();
}
