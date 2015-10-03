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
        
        if(gs.currentStage.Collide(x+(-1 * Math.signum(vx)), y))
        {
            vx = vx * StaticPhysics.TORQUE * 0.90;
        }
        else if(gs.currentStage.Collide(x+(1 * Math.signum(vx)), y))
        {
            vx = vx * StaticPhysics.TORQUE * -0.90;
        }
            
        //vertical bounce
        if(gs.currentStage.Collide(x, y+1))
        {
            vy = vy * StaticPhysics.TORQUE * -0.5;
        }
        else if(gs.currentStage.Collide(x, y-1))
        {
            vy = Math.abs(vy + StaticPhysics.GRAVITY);
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
