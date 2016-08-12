/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import gameclasses.earthworms.ExplosionFactory.ExplosionSize;
import java.util.List;

/**
 *
 * @author lukasz
 */
public class Projectile extends Actor 
{
    protected int fuse = 600;
    protected int burnout = 0;
    
    protected boolean hitScan = false;
    protected boolean goThroughObjects = false;
    
    protected boolean explodes = true;
    protected boolean explodesOnHit = true;
    protected ExplosionSize explodeSize = ExplosionSize.None;
    protected boolean expConstDamage = false;
    protected int expDamage = 0;
    protected double expPower = 0;
    protected int expBias = 0;
    protected double expHurtRadius = -1;
    
    protected boolean windAffected = false;
    protected boolean gravityAffected = false;
    protected double weight = 1;
    
    protected boolean spawnChildrenOnExplosion = false;
    protected boolean spawnChildrenOnTravel = false;
    protected List<Projectile> children = null; 
    
    protected boolean bouncesOnHit = false;
    protected double bounceReductionOnImpact = 0;
    protected double bounceReductionOnRolling = 0;
    protected double bounceReductionOnBounce = 0;
            
    public Projectile(Actor p, double ix, double iy, double ivx, double ivy)
    {
        parent = p;
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
    }
    
    @Override
    public void step(GSGame gs)
    {
        do
        {
            fuse--;
            burnout--;

            if(windAffected) vx = vx + gs.getWind();
            if(gravityAffected) vy = vy + StaticPhysics.GRAVITY;

            checkCollide(gs);

            if(this.isOutsideAreaOfPlay(gs))
            {
                gs.removeObject(this);
                return;
            }

            if(bouncesOnHit)
                grenadeBounce(gs, bounceReductionOnImpact, bounceReductionOnRolling, bounceReductionOnBounce, goThroughObjects);

            if((snapToLevelVel(gs, vx, vy, bouncesOnHit, false) && explodesOnHit) || fuse <= 0)
            {
                explode(gs);
                return;
            }
        }
        while(hitScan);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        if (weight == 0) return;
        vx += ivx/weight;
        vy += ivy/weight;
    }
    
    public void explode(GSGame gs)
    {
        if(explodes) ExplosionFactory.MakeCustomExplosion(gs, (int)x, (int)y, explodeSize, expDamage, expPower, expBias, expHurtRadius, expConstDamage);
        gs.removeObject(this);
    }
}
