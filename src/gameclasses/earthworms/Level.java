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
        public static final int nodeSize = 128;
        
        WritableImage tile;
        Rectangle2D boundary;
    }
    
    WritableImage MainPictureData;
    List<PictureNode> PictureTiles;
    
    public Level()
    {
        TerrainGen LevelGenerator = new TerrainGen(8795649);
        MainPictureData = LevelGenerator.returnImage();
    }
    
    public void render(GraphicsContext gc, Camera cd)
    {
        gc.drawImage(MainPictureData, cd.GetBoundary().getMinX(), cd.GetBoundary().getMinY());
    }
    
    private List<PictureNode> divideImage()
    {
        List<PictureNode> l = new ArrayList<>();
        
        int width = (int)MainPictureData.getWidth();
        int height = (int)MainPictureData.getHeight();
        
        for(int x = 0; x * PictureNode.nodeSize < width; x++)
            for(int y = 0; y * PictureNode.nodeSize < height; y++)
            {
                PictureNode pn = new PictureNode();
                pn.boundary = new Rectangle2D(x * PictureNode.nodeSize, y * PictureNode.nodeSize, PictureNode.nodeSize, PictureNode.nodeSize);
                pn.tile = new WritableImage(MainPictureData.getPixelReader(), x * PictureNode.nodeSize, y * PictureNode.nodeSize, PictureNode.nodeSize, PictureNode.nodeSize);
            }
        
        return l;
    }
    
}
