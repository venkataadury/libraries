package commons;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class HashMapUser extends X
{
	public static boolean contains(HashMap hm,Object o)
	{
		for(Object v : hm.keySet())
		{
			if(v.equals(o))
				return true;
		}
		return false;
	}
	public static Object getValueOf(HashMap m,Object i)
	{
		for(Object v : m.keySet())
		{
			if(v.equals(i))
				return m.get(v);
		}
		throw new NullPointerException();
	}
}
