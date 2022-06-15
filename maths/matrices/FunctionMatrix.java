package maths.matrices;
import commons.*;
import maths.Arithmetical;
import maths.Maths;
import maths.TypeMismatchException;
import maths.functions.*;
import java.io.IOException;

public class FunctionMatrix implements Arithmetical<FunctionMatrix>
{
	protected Function[][] mat=new Function[0][0];
	public static final Function ZERO=Function.ZERO;
	protected int dimx,dimy;
	
	public FunctionMatrix() {dimx=0;dimy=0;}
	public FunctionMatrix(int dX,int dY) {dimx=dX; dimy=dY;mat=new Function[dX][dY];}
	public FunctionMatrix(Function[] m1) {mat=new Function[1][0];mat[0]=m1;dimx=m1.length;dimy=1;}
	public FunctionMatrix(Function[][] m2) {mat=m2;dimx=m2.length;if(m2.length>0)dimy=m2[0].length;else dimy=0;}
	public FunctionMatrix(boolean b)
	{
		if(b)
		{
			inputMatrixPersonal();
			dimx=mat.length;
			dimy=(dimx>0)?mat[0].length:0;
		}
		else
			dimx=dimy=0;
	}
	public FunctionMatrix(FunctionMatrix ext)
	{
		this.assign(ext);
	}
	public int getDimX()
	{
		return dimx;
	}
	public int getDimY()
	{
		return dimy;
	}
	protected void clear()
	{
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]=ZERO;
		}
	}
	public void assign(FunctionMatrix ext)
	{
		dimx=ext.dimx;
		dimy=ext.dimy;
		mat=ext.mat;
	}
	public void printDimensions(String col)
	{
		X.sopln(dimx+"x"+dimy,col);
	}
	public void printDimensions()
	{
		printDimensions("white");
	}
	
	public final void inputMatrixPersonal()
	{
		mat=inputMatrix();
	}
	public static Function[][] inputMatrix()
	{
		X.sopln("Type your functions, separated by spaces","yellow");
		X.sopln("Type 'done' when done","red");
		String str;
		String[] mtstr;
		Function[] fxs;
		Function[][] mtx=new Function[0][0];
		while(true)
		{
			X.sop(": ","yellow");
			try {str=X.rL().replace("	",";").replace("  "," ").replace(" ",";").trim();}
			catch(IOException e) {e.printStackTrace();return new Function[0][0];}
			if(str.equalsIgnoreCase("done"))
				break;
			mtstr=ArrayFx.splitString(str,';');
			fxs=new Function[mtstr.length];
			for(int i=0;i<mtstr.length;i++)
				fxs[i]=new Function(mtstr[i]);
			mtx=appendColumn(mtx,fxs);
		}
		return mtx;
	}
	
	public FunctionMatrix getTranspose()
	{
		FunctionMatrix nxt=new FunctionMatrix(dimy,dimx);
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<dimy;j++)
				nxt.mat[j][i]=mat[i][j];
		}
		return nxt;
	}
	public void transpose()
	{
		this.assign(getTranspose());
	}
	public static Function[][] copyMatrix(Function[][] m1,Function[][] m2)
	{
		if(m2.length==0)
			return m1;
		if(m1.length<m2.length)
			m1=new Function[m2.length][m2[0].length];
		for(int i=0;i<m2.length;i++)
		{
			for(int j=0;j<m2[0].length;j++)
				m1[i][j]=m2[i][j];
		}
		return m1;
	}
	public static Function[][] appendColumn(Function[][] ma,Function[] c)
	{
		int mxl=0;
		if(ma.length!=0)
			mxl=ma[0].length;
		Function[][] mat2=new Function[ma.length+1][Math.max(mxl,c.length)];
		mat2=copyMatrix(mat2,ma);
		mat2[mat2.length-1]=c;
		return mat2;
	}
	
	public void printMatrix(String col)
	{
		X.sop("[ ",col);
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
			{
				X.sop(mat[i][j].toString(),col);
				if(mat.length!=i+1 || j+1!=mat[i].length)
					X.sop("\t");
			}
			if(mat.length==i+1)
			{
				X.sopln(" ]",col);
				return;
			}
			else
			{
				X.sopln();
				X.sop(" ",col);
			}
		}
	}
	public void printMatrix()
	{
		printMatrix("white");
	}
	
	//Operations
	
	public void DIVI(double v)
	{
		MUL(1/v);
	}
	public void MUL(double v)
	{
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]=new Function(new Term(mat[i][j],new Constant(v)));;
		}
	}
	public void SUB(FunctionMatrix mtx)throws TypeMismatchException
	{
		if(!addable(this,mtx))
			throw new TypeMismatchException();
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]=new Function(new FuncBlock[] {mat[i][j],mtx.mat[i][j].negate()});
		}
	}
	public void ADD(FunctionMatrix mtx)throws TypeMismatchException
	{
		if(!addable(this,mtx))
			throw new TypeMismatchException();
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]=new Function(new FuncBlock[] {mat[i][j],mtx.mat[i][j]});
		}
	}
	public FunctionMatrix linkedCopy()
	{
		return this;
	}
	
	public FunctionMatrix getMUL(FunctionMatrix m2)throws TypeMismatchException
	{
		if(!multipliable(this,m2))
			throw new TypeMismatchException();
		FunctionMatrix extra=getEmptyMatrix(this,m2);
		double j2;
		Function sum=new Function();
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<m2.mat[0].length;j++)
			{
				sum=new Function();
				for(int k=0;k<mat[i].length;k++)
					sum.addTerm(new Term(mat[i][k],m2.mat[k][j]));
				extra.mat[i][j]=sum;
			}
		}
		return extra;
	}
	public void MUL(FunctionMatrix m2)throws TypeMismatchException 
	{this.assign(getMUL(m2));}
	/*public Matrix copy()
	{
		return new Matrix(this);
	}*/ //Deprecated ?
	
	public FunctionMatrix getMatrixOfMinors()
	{
		FunctionMatrix m2=this;
		//mat
		FunctionMatrix mnr=new FunctionMatrix(m2.mat.length,m2.mat[0].length);
		for(int i=0;i<m2.mat.length;i++)
		{
			for(int j=0;j<m2.mat[i].length;j++)
				mnr.mat[i][j]=pivotMatrix(i,j).getDeterminant();
		}
		return mnr;
	}
	public FunctionMatrix getCheckeredMatrixOfMinors()
	{
		FunctionMatrix M=getMatrixOfMinors();
		M.checkboard();
		return M;
	}
	private void checkboard() //Matrix of cofactors
	{
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
			{
				if((i+j)%2!=0)
					mat[i][j].negate();
			}
		}
	}
	public FunctionMatrix subMatrix(int px,int py,int l,int b)
	{
		FunctionMatrix ret=new FunctionMatrix(l,b);
		for(int i=px;i<px+l;i++)
		{
			for(int j=py;j<py+b;j++)
				ret.mat[i-px][j-py]=mat[i][j];
		}
		return ret;
	}
	public FunctionMatrix pivotMatrix(int pvt)
	{
		return pivotMatrix(0,pvt);
	}
	public FunctionMatrix pivotMatrix(int pvtx,int pvty)
	{
		FunctionMatrix ret=new FunctionMatrix(mat.length-1,mat[0].length-1);
		int K1=0,K2=0;
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
			{
				if(i==pvtx || j==pvty)
					continue;
				ret.mat[K1][K2++]=mat[i][j];
				if(K2>=ret.mat[0].length)
				{
					K1++;
					K2=0;
				}
			}
		}
		return ret;
	}
	public Function[][] getMatrix()
	{
		return mat;
	}
	/*public FunctionMatrix getInverse()
	{
		FunctionMatrix m2=this;
		Function det=m2.getDeterminant();
		FunctionMatrix m3=m2.getCheckeredMatrixOfMinors();
		m3.transpose();
		m3.DIVI(det);
		return m3;
	}*/
	public Function getDeterminant()
	{
		if(mat.length==1)
			return mat[0][0];
		if(mat.length==2)
		{
			Function ret=new Function();
			ret.addTerm(new Term(mat[0][0],mat[1][1]));
			ret.addTerm((new Term(mat[0][1],mat[1][0])).negate());
			return ret;
		}
		FunctionMatrix m2=this;
		FunctionMatrix temp;
		Function det=new Function();
		for(int i=0;i<m2.mat.length;i++)
		{
			temp=m2.pivotMatrix(i);
			det.addTerm(new Term(m2.getPivot(i),temp.getDeterminant(),new Constant(Math.pow(-1,i))));
		}
		return det;
	}
	public Function getPivot(int i)
	{
		return mat[0][i];
	}
	
	public static FunctionMatrix getEmptyMatrix(FunctionMatrix m1,FunctionMatrix m2)
	{
		return new FunctionMatrix(m1.dimx,m2.dimy);
	}
	public static boolean addable(FunctionMatrix m1,FunctionMatrix m2)
	{
		return (m1.dimx==m2.dimx && m1.dimy==m2.dimy);
	}
	public static boolean multipliable(FunctionMatrix m1,FunctionMatrix m2)
	{
		return (m1.dimy==m2.dimx);
	}
}
