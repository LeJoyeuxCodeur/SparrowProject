package model.examples.ddd;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


/**
* A simple demonstration for the use of a PickingBehaviour. The program prints
* which of the objects is picked.
*
* @author Frank Klawonn
* Last change 07.07.2005
* @see PickingTest
*/
public class InteractionTest extends JFrame
{


  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;



  public InteractionTest()
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
    setTitle("Which object is selected?");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     InteractionTest interexam = new InteractionTest();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

//*** Generate a red cube including its transformation group. ***
    Color3f ambientColourFBox = new Color3f(0.5f,0.0f,0.0f);
    Color3f emissiveColourFBox = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourFBox = new Color3f(0.5f,0.0f,0.0f);
    Color3f specularColourFBox = new Color3f(0.8f,0.0f,0.0f);
    float shininessFBox = 10.0f;

    Appearance fBoxApp = new Appearance();

    fBoxApp.setMaterial(new Material(ambientColourFBox,emissiveColourFBox,
                          diffuseColourFBox,specularColourFBox,shininessFBox));


    Box fBox = new Box(0.2f,0.2f,0.2f,fBoxApp);

    //The UserData are needed in order to identify the cube when it is picked.
    fBox.setUserData("cube");


    Transform3D tfFBox = new Transform3D();
    tfFBox.rotY(Math.PI/6);
    Transform3D rotationX = new Transform3D();
    rotationX.rotX(-Math.PI/5);
    tfFBox.mul(rotationX);
    TransformGroup tgFBox = new TransformGroup(tfFBox);

    tgFBox.addChild(fBox);



//*** Generate a red cube including its transformation group. ***
    Color3f ambientColourBSphere = new Color3f(0.0f,0.6f,0.0f);
    Color3f emissiveColourBSphere = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourBSphere = new Color3f(0.0f,0.6f,0.0f);
    Color3f specularColourBSphere = new Color3f(0.0f,0.6f,0.0f);
    float shininessBSphere = 2.0f;

    Appearance bSphereApp = new Appearance();

    bSphereApp.setMaterial(new Material(ambientColourBSphere,emissiveColourBSphere,
                          diffuseColourBSphere,specularColourBSphere,shininessBSphere));

    Sphere bSphere = new Sphere(0.4f,bSphereApp);

    //The UserData are needed in order to identify the sphere when it is picked.
    bSphere.setUserData("sphere");

    Transform3D tfBSphere = new Transform3D();
    tfBSphere.setTranslation(new Vector3f(2.0f,0.0f,-10.5f));
    TransformGroup tgBSphere = new TransformGroup(tfBSphere);
    tgBSphere.addChild(bSphere);

    //Generate the scenegraph.
    BranchGroup theScene = new BranchGroup();
    theScene.addChild(tgFBox);
    theScene.addChild(tgBSphere);

    //Definition of the parameters for picking. Generate the PickingBehaviour
    //and add it to the scene.
    BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    PickingTest sp = new PickingTest(myCanvas3D,theScene,bs);
    theScene.addChild(sp);

    theScene.compile();

    //Add everything to the universe.
    su.addBranchGraph(theScene);

  }






  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);

    //Directional light.
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);


    //Ambient light.
    Color3f lightColourAmb = new Color3f(0.5f, 0.5f, 0.5f);
    AmbientLight lightAmb = new AmbientLight(lightColourAmb);
    lightAmb.setInfluencingBounds(bounds);
    bgLight.addChild(lightAmb);


    su.addBranchGraph(bgLight);

  }


}
