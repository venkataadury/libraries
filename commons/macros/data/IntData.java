package commons.macros.data;
import commons.macros.Data;
import commons.X;
import commons.Pointer;

public class IntData extends Data<Integer>
{
	Integer val=null;
	
	public IntData() {this(0);}
	public IntData(int i) {this(new Integer(i));}
	public IntData(int i,String s) {this(new Integer(i),s);}
	public IntData(Integer i) {super(i); val=i;}
	public IntData(Integer i,String s) {super(i,s); val=i;}
	public IntData(Number n) {this(new Integer(n.intValue()));}
	public IntData(Number n,String s) {this(new Integer(n.intValue()),s);}
	public IntData(Pointer<Integer> ptr) {super(ptr); val=ptr.get();}
	
	public void set(String s)
	{
		set((int)X.dpd(s));
	}
	public void set(int i) {set(new Integer(i));}
	public IntData copyAs(String n2)
	{
		IntData ret=new IntData(this.getPointer());
		ret.setName(n2);
		return ret;
	}
}
