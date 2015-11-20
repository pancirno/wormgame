/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

import gameclasses.game.Actor;
import gameclasses.loop.GSGame;

/**
 *
 * @author lukasz
 */
public class MIRVSplit extends Rocket
{
    public MIRVSplit(Actor p, double ix, double iy, double ivx, double ivy) {
        super(p, ix, iy, ivx, ivy);
        windAffected = false;
    }
    
    @Override
    protected Object[] findNearbyObjects(GSGame gs, double destx, double desty, int radius) 
    {
        Object[] obj = super.findNearbyObjects(gs, destx, desty, radius);
        excludeOwnClassObjects(obj);
        return obj;
    }
}
