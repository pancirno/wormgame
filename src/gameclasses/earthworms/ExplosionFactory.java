/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

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
    static final Image MediumI;
    static final Image MediumPlusI;
    static final Image LargeI;
    static final Image ExtraLargeI;
    static final Image HugeI;
    
    static
    {
        BulletI = GenerateHoleSprite(19,19);
        MediumI = GenerateHoleSprite(49,49);
        MediumPlusI = GenerateHoleSprite(69,69);
        LargeI = GenerateHoleSprite(99,99);
        ExtraLargeI = GenerateHoleSprite(149,149);
        HugeI = GenerateHoleSprite(255,255);
    }
    
    //red color means level should ignore drawing red
    //blue color means level should remove anything that blue touches
    static Image GenerateHoleSprite(int w, int h) //TODO generate something decent
    {
        Canvas c = new Canvas(w,h);
        GraphicsContext gc = c.getGraphicsContext2D();
        
        //gc.setEffect();
        
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, w, h);
        gc.setFill(Color.BROWN);
        gc.fillOval(0, 0, w, h);
        gc.setFill(Color.BLUE);
        gc.fillOval(3, 3, w-6, h-6);
        
        WritableImage wi = c.snapshot(null, null);
        return (Image)wi;
    }
    
    static public Explosion MakeBlazeExplosion(int x, int y)
    {
        Explosion e = new Explosion(BulletI, x, y, 1, 1, -3, 20);
        e.constDamage = true;
        return e;
    }
    
    static public Explosion MakeBlazeNoDigExplosion(int x, int y)
    {
        Explosion e = new Explosion(null, x, y, 1, 1, -3, 20);
        e.constDamage = true;
        return e;
    }
    
    static public Explosion MakeBulletExplosion(int x, int y)
    {
        Explosion e = new Explosion(BulletI, x, y, 5, 1, -10);
        e.constDamage = true;
        return e;
    }
    
    static public Explosion MakeSmallExplosion(int x, int y)
    {
        Explosion e = new Explosion(MediumI, x, y, 20, 2, -10);
        e.constDamage = true;
        return e;
    }
    
    static public Explosion MakeMediumExplosion(int x, int y)
    {
        Explosion e = new Explosion(MediumPlusI, x, y, 35, 2, -10);
        
        return e;
    }
    
    static public Explosion MakeLargeExplosion(int x, int y)
    {
        Explosion e = new Explosion(LargeI, x, y, 50, 6, -10);
        
        return e;
    }
    
    static public Explosion MakeBigExplosion(int x, int y)
    {
        Explosion e = new Explosion(ExtraLargeI, x, y, 75, 8, -15);
        
        return e;
    }
}
