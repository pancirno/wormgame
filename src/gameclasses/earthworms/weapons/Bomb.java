/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author samsung
 */
public class Bomb extends Grenade 
{

    public Bomb(double ix, double iy, double ivx, double ivy, int ifuse) {
        super(ix, iy, ivx, ivy, 300);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.DIMGRAY);
        loop.GetGraphicsContext().fillOval(anchx-8, anchy-8, 16, 16);
    }
    
    @Override
    public void step(GSGame gs)
    {
        snapToLevel(gs, vx, vy, true);
        
        grenadeBounce(gs, 0.7, 1.1, 0.25);

        fuse--;
        
        if(fuse <= 0)
        {
            gs.spawnExplosion(ExplosionFactory.MakeBigExplosion((int)x, (int)y));
            gs.removeObject(this);
        }
    }
}
