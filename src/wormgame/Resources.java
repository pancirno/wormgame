/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

/**
 *
 * @author pancirno
 */
public class Resources 
{
    private static Resources instance = null;
    
    protected Resources(){}
    
    public static boolean initalizeResources()
    {
         instance = new Resources();
         instance.loadResources();
         
         return true;
    }
    
    public static Resources getInstance()
    {        
        return instance;
    }
    
    private HashMap<String, Image> gfx = new HashMap<>();
    private HashMap<String, AudioClip> sfx = new HashMap<>();
    
    private ArrayList<AudioClip> playList = new ArrayList<>();

    private void loadResources() 
    {
        File[] directoryListing;
        File gfxdir = new File("./gfx/");
        File sfxdir = new File("./sfx/");
        
        directoryListing = gfxdir.listFiles();
        if (directoryListing != null) 
        {
            for (File f : directoryListing) 
                try
                {
                    gfx.put("gfx/" + f.getName(), new Image(new FileInputStream(f)));
                } 
                catch (FileNotFoundException ex) 
                {
                    Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        directoryListing = sfxdir.listFiles();
        if (directoryListing != null) 
        {
            for (File f : directoryListing) 
                    sfx.put("sfx/" + f.getName(), new AudioClip(f.toURI().toString()));
        }
    }
    
    public void playSounds()
    {
        for (AudioClip au : playList)
        {
            au.play();
        }
        playList.clear();
    }
    
    public void registerSound(String s)
    {
        AudioClip au = sfx.getOrDefault(s, null);
        if(au != null)
        {
            playList.add(au);
        }
        
    }
}
