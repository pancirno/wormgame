/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

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
    
    public final int drawx;
    public final int drawy;
    
    public final int damage;
    public final double power;
    public final double powerBias;
    
    public final double radius;
    
    public Explosion(Image es, int xi, int yi, int dmg, double pow, double bias)
    {
        explosionSprite = es;
        radius = es.getWidth();
        
        x = xi;
        y = yi;
        
        drawx = xi - (int)es.getWidth()/2;
        drawy = yi - (int)es.getHeight()/2;
        
        damage = dmg;
        power = pow;
        powerBias = bias;
    }
}
