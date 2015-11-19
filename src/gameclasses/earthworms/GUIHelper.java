/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import java.util.HashMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 *
 * @author pancirno
 */
public class GUIHelper
{
    static Text textObject = new Text();
    static HashMap<String, Integer> textWidthCache = new HashMap<>();
    
    static public void drawRoundCube(GraphicsContext gc, int x, int y, int w, int h)
    {
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(x, y, w, h, 4, 4);
        gc.strokeRoundRect(x, y, w, h, 4, 4);
    }
    
    public enum boxAlignment
    {
        left,
        center,
        right
    }
    
    static public void drawTextCube(GraphicsContext gc, int x, int y, String s, Paint p, boxAlignment top)
    {
        int lt = renderedTextWidth(s);
        
        switch(top)
        {
            case left:
                drawRoundCube(gc, x, y, lt + 8, 16);
                gc.setStroke(p);
                gc.strokeText(s, x+4, y+12);
                break;
            
            case center:
                drawRoundCube(gc, x - (lt/2) - 4, y, lt + 8, 16);
                gc.setStroke(p);
                gc.strokeText(s, x - (lt/2), y+12);
                break;
        }
        
        
    }
    
    static int renderedTextWidth(String s)
    {
        if(textWidthCache.size() > 256) 
        {
            textWidthCache.clear();
        }
        
        if(textWidthCache.containsKey(s)) return textWidthCache.get(s);
        else
        {
            textObject.setText(s);
            int i = (int)textObject.getLayoutBounds().getWidth();
            textWidthCache.put(s, i);
            return i;
        }
        
    }
}