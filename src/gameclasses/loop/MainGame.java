/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.loop;

import gameclasses.maingame.Camera;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class MainGame extends GameState
{
    Camera gameCamera;
    Level currentStage;
    
    public MainGame()
    {
        gameCamera = new Camera(0, 0, 800, 600);
        currentStage = new Level();
    }
    
    @Override
    protected void execute(MainLoop loop)
    {
        //collect inputs
        gameCamera.move(loop.GetInputEngine());
        
        //run object logic
        
        //draw background
        drawBackground(loop.GetGraphicsContext(), gameCamera);
        //draw terrain
        currentStage.render(loop.GetGraphicsContext(), gameCamera);
        //draw sprites
        //draw foreground
        //draw ui
    }
    
    private void drawBackground(GraphicsContext gc, Camera cam)
    {
        LinearGradient lg;
        lg = new LinearGradient(0,0,0,2, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(1, Color.BLUE));
        gc.setFill(lg);
        gc.fillRect(0, 0, cam.GetBoundary().getWidth(), cam.GetBoundary().getHeight());
    }
    
}
