/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author lukasz
 */
public class WeaponInfo {
    
    public enum AvailableWeapons
    {
        NULL,
        ROCKET,
        GRENADE,
        BOMB,
        MIRV,
        MINIGUN,
        SHOTGUN,
        RIFLE,
        BLOWTORCH,
        ROPE,
        BALLOON,
        HIBARI
    }
    
    public static EnumSet<AvailableWeapons> InstantShot = EnumSet.of(AvailableWeapons.BOMB, AvailableWeapons.MINIGUN, AvailableWeapons.SHOTGUN, AvailableWeapons.RIFLE, AvailableWeapons.BLOWTORCH, AvailableWeapons.ROPE);
    
    static int ChoosenWeapon = 0;
    static int CurrentRow = -1;
    
    static final ArrayList<AvailableWeapons> RowA = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowB = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowC = new ArrayList<>();
    static
    {
        RowA.add(AvailableWeapons.ROCKET);
        RowA.add(AvailableWeapons.MIRV);
        
        RowB.add(AvailableWeapons.GRENADE);
        RowB.add(AvailableWeapons.BOMB);
        
        RowC.add(AvailableWeapons.SHOTGUN);
        RowC.add(AvailableWeapons.MINIGUN);
    }
    
    static AvailableWeapons pickWeapon(int row)
    {
        if(row != CurrentRow)
            ChoosenWeapon = 0;
        else ChoosenWeapon++;
        
        CurrentRow = row;
        
        switch(row)
        {
            case 0:
                return RowA.get(ChoosenWeapon % RowA.size());
            case 1:
                return RowB.get(ChoosenWeapon % RowB.size());
            case 2:
                return RowC.get(ChoosenWeapon % RowC.size());
            
            default:
                return AvailableWeapons.NULL;
        }
    }
}
