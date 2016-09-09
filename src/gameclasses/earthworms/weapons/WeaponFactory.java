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
    static final ProjectileDriver GenericProjectile;
    
    static
    {
        //Generic bullet Projectile
        GenericProjectile = new ProjectileDriver();
        
        GenericProjectile.hitScan = true;
        GenericProjectile.explodeSize = ExplosionFactory.ExplosionSize.Small;
        GenericProjectile.expConstDamage = true;
        GenericProjectile.expDamage = 5;
        GenericProjectile.expPower = 1;
        GenericProjectile.expBias = -10;
    }
    
    static public HashMap<String, Weapon> GetDefaultWeaponList()
    {
        HashMap<String, Weapon> outWeapons = new HashMap<>();
        
        BuildRocketLauncher(outWeapons);
        
        BuildMIRV(outWeapons);
        
        BuildGrenade(outWeapons);
        
        BuildFireGrenade(outWeapons);
        
        BuildNuclearGrenade(outWeapons);
        
        BuildBananaGrenade(outWeapons);
        
        BuildShotgun(outWeapons);
                
        BuildMinigun(outWeapons);
        
        BuildDoubleBarelledShotgun(outWeapons);
        
        BuildBomb(outWeapons);
        
        BuildAirstrike(outWeapons);
        
        BuildFirestrike(outWeapons);
        
        return outWeapons;
    }

    private static void BuildDoubleBarelledShotgun(HashMap<String, Weapon> outWeapons) {
        //Double Shotgun
        Weapon ssgtemp = new Weapon();
        ssgtemp.WeaponName = "Double Barreled Shotgun";
        ssgtemp.WeaponTag = "ssgun";
        ssgtemp.WeaponGroup = 3;
        ssgtemp.WeaponPriority = 3;
        ssgtemp.instantShot = true;
        ssgtemp.defaultShootPower = 15;
        ssgtemp.shootSpread = 1;
        
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        ssgtemp.projectilesToShoot.add(GenericProjectile);
        
        outWeapons.put(ssgtemp.WeaponTag, ssgtemp);
    }

    private static void BuildMinigun(HashMap<String, Weapon> outWeapons) {
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
        
        minitemp.projectilesToShoot.add(GenericProjectile);
        
        outWeapons.put(minitemp.WeaponTag, minitemp);
    }

    private static void BuildFirestrike(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildAirstrike(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildBomb(HashMap<String, Weapon> outWeapons) {
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
        bproj.bounceReductionOnRolling = 1.3;
        bproj.bounceReductionOnBounce = 0.2;
        
        btemp.projectilesToShoot.add(bproj);
        
        outWeapons.put(btemp.WeaponTag, btemp);
    }

    private static void BuildShotgun(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildBananaGrenade(HashMap<String, Weapon> outWeapons) {
        //Banana Bomb
        Weapon bntemp = new Weapon();
        bntemp.WeaponName = "Banana Grenade";
        bntemp.WeaponTag = "bananagrenade";
        bntemp.WeaponGroup = 2;
        bntemp.WeaponPriority = 4;
        bntemp.configureFuse = true;
        
        //Banana Bomb Projectile
        ProjectileDriver bananaproj1 = new ProjectileDriver();
        ProjectileDriver bananaproj2 = new ProjectileDriver();
        bananaproj1.initialFuse = 180;
        bananaproj1.gravityAffected = true;
        bananaproj1.explodesOnHit = false;
        bananaproj1.bouncesOnHit = true;
        bananaproj1.explodeSize = ExplosionFactory.ExplosionSize.Huge;
        bananaproj1.expDamage = 75;
        bananaproj1.expPower = 12;
        bananaproj1.expBias = -10;
        bananaproj1.bounceReductionOnImpact = 0.9;
        bananaproj1.bounceReductionOnRolling = 0.9;
        bananaproj1.bounceReductionOnBounce = 0.9;
        bananaproj1.AddTrait(new PTChildren(true, true, 0, 0, -2, 1, 0.5, bananaproj2, 5));
        
        bananaproj2.weight = 5;
        bananaproj2.initialFuse = 600;
        bananaproj2.gravityAffected = true;
        bananaproj2.explodesOnHit = true;
        bananaproj2.goThroughObjects = true;
        bananaproj2.explodeSize = ExplosionFactory.ExplosionSize.Huge;
        bananaproj2.expDamage = 75;
        bananaproj2.expPower = 12;
        bananaproj2.expBias = -10;
        bananaproj2.bounceReductionOnImpact = 0.9;
        bananaproj2.bounceReductionOnRolling = 0.9;
        bananaproj2.bounceReductionOnBounce = 0.9;
        
        bntemp.projectilesToShoot.add(bananaproj1);
        
        outWeapons.put(bntemp.WeaponTag, bntemp);
    }

    private static void BuildNuclearGrenade(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildFireGrenade(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildGrenade(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildMIRV(HashMap<String, Weapon> outWeapons) {
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
    }

    private static void BuildRocketLauncher(HashMap<String, Weapon> outWeapons) {
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
    }
}
