/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.loop.GSGame;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author lukasz
 */
public class Rope extends Actor
{
    private final double maxropelength;
    private double ropelength;
    private double endangle;
    
    private double angularvel = 0;
    
    private double approxvx = 0;
    private double approxvy = 0;
    
    public Rope(double maxrope, double ropelen, double destx, double desty, Player p)
    {
        x = destx;
        y = desty;
        
        ropelength = ropelen;
        maxropelength = maxrope;
        
        endangle = calcAngle(p.getX(), p.getY());
    }
    
    public Point2D calcPlayerPos(GSGame gs)
    {
        Point2D start = getEndPosition(endangle);
        
        if(Math.cos(endangle) < 0)
            angularvel += Math.abs(Math.cos(endangle));
        else
            angularvel -= Math.abs(Math.cos(endangle));
        
        //double angledelta = angularvel/getRadius();
        double i = 0;
        double previousi = 0;
        double anglesign = Math.signum(angularvel);
        
        while(true)
        {
            previousi = i;
            i++; i = Math.min(Math.abs(i), Math.abs(angularvel));
            double angledelta = i/getRadius() * anglesign;
            Point2D p2delta = getEndPosition(endangle + angledelta);
            
            if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(p2delta.getX(), p2delta.getY())))
            {
                angledelta = (previousi)/getRadius() * anglesign;
                p2delta = getEndPosition(endangle + angledelta);
                if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(p2delta.getX(), p2delta.getY())))
                {
                    angularvel = 0;
                    break;
                }
                
                endangle += angledelta;
                angularvel = angularvel * -1 * 0.9;
                break;
            }
            
            if(Math.abs(i) >= Math.abs(angularvel))
            {
                endangle += angledelta;
                break;
            }
        }
        
        Point2D delta = getEndPosition(endangle);
        
        approxvx = delta.getX() - start.getX();
        approxvy = delta.getY() - start.getY();
        
        return delta.add(x,y);
    }
        
    private double calcAngle(double ix, double iy) 
    {
        //return Math.atan2(ix - x, iy - y) - Math.PI/2;
        return CommonMath.getInvertedDiffAngle(ix - x, iy - y);
    }
    
    public double getRadius()
    {
        return 2 * Math.PI * ropelength;
    }
    
    public double getLength()
    {
        return ropelength;
    }
    
    public void decreaseLength(GSGame gs, double amount)
    {
        ropelength -= amount;
        Point2D endp = getEndPosition(endangle);
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(endp.getX(), endp.getY())))
            ropelength += amount;
        
        if(ropelength < 10) ropelength = 10;
    }
    
    public void increaseLength(GSGame gs, double amount)
    {
        ropelength += amount;
        Point2D endp = getEndPosition(endangle);
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(endp.getX(), endp.getY())))
            ropelength -= amount;
        
        if(ropelength > maxropelength) ropelength = maxropelength;
    }
    
    public void swingLeft(double amount)
    {
        angularvel -= amount;
    }
    
    public void swingRight(double amount)
    {
        angularvel += amount;
    }
    
    public Point2D getEndPosition(double ang)
    {
        return new Point2D(Math.cos(ang) * ropelength, Math.sin(ang)*-1*ropelength);
    }
    
    public Point2D getVelocityVector()
    {
        return new Point2D(approxvx, approxvy);
    }
    
    @Override
    public Rectangle2D getCollisionArea()
    {
        return new Rectangle2D(x - Player.BOX_WIDTH/2, y - Player.BOX_HEIGHT, Player.BOX_WIDTH, Player.BOX_HEIGHT);
    }
    
    @Override
    public Rectangle2D getCollisionAreaDelta(double dx, double dy)
    {
        return new Rectangle2D((x + dx) - Player.BOX_WIDTH/2, (y + dy) - Player.BOX_HEIGHT, Player.BOX_WIDTH, Player.BOX_HEIGHT);
    }
    
    //
}
