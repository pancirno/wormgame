/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.WeaponInfo.AvailableWeapons;
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
    static final int MAX_SHOOT_POWER = 15;
    
    public static final int BOX_WIDTH = 8;
    public static final int BOX_HEIGHT = 16;
    public static final int AIM_HEIGHT = 12;
    
    //action types
    enum Jump
    {
        FORWARD,
        BACKWARD
    }
    
    enum PlayerState
    {
        ACTIVE,
        STATIC,
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
    String playerName;
    
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
    boolean lockswitch = false;
    
    //aiming - calculated values
    double aim_precos;
    double aim_presin;
    double aim_horizaim;
    double aim_vertaim;
    double aim_horizthr;
    double aim_vertthr;
    double aim_horizthrinst;
    double aim_vertthrinst;
    
    //marker
    boolean ismarked = false;
    Point2D markerClick;
    double targetmarkerX = 0;
    double targetmarkerY = 0;
    
    //rope
    RopeConnector ropeshoot = null;
    Rope ropestring = null;
    boolean ropepush = false;
    boolean ropepull = false;
    
    //draw info
    
    public Player(int ix, int iy, Team it, int id, String name)
    {
        x = ix;
        y = iy;
        playerTeam = it;
        playerID = id;
        playerName = name;
        
        cx = BOX_WIDTH;
        cy = BOX_HEIGHT;
        
        healthPoints = 100;
        fallDamageRatio = 5;
    }
    
    @Override
    public void step(GSGame gs)
    {       
        isCurrentlySelected = (this == gs.getActivePlayer());
        
        //sprawdzamy czy nie spad z rowera
        if(isDead(gs))
        {
            gs.removePlayer(this);
            return;
        }
        
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
                gs.endTheTurn();
                restartPlayer();
            }
        }
        
        //toggle rope
        if(ropeshoot != null)
        {
            if(ropeshoot.impact == true)
            {
                playerTeam.deductAmmo(AvailableWeapons.ROPE);
                
                double dst = CommonMath.distance(x,y,ropeshoot.getX(), ropeshoot.getY());
                
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
            if(shoot && isCurrentlySelected && retreatTime <= 0)
            {
                doShooting(gs);
            }
            if(markerClick != null)
            {
                ismarked = true;
                targetmarkerX = gs.gameCamera.GetBoundary().getMinX() + markerClick.getX();
                targetmarkerY = gs.gameCamera.GetBoundary().getMinY() + markerClick.getY();
                markerClick = null;
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
        ismarked = false;
        lockswitch = false;
    }
    
    protected void configureAiming()
    {
        aim_precos = Math.cos(aimangle);
        aim_presin = Math.sin(aimangle);
        aim_horizaim = x + aim_precos * 5;
        aim_vertaim = y - AIM_HEIGHT + aim_presin * 5;
        aim_horizthr = aim_precos * aimpower;
        aim_vertthr = aim_presin * aimpower;
        aim_horizthrinst = aim_precos * MAX_SHOOT_POWER;
        aim_vertthrinst = aim_presin * MAX_SHOOT_POWER;
    }
    
    private void setEquippedGun(AvailableWeapons aw)
    {
        if(!lockswitch)
            equippedGun = aw;
    }

    private void doShooting(GSGame gs) 
    {        
        configureAiming();
        
        switch(equippedGun)
        {
            case ROCKET:
                gs.spawnProjectile(new Rocket(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr));
                break;
            case MIRV:
                gs.spawnProjectile(new MIRV(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr));
                break;
            case HOMINGMISSILE:
                if(!ismarked)return;
                gs.spawnProjectile(new HomingMissile(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr, (int)targetmarkerX, (int)targetmarkerY));
                break;
            case GRENADE:
                gs.spawnProjectile(new Grenade(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr, 180));
                break;
            case FIREGRENADE:
                gs.spawnProjectile(new FireGrenade(this, aim_horizaim, aim_vertaim, aim_horizthr, aim_vertthr, 180));
                break;
            case BOMB:
                gs.spawnProjectile(new Bomb(this, aim_horizaim, aim_vertaim, 1.2 * Math.signum(aim_horizthr), -1.5, -1));
                break;
            case SHOTGUN:
                lockswitch = true;
                
                if(!getCanShootAgain())
                {
                    playerTeam.deductAmmo(equippedGun);
                    refire = 2;
                }
                gs.playSound("sfx/shot1.wav");
                gs.spawnProjectile(new Shotgun(this, aim_horizaim, aim_vertaim, aim_horizthrinst, aim_vertthrinst, gs));
                refire--;
                retreatTime = 120;
                break;
            case MINIGUN:
                lockswitch = true;
                
                if(!getCanShootAgain())
                {
                    playerTeam.deductAmmo(equippedGun);
                    refire = 15;
                    gs.playSound("sfx/minigun1.wav");
                }
                
                autoshoot = true;
                gs.spawnProjectile(new UZI(this, aim_horizaim , aim_vertaim, aimangle, gs));
                refire--;
                retreatTime = 4;
                break;
            case FLAMETHROWER:
                lockswitch = true;
                
                if(!getCanShootAgain())
                {
                    playerTeam.deductAmmo(equippedGun);
                    refire = 15;
                }
                
                autoshoot = true;
                gs.spawnProjectile(new Flamethrower(this, aim_horizaim, aim_vertaim, aim_horizthrinst/2.5, aim_vertthrinst/2.5));
                refire--;
                retreatTime = 8;
                break;
            case ROPE:
                shootRopeTracer(gs);
                break;
            case AIRSTRIKE:
                if(!ismarked)return;
                gs.spawnProjectile(new AirStrike(this, targetmarkerX, gs.currentStage.GameArea.getMinY()+500, 0.5, 1));
                break;
            case FIRESTRIKE:
                if(!ismarked)return;
                gs.spawnProjectile(new AirFireStrike(this, targetmarkerX, gs.currentStage.GameArea.getMinY()+500, 0.5, 1));
                break;
            case HIBARI:
                if(!ismarked)return;
                gs.spawnProjectile(new Hibari(this, targetmarkerX, gs.currentStage.GameArea.getMinY()+500, 0, 1));
                break;
            case DOUBLESHOTGUN:
                gs.playSound("sfx/doubleshot1.wav");
                for(int i = 0; i < 15; i++)
                    gs.spawnProjectile(new UZI(this, aim_horizaim , aim_vertaim, aimangle, gs));
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

    private void shootRopeTracer(GSGame gs)
    {
        refire = 9999;
        
        if(!gs.ifObjectExists(ropeshoot))
        {
            ropeshoot = new RopeConnector(this, aim_horizaim, aim_vertaim, aim_horizthrinst, aim_vertthrinst, 15);
            gs.spawnProjectile(ropeshoot);
        }
        
        retreatTime = 120;
    }
    
    public void renderGUI(GSGame gs, MainLoop loop, Camera c)
    {
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
        
        //bron
        int drawAmmoTimer = gs.getScheme().getDelay(equippedGun) - gs.getCurrentTurn();
        
        Color ammoStringC = Color.WHITE;
            
        String ammoLeft = "";
        if(playerTeam.getAmmo(equippedGun) > 0)
        {
            ammoLeft = " x" + playerTeam.getAmmo(equippedGun);
        }
        else
            ammoStringC = Color.GRAY;

        String timeLeft = "";
        if(drawAmmoTimer > 0) 
        {
            timeLeft = " (" + drawAmmoTimer + ")";
            ammoStringC = Color.RED;
        }

        GUIHelper.drawTextCube(loop.GetGraphicsContext(), 20, 50, equippedGun.name() + ammoLeft + timeLeft, ammoStringC, GUIHelper.boxAlignment.left);

    }

    @Override
    public void render(MainLoop loop, Camera c)
    {
        //Rectangle2D col = new Rectangle2D(x,y,24,24);
        //if on camera costam;
        
        int anchx = c.GetCameraDeltaX((int)x);
        int anchy = c.GetCameraDeltaY((int)y);
                
        //new hp text
        GUIHelper.drawTextCube(loop.GetGraphicsContext(), anchx, anchy-42, String.valueOf(healthPoints), playerTeam.teamcolor, GUIHelper.boxAlignment.center);
        GUIHelper.drawTextCube(loop.GetGraphicsContext(), anchx, anchy-60, playerName, playerTeam.teamcolor, GUIHelper.boxAlignment.center);
        
        //sprite
        Rectangle2D r2d = getCollisionArea();
        loop.GetGraphicsContext().setFill(playerTeam.teamcolor);
        loop.GetGraphicsContext().fillRect(anchx - cx/2, anchy - cy, cx, cy);
        
        if(isCurrentlySelected)
        {
            
            //celownik
            int aimx = anchx + (int)(Math.cos(aimangle) * 25);
            int aimy = anchy - AIM_HEIGHT + (int)(Math.sin(aimangle) * 25);

            loop.GetGraphicsContext().setFill(Color.BLUE);
            loop.GetGraphicsContext().fillOval(aimx-3, aimy-3, 6, 6);
            
            //marker
            if(ismarked)
            {
                loop.GetGraphicsContext().setFill(Color.YELLOW);
                int mx = c.GetCameraDeltaX((int)targetmarkerX);
                int my = c.GetCameraDeltaY((int)targetmarkerY);
                loop.GetGraphicsContext().strokeLine(mx - 8, my - 8, mx + 8, my + 8);
                loop.GetGraphicsContext().strokeLine(mx - 8, my + 8, mx + 8, my - 8);
            }
        }
        
        //strzal
        if(aimpower > 0)
        {
            loop.GetGraphicsContext().setStroke(Color.WHITE);
            loop.GetGraphicsContext().strokeLine(anchx, anchy - AIM_HEIGHT, anchx + Math.cos(aimangle) * (100 * aimpower/15), anchy - AIM_HEIGHT + Math.sin(aimangle) * (100 * aimpower/15));
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
    
    public void tryShooting(GSGame gs)
    {
        if(shoot) return;
        
        if(playerTeam.canShootWeapon(gs, equippedGun))
        {
            shoot = true;
            
            switch(equippedGun)
            {
                case ROPE:
                case SHOTGUN:
                case MINIGUN:
                case FLAMETHROWER:
                    break;
                    
                default:
                    playerTeam.deductAmmo(equippedGun);
            }
            
        }
    }

    public void move(GSGame gs, InputEngine ie)
    {        
        //reset status
        moveLeft = false;
        moveRight = false;
        ropepull = false;
        ropepush = false;
        
        if(ie.isClicked() && WeaponInfo.SetMarker.contains(equippedGun));
        {
            markerClick = ie.getClicked();
        }
        
        if(ie.checkPressed(KeyCode.LEFT) == true)
        {
            faceDirection = false;
            moveLeft = true;
        }
        if(ie.checkPressed(KeyCode.RIGHT) == true)
        {
            faceDirection = true;
            moveRight = true;
        }
        if(ie.checkPressed(KeyCode.UP) == true)
        {
            ropepull = true;
            aimangle -= 0.05;
        }
        if(ie.checkPressed(KeyCode.DOWN) == true)
        {
            ropepush = true;
            aimangle += 0.05;
        }
        if(ie.checkPulse(KeyCode.ENTER) == true)
        {
            directionToJump = Jump.FORWARD;
            wantToJump = true;
        }
        if(ie.checkPulse(KeyCode.BACK_SPACE) == true)
        {
            directionToJump = Jump.BACKWARD;
            wantToJump = true;
        }
        
        //let go of rope
        if(currentState == PlayerState.ROPING)
        {
            if(ie.checkPulse(KeyCode.SPACE) == true)
            {
                detachRope();
            }
        }
        
        //shoot sequence, increase power on press and shot on release
        if(!getIsRetreading() && currentState == PlayerState.ACTIVE)
        {
            if(ie.checkPressed(KeyCode.SPACE) == true)
            {
                if(WeaponInfo.InstantShot.contains(equippedGun))
                {
                    tryShooting(gs);
                }
                else
                {
                    aimpower += 0.2;
                    if(aimpower > MAX_SHOOT_POWER) tryShooting(gs);
                }
            }
            if(ie.checkPressed(KeyCode.SPACE) == false && aimpower > 0)
            {
                tryShooting(gs);
            }

            if(ie.checkPulse(KeyCode.F1) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(0));
            }
            if(ie.checkPulse(KeyCode.F2) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(1));
            }
            if(ie.checkPulse(KeyCode.F3) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(2));
            }
            if(ie.checkPulse(KeyCode.F4) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(3));
            }
            if(ie.checkPulse(KeyCode.F5) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(4));
            }
            if(ie.checkPulse(KeyCode.F6) == true)
            {
                if(!lockswitch)refire = 0;
                setEquippedGun(WeaponInfo.pickWeapon(5));
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
                if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(speed * sign, 0)))
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
                        if(!gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, -climb)))
                        {
                            if(!gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(1 * sign , -climb)));
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
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, 1)))
        {
            currentState = PlayerState.ACTIVE;
            return;
        }
        
        for(int fall = 2; fall <= 8; fall++)
        {
            if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, fall)))
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
        
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(0, vy)))
        {
            vy = 0;
            currentState = PlayerState.ACTIVE;
            wantToJump = false;
            return;
        }
        else 
        {
            y = y + vy;
            if (vy >= 7) currentState = PlayerState.RAGDOLL;
        }
        
        //horizontal
        if(gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(vx, 0)))
        {
            vx = vx * StaticPhysics.TORQUE * -1;
        }
        else
        x = x + vx;
    }
    
    void doBounce(GSGame gs)
    {
        this.snapToLevelVel(gs, vx, vy, true, false);
        this.grenadeBounce(gs, 0.7, 0.7, 0.3, false);
        
        if(currentState == PlayerState.RAGDOLL && (Math.abs(vx) + Math.abs(vy)) < 0.2)
        {
            vx = 0;
            vy = 0;
            currentState = PlayerState.ACTIVE;
        }
    }
    
    @Override
    protected Object[] findNearbyObjects(GSGame gs, double destx, double desty, int radius) 
    {
        Object[] obj = super.findNearbyObjects(gs, destx, desty, radius);
        excludePlayerClassObjects(obj);
        return obj;
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
                vy = -2.5;
                break;
            case BACKWARD:
                vx = -0.7 * (double)sign;
                vy = -4;
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
            
            if(y >= ropestring.getY())
            {
                if(moveLeft)
                    ropestring.swingLeft(0.5);
                if(moveRight)
                    ropestring.swingRight(0.5);
            }
            else
            {
                if(moveLeft)
                    ropestring.swingRight(0.5);
                if(moveRight)
                    ropestring.swingLeft(0.5);
            }
            
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
    
    @Override
    public void push(GSGame gs, double ivx, double ivy)
    {
        while (gs.currentStage.RectangleOverlapsStage(getCollisionAreaDelta(vx + ivx, vy + ivy)) && ivx != 0 && ivy != 0) 
        {
            ivx *= -0.9;
            if(Math.abs(ivx) <= 0.05) ivx = 0;
            ivy *= -0.9;
            if(Math.abs(ivy) <= 0.05) ivy = 0;
        }
        
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
    
    public boolean isDead(GSGame gs)
    {
        return (this.isOutsideAreaOfPlay(gs) || this.healthPoints <= 0);
    }
    
    @Override
    public Rectangle2D getCollisionArea()
    {
        return new Rectangle2D(x - cx/2, y - cy, cx, cy);
    }
    
    @Override
    public Rectangle2D getCollisionAreaDelta(double dx, double dy)
    {
        return new Rectangle2D((x + dx) - cx/2, (y + dy) - cy, cx, cy);
    }
}
