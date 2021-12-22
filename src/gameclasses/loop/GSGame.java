/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.loop;

import gameclasses.earthworms.weapons.Projectile;
import gameclasses.earthworms.weapons.Weapon;
import gameclasses.game.*;
import gameclasses.earthworms.*;
import gameclasses.earthworms.GUIHelper.*;
import gameclasses.earthworms.objects.*;
import gameclasses.game.SynchronizedArrayList;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import varunpant.*;
import wormgame.Resources;

/**
 *
 * @author pancirno
 */
public class GSGame extends GameState
{
    //game data
    public Camera gameCamera;
    public Random randomizer;
    public Level currentStage;
    
    //info
    ArrayList<Team> teamList;
    HashMap<Team, ArrayList<Player>> teamPlayerList; 
    HashMap<Team, Integer> teamIterator;
    int nextTeam = 0;
    
    GameScheme currentScheme;
    
    //game objects
    SynchronizedArrayList<Player> players;
    SynchronizedArrayList<Projectile> projectiles;
    SynchronizedArrayList<LevelObject> objects;
    SynchronizedArrayList<Particle> particles;
    ArrayList<Explosion> explosions;
        
    //loot table
    ArrayList<String> lootTable;
    
    //check for collisions
    QuadTree collisionTree;
    
    //current moving object
    Player activePlayer;
    
    //turn data
    TurnType currentTurnType = TurnType.IDLE;
    int currentTurn = 0;
    int turnTimer = -1;
    double windPower = 0.0;
    Team firstSelectedTeam = null;
    
    //debug
    boolean debug = true;
    
    //initialize gsgame state
    public GSGame()
    {
        gameCamera = new Camera(0, 0, 800, 600);
        randomizer = new Random();
        currentStage = new Level(randomizer.nextInt());
        
        currentScheme = GameScheme.testScheme();
        
        teamList = new ArrayList<>();
        teamPlayerList = new HashMap<>();
        teamIterator = new HashMap<>();
        
        players = new SynchronizedArrayList<>();
        projectiles = new SynchronizedArrayList<>();
        objects = new SynchronizedArrayList<>();
        particles = new SynchronizedArrayList<>();
        explosions = new ArrayList<>();
        
        lootTable = new ArrayList<>();
        
        collisionTree = new QuadTree(currentStage.GameArea.getMinX(), currentStage.GameArea.getMinY(), currentStage.GameArea.getMaxX(), currentStage.GameArea.getMaxY());
        
        prepareMatch();
    }

    //initialize match variables
    private void prepareMatch() 
    {
        addTeam(new Team("wew", "Gracz 1","Gracz 2","Gracz 3","Gracz 4",Color.RED, 0, false));
        addTeam(new Team("dupa1", "CPU 1","CPU 2","CPU 3","CPU 4",Color.BLUE, 0, true));

        ArrayList<Point2D> availablePlaces = currentStage.findAvailablePointsForPlayers();
                
        teamList.stream().forEach((_item) ->
        {
            for(int i = 0; i < 4; i++)
            {
                Point2D p = pickRandomPointElement(availablePlaces);
                insertPlayer(new Player((int)p.getX(), (int)p.getY(), _item, i, _item.playernames.get(i)));
            }
        });
        
        
        for(int i = 0; i < 4; i++)
        {
            Point2D p = pickRandomPointElement(availablePlaces);
            spawnObject(new OilBarrel((int)p.getX(), (int)p.getY()));
        }
        
        for(int i = 0; i < 4; i++)
        {
            Point2D p = pickRandomPointElement(availablePlaces);
            spawnObject(new Mine((int)p.getX(), (int)p.getY(), 180));
        }
        
        selectNextPlayer();
    }

    //insert new team
    private void addTeam(Team t1) 
    {
        teamList.add(t1);
        teamPlayerList.put(t1, new ArrayList<>());
        teamIterator.put(t1, 0);
        t1.setAvailableAmmo(currentScheme);
    }
    
    //find a point from available spawn points in the map
    private Point2D pickRandomPointElement(ArrayList<Point2D> l)
    {
        Point2D p = l.get(randomizer.nextInt(l.size()));
        l.remove(p);
        return p;
    }
    
    private void insertPlayer(Player p)
    {
        players.InsertObject(p);
        teamPlayerList.get(p.getPlayerTeam()).add(p);
    }
    
    //reset active player and wait for next player
    public void endTheTurn()
    {
        activePlayer = null;
        turnTimer = 0;
    }
    
    //jump from hotseat into normal turn
    public void beginProperTurn()
    {
        if(currentTurnType != TurnType.PLAYER_MOVE)
        {
            currentTurnType = TurnType.PLAYER_MOVE;
            turnTimer = currentScheme.turntime * 60;
        }
    }
    
    //pick next player
    public void selectNextPlayer()
    {        
        if(nextTeam >= teamList.size())
        {
            nextTeam = 0;
        }
        
        Team t = teamList.get(nextTeam);
        
        if(firstSelectedTeam == null) firstSelectedTeam = t;
        else
        {
            if(t == firstSelectedTeam) currentTurn++;
        }
        
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
        
        gameCamera.MoveCameraAbs((int)activePlayer.getX() - gameCamera.GetWidth() / 2, (int)activePlayer.getY() - gameCamera.GetHeight() / 2);
        
        teamIterator.replace(t, nextPlayerId);
        
        nextTeam++;
    }
    
    public double getWind()
    {
        return windPower / 10;
    }
    
    public double getActualWind()
    {
        return windPower;
    }
    
    //od -0.5 do 0.5
    private void changeWind()
    {
        windPower = getRandomNumber() - 0.5; 
        if(Math.abs(windPower) < 0.05) windPower = 0;
        if(Math.abs(windPower) > 0.45) windPower = 0.5 * Math.signum(windPower);
    }
    
    @Override
    protected void execute(MainLoop loop)
    {
        //if turn ends
        if(--turnTimer <= 0 && explosions.isEmpty() && projectiles.size() == 0 && !arePlayersMoving() && !areObjectsMoving())
            switch(currentTurnType)
            {
                case IDLE:
                    changeWind();
                    selectNextPlayer(); //or event
                    
                    currentTurnType = TurnType.HOTSEAT;
                    turnTimer = currentScheme.hotseattime * 60;
                    break;
                    
                case HOTSEAT:
                    beginProperTurn();
                    break;
                    
                case PLAYER_MOVE:
                    endTheTurn();
                    
                    currentTurnType = TurnType.IDLE;
                    turnTimer = 180;
                    break;
                    
                case EVENT:
                    dropPickup();
                    break;
            }
        
        //collect inputs
        gameCamera.move(loop.GetInputEngine());
        if(activePlayer != null)
        {
            activePlayer.move(this, loop.GetInputEngine());
            if(activePlayer.isMoving() && currentTurnType == TurnType.HOTSEAT)
            {
                beginProperTurn();
            }
        }
        
        executeHandleExplosions();
        executeHandleCollisions();
        executeStep();
        executeRemoveObjects();
        executeDraw(loop);
        executeSounds();
        
    }

    private void executeDraw(MainLoop loop) 
    {
        GraphicsContext gc = loop.GetGraphicsContext();
                
        gameCamera.SetResolution((int)gc.getCanvas().getWidth(), (int)loop.GetGraphicsContext().getCanvas().getHeight());
        gameCamera.RefreshBoundary();
        
        //draw background
        drawBackground(gc, gameCamera);
        //draw terrain
        currentStage.render(gc, gameCamera);
        //draw sprites
        
        players.GetIterator().forEachRemaining(p -> 
        {
            p.render(loop, gameCamera);
            if(p == activePlayer) p.renderGUI(this, loop, gameCamera);
        });
        
        projectiles.GetIterator().forEachRemaining(pro ->
        {
            pro.render(loop, gameCamera);
        });
        
        objects.GetIterator().forEachRemaining(lo ->
        {
            lo.render(loop, gameCamera);
        });
        
        particles.GetIterator().forEachRemaining(pr ->
        {
            if(pr.trash)
            {
                particles.RemoveObject(pr);
            }
            else pr.render(loop, gameCamera);
        });
        
        //draw foreground
        //draw ui
        
        if(debug)
        {
            //GUIHelper.drawRoundCube(gc, 5, 10, 40, 15);
            //gc.setFill(Color.WHITE);
            //gc.fillText(String.valueOf(), 10, 22);
            
            GUIHelper.drawTextCube(gc, 20, 20, String.valueOf(MainLoop.executionrate), Color.WHITE, boxAlignment.left);
            GUIHelper.drawTextCube(gc, 20, (int)gc.getCanvas().getHeight() - 80, String.valueOf((turnTimer/60)+1), Color.WHITE, boxAlignment.left);
            GUIHelper.drawWindMeter(gc, (int)gc.getCanvas().getWidth() - 120, (int)gc.getCanvas().getHeight() - 80, windPower);

        }
    }
    
    private void executeSounds()
    {
        Resources.getInstance().playSounds();
    }
    
    public void playSound(String soundname)
    {
        Resources.getInstance().registerSound(soundname);
    }

    private void executeRemoveObjects() {
        //clean up objects
        projectiles.Sync();
        
        objects.Sync();
        
        particles.Sync();
        
        players.GetIteratorToDel().forEachRemaining(p -> 
        {
            if(p == activePlayer)
                endTheTurn();
        });
        players.Sync();
    }

    private void executeStep() {
        //run object logic
        //p.step(this);
        players.GetIterator().forEachRemaining(p -> 
        {
            p.step(this);
        });
        
        projectiles.GetIterator().forEachRemaining(pro ->
        {
            pro.step(this);
        });
        
        objects.GetIterator().forEachRemaining(lo ->
        {
            lo.step(this);
        });
    }

    private void executeHandleExplosions() {
        //handle explosions
        for(Explosion exp : explosions)
        {
            currentStage.HandleExplosion(exp);
            executePushObjects(exp);
            executeHurtPlayers(exp);
        }
        explosions.clear();
    }
    
    private void executeHandleCollisions()
    {
        collisionTree.clear();
        
        players.GetIterator().forEachRemaining(ac -> 
        {
            if(ac.checkCollidable() && !ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        });
        
        projectiles.GetIterator().forEachRemaining(ac ->
        {
            if(ac.checkCollidable() && !ac.isHitscan() && !ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        });
        
        objects.GetIterator().forEachRemaining(ac ->
        {
            if(ac.checkCollidable() && !ac.isOutsideAreaOfPlay(this))
                collisionTree.set(ac.getX(), ac.getY(), ac);
        });
        
    }
    
    public Object[] findObjectsInCollisionTree(int x, int y, int mx, int my)
    {
        int t;
        
        if(x > mx)
        {
            t = mx;
            mx = x;
            x = t;
        }
        
        if(y > my)
        {
            t = my;
            my = y;
            y = t;
        }
        
        Point[] pts = collisionTree.searchWithin(x, y, mx, my);
        ArrayList<Object> ob = new ArrayList<>();
        
        for(Point pt : pts)
        {
            ob.add(pt.getValue());
        }
        
        return ob.toArray();
    }

    private void executePushObjects(Explosion exp)
    {
        Point2D exppoint = new Point2D(exp.x, exp.y);
        //push all nearby projectiles
        projectiles.GetIterator().forEachRemaining(pro ->
        {
            double dist = exppoint.distance(pro.getX(), pro.getY());
            if(dist <= exp.hurtRadius)
            {
                PushObject(pro, exppoint.getX(), exppoint.getY(), exp, dist);
            }
        });
        
        objects.GetIterator().forEachRemaining(lo ->
        {
            double dist = exppoint.distance(lo.getX(), lo.getY());
            if(dist <= exp.hurtRadius)
            {
                PushObject(lo, exppoint.getX(), exppoint.getY(), exp, dist);
            }
        });
    }
    
    private void executeHurtPlayers(Explosion exp) 
    {
        Point2D exppoint = new Point2D(exp.x, exp.y);
        //push all nearby projectiles
        players.GetIterator().forEachRemaining(plr -> 
        {
            Rectangle2D plrrec = plr.getCollisionArea();
            double dist = exppoint.distance(plrrec.getMinX() + plrrec.getWidth()/2, plrrec.getMinY() + plrrec.getHeight()/2);
            if(dist <= exp.hurtRadius)
            {
                double pushangle = calculatePushAngle(plr.getX(), plr.getY(), exppoint.getX(), exppoint.getY(), exp, dist);
                double epower = exp.power * (1 - (dist/exp.hurtRadius));
                
                double damage;
                
                if(exp.constDamage) damage = exp.damage;
                else damage = Math.min(exp.damage * (1 - (dist/exp.hurtRadius)), exp.damage);   
                
                if(damage < 0) damage = 0;
                
                plr.dealDamage((int) damage);
                plr.push(this, CommonMath.getDirectionVector(pushangle).multiply(epower));
            }
        });
    }
    
    private void PushObject(Actor pro, double expx, double expy,  Explosion exp, double dist)
    {
        double pushangle = calculatePushAngle(pro.getX(), pro.getY(), expx, expy, exp, dist);
        double epower = exp.power * (1 - (dist/exp.hurtRadius));
        pro.push(this, CommonMath.getDirectionVector(pushangle).multiply(epower));
    }
    
    private double calculatePushAngle(double targetX, double targetY, double expx, double expy, Explosion exp, double dist)
    {
        double xDiff = targetX - expx;
        double yDiff = targetY - expy + exp.bias;
        
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
            projectiles.InsertObject(e);
    }
    
    public void spawnObject(LevelObject e)
    {
        objects.InsertObject(e);
    }
    
    public void spawnParticle(Particle p)
    {
        if(particles.size() < 256)
            particles.InsertObject(p);
    }
    
    public void removeObject(Projectile e)
    {
        projectiles.RemoveObject(e);
    }
    
    public void removeObject(LevelObject e)
    {
        objects.RemoveObject(e);
    }
    
    public void removePlayer(Player p)
    {
        players.RemoveObject(p);
        teamPlayerList.get(p.getPlayerTeam()).remove(p);
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
    
    public int getRandomInt()
    {
        return randomizer.nextInt();
    }
    
    public int getRandomInt(int limit)
    {
        return randomizer.nextInt(limit);
    }
    
    public GameScheme getScheme()
    {
        return currentScheme;
    }
    
    public Weapon getWeaponInfo(String w)
    {
        return currentScheme.getWeapon(w);
    }
    
    public HashMap<Integer, List<Weapon>> getWeaponTable()
    {
        return currentScheme.getWeaponTable();
    }
    
    public Weapon findNextWeapon(int row, int column)
    {
        HashMap<Integer, List<Weapon>> wtable = currentScheme.getWeaponTable();
        List<Weapon> wp = wtable.get(row);
        wp.sort((o1, o2) -> o1.getPriority() - o2.getPriority());
        
        if(wp.isEmpty()) return null;
        
        Weapon get = wp.get(0);
        
        for(Weapon wfind : wp)
        {
            if (wfind.getPriority() > column)
            {
                get = wfind;
                break;
            }
        }
        
        return get;
    }
    
    public int getCurrentTurn()
    {
        return currentTurn;
    }
    
    public TurnType getCurrentTurnPhase()
    {
        return currentTurnType;
    }
    
    public boolean arePlayersMoving()
    {
        return players.GetStream().anyMatch(x -> x.isMoving());
    }
    
    public boolean areObjectsMoving()
    {
        return objects.GetStream().anyMatch(x -> x.isMoving());
    }
    
    private void dropPickup() 
    {
        if(this.getRandomNumber() < 0.25)
            spawnObject((this.getRandomNumber() < 0.5) ? new AmmoPickup(this, this.getRandomInt((int)currentStage.GameArea.getMaxX()), -50) : new HealthPickup(this.getRandomInt((int)currentStage.GameArea.getMaxX()), -50));
    }
    
    public String getLoot()
    {
        if(lootTable.isEmpty())
        {
            currentScheme.getCrateTable().forEach((k,v) -> 
            {
                for(int i = 0; i < v; i++)
                    lootTable.add(k);
            }    
            );
        }
        
        int tableNum = getRandomInt(lootTable.size());
        String ar = lootTable.get(tableNum);
        lootTable.remove(tableNum);
        return ar;
    }

}
