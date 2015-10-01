/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;

/**
 *
 * @author lukasz
 */
public class Projectile extends Actor {
        
    public Projectile(double ix, double iy, double ivx, double ivy)
    {
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
    }
    
    public Projectile(double ix, double it, int angle, int power)
    {
        
    }
    
    @Override
    public void step(GSGame gs)
    {
        doPhysics(gs);
        if(gs.currentStage.Collide(x, y))
        {
            gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion((int)x, (int)y));
            gs.removeObject(this);
        }
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    void doPhysics(GSGame gs)
    {
        vy = vy + StaticPhysics.GRAVITY;
        //vx = vx; //add wind
        snapToLevel(gs, vx, vy);
    }
    
    void snapToLevel(GSGame gs, double tvx, double tvy)
    {
        double destx = x + tvx;
        double desty = y + tvy;
        
        double checkx, checky;
        
        int steps = (int)(Math.sqrt((x - destx)*(x - destx) + (y-desty)*(y-desty))*1.33);
        
        for(int i = 1; i <= steps; i++)
        {
            checkx = x + (tvx * (i/steps));
            checky = y + (tvy * (i/steps));
            
            if(gs.currentStage.Collide(checkx, checky))
            {
                x = checkx;
                y = checky;
                return;
            }
        }
        
        x = destx;
        y = desty;
    }
}
