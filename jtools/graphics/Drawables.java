package jtools.graphics;
import commons.X;
import maths.Maths;
import java.awt.*;
import jtools.gaming.GameObject;
import jtools.gaming.GameDimension;
import java.awt.image.*;
import javax.imageio.ImageIO;
import draw.ImageWorks;

public class Drawables
{
  public static Image getChemApparatus(String name,GameDimension dim)
  {
    int w=dim.getWidth(),h=dim.getHeight();
    BufferedImage bi=ImageWorks.makeTransparent(ImageWorks.getEmptyImg(w,h));
    //BufferedImage bi=ImageWorks.getEmptyImg(w,h);
    try {ImageIO.write(bi,"PNG",new java.io.File("/home/venkata/test.png"));} catch(Exception ex) {ex.printStackTrace(); return bi;}
    final Graphics2D g=bi.createGraphics();
    if(name.equalsIgnoreCase("beaker"))
    {
      g.setColor(Color.black);
      g.drawLine(0,0,0,h-1);
      g.drawLine(0,h-1,w-1,h-1);
      g.drawLine(w-1,h-1,w-1,0);
      int K=0;
      for(int i=5;i<h;i+=10)
      {
        if(K%5!=0)
          g.drawLine(0,i,5,i);
        else
          g.drawLine(0,i,10,i);
        K++;
      }
    }

    return bi;
  }
}
