/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.game.Actor;
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

    public Bomb(Actor p, double ix, double iy, double ivx, double ivy, int ifuse) {
        super(p, ix, iy, ivx, ivy, 300);
        weight = 2;
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
        fuse--;
        
        snapToLevelVel(gs, vx, vy, true, false);
        
        grenadeBounce(gs, 0.7, 1.1, 0.25);

        if(fuse <= 0)
        {
            explode(gs);
        }
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeBigExplosion((int)x, (int)y));
        gs.removeObject(this);
    }
}
