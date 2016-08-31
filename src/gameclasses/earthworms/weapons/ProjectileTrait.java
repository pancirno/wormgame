/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.weapons.Projectile;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public interface ProjectileTrait 
{
    boolean onStep(GSGame gs, Projectile p);
    boolean onMove(GSGame gs, Projectile p);
    boolean onExplosion(GSGame gs, Projectile p);
}
