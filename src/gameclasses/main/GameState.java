/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.main;

import wormgame.*;

/**
 *
 * @author pancirno
 */
public abstract class GameState
{
    abstract public void execute(MainLoop loop);
}
