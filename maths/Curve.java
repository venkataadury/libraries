package maths;
import java.awt.Point;
import maths.functions.Function;

public interface Curve
{
	public FunctionLocus getLocus();
	public Function getFunction();
	public boolean isCircle();
	public boolean isParabola();
	public boolean isHyperbola();
}
