/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.BulletProjectile;
import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class Shotgun extends BulletProjectile
{
    public Shotgun(Actor p, double ix, double iy, double ivx, double ivy, GSGame gs) {
        super(p, ix, iy, ivx, ivy);
    }
    
    @Override protected void hitScanExp(GSGame gs)
    {
        ExplosionFactory.MakeSmallExplosion(gs, (int)x, (int)y);
    }
}
