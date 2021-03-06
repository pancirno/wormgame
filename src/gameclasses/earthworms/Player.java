/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.earthworms.weapons.RopeConnector;
import gameclasses.earthworms.weapons.Weapon;
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
    Weapon equippedGunData = null;
    
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
    
    //marker
    boolean ismarked = false;
    Point2D markerClick = null;
    
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
            if(ropeshoot.ifImpacted() == true)
            {
                //playerTeam.deductAmmo(AvailableWeapons.ROPE);
                
                double dst = CommonMath.distance(x,y,ropeshoot.getX(), ropeshoot.getY());
                
                ropestring = new Rope(300,dst,ropeshoot.getX(), ropeshoot.getY(), this);
                currentState = PlayerState.ROPING;
                ropeshoot = null;
            }
        }
            
        switch (currentState) 
        {
        //handle by player movement
            case ACTIVE:
                vx = 0;//clear any velocities
                vy = 0;
                doFalling(gs);
                if(moveLeft)
                    doWalking(gs, 1);
                if(moveRight)
                    doWalking(gs, 1);
                if(wantToJump)
                    doJumping();
                if(shoot && isCurrentlySelected && retreatTime <= 0)
                    doShooting(gs);
        //free fall
            case FREEFALL:
                doFreeFall(gs);
                wantToJump = false;//lock jumping to not jump around
                break;
            case RAGDOLL:
                doBounce(gs);
                break;
            case ROPING:
                doRope(gs);
                break;
            default:
                break;
        }
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
    
    @Override
    protected Object[] findNearbyObjects(GSGame gs, double destx, double desty, int radius) 
    {
        Object[] obj = super.findNearbyObjects(gs, destx, desty, radius);
        excludePlayerClassObjects(obj);
        return obj;
    }
    
    
    public void renderGUI(GSGame gs, MainLoop loop, Camera c)
    {
        //int anchx = c.GetCameraDeltaX((int)x);
        //int anchy = c.GetCameraDeltaY((int)y);
        
        //bron
        
        if(equippedGunData != null)
        {
            int drawAmmoTimer = gs.getScheme().getDelay(equippedGunData.getTag()) - gs.getCurrentTurn();

            Color ammoStringC = Color.WHITE;

            String ammoLeft = "";
            if(playerTeam.getAmmo(equippedGunData.getTag()) > 0)
            {
                ammoLeft = " x" + playerTeam.getAmmo(equippedGunData.getTag());
            }
            else
                ammoStringC = Color.GRAY;

            String timeLeft = "";
            if(drawAmmoTimer > 0) 
            {
                timeLeft = " (" + drawAmmoTimer + ")";
                ammoStringC = Color.RED;
            }

            GUIHelper.drawTextCube(loop.GetGraphicsContext(), 20, 50, equippedGunData.getName() + ammoLeft + timeLeft, ammoStringC, GUIHelper.boxAlignment.left);
        }
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
                int mx = c.GetCameraDeltaX((int)markerClick.getX());
                int my = c.GetCameraDeltaY((int)markerClick.getY());
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
    
    public void move(GSGame gs, InputEngine ie)
    {        
        //reset status
        moveLeft = false;
        moveRight = false;
        ropepull = false;
        ropepush = false;
        
        if(ie.isClicked());
        {
            if(equippedGunData != null)
            if(equippedGunData.ifNeedsMarker())
            {
                Point2D mouse = ie.getClickedThenNull();
                if(mouse != null)
                {
                    ismarked = true;
                    markerClick = mouse.add(gs.gameCamera.GetBoundary().getMinX(), gs.gameCamera.GetBoundary().getMinY());
                }
            }
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
        
        //pick weapon
        if(!getIsRetreading() && currentState == PlayerState.ACTIVE)
        {
            boolean changeWeapon = false;
            int changeWeaponRow = -1;
            
            if(ie.checkPulse(KeyCode.F1) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 1;
            }
            if(ie.checkPulse(KeyCode.F2) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 2;
            }
            if(ie.checkPulse(KeyCode.F3) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 3;
            }
            if(ie.checkPulse(KeyCode.F4) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 4;
            }
            if(ie.checkPulse(KeyCode.F5) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 5;
            }
            if(ie.checkPulse(KeyCode.F6) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 6;
            }
            if(ie.checkPulse(KeyCode.F7) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 7;
            }
            if(ie.checkPulse(KeyCode.F8) == true)
            {
                if(!lockswitch)refire = 0;
                changeWeapon = true;
                changeWeaponRow = 8;
            }
            
            if(changeWeapon)
            {
                if(equippedGunData == null) equippedGunData = gs.findNextWeapon(changeWeaponRow, -1);
                else
                {
                    if(changeWeaponRow != equippedGunData.getRow()) equippedGunData = gs.findNextWeapon(changeWeaponRow, -1);
                    else equippedGunData = gs.findNextWeapon(changeWeaponRow, equippedGunData.getPriority());
                }
            }
        }
        
        //shoot sequence, increase power on press and shot on release
        if(equippedGunData != null)
            if(!getIsRetreading() && currentState == PlayerState.ACTIVE)
            {
                if(ie.checkPressed(KeyCode.SPACE) == true)
                {
                    if(equippedGunData.ifInstantShot())
                    {
                        aimpower = MAX_SHOOT_POWER;
                        tryShooting(gs);
                    }
                    else
                    {
                        aimpower += 0.2;
                        if(aimpower > MAX_SHOOT_POWER) 
                        {
                            aimpower = MAX_SHOOT_POWER;
                            tryShooting(gs);
                        }
                    }
                }
                if(ie.checkPressed(KeyCode.SPACE) == false && aimpower > 0)
                {
                    tryShooting(gs);
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
        vy = vy + StaticPhysics.GRAVITY;
        this.grenadeBounce(gs, 0.7, 0.7, 0.3, false);
        this.snapToLevelVel(gs, vx, vy, true, false);
        
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
    
    protected void setEquippedGun(Weapon w)
    {
        if(!lockswitch)
        {
            equippedGunData = w;
        }
        
        if(equippedGunData.ifNeedsMarker())
        {
            ismarked = false;
            markerClick = null;
        }
    }
        
    public void tryShooting(GSGame gs)
    {
        if(equippedGunData == null) return;
        if(shoot) return;
        
        if(playerTeam.canShootWeapon(gs, equippedGunData.getTag()))
        {
            doShooting(gs);
        }
    }

    private void doShooting(GSGame gs) 
    {        
        if(equippedGunData.ifNeedsMarker())
        {
            if(!ismarked || markerClick == null) return;
        }

        aim_horizaim = x + aim_precos * 5;
        aim_vertaim = y - AIM_HEIGHT + aim_presin * 5;
        
        if(equippedGunData.getShootsAmount() > 1)
        {
            lockswitch = true;
            autoshoot = equippedGunData.ifConsecutiveShoots();
            if(!getCanShootAgain())
            {
                refire = equippedGunData.getShootsAmount();
            }
            refire--;
            retreatTime = equippedGunData.getFramesBetweenShoots();
        }
        
        equippedGunData.DoShooting(this, gs, aim_horizaim, aim_vertaim, aimangle, aimpower, markerClick, 180);
                
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
        
//        if(!gs.ifObjectExists(ropeshoot))
//        {
//            ropeshoot = new RopeConnector(this, aim_horizaim, aim_vertaim, aim_horizthrinst, aim_vertthrinst, 15);
//            gs.spawnProjectile(ropeshoot);
//        }
        
        retreatTime = 120;
    }
    
    private void detachRope() 
    {
        currentState = PlayerState.RAGDOLL;
        ropeshoot = null;
        ropestring = null;
    }
    
    public void dealDamage(int dmg)
    {
        healthPoints -= dmg;
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
    
}
