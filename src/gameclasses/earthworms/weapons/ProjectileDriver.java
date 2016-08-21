/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import java.util.ArrayList;

/**
 *
 * @author pancirno
 */
public class ProjectileDriver
{
    protected int initialFuse = 600;
    protected int initialBurnout = 0;
    
    protected boolean hitScan = false;
    protected boolean goThroughObjects = false;
        
    protected boolean explodes = true;
    protected boolean explodesOnHit = true;
    protected boolean explodesOnDescend = false;
    protected boolean explodesOnActivation = false;
    protected boolean removeOnExplosion = true;
    protected ExplosionFactory.ExplosionSize explodeSize = ExplosionFactory.ExplosionSize.None;
    protected boolean expConstDamage = false;
    protected int expDamage = 0;
    protected double expPower = 0;
    protected int expBias = 0;
    protected double expHurtRadius = -1;
    protected int expFireParticles = 0;
    
    protected boolean windAffected = false;
    protected boolean gravityAffected = false;
    protected double weight = 1;
    
    protected boolean spawnChildrenOnExplosion = false;
    protected int spawnChildrenOnTravelInterval = 0;
    protected boolean childrenInheritVelocity = false;
    protected ArrayList<Projectile> children = null; 
    
    protected boolean bouncesOnHit = false;
    protected double bounceReductionOnImpact = 0;
    protected double bounceReductionOnRolling = 0;
    protected double bounceReductionOnBounce = 0;
            
    protected double homingAccuracy = 0;
    
    public ProjectileDriver()
    {
    }
    
    public ProjectileDriver(ProjectileDriver cp)
    {
        initialFuse = cp.initialFuse;
        initialBurnout = cp.initialBurnout;
        
        hitScan = cp.hitScan;
        goThroughObjects = cp.goThroughObjects;

        explodes = cp.explodes;
        explodesOnHit = cp.explodesOnHit;
        explodesOnDescend = cp.explodesOnDescend;
        explodesOnActivation = cp.explodesOnActivation;
        removeOnExplosion = cp.removeOnExplosion;
        explodeSize = cp.explodeSize;
        expConstDamage = cp.expConstDamage;
        expDamage = cp.expDamage;
        expPower = cp.expPower;
        expBias = cp.expBias;
        expHurtRadius = cp.expHurtRadius;
        expFireParticles = cp.expFireParticles;

        windAffected = cp.windAffected;
        gravityAffected = cp.gravityAffected;
        weight = cp.weight;

        spawnChildrenOnExplosion = cp.spawnChildrenOnExplosion;
        spawnChildrenOnTravelInterval = cp.spawnChildrenOnTravelInterval;
        childrenInheritVelocity = cp.childrenInheritVelocity;

        bouncesOnHit = cp.bouncesOnHit;
        bounceReductionOnImpact = cp.bounceReductionOnImpact;
        bounceReductionOnRolling = cp.bounceReductionOnRolling;
        bounceReductionOnBounce = cp.bounceReductionOnBounce;
        
        homingAccuracy = cp.homingAccuracy;
        
        if(cp.children != null)
        {
            children = new ArrayList<>();
            cp.children.stream().forEach((childp) -> {
                children.add(new Projectile(this));
            });
        }
    }

}
