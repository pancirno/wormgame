/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

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
    int burnout;
    public Fire(double ix, double iy, double ivx, double ivy, int f) {
        super(null, ix, iy, ivx, ivy);
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
        if(gs.currentStage.Collide(x, y+2)) burnout--;
        
        double horizdelta = 0;
        
        boolean wiggleleft = !gs.currentStage.Collide(x-2, y);
        boolean wiggleright = !gs.currentStage.Collide(x+2, y);
        
        if(wiggleleft) horizdelta -= gs.getRandomNumber() * 1;
        if(wiggleright) horizdelta += gs.getRandomNumber() * 1;
        
        vx = vx * 0.90;
        if(gs.currentStage.Collide(x + vx + horizdelta, y+1))
        {
            vx = 0;
            vy = 0;
        }
        else
        {
            vy = 2;
            snapToLevelVel(gs, vx + horizdelta, vy, true);
        }
        
        if(fuse % 15 == 0)
        {
            if(gs.getRandomNumber() < 0.1)
            {
                gs.spawnExplosion(ExplosionFactory.MakeBlazeExplosion((int)x, (int)y));
            }
            else
            {
                gs.spawnExplosion(ExplosionFactory.MakeBlazeNoDigExplosion((int)x, (int)y));
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
