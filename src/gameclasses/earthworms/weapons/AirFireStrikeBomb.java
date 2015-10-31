/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.earthworms.Fire;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class AirFireStrikeBomb extends AirStrikeBomb
{

    public AirFireStrikeBomb(double ix, double iy, double ivx, double ivy)
    {
        super(ix, iy, ivx, ivy);
        
        fuse = 45;
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion((int)x, (int)y));
        
        for(int i = 0; i < 8; i++)
        {
            gs.spawnProjectile(new Fire(x, y, (i-4)/3, 0, (int)(gs.getRandomNumber()*50) + 400));
        }
        
        gs.removeObject(this);
    }
}
