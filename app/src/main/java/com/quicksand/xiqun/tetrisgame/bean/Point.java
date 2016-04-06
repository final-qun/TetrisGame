package com.quicksand.xiqun.tetrisgame.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiqun on 16/3/30.
 */
public class Point{
    public int x;
    public int y;
    public int style;

    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Point(int x,int y,int style){
        this.x = x;
        this.y = y;
        this.style = style;
    }
    @Deprecated
    public static List<Point> setIntToPoints(int[][] array){
        List<Point> points = new ArrayList<Point>();
        Point point;
        for (int[] p : array){
            point = new Point(p[0],p[1]);
            points.add(point);
        }
        return points;
    }
    public static List<Point> setIntToPoints(int[][] array,int style){
        List<Point> points = new ArrayList<Point>();
        Point point;
        for (int[] p : array){
            point = new Point(p[0],p[1],style);
            points.add(point);
        }
        return points;
    }
    @Deprecated
    public static List<Point> setIntToPoints(int[] array){
        List<Point> points = new ArrayList<Point>();
        Point point;
        for (int i = 0;i<array.length ; i=i+2){
            point = new Point(array[i],array[i+1]);
            points.add(point);
        }
        return points;
    }
    @Deprecated
    public static List<Point> setIntToPoints(Point[] array){
        List<Point> points = new ArrayList<Point>();
        Point point;
        for (Point p : array){
            points.add(p);
        }
        return points;
    }
    @Deprecated
    public static Point addPoint(Point p1,Point p2){
        return new Point((p1.x+p2.x),(p1.y+p2.y));
    }
    public static Point addPoint(Point p1,Point p2,int style){
        return new Point((p1.x+p2.x),(p1.y+p2.y),style);
    }

    public static Point changePoint(Point point,int x,int y){
        return new Point((point.x+x),(point.y+y),point.style);
    }

    public static List<Point> addPoints(List<Point> points,Point p){
        List<Point> targetPoints = new ArrayList<Point>();
        for (Point point:points){
            targetPoints.add(addPoint(point, p,point.style));
        }
        return targetPoints;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        Point point = (Point) o;
        if(this.x != point.x)
            return false;
        if (this.y != point.y)
            return false;
        return true;
    }
}
