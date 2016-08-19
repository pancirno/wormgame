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
import javafx.geometry.Point2D;



/**
 *
 * @author pancirno
 */
public class Weapon 
{
    static final int AIRSTRIKEPIXELSTEP = 50;
    static final int AIRSTRIKEPIXELSTEP_HALF = 25;
    
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
    protected double shootPowerMod = 1;
    
    protected boolean instantShot = false;
    protected double defaultShootPower = 0;
    protected boolean configureFuse = false;
    
    protected boolean markTheSpot = false;
    
    protected double angleMin = 0;
    protected double angleMax = 180;
    
    protected int special = 0;
    
    /**
     *
     * @param plr Player object who made the shoot
     * @param gs GSGame loop object
     * @param shootx startx
     * @param shooty starty
     * @param angle shooting angle
     * @param power shooting power
     * @param target can be null
     */
    public void DoShooting(Player plr, GSGame gs, double shootx, double shooty, double angle, double power, Point2D target, int fuse)
    {
        if(special != 0)
            if(DoSpecial(plr, gs, shootx, shooty, angle, power, target, fuse)) return;
        
        power = (defaultShootPower == 0) ? power : defaultShootPower;
        
        double aim_horizthr = Math.cos(angle) * power;
        double aim_vertthr = Math.sin(angle) * power;
        
        DoLaunchProjectiles(plr, gs, shootx, shooty, aim_horizthr * shootPowerMod, aim_vertthr * shootPowerMod, target, fuse);
    }

    private void DoLaunchProjectiles(Player plr, GSGame gs, double shootx, double shooty, double aim_horizthr, double aim_vertthr, Point2D target, int fuse)
    {
        for(Projectile p : projectilesToShoot)
        {
            double vx = aim_horizthr;
            double vy = aim_vertthr;
            
            if(shootSpread > 0)
            {
                vx += (gs.getGaussianRandomNumber() * shootSpread) - shootSpread/2;
                vy += (gs.getGaussianRandomNumber() * shootSpread) - shootSpread/2;
            }
            
            Projectile np = new Projectile(p);
            np.initProjectile(plr, shootx, shooty, vx, vy);
            if(target != null) np.setTarget(target);
            if(configureFuse) np.fuse = fuse;
            gs.spawnProjectile(np);
        }
    }
    
    /**
     * handle predefined special cases
     * @param plr
     * @param gs
     * @param shootx
     * @param shooty
     * @param angle
     * @param power
     * @param target can be null
     * @return if false continue regular shooting routine
     */
    public boolean DoSpecial(Player plr, GSGame gs, double shootx, double shooty, double angle, double power, Point2D target, int fuse)
    {
        switch(special)
        {
            case 1: //shoot rope connector (handle by player)
            case 2: //blowtorch
            case 3: //drill
                return true;
            case 10: //airstrike behaviour
                double pos = target.getX() - AIRSTRIKEPIXELSTEP_HALF*(projectilesToShoot.size() - 1);
                
                for(Projectile p : projectilesToShoot)
                {
                    Projectile np = new Projectile(p);
                    np.initProjectile(plr, pos, 0, 0, 0);
                    np.setTarget(target);
                    gs.spawnProjectile(np);
                    pos += AIRSTRIKEPIXELSTEP;
                }
                return true;
            default:
                return false;
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
    
    public boolean ifNeedsMarker()
    {
        return markTheSpot;
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
