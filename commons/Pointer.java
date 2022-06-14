package commons;

public final class Pointer<T>
{
	private T object=null;
	
	private Pointer() {}
	public Pointer(T obj) {object=obj;}
	
	public T get() {return object;}
	public T getObject() {return get();}
	
	public void set(T obj) {object=obj;}
	public void pointTo(T obj) {set(obj);}
	public void setObject(T obj) {set(obj);}
	
	public String toString() {return "commons.Pointer: "+object;}
}
