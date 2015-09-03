/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import gameclasses.game.Camera;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import landgen.*;

/**
 *
 * @author pancirno
 */
public class Level
{
    WritableImage FrontPictureData;
    
    
    public Level()
    {
        TerrainGen LevelGenerator = new TerrainGen(8795649);
        FrontPictureData = LevelGenerator.returnImage();
    }
    
    public void render(GraphicsContext gc, Camera cd)
    {
        gc.drawImage(FrontPictureData, cd.GetBoundary().getMinX(), cd.GetBoundary().getMinY());
    }
}
