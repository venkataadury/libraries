package maths.sets;
import commons.X;
import maths.Maths;
import upgrade.ArrayFx;
import java.lang.reflect.Array;

public class FiniteSet<T> extends ESet<T>
{
	T[] els=(T[])(new Object[0]);
	public FiniteSet() {els=(T[])(new Object[0]);}
	public FiniteSet(T t) {els=(T[])Array.newInstance(t.getClass(),1); els[0]=t;}
	public FiniteSet(T[] t) {els=t;}
	
	public boolean isFinite() {return true;}
	public double getLength() {return els.length;}
	public T[] getElements() {return els;}
	public T[] getElements(int n)throws ArrayIndexOutOfBoundsException
	{
		if(n>getLength())
			throw new ArrayIndexOutOfBoundsException();
		T[] ret=(T[])Array.newInstance(els.getClass().getComponentType(),n);
		for(int i=0;i<n;i++)
			ret[i]=els[i];
		return ret;
	}
	public T getElement(int i)throws ArrayIndexOutOfBoundsException
	{
		if(getLength()<i)
			throw new ArrayIndexOutOfBoundsException();
		return els[i];
	}
	
	public boolean contains(Object t)
	{
		for(T obj : els)
		{
			if(obj==null)
			{
				if(t==null)
					return true;
				continue;
			}
			if(obj.equals(t))
				return true;
		}
		return false;
	}
	public boolean isListable() {return true;}
	
	public FiniteSet intersection(FiniteSet fs)
	{
		Class HC=X.getCommonResultclass(fs.getElementClass(),getElementClass());
		Object[] ret=new Object[0];
		for(Object t : fs.getElements())
		{
			if(t==null)
				continue;
			if(this.contains(t))
				ret=ArrayFx.append(ret,t);
		}
		return new FiniteSet<>(Array.newInstance(HC,1).getClass().cast(ret));
	}
	public FiniteSet intersection(InfiniteSet is)
	{
		Class HC=X.getCommonResultclass(is.getElementClass(),getElementClass());
		Object[] ret=new Object[0];
		for(Object t : getElements())
		{
			if(t==null)
				continue;
			if(is.contains(t))
				ret=ArrayFx.append(ret,t);
		}
		return new FiniteSet<>(Array.newInstance(HC,1).getClass().cast(ret));
	}
	
	public FiniteSet intersection(ESet es)
	{
		if(es instanceof FiniteSet)
			return intersection((FiniteSet)es);
		else
			return intersection((InfiniteSet)es);
	}
	/*public FiniteSet<T> union(FiniteSet<? extends T> fs)
	{
		T[] ret=(T[])(new Object[0]);
		for(T t : fs.getElements())
		{
			if(t==null)
				continue;
			if(!ArrayFx.contains(ret,t))
				ret=ArrayFx.append(ret,t);
		}
		return new FiniteSet<T>(ret);	
	}
	public FiniteSet<T> minus(FiniteSet<? extends T> fs)
	{
		
	}*/
}
