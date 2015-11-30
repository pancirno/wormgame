/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.particles;

import gameclasses.game.Camera;
import gameclasses.game.Particle;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author pancirno
 */
public class PExplosion extends Particle
{
    public double radius;
    
    public PExplosion(double ix, double iy, double r)
    {
        super(ix,iy,0,0,20);
        radius = r;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
        
        loop.GetGraphicsContext().setFill(Color.rgb(255, 255, 255, Math.max(0.1, (double)timer/20)));
        loop.GetGraphicsContext().fillOval(anchx-radius, anchy-radius, radius*2, radius*2);
        
        super.render(loop, c);
    }
}
