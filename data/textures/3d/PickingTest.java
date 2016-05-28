package model.examples.ddd;

import javax.media.j3d.*;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.geometry.*;

/**
* A very simple use of a picking behaviour. The program prints
* which of the objects is picked.
*
* @author Frank Klawonn
* Last change 07.07.2005
* @see InteractionTest
*/
public class PickingTest extends PickMouseBehavior
{

  public PickingTest(Canvas3D pCanvas, BranchGroup root, Bounds pBounds)
  {
    super(pCanvas,root,pBounds);
    setSchedulingBounds(pBounds);
  }


  //Specification of the action to be carried out when an object is picked.
  public void updateScene(int xpos, int ypos)
  {
    Primitive pickedShape = null;
    pickCanvas.setShapeLocation(xpos,ypos);
    PickResult pResult = pickCanvas.pickClosest();
    if (pResult != null)
    {
      pickedShape = (Primitive) pResult.getNode(PickResult.PRIMITIVE);
    }

    if (pickedShape != null)
    {
      System.out.println("The object "+pickedShape.getUserData()+" has been selected.");
    }
    else
    {
      System.out.println("No object has been selected.");
    }

  }

}
