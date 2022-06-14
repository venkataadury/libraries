package commons.macros;
import commons.X;
import upgrade.ArrayFx;

public class DataSet
{
	Data[] myData=new Data[0];
	
	public DataSet() {}
	public DataSet(Data[] da) {myData=da;}
	public DataSet(Data d) {myData=new Data[] {d};}
	
	public Data[] getAllData() {return myData;}
	public void append(Data d) {myData=ArrayFx.append(myData,d);}
	public void remove(Data d) {myData=ArrayFx.remove(myData,d);}
	
	public Data get(String vn)
	{
		for(Data d : myData)
		{
			if(d.getName()==null)
			{
				X.sopln("WARNING: Null Data:\t"+d.toString(),"red");
				continue;
			}
			if(d.getName().equals(vn))
				return d;
		}
		throw new DataVariableNotFoundException(vn);
	}
	public String getDataString(String n)
	{
		for(Data d : myData)
		{
			if(d.getName().equals(n))
				return d.toString();
		}
		return "";
	}
	
	public void set(String dName,String dVal)
	{
		for(Data d : myData)
		{
			if(d.getName()==null)
			{
				X.sopln("WARNING: Null Data:\t"+d.toString(),"red");
				continue;
			}
			if(d.getName().equals(dName))
			{
				d.set(dVal);
				return;
			}
		}
		throw new DataVariableNotFoundException();
	}
	
	public static final DataSet merge(DataSet d1,DataSet d2)
	{
		if(d1==null)
			return d2;
		if(d2==null)
			return d1;
		Data[] dt1=d1.myData;
		Data[] dt2=d2.myData;
		return new DataSet((Data[])ArrayFx.join((Data[])dt1,(Data[])dt2));
	}
	
	public void showData()
	{
		for(Data d : myData)
		{
			X.sop(d.getName()+":\t","yellow");
			X.sopln(d.get().toString(),"red");
		}
	}
	
	public boolean containsVar(String varname)
	{
		for(Data d : myData)
		{
			if(d.getName()==null)
				continue;
			if(d.getName().equals(varname))
				return true;
		}
		return false;
	}
}

class DataVariableNotFoundException extends RuntimeException
{
	public DataVariableNotFoundException()
	{
		X.sopln("Data variable not found in macro.","red");
	}
	public DataVariableNotFoundException(String vname)
	{
		this();
		X.sopln("Variable name: "+vname);
	}
	
}
