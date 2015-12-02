/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons.aitracer;

import gameclasses.earthworms.Player;
import gameclasses.earthworms.weapons.Rocket;
import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author pancirno
 */
public class TracerRocket extends Rocket implements IScoredTracer
{
    public boolean simulationFinished = false;
    public boolean selfHarm = false;
    
    public int totalDamage = 0;
    
    public TracerRocket(Actor p, double ix, double iy, double ivx, double ivy) 
    {
        super(p, ix, iy, ivx, ivy);
    }
    
    public void runSimulation(GSGame gs)
    {
        while(!simulationFinished)
            step(gs);
    }
    
    @Override
    public void explode(GSGame gs)
    {
        simulationFinished = true;
        
        Object[] objs = gs.findObjectsInCollisionTree((int)x-50, (int)y-50, (int)x+50, (int)y+50);
        
        for(Object p : objs)
        {
            if(p instanceof Player)
            {
                if(p == parent || ((Player)p).getPlayerTeam() == ((Player)parent).getPlayerTeam()) selfHarm = true;
                totalDamage++;
            }
        }
        
    }

    @Override
    public double getScore() 
    {
        return totalDamage;
    }
    
}
