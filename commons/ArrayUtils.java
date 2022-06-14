package commons;
import java.lang.reflect.Array;

public final class ArrayUtils<V>
{
	Class cla;
	public ArrayUtils(Class<? extends V> cls) {cla=cls;}
	
	@SuppressWarnings("unchecked")
	public V[] append(V[] ia,V obj)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,ia.length+1);
		System.arraycopy(ia,0,ga,0,ia.length);
		ga[ia.length]=obj;
		return ga;
	}
	
	public void print(V[] va,String col)
	{
		if(va.length==0)
		{
			X.sopln("{}");
			return;
		}
		X.sop("{");
		String pr="";
		for(V v : va)
		{
			if(v==null)
			{
				pr+="null,";
				continue;
			}
			pr+="'"+v.toString()+"',";
		}
		X.sop(pr.substring(0,pr.length()-1),col);
		X.sopln("}");
	}
	public void print(V[] va) {print(va,"white");}
	
	public boolean contains(V[] va,V v)
	{
		for(V val : va)
		{
			if(val.equals(v))
				return true;
		}
		return false;
	}
	
	public V[] getArray(int l,V def_val)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,l);
		for(int i=0;i<ga.length;i++)
			ga[i]=def_val;
		return ga;
	}
	
	public int indexOf(V v,V[] va) //Linear Search
	{
		for(int i=0;i<va.length;i++)
		{
			if(v==va[i])
				return i;
			if(v!=null && v.equals(va[i]))
				return i;
		}
		return  -1;
	}
	
	public V[] remove(V[] va,int ind)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,va.length-1);
		System.arraycopy(va,0,ga,0,ind);
		System.arraycopy(va,ind+1,ga,ind,va.length-ind-1);
		return ga;
	}
	public V[] removeByValue(V[] va,V v)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,0);
		for(V val : va)
		{
			if(v==null)
			{
				if(val==null)
					continue;
				else
					ga=append(ga,val);
				continue;
			}
			if(v.equals(val))
				continue;
			else
				ga=append(ga,val);
		}
		return ga;
	}
	
	public V[] merge(V[] v1,V[] v2)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,v1.length+v2.length);
		System.arraycopy(v1,0,ga,0,v1.length);
		System.arraycopy(v2,0,ga,v1.length,v2.length);
		return ga;
	}
	
	public V[] insert(V[] va,V v,int ind)
	{
		V[] ga=(V[])java.lang.reflect.Array.newInstance(cla,va.length+1);
		System.arraycopy(va,0,ga,0,ind);
		ga[ind]=v;
		System.arraycopy(va,ind,ga,ind+1,va.length-ind);
		return ga;
	}
}
