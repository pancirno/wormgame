/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class MainGame extends GameState
{
    CameraData Camera;
    Level currentStage;
    
    public MainGame()
    {
        Camera = new CameraData(0, 0, 800, 600);
        currentStage = new Level();
    }
    
    @Override
    public void execute(MainLoop loop)
    {
        //draw background
        drawBackground(loop.GetGraphicsContext(), Camera);
        //draw terrain
        currentStage.render(loop.GetGraphicsContext(), Camera);
        //draw sprites
        //draw foreground
        //draw ui
        
        //call for actions
        if(loop.GetInputEngine().keyStatus(KeyCode.UP) == true)
        {
            Camera.MoveCameraRel(0, 3);
        }
        if(loop.GetInputEngine().keyStatus(KeyCode.DOWN) == true)
        {
            Camera.MoveCameraRel(0, -3);
        }
        if(loop.GetInputEngine().keyStatus(KeyCode.LEFT) == true)
        {
            Camera.MoveCameraRel(3, 0);
        }
        if(loop.GetInputEngine().keyStatus(KeyCode.RIGHT) == true)
        {
            Camera.MoveCameraRel(-3, 0);
        }
    }
    
    private void drawBackground(GraphicsContext gc, CameraData cam)
    {
        LinearGradient lg;
        lg = new LinearGradient(0,0,0,2, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(1, Color.BLUE));
        gc.setFill(lg);
        gc.fillRect(0, 0, cam.GetBoundary().getWidth(), cam.GetBoundary().getHeight());
    }
    
}
