package draw;
import commons.X;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
public class ImageWorks
{
	public static final AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
	public static final AlphaComposite regular = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
	public static BufferedImage clear(BufferedImage bi)
	{
		BufferedImage img=new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_ARGB);
		(img.getGraphics()).setColor(Color.white);
    (img.getGraphics()).fillRect(0,0,img.getWidth(),img.getHeight());
    return img;
	}
	public static BufferedImage makeTransparent(final BufferedImage bi)
	{
		BufferedImage img = new BufferedImage (bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = (Graphics2D)img.createGraphics();
		g.setBackground(S.trasp);
		g.clearRect(0, 0, bi.getWidth(), bi.getHeight());
		g.dispose();
		/*try
		{
		ImageIO.write(img, "png", new File("/home/venkata/test.png"));
		}
		catch(Exception e) {}*/
		return img; //Perfect
	}
	public static void clearG2D(Graphics2D g2d,int wid,int hei)
	{
		g2d.setBackground(S.trasp);
    g2d.clearRect(0,0,wid,hei);
	}
	public static BufferedImage imageToBufferedImage(final Image im)
	{
		BufferedImage img=new BufferedImage(im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
    (img.getGraphics()).drawImage(im, 0, 0, null);
    return img;
	}
	 public static Image makeColorTransparent(final BufferedImage im, final Color color)
   {
      final ImageFilter filter = new RGBImageFilter()
      {
         // the color we are looking for (white)... Alpha bits are set to opaque
         public int markerRGB = color.getRGB() | 0xFFFFFFFF;

         public final int filterRGB(final int x, final int y, final int rgb)
         {
            if ((rgb | 0xFF000000) == markerRGB)
            {
               // Mark the alpha bits as zero - transparent
               return 0x00FFFFFF & rgb;
            }
            else
            {
               // nothing to do
               return rgb;
            }
         }
      };
      final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
      return Toolkit.getDefaultToolkit().createImage(ip);
   }
	 public static BufferedImage replaceColor(final BufferedImage in,final Color srccol,final Color tarcol)
	 {
		 int sint=srccol.getRGB(),tint=tarcol.getRGB();
		 BufferedImage ret=new BufferedImage(in.getWidth(),in.getHeight(),BufferedImage.TYPE_INT_ARGB);
		 for(int i=0;i<in.getWidth();i++)
		 {
			 for(int j=0;j<in.getHeight();j++)
			 {
				 if(in.getRGB(i,j)==sint) ret.setRGB(i,j,tint);
				 else ret.setRGB(i,j,in.getRGB(i,j));
			 }
		 }
		 return ret;
	 }
	 public static BufferedImage copyByColor(final BufferedImage in,final Color srccol,BufferedImage target,int tolr,int tolg,int tolb)
	 {
		 double scaleX=(double)target.getWidth()/in.getWidth(),scaleY=(double)target.getWidth()/in.getWidth();
		 int sint=srccol.getRGB();
		 //BufferedImage ret=new BufferedImage(in.getWidth(),in.getHeight(),BufferedImage.TYPE_INT_ARGB);
		 for(int i=0;i<in.getWidth();i++)
		 {
			 for(int j=0;j<in.getHeight();j++)
			 {
				 Color curcol=new Color(in.getRGB(i,j));
				 if(Math.abs(curcol.getRed()-srccol.getRed())<tolr && Math.abs(curcol.getGreen()-srccol.getGreen())<tolg && Math.abs(curcol.getBlue()-srccol.getBlue())<tolb) target.setRGB((int)(i*scaleX),(int)(j*scaleY),sint);
			 }
		 }
		 return target;
	 }
   public static BufferedImage getEmptyImg(int w,int h)
   {
   	BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
   	((Graphics2D)img.getGraphics()).setColor(S.trasp);
		((Graphics2D)img.getGraphics()).setBackground(S.trasp);
   	((Graphics2D)img.getGraphics()).clearRect(0,0,w,h);
		((Graphics2D)img.getGraphics()).fillRect(0,0,w,h);
   	return img;
   }
   public static BufferedImage rotateImage(BufferedImage bi,double ang) //ang in degrees
   {
   		AffineTransform at=new AffineTransform();
   		at.setToRotation(Math.toRadians(ang),bi.getWidth()/2,bi.getHeight()/2);
   		AffineTransformOp ato=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
   		return ato.filter(bi,null);
   }
   public static BufferedImage flipImage(BufferedImage img,boolean axis)
   {
	   BufferedImage ret=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
	   ret=makeTransparent(ret);
	   Graphics g=ret.createGraphics();
	   if(axis)
		   g.drawImage(img,img.getWidth(),0,-img.getWidth(),img.getHeight(),null);
	   else
		   g.drawImage(img,0,img.getHeight(),img.getWidth(),-img.getHeight(),null);
		return ret;
	}
	public static BufferedImage[] cutImage(BufferedImage img,int[] cuts,boolean axis)
	{
		BufferedImage[] ret=new BufferedImage[cuts.length+1];
		int xy=0;
		for(int i=0;i<cuts.length;i++)
		{
			ret[i]=img.getSubimage((axis)?xy:0,(!axis)?xy:0,(axis)?cuts[i]-xy:img.getWidth(),(!axis)?cuts[i]-xy:img.getHeight());
			xy=cuts[i];
		}
		X.sopln(cuts);
		X.sopln(img.getWidth()+","+xy);
		ret[ret.length-1]=img.getSubimage((axis)?xy:0,(!axis)?xy:0,(axis)?img.getWidth()-xy:img.getWidth(),(!axis)?img.getHeight()-xy:img.getHeight());
		return ret;
	}
	public static BufferedImage[] regularCutImage(BufferedImage img,int intv,boolean axis)
	{
		int[] ia=new int[(int)Math.floor(((axis)?img.getWidth():img.getHeight())/(double)intv)];
		ia[0]=intv;
		for(int i=1;i<ia.length;i++)
			ia[i]=ia[i-1]+intv;
		return cutImage(img,ia,axis);
	}
}
