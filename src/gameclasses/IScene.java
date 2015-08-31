/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses;

import javafx.scene.canvas.*;
import wormgame.*;

/**
 *
 * @author pancirno
 */
public interface IScene
{
    public void execute(InputEngine ie, GraphicsContext dc);
}
