/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.BulletProjectile;
import gameclasses.earthworms.CommonMath;
import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class UZI extends BulletProjectile
{
    public UZI(Actor p, double ix, double iy, double angle, GSGame gs)
    {
        this(p, ix, iy, 0, 0);
        
        angle += (gs.getGaussianRandomNumber() - 0.5)*0.1;
        
        double precos = Math.cos(angle);
        double presin = Math.sin(angle);
        vx = precos * 10;
        vy = presin * 10;
    }
    
    public UZI(Actor p, double ix, double iy, double ivx, double ivy) {
        super(p, ix, iy, ivx, ivy);
    }
    
    @Override protected void hitScanExp(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeBulletExplosion(gs, (int)x, (int)y));
    }
    
    @Override
    protected Object[] findNearbyObjects(GSGame gs, double destx, double desty, int radius) 
    {
        Object[] obj = super.findNearbyObjects(gs, destx, desty, radius);
        excludeOwnClassObjects(obj);
        return obj;
    }
}
