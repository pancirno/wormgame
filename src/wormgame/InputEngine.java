/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import java.util.*;
import javafx.scene.input.*;

/**
 *
 * @author pancirno
 */
public class InputEngine
{
    public enum KeyStatus
    {
        PULSE,
        CONFIRMED,
        OFF
    }
    
    private final HashMap<KeyCode, KeyStatus> PressedTable;
    
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
            
}

