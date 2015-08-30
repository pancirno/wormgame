/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import java.util.*;
import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import gameclasses.*;

/**
 *
 * @author pancirno
 */
public class GameLoop extends AnimationTimer
{    
    private InputEngine inputreceiver;
    private GraphicsContext drawengine;
    private Random r;
    
    int WindowWidth;
    int WindowHeight;
    CameraData Camera;
    
    Level currentStage;
    
    @Override
    public void start()
    {
        inputreceiver = new InputEngine();
        if(drawengine == null) return;
        
        WindowWidth = 800;
        WindowHeight = 600;
        
        r = new Random();
        Camera = new CameraData(0,0,WindowWidth, WindowHeight);
        
        currentStage = new Level();
        
        super.start();
    }
    
    @Override
    public void stop()
    {
        super.stop();
    }
    
    @Override
    public void handle(long now)
    {     
        //draw background
        //draw terrain
        currentStage.render(drawengine, Camera);
        //draw sprites
        //draw foreground
        //draw ui
        
        //call for actions
        if(inputreceiver.keyStatus(KeyCode.UP) == true)
        {
            Camera.MoveCameraRel(0, 3);
        }
        if(inputreceiver.keyStatus(KeyCode.DOWN) == true)
        {
            Camera.MoveCameraRel(0, -3);
        }
        if(inputreceiver.keyStatus(KeyCode.LEFT) == true)
        {
            Camera.MoveCameraRel(3, 0);
        }
        if(inputreceiver.keyStatus(KeyCode.RIGHT) == true)
        {
            Camera.MoveCameraRel(-3, 0);
        }
    }
        
    
    //konfiguracja przesylu wejscia wyjscia
    public void OnPress(KeyCode kc)
    {
        inputreceiver.setPressed(kc);
    }
    
    public void OnRelease(KeyCode kc)
    {
        inputreceiver.setReleased(kc);
    }
    
    //konfiguracja renderowania
    public void AttachRenderContext(GraphicsContext cg)
    {
        drawengine = cg;
    }
}
