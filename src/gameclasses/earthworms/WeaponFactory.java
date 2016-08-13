/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

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
        
        return outWeapons;
    }
}
