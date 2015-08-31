/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import javafx.scene.canvas.*;
import javafx.scene.input.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class Game implements IScene
{
    CameraData Camera;
    Level currentStage;
    
    public Game()
    {
        Camera = new CameraData(0, 0, 800, 600);
        
        currentStage = new Level();
    }
    
    @Override
    public void execute(InputEngine ie, GraphicsContext dc)
    {
        //draw background
        //draw terrain
        currentStage.render(dc, Camera);
        //draw sprites
        //draw foreground
        //draw ui
        
        //call for actions
        if(ie.keyStatus(KeyCode.UP) == true)
        {
            Camera.MoveCameraRel(0, 3);
        }
        if(ie.keyStatus(KeyCode.DOWN) == true)
        {
            Camera.MoveCameraRel(0, -3);
        }
        if(ie.keyStatus(KeyCode.LEFT) == true)
        {
            Camera.MoveCameraRel(3, 0);
        }
        if(ie.keyStatus(KeyCode.RIGHT) == true)
        {
            Camera.MoveCameraRel(-3, 0);
        }
    }
    
}
