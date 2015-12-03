/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.WeaponInfo.AvailableWeapons;
import gameclasses.loop.GSGame;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;

/**
 *
 * @author samsung
 */
public class Team {
    
    public String name;
    public ArrayList<String> playernames = new ArrayList<>();
    public Color teamcolor;
    public int teamweapon;
    public boolean ai;
    
    private HashMap<WeaponInfo.AvailableWeapons, Integer> ammo;
    
    public ArrayList<Player> ingameobjects = new ArrayList<>();
    
    
    public Team(String iname, String iplay1, String iplay2, String iplay3, String iplay4, Color iteamcolor, int iteamweapon, boolean isai)
    {
        name = iname;
        playernames.add(iplay1);
        playernames.add(iplay2);
        playernames.add(iplay3);
        playernames.add(iplay4);
        teamcolor = iteamcolor;
        teamweapon = iteamweapon;
        ai = isai;
    }
    
    public void setAvailableAmmo(GameScheme gs)
    {
        if(ammo == null)
            ammo = gs.getAmmoTable();
    }
    
    public HashMap<WeaponInfo.AvailableWeapons, Integer> getAvailableAmmo()
    {
        return ammo;
    }
    
    public int getAmmo(AvailableWeapons aw)
    {
        if(!ammo.containsKey(aw)) return 0;
        else return ammo.get(aw);
    }
    
    public boolean canShootWeapon(GSGame gs, AvailableWeapons aw)
    {
        //if(gs.getScheme().getDelay(aw)) return false;
        if(!ammo.containsKey(aw)) return false;
        if(ammo.get(aw) <= 0) return false;
        
        return true;
    }
    
    public void deductAmmo(AvailableWeapons aw)
    {
        if(ammo.containsKey(aw))
            ammo.compute(aw, (k, v) -> (--v));
    }
    
}
