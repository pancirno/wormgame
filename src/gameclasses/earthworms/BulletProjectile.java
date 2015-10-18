/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class BulletProjectile extends Projectile
{
    public BulletProjectile(double ix, double iy, double ivx, double ivy) {
        super(ix, iy, ivx, ivy);
    }
    
    @Override
    public void step(GSGame gs)
    {
        while(true)
        {
            snapToLevelVel(gs, vx, vy, false);

            if(gs.currentStage.IsOutsideOfLevel(x, y))
            {
                gs.removeObject(this);
                return;
            }

            if(gs.currentStage.Collide(x, y))
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
