package commons;

public abstract class AbstractFunction<T,R>
{
	public AbstractFunction() {}
	public abstract R doThis();
	public abstract R doThis(T ob);
}
