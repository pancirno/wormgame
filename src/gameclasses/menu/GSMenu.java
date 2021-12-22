/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.menu;

import gameclasses.loop.GSGame;
import gameclasses.loop.GameState;
import gameclasses.loop.MainLoop;

/**
 *
 * @author pancirno
 */
public class GSMenu extends GameState
{
    private boolean  _showMenu = false;
    private MainMenu _menuInstance;
    
    @Override
    protected void execute(MainLoop loop) 
    {
        if(!_showMenu)
        {
            _showMenu = true;
            
            _menuInstance = new MainMenu();
            _menuInstance.setVisible(true);
        }
        else if(!_menuInstance.isVisible())
        {
            if(_menuInstance.IsGameStarted())
            {
                loop.SetNewGameState(new GSGame());
            }
            else
            {
                
            }
        }
    }
}
