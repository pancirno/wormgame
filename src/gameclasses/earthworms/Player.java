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
    final int MAX_SHOOT_POWER = 15;
    
    //action types
    enum Jump
    {
        FORWARD,
        BACKWARD
    }
    
    enum PlayerState
    {
        ACTIVE,
        JUMPING,
        FALLING,
        FREEFALL,
        //RETREAT,
        RAGDOLL
    }
    
    PlayerState currentState = PlayerState.ACTIVE;
    
    //user movement
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean isFalling = false;
    boolean faceDirection = false;
    int retreatTime = -1;
    
    //jump
    boolean wantToJump = false;
    Jump directionToJump = Jump.FORWARD;
    
    //shooting
    WeaponInfo.AvailableWeapons equippedGun = WeaponInfo.AvailableWeapons.ROCKET;
    double aimangle = 0;
    double aimpower = 0; //max 15
    boolean shoot = false;
    int refire = -1;
    
    
    public Player()
    {
        x = 500;
    }
    
    @Override
    public void step(GSGame gs)
    {       
        if(retreatTime > 0)retreatTime--;
        
        if(retreatTime == 0) retreatTime = -1; //TODO end turn [sprawdz refire i retreattime]
        
        if(currentState == PlayerState.ACTIVE) //handle by player movement
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
                doShooting(gs);
            }
            
        }
        else if(currentState == PlayerState.FREEFALL) //free fall
        {
            doFreeFall(gs);
            
            wantToJump = false;//lock jumping to not jump around
        }
    }
    
    public void SelectPlayer()
    {
        refire = -1;
        retreatTime = -1;
    }

    private void doShooting(GSGame gs) 
    {
        double horizaim = x + Math.cos(aimangle) * 5;
        double vertaim = y + Math.sin(aimangle) * 5;
        double horizthr = Math.cos(aimangle) * aimpower;
        double vertthr = Math.sin(aimangle) * aimpower;
        double horizthrinst = Math.cos(aimangle) * MAX_SHOOT_POWER;
        double vertthrinst = Math.sin(aimangle) * MAX_SHOOT_POWER;
        
        switch(equippedGun)
        {
            case ROCKET:
                gs.spawnProjectile(new Rocket(horizaim, vertaim, horizthr, vertthr));
                break;
            case GRENADE:
                gs.spawnProjectile(new Grenade(horizaim, vertaim, horizthr, vertthr, 180));
                break;
            case BOMB:
                gs.spawnProjectile(new Bomb(horizaim, vertaim, 1.2 * Math.signum(horizthr), -1.5, -1));
                break;
            case SHOTGUN:
                if(!getCanShootAgain())
                    refire = 2;
                gs.spawnProjectile(new BulletProjectile(horizaim, vertaim, horizthrinst, vertthrinst));
                refire--;
                retreatTime = 120;
                break;
        }
        
        if(refire == 0)
        {
            refire = -1;
        }
        
        if(!getCanShootAgain())
        {
            retreatTime = 180;
        }
        
        aimpower = 0;
        shoot = false;
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
        
        //shoot sequence, increase power on press and shot on release
        if(!getIsRetreading())
        {
            if(ie.keyStatus(KeyCode.SPACE) == true)
            {
                if(WeaponInfo.InstantShot.contains(equippedGun))
                {
                    shoot = true;
                }

                aimpower += 0.20;
                if(aimpower > MAX_SHOOT_POWER) shoot = true;
            }
            if(ie.keyStatus(KeyCode.SPACE) == false && aimpower > 0)
            {
                shoot = true;
            }
            
            if(ie.keyStatus(KeyCode.F1) == true)
            {
                equippedGun = WeaponInfo.AvailableWeapons.ROCKET;
            }
            if(ie.keyStatus(KeyCode.F2) == true)
            {
                equippedGun = WeaponInfo.AvailableWeapons.GRENADE;
            }
            if(ie.keyStatus(KeyCode.F3) == true)
            {
                equippedGun = WeaponInfo.AvailableWeapons.BOMB;
            }
            if(ie.keyStatus(KeyCode.F4) == true)
            {
                equippedGun = WeaponInfo.AvailableWeapons.SHOTGUN;
            }
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
            currentState = PlayerState.ACTIVE;
            return;
        }
        
        for(int fall = 2; fall <= 8; fall++)
        {
            if(gs.currentStage.Collide(x, y+fall))
            {
                y = y+fall-1;
                currentState = PlayerState.ACTIVE;
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
            currentState = PlayerState.ACTIVE;
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
    
    boolean getIsRetreading()
    {
        return retreatTime != -1;
    }
    
    boolean getCanShootAgain()
    {
        return refire > 0;
    }
}
