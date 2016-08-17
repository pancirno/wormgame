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
import gameclasses.earthworms.ExplosionFactory.ExplosionSize;
import gameclasses.earthworms.StaticPhysics;
import java.util.ArrayList;
import javafx.scene.paint.Color;

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
    protected ArrayList<Projectile> children = null; 
    
    protected boolean bouncesOnHit = false;
    protected double bounceReductionOnImpact = 0;
    protected double bounceReductionOnRolling = 0;
    protected double bounceReductionOnBounce = 0;
            
    
    public Projectile()
    {
        
    }
    
    public Projectile(Projectile cp)
    {
        fuse = cp.fuse;
        burnout = cp.burnout;

        hitScan = cp.hitScan;
        goThroughObjects = cp.goThroughObjects;

        explodes = cp.explodes;
        explodesOnHit = cp.explodesOnHit;
        explodeSize = cp.explodeSize;
        expConstDamage = cp.expConstDamage;
        expDamage = cp.expDamage;
        expPower = cp.expPower;
        expBias = cp.expBias;
        expHurtRadius = cp.expHurtRadius;

        windAffected = cp.windAffected;
        gravityAffected = cp.gravityAffected;
        weight = cp.weight;

        spawnChildrenOnExplosion = cp.spawnChildrenOnExplosion;
        spawnChildrenOnTravel = cp.spawnChildrenOnTravel;

        bouncesOnHit = cp.bouncesOnHit;
        bounceReductionOnImpact = cp.bounceReductionOnImpact;
        bounceReductionOnRolling = cp.bounceReductionOnRolling;
        bounceReductionOnBounce = cp.bounceReductionOnBounce;
    
        if(cp.children != null)
        {
            children = new ArrayList<>();
            cp.children.stream().forEach((childp) -> {
                children.add(new Projectile(childp));
            });
        }
    
    }
            
    public void initProjectile(Actor p, double ix, double iy, double ivx, double ivy)
    {
        parent = p;
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
        
        cx = 4;
        cy = 4;
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
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
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
