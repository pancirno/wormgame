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
    protected boolean childrenInheritVelocity = false;
    protected int spawnChildrenOnTravelInterval = 0;
    protected double childInitialVX = 0;
    protected double childInitialVY = 0;
    protected double childSpreadVX = 0;
    protected double childSpreadVY = 0;
    protected ArrayList<ProjectileDriver> children = null; 
    
    public PTChildren(boolean onexp, boolean inhvel, int travelint, double initx, double inity, double sprx, double spry, ArrayList<ProjectileDriver> kids)
    {
        spawnChildrenOnExplosion = onexp;
        childrenInheritVelocity = inhvel;
        spawnChildrenOnTravelInterval = travelint;
        
        childInitialVX = initx;
        childInitialVY = inity;
        childSpreadVX = sprx;
        childSpreadVY = spry;
        
        children = kids;
    }
    
    public PTChildren(boolean onexp, boolean inhvel, int travelint, double initx, double inity, double sprx, double spry, ProjectileDriver obj, int num)
    {
        this(onexp, inhvel, travelint, initx, inity, sprx, spry, null);
        
        ArrayList<ProjectileDriver> kids = new ArrayList<>();
        for(int i = 0; i < num; i++) kids.add(obj);
        
        children = kids;
    }
    
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
           for(ProjectileDriver c : children)
           {
               Projectile nc = new Projectile(c);
               
               double cvx = childInitialVX + gs.getGaussianRandomNumber() * childSpreadVX + (childrenInheritVelocity ? p.getVX() : 0);
               double cvy = childInitialVY + gs.getGaussianRandomNumber() * childSpreadVY + (childrenInheritVelocity ? p.getVY() : 0);
               
               nc.initProjectile(p, p.getX(), p.getY(), cvx, cvy);
               gs.spawnProjectile(nc);
           }
        }
    }
    
}
