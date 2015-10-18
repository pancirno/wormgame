/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Actor;
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
    
    public Point2D step()
    {
        Point2D start = getEndPosition();
        
        if(Math.cos(endangle) < 0)
            angularvel += Math.abs(Math.cos(endangle));
        else
            angularvel -= Math.abs(Math.cos(endangle));
        
        endangle += angularvel/getRadius();
        
        Point2D delta = getEndPosition();
        
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
    
    public void decreaseLength(double amount)
    {
        ropelength -= amount;
        if(ropelength < 10) ropelength = 10;
    }
    
    public void increaseLength(double amount)
    {
        ropelength += amount;
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
    
    public Point2D getEndPosition()
    {
        return new Point2D(Math.cos(endangle) * ropelength, Math.sin(endangle)*-1*ropelength);
    }
    
    public Point2D getVelocityVector()
    {
        return new Point2D(approxvx, approxvy);
    }
}
