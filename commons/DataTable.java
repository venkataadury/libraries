package commons;
import java.util.HashMap;
import java.util.Map;

public class DataTable<K,V> extends HashMap 
{
	
	public DataTable(Map<? extends K,? extends V> m) {super(m);}
	public DataTable(K[] keys,V[] vals,int l)
	{
		super();
		for(int i=0;i<l;i++)
			this.put(keys[i],vals[i]);
	}
	
}