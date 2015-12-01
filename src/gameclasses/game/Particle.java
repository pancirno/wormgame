/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import gameclasses.loop.GSGame;
import gameclasses.loop.MainLoop;
import java.util.Random;

/**
 *
 * @author lukasz
 */
public class Particle 
{
    public double x, y, vx, vy;
    public int timer;
    
    public boolean trash = false;
    
    static public Random partrandom = new Random();
    
    public Particle(double ix, double iy, double ivx, double ivy, int t)
    {
        x = ix;
        y = iy;
        vx = ivx;
        vy = ivy;
        timer = t;
    }
    
    public void render(MainLoop loop, Camera c)
    {
        timer--;
        if(timer <= 0)
        {
            trash = true;
            return;
        }
    }
}
