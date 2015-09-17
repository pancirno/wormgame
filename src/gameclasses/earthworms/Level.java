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
        divideImage();
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
    
    private void divideImage()
    {
        List<PictureNode> returnl = new ArrayList<>();
        
        int width = (int)MainPictureData.getWidth();
        int height = (int)MainPictureData.getHeight();
        
        int nx;//counts position of picture
        int ny;
        int nwidth; //check the size of resulting node
        int nheight;
        int mx = 0, my = 0; //size in matrix
        
        PictureNodeMatrix = new PictureNode[(int)Math.ceil((double)width/(double)PictureNode.nodeSize)][(int)Math.ceil((double)height/(double)PictureNode.nodeSize)];
        
        for(nx = 0; nx < width; nx += PictureNode.nodeSize)
        {
            List<PictureNode> horizontalmatrix = new ArrayList<>();
            for(ny = 0; ny < height; ny+= PictureNode.nodeSize)
            {
                nwidth = PictureNode.nodeSize;
                nheight = PictureNode.nodeSize;
                
                if(nx + nwidth > width) nwidth = width % PictureNode.nodeSize;
                if(ny + nheight > height) nheight = height % PictureNode.nodeSize;
                
                PictureNode pn = new PictureNode();
                pn.boundary = new Rectangle2D(nx, ny, nwidth, nheight);
                pn.tile = new WritableImage(MainPictureData.getPixelReader(), nx, ny, nwidth, nheight);
                
                returnl.add(pn);
                PictureNodeMatrix[mx][my++] = pn;
            }
            mx++;
            my=0;
        }
        
        PictureTiles = returnl;
                
    }
    
    
    public boolean Collide(int x, int y) //todo - compare to matrix instead of crawling through the list
    {
        return true;
    }
    
    public void HandleExplosion(Explosion e)
    {
        Rectangle2D erect = new Rectangle2D(e.x, e.y, e.explosionSprite.getWidth()-1, e.explosionSprite.getHeight()-1);
        
        PictureTiles.stream().filter((i) -> (i.boundary.intersects(erect))).forEach((i) ->
        {
            PixelWriter levelpw = i.tile.getPixelWriter();
            PixelReader levelpr = i.tile.getPixelReader();
            PixelReader explpr = e.explosionSprite.getPixelReader();
            
            for(int x = 0; x < i.tile.getWidth(); x++)
                for(int y = 0; y < i.tile.getHeight(); y++)
                {
                    Color tc = levelpr.getColor(x, y);
                    
                    int relx = (int) (x + i.boundary.getMinX());
                    int rely = (int) (y + i.boundary.getMinY());
                    
                    if (tc.isOpaque() && erect.contains(new Point2D(relx, rely)))
                    {
                        try
                        {
                            Color rep = explpr.getColor((int)(relx - erect.getMinX()), (int)(rely - erect.getMinY()));
                            if (rep.getBlue() == 1.0)
                                levelpw.setColor(x, y, Color.color(0, 0, 0, 0));
                            else if (rep.getRed() != 1.0)
                                levelpw.setColor(x, y, rep);
                        }
                        catch(IndexOutOfBoundsException ex)
                        {
                            System.err.println("pos: " + (int)(x + i.boundary.getMinX() - erect.getMinX()) + " " + (int)(y + i.boundary.getMinY() - erect.getMinY()));
                        }
                        
                    }
                }
        });
    }
    
}
