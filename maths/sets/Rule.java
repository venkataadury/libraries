package maths.sets;
import commons.X;
import maths.Maths;
import upgrade.ArrayFx;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

public abstract class Rule<T>
{
	public static final Rule<Integer> NATURAL=new Rule<Integer>(1) {
		public boolean isFinite() {return false;}
		public Integer getNextElement(Integer e1) {return new Integer(e1.intValue()+1);}
		public boolean contains(Integer i) {return i.intValue()>=1;}
	};
	public static final Rule<Integer> WHOLE =new Rule<Integer>(0) {
		public boolean isFinite() {return false;}
		public Integer getNextElement(Integer e1) {return new Integer(e1.intValue()+1);}
		public boolean contains(Integer i) {return i.intValue()>=1;}
	};
	public static final Rule<Integer> INTEGER=new Rule<Integer>() {
		public boolean isFinite() {return false;}
		public boolean contains(Integer i) {return true;}
		public Integer getNextElement(Integer pe)throws TooManyEntriesException {throw new TooManyEntriesException();}
	};
	public static final Rule<Double> REAL=new Rule<Double>() {
		public boolean isFinite() {return false;}
		public boolean contains(Double d) {return true;}
		public Double getNextElement(Double pe)throws TooManyEntriesException {throw new TooManyEntriesException();}
	};
	
	T firstEl;
	final Class TypeClass;
	public Rule() {this.TypeClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];}
	public Rule(T e1) {this();firstEl=e1;}
	
	public double getLength() {return 1d/0d;} // Override if isFinite.
	public boolean containsRule(Object o)
	{
		if(getElementClass().isInstance(o))
			return contains((T)o);
		else
			return false;
		return false;
	}
	public Class getElementClass() {return TypeClass;}
	public boolean isListable() {return firstEl!=null;}
	public abstract boolean contains(T t);
	public T getFirstElement() {return firstEl;}
	public T nextElement(T t)
	{
		if(contains(t))
			return getNextElement(t);
		else
			return null;
	}
	private abstract T getNextElement(T pe)throws TooManyEntriesException;
	public abstract boolean isFinite();
	public T[] getElements(int n)throws TooManyEntriesException
	{
		if(firstEl==null)
			throw new TooManyEntriesException();
		T[] ret=(T[])Array.newInstance(firstEl.getClass(),n);
		if(n==0)
			return ret;
		ret[0]=firstEl;
		for(int i=1;i<ret.length;i++)
			ret[i]=getNextElement(ret[i-1]);
		return ret;
	}
	
	public T getElement(int n)throws TooManyEntriesException
	{
		if(firstEl==null)
			throw new TooManyEntriesException();
		T el=firstEl;
		for(int i=1;i<n;i++)
			el=getNextElement(el);
		return el;
	}
}

/*class abstract NumberRule<Double> extends Rule<Double>
{
	public NumberRule() {}
	
}*/
class DefaultRule<T> extends Rule<T>
{
	public DefaultRule()
	{
		firstEl=null;
	}
	public double getLength() {return 0;}
	public boolean contains(T t) {return false;}
	public boolean isFinite() {return true;}
	public T getNextElement(T t) {return null;}
}

class TooManyEntriesException extends RuntimeException
{
	public TooManyEntriesException()
	{
		X.sopln("Too many entries to selectively use elements of set.","yellow");
	}
}
