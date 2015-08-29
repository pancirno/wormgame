/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import java.util.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;

/**
 *
 * @author pancirno
 */
public abstract class Sprite
{
    protected List<Image> imageStates;
    protected ImageView spriteFrame;
    protected SVGPath collisionMask;
            
    public Sprite(String SVG, List<Image> images)
    {
        collisionMask = new SVGPath();
        collisionMask.setContent(SVG);
        imageStates = images;
        spriteFrame = new ImageView(images.get(0));
    }
}
