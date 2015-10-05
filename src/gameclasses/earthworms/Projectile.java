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
    
    public void push(double ivx, double ivy)
    {
        if (weight == 0) return;
        vx += ivx/weight;
        vy += ivy/weight;
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
                else if (i > 1)
                {
                    checkx = x + (tvx * ((double)(i-1)/(double)steps));
                    checky = y + (tvy * ((double)(i-1)/(double)steps));
                }
                
                x = checkx;
                y = checky;
                
                return;
            }
        }
        
        x = destx;
        y = desty;
    }
    
    protected void grenadeBounce(GSGame gs, double impactred, double rollred, double bouncered)
    {
        if(gs.currentStage.Collide(x+(-1 * Math.signum(vx)), y))
        {
            vx = vx * StaticPhysics.TORQUE * rollred; //0.9
        }
        else if(gs.currentStage.Collide(x+(1 * Math.signum(vx)), y))
        {
            vx = vx * StaticPhysics.TORQUE * -1 * impactred;
        }
            
        //vertical bounce
        if(gs.currentStage.Collide(x, y+1))
        {
            vy = vy * StaticPhysics.TORQUE * -1 * bouncered; //0.5
        }
        else if(gs.currentStage.Collide(x, y-1))
        {
            vy = Math.abs(vy + StaticPhysics.GRAVITY);
        }
        else
        {
            vy = vy + StaticPhysics.GRAVITY;
        }
    }
}
