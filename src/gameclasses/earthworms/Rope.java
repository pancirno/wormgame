/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
import gameclasses.loop.GSGame;
import javafx.geometry.Point2D;

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
        double anglesign = Math.signum(angularvel);
        
        while(true)
        {
            i++; i = Math.min(Math.abs(i), Math.abs(angularvel));
            double angledelta = i/getRadius() * anglesign;
            
            if(gs.currentStage.Collide(getEndPosition(endangle + angledelta).add(x, y)))
            {
                angledelta = (i-1)/getRadius() * anglesign;
                if(gs.currentStage.Collide(getEndPosition(endangle + angledelta).add(x, y)))
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
        return Math.atan2(ix - x, iy - y) - Math.PI/2;
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
        if(gs.currentStage.Collide(getEndPosition(endangle).add(x,y)))
            ropelength += amount;
        
        if(ropelength < 10) ropelength = 10;
    }
    
    public void increaseLength(GSGame gs, double amount)
    {
        ropelength += amount;
        if(gs.currentStage.Collide(getEndPosition(endangle).add(x,y)))
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
}
