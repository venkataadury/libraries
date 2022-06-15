package maths.calc;
import commons.X;
import commons.FOps;
import commons.Y;
import java.io.*;

public class User
{
	public static final String ROOT=X.HOME+"/.calc/";
	private final String username;
	// public final Function[] functions
	// public final Variable[] data;
	
	static
	{
		File f=new File(ROOT);
		if(!f.exists())
		{
			if(f.mkdir())
				X.sopln("Successfully created: "+f.getPath());
			else
				X.sopln("Could not create path: "+f.getPath());
		}
		else
		{
			if(!f.isDirectory())
				X.sopln(f.getPath()+" is not a directory");
		}
	}
	public User(String un)
	{
		username=un;
		File f0=new File(ROOT+un);
		if(f0.exists())
		{
			loadData(f0);
			return;
		}
		else
			try {f0.createNewFile();} catch(Exception e) {e.printStackTrace();return;}
	}
	
	private void loadData(File fl) {}
}
