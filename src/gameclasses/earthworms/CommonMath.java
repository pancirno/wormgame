/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclasses.earthworms;

import java.util.*;
import javafx.geometry.Point2D;

/**
 *
 * @author pancirno
 */
public class CommonMath
{
    public static double getDiffAngle(double xDiff, double yDiff)
    {
        return Math.atan2(xDiff, yDiff);
    }
    
    public static double getInvertedDiffAngle(double xDiff, double yDiff)
    {
        return Math.atan2(xDiff, yDiff) - Math.PI/2;
    }
    
    public static Point2D getDirectionVector(double angle)
    {
        return new Point2D(Math.cos(angle), Math.sin(angle) * -1);
    }
    
    public static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
