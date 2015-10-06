/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

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
    
    static
    {
        
    }
}
