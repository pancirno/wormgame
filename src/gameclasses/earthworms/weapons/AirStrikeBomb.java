/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirStrikeBomb extends Rocket
{    
    public AirStrikeBomb(double ix, double iy, double ivx, double ivy)
    {
        super(ix, iy, ivx, ivy);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion((int)x, (int)y));
        gs.removeObject(this);
    }
    
}
