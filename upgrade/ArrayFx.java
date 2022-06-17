package upgrade;

import commons.X;
import java.lang.reflect.Array;
import java.util.HashMap;

public class ArrayFx extends commons.ArrayFx
{

	/*public static<T> T[] createArray(int len,String msg)
	{

	}*/
	public static<T> boolean contains(T[] t,T sobj)
	{
		for(T obj : t)
		{
			if(obj.equals(sobj))
				return true;
		}
		return false;
	}
	public static<T> T[] append(T[] t,T obj)
	{
		T[] ret=(T[])Array.newInstance(t.getClass().getComponentType(), t.length+1);
		for(int i=0;i<t.length;i++)
			ret[i]=t[i];
		ret[t.length]=obj;
		return (T[])ret;
	}
	public static<T> HashMap<T,Integer> count(T[] data)
	{
		HashMap<T,Integer> hm=new HashMap<T,Integer>();
		for(T t : data)
		{
			if(hm.containsKey(t))
				hm.replace(t,hm.get(t)+1);
			else
				hm.put(t,1);
		}
		return hm;
	}
	public static<T> T[] remove(T[] t,T obj)
	{
		int loc=Lsearch(t,obj)[0];
		if(loc==-1)
			return t;
		return remove(t,loc);
	}
	public static<T> T[] remove(T[] t,int ind)throws ArrayIndexOutOfBoundsException
	{
		T[] ret=(T[])Array.newInstance(t.getClass().getComponentType(), t.length-1);
		if(ind>=t.length)
			throw new ArrayIndexOutOfBoundsException();
		for(int i=0;i<ind;i++)
			ret[i]=t[i];
		for(int i=ind+1;i<t.length;i++)
			ret[i-1]=t[i];
		return ret;
	}
	public static<T> T[] removeAll(T[] t,T obj)
	{
		int loc=Lsearch(t,obj)[0];
                if(loc==-1)
                        return t;
                t=remove(t,loc);
		return removeAll(t,obj);
	}

	public static<T> int[] Lsearch(T[] t,T obj)
	{
		int[] locs=new int[0];
		boolean flag=false;
		for(int i=0;i<t.length;i++)
		{
			if((t[i]==null && obj==null) || t[i].equals(obj))
			{
				locs=commons.ArrayFx.append(locs,i);
				flag=true;
			}
		}
		if(flag)
			return locs;
		else
			return new int[] {-1};
	}

	public static<T> void printArray(T[] arr,String col)
	{
		for(T t : arr)
			X.sopln(t.toString(),"col");
	}
	public static<T> void printArray(T[] arr) {printArray(arr,"white");}
	public static<T> void printArrayAsSet(T[] arr,String col)
	{
		if(arr.length==0) {X.sopln("{}");return;}
		X.sop("{");
		X.sTerm(col);
		for(T t : arr)
			X.sop(t.toString()+",",col);
		X.sop("\b");
		X.sTerm("white");
		X.sopln("}");
	}
	public static<T> void printArrayAsSet(T[] arr) {printArrayAsSet(arr,"white");}

	public static<T> T[] swap(T[] arr,int i1,int i2)
	{
		T temp=arr[i1];
		arr[i1]=arr[i2];
		arr[i2]=temp;
		return arr;
	}

	public static<T extends Comparable> T min(T[] t,int sI) //sI=> Starting index
	{
		T min=t[sI];
		for(int i=sI;i<t.length;i++)
		{
			if(t[i].compareTo(min)<0)
			{
				min=t[i];
				continue;
			}
		}
		return min;
	}
	public static<T extends Comparable> T max(T[] t,int sI) //sI=> Starting index
	{
		T max=t[sI];
		for(int i=sI;i<t.length;i++)
		{
			if(t[i].compareTo(max)>0)
			{
				max=t[i];
				continue;
			}
		}
		return max;
	}

	public static<T extends Comparable> int findMin(T[] t,int sI) //sI=> Starting index
	{
		T min=t[sI];
		int ind=sI;
		for(int i=sI;i<t.length;i++)
		{
			if(t[i].compareTo(min)<0)
			{
				min=t[i];
				ind=i;
				continue;
			}
		}
		return ind;
	}
	public static<T extends Comparable> int findMax(T[] t,int sI) //sI=> Starting index
	{
		T max=t[sI];
		int ind=sI;
		for(int i=sI;i<t.length;i++)
		{
			if(t[i].compareTo(max)>0)
			{
				max=t[i];
				ind=i;
				continue;
			}
		}
		return ind;
	}
	public static<T extends Comparable> T[] selectionSort(T[] t)
	{
		T min;
		int ind;
		for(int i=0;i<t.length;i++)
		{
			min=t[i];
			ind=i;
			for(int j=i+1;j<t.length;j++)
			{
				if(t[j].compareTo(min)<0)
				{
					min=t[j];
					ind=j;
					continue;
				}
			}
			t=swap(t,i,ind);
		}
		return t;
	}
	public static<T extends Comparable> T[] bubbleSort(T[] t)
	{
		int sC=0;
		int iC=0;
		for(int i=t.length;i>0;i--)
		{
			for(int j=0;j<i-1;j++)
				if(t[j].compareTo(t[j+1])>0) {t=swap(t,j,j+1); sC++;}
			if(sC==0)
				break;
			iC++;
		}
		return t;
	}

	public static<T> String joinToString(T[] t,char delim)
	{
		String str="";
		for(int i=0;i<t.length;i++)
			str+=t[i].toString()+delim;
		return str;
	}
	public static<T> T[] join(T[] t1,T[] t2)
	{
		T[] ret=(T[])Array.newInstance(t1.getClass().getComponentType(), t1.length+t2.length);
		for(int i=0;i<t1.length;i++)
			ret[i]=t1[i];
		for(int i=t1.length;i<ret.length;i++)
			ret[i]=t2[i-t1.length];
		return ret;
	}

	public static<T> String serialize(T[] t,char delim)
	{
		String ret=" ";
		for(T obj : t)
			ret+=obj.toString()+delim;
		return ret.substring(0,ret.length()-1).trim();
	}
	public static<T> String serialize(T[] t) {return serialize(t,',');}

	public static<T> T selectRandom(T[] t)
	{
		int rn=(int)(Math.random()*t.length);
		return t[rn];
	}
	public static<T> T[] schuffle(T[] t)
	{
		T[] ret=(T[])Array.newInstance(t.getClass().getComponentType(), t.length);
		T[] u=t;
		int K=0;
		while(u.length!=0)
		{
			int rn=(int)(Math.random()*u.length);
			ret[K++]=u[rn];
			u=remove(u,rn);
		}
		return ret;
	}
}
