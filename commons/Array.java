package commons;
import java.io.*;
public class Array extends ArrayFx
{
	int len;
	int[] intarray; float[] floatarray; double[] doublearray; long[] longarray; char[] chararray; String[] stringarray;
	char type;
	int num=0;
	boolean sorted=false;
	String typeS="";
	String callM="";
	public static void main(String args[])
	{
		X.sopln("Sorry, this is not to be run directly.");
	}
	Array(char types,String msg,int length)throws IOException
	{
		switch(types)
		{
			case 'i':
				len=length;
				intarray=arrayInt(len,msg);
				type='i';
				num++;
				callM="Int";
				break;
			case 'l':
				len=length;
				longarray=arrayLong(len,msg);
				type='l';
				num++;
				callM="Long";
				break;
			case 'f':
				len=length;
				floatarray=arrayFloat(len,msg);
				type='f';
				num++;
				callM="Float";
				break;
			case 'd':
				len=length;
				doublearray=arrayDouble(len,msg);
				type='d';
				num++;
				callM="Double";
				break;
			case 'c':
				len=length;
				chararray=arrayChar(len,msg);
				type='c';
				num++;
				callM="Char";
				break;
			case 's':
				len=length;
				stringarray=arrayStr(len,msg);
				type='s';
				num++;
				callM="String";
				break;
			default:
				X.sepln("Error. Option wrong?\nUse i,l,f,d,c, or s");
				return;
		}
		settype();
	}
	Array(int[] a)
	{
		len=a.length;
		intarray=a;
		type='i';
		num++;
		typeS="int";
	}
	Array(float[] a)
	{
		len=a.length;
		floatarray=a;
		type='f';
		num++;
		typeS="float";
	}
	Array(long[] a)
	{
		len=a.length;
		longarray=a;
		type='l';
		num++;
		typeS="long";
	}
	Array(double[] a)
	{
		len=a.length;
		doublearray=a;
		type='d';
		num++;
		typeS="double";
	}
	Array(char[] a)
	{
		len=a.length;
		chararray=a;
		type='c';
		num++;
		typeS="char";
	}
	Array(String[] a)
	{
		len=a.length;
		stringarray=a;
		type='s';
		num++;
		typeS="string";
	}
	void print()
	{
		switch(type)
		{
			case 'i':
				printArray(intarray);
				break;
			case 'l':
				printArray(longarray);
				break;
			case 'f':
				printArray(floatarray);
				break;
			case 'd':
				printArray(doublearray);
				break;
			case 'c':
				printArray(chararray);
				break;
			case 's':
				printArray(stringarray);
				break;
			default:
				X.sepln("Error. No array created?");
				return;
		}
	}
	int[] search(int val)
	{
		int[] ans=new int[1];
		if(type!='i')
			X.sepln("Invalid data type 'int' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(intarray,val);
			else
				ans=Lsearch(intarray,val);
		}
		return ans;
	}
	int[] search(float val)
	{
		int[] ans=new int[1];
		if(type!='f')
			X.sepln("Invalid data type 'float' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(floatarray,val);
			else
				ans=Lsearch(floatarray,val);
		}
		return ans;
	}
	int[] search(long val)
	{
		int[] ans=new int[1];
		if(type!='l')
			X.sepln("Invalid data type 'long' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(longarray,val);
			else
				ans=Lsearch(longarray,val);
		}
		return ans;
	}
	int[] search(double val)
	{
		int[] ans=new int[1];
		if(type!='d')
			X.sepln("Invalid data type 'double' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(doublearray,val);
			else
				ans=Lsearch(doublearray,val);
		}
		return ans;
	}
	int[] search(char val)
	{
		int[] ans=new int[1];
		if(type!='c')
			X.sepln("Invalid data type 'char' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(chararray,val);
			else
				ans=Lsearch(chararray,val);
		}
		return ans;
	}
	int[] search(String val)
	{
		int[] ans=new int[1];
		if(type!='s')
			X.sepln("Invalid data type 'string' to be searched in array of type: '"+typeS+"'");
		else
		{
			if(sorted)
				ans[0]=Bsearch(stringarray,val);
			else
				ans=Lsearch(stringarray,val);
		}
		return ans;
	}
	void sort()
	{
		switch(type)
		{
			case 'i':
				intarray=Ssort(intarray);
				break;
			case 'l':
				longarray=Ssort(longarray);
				break;
			case 'f':
				floatarray=Ssort(floatarray);
				break;
			case 'd':
				doublearray=Ssort(doublearray);
				break;
			case 'c':
				chararray=Ssort(chararray);
				break;
			case 's':
				stringarray=Ssort(stringarray);
				break;
			default:
				X.sepln("Error. No array created?");
				return;
		}
		sorted=true;
	}
	void swapPc(int a,int b)
	{
		if(a!=b)
		{
			switch(type)
			{
				case 'i':
					intarray=swap(intarray,a,b);
					break;
				case 'l':
					longarray=swap(longarray,a,b);
					break;
				case 'f':
					floatarray=swap(floatarray,a,b);
					break;
				case 'd':
					doublearray=swap(doublearray,a,b);
					break;
				case 'c':
					chararray=swap(chararray,a,b);
					break;
				case 's':
					stringarray=swap(stringarray,a,b);
					break;
				default:
					X.sepln("Error. No array created?");
					return;
			}
		sorted=false;
		}
	}
	void eq(int ps,int val)
	{
		if(type!='i')
			X.sepln("Invalid data type 'int' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				intarray[ps]=val;
			sorted=false;
		}
	}
	void eq(int ps,long val)
	{
		if(type!='l')
			X.sepln("Invalid data type 'long' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				longarray[ps]=val;
			sorted=false;
		}
	}
	void eq(int ps,float val)
	{
		if(type!='f')
			X.sepln("Invalid data type 'float' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				floatarray[ps]=val;
			sorted=false;
		}
	}
	void eq(int ps,double val)
	{
		if(type!='d')
			X.sepln("Invalid data type 'double' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				doublearray[ps]=val;
			sorted=false;
		}
	}
	void eq(int ps,char val)
	{
		if(type!='c')
			X.sepln("Invalid data type 'char' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				chararray[ps]=val;
			sorted=false;
		}
	}
	void eq(int ps,String val)
	{
		if(type!='s')
			X.sepln("Invalid data type 'string' to be assigned in array of type: '"+typeS+"'");
		else
		{
			if(ps<=len)
				stringarray[ps]=val;
			sorted=false;
		}
	}
	char getType()
	{
		return type;
	}
	int[] getArrayInt()
	{
		if(type!='i')
			X.sepln("Invalid data type 'int' to be returned in array of type: '"+typeS+"'");
		else
			return intarray;
		return fail;
	}
	long[] getArrayLong()
	{
		if(type!='l')
			X.sepln("Invalid data type 'long' to be returned in array of type: '"+typeS+"'");
		else
			return longarray;
		return failL;
	}
	float[] getArrayFloat()
	{
		if(type!='f')
			X.sepln("Invalid data type 'float' to be returned in array of type: '"+typeS+"'");
		else
			return floatarray;
		return failF;
	}
	double[] getArrayDouble()
	{
		if(type!='d')
			X.sepln("Invalid data type 'double' to be returned in array of type: '"+typeS+"'");
		else
			return doublearray;
		return failD;
	}
	char[] getArrayChar()
	{
		if(type!='c')
			X.sepln("Invalid data type 'char' to be returned in array of type: '"+typeS+"'");
		else
			return chararray;
		return failC;
	}
	String[] getArrayString()
	{
		if(type!='i')
			X.sepln("Invalid data type 'string' to be returned in array of type: '"+typeS+"'");
		else
			return stringarray;
		return failS;
	}
	void settype()
	{
		switch(type)
		{
			case 'i':
				typeS="int";
				break;
			case 'l':
				typeS="long";
				break;
			case 'f':
				typeS="float";
				break;
			case 'd':
				typeS="double";
				break;
			case 'c':
				typeS="char";
				break;
			case 's':
				typeS="string";
				break;
			default:
				X.sepln("Error. No array created?");
				return;
		}
	}
	void split(String st,char ch)
	{
		switch(type)
		{
			case 'i':
				intarray=splitInt(st,ch);
				break;
			case 'l':
				longarray=splitLong(st,ch);
				break;
			case 'f':
				floatarray=splitFloat(st,ch);
				break;
			case 'd':
				doublearray=splitDouble(st,ch);
				break;
			case 'c':
				chararray=splitChar(st,ch);
				break;
			case 's':
				stringarray=splitString(st,ch);
				break;
			default:
				X.sepln("Error. No array created?");
				return;
		}
	}
	String join(char type2,char c)
	{
		switch(type2)
		{
			case 'i':
				return ArrayFx.join(intarray,c);
			case 'l':
				return ArrayFx.join(longarray,c);
			case 'f':
				return ArrayFx.join(floatarray,c);
			case 'd':
				return ArrayFx.join(doublearray,c);
			case 'c':
				return ArrayFx.join(chararray,c);
			case 's':
				return ArrayFx.join(stringarray,c);
			default:
				X.sepln("Error. No array created?");
				return "";
		}
	}
	int[] toInt()
	{
		switch(type)
		{
			case 'i':
				return intarray;
			case 'l':
				LongConv ob1=new LongConv(longarray);
				return ob1.toInt();
			case 'f':
				FloatConv ob2=new FloatConv(floatarray);
				return ob2.toInt();
			case 'd':
				DoubleConv ob3=new DoubleConv(doublearray);
				return ob3.toInt();
			case 'c':
				CharConv ob4=new CharConv(chararray);
				return ob4.toInt();
			case 's':
				StringConv ob5=new StringConv(stringarray);
				return ob5.toInt();
			default:
				X.sepln("Error. No array created?");
				return fail;
		}
	}
	float[] toFloat()
	{
		switch(type)
		{
			case 'i':
				IntConv ob=new IntConv(intarray);
				return ob.toFloat();
			case 'l':
				LongConv ob1=new LongConv(longarray);
				return ob1.toFloat();
			case 'f':
				return floatarray;
			case 'd':
				DoubleConv ob3=new DoubleConv(doublearray);
				return ob3.toFloat();
			case 'c':
				CharConv ob4=new CharConv(chararray);
				return ob4.toFloat();
			case 's':
				StringConv ob5=new StringConv(stringarray);
				return ob5.toFloat();
			default:
				X.sepln("Error. No array created?");
				return failF;
		}
	}
	long[] toLong()
	{
		switch(type)
		{
			case 'i':
				IntConv ob=new IntConv(intarray);
				return ob.toLong();
			case 'l':
				return longarray;
			case 'f':
				FloatConv ob2=new FloatConv(floatarray);
				return ob2.toLong();
			case 'd':
				DoubleConv ob3=new DoubleConv(doublearray);
				return ob3.toLong();
			case 'c':
				CharConv ob4=new CharConv(chararray);
				return ob4.toLong();
			case 's':
				StringConv ob5=new StringConv(stringarray);
				return ob5.toLong();
			default:
				X.sepln("Error. No array created?");
				return failL;
		}
	}
	double[] toDouble()
	{
		switch(type)
		{
			case 'i':
				IntConv ob=new IntConv(intarray);
				return ob.toDouble();
			case 'l':
				LongConv ob1=new LongConv(longarray);
				return ob1.toDouble();
			case 'f':
				FloatConv ob2=new FloatConv(floatarray);
				return ob2.toDouble();
			case 'd':
				return doublearray;
			case 'c':
				CharConv ob4=new CharConv(chararray);
				return ob4.toDouble();
			case 's':
				StringConv ob5=new StringConv(stringarray);
				return ob5.toDouble();
			default:
				X.sepln("Error. No array created?");
				return failD;
		}
	}
	char[] toChar()
	{
		switch(type)
		{
			case 'i':
				IntConv ob=new IntConv(intarray);
				return ob.toChar();
			case 'l':
				LongConv ob1=new LongConv(longarray);
				return ob1.toChar();
			case 'f':
				FloatConv ob2=new FloatConv(floatarray);
				return ob2.toChar();
			case 'd':
				DoubleConv ob3=new DoubleConv(doublearray);
				return ob3.toChar();
			case 'c':
				return chararray;
			case 's':
				StringConv ob5=new StringConv(stringarray);
				return ob5.toChar();
			default:
				X.sepln("Error. No array created?");
				return failC;
		}
	}
	String[] toStr()
	{
		switch(type)
		{
			case 'i':
				IntConv ob=new IntConv(intarray);
				return ob.toStr();
			case 'l':
				LongConv ob1=new LongConv(longarray);
				return ob1.toStr();
			case 'f':
				FloatConv ob2=new FloatConv(floatarray);
				return ob2.toStr();
			case 'd':
				DoubleConv ob3=new DoubleConv(doublearray);
				return ob3.toStr();
			case 'c':
				CharConv ob4=new CharConv(chararray);
				return ob4.toStr();
			case 's':
				return stringarray;
			default:
				X.sepln("Error. No array created?");
				return failS;
		}
	}
	int[] getV(int a)
	{
		return intarray;
	}
	long[] getV(long a)
	{
		return longarray;
	}
	float[] getV(float a)
	{
		return floatarray;
	}
	double[] getV(double a)
	{
		return doublearray;
	}
	char[] getV(char a)
	{
		return chararray;
	}
	String[] getV(String a)
	{
		return stringarray;
	}
	void flip()
	{
		switch(type)
		{
			case 'i':
				intarray=ArrayFx.flip(intarray);
			case 'l':
				longarray=ArrayFx.flip(longarray);
			case 'f':
				floatarray=ArrayFx.flip(floatarray);
			case 'd':
				doublearray=ArrayFx.flip(doublearray);
			case 'c':
				chararray=ArrayFx.flip(chararray);
			case 's':
				stringarray=ArrayFx.flip(stringarray);
			default:
				X.sepln("Error. No array created?");
				return;
		}
	}
}
