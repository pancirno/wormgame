/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import javafx.geometry.*;
import javafx.scene.input.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public class Camera implements IMovable
{
    private int CameraX;
    private int CameraY;
    private final int Width;
    private final int Height;
    
    private Rectangle2D CameraBoundary;
    
    public Camera(int x, int y, int w, int h)
    {
        CameraX = x;
        CameraY = y;
        Width = w;
        Height = h;
        
        RefreshBoundary();
    }
    
    final void RefreshBoundary()
    {
        CameraBoundary = new Rectangle2D(CameraX, CameraY, Width, Height);
    }
    
    public void MoveCameraAbs(int x, int y)
    {
        CameraX = x;
        CameraY = y;
        RefreshBoundary();
    }
    
    public void MoveCameraRel(int x, int y)
    {
        CameraX += x;
        CameraY += y;
        RefreshBoundary();
    }
    
    public Rectangle2D GetBoundary()
    {
        return CameraBoundary;
    }
    
    public boolean IsVisible(Rectangle2D rec)
    {
        return CameraBoundary.intersects(rec);
    }
    
    public boolean IsVisible(Point2D pt)
    {
        return CameraBoundary.contains(pt);
    }
    
    public Point2D GetCameraDelta(Point2D p)
    {
        return p.add(CameraX, CameraY);
    }
    
    public int GetCameraDeltaX(int x)
    {
        return x + CameraX;
    }
    
    public int GetCameraDeltaY(int y)
    {
        return y + CameraY;
    }

    @Override
    public void move(InputEngine ie)
    {
        //call for actions
        if(ie.keyStatus(KeyCode.W) == true)
        {
            MoveCameraRel(0, 3);
        }
        if(ie.keyStatus(KeyCode.S) == true)
        {
            MoveCameraRel(0, -3);
        }
        if(ie.keyStatus(KeyCode.A) == true)
        {
            MoveCameraRel(3, 0);
        }
        if(ie.keyStatus(KeyCode.D) == true)
        {
            MoveCameraRel(-3, 0);
        }
    }
}
