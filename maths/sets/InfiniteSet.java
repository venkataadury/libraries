package maths.sets;
import commons.X;
import maths.Maths;
import upgrade.ArrayFx;

public class InfiniteSet<T> extends ESet<T>
{
	public static final int CAP=50;
	Rule<T> rule=new DefaultRule<T>();
	public InfiniteSet() {}
	public InfiniteSet(Rule r) {rule=r;}
	
	public boolean contains(Object t) {return rule.containsRule(t);}
	public boolean isListable() {return rule.isListable();}
	public boolean isFinite() {return rule.isFinite();}
	public double getLength() {return rule.getLength();}
	public T[] getElements()throws TooManyEntriesException {return getElements(CAP);}
	public T[] getElements(int n)throws TooManyEntriesException {return rule.getElements(n);}
	public T getElement(int i)throws TooManyEntriesException {return rule.getElement(i);}
	
	
	public FiniteSet intersection(FiniteSet fs) {return fs.intersection(this);}
	public ESet intersection(ESet es)
	{
		if(es instanceof FiniteSet)
			return intersection((FiniteSet)es);
		else
			return intersectionRule(this,es);
	}
	
	public static final InfiniteSet<T> intersectionRule(ESet s1,ESet s2)
	{
		Rule<T> rule=new Rule<T>() {
			ESet set1=s1,set2=s2;
			boolean ext=(set1.isListable() || set2.isListable());
			public boolean contains(T t) {return (set1.contains(t) && set2.contains(t));}
			public boolean isListable() {return ext;}
			public T getNextElement(T t)
			{
				try 
				{
					if(sel1.isListable())
					{
						Object o=sel1.getFirstElement();
						
					}
				}
				catch(StackOverflowError soe) {return null;}
			}
		};
		return new InfiniteSet(rule);
	}
}
