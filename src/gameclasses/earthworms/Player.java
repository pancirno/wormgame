/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.*;
import gameclasses.loop.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class Player
{
    int x;
    int y;
    
    boolean shouldExplode = false;
    
    boolean moveLeft = false;
    boolean moveRight = false;
    
    public void step(GSGame gs)
    {
        if(shouldExplode)
        {
            shouldExplode = false;
            gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion(x,y));
        }
        
        if(moveLeft)
        {
            x-=5;
        }
        
        if(moveRight)
        {
            x+=5;
        }
    }
    
    public void render(MainLoop loop, Camera c)
    {
        //Rectangle2D col = new Rectangle2D(x,y,24,24);
        //if on camera costam;
        
        int anchx = c.GetCameraDeltaX(x);
        int anchy = c.GetCameraDeltaY(y);
        
        loop.GetGraphicsContext().setFill(Color.BLANCHEDALMOND);
        loop.GetGraphicsContext().fillOval(anchx, anchy, 24, 24);
    }

    public void move(InputEngine ie)
    {
        //reset status
        moveLeft = false;
        moveRight = false;
        
        //call for actions
        if(ie.keyStatus(KeyCode.UP) == true)
        {
            y -= 5;
        }
        if(ie.keyStatus(KeyCode.DOWN) == true)
        {
            y += 5;
        }
        if(ie.keyStatus(KeyCode.LEFT) == true)
        {
            moveLeft = true;
        }
        if(ie.keyStatus(KeyCode.RIGHT) == true)
        {
            moveRight = true;
        }
        if(ie.keyStatus(KeyCode.Z) == true)
        {
            shouldExplode = true;
        }
    }
}
