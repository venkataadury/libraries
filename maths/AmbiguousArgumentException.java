package maths;
import commons.X;

public class AmbiguousArgumentException extends RuntimeException
{
	public AmbiguousArgumentException()
	{
		X.sopln("Ambiguous Argument","yellow");
	}
	public AmbiguousArgumentException(String msg) {this(); X.sopln(msg,"red");}
}
