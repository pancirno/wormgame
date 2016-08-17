/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms.weapons;

/**
 *
 * @author lukasz
 */
public class RopeConnector extends Projectile
{
    protected boolean _impact = false;
    
    public boolean ifImpacted()
    {
        return _impact;
    }
}
