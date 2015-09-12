/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Camera;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import landgen.*;

/**
 *
 * @author pancirno
 */
public class Level
{
    class PictureNode
    {
        public static final int nodeSize = 256;
        
        WritableImage tile;
        Rectangle2D boundary;
    }
    
    WritableImage MainPictureData;
    
    List<PictureNode> PictureTiles;
    PictureNode[][] PictureNodeMatrix; //TODO optimize code for 2d array in the future for faster tile lookup
    
    public Level()
    {
        TerrainGen LevelGenerator = new TerrainGen(8795649);
        MainPictureData = LevelGenerator.returnImage();
        PictureTiles = divideImage();
    }
    
    public void render(GraphicsContext gc, Camera cd)
    {
        //gc.drawImage(MainPictureData, cd.GetBoundary().getMinX(), cd.GetBoundary().getMinY());
        int rendered = 0;
        
        for (PictureNode i : PictureTiles)
        {
            if (cd.IsVisible(i.boundary))
            {
                gc.drawImage(i.tile, i.boundary.getMinX() - cd.GetBoundary().getMinX(), i.boundary.getMinY() - cd.GetBoundary().getMinY());
                rendered++;
            }
        }
        gc.setStroke(Color.WHITE);
        gc.strokeText("drawn level tiles: " + rendered + "/" + PictureTiles.size(), 4, 16);
    }
    
    private List<PictureNode> divideImage()
    {
        List<PictureNode> l = new ArrayList<>();
        
        int width = (int)MainPictureData.getWidth();
        int height = (int)MainPictureData.getHeight();
        
        int nx;
        int ny;
        int nwidth;
        int nheight;
        
        for(nx = 0; nx < width; nx += PictureNode.nodeSize)
            for(ny = 0; ny < height; ny+= PictureNode.nodeSize)
            {
                nwidth = PictureNode.nodeSize;
                nheight = PictureNode.nodeSize;
                
                if(nx + nwidth > width) nwidth = width % PictureNode.nodeSize;
                if(ny + nheight > height) nheight = height % PictureNode.nodeSize;
                
                PictureNode pn = new PictureNode();
                pn.boundary = new Rectangle2D(nx, ny, nwidth, nheight);
                pn.tile = new WritableImage(MainPictureData.getPixelReader(), nx, ny, nwidth, nheight);
                
                l.add(pn);
            }
        
        return l;
    }
    
    public void HandleExplosion(Explosion e)
    {
        Rectangle2D erect = new Rectangle2D(e.x, e.y, e.explosionSprite.getWidth(), e.explosionSprite.getHeight()); //benis
        
        for(PictureNode i : PictureTiles)
        {
            if(i.boundary.contains(erect))
            {
                PixelWriter levelpw = i.tile.getPixelWriter();
                PixelReader levelpr = i.tile.getPixelReader();
                PixelReader explpr = e.explosionSprite.getPixelReader();
                
                for(int x = 0; x < i.tile.getWidth(); x++)
                    for(int y = 0; y < i.tile.getHeight(); y++)
                    {
                        Color tc = levelpr.getColor(x, y);
                        if (tc.isOpaque() && erect.contains(new Point2D(x + i.boundary.getMinX(), y + i.boundary.getMinY())))
                        {
                            try
                            {
                                Color rep = explpr.getColor((int)(x + i.boundary.getMinX() - erect.getMinX()), (int)(y + i.boundary.getMinY() - erect.getMinY()));
                                levelpw.setColor(x, y, rep);
                            }
                            catch(IndexOutOfBoundsException ex)
                            {
                                
                            }
                            
                        }
                    }
            }
        }
    }
    
}
