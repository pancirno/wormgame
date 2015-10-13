/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.BulletProjectile;
import gameclasses.earthworms.ExplosionFactory;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class UZI extends BulletProjectile
{
    public UZI(double ix, double iy, double ivx, double ivy) {
        super(ix, iy, ivx, ivy);
    }
    
    @Override protected void hitScanExp(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeBulletExplosion((int)x, (int)y));
    }
}