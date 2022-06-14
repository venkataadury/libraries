package commons.macros.data;
import commons.macros.Data;
import commons.X;
import commons.Pointer;

public class StringData extends Data<String>
{
	String val=null;
	
	public StringData() {this("");}
	public StringData(String i) {super(i); val=i;}
	public StringData(String i,String s) {super(i,s); val=i;}
	public StringData(Pointer<String> ptr) {super(ptr); val=ptr.get();}
	
	public void set(String s)
	{
		data.set(s);
	}
	public StringData copyAs(String n2)
	{
		StringData ret=new StringData(this.getPointer());
		ret.setName(n2);
		return ret;
	}
}
