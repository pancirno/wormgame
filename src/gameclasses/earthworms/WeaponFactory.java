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
        
        Weapon wtemp;
        
        //Rocket Launcher
        wtemp = new Weapon();
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
        
        return outWeapons;
    }
}
