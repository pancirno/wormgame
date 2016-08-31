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
 * @author lukasz
 */
public class PTFireExplosion implements ProjectileTrait
{
    protected int fireparticles;
    
    public PTFireExplosion(int f)
    {
        fireparticles = f;
    }
    
    @Override
    public boolean onStep(GSGame gs, Projectile p) 
    {
        return false;
    }

    @Override
    public boolean onMove(GSGame gs, Projectile p) 
    {
        return false;
    }

    @Override
    public boolean onExplosion(GSGame gs, Projectile p) 
    {
        for(int i = 0; i < fireparticles; i++)
        {
            gs.spawnProjectile(new Fire(p.getX(), p.getY(), (i-(fireparticles/2))*0.5, 0, (int)(gs.getRandomNumber()*50) + 300));
        }
        
        return false;
    }
    
}
