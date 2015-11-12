/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.loop.*;

/**
 *
 * @author lukasz
 */
public class LevelObject extends Actor
{
    @Override public void step(GSGame gs)
    {
        if(this.isOutsideAreaOfPlay(gs))
        {
            gs.removeObject(this);
        }
    }
}
