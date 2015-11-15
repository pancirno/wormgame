/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirFireStrike extends AirStrike
{
    public AirFireStrike(Actor p, double ix, double iy, double ivx, double ivy)
    {
        super(p, ix, iy, ivx, ivy);
    }
    
    @Override
    public void step(GSGame gs)
    {
        gs.spawnProjectile(new AirFireStrikeBomb(this,x-60,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(this,x-30,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(this,x,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(this,x+30,y,vx,vy));
        gs.spawnProjectile(new AirFireStrikeBomb(this,x+60,y,vx,vy));
        
        gs.removeObject(this);
    }
}
