/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.objects;

import gameclasses.earthworms.LevelObject;
import gameclasses.earthworms.Player;
import gameclasses.earthworms.StaticPhysics;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class Pickup extends LevelObject
{

    public Pickup(double ix, double iy) 
    {
        x = ix;
        y = iy;
        healthPoints = 1;
    }
    
    @Override
    public void step(GSGame gs)
    {
        vy += StaticPhysics.GRAVITY;
        grenadeBounce(gs, 0.25, 0.25, 0.25, true);
        snapToLevelVel(gs, vx, vy, true, false);
        
        Object[] playercol = gs.findObjectsInCollisionTree((int)x-8, (int)y-8, (int)x+8, (int)y+8);
        for(Object o : playercol)
        {
            if(o instanceof Player)
            {
                onPickup((Player)o);
                gs.removeObject(this);
                return;
            }
        }
        
        if(healthPoints == 0)
        {
            //ExplosionFactory.MakeMediumExplosion(gs, (int)x, (int)y);
            gs.removeObject(this);
        }
        
        super.step(gs);
    }
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        healthPoints = 0;
    }
   
    protected void onPickup(Player player) 
    {

    }
    
}
