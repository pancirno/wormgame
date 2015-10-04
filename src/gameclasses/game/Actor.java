/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import gameclasses.loop.*;

/**
 *
 * @author pancirno
 */
public class Actor
{
    protected double x = 0;
    protected double y = 0;
    
    protected double vx = 0;
    protected double vy = 0;

    public void render(MainLoop loop, Camera c)
    {
        
    }

    public void step(GSGame gs)
    {

    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
}
