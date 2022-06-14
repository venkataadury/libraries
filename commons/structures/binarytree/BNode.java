package commons.structures.binarytree;
import  commons.X;
import commons.Y;
import commons.structures.Data;
import commons.structures.Process;
import java.io.File;

public class BNode
{
	Data d=null;
	public static final String EXT=".jbt"; // file extension
	public String label;
	private final int ID;
	public static int cID=0;
	BNode left=null,right=null;
	BNode parent=null;
	
	public BNode() {ID=cID++;}
	public BNode(Object o) {this();d=new Data(o);}
	public BNode(File f)throws java.io.IOException {this();this.assign(loadFromFile(f));}
	public BNode(BNode p,Object o,boolean side) {this(o);setParent(p,side);}
	
	public int getID() {return ID;}
	public Data getData() {return d;}
	public String getText() {return d.toString();}
	public BNode up() {return getParent();}
	public BNode getParent() {return parent;}
	public BNode left() {return getLeftNode();}
	public BNode getLeftNode() {return left;}
	public BNode right() {return getRightNode();}
	public BNode getRightNode() {return right;}
	public String getLabel() {if(label!=null)return label;else return "";}
	
	public void assign(BNode o)
	{
		d=o.d;
		label=o.label;
		left=o.left; right=o.right;
		parent=o.parent;
	}
	public BNode goToTop()
	{
		BNode temp=this;
		while(temp.parent!=null)
			temp=temp.parent;
		return temp;
	}
	
	public void setLabel(String l) {if(l==null) return; if(l.indexOf(':')!=-1) throw new RuntimeException("Label may not contain ':' "); label=l;}
	public void setParent(BNode par,boolean side)
	{
		this.parent=par;
		if(side)
			par.left=this;
		else
			par.right=this;
	}
	public void setData(Data da) {d=da;}
	
	public void extendRight(String l,String d)
	{
		if(!isEmpty(right))
			throw new OccupiedException();
		BNode bn=new BNode(d); bn.setLabel(l);
		bn.setParent(this,false);
	}
	public void extendLeft(String l,String d)
	{
		if(!isEmpty(left))
			throw new OccupiedException();
		BNode bn=new BNode(d); bn.setLabel(l);
		bn.setParent(this,true);
	}
	public void extendRight(String d) {extendRight(null,d);}
	public void extendLeft(String d) {extendLeft(null,d);}
	
	public void disconnectLeft() {left=null;}
	public void disconnectRight() {right=null;}
	public void autoExtend(BNode ext)
	{
		if(this.isFull())
			throw new OccupiedException();
		if(left==null)
			ext.setParent(this,true);
		else
			ext.setParent(this,false);
	}
	
	public void saveTo(File f)
	{
		String str=goToTop().getSaveString();
		java.io.FileWriter fw=null;
		java.io.BufferedWriter bw=null;
		try
		{
			fw=new java.io.FileWriter(f);
			bw=new java.io.BufferedWriter(fw);
			bw.write(str);
			bw.close();
			fw.close();
		}
		catch(java.io.IOException e) {e.printStackTrace(); return;}
	}
	public void saveTo(String s) {saveTo(new File(s));}
	public void save(String s) {saveTo(s);}
	public void save(File f) {saveTo(f);}
	
	public boolean isTerminal() {return (isEmpty(right) && isEmpty(left));}
	public boolean isFull() {return (left!=null && right!=null);}
	
	public static boolean isEmpty(BNode bn)
	{
		if(bn==null)
			return true;
		return bn.isEmptyNode();
	}
	public boolean isEmptyNode() {return false;}
	
	public String getSaveString()
	{
		if(this.isTerminal())
			return getLabel()+":"+d+"\n";
		String svstr="";
		BNode par=null;
		svstr+=getLabel()+":"+d+"\n";
		par=this;
		svstr+="-\n";
		if(isEmpty(left))
			svstr+="null\n";
		else
			svstr+=left.getSaveString();
		if(isEmpty(right))
			svstr+="null\n";
		else
			svstr+=right.getSaveString();
		svstr+="+\n";
		return svstr;
	}
	
	public static BNode loadFromFile(File f)throws java.io.IOException
	{
		BNode par=null;
		BNode temp;
		java.io.BufferedReader br=new java.io.BufferedReader(new java.io.FileReader(f));
		String str;
		str=br.readLine();
		temp=loadNode(str.trim());
		while((str=br.readLine())!=null)
		{
			str=str.trim();
			if(str.isEmpty())
				continue;
			else if(str.equals("-"))
				par=temp;
			else if(str.equals("+"))
			{
				if(par.parent!=null)
					par=par.parent;
			}
			else
			{
				if(str.equals("null"))
					temp=new EmptyBNode();
				else
					temp=loadNode(str.trim());
				par.autoExtend(temp);
			}
		}
		return par;
	}
	
	public static BNode loadNode(String str)
	{
		int ind=str.indexOf(':');
		String l=str.substring(0,ind);
		String d=str.substring(ind+1,str.length());
		BNode bn=new BNode(d); bn.setLabel(l);
		return bn;
	}
	
	public BNode findByLabel(String lbl)
	{
		BNode n=goToTop();
		return findLabel(n,lbl);
	}
	public static final BNode findLabel(BNode st,String l)
	{
		BNode n=st;
		if(n.getLabel().equals(l))
			return n;
		if(n.isTerminal())
			return null;
		BNode no=findLabel(n.left(),l);
		if(no!=null) return no;
		else
			return findLabel(n.right(),l);
	}
	
	public String toString() {return getLabel()+":"+d;}
}

class OccupiedException extends RuntimeException
{
	public OccupiedException()
	{
		X.sopln("Sorry: Node has already be connected elsewhere.","red");
	}
}
