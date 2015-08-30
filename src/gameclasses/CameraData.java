/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import javafx.geometry.*;

/**
 *
 * @author pancirno
 */
public class CameraData
{
    private int CameraX;
    private int CameraY;
    private final int Width;
    private final int Height;
    
    private Rectangle2D CameraBoundary;
    
    public CameraData(int x, int y, int w, int h)
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
}
