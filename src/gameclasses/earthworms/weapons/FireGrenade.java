/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Fire;
import gameclasses.loop.GSGame;

/**
 *
 * @author samsung
 */
public class FireGrenade extends Grenade
{
    public FireGrenade(double ix, double iy, double ivx, double ivy, int ifuse) {
        super(ix, iy, ivx, ivy, ifuse);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        super.explode(gs);
        
        for(int i = 0; i < 16; i++)
        {
            gs.spawnProjectile(new Fire(x, y, (i-8)/2, 0, (int)(gs.getRandomNumber()*50) + 300));
        }
    }
}
