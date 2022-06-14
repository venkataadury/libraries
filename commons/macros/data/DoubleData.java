package commons.macros.data;
import commons.macros.Data;
import commons.Pointer;

public class DoubleData extends Data<Double>
{
	Double val=null;
	
	public DoubleData() {this(0);}
	public DoubleData(double i) {this(new Double(i));}
	public DoubleData(Double i) {super(i); val=i;}
	public DoubleData(Number n) {this(new Double(n.doubleValue()));}
	public DoubleData(Pointer<Double> ptr) {super(ptr);val=ptr.get();}
	
	public void set(String s)
	{
		set(new Double(s));
	}
	public void set(double d) {set(new Double(d));}
	public DoubleData copyAs(String n2)
	{
		DoubleData ret=new DoubleData(this.getPointer());
		ret.setName(n2);
		return ret;
	}
}
