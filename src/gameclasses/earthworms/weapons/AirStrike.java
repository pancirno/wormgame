/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Projectile;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirStrike extends Projectile
{

    public AirStrike(double ix, double iy, double ivx, double ivy)
    {
        super(ix, iy, ivx, ivy);
    }
    
    @Override
    public void step(GSGame gs)
    {
        gs.spawnProjectile(new AirStrikeBomb(x-60,y,vx,vy));
        gs.spawnProjectile(new AirStrikeBomb(x-30,y,vx,vy));
        gs.spawnProjectile(new AirStrikeBomb(x,y,vx,vy));
        gs.spawnProjectile(new AirStrikeBomb(x+30,y,vx,vy));
        gs.spawnProjectile(new AirStrikeBomb(x+60,y,vx,vy));
        
        gs.removeObject(this);
    }
    
}
