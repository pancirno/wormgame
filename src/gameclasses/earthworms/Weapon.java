/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.loop.GSGame;
import java.util.ArrayList;



/**
 *
 * @author pancirno
 */
public class Weapon 
{
    public enum AIWeaponFlags
    {
        AIDirect,
        AIBazooka,
        AIGrenade,
        AIDeploy,
        AIClose,
        AIRepeatable
    }
    
    public String WeaponName = "";
    public String WeaponTag = "";
    public int WeaponGroup = 0;
    public int WeaponPriority = 0;
    
    public ArrayList<AIWeaponFlags> AIHints = new ArrayList<>(); //set?
    
    public ArrayList<Projectile> projectilesToShoot = new ArrayList<>();
    public int shootsAmount = 1;
    public int framesBetweenShoots = 0;
    public boolean consecutiveShoots = true;
    public boolean endTurnAfterShoot = true;
    public double shootSpread = 0;
    
    public boolean instantShot = false;
    
    public boolean markTheSpot = false;
    public boolean airStrike = false;
    
    public double angleMin = 0;
    public double angleMax = 180;
    
    public int special = 0;
    
    public void DoShooting(Player plr, GSGame gs, double shootx, double shooty, double shootvx, double shootvy)
    {
        for(Projectile p : projectilesToShoot)
        {
            Projectile np = new Projectile(p);
            np.initProjectile(plr, shootx, shooty, shootvx, shootvy);
            gs.spawnProjectile(np);
        }
    }
}
