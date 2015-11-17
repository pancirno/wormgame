/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Projectile;
import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class RopeConnector extends Projectile 
{
    public boolean impact = false;
    
    public RopeConnector(Actor p, double ix, double iy, double ivx, double ivy, int traveltime) {
        super(p, ix, iy, ivx, ivy);
        fuse = traveltime;
        
        cx = 4;
        cy = 4;
    }
    
    @Override
    public void step(GSGame gs)
    {
        fuse --;
        
        if(snapToLevelVel(gs, vx, vy, false, true))
        {
            explode(gs);
        }
        
        if(fuse <= 0)
        {
            gs.removeObject(this);
        }
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.WHITE);
        loop.GetGraphicsContext().fillOval(anchx-8, anchy-8, 16, 16);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        impact = true;
        gs.removeObject(this);
    }
}
