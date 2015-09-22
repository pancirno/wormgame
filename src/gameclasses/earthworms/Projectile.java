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
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    void doPhysics(GSGame gs)
    {
        vy = vy + StaticPhysics.GRAVITY;
        //vx = vx; //add wind
        
        x += vx;
        y += vy;
    }
}
