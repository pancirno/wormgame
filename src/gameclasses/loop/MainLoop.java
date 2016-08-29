/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.loop;

import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import wormgame.InputEngine;

/**
 *
 * @author pancirno
 */
public class MainLoop extends AnimationTimer
{        
    GameState g;
    
    private final InputEngine inputreceiver = new InputEngine();
    private final GraphicsContext drawengine;
        
    int WindowWidth;
    int WindowHeight;
    
    //measure how fast it goes
    public static double executionrate = 0;
    
    public MainLoop(GraphicsContext cg)
    {
        drawengine = cg;
    }
    
    @Override
    public void start()
    {
        if(drawengine == null) return;
                
        g = new GSGame();
        
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
        g.execute(this);
        executionrate = (int)(((double)(System.nanoTime() - now) / 16666666)*100);
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
        
    public void OnClick(double sceneX, double sceneY) {
        inputreceiver.setClicked(sceneX, sceneY);
    }
    
    public InputEngine GetInputEngine()
    {
        return inputreceiver;
    }
    
    public GraphicsContext GetGraphicsContext()
    {
        return drawengine;
    }
    
    public int GetWindowWidth()
    {
        return WindowWidth;
    }
    
    public int GetWindowHeight()
    {
        return WindowHeight;
    }

    
}
