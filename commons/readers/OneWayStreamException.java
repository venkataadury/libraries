package commons.readers;
import commons.X;

public class OneWayStreamException extends RuntimeException
{
	public OneWayStreamException() 
	{
		X.sopln("Error: Trying to navigate backwards in a one-way stream","red");
	}
}
