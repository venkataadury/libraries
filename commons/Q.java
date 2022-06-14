package commons;
import java.sql.*;

public class Q extends X
{
	private Q() {}
	
	public static final String allData="SELECT * FROM <TABLENAME>;";
	public static final String insertData="INSERT INTO <TABLENAME> VALUES (<VALS>);";
	public static final String addColumn="ALTER TABLE <TABLENAME> ADD COLUMN <COLUMNNAME> <COLUMNDEF>";
	
	public static ResultSet getAllData(SQLs sq,String table)
	{
		return sq.runQuery("select * from "+table+";");
	}
	public static boolean next(ResultSet rs)
	{
		try {return rs.next();} catch(SQLException e) {e.printStackTrace(); return false;}
	}
	public static ResultSet getAllData(SQLs sq)
	{
		if(sq.getTable()==null)
			return null;
		else
			return sq.runQuery("select * from "+sq.getTable()+";");
	}
	public static void addColumn(String dbn,String tbn,String coln,String def)
	{
		SQLs sql=new SQLs(dbn,tbn);
		sql.runQuery(addColumn.replace("<COLUMNNAME>",coln).replace("<COLUMNDEF>",def).replace("<TABLENAME>",tbn));
	}
	public static int countRows(final ResultSet rs)
	{
		try{
		int ind=rs.getRow();
		rs.last();
		int ret=rs.getRow();
		if(ind!=0)
			rs.absolute(ind);
		return ret;
		}catch(Exception e) {e.printStackTrace();return -1;}
	}
	public static String getData(ResultSet rs,int ind)
	{
		try{
			return rs.getString(ind);
		}
		catch(SQLException ex) {return null;}
		catch(Exception e){e.printStackTrace();return null;}
	}
	public static String getData(ResultSet rs,int ind,int piv)
	{
		return getData(rs,ind+piv);
	}
	public static String[][] toStringMatrix(ResultSet rs)
	{
		//String[][] ret=new String[colnum][rownum];
		try {
		rs.beforeFirst();
		int colcount=rs.getMetaData().getColumnCount();
		String[][] ret=new String[colcount][countRows(rs)];
		rs.beforeFirst();
		int K=0;
		while(next(rs))
		{
			for(int i=0;i<colcount;i++)
				ret[i][K]=rs.getString(i+1);
			K++;
		}
		return ret;
		}
		catch(SQLException e) {e.printStackTrace(); return new String[0][0];}
	}
}
