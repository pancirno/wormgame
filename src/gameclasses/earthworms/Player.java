/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.*;
import gameclasses.game.*;
import gameclasses.loop.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class Player extends Actor
{
    //action types
    enum Jump
    {
        FORWARD,
        BACKWARD
    }
    
    enum PlayerState
    {
        IDLE,
        JUMPING,
        FALLING,
        FREEFALL,
        RAGDOLL
    }
    
    enum AvailableWeapons
    {
        ROCKET,
        GRENADE,
        BOMB,
        MINIGUN,
        SHOTGUN,
        RIFLE,
        BLOWTORCH,
        ROPE,
        BALLOON,
        HIBARI
    }
    
    PlayerState currentState = PlayerState.IDLE;
    
    //user movement
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean isFalling = false;
    boolean faceDirection = false;
    
    //jump
    boolean wantToJump = false;
    Jump directionToJump = Jump.FORWARD;
    
    //shooting
    AvailableWeapons equippedGun = AvailableWeapons.ROCKET;
    double aimangle = 0;
    double aimpower = 0; //max 15
    boolean shoot = false;
    
    public Player()
    {
        x = 500;
    }
    
    @Override
    public void step(GSGame gs)
    {       
        if(currentState == PlayerState.IDLE) //handle by player movement
        {
            vx = 0;//clear any velocities
            vy = 0;
            
            doFalling(gs);
            
            if(moveLeft)
            {
                doWalking(gs, 1);
            }
            if(moveRight)
            {
                doWalking(gs, 1);
            }
            if(wantToJump)
            {
                doJumping();
            }
            
            if(shoot)
            {
                double horizaim = x + Math.cos(aimangle) * 5;
                double vertaim = y + Math.sin(aimangle) * 5;
                double horizthr = Math.cos(aimangle) * aimpower;
                double vertthr = Math.sin(aimangle) * aimpower;
                
                switch(equippedGun)
                {
                    case ROCKET:
                        gs.spawnProjectile(new MIRV(horizaim, vertaim, horizthr, vertthr));
                        break;
                    case GRENADE:
                        gs.spawnProjectile(new Grenade(horizaim, vertaim, horizthr, vertthr, 180));
                        break;
                    case BOMB:
                        gs.spawnProjectile(new Bomb(horizaim, vertaim, 1.2 * Math.signum(horizthr), -1.5, -1));
                        break;
                }
                
                aimpower = 0;
                shoot = false;
            }
            
        }
        else if(currentState == PlayerState.FREEFALL) //free fall
        {
            doFreeFall(gs);
            
            wantToJump = false;//lock jumping to not jump around
        }
    }

    @Override
    public void render(MainLoop loop, Camera c)
    {
        //Rectangle2D col = new Rectangle2D(x,y,24,24);
        //if on camera costam;
        
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        loop.GetGraphicsContext().setFill(Color.RED);
        loop.GetGraphicsContext().fillOval(anchx-12, anchy-12, 24, 24);
        
        //celownik
        int aimx = anchx + (int)(Math.cos(aimangle) * 25);
        int aimy = anchy + (int)(Math.sin(aimangle) * 25);
        
        loop.GetGraphicsContext().setFill(Color.BLUE);
        loop.GetGraphicsContext().fillOval(aimx-3, aimy-3, 6, 6);
        
        //strzal
        if(aimpower > 0)
        {
            loop.GetGraphicsContext().setStroke(Color.WHITE);
            loop.GetGraphicsContext().strokeLine(anchx, anchy, anchx + Math.cos(aimangle) * (100 * aimpower/15), anchy + Math.sin(aimangle) * (100 * aimpower/15));
        }
    }

    public void move(InputEngine ie)
    {
        //reset status
        moveLeft = false;
        moveRight = false;
        
        //call for actions
        if(ie.keyStatus(KeyCode.UP) == true)
        {
            aimangle -= 0.1;
        }
        if(ie.keyStatus(KeyCode.DOWN) == true)
        {
            aimangle += 0.1;
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
        if(ie.keyStatus(KeyCode.ENTER) == true)
        {
            directionToJump = Jump.FORWARD;
            wantToJump = true;
        }
        if(ie.keyStatus(KeyCode.BACK_SPACE) == true)
        {
            directionToJump = Jump.BACKWARD;
            wantToJump = true;
        }
        if(ie.keyStatus(KeyCode.SPACE) == true)
        {
            aimpower += 0.20;
            if(aimpower > 15) shoot = true;
        }
        if(ie.keyStatus(KeyCode.SPACE) == false && aimpower > 0)
        {
            shoot = true;
        }
        
        if(ie.keyStatus(KeyCode.F1) == true)
        {
            equippedGun = AvailableWeapons.ROCKET;
        }
        if(ie.keyStatus(KeyCode.F2) == true)
        {
            equippedGun = AvailableWeapons.GRENADE;
        }
        if(ie.keyStatus(KeyCode.F3) == true)
        {
            equippedGun = AvailableWeapons.BOMB;
        }
    }
    
    void doWalking(GSGame gs, int speed)
    {
        if(currentState == PlayerState.FALLING) return;
        
        int sign = 1;
        if(faceDirection == false) sign = -1;
        
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
        if(gs.currentStage.Collide(x, y+1))
        {
            currentState = PlayerState.IDLE;
            return;
        }
        
        for(int fall = 2; fall <= 8; fall++)
        {
            if(gs.currentStage.Collide(x, y+fall))
            {
                y = y+fall-1;
                currentState = PlayerState.IDLE;
                return;
            }
        }
        
        currentState = PlayerState.FREEFALL;
    }
    
    void doFreeFall(GSGame gs)
    {
        //verticals first
        vy = vy + StaticPhysics.GRAVITY;
        
        if(gs.currentStage.Collide(x, y + vy))
        {
            vy = 0;
            currentState = PlayerState.IDLE;
            wantToJump = false;
            return;
        }
        else y = y + vy;
        
        //horizontal
        if(gs.currentStage.Collide(x + vx, y))
        {
            vx = vx * StaticPhysics.TORQUE * -1;
        }
        else
        x = x + vx;
    }
    
    void doJumping()
    {
        int sign = 1;
        if(faceDirection == false) sign = -1;
        
        wantToJump = false;
        currentState = PlayerState.FREEFALL;
        
        switch(directionToJump)
        {
            case FORWARD:
                vx = 2 * (double)sign;
                vy = -3;
                break;
            case BACKWARD:
                vx = -0.7 * (double)sign;
                vy = -5;
        }
    }
}
