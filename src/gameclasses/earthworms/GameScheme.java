/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.WeaponFactory;
import gameclasses.earthworms.weapons.Weapon;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pancirno
 */
public class GameScheme 
{
    public int turntime;
    public int hotseattime;
    
    private final HashMap<String, Weapon> weapondata = new HashMap<>();
    
    private final HashMap<String, Integer> ammo = new HashMap<>();
    private final HashMap<String, Integer> delay = new HashMap<>();
    private final HashMap<String, Integer> power = new HashMap<>();
    private final HashMap<String, Integer> crate = new HashMap<>();
    
    HashMap<Integer, List<Weapon>> rowMapCache = null;
    
    public Weapon getWeapon(String w)
    {
        return weapondata.getOrDefault(w, null);
    }
    
    public void insertWeaponData(HashMap<String, Weapon> wd)
    {
        weapondata.putAll(wd);
        rowMapCache = null;
    }
    
    public void setAmmo(String w, int i)
    {
        ammo.putIfAbsent(w, i);
    }
    
    public void setDelay(String w, int i)
    {
        delay.putIfAbsent(w, i);
    }
    
    public void setPower(String w, int i)
    {
        power.putIfAbsent(w, i);
    }
    
    public void setCrate(String w, int i)
    {
        crate.putIfAbsent(w, i);
    }
    
    public int getAmmo(String w)
    {
        return ammo.getOrDefault(w, 0);
    }
    
    public int getDelay(String w)
    {
        return delay.getOrDefault(w, 0);
    }
    
    public int getPower(String w)
    {
        return power.getOrDefault(w, 2);
    }
    
    public int getCrate(String w)
    {
        return crate.getOrDefault(w, 0);
    }
    
    public HashMap<String, Integer> getAmmoTable()
    {
        return new HashMap<>(ammo);
    }
    
    public HashMap<String, Integer> getCrateTable()
    {
        return new HashMap<>(crate);
    }
    
    public HashMap<Integer, List<Weapon>> getWeaponTable()
    {
        if(rowMapCache == null)
        {
            HashMap<Integer, List<Weapon>> hm = new HashMap<>();
        
            for(int i = 0; i <= 12; i++)
            {
                final int vi = i;
                hm.put(vi, weapondata.values().stream().filter(x -> x.getRow() == vi).collect(Collectors.toList()));
            }

            rowMapCache = hm;
        }
        
        return rowMapCache;
    }
    
    public static GameScheme testScheme()
    {
        GameScheme gs = new GameScheme();
        gs.insertWeaponData(WeaponFactory.GetDefaultWeaponList());
        
        gs.turntime = 99;
        gs.hotseattime = 5;

        gs.setAmmo("rocket", 99);
        gs.setAmmo("grenade", 99);
        gs.setAmmo("ngrenade", 99);
        gs.setAmmo("homing", 99);
        gs.setAmmo("bomb", 99);
        gs.setAmmo("bananagrenade", 99);
        gs.setAmmo("shotgun", 99);
        gs.setAmmo("rope", 99);
        gs.setAmmo("fgrenade", 99);
        gs.setAmmo("airstrike", 99);
        gs.setAmmo("fairstrike", 99);
        gs.setAmmo("minigun", 99);
        gs.setAmmo("ssgun", 99);
        gs.setAmmo("flamethrower", 99);
        gs.setAmmo("mirv", 99);
        gs.setAmmo("hibari", 99);
        gs.setCrate("fairstrike", 99);
        gs.setCrate("airstrike", 99);
        
        return gs;
    }
    
    public static GameScheme defaultIntermediate()
    {
        GameScheme gs = new GameScheme();
        
        gs.insertWeaponData(WeaponFactory.GetDefaultWeaponList());
        
        gs.turntime = 45;
        gs.hotseattime = 5;
        
        gs.setAmmo("rocket", 99);
        gs.setAmmo("grenade", 99);
        
        gs.setAmmo("homing", 1);
        gs.setCrate("homing", 2);
        
        gs.setAmmo("bomb", 1);
        gs.setCrate("bomb", 2);
        
        gs.setAmmo("shotgun", 99);
        
        gs.setAmmo("rope", 5);
        gs.setCrate("rope", 4);
        
        gs.setAmmo("firegrenade", 1);
        gs.setDelay("firegrenade", 2);
        gs.setCrate("firegrenade", 2);
        
        gs.setAmmo("airstrike", 1);
        gs.setDelay("airstrike", 5);
        gs.setCrate("airstrike", 2);
        
        gs.setAmmo("firestrike", 1);
        gs.setDelay("firestrike", 7);
        gs.setCrate("firestrike", 1);
        
        gs.setCrate("minigun", 5);
        gs.setCrate("doublegun", 2);
        gs.setCrate("flamethrower", 2);
        gs.setCrate("mirv", 1);
        gs.setCrate("hibari", 1);
        
        return gs;
    }
}
