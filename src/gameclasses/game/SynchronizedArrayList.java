/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 *
 * @author pancirno
 * @param <T>
 */
public class SynchronizedArrayList<T> 
{
    ArrayList<T> objList = new ArrayList<>();
    ArrayList<T> objIncomingList = new ArrayList<>();
    ArrayList<T> objOutcomingList = new ArrayList<>();
    
    public SynchronizedArrayList()
    {
    }
    
    public Iterator<T> GetIterator()
    {
        return objList.iterator();
    }
    
    public Stream<T> GetStream()
    {
        return objList.stream();
    }
    
    public T[] GetArray()
    {
        return (T[])objList.toArray();
    }
    
    public Iterator<T> GetIteratorToAdd()
    {
        return objIncomingList.iterator();
    }
    
    public Iterator<T> GetIteratorToDel()
    {
        return objOutcomingList.iterator();
    }
    
    public boolean contains(T obj)
    {
        return objList.contains(obj);
    }
    
    public int size()
    {
        return objList.size();
    }
    
    public void InsertObject(T obj)
    {
        objIncomingList.add(obj);
    }
    
    public void RemoveObject(T obj)
    {
        objOutcomingList.add(obj);
    }
    
    public void Sync()
    {
        objIncomingList.stream().forEach((obj) -> {
            objList.add(obj);
        });
        
        objOutcomingList.stream().forEach((obj) -> {
            objList.remove(obj);
        });
        
        objIncomingList.clear();
        objOutcomingList.clear();
    }
}
