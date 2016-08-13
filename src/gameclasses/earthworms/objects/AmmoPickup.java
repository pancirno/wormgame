/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.objects;

import gameclasses.earthworms.Player;
import gameclasses.game.Camera;
import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import javafx.scene.paint.Color;

/**
 *
 * @author pancirno
 */
public class AmmoPickup extends Pickup
{
    String containsWeapon;
    
    public AmmoPickup(GSGame gs, double ix, double iy) {
        super(ix, iy);
        containsWeapon = gs.getLoot();
    }
    
    @Override
    public void render(MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.DARKORANGE);
        loop.GetGraphicsContext().fillRect(anchx-8, anchy-8, 16, 16);
        loop.GetGraphicsContext().setFill(Color.DARKOLIVEGREEN);
        loop.GetGraphicsContext().fillRect(anchx-2, anchy-6, 4, 12);
        loop.GetGraphicsContext().fillRect(anchx-7, anchy-6, 4, 12);
        loop.GetGraphicsContext().fillRect(anchx+3, anchy-6, 4, 12);
    }
    
    @Override
    protected void onPickup(Player player) 
    {
        player.getPlayerTeam().grantAmmo(containsWeapon);
    }
}
