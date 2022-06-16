package draw;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public interface BasicMouseListener extends MouseListener
{
	public default void mouseExited(MouseEvent e) {}
	public default void mouseEntered(MouseEvent e) {}
	public default void mousePressed(MouseEvent e) {}
	public default void mouseReleased(MouseEvent e) {}
}
