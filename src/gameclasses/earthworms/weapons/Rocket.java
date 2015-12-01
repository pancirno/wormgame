/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.earthworms.Projectile;
import gameclasses.earthworms.StaticPhysics;
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
    boolean windAffected = true;
    
    public Rocket(Actor p, double ix, double iy, double ivx, double ivy) 
    {
        super(p, ix, iy, ivx, ivy);
        weight = 10;
        
        cx = 5;
        cy = 5;
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
        fuse--;
        
        if(windAffected) vx = vx + gs.getWind();
        vy = vy + StaticPhysics.GRAVITY;
        
        if(snapToLevelVel(gs, vx, vy, false, false) || fuse <= 0)
        {
            explode(gs);
        }
        
        super.step(gs);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        ExplosionFactory.MakeLargeExplosion(gs, (int)x, (int)y);
        gs.removeObject(this);
    }
}
