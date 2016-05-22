package model.examples.ddd;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.vp.*;
import java.io.FileInputStream;
import javax.swing.JFrame;


/**
* An example for integrating sound into a scene. As a background sound, rain
* is used. A flying bird screeches using a PointSound.
* This program requires the files bgsound.wav, psound.wav
* and darkclouds.jpg.
*
* @author Frank Klawonn
* Last change 17.07.2005
*/
public class SoundExample extends JFrame
{


  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public SoundExample()
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


    //Erzeuge das AudioDevice zur Wiedergabe der Sounds
    AudioDevice ad = simpUniv.getViewer().createAudioDevice();


    //The scene is generated in this method.
    createSceneGraph(simpUniv);


    //Add some light to the scene.
    addLight(simpUniv);


    //The following three lines enable navigation through the scene using the mouse.
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("Background and point sound");
    setSize(1000,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);


  }




  public static void main(String[] args)
  {
     SoundExample be = new SoundExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

    //Load and define the sounds.
    MediaContainer soundMC;
    MediaContainer pointMC;
    PointSound soundPoint = new PointSound();
    BackgroundSound soundBG = new BackgroundSound();
    try
    {
      FileInputStream bgis = new FileInputStream("src/model/examples/bgsound.wav");
      FileInputStream pointis = new FileInputStream("src/model/examples/psound.wav");
      soundMC = new MediaContainer(bgis);
      pointMC = new MediaContainer(pointis);
      soundBG.setSoundData(soundMC);
      soundPoint.setSoundData(pointMC);
    }
    catch(Exception e)
    {
      System.out.println("Problems occurred while loading the sound files.");
    }


    soundBG.setEnable(true);
    soundBG.setLoop(Sound.INFINITE_LOOPS);
    soundBG.setInitialGain(0.9f);

    soundPoint.setEnable(true);
    soundPoint.setLoop(Sound.INFINITE_LOOPS);
    soundPoint.setInitialGain(0.6f);



    BoundingSphere sbounds = new BoundingSphere (new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    soundBG.setSchedulingBounds(sbounds);
    soundPoint.setSchedulingBounds(sbounds);


    //The colours for the Appearance of the bird.
    Color3f ambientColourBird = new Color3f(0.0f,0.0f,0.0f);
    Color3f emissiveColourBird = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourBird = new Color3f(0.1f,0.1f,0.1f);
    Color3f specularColourBird = new Color3f(0.1f,0.1f,0.1f);
    float shininessBird = 10.0f;



    //The bird's Appearance.
    Appearance birdApp = new Appearance();
    birdApp.setMaterial(new Material(ambientColourBird,emissiveColourBird,
                          diffuseColourBird,specularColourBird,shininessBird));

    //The wings.
    Box wings = new Box(0.03f,0.005f,0.3f,birdApp);

    //The transformation group for the bird including the flight and the sound.
    TransformGroup tgmBird = new TransformGroup();
    tgmBird.addChild(wings);

    //The body of the bird.
    Box body = new Box(0.1f,0.01f,0.01f,birdApp);
    tgmBird.addChild(body);


    //The bird's flight.
    long flightTime = 10000;
    Alpha flightAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE+Alpha.DECREASING_ENABLE,
                                  0,0,flightTime,0,0,flightTime,0,0);

    float flightDistance = 9.0f;
    PositionInterpolator posIFlight = new PositionInterpolator(flightAlpha,
                                                   tgmBird,new Transform3D(),
                                                   0.0f,flightDistance);

    BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    posIFlight.setSchedulingBounds(bs);

    tgmBird.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgmBird.addChild(posIFlight);

    //Add the PointSound also to the transformation group of the bird.
    tgmBird.addChild(soundPoint);


    //A transformation group to position the bird including its flight and sound.
    Transform3D tfBird = new Transform3D();
    tfBird.setTranslation(new Vector3f(-flightDistance/2,1.7f,-5.0f));
    TransformGroup tgBird = new TransformGroup(tfBird);
    tgBird.addChild(tgmBird);


    //Add everything to the scene.
    BranchGroup theScene = new BranchGroup();
    theScene.addChild(tgBird);


    //The bounding region for the background sound.
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    //Load the background image.
    TextureLoader textureLoad = new TextureLoader("src/model/examples/darkclouds.jpg",null);
    //Define the loaded image as the background for the scene.
    Background bgImage = new Background(textureLoad.getImage());
    bgImage.setApplicationBounds(bounds);
    theScene.addChild(bgImage);

    //Add the background sound (rain) to the scene.
    theScene.addChild(soundBG);

    theScene.compile();

    //Add the scene to the universe.
    su.addBranchGraph(theScene);

  }






  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    //Directional light.
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.1f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);


    //Ambient light.
    Color3f ambientLightColour = new Color3f(0.5f, 0.5f, 0.5f);
    AmbientLight ambLight = new AmbientLight(ambientLightColour);
    ambLight.setInfluencingBounds(bounds);
    bgLight.addChild(ambLight);



    su.addBranchGraph(bgLight);

  }



}
