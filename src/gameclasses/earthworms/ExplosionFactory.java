/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.particles.PExplosion;
import gameclasses.loop.GSGame;
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
        None,
        Bullet,
        Small,
        Medium,
        Large,
        ExtraLarge,
        Huge,
        Gigantic
    }
    
    static final Image SmallI;
    static final Image MediumI;
    static final Image LargeI;
    static final Image ExtraLargeI;
    static final Image HugeI;
    static final Image GiganticI;
    
    static
    {
        SmallI = GenerateHoleSprite(19,19);
        MediumI = GenerateHoleSprite(49,49);
        LargeI = GenerateHoleSprite(69,69);
        ExtraLargeI = GenerateHoleSprite(99,99);
        HugeI = GenerateHoleSprite(149,149);
        GiganticI = GenerateHoleSprite(255,255);
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
    
    static public void MakeCustomExplosion(GSGame gs, int x, int y, ExplosionSize sprite, int damage, double power, int bias, double damageradius)
    {
        Image eImg = null;
        
        switch(sprite)
        {
            case Small:
                eImg = SmallI;
                break;
            case Medium:
                eImg = MediumI;
                break;
            case Large:
                eImg = LargeI;
                break;
            case ExtraLarge:
                eImg = ExtraLargeI;
                break;
            case Huge:
                eImg = HugeI;
                break;
            case Gigantic:
                eImg = GiganticI;
                break;
            case None:
            default:
            
        }
        
        Explosion e = new Explosion(eImg, gs, x, y, damage, power, bias, damageradius);
        //e.constDamage = true;
        
        gs.spawnExplosion(e);
    }
    
    static public void MakeBlazeExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(SmallI, gs, x, y, 1, 1, -3, 20);
        e.constDamage = true;
        
        gs.spawnExplosion(e);
    }
    
    static public void MakeBlazeNoDigExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(null, gs, x, y, 1, 1, -3, 20);
        e.constDamage = true;
        
        gs.spawnExplosion(e);
    }
    
    static public void MakeBulletExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(SmallI, gs, x, y, 5, 1, -10, -1);
        e.constDamage = true;
        
        gs.spawnParticle(new PExplosion(x,y, 10));
        gs.spawnExplosion(e);
    }
    
    static public void MakeSmallExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(MediumI, gs, x, y, 20, 3, -10, -1);
        e.constDamage = true;
        
        gs.spawnParticle(new PExplosion(x,y, 25));
        gs.spawnExplosion(e);
    }
    
    static public void MakeMediumExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(LargeI, gs, x, y, 35, 3, -10, -1);
        
        gs.spawnParticle(new PExplosion(x,y, 35));
        gs.spawnExplosion(e);
    }
    
    static public void MakeLargeExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(ExtraLargeI, gs, x, y, 50, 6, -10, -1);
        gs.playSound("sfx/boom1.wav");
        gs.spawnParticle(new PExplosion(x,y, 50));
        gs.spawnExplosion(e);
    }
    
    static public void MakeBigExplosion(GSGame gs, int x, int y)
    {
        Explosion e = new Explosion(HugeI, gs, x, y, 75, 8, -15, -1);
        gs.spawnParticle(new PExplosion(x,y, 75));
        
        gs.spawnExplosion(e);
    }
}
