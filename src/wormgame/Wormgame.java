/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wormgame;

import javafx.application.Application;
import javafx.stage.*;

/**
 *
 * @author pancirno
 */
public class Wormgame extends Application
{
    
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("wormgame");
        primaryStage.setScene(MainWindow.Instance.getSceneContainer());
        //primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        
        MainWindow.Instance.setCanvasScalable(primaryStage);
        
        primaryStage.show();
    }
    
    @Override
    public void init()
    {
        System.out.println("init happened");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
