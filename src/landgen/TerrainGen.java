/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landgen;

import java.awt.*;
import java.awt.Image;
import java.awt.image.*;
import static java.awt.image.BufferedImage.TYPE_BYTE_INDEXED;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javafx.embed.swing.*;
import javafx.scene.image.*;
import javax.imageio.ImageIO;

/**
 *
 * @author lukasz
 */
public class TerrainGen {
    
    BufferedImage bi;
    Random r;
    
    int width;
    int height;
    
    double complication; //0.75 to 0.95
    double wigglyness;
    double tolerance;
    
    public TerrainGen(int seed)
    {
        OpenSimplexNoise osn;
        
        if(seed > 0)
        r = new Random(seed);
        else r = new Random();
        
        wigglyness = (-20 + r.nextInt(41))/100;
        tolerance = -1.30;
        complication = (double)r.nextInt(21)/100 + 0.75;
        
        System.out.println(complication + " ");
        
        width = 1920;
        height = 696;
        
        int intimg[][] = new int[width][height];
        
        osn = new OpenSimplexNoise(r.nextLong()); 
        
        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        double value;
        double[] tolerancetable = new double[height];
        
        for(double y = 0; y < height; y++)
        {
            //tolerancetable[(int)y] = (y/height);
            
            tolerancetable[(int)y] = QuartEaseOut(y, 0, complication, height);
        }
        
        //tworzymy pole bitowe
        for(double x = 0; x < width; x++)
            for(double y = 0; y < height; y++)
            {
                //original = value = osn.eval(x / 96, y / 96) * 0.75 + osn.eval(x / 128,  y / 128) * 0.25;
                value = osn.eval(x / 96,  y / 96) * (0.75+wigglyness) + osn.eval(x / 128,  y / 128) * (0.25-wigglyness);
                
                double island = switchisland(x);
                
                if(value > (tolerancetable[(int)y] * tolerance) + island)
                {
                    intimg[(int)x][(int)y] = 1;
                }
                else intimg[(int)x][(int)y] = 0;
            }
        
        //wydzielamy wyspy
        intimg = separateislands(intimg, width, height, 0);
        
        //usuwamy unoszacy sie teren
        intimg = deletefloating(intimg, width, height, 0, false);
        
        //wydzielamy dziury
        intimg = separateislands(intimg, width, height, 1);
        
        //usuwamy dziury
        intimg = deletefloating(intimg, width, height, 1, true);
        
        //robimy podloze
        //ground temp - 0: brak podloza; 1-10: podloze; 
        int[][] groundtemp = new int[width][height];
        boolean finisheddrawing = true;
        
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height;) 
            {
                if(intimg[x][y] == 0)
                {
                    finisheddrawing = false;
                    y++;
                    continue;
                }
                
                int i = 0;
                
                if(intimg[x][y] == 1)
                {
                    if(finisheddrawing)
                    {
                        y++;
                        continue;
                    }
                    
                    for(i = 0; i <= 10; i++) 
                    {
                        if(y+i >= height) 
                        {
                            finisheddrawing = true;
                            break;
                        }
                        
                        if(intimg[x][y+i] == 1)
                        {
                            groundtemp[x][y+i] = i+1;
                            if(i >= 10) finisheddrawing = true;
                        }
                        else
                        {
                            finisheddrawing = true;
                            break;
                        }
                    }
                }
                y += 1+i; 
            }
        
        //teksturujemy
        TerrainTexture tt = new TerrainTexture(r.nextInt());
        Color c;
        
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
            {
                if(groundtemp[x][y] > 0)
                {
                    c = tt.colorInterpolate((double)groundtemp[x][y]/10);
                    bi.setRGB(x, y, c.getRGB());
                    continue;
                }
                
                if(intimg[x][y] == 0) c = Color.BLACK;
                else
                    c = tt.getColor(x, y);
                
                bi.setRGB(x, y, c.getRGB());
            }
    }
    
    private double switchisland(double xval)
    {
        if(xval < 100) return interpolate(3,1, (xval/100));
        else if(xval > width - 100) return interpolate(1,3, ((xval - (width - 100))/100));
        
        else return 1;
    }
    
    private double interpolate(int min, int max, double step)
    {
        return min*(1-step) + max*step;
    }
    
    private int[][] separateislands(int[][] input, int wx, int wy, int background)
    {
                
        int[][] analyze = new int[wx+2][wy+2]; //wymiary wieksze o 2 od oryginalu
        
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        
        int nextlabel = 1000;
        
        LinkedList<Point> pixellist;
        
        //etap 1 - zebranie relacji miedzy labelami
        for(int iy = 1; iy < wy+1; iy++)
            for(int ix = 1; ix < wx+1; ix++)
                if(input[ix-1][iy-1] != background)
                {
                    if(analyze[ix][iy] > 0) continue;
                    
                    analyze[ix][iy] = ++nextlabel;
                    pixellist = new LinkedList<>();
                    pixellist.push(new Point(ix,iy));
                    
                    do
                    {
                        Point p = pixellist.pop();
                        for(int it = 0; it < 4; it++)
                        {
                            int fx = p.x + dx[it];
                            int fy = p.y + dy[it];
                            
                            try
                            {
                                if(input[fx-1][fy-1] != background && analyze[fx][fy] == 0)
                                {
                                    analyze[fx][fy] = nextlabel;
                                    pixellist.push(new Point(fx,fy));
                                }
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                
                            }
                            
                        }
                    }
                    while(pixellist.size() > 0);
                    
                }
            
        
        int[][] output = new int[wx][wy];
        
        for(int iy = 1; iy < wy+1; iy++)
            for(int ix = 1; ix < wx+1; ix++)
                output[ix-1][iy-1] = analyze[ix][iy];
        
        //System.out.println(nextlabel);
        
        return output;
    }

    //finds marked floating islands and fills them with background, and the rest is filled with background+1
    private int[][] deletefloating(int[][] intimg, int x, int y, int background, boolean checksky) 
    {
        HashMap<Integer, Boolean> islandstokeep = new HashMap<>();
        
        int checkposition;
        if(checksky) checkposition = 0;
        else checkposition = y-1;
        
        for(int ix = 0; ix < x; ix++)
        {
            if(intimg[ix][checkposition] != background)
                islandstokeep.put(intimg[ix][checkposition], Boolean.TRUE);
        }
        
        if(checksky)
            for(int iy = 0; iy < y; iy++)
                for(int ix = 0; ix < x; ix++)
                {
                    intimg[ix][iy] = islandstokeep.containsKey(intimg[ix][iy]) ? 0 : 1;
                }
        else
            for(int iy = 0; iy < y; iy++)
                for(int ix = 0; ix < x; ix++)
                {
                    intimg[ix][iy] = islandstokeep.containsKey(intimg[ix][iy]) ? 1 : 0;
                }
        
        return intimg;
    }
    
    //easing algorithms for island generation by Robert Penner
    //t - x position of the math function
    //b - beginning value
    //c - how much we want to increase
    //d - total length of function
    public static double QuartEaseOut(double t, double b, double c, double d) 
    {
        return -c * ((t=t/d-1)*t*t*t - 1) + b;
    }
    
    public WritableImage returnImage()
    {
        return SwingFXUtils.toFXImage(bi, null);
    }
    
    public void saveimage(String filename)
    {
        try {
            ImageIO.write(bi, "png", new File(filename));
        } catch (IOException ex) {
            //Logger.getLogger(Terraingen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveimagereduced(String filename)
    {
        LinkedHashSet<Color> rgbs = new LinkedHashSet();
        
        rgbs.add(Color.BLACK);
        
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
            {
                int rgb = bi.getRGB(x, y);
                if(rgb != Color.BLACK.getRGB())
                rgbs.add(new Color(rgb));
            }
        
        byte[] reds = new byte[rgbs.size()];
        byte[] greens = new byte[rgbs.size()];
        byte[] blues = new byte[rgbs.size()];
                
        int i = 0;
        
        for (Color c : rgbs) {
          reds[i] = (byte) c.getRed();
          greens[i] = (byte) c.getGreen();
          blues[i] = (byte) c.getBlue();
          i++;
        }
        
        IndexColorModel cm = new IndexColorModel(8, reds.length, reds, greens, blues);
        
        BufferedImage indexed = new BufferedImage(width, height, TYPE_BYTE_INDEXED, cm);
        
        indexed.createGraphics().drawImage(bi, 0, 0, null);
        
        try {
            ImageIO.write(indexed, "png", new File(filename));
        } catch (IOException ex) {
            //Logger.getLogger(Terraingen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        indexed.flush();
    }
        
    protected void finalize()
    {
            bi.flush();
    }
    
}
