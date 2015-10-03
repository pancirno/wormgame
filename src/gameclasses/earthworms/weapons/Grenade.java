/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;
import gameclasses.earthworms.*;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Grenade extends Projectile {
    
    int fuse = 180;
    
    public Grenade(double ix, double iy, double ivx, double ivy, int ifuse) 
    {
        super(ix, iy, ivx, ivy);
        fuse = ifuse;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.GREEN);
            
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }
    
    @Override
    public void step(GSGame gs)
    {
        snapToLevel(gs, vx, vy, true);
        
        if(gs.currentStage.Collide(x+1, y) || gs.currentStage.Collide(x-1, y))
        {
            vx = vx * StaticPhysics.TORQUE * -0.5;
        }
        
        if(gs.currentStage.Collide(x, y+1) || gs.currentStage.Collide(x, y-1))
        {
            vy = vy * StaticPhysics.TORQUE * -0.5;
        }
        else
        {
            vy = vy + StaticPhysics.GRAVITY;
        }

        fuse--;
        
        if(fuse <= 0)
        {
            gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion((int)x, (int)y));
            gs.removeObject(this);
        }
    }
    
}
