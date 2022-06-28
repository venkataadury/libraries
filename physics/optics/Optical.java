package physics.optics;

public interface Optical extends Drawable
{
	public double imageDistance(double u,double eRI); //external refractive index = eRI
	public double imageDistance(double u);
	public int getX();
	public void setX(int x);
}
