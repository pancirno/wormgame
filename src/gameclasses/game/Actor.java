/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import gameclasses.earthworms.StaticPhysics;
import gameclasses.loop.*;
import javafx.geometry.*;

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
    
    protected double cx = 1;
    protected double cy = 1;
    
    protected Actor parent = null;

    protected int healthPoints;
    
    public void render(MainLoop loop, Camera c)
    {
        
    }

    public void step(GSGame gs)
    {

    }
    
    public void checkCollide(Actor ac)
    {
        
    }
    
    public void push(double ivx, double ivy)
    {
    }
    
    public void push(Point2D p)
    {
        push(p.getX(), p.getY());
    }
    
    //returns true if snapped to level or object
    protected boolean snapToLevelAbs(GSGame gs, double destx, double desty, boolean snaptype)
    {
        double checkx, checky;
        
        double tvx = destx - x;
        double tvy = desty - y;
        
        boolean ifcollided = false;
        boolean objectcollide = false;
        
        double steps = Math.sqrt((x - destx)*(x - destx) + (y-desty)*(y-desty))*2; //TODO implement this thing https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        
        Object[] colobjects = gs.findObjectsInCollisionTree((int)x-64, (int)y-64, (int)destx+64, (int)desty+64);
        
        for(int i = 1; i <= steps; i++)
        {
            checkx = tvx * ((double)i/steps);
            checky = tvy * ((double)i/steps);
            
            for(Object o : colobjects)
                if(o instanceof Actor && o != this && o != parent)
                    if(((Actor)o).getCollisionArea().intersects(getCollisionAreaDelta(checkx, checky))) objectcollide = true;
            
            if(objectcollide || gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(checkx, checky)))
            {
                ifcollided = true;
                
                if (snaptype && i > 1)
                {
                    while(!gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(checkx, checky)) && i > 0)
                    {
                        checkx = tvx * ((double)(--i)/(double)steps);
                        checky = tvy * ((double)(--i)/(double)steps);

                        checkx = Math.round(checkx);
                        checky = Math.round(checky);
                    }
                }
                
                destx = x + checkx;
                desty = y + checky;
                
                break;
            }
        }
        
        x = destx;
        y = desty;
        
        return ifcollided;
    }
    
    //returns true if snapped to level or object
    protected boolean snapToLevelVel(GSGame gs, double tvx, double tvy, boolean snaptype)
    {
        return snapToLevelAbs(gs, x + tvx, y + tvy, snaptype);
    }
    
    protected void grenadeBounce(GSGame gs, double impactred, double rollred, double bouncered)
    {
        //horizontal bounce
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(-1 * Math.signum(vx), 0)))
        {
            vx = vx * StaticPhysics.TORQUE * rollred; //0.9
        }
        else if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(1 * Math.signum(vx), 0)))
        {
            vx = vx * StaticPhysics.TORQUE * -1 * impactred;
        }
            
        //vertical bounce
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, 1)))
        {
            vy = vy * StaticPhysics.TORQUE * -1 * bouncered; //0.5
        }
        else if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, -1)))
        {
            vy = Math.abs(vy + StaticPhysics.GRAVITY);
        }
        else
        {
            vy = vy + StaticPhysics.GRAVITY;
        }
        
        //stop if speed is too small
        if(Math.abs(vx) < 0.001) vx = 0;
        if(Math.abs(vy) < 0.001) vy = 0;
    }
    
    public boolean isMoving()
    {
        return (vx != 0) && (vy != 0);
    }
    
    public boolean isOutsideAreaOfPlay(GSGame gs)
    {
        return !gs.currentStage.GameArea.contains(x, y);
    }
    
    public boolean checkCollidable()
    {
        return (cx > 0 && cy > 0);
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public Rectangle2D getCollisionArea()
    {
        return new Rectangle2D((int)(x - cx/2), (int)(y - cy/2), cx, cy);
    }
    
    public Rectangle2D getCollisionAreaDelta(double dx, double dy)
    {
        return new Rectangle2D((int)(x + dx - cx/2), (int)(y + dy - cy/2), cx, cy);
    }
}
