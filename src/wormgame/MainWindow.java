/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;

/**
 *
 * @author pancirno
 */
public class MainWindow
{
    public static MainWindow Instance = new MainWindow();
    
    private Scene SceneContainer;
    private GameLoop Game;
    private Group RenderContainer;
    private Canvas DrawObject;
    
    private MainWindow()
    {        
        RenderContainer = new Group();
        SceneContainer = new Scene(RenderContainer, 800, 600, Color.BLACK);
        DrawObject = new Canvas(800,600);
        RenderContainer.getChildren().add(DrawObject);
        
        Game = new GameLoop();
        Game.AttachRenderContext(DrawObject.getGraphicsContext2D());
        
        SceneContainer.setOnKeyPressed((KeyEvent event) ->
        {
            Game.OnPress(event.getCode());
        });
        
        SceneContainer.setOnKeyReleased((KeyEvent event) ->
        {
            Game.OnRelease(event.getCode());
        });
        
        Game.start();
    }
    
    public Scene getSceneContainer()
    {
        return SceneContainer;
    }
}
