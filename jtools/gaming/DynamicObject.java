package jtools.gaming;
import jtools.time.*;
import java.awt.image.*;
import java.awt.*;
import draw.S;
import java.util.HashMap;
import javax.naming.NameAlreadyBoundException;

public abstract class DynamicObject extends GameObject
{
  boolean dynamic=true;
  public DynamicObject(Image img)
  {
    super(img);
    dynamic=false;
  }
  public DynamicObject(Image img,GameDimension gd)
  {
    super(img,gd);
    dynamic=false;
  }
  public DynamicObject(Image img,GamePoint pti)
  {
    super(img,pti);
    dynamic=false;
  }
  public DynamicObject(Image img,Point pti)
  {
	   super(img,pti);
     dynamic=false;
  }

  public DynamicObject(GameDimension gd)
  {
    super(gd);
  }
  public DynamicObject() {super(new GameDimension(0,0));}

  abstract Image getDrawable();

  @Override
  public Image getImage() {return this.getDrawable();}
}
