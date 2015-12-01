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
public class PRibbon extends Particle
{
    Color c1, c2;
    
    public PRibbon(double ix, double iy, double ivx, double ivy, Color colorf, Color colors)
    {
        super(ix,iy,ivx,ivy,4);
        c1 = colorf;
        c2 = colors;
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
        
        loop.GetGraphicsContext().setStroke(c1);
        loop.GetGraphicsContext().strokeLine(anchx, anchy, anchx + vx, anchy + vy);
        
        x += vx;
        y += vy;
        
        super.render(loop, c);
    }
}
