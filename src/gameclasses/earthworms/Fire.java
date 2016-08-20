/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.Projectile;
import gameclasses.earthworms.particles.PSmoke;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author samsung
 */
public class Fire extends Projectile 
{
    public Fire(double ix, double iy, double ivx, double ivy, int f) 
    {
        initProjectile(null, ix, iy, ivx, ivy);
        fuse = f + 1800;
        burnout = f;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
            
        loop.GetGraphicsContext().fillOval(anchx-4, anchy-4, 8, 8);
    }
    
    @Override
    public void step(GSGame gs)
    {
        fuse--;
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0,1)))
        {
            burnout--;
            
            if(fuse % 6 == 0)
            {
                gs.spawnParticle(new PSmoke(x,y));
            }
        }
        
        double horizdelta = gs.getActualWind()*1.33;
        
        boolean wiggleleft = !gs.currentStage.Collide(x-2, y);
        boolean wiggleright = !gs.currentStage.Collide(x+2, y);
        
        if(wiggleleft) horizdelta -= gs.getRandomNumber() * 1;
        if(wiggleright) horizdelta += gs.getRandomNumber() * 1;
        
        vx = vx * 0.90;
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(vx + horizdelta,1)))
        {
            vx = 0;
            vy = 0;
        }
        else
        {
            vy = 2;
            snapToLevelVel(gs, vx + horizdelta, vy, true, true);
        }
        
        if(fuse % 20 == 0)
        {
            if(gs.getRandomNumber() < 0.2)
            {
                ExplosionFactory.MakeBlazeExplosion(gs, (int)x, (int)y);
            }
            else
            {
                ExplosionFactory.MakeBlazeNoDigExplosion(gs, (int)x, (int)y);
            }
        }
        
        if(burnout <= 0 || fuse <= 0)
        {
            gs.removeObject(this);
        }
        
        if(this.isOutsideAreaOfPlay(gs))
        {
            gs.removeObject(this);
        }
    }
    
}
