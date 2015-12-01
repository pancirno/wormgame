/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.particles.PSmoke;
import gameclasses.game.Particle;
import gameclasses.loop.GSGame;
import javafx.scene.image.*;

/**
 *
 * @author pancirno
 */
public class Explosion
{
    public final Image explosionSprite;
    
    public final int x;
    public final int y;
    
    public int drawx;
    public int drawy;
    
    public final int damage;
    public final double power;
    public final int bias;
    
    public double hurtRadius;
    public boolean constDamage;
    
    public Explosion(Image es, GSGame gs, int xi, int yi, int dmg, double pow, int ibias)
    {
        explosionSprite = es;
        
        x = xi;
        y = yi;
        
        hurtRadius = 0;
        drawx = 0;
        drawy = 0;
        
        if(explosionSprite != null)
        {
            hurtRadius = es.getWidth()*0.75;
            drawx = xi - (int)es.getWidth()/2;
            drawy = yi - (int)es.getHeight()/2;
            
            if(explosionSprite.getWidth() >= 25)
            for(double i = explosionSprite.getWidth()/3; i >= 0; i--)
                gs.spawnParticle(new PSmoke(x - es.getWidth()*0.5 + Particle.partrandom.nextDouble()*es.getWidth(),y - es.getHeight()*0.5 + Particle.partrandom.nextDouble()*es.getHeight()));

        }
        
        damage = dmg;
        power = pow;
        bias = ibias;
        
        constDamage = false;
        
    }
    
    public Explosion(Image es, GSGame gs, int xi, int yi, int dmg, double pow, int ibias, double hurtrad)
    {
        this(es, gs, xi, yi, dmg, pow, ibias);
        hurtRadius = hurtrad;
    }
    
}
