package model.examples.ddd;

import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;


/**
* An example for loading a geometric object and showing it in the scene.
* The names of the parts of the object are printed.
* The colour blue is assigned to one of the part.
*
* @author Frank Klawonn
* Last change 07.07.2005
*/
public class Load3DExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public Load3DExample()
  {

    //Mechanism for closing the window and ending the program.
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //Default settings for the viewer parameters.
    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());


    //Construct the SimpleUniverse:
    //First generate it using the Canvas.
    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);


    //Default position of the viewer.
    simpUniv.getViewingPlatform().setNominalViewingTransform();


    //The scene is generated in this method.
    createSceneGraph(simpUniv);


    //Add some light to the scene.
    addLight(simpUniv);


    //The following three lines enable navigation through the scene using the mouse.
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("An object loaded from a file");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);


  }




  public static void main(String[] args)
  {
     Load3DExample le = new Load3DExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {



    //Load an obj-file.
    ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
    Scene s = null;

    try
    {
      s = f.load("src/model/examples/black_pearl/black_pearl.obj");
    }
    catch (Exception e)
    {
      System.out.println("File loading failed:" + e);
    }

    //Generate a transformation group for the loaded object.
    Transform3D tfObject = new Transform3D();
    tfObject.rotZ(0.4*Math.PI);
    Transform3D xRotation = new Transform3D();
    xRotation.rotY(0.4*Math.PI);
    tfObject.mul(xRotation);
    TransformGroup tgObject = new TransformGroup(tfObject);
    tgObject.addChild(s.getSceneGroup());



    //In the following way, the names of the parts of the object can be
    //obtained. The names are printed.
    Hashtable namedObjects = s.getNamedObjects();
    Enumeration enumer = namedObjects.keys();
    String name;
    while (enumer.hasMoreElements())
    {
      name = (String) enumer.nextElement();
      System.out.println("Name: "+name);
    }



    //Assign the colour blue to the part named "sphere982".
    Appearance lightBlueApp = new Appearance();
    setToMyDefaultAppearance(lightBlueApp,new Color3f(0.0f,0.1f,0.3f));
    Shape3D partOfTheObject = (Shape3D) namedObjects.get("sphere982");
    partOfTheObject.setAppearance(lightBlueApp);


    BranchGroup theScene = new BranchGroup();

    theScene.addChild(tgObject);

    //The following four lines generate a white background.
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    bg.setApplicationBounds(bounds);
    theScene.addChild(bg);


    theScene.compile();

    //Add the scene to the universe.
    su.addBranchGraph(theScene);
  }


  /**
  * Generates a default surface (Appearance) in a specified colour.
  *
  * @param app      The Appearance for the surface.
  * @param col      The colour.
  */
  public static void setToMyDefaultAppearance(Appearance app, Color3f col)
  {
    app.setMaterial(new Material(col,col,col,col,150.0f));
  }



  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  }



}
