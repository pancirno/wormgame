/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirFireStrike extends AirStrike
{
    public AirFireStrike(double ix, double iy, double ivx, double ivy)
    {
        super(ix, iy, ivx, ivy);
    }
    
    @Override
    public void step(GSGame gs)
    {
        gs.spawnProjectile(new AirFireStrikeBomb(x-60,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(x-30,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(x,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(x+30,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(x+60,y,vx,vy));
        
        gs.removeObject(this);
    }
}
