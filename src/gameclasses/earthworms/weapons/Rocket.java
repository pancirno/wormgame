/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory.ExplosionSize;
import gameclasses.earthworms.Projectile;
import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Rocket extends Projectile 
{    
    public Rocket(Actor p, double ix, double iy, double ivx, double ivy) 
    {
        super(p, ix, iy, ivx, ivy);
        weight = 10;
        
        cx = 5;
        cy = 5;
        
        windAffected = true;
        gravityAffected = true;
        
        explodesOnHit = true;
        explodeSize = ExplosionSize.ExtraLarge;
        expDamage = 50;
        expPower = 6;
        expBias = -10;
        
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
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
