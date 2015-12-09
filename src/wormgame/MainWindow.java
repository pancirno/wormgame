/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import gameclasses.loop.MainLoop;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**
 *
 * @author pancirno
 */
public class MainWindow
{
    public static MainWindow Instance = new MainWindow();
    
    private Scene SceneContainer;
    private MainLoop Game;
    private Group RenderContainer;
    private Canvas DrawObject;
    
    private MainWindow()
    {        
        Resources.initalizeResources();
        
        RenderContainer = new Group();
        SceneContainer = new Scene(RenderContainer, 800, 600, Color.BLACK);
        DrawObject = new Canvas();
        
        RenderContainer.getChildren().add(DrawObject);
        
        try
        {
            Game = new MainLoop(DrawObject.getGraphicsContext2D());
        }
        catch(Exception e)
        {
            System.err.printf(e.getMessage());
        }
        
        SceneContainer.setOnKeyPressed((KeyEvent event) ->
        {
            Game.OnPress(event.getCode());
        });
        
        SceneContainer.setOnKeyReleased((KeyEvent event) ->
        {
            Game.OnRelease(event.getCode());
        });
        
        SceneContainer.setOnMouseClicked((MouseEvent event) ->
        {
            Game.OnClick(event.getSceneX(), event.getSceneY());
        });
        
        Game.start();
    }
    
    public Scene getSceneContainer()
    {
        return SceneContainer;
    }
    
    public void setCanvasScalable(Stage s)
    {
        DrawObject.widthProperty().bind(s.widthProperty());
        DrawObject.heightProperty().bind(s.heightProperty());
    }
}
