package commons.structures.chain;
import commons.structures.Data;
import commons.X;

public class Bond
{
	Node n1,n2;
	public Data bDat;
	public int order=1;
	
	public Bond(Node a,Node b)
	{
		n1=a;
		n2=b;
		n1.addBond(this);
		n2.addBond(this);
	}
	public Bond(Node a,Node b,int o)
	{
		this(a,b);
		order=o;
	}
	
	
	public boolean isTerminalBond()
	{
		return (n1.isTerminal() || n2.isTerminal());
	}
	public Data destroy()
	{
		n1=n2=null;
		Data d=bDat;
		bDat=null;
		return d;
	}
	public boolean equals(Bond b2) { return (contains(b2.n1) && contains(b2.n2));}
	public Node getNode1() {return n1;}
	public Node getNode2() {return n2;}
	public boolean contains(Node n) {return (n1.equals(n) || n2.equals(n2));}
	public Node getSecond(Node n)
	{
		if(n1.equals(n))
			return n2;
		else if(n2.equals(n))
			return n1;
		return null;
	}
	
	public void setData(Data d) {bDat=d;}
	public void setData(Object o) {bDat=new Data(o);}
	public Data getData() {return bDat;}
	
	public int getOrder() {return order;}
	public void setOrder(int o) {order=o;}
	public void stepUpOrder() {order++;}
	public void stepDownOrder() {order--; if(order<1) order=1;}
}
