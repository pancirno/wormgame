/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landgen;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author lukasz
 */
public class TerrainTexture {
    
    Random r;
    OpenSimplexNoise osn;
    
    Color first;
    Color second;
        
    public TerrainTexture(int seed)
    {
        if(seed > 0)
            r = new Random(seed);
        else
            r = new Random();
        
        osn = new OpenSimplexNoise(seed);
        
        float hue = r.nextFloat();
        float sat = (float) (0.25 + (r.nextFloat()/2));
        float bri = (float) (0.25 + (r.nextFloat()/2));
        
        first = new Color(Color.HSBtoRGB(hue, sat ,bri));
        second = new Color(Color.HSBtoRGB((float)(hue + 0.15 + r.nextFloat()/2), (float)(sat - 0.10), (float)(bri + 0.10)));
    }
    
    public Color getColor(int x, int y)
    {
        double d = osn.eval((double)x/32, (double)y/32);
        if(d <= 0) return first;
        else return second;
    }
    
    public Color colorInterpolate(Color fst, Color snd, double percent)
    {
        //double r = (double)fst.getRGB() * (1 - percent) + (double)snd.getRGB() * (percent);
        //return new Color((int)Math.floor(r), false);
        
        int r = (int)((double)fst.getRed() * (1 - percent) + (double)snd.getRed() * (percent)) & 255;
        int g = (int)((double)fst.getGreen() * (1 - percent) + (double)snd.getGreen() * (percent)) & 255;
        int b = (int)((double)fst.getBlue() * (1 - percent) + (double)snd.getBlue() * (percent)) & 255;
                
        return new Color(r,g,b);
    }
    
    public Color colorInterpolate(double percent)
    {
        return colorInterpolate(first, second, percent);
    }
    
}
