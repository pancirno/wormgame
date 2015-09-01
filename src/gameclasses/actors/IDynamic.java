/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.actors;

/**
 *
 * @author pancirno
 */
public interface IDynamic
{
    public void pre_step();
    
    public void step();
    
    public void after_step();
}
