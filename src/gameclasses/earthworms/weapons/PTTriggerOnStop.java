/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class PTTriggerOnStop implements ProjectileTrait
{

    @Override
    public boolean onStep(GSGame gs, Projectile p) 
    {
        if (!p.isMoving())
        {
            p.explode(gs);
            gs.removeObject(p);
        }
        return false;
    }

    @Override
    public boolean onMove(GSGame gs, Projectile p) 
    {
        return false;
    }

    @Override
    public boolean onExplosion(GSGame gs, Projectile p) 
    {
        return false;
    }
    
}
