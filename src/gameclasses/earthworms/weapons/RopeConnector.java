/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Projectile;
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
    
    public RopeConnector(double ix, double iy, double ivx, double ivy, int traveltime) {
        super(ix, iy, ivx, ivy);
        fuse = traveltime;
    }
    
    @Override
    public void step(GSGame gs)
    {
        fuse --;
        snapToLevel(gs, vx, vy, false);
        
        if(gs.currentStage.Collide(x, y))
        {
            gs.removeObject(this);
            return;
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
}
