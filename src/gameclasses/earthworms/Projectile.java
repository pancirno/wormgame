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
    protected int fuse = 10000;
    
    public Projectile(double ix, double iy, double ivx, double ivy)
    {
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
    }
    
    @Override
    public void step(GSGame gs)
    {
        
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    public void push(double ivx, double ivy)
    {
        if (weight == 0) return;
        vx += ivx/weight;
        vy += ivy/weight;
    }
    
    public void explode(GSGame gs)
    {
        
    }
}
