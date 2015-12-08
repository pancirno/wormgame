/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.objects;

import gameclasses.earthworms.Player;
import gameclasses.game.Camera;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author pancirno
 */
public class HealthPickup extends Pickup
{
    public HealthPickup(double ix, double iy) {
        super(ix, iy);
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.WHITE);
        loop.GetGraphicsContext().fillRect(anchx-8, anchy-8, 16, 16);
        loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillRect(anchx-2, anchy-8, 4, 16);
        loop.GetGraphicsContext().fillRect(anchx-8, anchy-2, 16, 4);
    }
    
    @Override
    protected void onPickup(Player player) 
    {
        player.dealDamage(-20);
    }
}
