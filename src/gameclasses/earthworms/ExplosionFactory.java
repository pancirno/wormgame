/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class ExplosionFactory
{
    public enum ExplosionSize
    {
        Bullet,
        Small,
        Medium,
        Large,
        ExtraLarge,
        Huge,
        Gigantic
    }
    
    static final Image BulletI;
    static final Image SmallI;
    static final Image MediumI;
    static final Image LargeI;
    static final Image ExtraLargeI;
    static final Image HugeI;
    static final Image GiganticI;
    
    static
    {
        BulletI = GenerateHoleSprite(9,9);
        SmallI = GenerateHoleSprite(25,25);
        MediumI = GenerateHoleSprite(50,50);
        LargeI = GenerateHoleSprite(75,75);
        ExtraLargeI = GenerateHoleSprite(128,128);
        HugeI = GenerateHoleSprite(256,256);
        GiganticI = GenerateHoleSprite(384,384);
    }
    
    static Image GenerateHoleSprite(int w, int h) //TODO generate something decent
    {
        WritableImage wi = new WritableImage(w,h);
        
        return (Image)wi;
    }
    
    static public Explosion MakeMediumExplosion(int x, int y)
    {
        Explosion e = new Explosion(MediumI, x, y, 50, 0, 0);
        
        return e;
    }
}
