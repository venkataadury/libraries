package draw;
import commons.X;
public class UnclosedFigureException extends RuntimeException
{
	public UnclosedFigureException()
	{
		X.sopln("The contains() method was called on an unclosed figre, eg. line","red");
	}
}
