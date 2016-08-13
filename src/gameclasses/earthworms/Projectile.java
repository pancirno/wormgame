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
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Projectile extends Actor 
{
    public int fuse = 600;
    public int burnout = 0;
    
    public boolean hitScan = false;
    public boolean goThroughObjects = false;
    
    public boolean explodes = true;
    public boolean explodesOnHit = true;
    public ExplosionSize explodeSize = ExplosionSize.None;
    public boolean expConstDamage = false;
    public int expDamage = 0;
    public double expPower = 0;
    public int expBias = 0;
    public double expHurtRadius = -1;
    
    public boolean windAffected = false;
    public boolean gravityAffected = false;
    public double weight = 1;
    
    public boolean spawnChildrenOnExplosion = false;
    public boolean spawnChildrenOnTravel = false;
    public ArrayList<Projectile> children = null; 
    
    public boolean bouncesOnHit = false;
    public double bounceReductionOnImpact = 0;
    public double bounceReductionOnRolling = 0;
    public double bounceReductionOnBounce = 0;
            
    
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
