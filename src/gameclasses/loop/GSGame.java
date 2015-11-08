/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.loop;

import gameclasses.game.*;
import gameclasses.earthworms.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import varunpant.QuadTree;

/**
 *
 * @author pancirno
 */
public class GSGame extends GameState
{
    //game data
    public Camera gameCamera;
    public Level currentStage;
    public Random randomizer;
    
    //info
    ArrayList<Team> teamList;
    HashMap<Team, ArrayList<Player>> teamPlayerList; 
    HashMap<Team, Integer> teamIterator;
    int nextTeam = 0;
    
    //game objects
    ArrayList<Explosion> explosions;
    ArrayList<Projectile> projectiles;
    ArrayList<Player> players;
    ArrayList<LevelObject> objects;
    ArrayList<Particle> particles;
    
    //trashbins
    ArrayList<Projectile> trashProj;
    ArrayList<Player> trashPlayer;
    ArrayList<LevelObject> trashObject;
    ArrayList<Particle> trashParticle;
    
    //spawners
    ArrayList<Projectile> spawnProj;
    ArrayList<LevelObject> spawnObject;
    ArrayList<Particle> spawnParticle;
    
    //check for collisions
    QuadTree collisionTree;
    
    //current moving object
    Player activePlayer;
    
    //turn data
    boolean pickNextPlayer = false;
    
    
    
    public GSGame()
    {
        gameCamera = new Camera(0, 0, 800, 600);
        currentStage = new Level();
        randomizer = new Random();
        
        teamList = new ArrayList<>();
        teamPlayerList = new HashMap<>();
        teamIterator = new HashMap<>();
        
        explosions = new ArrayList<>();
        projectiles = new ArrayList<>();
        players = new ArrayList<>();
        objects = new ArrayList<>();
        particles = new ArrayList<>();
        
        trashObject = new ArrayList<>();
        spawnObject = new ArrayList<>();
        
        trashProj = new ArrayList<>();
        spawnProj = new ArrayList<>();
        
        trashParticle = new ArrayList<>();
        spawnParticle = new ArrayList<>();
        
        trashPlayer = new ArrayList<>();
        
        collisionTree = new QuadTree(currentStage.GameArea.getMinX(), currentStage.GameArea.getMinY(), currentStage.GameArea.getMaxX(), currentStage.GameArea.getMaxY());
        
        prepareMatch();
    }

    private void prepareMatch() {
        Team t1 = new Team("wew", "lel","lel","lel","lel",Color.RED, 0);
        Team t2 = new Team("dupa2", "lel","lel","lel","lel",Color.BLUE, 0);
        
        teamList.add(t1);
        teamPlayerList.put(t1, new ArrayList<>());
        teamIterator.put(t1, 0);
        teamList.add(t2);
        teamPlayerList.put(t2, new ArrayList<>());
        teamIterator.put(t2, 0);
                
        insertPlayer(new Player(300, 0, t1, 0));
        insertPlayer(new Player(500, 0, t1, 1));
        insertPlayer(new Player(700, 0, t1, 2));
        insertPlayer(new Player(900, 0, t1, 3));
        insertPlayer(new Player(1100, 0, t2, 0));
        insertPlayer(new Player(1300, 0, t2, 1));
        insertPlayer(new Player(1500, 0, t2, 2));
        insertPlayer(new Player(1700, 0, t2, 3));
        
        selectNextPlayer();
    }
    
    private void insertPlayer(Player p)
    {
        players.add(p);
        teamPlayerList.get(p.getPlayerTeam()).add(p);
    }
    
    public void endTheTurn()
    {
        activePlayer = null;
        pickNextPlayer = true;
    }
    
    public void selectNextPlayer()
    {        
        if(nextTeam >= teamList.size())
        {
            nextTeam = 0;
        }
        
        Team t = teamList.get(nextTeam);
        if(teamPlayerList.get(t).isEmpty())
        {
            nextTeam++;
            selectNextPlayer();
            return;
        }
        
        int nextPlayerId = teamIterator.get(t);
        nextPlayerId = ++nextPlayerId % teamPlayerList.get(t).size();
        
        activePlayer = teamPlayerList.get(t).get(nextPlayerId);
        activePlayer.SelectPlayer();
        
        gameCamera.MoveCameraAbs((int)activePlayer.getX() - 400, (int)activePlayer.getY() - 300);
        
        teamIterator.replace(t, nextPlayerId);
        
        nextTeam++;
        
        pickNextPlayer = false;
    }
    
    @Override
    protected void execute(MainLoop loop)
    {
        //check if player needs to be switched
        if(pickNextPlayer && explosions.isEmpty() && projectiles.isEmpty() && !arePlayersMoving())
        {
            selectNextPlayer();
        }
        
        //collect inputs
        gameCamera.move(loop.GetInputEngine());
        if(activePlayer != null)
            activePlayer.move(loop.GetInputEngine());
        
        executeSpawnObjects();
        
        executeHandleExplosions();
        
        executeHandleCollisions();
        
        executeStep();
        
        executeRemoveObjects();
        
        executeDraw(loop);
    }

    private void executeDraw(MainLoop loop) {
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
        
        for(LevelObject lo : objects)
        {
            lo.render(loop, gameCamera);
        }
        
        for(Particle pr : particles)
        {
            pr.render(loop, gameCamera);
        }
        //draw foreground
        //draw ui
    }

    private void executeRemoveObjects() {
        //clean up objects
        for(Projectile tpro : trashProj)
        {
            projectiles.remove(tpro);
        }
        trashProj.clear();
        
        for(LevelObject tlo : trashObject)
        {
            objects.remove(tlo);
        }
        trashObject.clear();
        
        for(Particle pr : trashParticle)
        {
            particles.remove(pr);
        }
        trashParticle.clear();
        
        //kill dead players
        for(Player p : trashPlayer)
        {
            if(p == activePlayer)
                endTheTurn();
            
            deletePlayer(p);
        }
        trashPlayer.clear();
    }

    private void executeStep() {
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
        
        for(LevelObject lo : objects)
        {
            lo.step(this);
        }
    }

    private void executeHandleExplosions() {
        //handle explosions
        for(Explosion exp : explosions)
        {
            currentStage.HandleExplosion(exp);
            PushProjectiles(exp);
            HurtPlayers(exp);
        }
        explosions.clear();
    }
    
    private void executeHandleCollisions()
    {
        collisionTree.clear();
        
        for(Actor ac : players)
        {
            if(!ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        }
        
        for(Actor ac : projectiles)
        {
            if(!ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        }
        
        for(Actor ac : objects)
        {
            if(!ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        }
    }

    private void executeSpawnObjects() {
        //create new objects
        projectiles.addAll(spawnProj);
        spawnProj.clear();
        
        objects.addAll(spawnObject);
        spawnObject.clear();
        
        particles.addAll(spawnParticle);
        spawnParticle.clear();
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
                double pushangle = calculatePushAngle(pro.getX(), pro.getY(), exppoint, exp, dist);
                double epower = exp.power * (1 - (dist/exp.hurtRadius));
                pro.push(CommonMath.getDirectionVector(pushangle).multiply(epower));
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
                double pushangle = calculatePushAngle(plr.getX(), plr.getY(), exppoint, exp, dist);
                
                double epower = exp.power * (1 - (dist/exp.hurtRadius));
                double damage = Math.min(exp.damage * (1 - ((dist - 5)/exp.hurtRadius)), exp.damage);
                if(damage > exp.damage) damage = exp.damage;
                if(damage < 0) damage = 0;
                
                plr.dealDamage((int) damage);
                plr.push(CommonMath.getDirectionVector(pushangle).multiply(epower));
            }
        }
    }
    
    private double calculatePushAngle(double targetX, double targetY, Point2D exppoint, Explosion exp, double dist)
    {
        double xDiff = targetX - exppoint.getX();
        double yDiff = targetY - exppoint.getY() + exp.bias;
        
        return CommonMath.getInvertedDiffAngle(xDiff, yDiff);
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
    
    public void spawnObject(LevelObject e)
    {
        spawnObject.add(e);
    }
    
    public void removeObject(Projectile e)
    {
        if(projectiles.contains(e))
            trashProj.add(e);
    }
    
    public void removeObject(LevelObject e)
    {
        if(objects.contains(e))
            trashObject.add(e);
    }
    
    public void removePlayer(Player p)
    {
        trashPlayer.add(p);
    }
    
    public boolean ifObjectExists(Projectile e)
    {
        return projectiles.contains(e);
    }
    
    public Player getActivePlayer()
    {
        return activePlayer;
    }
    
    public double getRandomNumber()
    {
        return randomizer.nextDouble();
    }
    
    public double getGaussianRandomNumber()
    {
        return randomizer.nextGaussian();
    }
    
    public boolean arePlayersMoving()
    {
        for(Player p : players)
            if(p.isMoving())
            {
                return true;
            }
        
        return false;
    }
    
    private void deletePlayer(Player p)
    {
        players.remove(p);
        teamPlayerList.get(p.getPlayerTeam()).remove(p);
    }

}
