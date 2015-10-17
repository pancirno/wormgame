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
    double ropelength = 0;
    
    public Rope(double srcx, double srcy, double destx, double desty)
    {
        x = destx;
        y = desty;
        
        Point2D plr = new Point2D(srcx, srcy);
        ropelength = plr.distance(x, y);
    }
    
    Point2D calcTension(Player p)
    {
        Point2D plr = new Point2D(p.getX(), p.getY());
        double dist = plr.distance(x, y);
        double pushangle = calcAngle(plr);
        double tenx = Math.cos(pushangle) * -1;
        double teny = Math.sin(pushangle);
        
        //if(dist > ropelength*1.2) return new Point2D(tenx*3,teny*3);
        return new Point2D(tenx, teny);
    }

    public double calcAngle(Point2D plr) 
    {
        return Math.atan2(plr.getX() - x, plr.getY() - y) - Math.PI/2;
    }
}
