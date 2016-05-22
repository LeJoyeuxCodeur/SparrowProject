package view;

import env3d.advanced.EnvAdvanced;
import env3d.advanced.EnvSkyRoom;
import env3d.advanced.EnvWater;
import env3d.Env;
   
public class Game{
    public void play() {
        // EnvAdvanced must be used for water to work
        Env env = new EnvAdvanced();
        // Need to use a skybox for our environment, so the water
        // has something to reflect.
        env.setRoom(createSkyRoom());
                  
        // Create the water object
        EnvWater water = new EnvWater();
         
        // Add the wataer object into our environment.
        // By default, the size of the water plain is 1x1 and
        // the center of the water is located at 0,0,0
        env.addObject(water);        
         
        // Move the camera back a little to show the water plain
        env.setCameraXYZ(0, 0, 10);
           
        // Exit when the escape key is pressed
        while (env.getKey() != 1) {
            env.advanceOneFrame();
        }
        env.exit();
    }
     
    public EnvSkyRoom createSkyRoom() {
        // Create a the sky box.  The parameter specifies the directory 
        // that contains north.jpg, south.jpg, east.jpg, west.jpg, top.jpg,
        // and bottom.jpg
         
        EnvSkyRoom skyroom = new EnvSkyRoom("lib/env3d_template/textures/skybox/");
          
        return skyroom;
    }
     
    public static void main(String [] args) {
        (new Game()).play();
    }
}