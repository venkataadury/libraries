package maths.calc;
import commons.X;
import maths.*;
import java.lang.reflect.*;

public class Variable<C> 
{
	C data=null;
	Class<C> cls;
	public final String varname;
	
	public Variable(Class<C> cl,String vn)throws InstantiationException,IllegalAccessException
	{
		cls=cl; varname=vn;
	}
	public Variable(C in,String vn) {varname=vn;cls=(Class<C>)in.getClass();data=in;}
	
	public C getData() {return data;}
	public Class<C> getClassData() {return cls;}
	public void setData(C dat) {data=dat;}
}
