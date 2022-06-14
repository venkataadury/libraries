package commons;
//import com.mysql.jdbc.Driver;
import java.sql.*;

public class SQLs extends X
{
	public static final String url="jdbc:mysql://localhost/";
	private Connection con=null;
	private Statement stmt=null;
	private String tableName=null;
	private boolean connected=false;
	
	public SQLs()
	{
	}
	public SQLs(String dbn)
	{
		connect(dbn);
	}
	public SQLs(String dbn,String tbn)
	{
		this(dbn);
		this.setTable(tbn);
	}
	public SQLs(String dbn,boolean AC)
	{
		this();
		connect(dbn,AC);
	}
	static{
		try{
		Class.forName ("com.mysql.jdbc.Driver");}catch(Exception e) {e.printStackTrace();}
		}
		
		public void connect(String dbname) {connect(dbname,true);}
		public void connect(String dbname,boolean ac)
		{
			try{
			con=DriverManager.getConnection(url+dbname,"root","sairam"); con.setAutoCommit(ac);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			connected=true;
			con.setAutoCommit(ac);
			}catch(Exception e) {e.printStackTrace();connected=false;}
		}
		
		public void disconnect()
		{
			try{
			if(con!=null)
			{
				con.close();
				connected=false;
			}
			}catch(Exception e){e.printStackTrace();connected=true;}
		}
		
		public ResultSet runQuery(String QUERY)
		{
			String q=QUERY.toLowerCase().trim();
			if(q.startsWith("insert") || q.startsWith("update") || q.startsWith("delete"))
			{
				runUpdate(QUERY);
				return null;
			}
			if(connected)
			{
				try{return stmt.executeQuery(QUERY);}catch(Exception e) {e.printStackTrace();return null;}
			}
			else
				return null;
		}
		public void runUpdate(String QUERY)
		{
			if(connected)
			{
				try{stmt.executeUpdate(QUERY);}catch(Exception e) {e.printStackTrace();return;}
			}
		}
		public void runUpdateLeaveExceptions(String QUERY)throws SQLException
		{
			if(connected)
				stmt.executeUpdate(QUERY);
		}
		public void setTable(String tName)
		{
			tableName=tName;
		}
		public void clearTable()
		{
			tableName=null;
		}
		public String getTable()
		{
			return tableName;
		}
		public ResultSet describe()
		{
			if(tableName!=null)
				return runQuery("DESC "+tableName);
			else
				return null;
		}
		public int getLengthOfTable(String tna,int chSize)
		{
			ResultSet rs1=null,rs2=null;
			Statement stm=null;
			
			int size=0,s2,s1;
			try{
				stm=con.createStatement();
				rs1=runQuery("desc "+tna);
				while(rs1.next())
				{
					s1=rs1.getString(1).length();
					rs2=stm.executeQuery("SELECT max(length("+rs1.getString(1)+")) FROM "+tna+";");
					rs2.next();
					s2=rs2.getInt(1)*chSize;
					size+=Math.max(s1,s2);
				}
				return size*chSize+20;
				
			}catch(Exception e) {e.printStackTrace();return -1;}
		}
		public void setAutoCommit(boolean v)
		{
			try{con.setAutoCommit(v);}catch(Exception e){e.printStackTrace();}
		}
		public boolean getAutoCommit()
		{
			try{return con.getAutoCommit();}catch(Exception e) {e.printStackTrace();return false;}
		}
		public void commit()
		{
			try{con.commit();}catch(SQLException e) {e.printStackTrace();return;}
		}
		public int noOfEntries(String tna)
		{
			try{
			ResultSet rs3=runQuery("select count(*) from "+tna+";");
			rs3.next();
			return rs3.getInt(1);
			}catch(Exception e) {e.printStackTrace();return -1;}
		}
		public int getTitleLength(String tna,String cname)
		{
			try{
				ResultSet rs3=runQuery("desc "+tna);
				while(rs3.next())
				{
					if(rs3.getString(1).equalsIgnoreCase(cname.trim()))
						return rs3.getString(1).length();
				}
				return -1;
			}catch(Exception e) {e.printStackTrace();return -1;}
		}
		public int getLengthOfColumn(String tna,String cname)
		{
			try{
				ResultSet rs3=runQuery("select max(length("+cname+")) from "+tna+";");
				rs3.next();
				return Math.max(rs3.getInt(1),getTitleLength(tna,cname));
			}catch(Exception e) {e.printStackTrace();return -1;}
		}
		public int getLengthOfColumn(String tna,String cname,int cSize)
		{
			try{
				ResultSet rs3=runQuery("select max(length("+cname+")) from "+tna+";");
				rs3.next();
				return Math.max(rs3.getInt(1),getTitleLength(tna,cname))*cSize;
			}catch(Exception e) {e.printStackTrace();return -1;}
		}
		
		//Static methods in Q.java
		
}
