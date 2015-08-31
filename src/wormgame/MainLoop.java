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
public class MainLoop extends AnimationTimer
{    
    IScene g;
    
    private InputEngine inputreceiver;
    private GraphicsContext drawengine;
    
    int WindowWidth;
    int WindowHeight;
    
    @Override
    public void start()
    {
        inputreceiver = new InputEngine();
        if(drawengine == null) return;
        
        WindowWidth = 800;
        WindowHeight = 600;
        
        g = new Game();
        
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
        g.execute(inputreceiver, drawengine);
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
