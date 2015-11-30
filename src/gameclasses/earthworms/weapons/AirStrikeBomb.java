/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirStrikeBomb extends Rocket
{    
    public AirStrikeBomb(Actor p, double ix, double iy, double ivx, double ivy)
    {
        super(p, ix, iy, ivx, ivy);
        windAffected = false;
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion(gs, (int)x, (int)y));
        gs.removeObject(this);
    }
    
}
