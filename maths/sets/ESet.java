package maths.sets;
import commons.X;
import java.lang.reflect.ParameterizedType;

abstract class ESet<T>
{
	public abstract boolean isFinite();
	Class TypeClass;
	
	public ESet() 
	{
		this.TypeClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public abstract boolean contains(Object t)throws TooManyEntriesException;
	public abstract double getLength();
	public abstract T[] getElements()throws TooManyEntriesException;
	public abstract T[] getElements(int n)throws ArrayIndexOutOfBoundsException,TooManyEntriesException; //Get first n elements
	public abstract T getElement(int i)throws ArrayIndexOutOfBoundsException; //Get i'th element
	public Class getElementClass() {return TypeClass;}
	public abstract boolean isListable();
	
	public abstract ESet intersection(ESet es);
	/*public abstract ESet union(ESet es);
	public abstract ESet minus(ESet es);*/
}
