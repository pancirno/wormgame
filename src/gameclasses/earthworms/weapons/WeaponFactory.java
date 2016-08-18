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
        Projectile rocketproj = new Projectile();
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
        
        //Grenade Projectile
        Projectile grenadeproj = new Projectile();
        
        grenadeproj.fuse = 180;
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
        Projectile shotgunproj = new Projectile();
        
        shotgunproj.hitScan = true;
        shotgunproj.explodeSize = ExplosionFactory.ExplosionSize.Medium;
        shotgunproj.expConstDamage = true;
        shotgunproj.expDamage = 20;
        shotgunproj.expPower = 3;
        shotgunproj.expBias = -10;
        
        shtemp.projectilesToShoot.add(shotgunproj);
        
        outWeapons.put(shtemp.WeaponTag, shtemp);
                
        //Generic bullet Projectile
        Projectile hitproj = new Projectile();
        
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
        
        minitemp.projectilesToShoot.add(hitproj);
        
        outWeapons.put(minitemp.WeaponTag, minitemp);
        
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
        Projectile airproj = new Projectile();
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
        
        return outWeapons;
    }
}
