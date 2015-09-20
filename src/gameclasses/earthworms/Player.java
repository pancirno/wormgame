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
    int x = 500;
    int y;
    
    boolean shouldExplode = false;
    
    //user movement
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean isFalling = false;
    boolean faceDirection = false;
    
    //physics
    boolean freeFall = false;
    double vx = 0;
    double vy = 0;
    
    public void step(GSGame gs)
    {
        if(shouldExplode)
        {
            shouldExplode = false;
            gs.spawnExplosion(ExplosionFactory.MakeMediumExplosion(x,y));
        }
        
        if(!freeFall) //handle by player movement
        {
            if(moveLeft)
            {
                doWalking(gs, false, 1);
            }
            if(moveRight)
            {
                doWalking(gs, true, 1);
            }
        
            doFalling(gs);
        }
        else //free fall
        {
            
        }
        
        
        
    }
    
    public void render(MainLoop loop, Camera c)
    {
        //Rectangle2D col = new Rectangle2D(x,y,24,24);
        //if on camera costam;
        
        int anchx = c.GetCameraDeltaX(x);
        int anchy = c.GetCameraDeltaY(y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
            
        loop.GetGraphicsContext().fillOval(anchx-12, anchy-12, 24, 24);
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
            faceDirection = false;
            moveLeft = true;
        }
        if(ie.keyStatus(KeyCode.RIGHT) == true)
        {
            faceDirection = true;
            moveRight = true;
        }
        if(ie.keyStatus(KeyCode.Z) == true)
        {
            shouldExplode = true;
        }
    }
    
    void doWalking(GSGame gs, boolean dir, int speed)
    {
        if(isFalling) return;
        
        int sign = 1;
        if(dir == false) sign = -1;
        
        while(speed > 0)
            {
                if(gs.currentStage.Collide(x  + (speed * sign), y))
                {
                    speed--;
                }
                else
                {
                    x += speed * sign;
                    return;
                }
                
                if(speed == 0)
                {
                    for(int climb = 1; climb <= 8; climb++)
                    {
                        if(!gs.currentStage.Collide(x, y-climb))
                        {
                            if(!gs.currentStage.Collide(x + 1 * sign , y-climb));
                            {
                                y = y-climb;
                                x = x+1*sign;
                                break;
                            }
                        }
                    }
                }
            }
    }

    void doFalling(GSGame gs)
    {
        if(isFalling)
        {
            if(gs.currentStage.Collide(x, y+1))
            {
                isFalling = false;
                return;
            }
            else
            {
                y+=1;
                return;
            }
        }
        
        if(gs.currentStage.Collide(x, y+1))
        {
            isFalling = false;
        }
        else
        {
            for(int fall = 2; fall <= 8; fall++)
            {
                if(gs.currentStage.Collide(x, y+fall))
                {
                    y = y+fall-1;
                    isFalling = false;
                    return;
                }
            }
            
            isFalling = true;
            y+=1;
        }
    }
}
