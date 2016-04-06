package com.quicksand.xiqun.tetrisgame.bean;

import com.quicksand.xiqun.tetrisgame.state.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by xiqun on 16/3/31.
 */
public class GameBody extends Observable{
    private Point startPoint;
    private int style;
    private int state;
    private List<Point> points;
    private int bodyWidth;
    private int bodyHeight;
    public GameBody(){}
    public GameBody(int style, int state, Point startPoint) {
        this.style = style;
        this.state = state;
        this.startPoint = startPoint;
        points = new ArrayList<>();
    }
    public GameBody(int style, int state) {
        this(style,state,new Point(0,0));
    }

    public List<Point> getPoints(){
        switch (style){
            case Style.S:
                points = createBodyS(state);
                break;
            case Style.Z:
                points = createBodyZ(state);
                break;
            case Style.L:
                points = createBodyL(state);
                break;
            case Style.J:
                points = createBodyJ(state);
                break;
            case Style.I:
                points = createBodyI(state);
                break;
            case Style.O:
                points = createBodyO(state);
                break;
            case Style.T:
                points = createBodyT(state);
                break;
        }
        points = Point.addPoints(points,startPoint);
        return points;
    }

    /**
     * S
     * {1,0},{2,0},{0,1},{1,1}
     * {0,0},{0,1},{1,1},{1,2}
     */
    private List<Point> createBodyS(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{1,0},{2,0},{0,1},{1,1}},style);
        }else{
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{0,0},{0,1},{1,1},{1,2}},style);
        }
    }

    /**
     *Z
     * {0,0},{1,0},{1,1},{2,1}
     * {1,0},{0,1},{1,1},{0,2}
     */
    private List<Point> createBodyZ(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{1,1},{2,1}},style);
        }else{
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{1,0},{0,1},{1,1},{0,2}},style);
        }
    }

    /**
     *L
     * {0,0},{0,1},{0,2},{1,2}
     * {2,0},{0,1},{1,1},{2,1}
     * {0,0},{1,0},{1,1},{1,2}
     * {0,0},{1,0},{2,0},{0,1}
     */
    private List<Point> createBodyL(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{0,0},{0,1},{0,2},{1,2}},style);
        } else if (state == Style.RIGHT){

            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{2,0},{0,1},{1,1},{2,1}},style);
        } else if (state == Style.RESERVE){
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{1,1},{1,2}},style);
        }else{
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{2,0},{0,1}},style);
        }
    }

    /**
     *J
     * {1,0},{1,1},{0,2},{1,2}
     * {0,0},{1,0},{2,0},{2,1}
     * {0,0},{1,0},{0,1},{0,2}
     * {0,0},{0,1},{1,1},{2,1}
     */
    private List<Point> createBodyJ(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{1,0},{1,1},{0,2},{1,2}},style);
        } else if (state == Style.RIGHT){
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{2,0},{2,1}},style);
        } else if (state == Style.RESERVE){
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{0,1},{0,2}},style);
        }else{
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{0,0},{0,1},{1,1},{2,1}},style);
        }
    }

    /**
     *I
     * {0,0},{0,1},{0,2},{0,3}
     * {0,0},{1,0},{2,0},{3,0}
     */
    private List<Point> createBodyI(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(1,4);
            return Point.setIntToPoints(new int[][]{{0,0},{0,1},{0,2},{0,3}},style);
        }else{
            setBodyWidthAndHeight(4,1);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{2,0},{3,0}},style);
        }
    }

    /**
     *O
     * {0,0},{1,0},{1,0},{1,1}
     */
    private List<Point> createBodyO(int state){
        setBodyWidthAndHeight(2,2);
        return Point.setIntToPoints(new int[][]{{0,0},{1,0},{0,1},{1,1}},style);
    }

    /**
     * T
     * {0,0},{1,0},{2,0},{1,1}
     * {0,0},{0,1},{1,1},{0,2}
     * {1,0},{0,1},{1,1},{2,1}
     * {1,0},{0,1},{1,1},{1,2}
     */
    private List<Point> createBodyT(int state){
        if(state == Style.NORMAL){
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{0,0},{1,0},{2,0},{1,1}},style);
        } else if (state == Style.RIGHT){
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{0,0},{0,1},{1,1},{0,2}},style);
        } else if (state == Style.RESERVE){
            setBodyWidthAndHeight(3,2);
            return Point.setIntToPoints(new int[][]{{1,0},{0,1},{1,1},{2,1}},style);
        }else{
            setBodyWidthAndHeight(2,3);
            return Point.setIntToPoints(new int[][]{{1,0},{0,1},{1,1},{1,2}},style);
        }
    }
    public int getStyle(){
        return style;
    }

    public int getGameBodyWidth(){
        return bodyWidth;
    }

    public int getGameBodyHeight(){
        return bodyHeight;
    }
    public int getState(){
        return state;
    }

    private void setBodyWidthAndHeight(int width,int height){
        this.bodyWidth = width;
        this.bodyHeight = height;
    }

    public void rotateBody(){
        if(style == Style.O) {
            return;
        }
        points.clear();
        changeStartPoint();
        changeState();
        points = Point.addPoints(points,startPoint);
    }
    private void changeState(){
        if(state == Style.NORMAL){
            state = Style.RIGHT;
        }else if(state == Style.RIGHT){
            if(style == Style.S || style == Style.Z || style == Style.I){
                state = Style.NORMAL;
            }else {
                state = Style.RESERVE;
            }
        }else if(state == Style.RESERVE){
            state = Style.LEFT;
        }else {
            state = Style.NORMAL;
        }
    }
    private void changeStartPoint(){
        switch (style) {
            case Style.S:
            case Style.Z:
                if(state == Style.NORMAL){
                    setBodyWidthAndHeight(2,3);
                    startPoint = Point.changePoint(startPoint,1,-1);
                }else{
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,-1,1);
                }
                break;
            case Style.L:
                if(state == Style.NORMAL){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,-1,0);
                }else if(state == Style.RESERVE){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,0,1);
                }else if(state ==Style.LEFT){
                    setBodyWidthAndHeight(2,3);
                    startPoint = Point.changePoint(startPoint,1,-1);
                }else {
                    setBodyWidthAndHeight(2,3);
                }
                break;
            case Style.J:
                if(state == Style.NORMAL){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,0,1);
                }else if(state == Style.RIGHT){
                    setBodyWidthAndHeight(2,3);
                    startPoint = Point.changePoint(startPoint,1,-1);
                }else if(state == Style.RESERVE){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,-1,0);
                }else{
                    setBodyWidthAndHeight(2,3);
                }
                break;
            case Style.I:
                if(state == Style.NORMAL){
                    setBodyWidthAndHeight(4,1);
                    startPoint = Point.changePoint(startPoint,0,0);
                }else{
                    setBodyWidthAndHeight(1,4);
                    startPoint = Point.changePoint(startPoint,0,0);
                }
                break;
            case Style.T:
                if(state == Style.NORMAL){
                    setBodyWidthAndHeight(2,3);
                    startPoint = Point.changePoint(startPoint,1,-1);
                }else if(state == Style.RIGHT){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,-1,0);
                }else if(state == Style.LEFT){
                    setBodyWidthAndHeight(3,2);
                    startPoint = Point.changePoint(startPoint,0,1);
                }else{
                    setBodyWidthAndHeight(2,3);
                }
                break;
        }
    }
    public Point getStartPoint(){
        return startPoint;
    }
    public void setStartPoint(Point startPoint){
        this.startPoint = startPoint;
    }
}
