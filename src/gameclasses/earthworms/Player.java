/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.*;
import gameclasses.game.*;
import gameclasses.loop.*;
import javafx.geometry.*;
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
        RAGDOLL,
        ROPING
    }
    
    PlayerState currentState = PlayerState.ACTIVE;
    
    //teaminfo
    Team playerTeam;
    int playerID;
    
    //vital
    int healthPoints = 100;
    
    //game logic
    boolean isCurrentlySelected = false;
    
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
    boolean autoshoot = false;
    int refire = -1;
    
    //rope
    RopeConnector ropeshoot = null;
    Rope ropestring = null;
    boolean ropepush = false;
    boolean ropepull = false;
    
    public Player(int ix, int iy, Team it, int id)
    {
        x = ix;
        y = iy;
        playerTeam = it;
        playerID = id;
    }
    
    @Override
    public void step(GSGame gs)
    {       
        
        isCurrentlySelected = (this == gs.getActivePlayer());
        
        if(retreatTime > 0)retreatTime--;
        
        if(retreatTime == 0) 
        {
            if(autoshoot)
            {
                doShooting(gs);
                return;
            }
            else if (refire > 0)
            {
                retreatTime = -1;
            }
            else
            {
                gs.selectNextPlayer();
                restartPlayer();
            }
        }
        
        //toggle rope
        if(ropeshoot != null)
        {
            if(ropeshoot.impact == true)
            {
                Point2D dstp = new Point2D(x,y);
                double dst = dstp.distance(ropeshoot.getX(), ropeshoot.getY());
                
                ropestring = new Rope(300,dst,ropeshoot.getX(), ropeshoot.getY(), this);
                currentState = PlayerState.ROPING;
                ropeshoot = null;
            }
        }
            
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
        else if(currentState == PlayerState.RAGDOLL)
        {
            doBounce(gs);
        }
        else if(currentState == PlayerState.ROPING)
        {
            doRope(gs);
        }
    }

    public void SelectPlayer()
    {
        restartPlayer();
    }
    
    void restartPlayer()
    {
        refire = -1;
        retreatTime = -1;
        autoshoot = false;
        shoot = false;
        wantToJump = false;
        moveLeft = false;
        moveRight = false;       
    }

    private void doShooting(GSGame gs) 
    {
        double precos = Math.cos(aimangle);
        double presin = Math.sin(aimangle);
        double horizaim = x + precos * 5;
        double vertaim = y + presin * 5;
        double horizthr = precos * aimpower;
        double vertthr = presin * aimpower;
        double horizthrinst = precos * MAX_SHOOT_POWER;
        double vertthrinst = presin * MAX_SHOOT_POWER;
        
        switch(equippedGun)
        {
            case ROCKET:
                gs.spawnProjectile(new Rocket(horizaim, vertaim, horizthr, vertthr));
                break;
            case MIRV:
                gs.spawnProjectile(new MIRV(horizaim, vertaim, horizthr, vertthr));
                break;
            case GRENADE:
                gs.spawnProjectile(new Grenade(horizaim, vertaim, horizthr, vertthr, 180));
                break;
            case FIREGRENADE:
                gs.spawnProjectile(new FireGrenade(horizaim, vertaim, horizthr, vertthr, 180));
                break;
            case BOMB:
                gs.spawnProjectile(new Bomb(horizaim, vertaim, 1.2 * Math.signum(horizthr), -1.5, -1));
                break;
            case SHOTGUN:
                if(!getCanShootAgain())
                    refire = 2;
                gs.spawnProjectile(new Shotgun(horizaim, vertaim, horizthrinst, vertthrinst));
                refire--;
                retreatTime = 120;
                break;
            case MINIGUN:
                if(!getCanShootAgain())
                    refire = 10;
                
                autoshoot = true;
                gs.spawnProjectile(new UZI(horizaim, vertaim, horizthrinst, vertthrinst));
                refire--;
                retreatTime = 3;
                break;
            case ROPE:
                refire = 9999;
                
                if(!gs.ifObjectExists(ropeshoot))
                {
                    ropeshoot = new RopeConnector(horizaim, vertaim, horizthrinst, vertthrinst, 15);
                    gs.spawnProjectile(ropeshoot);
                }
                
                retreatTime = 120;
                break;
        }
        
        if(!getCanShootAgain())
        {
            retreatTime = 180;
            autoshoot = false;
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
                
        //hp text
        loop.GetGraphicsContext().setStroke(playerTeam.teamcolor);
        loop.GetGraphicsContext().strokeText(String.valueOf(healthPoints) + " " + equippedGun.name(), anchx-6, anchy-25);
        
        //sprite
        loop.GetGraphicsContext().setFill(playerTeam.teamcolor);
        loop.GetGraphicsContext().fillOval(anchx-12, anchy-12, 24, 24);
        
        //celownik
        if(isCurrentlySelected)
        {
            int aimx = anchx + (int)(Math.cos(aimangle) * 25);
            int aimy = anchy + (int)(Math.sin(aimangle) * 25);

            loop.GetGraphicsContext().setFill(Color.BLUE);
            loop.GetGraphicsContext().fillOval(aimx-3, aimy-3, 6, 6);
        }
        
        //strzal
        if(aimpower > 0)
        {
            loop.GetGraphicsContext().setStroke(Color.WHITE);
            loop.GetGraphicsContext().strokeLine(anchx, anchy, anchx + Math.cos(aimangle) * (100 * aimpower/15), anchy + Math.sin(aimangle) * (100 * aimpower/15));
        }
        
        //sznur
        if(ropestring != null)
        {
            int anchrx = c.GetCameraDeltaX((int)ropestring.getX());
            int anchry = c.GetCameraDeltaY((int)ropestring.getY());
            
            loop.GetGraphicsContext().setStroke(Color.YELLOW);
            loop.GetGraphicsContext().strokeLine(anchx, anchy, anchrx, anchry);

        }
    }

    public void move(InputEngine ie)
    {
        //reset status
        moveLeft = false;
        moveRight = false;
        ropepull = false;
        ropepush = false;
        
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
        if(ie.keyStatus(KeyCode.UP) == true)
        {
            ropepull = true;
            aimangle -= 0.05;
        }
        if(ie.keyStatus(KeyCode.DOWN) == true)
        {
            ropepush = true;
            aimangle += 0.05;
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
        
        //let go of rope
        if(currentState == PlayerState.ROPING)
        {
            if(ie.keyStatus(KeyCode.SPACE) == true)
            {
                detachRope();
            }
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

                    aimpower += 0.2;
                    if(aimpower > MAX_SHOOT_POWER) shoot = true;
                }
                if(ie.keyStatus(KeyCode.SPACE) == false && aimpower > 0)
                {
                    shoot = true;
                }

                if(ie.keyStatus(KeyCode.F1) == true)
                {
                    refire = 0;
                    equippedGun = WeaponInfo.pickWeapon(0);
                }
                if(ie.keyStatus(KeyCode.F2) == true)
                {
                    refire = 0;
                    equippedGun = WeaponInfo.pickWeapon(1);
                }
                if(ie.keyStatus(KeyCode.F3) == true)
                {
                    refire = 0;
                    equippedGun = WeaponInfo.pickWeapon(2);
                }
                if(ie.keyStatus(KeyCode.F4) == true)
                {
                    refire = 0;
                    equippedGun = WeaponInfo.pickWeapon(3);
                }
            }
    }

    private void detachRope() 
    {
        currentState = PlayerState.RAGDOLL;
        ropeshoot = null;
        ropestring = null;
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
    
    void doBounce(GSGame gs)
    {
        this.snapToLevelVel(gs, vx, vy, true);
        this.grenadeBounce(gs, 0.7, 0.7, 0.3);
        
        if(currentState == PlayerState.RAGDOLL && (Math.abs(vx) + Math.abs(vy)) < 0.2)
        {
            vx = 0;
            vy = 0;
            currentState = PlayerState.ACTIVE;
        }
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
    
    private void doRope(GSGame gs)
    {        
        if(ropestring != null)
        {
            if(ropepush)
                ropestring.increaseLength(gs, 2);
            if(ropepull)
                ropestring.decreaseLength(gs, 2);
            
            if(moveLeft)
                ropestring.swingLeft(1);
            if(moveRight)
                ropestring.swingRight(1);
            
            Point2D d = ropestring.calcPlayerPos(gs);
            x = d.getX();
            y = d.getY();
            
            Point2D v = ropestring.getVelocityVector();
            vx = v.getX();
            vy = v.getY();
        }
    }
    
    public void dealDamage(int dmg)
    {
        healthPoints -= dmg;
    }
    
    public void push(double ivx, double ivy)
    {
        vx += ivx;
        vy += ivy;
        currentState = PlayerState.RAGDOLL;
    }
    
    boolean getIsRetreading()
    {
        return retreatTime > -1;
    }
    
    boolean getCanShootAgain()
    {
        return refire > 0;
    }
    
    public Team getPlayerTeam()
    {
        return playerTeam;
    }
    
    public int getPlayerID()
    {
        return playerID;
    }
}
