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
    
    public Level()
    {
        TerrainGen LevelGenerator = new TerrainGen(8795649);
        MainPictureData = LevelGenerator.returnImage();
        PictureTiles = divideImage();
    }
    
    public void render(GraphicsContext gc, Camera cd)
    {
        //gc.drawImage(MainPictureData, cd.GetBoundary().getMinX(), cd.GetBoundary().getMinY());
        
        for (PictureNode i : PictureTiles)
        {
            gc.drawImage(i.tile, i.boundary.getMinX() + cd.GetBoundary().getMinX(), i.boundary.getMinY() + cd.GetBoundary().getMinY());
        }
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
    
}
