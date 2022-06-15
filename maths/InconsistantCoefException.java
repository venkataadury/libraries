package maths;
import commons.X;
public class InconsistantCoefException extends RuntimeException
{
        public InconsistantCoefException()
        {
                X.sopln("The Operatable value has a varying coefficient","red");
        }
	public InconsistantCoefException(String msg)
        {
            X.sopln(msg,"red");
        }
}
