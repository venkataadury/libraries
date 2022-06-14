package commons.macros;
import commons.X;
import commons.Pointer;

public abstract class Data<T>
{
	protected Pointer<T> data=null;
	protected String name=null;
	
	public Data() {}
	public Data(T obj) {data=new Pointer<T>(obj);}
	public Data(T obj,String n) {this(obj); name=n; }
	public Data(Pointer<T> ptr) {data=ptr;}
	public Data(Pointer<T> ptr,String n)  {this(ptr); name=n;}
	
	
	public void setName(String s) {name=s;}
	public String getName() {return name;}
	
	public T get() {return data.get();}
	public String toString() {if(data==null) return null;return data.get().toString();}
	public Class getObjectClass() {if(data==null) {return null;}return data.get().getClass();}
	
	public Pointer<T> getPointer() {return data;}
	
	public void set(T val) {data.set(val);}
	public abstract void set(String val);
	public abstract Data<T> copyAs(String name2);
	
	public boolean equals(Data d2)
	{
		if(data==null && d2.data==null)
			return true;
		if(data==null || d2.data==null)
			return false;
		return data.equals(d2.data);
	}
	public T getData() {return data.get();}
}
