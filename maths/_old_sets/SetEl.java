package maths.sets;
import commons.*;

public class SetEl<U>
{
	U val;
	
	public SetEl() {}
	public SetEl(U u) {val=u;}
	
	public void setVal(U u) {val=u;}
	public U getVal() {return val;}
}
