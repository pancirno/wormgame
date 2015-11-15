/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.CommonMath;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;
import javafx.geometry.Point2D;

/**
 *
 * @author pancirno
 */
public class HomingMissile extends Rocket
{
    Point2D target;
    
    int burnout = 45;
    
    public HomingMissile(Actor p, double ix, double iy, double ivx, double ivy, int tx, int ty)
    {
        super(p, ix, iy, ivx, ivy);
        
        target = new Point2D(tx,ty);
    }
    
    @Override
    public void step(GSGame gs)
    {
        burnout--;
        
        if(burnout > 0)
        {
            super.step(gs);
        }
        else
        {
            if(burnout == 0)
            {
                vx *= 0.65;
                vy *= 0.65;
            }
            
            double angle = CommonMath.getInvertedDiffAngle(target.getX() - x, target.getY() - y);
            Point2D vec = CommonMath.getDirectionVector(angle);
            
            
            if(target.distance(x, y) > 128)
            {
                vx += vec.getX() * 0.25;
                vy += vec.getY() * 0.25;
            }
            else
            {
                vx = vec.getX() * 1 + vx * 0.9;
                vy = vec.getY() * 1 + vy * 0.9;
            }
            
            fuse--;
        
            if(snapToLevelVel(gs, vx, vy, false) || fuse <= 0)
            {
                explode(gs);
            }
        }
    }
}
