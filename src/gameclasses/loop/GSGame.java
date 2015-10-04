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
    
    //game objects
    ArrayList<Explosion> explosions;
    ArrayList<Projectile> projectiles;
    
    //trashbins
    ArrayList<Projectile> trashProj;
    
    //current moving object
    Player p;
    
    public GSGame()
    {
        gameCamera = new Camera(0, 0, 800, 600);
        currentStage = new Level();
        
        explosions = new ArrayList<>();
        projectiles = new ArrayList<>();
        
        trashProj = new ArrayList<>();
        
        p = new Player();
    }
    
    @Override
    protected void execute(MainLoop loop)
    {
        //collect inputs
        gameCamera.move(loop.GetInputEngine());
        p.move(loop.GetInputEngine());
        
        //handle explosions
        for(Explosion exp : explosions)
        {
            currentStage.HandleExplosion(exp);
            
            PushProjectiles(exp);
        }
        explosions.clear();
        
        //run object logic
        p.step(this);
        
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
        p.render(loop, gameCamera);
        
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
        for(Projectile p : projectiles)
        {
            if(exppoint.distance(p.getX(), p.getY()) <= exp.radius)
            {
                double xDiff = p.getX() - exppoint.getX();
                double yDiff = p.getY() - exppoint.getY() + exp.bias;
                
                double pushangle = Math.atan2(xDiff, yDiff) - Math.PI/2;
               
                p.push(Math.cos(pushangle) * exp.power, Math.sin(pushangle) * -1 * exp.power);
            }
        }
    }
    
    private void drawBackground(GraphicsContext gc, Camera cam)
    {
        //LinearGradient lg;
        //lg = new LinearGradient(0,0,0,2, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(1, Color.BLUE));
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, cam.GetBoundary().getWidth(), cam.GetBoundary().getHeight());
    }
    
    public void spawnExplosion(Explosion e)
    {
        explosions.add(e);
    }
    
    public void spawnProjectile(Projectile e)
    {
        if(projectiles.size() < 256)
            projectiles.add(e);
    }
    
    public void removeObject(Projectile e)
    {
        if(projectiles.contains(e))
        trashProj.add(e);
    }
}
