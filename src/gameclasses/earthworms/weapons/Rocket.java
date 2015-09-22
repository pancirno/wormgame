/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.earthworms.Projectile;
import gameclasses.game.Camera;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasz
 */
public class Rocket extends Projectile 
{
    public Rocket(double ix, double iy, double ivx, double ivy) 
    {
        super(ix, iy, ivx, ivy);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
            
        loop.GetGraphicsContext().fillOval(anchx-6, anchy-6, 12, 12);
    }
}
