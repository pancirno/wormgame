/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import java.util.HashMap;

/**
 *
 * @author pancirno
 */
public class WeaponFactory 
{
    static public HashMap<String, Weapon> GetDefaultWeaponList()
    {
        HashMap<String, Weapon> outWeapons = new HashMap<>();
        
        //Rocket Launcher
        Weapon wtemp = new Weapon();
        wtemp.WeaponName = "Rocket Launcher";
        wtemp.WeaponTag = "rocket";
        wtemp.WeaponGroup = 1;
        wtemp.WeaponPriority = 1;
        
        //Rocket Launcher Projectile
        ProjectileDriver rocketproj = new ProjectileDriver();
        rocketproj.weight = 10;
        
        rocketproj.windAffected = true;
        rocketproj.gravityAffected = true;
        rocketproj.explodesOnHit = true;
        rocketproj.explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
        rocketproj.expDamage = 50;
        rocketproj.expPower = 6;
        rocketproj.expBias = -10;
        wtemp.projectilesToShoot.add(rocketproj);
        
        outWeapons.put(wtemp.WeaponTag, wtemp);
        
        //MIRV
        Weapon mirvtemp = new Weapon();
        mirvtemp.WeaponName = "MIRV";
        mirvtemp.WeaponTag = "mirv";
        mirvtemp.WeaponGroup = 1;
        mirvtemp.WeaponPriority = 2;
        
        //MIRV Spawn
        ProjectileDriver mirvsubproj = new ProjectileDriver();
        mirvsubproj.weight = 10;
        
        mirvsubproj.windAffected = true;
        mirvsubproj.gravityAffected = true;
        mirvsubproj.explodesOnHit = true;
        mirvsubproj.explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
        mirvsubproj.expDamage = 50;
        mirvsubproj.expPower = 6;
        mirvsubproj.expBias = -10;
        mirvsubproj.goThroughObjects = true;
        
        //MIRV Projectile
        ProjectileDriver mirvproj = new ProjectileDriver();
        mirvproj.weight = 10;
        
        mirvproj.windAffected = true;
        mirvproj.gravityAffected = true;
        mirvproj.explodesOnHit = true;
        mirvproj.explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
        mirvproj.expDamage = 50;
        mirvproj.expPower = 6;
        mirvproj.expBias = -10;
        mirvproj.AddTrait(new PTMIRVBehaviour());
        mirvproj.AddTrait(new PTChildren(true, true, 0, 0, 0, 2, 0, mirvsubproj, 3));
        mirvtemp.projectilesToShoot.add(mirvproj);
        
        outWeapons.put(mirvtemp.WeaponTag, mirvtemp);
        
        //Grenade
        Weapon gtemp = new Weapon();
        gtemp.WeaponName = "Hand Grenade";
        gtemp.WeaponTag = "grenade";
        gtemp.WeaponGroup = 2;
        gtemp.WeaponPriority = 1;
        gtemp.configureFuse = true;
        
        //Grenade Projectile
        ProjectileDriver grenadeproj = new ProjectileDriver();
        
        grenadeproj.initialFuse = 180;
        grenadeproj.gravityAffected = true;
        grenadeproj.explodesOnHit = false;
        grenadeproj.bouncesOnHit = true;
        grenadeproj.explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
        grenadeproj.expDamage = 50;
        grenadeproj.expPower = 6;
        grenadeproj.expBias = -10;
        grenadeproj.bounceReductionOnImpact = 0.9;
        grenadeproj.bounceReductionOnRolling = 0.9;
        grenadeproj.bounceReductionOnBounce = 0.5;
        
        gtemp.projectilesToShoot.add(grenadeproj);
        
        outWeapons.put(gtemp.WeaponTag, gtemp);
        
        //FGrenade
        Weapon fgtemp = new Weapon();
        fgtemp.WeaponName = "Fire Grenade";
        fgtemp.WeaponTag = "fgrenade";
        fgtemp.WeaponGroup = 2;
        fgtemp.WeaponPriority = 2;
        fgtemp.configureFuse = true;
        
        //FGrenade Projectile
        ProjectileDriver fgrenadeproj = new ProjectileDriver();
        
        fgrenadeproj.initialFuse = 180;
        fgrenadeproj.gravityAffected = true;
        fgrenadeproj.explodesOnHit = false;
        fgrenadeproj.bouncesOnHit = true;
        fgrenadeproj.explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
        fgrenadeproj.expDamage = 50;
        fgrenadeproj.expPower = 6;
        fgrenadeproj.expBias = -10;
        fgrenadeproj.bounceReductionOnImpact = 0.9;
        fgrenadeproj.bounceReductionOnRolling = 0.9;
        fgrenadeproj.bounceReductionOnBounce = 0.5;
        fgrenadeproj.AddTrait(new PTFireExplosion(16));
        
        fgtemp.projectilesToShoot.add(fgrenadeproj);
        
        outWeapons.put(fgtemp.WeaponTag, fgtemp);
        
        //Nuclear Grenade
        Weapon ngtemp = new Weapon();
        ngtemp.WeaponName = "Nuclear Grenade";
        ngtemp.WeaponTag = "ngrenade";
        ngtemp.WeaponGroup = 2;
        ngtemp.WeaponPriority = 3;
        
        //Nuclear Grenade Projectile
        ProjectileDriver ngrenadeproj = new ProjectileDriver();
        
        ngrenadeproj.initialFuse = 600;
        ngrenadeproj.gravityAffected = true;
        ngrenadeproj.explodesOnHit = false;
        ngrenadeproj.bouncesOnHit = true;
        ngrenadeproj.explodeSize = ExplosionFactory.ExplosionSize.Gigantic;
        ngrenadeproj.expDamage = 100;
        ngrenadeproj.expPower = 20;
        ngrenadeproj.expBias = -20;
        ngrenadeproj.bounceReductionOnImpact = 1;
        ngrenadeproj.bounceReductionOnRolling = 0.8;
        ngrenadeproj.bounceReductionOnBounce = 0.75;
        ngrenadeproj.AddTrait(new PTTriggerOnStop());
        
        ngtemp.projectilesToShoot.add(ngrenadeproj);
        
        outWeapons.put(ngtemp.WeaponTag, ngtemp);
        
        //Shotgun
        Weapon shtemp = new Weapon();
        shtemp.WeaponName = "Shotgun";
        shtemp.WeaponTag = "shotgun";
        shtemp.WeaponGroup = 3;
        shtemp.WeaponPriority = 1;
        shtemp.instantShot = true;
        shtemp.defaultShootPower = 15;
        shtemp.shootsAmount = 2;
        shtemp.consecutiveShoots = false;
        shtemp.framesBetweenShoots = 120;
        
        //Shotgun Projectile
        ProjectileDriver shotgunproj = new ProjectileDriver();
        
        shotgunproj.hitScan = true;
        shotgunproj.explodeSize = ExplosionFactory.ExplosionSize.Medium;
        shotgunproj.expConstDamage = true;
        shotgunproj.expDamage = 20;
        shotgunproj.expPower = 3;
        shotgunproj.expBias = -10;
        
        shtemp.projectilesToShoot.add(shotgunproj);
        
        outWeapons.put(shtemp.WeaponTag, shtemp);
                
        //Generic bullet Projectile
        ProjectileDriver hitproj = new ProjectileDriver();
        
        hitproj.hitScan = true;
        hitproj.explodeSize = ExplosionFactory.ExplosionSize.Small;
        hitproj.expConstDamage = true;
        hitproj.expDamage = 5;
        hitproj.expPower = 1;
        hitproj.expBias = -10;
        
        //Minigun
        Weapon minitemp = new Weapon();
        minitemp.WeaponName = "Minigun";
        minitemp.WeaponTag = "minigun";
        minitemp.WeaponGroup = 3;
        minitemp.WeaponPriority = 2;
        minitemp.instantShot = true;
        minitemp.defaultShootPower = 15;
        minitemp.shootsAmount = 15;
        minitemp.consecutiveShoots = true;
        minitemp.framesBetweenShoots = 4;
        minitemp.shootSpread = 1;
        
        minitemp.projectilesToShoot.add(hitproj);
        
        outWeapons.put(minitemp.WeaponTag, minitemp);
        
        //Double Shotgun
        Weapon ssgtemp = new Weapon();
        ssgtemp.WeaponName = "Double Barreled Shotgun";
        ssgtemp.WeaponTag = "ssgun";
        ssgtemp.WeaponGroup = 3;
        ssgtemp.WeaponPriority = 3;
        ssgtemp.instantShot = true;
        ssgtemp.defaultShootPower = 15;
        ssgtemp.shootSpread = 1;
        
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        ssgtemp.projectilesToShoot.add(hitproj);
        
        outWeapons.put(ssgtemp.WeaponTag, ssgtemp);
        
        //Bomb
        Weapon btemp = new Weapon();
        btemp.WeaponName = "Bomb";
        btemp.WeaponTag = "bomb";
        btemp.WeaponGroup = 4;
        btemp.WeaponPriority = 1;
        btemp.instantShot = true;
        btemp.defaultShootPower = 0.5;
        
        //Bomb Projectile
        ProjectileDriver bproj = new ProjectileDriver();
        
        bproj.initialFuse = 300;
        bproj.weight = 10;
        bproj.gravityAffected = true;
        bproj.explodesOnHit = false;
        bproj.bouncesOnHit = true;
        bproj.explodeSize = ExplosionFactory.ExplosionSize.Huge;
        bproj.expDamage = 75;
        bproj.expPower = 12;
        bproj.expBias = -10;
        bproj.bounceReductionOnImpact = 0.2;
        bproj.bounceReductionOnRolling = 0.2;
        bproj.bounceReductionOnBounce = 0.2;
        
        btemp.projectilesToShoot.add(bproj);
        
        outWeapons.put(btemp.WeaponTag, btemp);
        
        //Airstrike
        Weapon airtemp = new Weapon();
        airtemp.WeaponName = "Airstrike";
        airtemp.WeaponTag = "airstrike";
        airtemp.WeaponGroup = 7;
        airtemp.WeaponPriority = 1;
        airtemp.instantShot = true;
        airtemp.markTheSpot = true;
        airtemp.special = 10;
        
        //Airstrike Projectile
        ProjectileDriver airproj = new ProjectileDriver();
        airproj.weight = 10;
        
        airproj.windAffected = false;
        airproj.gravityAffected = true;
        airproj.explodesOnHit = true;
        airproj.explodeSize = ExplosionFactory.ExplosionSize.Large;
        airproj.expDamage = 30;
        airproj.expPower = 3;
        airproj.expBias = -5;
        airtemp.projectilesToShoot.add(airproj);
        airtemp.projectilesToShoot.add(airproj);
        airtemp.projectilesToShoot.add(airproj);
        airtemp.projectilesToShoot.add(airproj);
        airtemp.projectilesToShoot.add(airproj);
        
        outWeapons.put(airtemp.WeaponTag, airtemp);
        
        //Fire Airstrike
        Weapon fairtemp = new Weapon();
        fairtemp.WeaponName = "Napalm Strike";
        fairtemp.WeaponTag = "fairstrike";
        fairtemp.WeaponGroup = 7;
        fairtemp.WeaponPriority = 2;
        fairtemp.instantShot = true;
        fairtemp.markTheSpot = true;
        fairtemp.special = 10;
        
        //Fire Airstrike Projectile
        ProjectileDriver fairproj = new ProjectileDriver();
        fairproj.weight = 10;
        
        fairproj.windAffected = false;
        fairproj.gravityAffected = true;
        fairproj.explodesOnHit = true;
        fairproj.explodeSize = ExplosionFactory.ExplosionSize.Large;
        fairproj.expDamage = 30;
        fairproj.expPower = 3;
        fairproj.expBias = -5;
        fairproj.AddTrait(new PTFireExplosion(8));
        fairproj.AddTrait(new PTMIRVBehaviour());
        
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        
        outWeapons.put(fairtemp.WeaponTag, fairtemp);
        
        return outWeapons;
    }
}
