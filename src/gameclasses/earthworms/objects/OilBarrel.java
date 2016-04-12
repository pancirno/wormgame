/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.objects;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.earthworms.Fire;
import gameclasses.earthworms.LevelObject;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author pancirno
 */
public class OilBarrel extends LevelObject
{
    public OilBarrel(double ix, double iy) 
    {
        x = ix;
        y = iy;
        healthPoints = 1;
    }
    
    @Override
    public void step(GSGame gs)
    {
        snapToLevelVel(gs, vx, vy, true, false);
        grenadeBounce(gs, 0.25, 0.25, 0.25, true);
        
        if(healthPoints == 0)
        {
            ExplosionFactory.MakeLargeExplosion(gs, (int)x, (int)y);
            gs.removeObject(this);
            
            for(int i = 0; i < 16; i++)
            {
                gs.spawnProjectile(new Fire(x, y, (i-8)/2, 0, (int)(gs.getRandomNumber()*50) + 300));
            }
        }
        
        super.step(gs);
    }
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        healthPoints = 0;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.ORANGE);
        loop.GetGraphicsContext().fillOval(anchx-24, anchy-48, 48, 48);
    }
    
    
}
