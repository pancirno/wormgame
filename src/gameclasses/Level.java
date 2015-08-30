/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import landgen.*;

/**
 *
 * @author pancirno
 */
public class Level
{
    Image PictureData;
    TerrainGen LevelGenerator;
    
    public Level()
    {
        LevelGenerator = new TerrainGen(667);
        PictureData = LevelGenerator.returnImage();
    }
    
    public void render(GraphicsContext gc, CameraData cd)
    {
        gc.drawImage(PictureData, cd.GetBoundary().getMinX(), cd.GetBoundary().getMinY());
    }
}
