package maths.matrices;
import commons.*;
import maths.Arithmetical;
import maths.Maths;
import maths.TypeMismatchException;
import java.io.IOException;

public class Matrix extends Maths implements Arithmetical<Matrix>,Duplicity<Matrix>
{
	public static int ZERO=0,UNIT=1;
	private int dimx,dimy;
	protected double[][] mat=new double[0][0];
	public static final Matrix NULL=new Matrix();
	protected Matrix() {dimx=dimy=0;}
	public Matrix(int[][] matR)
	{
		mat=makeCompatible(matR);
		dimx=matR.length;
		dimy=matR[0].length;
	}
	public Matrix(int d1,int d2)
	{
		dimx=d1; dimy=d2;
		mat=new double[dimx][dimy];
	}
	public Matrix(double[][] matR)
	{
		mat=matR;
		dimx=matR.length;
		dimy=matR[0].length;
	}
	public Matrix(boolean b)
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
	public Matrix(Matrix ext)
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
				mat[i][j]=0;
		}
	}
	public void assign(Matrix ext)
	{
		dimx=ext.dimx;
		dimy=ext.dimy;
		mat=ext.mat;
	}
	public void set(int x,int y,double v) {mat[x][y]=v;}
	public double get(int x,int y) {return mat[x][y];}

	public void printDimensions(String col)
	{
		X.sopln(dimx+"x"+dimy,col);
	}
	public void printDimensions()
	{
		printDimensions("white");
	}
	public void printMatrix(String col)
	{
		X.sop("[ ",col);
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
			{
				X.sop(""+Maths.perfect(mat[i][j]),col);
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
				X.sop("  ",col);
			}
		}
	}
	public void printMatrix()
	{
		printMatrix("white");
	}
	public final void inputMatrixPersonal()
	{
		mat=inputMatrix();
	}
	public static double[][] inputMatrix()
	{
		X.sopln("Type your nos, separated by spaces","yellow");
		X.sopln("Type 'done' when done","red");
		String str;
		double[] mt;
		double[][] mtx=new double[0][0];
		while(true)
		{
			X.sop(": ","yellow");
			try {str=X.rL().replace("	"," ").replace("  "," ").trim();}
			catch(IOException e) {e.printStackTrace();return new double[0][0];}
			if(str.equalsIgnoreCase("done"))
				break;
			mt=ArrayFx.splitDouble(str,' ');
			mtx=appendColumn(mtx,mt);
		}
		return mtx;
	}
	public Matrix getTranspose()
	{
		Matrix nxt=new Matrix(dimy,dimx);
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
	public Matrix copy()
	{
		return new Matrix(this);
	}
	public static double[][] copyMatrix(double[][] m1,double[][] m2)
	{
		if(m2.length==0)
			return m1;
		if(m1.length<m2.length)
			m1=new double[m2.length][m2[0].length];
		for(int i=0;i<m2.length;i++)
		{
			for(int j=0;j<m2[0].length;j++)
				m1[i][j]=m2[i][j];
		}
		return m1;
	}
	public static double[][] appendColumn(double[][] ma,double[] c)
	{
		int mxl=0;
		if(ma.length!=0)
			mxl=ma[0].length;
		double[][] mat2=new double[ma.length+1][Math.max(mxl,c.length)];
		mat2=copyMatrix(mat2,ma);
		mat2=setArray(mat2,ma.length,c);
		return mat2;
	}
	public void DIVI(double v)
	{
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]/=v;
		}
	}
	public void MUL(double v)
	{
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]*=v;
		}
	}
	public void SUB(Matrix mtx)throws TypeMismatchException
	{
		if(!addable(this,mtx))
			throw new TypeMismatchException();
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]-=mtx.mat[i][j];
		}
	}
	public void ADD(Matrix mtx)throws TypeMismatchException
	{
		if(!addable(this,mtx))
			throw new TypeMismatchException();
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
				mat[i][j]+=mtx.mat[i][j];
		}
	}
	public void MUL(Matrix m2)throws TypeMismatchException
	{
		if(!multipliable(this,m2))
			throw new TypeMismatchException();
		Matrix extra=getEmptyMatrix(this,m2);
		double sum=0,j2;
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<m2.mat[0].length;j++)
			{
				sum=0;
				for(int k=0;k<mat[i].length;k++)
					sum+=Maths.round(mat[i][k]*m2.mat[k][j],10);
				extra.mat[i][j]=sum;
			}
		}
		this.assign(extra);
	}
	public Matrix linkedCopy()
	{
		return this;
	}

	public Matrix getMatrixOfMinors()
	{
		Matrix m2=this;
		//mat
		Matrix mnr=new Matrix(m2.mat.length,m2.mat[0].length);
		for(int i=0;i<m2.mat.length;i++)
		{
			for(int j=0;j<m2.mat[i].length;j++)
				mnr.mat[i][j]=pivotMatrix(i,j).getDeterminant();
		}
		return mnr;
	}
	public Matrix getCheckeredMatrixOfMinors()
	{
		Matrix M=getMatrixOfMinors();
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
					mat[i][j]*=-1;
			}
		}
	}
	public Matrix subMatrix(int px,int py,int l,int b)
	{
		Matrix ret=new Matrix(l,b);
		for(int i=px;i<px+l;i++)
		{
			for(int j=py;j<py+b;j++)
				ret.mat[i-px][j-py]=mat[i][j];
		}
		return ret;
	}
	public Matrix pivotMatrix(int pvt)
	{
		return pivotMatrix(0,pvt);
	}
	public Matrix pivotMatrix(int pvtx,int pvty)
	{
		Matrix ret=new Matrix(mat.length-1,mat[0].length-1);
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
	public double[][] getMatrix()
	{
		return mat;
	}
	public Matrix getInverse()
	{
		Matrix m2=this;
		double det=m2.getDeterminant();
		if(det==0)
			throw new RuntimeException("Error in Matrix.getInverse(): Determinant is 0");
		Matrix m3=m2.getCheckeredMatrixOfMinors();
		m3.transpose();
		m3.DIVI(det);
		return m3;
	}
	public double getDeterminant()
	{
		if(mat.length==1)
			return mat[0][0];
		if(mat.length==2)
			return mat[0][0]*mat[1][1]-mat[0][1]*mat[1][0];
		Matrix m2=this;
		Matrix temp;
		double det=0;
		for(int i=0;i<m2.mat.length;i++)
		{
			temp=m2.pivotMatrix(i);
			det+=m2.getPivot(i)*Math.pow(-1,i)*temp.getDeterminant();
		}
		return det;
	}
	public double getPivot(int i)
	{
		return mat[0][i];
	}

	public String toString()
	{
		String str="[";
		for(int i=0;i<mat.length;i++)
		{
			for(int j=0;j<mat[i].length;j++)
			{
				str+=""+Maths.perfect(mat[i][j]);
				if(mat.length!=i+1 || j+1!=mat[i].length)
					str+=("\t");
			}
			if(mat.length==i+1)
			{
				str+=" ]\n";
				return str;
			}
			else
			{
				str+="\n";
				str+=" ";
			}
		}
		return str;
	}
	public static double[][] makeCompatible(int[][] m)
	{
		if(m.length==0)
			return new double[0][0];
		double[][] ans=new double[m.length][m[0].length];
		for(int i=0;i<m.length;i++)
		{
			for(int j=0;j<m[i].length;j++)
				ans[i][j]=m[i][j];
		}
		return ans;
	}
	public static double[][] setArray(double[][] mtx,int l,double[] ex)
	{
		if(l>=mtx.length)
			throw new ArrayIndexOutOfBoundsException();
		for(int i=0;i<ex.length;i++)
			mtx[l][i]=ex[i];
		return mtx;
	}
	public static Matrix getEmptyMatrix(Matrix m1,Matrix m2)
	{
		return new Matrix(m1.dimx,m2.dimy);
	}
	public static boolean addable(Matrix m1,Matrix m2)
	{
		return (m1.dimx==m2.dimx && m1.dimy==m2.dimy);
	}
	public static boolean multipliable(Matrix m1,Matrix m2)
	{
		return (m1.dimy==m2.dimx);
	}
	public static SquareMatrix inputSquareMatrix()
	{
		return new SquareMatrix(inputMatrix());
	}
}
