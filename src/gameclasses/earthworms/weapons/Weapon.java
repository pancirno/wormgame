/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Player;
import gameclasses.loop.GSGame;
import java.util.ArrayList;
import java.util.EnumSet;



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
    
    protected String WeaponName = "";
    protected String WeaponTag = "";
    protected int WeaponGroup = 0;
    protected int WeaponPriority = 0;
    
    protected ArrayList<AIWeaponFlags> AIHints = new ArrayList<>();
    
    protected ArrayList<Projectile> projectilesToShoot = new ArrayList<>();
    protected int shootsAmount = 1;
    protected int framesBetweenShoots = 0;
    protected boolean consecutiveShoots = true;
    protected boolean endTurnAfterShoot = true;
    protected double shootSpread = 0;
    
    protected boolean instantShot = false;
    protected double defaultShootPower = 0;
    
    protected boolean markTheSpot = false;
    protected boolean airStrike = false;
    
    protected double angleMin = 0;
    protected double angleMax = 180;
    
    protected int special = 0;
    
    public void DoShooting(Player plr, GSGame gs, double shootx, double shooty, double angle, double power)
    {
        power = (defaultShootPower == 0) ? power : defaultShootPower;
        
        double aim_precos = Math.cos(angle);
        double aim_presin = Math.sin(angle);
        double aim_horizthr = aim_precos * power;
        double aim_vertthr = aim_presin * power;
        
        for(Projectile p : projectilesToShoot)
        {
            Projectile np = new Projectile(p);
            np.initProjectile(plr, shootx, shooty, aim_horizthr, aim_vertthr);
            gs.spawnProjectile(np);
        }
    }
    
    public EnumSet<AIWeaponFlags> getAIFlags()
    {
        return EnumSet.copyOf(AIHints);
    }
    
    public String getTag()
    {
        return WeaponTag;
    }
    
    public String getName()
    {
        return WeaponName;
    }
    
    public boolean ifInstantShot()
    {
        return instantShot;
    }
    
    public boolean ifConsecutiveShoots()
    {
        return consecutiveShoots;
    }
    
    public int getShootsAmount()
    {
        return shootsAmount;
    }
    
    public int getFramesBetweenShoots()
    {
        return framesBetweenShoots;
    }
}
