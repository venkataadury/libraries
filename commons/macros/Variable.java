package commons.macros;
import commons.X;

public final class Variable
{
	private String vname=null,vval=null;
	
	public Variable(String n) {vname=n;}
	public Variable(String n,Object o) {vname=n; vval=o.toString();}
	
	public String getName() {return vname;}
	public String getVal() {return vval;}
	
	public void setVal(String v) {vval=v;}
}
