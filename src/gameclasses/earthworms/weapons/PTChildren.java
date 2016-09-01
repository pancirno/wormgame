/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.loop.GSGame;
import java.util.ArrayList;

/**
 *
 * @author lukasz
 */
public class PTChildren implements ProjectileTrait
{
    protected boolean spawnChildrenOnExplosion = false;
    protected int spawnChildrenOnTravelInterval = 0;
    protected boolean childrenInheritVelocity = false;
    protected ArrayList<Projectile> children = null; 
    
    
    @Override
    public boolean onStep(GSGame gs, Projectile p) 
    {
        if(spawnChildrenOnTravelInterval > 0)
                if(p.fuse % spawnChildrenOnTravelInterval == 0)
                    dropChildren(gs, p);
        
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
        if(spawnChildrenOnExplosion)
        {
            dropChildren(gs, p);
        }
        
        return false;
    }
        
    private void dropChildren(GSGame gs, Projectile p)
    {
        if(children != null)
        {
           for(Projectile c : children)
           {
               Projectile nc = new Projectile(c.pDriver);
               if(childrenInheritVelocity) nc.setVelocity(p.getVX(), p.getVY(), false);
               gs.spawnProjectile(nc);
           }
        }
    }
    
}
