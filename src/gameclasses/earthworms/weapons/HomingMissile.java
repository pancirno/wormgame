/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.CommonMath;
import gameclasses.earthworms.StaticPhysics;
import gameclasses.loop.GSGame;
import javafx.geometry.Point2D;

/**
 *
 * @author pancirno
 */
public class HomingMissile extends Rocket
{
    Point2D target;
    
    int burnout = 90;
    
    public HomingMissile(double ix, double iy, double ivx, double ivy, int tx, int ty)
    {
        super(ix, iy, ivx, ivy);
        
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
                vx /= 2;
                vy /= 2;
            }
            
            double angle = CommonMath.getInvertedDiffAngle(target.getX() - x, target.getY() - y);
            Point2D vec = CommonMath.getDirectionVector(angle);
            
            vx += vec.getX() * 0.30;
            vy += vec.getY() * 0.30;
            
            fuse--;
        
            snapToLevelVel(gs, vx, vy, false);

            if(gs.currentStage.Collide(x, y) || fuse <= 0)
            {
                explode(gs);
            }
        }
    }
}
