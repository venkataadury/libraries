package commons.macros;
import commons.X;
import maths.Maths;

public abstract class MacroFunction
{
	public final String name;
	public MacroFunction(String n) {name=n;}
	
	public String getName() {return name;}
	
	public abstract String exec(Macro src,String params);
}
