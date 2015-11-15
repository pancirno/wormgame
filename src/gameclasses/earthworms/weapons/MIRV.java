/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.game.*;
import gameclasses.loop.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class MIRV extends Rocket
{

    public MIRV(Actor p, double ix, double iy, double ivx, double ivy)
    {
        super(p, ix, iy, ivx, ivy);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.PINK);
            
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }

    @Override
    public void step(GSGame gs)
    {
        super.step(gs);
        
        if(vy > 3.5 || fuse <= 0)
        {
            explode(gs);
        }
    }
    
    @Override
    public void explode(GSGame gs)
    {
        gs.spawnProjectile(new Rocket(this,x,y,vx*1.5,vy*1.1));
        gs.spawnProjectile(new Rocket(this,x,y,vx,vy*1.1));
        gs.spawnProjectile(new Rocket(this,x,y,vx*0.5,vy*1.1));
        gs.removeObject(this);
    }
    
}
