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
    
    static Image GenerateHoleSprite(int w, int h)
    {
        Canvas c = new Canvas(w, h);
        WritableImage wi = new WritableImage(w,h);
        
        c.getGraphicsContext2D().setFill(Color.color(0, 0, 0, 0));
        c.getGraphicsContext2D().fill();
        
        c.getGraphicsContext2D().setFill(Color.color(0.6, 0.4, 0.2, 1));
        c.getGraphicsContext2D().fillOval(0, 0, w, h);
        
        c.getGraphicsContext2D().setFill(Color.color(0, 0, 0, 0));
        c.getGraphicsContext2D().fillOval(4, 4, w-4, h-4);
        
        return (Image)wi;
    }
}
