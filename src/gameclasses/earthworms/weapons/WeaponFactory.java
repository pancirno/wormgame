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
        fgrenadeproj.expFireParticles = 16;
        
        fgtemp.projectilesToShoot.add(fgrenadeproj);
        
        outWeapons.put(fgtemp.WeaponTag, fgtemp);
        
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
        
        //Minigun
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
        
        //Airstrike
        Weapon airtemp = new Weapon();
        airtemp.WeaponName = "Airstrike";
        airtemp.WeaponTag = "airstrike";
        airtemp.WeaponGroup = 4;
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
        fairtemp.WeaponGroup = 4;
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
        fairproj.explodesOnDescend = true;
        fairproj.expFireParticles = 8;
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        fairtemp.projectilesToShoot.add(fairproj);
        
        outWeapons.put(fairtemp.WeaponTag, fairtemp);
        
        return outWeapons;
    }
}
