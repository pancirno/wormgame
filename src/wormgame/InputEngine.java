/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import java.util.*;
import javafx.geometry.Point2D;
import javafx.scene.input.*;

/**
 *
 * @author pancirno
 */
public class InputEngine
{
    //keyboard
    public enum KeyStatus
    {
        PULSE,
        CONFIRMED,
        OFF
    }
    
    private final HashMap<KeyCode, KeyStatus> PressedTable;
    
    //mouse
    private Point2D mousePos = null;
    
    public InputEngine()
    {
        PressedTable = new HashMap<>();
        for(KeyCode k : KeyCode.values())
        {
            PressedTable.put(k, KeyStatus.OFF);
        }
    }
    
    public KeyStatus keyStatus(KeyCode kc)
    {
        KeyStatus k = PressedTable.get(kc);
        if(k == KeyStatus.PULSE)
        {
            PressedTable.put(kc, KeyStatus.CONFIRMED);
            return KeyStatus.PULSE;
        }
        else
            return k;
    }
    
    public void setPressed(KeyCode kc)
    {
        PressedTable.put(kc, KeyStatus.PULSE);
    }
    
    public void setReleased(KeyCode kc)
    {
        PressedTable.put(kc, KeyStatus.OFF);
    }
    
    public boolean checkPressed(KeyCode kc)
    {
        KeyStatus k = keyStatus(kc);
        return k == KeyStatus.PULSE || k == KeyStatus.CONFIRMED;
    }
    
    public boolean checkPulse(KeyCode kc)
    {
        KeyStatus k = keyStatus(kc);
        return k == KeyStatus.PULSE;
    }
            
    
    //mouse support, clear mouse after receiving it or refreshing game state
    public void setClicked(double sceneX, double sceneY) 
    {
        mousePos = new Point2D(sceneX, sceneY);
    }
    
    public boolean isClicked()
    {
        return mousePos != null;
    }
    
    public Point2D getClicked()
    {
        return mousePos;
    }
    
    public Point2D getClickedThenNull()
    {
        Point2D ret = mousePos;
        mousePos = null;
        return ret;
    }
    
}

