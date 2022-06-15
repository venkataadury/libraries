package maths;
import commons.X;

public class NoCommonLimitException extends ArithmeticException
{
	public NoCommonLimitException()
	{
		X.sopln("There is no common limit existing for the given value","red");
	}
}