package commons.structures.tree;
import commons.ArrayUtils;
import commons.X;
import static maths.Maths.avg;
import org.xml.sax.SAXException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class MyNode extends Object
{
    MyNode parent;
    MyNode[] children=new MyNode[0];
    String Name;
    private final int ID;
    public static int C=0;
    public static final ArrayUtils<MyNode> AU=new ArrayUtils<>(MyNode.class);
    public static final String IGNCONST="#text";
    public static int Cou=0;
    public static double POSXNA=Math.PI;
    private double posX=POSXNA;
    
    public MyNode() {ID=C++;}
    public MyNode(MyNode p) {this();parent=p;if(p!=null)parent.addChild(this);else this.setParent(null);}
    public MyNode(String n) {this();Name=n;}
    public MyNode(String n,MyNode p) {this(p);Name=n;}
    public MyNode(Node ext,MyNode p)
    {
        this(p);
        NamedNodeMap nl=ext.getAttributes();
        if(nl!=null)
        {
            if(nl.item(0)!=null)
                Name=nl.item(0).getTextContent();
        }
        NodeList nl2=ext.getChildNodes();
        for(int i=0;i<nl2.getLength();i++)
        {
            if(nl2.item(i).getNodeName().equals(IGNCONST))
                continue;
            new MyNode(nl2.item(i),this);
        }
    }
    public MyNode(File ext)
    {
		this();
		generateData(ext);
    }
    
    public boolean isPositioned()
    {
		return (posX!=POSXNA);
	}
	public void setPos(double p)
	{
		posX=p;
	}
	public void setPosition(double p) {setPos(p);}
	public double getPos()
	{
		return posX;
	}
	public void clearPosition()
	{
		posX=POSXNA;
	}
    private void generateData(File xmlFile)
    {
		String fN=xmlFile.getPath().toLowerCase();
		if(fN.trim().endsWith(".csv"))
		{
			csvRead(xmlFile);
			return;
		}
		Document DOC;
		InputStream istream = null;
		InputSource is=null;
		try {
			istream=new FileInputStream(xmlFile.getPath());
			Reader reader = new InputStreamReader(istream,"UTF-8");
			is = new InputSource(reader);
			is.setEncoding("UTF-8");
		}catch(IOException e) {e.printStackTrace();return;}
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try 
        {
            docBuilder = dbf.newDocumentBuilder();
            DOC = docBuilder.parse(is);
        } 
        catch (ParserConfigurationException | SAXException | IOException ex) {ex.printStackTrace();return;}
        this.assign(generateMainNode(DOC));
    }
    private void csvRead(File fl)
    {
		X.sopln("Please use CSVNode class where possible","red");
		this.assign(new CSVNode(fl));
    }
    public void assign(MyNode ext)
    {
		Name=ext.Name;
		children=ext.children;
		parent=ext.parent;
    }
    
    private void setParent(MyNode mn)
    {
        parent=mn;
    }
    
    //Get Functions
    public MyNode getParent()
    {
        return parent;
    }
    public MyNode[] getChildren()
    {
        return children;
    }
    public boolean hasChildren()
    {
		return children.length!=0;
    }
    public MyNode getChild(int i)
    {
		return children[i];
	}
	public MyNode getChildAt(int i)
	{
		return getChild(i);
	}
	public MyNode getFirstChild()
	{
		if(children.length>0)
			return children[0];
		else
			return null;
	}
	public MyNode getLastChild()
	{
		if(children.length>0)
			return children[children.length-1];
		else
			return null;
	}
    public int getChildIndex(MyNode n)
    {
		for(int i=0;i<children.length;i++)
		{
			if(children[i].equals(n))
				return i;
		}
		return -1;
    }
    public boolean hasPreviousSibling()
    {
		return (getPreviousSibling()!=null);
    }
    public MyNode getPreviousSibling()
    {
		if(parent==null)
			return null;
		int S=parent.getChildIndex(this);
		if(S==0)
			return null;
		else
			return parent.getChild(S-1);
    }
    public boolean hasNextSibling()
    {
		return (getNextSibling()!=null);
    }
    public MyNode getNextSibling()
    {
		if(parent==null)
			return null;
		int S=parent.getChildIndex(this);
		if(S==parent.children.length-1)
			return null;
		else
			return parent.getChild(S+1);
    }
    public MyNode getNthParent(int n)
    {
		if(n==1)
			return parent;
		if(parent==null)
			return null;
		return parent.getNthParent(n-1);
    }
    public MyNode getLowestParentSibling()
    {
		if(this.hasPreviousSibling())
			return this.getPreviousSibling();
		if(parent==null)
			return null;
		if(parent.hasPreviousSibling())
			return parent.getPreviousSibling();
		return parent.getLowestParentSibling();
    }
    public MyNode getTopNode()
    {
		if(parent==null)
			return this;
		else
			return parent.getTopNode();
    }
    public void addChild(MyNode mn)
    {
        children=AU.append(children, mn);
        mn.setParent(this);
    }
    public void removeChild(MyNode mn)
    {
        AU.removeByValue(children, mn);
    }
    
    public boolean equals(MyNode mn)
    {
        return ID==mn.ID;
    }
    @Override
    public String toString()
    {
        return Name;
    }
    public String getName() {return getValue();}
    public String getValue() {return Name;}
    public void printNode(String col)
    {
        commons.X.sopln(Name,col);
    }
    public void printNode()
    {
        printNode("white");
    }
    public void printTree(String col)
    {
        printNode(col);
        for(MyNode mn :children)
            mn.printTree(col);
    }
    public void printTree()
    {
		printTree("white");
    }
    
    public void printLevelledTree(String col)
    {
		this.printLT(col,0);
    }
    public void printLevelledTree() {printLevelledTree("white");}
    public void printLT(String col,int l)
    {
		for(int i=0;i<l;i++)
			X.sop("\t");
		this.printNode();
		for(MyNode mn : children)
			mn.printLT(col,l+1);
    }
    
    //Data Functions
    public int levels()
    {
        if(children.length==0)
            return 1;
        int[] lv=new int[children.length];
        for(int i=0;i<children.length;i++)
            lv[i]=children[i].levels();
        return 1+max(lv);
    }
    public int getChildCount()
    {
		return children.length;
    }
    public int getSpan()
    {
        int l=levels();
        int[] sp=new int[l];
        for(int i=1;i<=l;i++)
            sp[i-1]=getLevel(this,i).length;
        return max(sp);
    }
    public int getSelfSpan()
    {
		if(children.length==0)
			return 1;
		int[] sps=new int[children.length];
		for(int i=0;i<children.length;i++)
			sps[i]=children[i].getSelfSpan();
		return maths.Maths.Sum(sps);
    }
    public double getRightSpan()
    {
		if(children.length==0)
			return 0.5;
		return this.getSelfSpan()/2.0;
		/*2,3,4
		int S=(children.length%2==0)?children.length/2:(children.length+1)/2;
		double sp=0;
		for(int i=S;i<children.length;i++)
			sp+=children[i].getSelfSpan();
		if(children.length%2!=0)
			sp+=children[S-1].getRightSpan();
		return sp;*/
		
    }
    public double getLeftSpan()
    {
		if(children.length==0)
			return 0.5;
		
		return this.getSelfSpan()/2.0;
		/*2,3,4
		int S=(children.length%2==0)?children.length/2:(children.length-1)/2;
		double sp=0;
		for(int i=0;i<S;i++)
			sp+=children[i].getSelfSpan();
		if(children.length%2!=0)
			sp+=children[S].getLeftSpan();
		return sp;*/
    }
    public double getLimitedLeftSpan()
    {
		if(children.length==0)
			return 0.5;
		return getLeftSpan()-getFirstChild().getLeftSpan();
    }
    public double getLimitedRightSpan()
    {
		if(children.length==0)
			return 0.5;
		return getRightSpan()-getLastChild().getRightSpan();
    }
    
    
    //Static functions
    public static int max(int[] vals)
    {
        if(vals.length==0)
            throw new NullPointerException("No Elements in Array");
        int max=vals[0];
        for(int i=1;i<vals.length;i++)
        {
            if(max<vals[i])
                max=vals[i];
        }
        return max;
    }
    public static MyNode[] getLevel(MyNode n,int l)
    {
        MyNode[] ret=new MyNode[0];
        if(l==1)
            return new MyNode[] {n};
        for(int i=0;i<n.children.length;i++)
            ret=AU.merge(ret,getLevel(n.children[i],l-1));
        return ret;
    }
    public static int[] getLevelChains(MyNode n,int l)
    {
		if(l==1)
			return new int[] {1};
		MyNode[] lNs=getLevel(n,l-1);
		int[] ret=new int[lNs.length];
		for(int i=0;i<ret.length;i++)
			ret[i]=lNs[i].getChildCount();
		return ret;
    }
    public static void printNodes(MyNode[] mna,String col)
    {
        AU.print(mna, col);
    }
    public static MyNode generateMainNode(Document doc)
    {
        MyNode M=null;
        Node n=doc.getChildNodes().item(0);
        if(n==null)
            return null;
        M=new MyNode(n,null);
        return M;
    }
    
    //Position Functions
    public static double getPosition(MyNode mn) //Parent Oriented: Less Bugged
    {
		final int K=0;
		if(mn.parent==null)
			return K;
		if(mn.hasPreviousSibling())
		{
			MyNode ps=mn.getPreviousSibling();
			return getPosition(ps)+ps.getRightSpan()+mn.getLeftSpan();
		}
		else
		{
			MyNode p=mn.getParent();
			return getPosition(p)-p.getLimitedLeftSpan();
		}
    }
    
    public static double getPosition2(MyNode mn) //Child Oriented: More Bugged
    {
		if(mn.isPositioned())
			return mn.posX;
		final int K=0; //Or 1
		if(mn.hasChildren())
		{
			double[] sP=new double[mn.children.length];
			for(int i=0;i<mn.children.length;i++)
				sP[i]=getPosition2(mn.getChildAt(i));
			double ps=avg(sP);
			mn.setPosition(ps);
			return ps;
		}
		if(mn.parent==null)
			return K;
		MyNode par=mn;
		int lv=1;
		do
		{
			par=par.getParent();
			lv++;
		}
		while(par.parent!=null);
		MyNode[] lV=getLevel(par,lv);
		MyNode[] lV2=getLevel(par,lv-1);
		int mI=-1;
		for(int i=0;i<lV.length;i++)
		{
			if(lV[i].equals(mn))
			{
				mI=i;
				break;
			}
		}
		if(mI==-1)
		{
			X.sopln("Error. Unable to locate node via getLevel().","red");
			return 10000;
		}
		MyNode Sib;
		if(mI>=0)
		{
			Sib=mn.getLowestParentSibling();
			if(Sib!=null)
			{
				double rS=Sib.getRightSpan();
				double pos=getPosition2(Sib)+rS+0.5;
				double pos2=0;
				if(mI==0)
				{
					//X.sopln(mn.getName()+": "+pos+"\t from sibling: "+Sib.getName()+":\tRight-Span: "+rS);
					mn.setPosition(pos);
					return pos;
				}
				pos2=getPosition2(lV[mI-1])+Math.max(lV[mI-1].getRightSpan()+mn.getLeftSpan(),getGap(lV2,lV[mI-1].getParent(),mn.getParent()));
				mn.setPosition(Math.max(pos,pos2));
				return Math.max(pos,pos2);
			}
			else
				return K;
		}
		
		/*X.sopln(mn.getName()+": "+pos+"\t from adjacent: "+lV[mI-1].getName());*/
		X.sopln("Location error","red");
		return 10000;
		
    }
    public static double getGap(MyNode[] ma,MyNode m1,MyNode m2)
    {
		int i1=-1,i2=-1;
		for(int i=0;i<ma.length;i++)
		{
			if(ma[i].equals(m1))
				i1=i;
			if(ma[i].equals(m2))
				i2=i;
		}
		if(i1==-1 || i2==-1)
			return -1;
		return Math.abs(i2-i1)-0.5;
    }
}
