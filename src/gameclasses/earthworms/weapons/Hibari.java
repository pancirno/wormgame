/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.ExplosionFactory;
import gameclasses.earthworms.Projectile;
import gameclasses.earthworms.StaticPhysics;
import gameclasses.game.Actor;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author pancirno
 */
public class Hibari extends Projectile
{
    public Hibari(Actor p, double ix, double iy, double ivx, double ivy)
    {
        initProjectile(p, ix, iy, ivx, ivy);
        cx = 96;
        cy = 2;
        weight = 10;
    }
    
    @Override
    public void step(GSGame gs)
    {
        fuse--;
        vx = 0;
        vy = vy + StaticPhysics.GRAVITY;
                
        if(snapToLevelVel(gs, vx, vy, true, true))
        {
            explode(gs);
        }
        
        super.step(gs);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.LIGHTPINK);
        loop.GetGraphicsContext().fillOval(anchx-64, anchy-64, 128, 128);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        ExplosionFactory.MakeBigExplosion(gs, (int)x, (int)y);
        vy = -1;
    }
}
