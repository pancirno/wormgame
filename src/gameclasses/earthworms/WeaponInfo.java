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
        FIREGRENADE,
        BOMB,
        MIRV,
        HOMINGMISSILE,
        MINIGUN,
        SHOTGUN,
        RIFLE,
        BLOWTORCH,
        ROPE,
        BALLOON,
        AIRSTRIKE,
        FIRESTRIKE,
        HIBARI
    }
    
    public static EnumSet<AvailableWeapons> InstantShot = EnumSet.of(AvailableWeapons.BOMB, AvailableWeapons.MINIGUN, AvailableWeapons.SHOTGUN, AvailableWeapons.RIFLE, AvailableWeapons.BLOWTORCH, AvailableWeapons.ROPE);
    public static EnumSet<AvailableWeapons> SetMarker = EnumSet.of(AvailableWeapons.AIRSTRIKE, AvailableWeapons.FIRESTRIKE, AvailableWeapons.HOMINGMISSILE);
    
    static int ChoosenWeapon = 0;
    static int CurrentRow = -1;
    
    static final ArrayList<AvailableWeapons> RowA = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowB = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowC = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowD = new ArrayList<>();
    static final ArrayList<AvailableWeapons> RowE = new ArrayList<>();
    
    static
    {
        RowA.add(AvailableWeapons.ROCKET);
        RowA.add(AvailableWeapons.HOMINGMISSILE);
        RowA.add(AvailableWeapons.MIRV);
        
        RowB.add(AvailableWeapons.GRENADE);
        RowB.add(AvailableWeapons.BOMB);
        RowB.add(AvailableWeapons.FIREGRENADE);
        
        RowC.add(AvailableWeapons.SHOTGUN);
        RowC.add(AvailableWeapons.MINIGUN);
        
        RowD.add(AvailableWeapons.ROPE);
        
        RowE.add(AvailableWeapons.AIRSTRIKE);
        RowE.add(AvailableWeapons.FIRESTRIKE);
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
            case 3:
                return RowD.get(ChoosenWeapon % RowD.size());
            case 4:
                return RowE.get(ChoosenWeapon % RowE.size());
                
            default:
                return AvailableWeapons.NULL;
        }
    }
}
