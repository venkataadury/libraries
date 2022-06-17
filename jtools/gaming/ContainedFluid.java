package jtools.gaming;
import jtools.time.*;
import java.awt.image.*;
import java.awt.*;
import maths.Maths;
import draw.S;
import draw.ImageWorks;
import java.util.HashMap;
import javax.naming.NameAlreadyBoundException;

public abstract class ContainedFluid extends DynamicObject
{
  public int alpha=70;
  protected Color fluidColor=new Color(225,240,255);
  protected double density=1;
  protected Polygon container;
  protected double pcent=100; //height
  public int base=0;
  protected boolean drawCont=true;
  protected int w,h,e=4;

  public ContainedFluid(Polygon p)
  {
    super(null,new GameDimension((int)p.getBounds().getWidth(),(int)p.getBounds().getHeight()));
    container=p;
    processPoly();
    //this.setLocation(0,0);
  }
  public ContainedFluid(Polygon p,Color c)
  {
    this(p,c,255);
  }
  public ContainedFluid(Polygon p,Color c,int al)
  {
    this(p);
    alpha=al;
    fluidColor=new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
  }

  private void processPoly()
  {
    base=(int)(container.getBounds().getY()+container.getBounds().getHeight());
    w=(int)container.getBounds().getWidth();
    h=(int)container.getBounds().getHeight();
    container.translate(0,e);
  }

  public double getFilledPercent() {return pcent;}
  public void setFilledPercent(double p) {pcent=p;}
  public void setDrawContainer(boolean b) {drawCont=b;}
  public void setContainerImage(Image img)
  {
    myImage=img;
  }

  public void pack() {}
  public Image getDrawable()
  {
    BufferedImage bi=ImageWorks.getEmptyImg(w,h+e);
    final Graphics g=bi.createGraphics();

    g.setColor(Color.black);
    g.setColor(fluidColor);
    int lim=(int)(h-(pcent*h)/100);
    for(int i=h+e;i>=lim+e;i--)
    {
      for(int j=0;j<w;j++)
      {
        if(container.contains(j,i))
          bi.setRGB(j,i,fluidColor.getRGB());
      }
    }
    int jl;
    for(int i=0;i<w;i++)
    {
      jl=e-(int)((Math.pow(i-w/2,2)*4*e)/(w*w));
      for(int j=lim+e-1;j>=lim+Maths.randInt(jl);j--)
      {
        if(container.contains(j,i))
          bi.setRGB(i,j,fluidColor.getRGB());
      }
    }
    if(myImage!=null && drawCont)
      g.drawImage(myImage,0,e,null);

    return bi;
  }
}
