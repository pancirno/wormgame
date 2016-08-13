/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;
import gameclasses.earthworms.*;
import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Grenade extends Projectile {
    
    public Grenade(Actor p, double ix, double iy, double ivx, double ivy, int ifuse) 
    {
        initProjectile(p, ix, iy, ivx, ivy);
        fuse = ifuse;
        
        cx = 4;
        cy = 4;
        
        gravityAffected = true;
        
        explodesOnHit = false;
        bouncesOnHit = true;
        
        expDamage = 50;
        expPower = 6;
        expBias = -10;
        
        bounceReductionOnImpact = 0.9;
        bounceReductionOnRolling = 0.9;
        bounceReductionOnBounce = 0.5;
        
        explodeSize = ExplosionFactory.ExplosionSize.ExtraLarge;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.GREEN);
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }
    
    @Override
    public void step(GSGame gs)
    {                
        super.step(gs);
    }
    
    @Override 
    public void explode(GSGame gs)
    {
        super.explode(gs);
    }
    
    
}