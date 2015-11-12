/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.*;
import gameclasses.game.*;
import gameclasses.loop.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class Flamethrower extends Rocket
{    
    public Flamethrower(double ix, double iy, double ivx, double ivy)
    {
        super(ix, iy, ivx, ivy);
        fuse = 30;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillOval(anchx-4, anchy-4, 8, 8);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnExplosion(ExplosionFactory.MakeBlazeExplosion((int)x, (int)y));
        gs.spawnProjectile(new Fire(x, y, vx, vy, (int)(gs.getRandomNumber()*50) + 200));
        gs.removeObject(this);
    }
}
