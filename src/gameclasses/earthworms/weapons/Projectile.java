/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import gameclasses.earthworms.Fire;
import gameclasses.earthworms.StaticPhysics;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Projectile extends Actor 
{
    protected int fuse = 600;
    protected int burnout = 0;
    
    protected ProjectileDriver pDriver;
    
    protected Point2D markerPoint = null;
    
    public Projectile()
    {
        
    }
    
    public Projectile(ProjectileDriver drv)
    {
        fuse = drv.initialFuse;
        burnout = drv.initialBurnout;
        
        pDriver = drv;
        
        markerPoint = null;
    }
            
    final public void initProjectile(Actor p, double ix, double iy, double ivx, double ivy)
    {
        parent = p;
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
        
        cx = 4;
        cy = 4;
    }
    
    public void setTarget(Point2D tgt)
    {
        markerPoint = tgt;
    }
    
    @Override
    public void step(GSGame gs)
    {
        do
        {
            //count some important stuff
            fuse--;

            checkCollide(gs);

            //is it time to explode?
            if(this.isOutsideAreaOfPlay(gs))
            {
                gs.removeObject(this);
                return;
            }
            
            if(fuse <= 0)
            {
                explode(gs);
                gs.removeObject(this);
                return;
            }
            
            if(pDriver.explodesOnDescend && vy > 2)
            {
                explode(gs);
                gs.removeObject(this);
                return;
            }
            
            //movement mode - projectile
            if(pDriver.spawnChildrenOnTravelInterval > 0)
                if(fuse % pDriver.spawnChildrenOnTravelInterval == 0)
                    dropChildren(gs);
            
            if(pDriver.windAffected) vx = vx + gs.getWind();
            if(pDriver.gravityAffected) vy = vy + StaticPhysics.GRAVITY;

            if(pDriver.bouncesOnHit)
                grenadeBounce(gs, pDriver.bounceReductionOnImpact, pDriver.bounceReductionOnRolling, pDriver.bounceReductionOnBounce, pDriver.goThroughObjects);
            
            if((snapToLevelVel(gs, vx, vy, pDriver.bouncesOnHit, false) && pDriver.explodesOnHit))
            {
                explode(gs);
                if(pDriver.removeOnExplosion) gs.removeObject(this);
                return;
            }
            
        }
        while(pDriver.hitScan);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        if (pDriver.weight == 0) return;
        vx += ivx/pDriver.weight;
        vy += ivy/pDriver.weight;
    }
    
    public void explode(GSGame gs)
    {
        if(pDriver.explodes) ExplosionFactory.MakeCustomExplosion(gs, (int)x, (int)y, pDriver.explodeSize, pDriver.expDamage, pDriver.expPower, pDriver.expBias, pDriver.expHurtRadius, pDriver.expConstDamage);
        
        if(pDriver.expFireParticles > 0)
            for(int i = 0; i < pDriver.expFireParticles; i++)
            {
                gs.spawnProjectile(new Fire(x, y, (i-(pDriver.expFireParticles/2))*0.5, 0, (int)(gs.getRandomNumber()*50) + 300));
            }
        
        if(pDriver.spawnChildrenOnExplosion)
        {
            dropChildren(gs);
        }
    }
    
    private void dropChildren(GSGame gs)
    {
        if(pDriver.children != null)
        {
           for(Projectile c : pDriver.children)
           {
               Projectile nc = new Projectile(pDriver);
               
               if(pDriver.childrenInheritVelocity)
               {
                   nc.vx = vx;
                   nc.vy = vy;
               }
               
               gs.spawnProjectile(nc);
           }
        }
    }
    
    public void activate()
    {
        if(pDriver.explodesOnActivation)
        {
            fuse = 0;
        }
    }
    
    public boolean isHitscan()
    {
        return pDriver.hitScan;
    }
}
