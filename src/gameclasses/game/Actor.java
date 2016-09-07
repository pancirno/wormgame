/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import gameclasses.earthworms.Player;
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

    protected int healthPoints = 1;
    protected Object[] nearbyobjects = null;
    protected int fallDamageRatio = 0;
    
    public void render(MainLoop loop, Camera c)
    {
        
    }

    public void step(GSGame gs)
    {
        
    }
    
    public void checkCollide(GSGame gs)
    {
        nearbyobjects = null;
        nearbyobjects = findNearbyObjects(gs, x, y, 64);
    }
    
    public void push(GSGame gs, double ivx, double ivy)
    {
    }
    
    public void push(GSGame gs, Point2D p)
    {
        push(gs, p.getX(), p.getY());
    }
    
    //returns true if snapped to level or object
    protected boolean snapToLevelAbs(GSGame gs, double destx, double desty, boolean snaptype, boolean ignoreobjects)
    {
        double tvx = destx - x;
        double tvy = desty - y;
        
        double checkx = 0, checky = 0;
        double pcheckx, pchecky;
        
        boolean ifcollided = false;
        boolean objectcollide;
        
        double steps = Math.sqrt((x - destx)*(x - destx) + (y-desty)*(y-desty)); //TODO implement this thing https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        
        Object[] colobjects = ignoreobjects ? null : findNearbyObjects(gs, destx, desty, 64);
        
        for(int i = 1; i <= steps; i++)
        {
            pcheckx = checkx;
            pchecky = checky;
            checkx = tvx * ((double)i/steps);
            checky = tvy * ((double)i/steps);
            
            objectcollide = checkForObjectOverlap(colobjects, checkx, checky);
            
            if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(checkx, checky)) || (objectcollide && !ignoreobjects))
            {
                ifcollided = true;
                                
                destx = x + ((snaptype) ? checkx : pcheckx);
                desty = y + ((snaptype) ? checky : pchecky);
                
                break;
            }
        }
        
        x = destx;
        y = desty;
        
        return ifcollided;
    }
    
    //returns true if snapped to level or object
    protected boolean snapToLevelVel(GSGame gs, double tvx, double tvy, boolean snaptype, boolean ignoreobjects)
    {
        return snapToLevelAbs(gs, x + tvx, y + tvy, snaptype, ignoreobjects);
    }

    protected boolean checkForObjectOverlap(Object[] colobjects, double checkx, double checky)
    {
        if(colobjects == null) return false;
        
        for(Object o : colobjects)
            if(o instanceof Actor && o != this && o != parent)
                if(((Actor)o).getCollisionArea().intersects(getCollisionAreaDelta(checkx, checky)))
                    return true;
        
        return false;
    }
    
    protected Object[] findNearbyObjects(GSGame gs, double destx, double desty, int radius) 
    {
        return gs.findObjectsInCollisionTree((int)x-radius, (int)y-radius, (int)destx+radius, (int)desty+radius);
        //return colobjects;
    }
    
    protected void excludeOwnClassObjects(Object[] obj) 
    {
        for(int i = 0; i < obj.length; i++)
        {
            if(obj[i].getClass() == this.getClass())
                obj[i] = new Object();
        }
    }
    
    protected void excludePlayerClassObjects(Object[] obj) 
    {
        for(int i = 0; i < obj.length; i++)
        {
            if(obj[i].getClass() == Player.class)
                obj[i] = new Object();
        }
    }
    
    protected void grenadeBounce(GSGame gs, double impactred, double rollred, double bouncered, boolean ignoreobjects)
    {
        //horizontal bounce
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(-1 * vx, 0)) || (!ignoreobjects && checkForObjectOverlap(nearbyobjects, -1 * vx, 0)) )
        {
            vx = vx * StaticPhysics.TORQUE * rollred;
        }
        else if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(vx, 0)) || (!ignoreobjects && checkForObjectOverlap(nearbyobjects, vx, 0)) )
        {
            vx = vx * StaticPhysics.TORQUE * -1 * impactred;
        }
            
        //vertical bounce
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, vy)) || (!ignoreobjects && checkForObjectOverlap(nearbyobjects, 0, vy)) )
        {
            if(Math.abs(vy) >= 5) healthPoints -= fallDamageRatio * Math.max(0,vy-5);
            if(vy > 0) vy -= StaticPhysics.GRAVITY; //if we sit on ground we compensate for gravity
            vy = vy * StaticPhysics.TORQUE * -1 * bouncered; //0.5
            vx = (vx * 0.75) + (vx * StaticPhysics.TORQUE * rollred * 0.25);
        }
        else if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, -1 * vy)) || (!ignoreobjects && checkForObjectOverlap(nearbyobjects, 0, -1 * vy)) )
        {
        }
        else
        {
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
    
    public double getVX()
    {
        return vx;
    }
    
    public double getVY()
    {
        return vy;
    }
    
    public Point2D getPoint2D()
    {
        return new Point2D(x,y);
    }
    
    public Rectangle2D getCollisionArea()
    {
        return new Rectangle2D(x - cx/2, y - cy/2, cx, cy);
    }
    
    public Rectangle2D getCollisionAreaDelta(double dx, double dy)
    {
        return new Rectangle2D(x + dx - cx/2, y + dy - cy/2, cx, cy);
    }
    
    public void setVelocity(double nvx, double nvy, boolean add)
    {
        if(add)
        {
            vx += nvx;
            vy += nvy;
        }
        else
        {
            vx = nvx;
            vy = nvy;
        }
    }
}
