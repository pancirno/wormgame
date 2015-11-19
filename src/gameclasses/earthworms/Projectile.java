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
        
    protected double weight = 1;
    protected int fuse = 600;
    
    public Projectile(Actor p, double ix, double iy, double ivx, double ivy)
    {
        parent = p;
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
    }
    
    @Override
    public void step(GSGame gs)
    {
        if(this.isOutsideAreaOfPlay(gs))
        {
            gs.removeObject(this);
        }
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        if (weight == 0) return;
        vx += ivx/weight;
        vy += ivy/weight;
    }
    
    public void explode(GSGame gs)
    {
        
    }
}
