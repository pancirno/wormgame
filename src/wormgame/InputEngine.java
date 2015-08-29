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
    private final HashMap<KeyCode, Boolean> PressedTable;
    
    public InputEngine()
    {
        PressedTable = new HashMap<>();
        for(KeyCode k : KeyCode.values())
        {
            PressedTable.put(k, Boolean.FALSE);
        }
    }
    
    public Boolean keyStatus(KeyCode kc)
    {
        return PressedTable.get(kc);
    }
    
    public void setPressed(KeyCode kc)
    {
        PressedTable.put(kc, Boolean.TRUE);
    }
    
    public void setReleased(KeyCode kc)
    {
        PressedTable.put(kc, Boolean.FALSE);
    }
}

