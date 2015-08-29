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
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class GameLoop extends AnimationTimer
{    
    private InputEngine inputreceiver;
    private GraphicsContext drawengine;
    
    private Random r;
    
    //test
    
    @Override
    public void start()
    {
        inputreceiver = new InputEngine();
        if(drawengine == null) return;
        
        r = new Random();
        
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
        drawengine.clearRect(0, 0, 800, 600);
        for(int x = 0; x < 5000; x++)
        {
        drawengine.setFill( Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)) );
        drawengine.fillText( "benis", r.nextInt(800), r.nextInt(600) );
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
