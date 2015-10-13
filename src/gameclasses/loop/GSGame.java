/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.loop;

import gameclasses.game.Camera;
import gameclasses.earthworms.Level;
import gameclasses.earthworms.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class GSGame extends GameState
{
    //game data
    public Camera gameCamera;
    public Level currentStage;
    
    //info
    ArrayList<Team> teamlist;
    int nextTeam = 0;
    int nextPlayerID = 0;
    
    //game objects
    ArrayList<Explosion> explosions;
    ArrayList<Projectile> projectiles;
    ArrayList<Player> players;
    
    //trashbins
    ArrayList<Projectile> trashProj;
    
    //spawners
    ArrayList<Projectile> spawnProj;
    
    //current moving object
    Player activePlayer;
    
    public GSGame()
    {
        gameCamera = new Camera(0, 0, 800, 600);
        currentStage = new Level();
        
        teamlist = new ArrayList<>();
        
        explosions = new ArrayList<>();
        projectiles = new ArrayList<>();
        players = new ArrayList<>();
        
        trashProj = new ArrayList<>();
        spawnProj = new ArrayList<>();
        
        prepareMatch();
    }

    private void prepareMatch() {
        Team t1 = new Team("wew", "lel","lel","lel","lel",Color.RED, 0);
        Team t2 = new Team("dupa2", "lel","lel","lel","lel",Color.BLUE, 0);
        
        teamlist.add(t1);
        teamlist.add(t2);
        
        players.add(new Player(300, 0, t1, 0));
        players.add(new Player(500, 0, t1, 1));
        players.add(new Player(700, 0, t1, 2));
        players.add(new Player(900, 0, t1, 3));
        players.add(new Player(1100, 0, t2, 0));
        players.add(new Player(1300, 0, t2, 1));
        players.add(new Player(1500, 0, t2, 2));
        players.add(new Player(1700, 0, t2, 3));
        
        selectNextPlayer();
    }
    
    public void selectNextPlayer()
    {
        for(Player p : players)
        {
            if(p.getPlayerTeam() == teamlist.get(nextTeam) && p.getPlayerID() == nextPlayerID)
            {
                activePlayer = p;
                activePlayer.SelectPlayer();
                
                gameCamera.MoveCameraAbs((int)p.getX() - 400, (int)p.getY() - 300);
                
                break;
            }
        }
        
        nextTeam++;
        
        if(nextTeam >= teamlist.size())
        {
            nextTeam = 0;
            nextPlayerID++;
        }
        
    }
    
    @Override
    protected void execute(MainLoop loop)
    {
        //collect inputs
        gameCamera.move(loop.GetInputEngine());
        activePlayer.move(loop.GetInputEngine());
        
        //create new objects
        projectiles.addAll(spawnProj);
        spawnProj.clear();
        
        //handle explosions
        for(Explosion exp : explosions)
        {
            currentStage.HandleExplosion(exp);
            PushProjectiles(exp);
            HurtPlayers(exp);
        }
        explosions.clear();
        
        //run object logic
        //p.step(this);
        for(Player p : players)
        {
            p.step(this);
        }
        
        for(Projectile pro : projectiles)
        {
            pro.step(this);
        }
        
        //clean up objects
        for(Projectile tpro : trashProj)
        {
            projectiles.remove(tpro);
        }
        trashProj.clear();
        
        //draw background
        drawBackground(loop.GetGraphicsContext(), gameCamera);
        //draw terrain
        currentStage.render(loop.GetGraphicsContext(), gameCamera);
        //draw sprites
        for(Player p : players)
        {
            p.render(loop, gameCamera);
        }
        
        for(Projectile pro : projectiles)
        {
            pro.render(loop, gameCamera);
        }
        //draw foreground
        //draw ui
        
    }

    private void PushProjectiles(Explosion exp)
    {
        Point2D exppoint = new Point2D(exp.x, exp.y);
        //push all nearby projectiles
        for(Projectile pro : projectiles)
        {
            double dist = exppoint.distance(pro.getX(), pro.getY());
            if(dist <= exp.hurtRadius)
            {
                double xDiff = pro.getX() - exppoint.getX();
                double yDiff = pro.getY() - exppoint.getY() + exp.bias;
                
                double pushangle = Math.atan2(xDiff, yDiff) - Math.PI/2;
                double epower = exp.power * (1 - (dist/exp.hurtRadius));
                
                pro.push(Math.cos(pushangle) * exp.power, Math.sin(pushangle) * -1 * exp.power);
            }
        }
    }
    
    
    private void HurtPlayers(Explosion exp) 
    {
        Point2D exppoint = new Point2D(exp.x, exp.y);
        //push all nearby projectiles
        for(Player plr : players)
        {
            double dist = exppoint.distance(plr.getX(), plr.getY());
            if(dist <= exp.hurtRadius)
            {
                double xDiff = plr.getX() - exppoint.getX();
                double yDiff = plr.getY() - exppoint.getY() + exp.bias;
                
                double pushangle = Math.atan2(xDiff, yDiff) - Math.PI/2;
                double epower = exp.power * (1 - (dist/exp.hurtRadius));
                
                double damage = (exp.damage * (1 - ((dist - 5)/exp.hurtRadius)));
                if(damage > exp.damage) damage = exp.damage;
                if(damage < 0) damage = 0;
                
                plr.dealDamage((int) damage);
                plr.push(Math.cos(pushangle) * exp.power, Math.sin(pushangle) * -1 * exp.power);
            }
        }
    }
    
    private void drawBackground(GraphicsContext gc, Camera cam)
    {
        LinearGradient lg;
        lg = new LinearGradient(0,0,0,2, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(1, Color.BLUE));
        gc.setFill(lg);
        gc.fillRect(0, 0, cam.GetBoundary().getWidth(), cam.GetBoundary().getHeight());
    }
    
    public void spawnExplosion(Explosion e)
    {
        explosions.add(e);
    }
    
    public void spawnProjectile(Projectile e)
    {
        if(projectiles.size() < 256)
            spawnProj.add(e);
    }
    
    public void removeObject(Projectile e)
    {
        if(projectiles.contains(e))
            trashProj.add(e);
    }
    
    public Player getActivePlayer()
    {
        return activePlayer;
    }

}
