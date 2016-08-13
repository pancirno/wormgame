/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class BulletProjectile extends Projectile
{
    public BulletProjectile(Actor p, double ix, double iy, double ivx, double ivy) {
        initProjectile(p, ix, iy, ivx, ivy);
    }
    
    @Override
    public void step(GSGame gs)
    {
        while(true)
        {
            if(gs.currentStage.IsOutsideOfLevel(x, y))
            {
                gs.removeObject(this);
                return;
            }

            if(snapToLevelVel(gs, vx, vy, false, false))
            {
                hitScanExp(gs);
                gs.removeObject(this);
                return;
            }
        }
        
    }
    
    protected void hitScanExp(GSGame gs)
    {
    }
}
