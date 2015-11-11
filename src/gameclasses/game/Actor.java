/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import gameclasses.earthworms.StaticPhysics;
import gameclasses.loop.*;
import javafx.geometry.Point2D;

/**
 *
 * @author pancirno
 */
public class Actor
{
    protected double x = 0;
    protected double y = 0;
    
    protected double vx = 0;
    protected double vy = 0;

    protected int healthPoints;
    
    public void render(MainLoop loop, Camera c)
    {
        
    }

    public void step(GSGame gs)
    {

    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void push(double ivx, double ivy)
    {
    }
    
    public void push(Point2D p)
    {
        push(p.getX(), p.getY());
    }
    
    protected void snapToLevelAbs(GSGame gs, double destx, double desty, boolean snaptype)
    {
        double checkx, checky;
        
        double tvx = destx - x;
        double tvy = desty - y;
        
        int steps = (int)(Math.sqrt((x - destx)*(x - destx) + (y-desty)*(y-desty)))*2; //TODO implement this thing https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        
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
                    
                    checkx = Math.round(checkx);
                    checky = Math.round(checky);
                }
                
                destx = checkx;
                desty = checky;
                
                break;
            }
        }
        
        x = destx;
        y = desty;
    }
    
    protected void snapToLevelVel(GSGame gs, double tvx, double tvy, boolean snaptype)
    {
        snapToLevelAbs(gs, x + tvx, y + tvy, snaptype);
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
    
    public boolean isMoving()
    {
        return (vx != 0) && (vy != 0);
    }
    
    public boolean isOutsideAreaOfPlay(GSGame gs)
    {
        return !gs.currentStage.GameArea.contains(x, y);
    }
}
