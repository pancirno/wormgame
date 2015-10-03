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
        
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        
    }
    
    //snaptype : true - scan back to when it didn't collide
    //false - force collision
    protected void snapToLevel(GSGame gs, double tvx, double tvy, boolean snaptype)
    {
        double destx = x + tvx;
        double desty = y + tvy;
        
        double checkx, checky;
        
        int steps = (int)(Math.sqrt((x - destx)*(x - destx) + (y-desty)*(y-desty)));
        
        for(int i = 1; i <= steps; i++)
        {
            checkx = x + (tvx * ((double)i/(double)steps));
            checky = y + (tvy * ((double)i/(double)steps));
            
            if(gs.currentStage.Collide(checkx, checky))
            {
                if(!snaptype)
                {
                }
                else
                {
                    checkx = x + (tvx * ((double)(i)/(double)steps));
                    checky = y + (tvy * ((double)(i)/(double)steps));
                }
                
                x = checkx;
                y = checky;
                
                return;
            }
        }
        
        x = destx;
        y = desty;
    }
}
