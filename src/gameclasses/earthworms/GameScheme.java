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
    public enum GameSchemeProperty
    {
        turn_time,
        round_length,
        sudden_death,
    }
    
    private HashMap<WeaponInfo.AvailableWeapons, Integer> ammo;
    private HashMap<WeaponInfo.AvailableWeapons, Integer> delay;
    private HashMap<WeaponInfo.AvailableWeapons, Integer> power;
    private HashMap<WeaponInfo.AvailableWeapons, Integer> crate;
    
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
}
