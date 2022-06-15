package maths.functions;
import commons.*;

public interface Calculus extends FuncBlock
{
		public default Calculus getIntegration(char resp)throws IntegrationException {return getIntegration(resp,0);}
		public Calculus getIntegration(char resp,double con)throws IntegrationException; //con is the Integration Constant
		public default Function getIntegration(char resp,int ll,int ul)throws IntegrationException //Partial Integral
		{
			Calculus c=getIntegration(resp);
			Calculus c1=(Calculus)c.partialPut(resp,new Constant(ul));
			Calculus c2=(Calculus)c.partialPut(resp,new Constant(ll));
			return new Function(c1,c2.negate());
		}
		public default Calculus getIntegration(char resp,char[] vars,FuncBlock[] vals,double con)
		{
			Calculus c=(Calculus)this.clone();
			c.partialPut(vars,vals);
			return c.getIntegration(resp,con);
		}
		public default Calculus getIntegration(char resp,char[] vars,FuncBlock[] vals) {return getIntegration(resp,vars,vals,0);}
		public Calculus getDifferentiation(char resp,char[] otV,FuncBlock[] ndvs)throws DifferentiationException;
		public default Calculus getDifferentiation(char resp) {return (Calculus)differentiate(resp);}
		public default boolean hasCalculus() {return true;}
}
class DifferentiationException extends RuntimeException
{
	DifferentiationException()
	{
		X.sopln("Error while differentiating. Compatibility error.","yellow");
	}
	DifferentiationException(String msg)
	{
		this();
		X.sopln(msg,"red");
	}
}
class IntegrationException extends RuntimeException
{
	IntegrationException()
	{
		X.sopln("Error while integrating. Compatibility error.","yellow");
	}
	IntegrationException(String msg)
	{
		this();
		X.sopln(msg,"red");
	}
}
