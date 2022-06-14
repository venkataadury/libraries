package commons.structures;
import commons.X;

public class Data
{
	public Object data="";
	
	public Data() {}
	public Data(Object o) {data=o;}
	public Data(String str) {data=str;}
	
	public Object process(Process p) {return p.process(this);}
	public double process(maths.functions.Function fx) {return fx.getVal(getNumber());}
	public double getNumber() {return X.dpd(data.toString());}
	
	public String toString() {return data.toString();}
	public Object getData() {return data;}
}
