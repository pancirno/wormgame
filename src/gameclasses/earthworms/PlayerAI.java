/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.WeaponInfo.AvailableWeapons;
import gameclasses.earthworms.weapons.aitracer.TracerRocket;
import gameclasses.loop.GSGame;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import wormgame.InputEngine;

/**
 *
 * @author pancirno
 */
public class PlayerAI extends Player
{
    boolean thought = false;
    
    double powerstep = -1;
    
    public PlayerAI(int ix, int iy, Team it, int id, String name)
    {
        super(ix, iy, it, id, name);
    }
    
    @Override
    public void SelectPlayer()
    {
        super.SelectPlayer();
        thought = false;
        powerstep = 0.3;
    }
    
    @Override
    public void step(GSGame gs)
    {    
        super.step(gs);
        if(!isCurrentlySelected) return;
        if(!thought)think(gs);
        shoot = thought;
    }
    
    @Override
    public void move(InputEngine ie)
    {
        
    }

    private void think(GSGame gs)
    {
        bruteBehaviour(gs);
    }
    
    private void bruteBehaviour(GSGame gs)
    {        
        equippedGun = AvailableWeapons.ROCKET;
        
        TracerRocket t;
        
        for(double d = 0; d <= Math.PI*2; d+=Math.PI/45)
        {
            aimangle = d;
            aimpower = MAX_SHOOT_POWER * powerstep;

            configureAiming();

            t = new TracerRocket(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr);
            t.runSimulation(gs);

            if(!t.selfHarm && t.getScore() > 0)
            {
                thought = true;
                return;
            }
        }
        
        powerstep+=0.1;
        
        if(powerstep > 1)
            basicBehaviour(gs);
    }

    private void basicBehaviour(GSGame gs) {
        //consider players
        List<Player> players = gs.getPlayerList();
        Player selectedtarget = closestPlayer(players);
        if (selectedtarget == null) 
        {
            thought = true;
            return;
        }
        thought = true;
        aimangle = CommonMath.getInvertedDiffAngle(selectedtarget.getX() - x, selectedtarget.getY() - y) * -1;
        aimpower = MAX_SHOOT_POWER;
        //CASE - enemy can be shoot directly
        if(Point2D.distance(x, y, selectedtarget.getX(), selectedtarget.getY()) > 100)
            equippedGun = (AvailableWeapons)WeaponInfo.AIDirect.toArray()[gs.getRandomInt(WeaponInfo.AIDirect.size())];
        else
            equippedGun = (AvailableWeapons)WeaponInfo.AIClose.toArray()[gs.getRandomInt(WeaponInfo.AIClose.size())];
    }

    private Player closestPlayer(List<Player> players)
    {
        //find closest enemy
        Player selectedtarget = null;
        HashMap<Player, Double> distances = new HashMap<>();
        
        for(Player p : players)
        {
            if(p.playerTeam == this.playerTeam) continue;
            
            distances.put(p, Point2D.distance(x, y, p.getX(), p.getY()));
        }
        
        Entry<Player, Double> min = null;
        for (Entry<Player, Double> entry : distances.entrySet())
            if (min == null || min.getValue() > entry.getValue()) 
                min = entry;
        
        if(min != null)
            selectedtarget = min.getKey();
        
        return selectedtarget;
    }
}
