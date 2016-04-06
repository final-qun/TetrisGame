package com.quicksand.tetrisgame.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.quicksand.tetrisgame.bean.GameBody;
import com.quicksand.tetrisgame.bean.GameItem;
import com.quicksand.tetrisgame.bean.Point;
import com.quicksand.tetrisgame.state.Style;

import java.util.ArrayList;
import java.util.List;

/**
 * onDrawd by shamaker on 16/3/29.
 */
public class GameView extends GameItem {
    private int style;
    private int state;
    private static final int DEFALUT_STEP = 1;

    private Point currentPoint;
    private GameBody currentGameBody;
    private GameBody nextGameBody;
    private List<Point>[] mHasItemPoint;
    private List<Point>[] mHasItemPointCopy;
    private Handler mHanlder;
    private int downStep = DEFALUT_STEP;
    private boolean isStart = false;

    public void setHandler(Handler handler){
        this.mHanlder = handler;
    }
    /**
     * 初始化界面
     *
     * @param context
     */
    public GameView(Context context) {
        super(context);
        invalidate();


    }
    public void initGameView(){
        setItem(Style.DEFAULT);
        currentGameBody = randomNewBody();
        nextGameBody = randomNewBody();
    }
    /**
     * 随机生成一个GameBody
     */
    public GameBody randomNewBody() {
        int num = (int) (Math.random() * 70);

        int randomStyle = num / 10 + 1;
        int randomState = (int) (Math.random() * 4);
        GameBody gameBody = new GameBody(randomStyle,randomState);
        return gameBody;
    }
    public void setDownStep(int downStep){
        this.downStep = downStep;
    }
    private void updateGameBody(){
        GameBody gameBody = new GameBody(nextGameBody.getStyle(),nextGameBody.getState());
        currentGameBody = gameBody;
        nextGameBody = randomNewBody();

        newGameBody();
    }

    public void newGameBody(){
        Message currentMsg = Message.obtain(mHanlder,Style.CURRENT_BODY_UPDATE,currentGameBody);
        currentMsg.sendToTarget();

        Message nextMsg = Message.obtain(mHanlder,Style.NEXT_BODY_UPDATE,nextGameBody);
        nextMsg.sendToTarget();

        int startX = mGameItemXCount == 0 ? 4 : shouldStartPoint();
        currentPoint = new Point(startX,0);
        style = currentGameBody.getStyle();
        state = currentGameBody.getState();
        currentGameBody.setStartPoint(currentPoint);
        drawGameBody(currentGameBody);
    }

    public GameBody getCurrentGameBody(){
        return currentGameBody;
    }

    public GameBody getNextGameBody(){
        return nextGameBody;
    }
    //通过点创建新的gamebody
    public void onDrawGameBody(Point startPoint) {
        setGameBody(style, state, startPoint);
    }

    public int shouldStartPoint() {
        if (style == Style.I && state == Style.RESERVE)
            return (mGameItemXCount - 4) / 2;
        if (style == Style.I && state == Style.NORMAL)
            return (mGameItemXCount - 1) / 2;
        if (((style == Style.S || style == Style.Z) && state == Style.NORMAL)
                || ((style == Style.L || style == Style.J) && (state == Style.LEFT || state == Style.RIGHT))
                || (style == Style.T && (style == Style.NORMAL || state == Style.RESERVE)))
            return (mGameItemXCount - 3) / 2;
        return (mGameItemXCount - 2) / 2;
    }

    private void setGameBody(int style, int state, Point startPoint) {
        GameBody gameBody = new GameBody(style, state, startPoint);

        currentGameBody = gameBody;

        drawGameBody(currentGameBody);
    }

    private void drawGameBody(GameBody gameBody) {
        List<Point> points = gameBody.getPoints();
        int currentStye = gameBody.getStyle();
        for (Point point : points) {
            setItem(currentStye, point.x, point.y);
        }
        drawItemView();
    }

    public void rotateGameBody() {
        if (isCanRotate()) {
            currentGameBody.rotateBody();
            state = currentGameBody.getState();
            currentPoint = currentGameBody.getStartPoint();
            drawGameBody(currentGameBody);
        } else {
            drawGameBody(currentGameBody);
        }
    }

    public boolean isCanRotate() {

        int rStyle = currentGameBody.getStyle();
        int rState = currentGameBody.getState();

        Point rStartPoint = currentGameBody.getStartPoint();
        GameBody gameBody = new GameBody(rStyle, rState, rStartPoint);
        gameBody.rotateBody();
        Point point = gameBody.getStartPoint();
        if (point.x < 0 || ((point.x + gameBody.getGameBodyWidth()) > mGameItemXCount)
                || point.y < 0 || ((point.y + gameBody.getGameBodyHeight()) > mGameItemYCount)) {
            return false;
        } else {
            List<Point> points = gameBody.getPoints();
            for (Point p : points) {
                if (mHasItemPoint[p.y].contains(p)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void moveGameBody(int direction) {
        if (currentGameBody == null) {
            return;
        }
        if (direction == Style.TODOWN) {
            if (isCanMove(direction)) {
                currentPoint.y += downStep;
            }
            onDrawGameBody(currentPoint);
        } else if (direction == Style.TOLEFT) {
            if (isCanMove(direction)) {
                currentPoint.x -= DEFALUT_STEP;
            }
            onDrawGameBody(currentPoint);
        } else if (direction == Style.TORIGHT) {
            if (isCanMove(direction)) {
                currentPoint.x += DEFALUT_STEP;
            }
            onDrawGameBody(currentPoint);
        }
    }

    public boolean isCanMove(int direction) {
        List<Point> points = currentGameBody.getPoints();
        switch (direction) {
            case Style.TODOWN:
                if ((currentPoint.y + 1 + currentGameBody.getGameBodyHeight()) > mGameItemYCount) {
                    return false;
                }
                points = Point.addPoints(points, new Point(0, 1));
                for (Point point : points) {
                    if (mHasItemPoint[point.y].contains(point)) {
                        return false;
                    }
                }
                return true;
            case Style.TOLEFT:
                if ((currentPoint.x - 1) < 0) {
                    return false;
                }
                points = Point.addPoints(points, new Point(-1, 0));
                for (Point point : points) {
                    if (mHasItemPoint[point.y].contains(point)) {
                        return false;
                    }
                }
                return true;
            case Style.TORIGHT:
                if ((currentPoint.x + 1 + currentGameBody.getGameBodyWidth()) > mGameItemXCount) {
                    return false;
                }
                points = Point.addPoints(points, new Point(1, 0));
                for (Point point : points) {
                    if (mHasItemPoint[point.y].contains(point)) {
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    public void shouldClear() {
        int count;
        if ((count = isShouldClear()) == 0) {
            return;
        }
        for (int i = mGameItemYCount - 1; i >= 0; i--) {
            if (mHasItemPoint[i].size() > 0 && mHasItemPoint[i].size() < mGameItemXCount) {
                for (int j = mGameItemYCount - 1; j >= 0; j--) {
                    if (mHasItemPointCopy[j].size() == 0) {
                        mHasItemPointCopy[j].addAll(mHasItemPoint[i]);
                        for (Point point : mHasItemPointCopy[j]) {
                            point.y = j;
                        }
                        break;
                    }
                }

            }
        }
        for (int i = mGameItemYCount - 1; i >= 0; i--) {
            mHasItemPoint[i].clear();
            mHasItemPoint[i].addAll(mHasItemPointCopy[i]);
            mHasItemPointCopy[i].clear();
        }
        caclulateGoal(count);
    }

    public int isShouldClear() {
        int count = 0;
        for (int i = mGameItemYCount - 1; i >= 0; i--) {
            if (mHasItemPoint[i].size() == mGameItemXCount) {
                count++;
            }
        }
        return count;
    }

    public void setListPoint(int i) {
        for (Point point : mHasItemPoint[i]) {
            point.y = i;
        }
    }

    public void caclulateGoal(int shouldClearCloumn) {

    }

    public void updateView() {
        if (mGameItemXCount != 0 && mHasItemPoint == null) {
            mHasItemPoint = new List[mGameItemYCount];
            for (int i = 0; i < mGameItemYCount; i++) {
                mHasItemPoint[i] = new ArrayList<Point>();
            }
            mHasItemPointCopy = new List[mGameItemYCount];
            for (int i = 0; i < mGameItemYCount; i++) {
                mHasItemPointCopy[i] = new ArrayList<Point>();
            }
        }

        if(!isStart){
            Log.i("xiqun","xiqun this is updateView1");
            clearItems();
            newGameBody();
            invalidate();
            isStart = true;
            return;
        }
        if(mHasItemPoint[0].size() != 0){
            Log.i("xiqun", "xiqun this is updateView1 size");
            Message.obtain(mHanlder,Style.GAME_OVER).sendToTarget();
            return;
        }
        Log.i("xiqun","xiqun this is updateView2 size");
        if (isCanMove(Style.TODOWN)) {
            clearItems();
            moveGameBody(Style.TODOWN);
            invalidate();
        } else {
            List<Point> points = currentGameBody.getPoints();
            for (Point point : points) {
                mHasItemPoint[point.y].add(point);
            }

            clearItems();
            shouldClear();
            updateGameBody();

            invalidate();
        }

    }

    public void drawItemView() {
        if (mHasItemPoint != null) {
            for (List<Point> list : mHasItemPoint) {
                for (Point point : list) {
                    setItem(point);
                }
            }
        }
    }

    public void clearGameView(){
        Log.i("xiqun","xiqun this is updateView1");
        for (List<Point> list:mHasItemPoint){
            list.clear();
        }
        currentGameBody = null;
        nextGameBody = null;
        isStart = false;
    }

}
