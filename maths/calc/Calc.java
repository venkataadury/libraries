package maths.calc;
import commons.X;
import commons.Y;
import maths.*;
import upgrade.ArrayFx;

public class Calc
{
	private final User u;
	private boolean echo=true;
	private String command=null;
	private Variable[] vars=new Variable[0];
	public static final String[] dataTypes =new String [] {"int,integer,","double,decimal,","complex,","vector,","point,","line,","matrix,","function,"};
	public static final Class[] dataTypeClasses =new Class[] {Integer.class,Double.class,ComplexNumber.class,maths.geom3D.Vector.class,maths.geom3D.Point3D.class,maths.geom3D.Line3D.class,maths.matrices.Matrix.class,maths.functions.Function.class};
	
	public Calc()
	{
		X.sop("Enter username: ");
		String s=null;
		try {s=X.rL().trim();} catch(java.io.IOException e) {e.printStackTrace(); u=null;return;}
		u=new User(s);
		prompt();
	}
	public Calc(String un) {u=new User(un);}
	
	private void prompt()
	{
		if(echo)
			X.sop("MATHTOOL_1.0: >>\t","yellow");
		try {command=X.rL().trim();} catch(Exception e) {e.printStackTrace();}
		if(command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit"))
			return;
		try {double d=calc(command);X.sopln(d,"green");} catch(NumberFormatException e) {eval(command,this);}
		prompt();
	}
	public static final void eval(String comm,Calc base)
	{
		if(comm.equalsIgnoreCase("toRadians"))
			Maths.setRadians(true);
		if(comm.equalsIgnoreCase("toDegrees"))
			Maths.setRadians(false);
		
		int eq=comm.indexOf('=');
		int deq=comm.indexOf("==");
		if(eq!=-1 && deq!=eq)
		{
			String targ=comm.substring(0,eq).trim();
			String src=comm.substring(eq+1);
			src=base.getFinalString(src);
			targ=base.getFinalString(targ);
			Class tcl=detect(targ),scl=detect(src);
			if(targ.indexOf(' ')==-1)
				targ=" "+targ;
			String vname;
			if(tcl==null)
				tcl=scl;
			if(tcl==null)
			{
				X.sopln("Failed to detect data type for assignment.");
				return;
			}
			else
				scl=tcl;
			vname=Y.cut(targ,' ',2).trim();
			if(isPureArithmetic(targ) || isPureArithmetic(vname))
			{
				X.sopln("---\tSorry, cannot assign to a math expression\t---","red");
				return;
			}
			X.sop("Evaluating RHS Expression as: ");
			X.sopln(scl.getName(),"red");
			Variable var=null;
			try {var=new Variable(tcl,vname);}
			catch(Exception e) {e.printStackTrace();return;}
			
			if(tcl==Double.class)
			{
				Double data=new Double(base.calc(src));
				var.setData(data);
			}
			if(tcl==Integer.class)
			{
				Integer data=new Integer((int)base.calc(src));
				var.setData(data);
			}
			if(tcl==ComplexNumber.class)
			{
				ComplexNumber cn=new ComplexNumber(base.getFinalString(src));
				var.setData(cn);
			}
			if(tcl==maths.geom3D.Vector.class)
			{
				maths.geom3D.Vector v=new maths.geom3D.Vector(base.getFinalString(src));
				var.setData(v);
			}
			if(tcl==maths.geom3D.Point3D.class)
			{
				maths.geom3D.Point3D pt=new maths.geom3D.Point3D(base.getFinalString(src));
				var.setData(pt);
			}
			if(tcl==GLine.class)
			{
				GLine gl=new GLine(base.getFinalString(src));
				var.setData(gl);
			}
			if(tcl==maths.matrices.Matrix.class)
			{
				maths.matrices.Matrix mat=new maths.matrices.Matrix(true);
				var.setData(mat);
			}
			if(tcl==maths.functions.Function.class)
			{
				maths.functions.Function fx=new maths.functions.Function(base.getFinalString(src));
				var.setData(fx);
			}
			base.addVar(var);
			/*if(targ.indexOf('(')==-1)
			{
				Object cName=identify(src);
				Class cl=getClassFor(src);
				
			}*/
		}
		else
			X.sopln(base.getFinalString(comm));
	}
	
	public boolean isDeclared(String vname)
	{
		for(Variable v : vars)
		{
			if(v.varname.equals(vname))
				return true;
		}
		return false;
	}
	public int posOfVar(String vname)
	{
		for(int i=0;i<vars.length;i++)
		{
			if(vars[i].varname.equals(vname))
				return i;
		}
		return -1;
	}
	public void addVar(Variable var)
	{
		int ind=posOfVar(var.varname);
		if(ind==-1)
			vars=ArrayFx.append(vars,var);
		else
			vars[ind]=var;
	}

	public String getFinalString(String str)
	{
		String vn;
		int ind;
		for(Variable v : vars)
		{
			vn=v.varname;
			ind=str.indexOf("$"+vn);
			if(ind+vn.length()>=str.length()-1)
			{
				str=str.replace("$"+vn,v.getData().toString());
				continue;
			}
			if(Character.isJavaIdentifierPart(str.charAt(ind+vn.length()+1)))
				continue;
			else
				str=str.replace("$"+vn,v.getData().toString());
		}
		return str;
	}
	public double calc(String str)throws NumberFormatException
	{
		str=getFinalString(str);
		return Maths.stringArithmetic(str);
	}
	
	public static final boolean isPureArithmetic(String s)
	{
		try {Maths.stringArithmetic(s.trim());return true;}
		catch(Exception e) {return false;}
	}
	
	public static final Class detect(String str)
	{
		str=str.trim().replace("  "," ");
		int brI=str.indexOf('(');
		if(brI==-1)
		{
			if(str.indexOf(' ')==-1)
				return autoDetect(str);
			else
			{
				String tmp=Y.cut(str,' ',1).toLowerCase();
				for(int i=0;i<dataTypes.length;i++)
				{
					if(dataTypes[i].indexOf(tmp+",")!=-1)
						return dataTypeClasses[i];
				}
				Class c= autoDetect(str);
				if(c==null)
					X.sopln("Warning: Unidentified data type: "+tmp,"yellow");
				return c;
			}
		}
		else //Check
			return autoDetect(str);
	}
	public static final Class autoDetect(String str)
	{
		if(isPureArithmetic(str))
			return Double.class;
		int brI=str.indexOf('(');
		if(brI==0)
			return maths.geom3D.Point3D.class;
		else if(brI>0)
			return maths.functions.Function.class;
		else
			return null;
	}
	
}
