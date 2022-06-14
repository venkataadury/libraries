package commons;
import maths.Maths;
import java.io.IOException;

public class ArrayX<V>
{
	V[] inp;
	public ArrayX()
	{
		inp=(V[])new Object[0];
	}
	public ArrayX(V[] in)
	{
		inp=in;
	}
	public ArrayX(V in)
	{
		inp=(V[])new Object[] {in};
	}
	
	public V[] getArray()
	{
		return inp;
	}
	public void append(V x)
	{
		/*V[] n=(V[]) new Object[inp.length+1];
		for(int i=0;i<inp.length;i++)
			n[i]=inp[i];
		n[inp.length]=x;
		inp=n;*/
		append(x,inp.length);
	}
	public void append(V x,int ind)
	{
		V[] n=(V[]) new Object[inp.length+1];
		for(int i=0;i<ind;i++)
			n[i]=inp[i];
		for(int i=ind;i<inp.length;i++)
			n[i+1]=inp[i];
		n[ind]=x;
		inp=n;
	}
	public void append(V[] x,int ind)
	{
		V[] n=(V[]) new Object[inp.length+x.length];
		for(int i=0;i<ind;i++)
			n[i]=inp[i];
		for(int i=ind;i<inp.length;i++)
			n[i+x.length]=inp[i];
		for(int i=0;i<x.length;i++)
			n[ind+i]=x[i];
		inp=n;
	}
	public void printArray(String col)
	{
		X.sop("[ ",col);
		for(Object o : getArray())
		{
			X.sTerm(col);
			System.out.print(o.toString()+", ");
		}
		X.sopln("]",col);
	}
	public void printArray()
	{
		printArray("white");
	}
	public void setVals(V[] x)
	{
		inp=x;
	}
	public boolean contains(V x)
	{
		for(V a : inp)
		{
			if(a==x)
				return true;
		}
		return false;
	}
	public int getLen()
	{
		return inp.length;
	}
}
