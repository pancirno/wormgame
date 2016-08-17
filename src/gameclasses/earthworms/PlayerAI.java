/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.Weapon;
import gameclasses.earthworms.weapons.aitracer.IScoredTracer;
import gameclasses.loop.GSGame;
import java.util.HashMap;
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
    double anglestep = -1;
    
    AIState currentAIState;
    
    enum AIState
    {
        TryStraight,
        TryGrenade,
        TryRocket,
        ChangeLocation,
        AnythingGoes,
        GiveUp
    }
    
    public PlayerAI(int ix, int iy, Team it, int id, String name)
    {
        super(ix, iy, it, id, name);
    }
    
    @Override
    public void SelectPlayer()
    {
        super.SelectPlayer();
        thought = false;
        currentAIState = AIState.TryRocket;
        resetAimIteration();
    }
    
    @Override
    public void step(GSGame gs)
    {    
        super.step(gs);
        if(!isCurrentlySelected) return;
        if(!thought)think(gs);
        
    }
    
    @Override
    public void move(GSGame gs, InputEngine ie)
    {
        
    }

    private void think(GSGame gs)
    {
        switch(currentAIState)
        {
            case TryRocket:
            case TryGrenade:
                bruteTryTracer(gs);
                break;
            
            default:
                basicBehaviour(gs);
        
        }
        
        if(thought) 
        {
            switchWeapon();
            tryShooting(gs);
            
            if(equippedGunData.getAIFlags().contains(Weapon.AIWeaponFlags.AIRepeatable))
            {
                thought = false;
                resetAimIteration();
            }
        }
    }
    
    private void resetAimIteration()
    {
        powerstep = 0.3;
    }
    
    private void switchState()
    {
        if(currentAIState == AIState.TryRocket) { currentAIState = AIState.TryGrenade; return; }
        if(currentAIState == AIState.TryGrenade) { currentAIState = AIState.TryStraight; }
    }
    
    private void switchWeapon()
    {
//        if(currentAIState == AIState.TryGrenade) { equippedGun = AvailableWeapons.GRENADE; return; }
//        if(currentAIState == AIState.TryRocket) { equippedGun = AvailableWeapons.ROCKET; }
    }
    
    private void bruteTryTracer(GSGame gs)
    {        
        IScoredTracer tr = null;
        
        for(double d = 0; d <= Math.PI*2; d+=Math.PI/45)
        {
            aimangle = d;
            aimpower = MAX_SHOOT_POWER * powerstep;

            //configureAiming();
            
            switch(currentAIState)
            {
                case TryGrenade:
                    //tr = new TracerGrenade(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr);
                    //tr.runSimulation(gs);
                    break;
                case TryRocket:
                    //tr = new TracerRocket(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr);
                    //tr.runSimulation(gs);
                    break;
            }
            
            if(tr == null) return;
            
            if(tr.getScore() > 0)
            {
                thought = true;
                return;
            }
        }
        
        powerstep+=0.1;
        
        if(powerstep > 1)
        {
            resetAimIteration();
            switchState();
        }
    }

    private void basicBehaviour(GSGame gs) {
        //consider players
        Player selectedtarget = closestPlayer(gs.getPlayerList());
        if (selectedtarget == null) 
        {
            thought = true;
            return;
        }
        thought = true;
        aimangle = CommonMath.getInvertedDiffAngle(selectedtarget.getX() - x, selectedtarget.getY() - y) * -1;
        aimpower = MAX_SHOOT_POWER;
        //CASE - enemy can be shoot directly
        if(CommonMath.distance(x, y, selectedtarget.getX(), selectedtarget.getY()) > 100)
        {
            
        }
//            equippedGun = AvailableWeapons.ROCKET;
        else
        {
            
        }
//            equippedGun = AvailableWeapons.SHOTGUN;
    }

    private Player closestPlayer(Player[] players)
    {
        //find closest enemy
        Player selectedtarget = null;
        HashMap<Player, Double> distances = new HashMap<>();
        
        for(Player p : players)
        {
            if(p.playerTeam == this.playerTeam) continue;
            
            distances.put(p, CommonMath.distance(x, y, p.getX(), p.getY()));
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
