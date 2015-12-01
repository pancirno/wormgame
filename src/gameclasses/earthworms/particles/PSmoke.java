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

public class PSmoke extends Particle
{
    public double radius;
    public double startfuse;
    
    public PSmoke(double ix, double iy)
    {
        super(ix,iy,0,-1 - partrandom.nextDouble()*2,0);
        startfuse = 30+partrandom.nextInt(30);
        timer = (int) startfuse;
        radius = 1+partrandom.nextInt(5);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        y += vy;
        
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
        
        loop.GetGraphicsContext().setFill(Color.rgb(64, 64, 64, Math.max(0.1, (double)timer/startfuse)));
        loop.GetGraphicsContext().fillOval(anchx-radius, anchy-radius, radius*2, radius*2);
        
        super.render(loop, c);
    }
}
    
