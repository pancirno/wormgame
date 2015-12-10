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
public class GameScheme 
{
    public int turntime;
    
    private final HashMap<WeaponInfo.AvailableWeapons, Integer> ammo = new HashMap<>();
    private final HashMap<WeaponInfo.AvailableWeapons, Integer> delay = new HashMap<>();
    private final HashMap<WeaponInfo.AvailableWeapons, Integer> power = new HashMap<>();
    private final HashMap<WeaponInfo.AvailableWeapons, Integer> crate = new HashMap<>();
    
    public void setAmmo(WeaponInfo.AvailableWeapons w, int i)
    {
        ammo.putIfAbsent(w, i);
    }
    
    public void setDelay(WeaponInfo.AvailableWeapons w, int i)
    {
        delay.putIfAbsent(w, i);
    }
    
    public void setPower(WeaponInfo.AvailableWeapons w, int i)
    {
        power.putIfAbsent(w, i);
    }
    
    public void setCrate(WeaponInfo.AvailableWeapons w, int i)
    {
        crate.putIfAbsent(w, i);
    }
    
    public int getAmmo(WeaponInfo.AvailableWeapons w)
    {
        return ammo.getOrDefault(w, 0);
    }
    
    public int getDelay(WeaponInfo.AvailableWeapons w)
    {
        return delay.getOrDefault(w, 0);
    }
    
    public int getPower(WeaponInfo.AvailableWeapons w)
    {
        return power.getOrDefault(w, 2);
    }
    
    public int getCrate(WeaponInfo.AvailableWeapons w)
    {
        return crate.getOrDefault(w, 0);
    }
    
    public HashMap<WeaponInfo.AvailableWeapons, Integer> getAmmoTable()
    {
        return new HashMap<>(ammo);
    }
    
    public static GameScheme testScheme()
    {
        GameScheme gs = new GameScheme();
        
        gs.turntime = 99;
        
        for(WeaponInfo.AvailableWeapons aw : WeaponInfo.AvailableWeapons.values())
        {
            gs.setAmmo(aw, 9999);
        }
        
        return gs;
    }
    
    public static GameScheme defaultIntermediate()
    {
        GameScheme gs = new GameScheme();
        
        gs.turntime = 45;
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.ROCKET, 99);
        gs.setAmmo(WeaponInfo.AvailableWeapons.GRENADE, 99);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.HOMINGMISSILE, 1);
        gs.setCrate(WeaponInfo.AvailableWeapons.HOMINGMISSILE, 2);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.BOMB, 1);
        gs.setCrate(WeaponInfo.AvailableWeapons.BOMB, 2);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.SHOTGUN, 99);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.ROPE, 5);
        gs.setCrate(WeaponInfo.AvailableWeapons.ROPE, 4);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.FIREGRENADE, 1);
        gs.setDelay(WeaponInfo.AvailableWeapons.FIREGRENADE, 2);
        gs.setCrate(WeaponInfo.AvailableWeapons.FIREGRENADE, 2);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.AIRSTRIKE, 1);
        gs.setDelay(WeaponInfo.AvailableWeapons.AIRSTRIKE, 5);
        gs.setCrate(WeaponInfo.AvailableWeapons.AIRSTRIKE, 2);
        
        gs.setAmmo(WeaponInfo.AvailableWeapons.FIRESTRIKE, 1);
        gs.setDelay(WeaponInfo.AvailableWeapons.FIRESTRIKE, 7);
        gs.setCrate(WeaponInfo.AvailableWeapons.FIRESTRIKE, 1);
        
        gs.setCrate(WeaponInfo.AvailableWeapons.MINIGUN, 5);
        gs.setCrate(WeaponInfo.AvailableWeapons.DOUBLESHOTGUN, 2);
        gs.setCrate(WeaponInfo.AvailableWeapons.FLAMETHROWER, 2);
        gs.setCrate(WeaponInfo.AvailableWeapons.MIRV, 1);
        gs.setCrate(WeaponInfo.AvailableWeapons.HIBARI, 1);
        
        return gs;
    }
}
