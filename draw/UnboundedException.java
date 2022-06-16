package draw;
import commons.X;

public class UnboundedException extends RuntimeException
{
	public UnboundedException()
	{
		X.sopln("The figure is unbounded, has no centre or bounds","red");
	}
}