/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.objects;

import gameclasses.earthworms.*;
import gameclasses.game.*;
import gameclasses.loop.*;
import java.awt.geom.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class Mine extends LevelObject
{
    private final int maxfuse;
    
    public Mine(double ix, double iy, int ifuse) 
    {
        x = ix;
        y = iy;
        healthPoints = ifuse;
        maxfuse = ifuse;
        
        cx = 100;
        cy = 100;
    }
    
    @Override public void step(GSGame gs)
    {
        snapToLevelVel(gs, vx, vy, true);
        grenadeBounce(gs, 0.9, 0.9, 0.5);
        
        if(healthPoints != maxfuse)
            healthPoints--;
        
        if(healthPoints == 0)
        {
            gs.spawnExplosion(ExplosionFactory.MakeLargeExplosion((int)x, (int)y));
            gs.removeObject(this);
        }
        
        super.step(gs);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        if(healthPoints == maxfuse) loop.GetGraphicsContext().setFill(Color.GREEN);
        else loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }
    
    @Override public void checkCollide(Actor ac)
    {
        if(healthPoints == maxfuse)
            if(ac instanceof Player)
                if(Point2D.distance(x, y, ac.getX(), ac.getY()) < 50)
                {
                    healthPoints--;
                }
    }
    
    @Override
    public void push(double ivx, double ivy)
    {
        vx += ivx;
        vy += ivy;
    }
}
