/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Projectile;
import gameclasses.earthworms.ExplosionFactory;
import gameclasses.earthworms.ExplosionFactory.ExplosionSize;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class Shotgun extends Projectile
{
    public Shotgun(Actor p, double ix, double iy, double ivx, double ivy, GSGame gs) 
    {
        initProjectile(p, ix, iy, ivx, ivy);
        
        hitScan = true;
        explodeSize = ExplosionSize.Medium;
        expConstDamage = true;
        expDamage = 20;
        expPower = 3;
        expBias = -10;
    }
}
