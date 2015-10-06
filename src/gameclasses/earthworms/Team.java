/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author samsung
 */
public class Team {
    
    public String name;
    public ArrayList<String> playernames = new ArrayList<>();
    public Color teamcolor;
    public int teamweapon;
    
    public ArrayList<Player> ingameobjects = new ArrayList<>();
    
    public Team(String iname, String iplay1, String iplay2, String iplay3, String iplay4, Color iteamcolor, int iteamweapon)
    {
        name = iname;
        playernames.add(iplay1);
        playernames.add(iplay2);
        playernames.add(iplay3);
        playernames.add(iplay4);
        teamcolor = iteamcolor;
        teamweapon = iteamweapon;
    }
    
}
