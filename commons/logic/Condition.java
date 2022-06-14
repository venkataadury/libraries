package commons.logic;
import commons.X;
import upgrade.ArrayFx;

public abstract class Condition<T>
{
	Condition[] conds=new Condition[0];
	public boolean rule; //Whether this Condition and the rest are linked as "and" or "or".
	public static boolean AND=true,OR=false;
	
	public boolean isSatisfied(T obj)
	{
		if(rule==AND && !this.satisfies(obj))
			return false;
		if(rule==OR && this.satisfies(obj))
			return true;
		boolean r;
		for(Condition c : conds)
		{
			r=c.isSatisfied(obj);
			if(rule==AND && !r)
				return false;
			if(rule==OR && r)
				return true;
		}
		return (rule==AND);
	}
	public abstract boolean satisfies(T obj);
	
	public boolean getRule() {return rule;}
	public boolean setRule(boolean r) {return rule=r;} 
	public void addCondition(Condition c) {conds=ArrayFx.append(conds,c);}
}
