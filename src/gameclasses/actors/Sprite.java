/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.actors;

import java.util.*;
import javafx.scene.image.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class Sprite
{
    protected List<Image> imageStates;
    protected ImageView spriteFrame;
                
    public Sprite(String SVG, List<Image> images)
    {
        imageStates = images;
        spriteFrame = new ImageView(images.get(0));
    }
    
    public void DrawSprite(MainLoop loop)
    {
        
    }
}
