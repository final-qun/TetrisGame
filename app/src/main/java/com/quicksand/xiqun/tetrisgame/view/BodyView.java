package com.quicksand.xiqun.tetrisgame.view;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;

import com.quicksand.xiqun.tetrisgame.bean.BodyItem;
import com.quicksand.xiqun.tetrisgame.bean.GameBody;
import com.quicksand.xiqun.tetrisgame.bean.Point;

import java.util.List;

/**
 * Created by xiqun on 16/4/5.
 */
public class BodyView extends BodyItem{

    private GameBody mCurrentBody;
    private GameBody mNextBody;


    public BodyView(Context context) {
        super(context);
    }

    public void drawGameBody(GameBody gameBody) {
        List<Point> points = gameBody.getPoints();
        int currentStye = gameBody.getStyle();
        for (Point point : points) {
            setItem(currentStye, point.x, point.y);
        }

    }

    public void setmCurrentBody(GameBody gameBody) {
        int style = gameBody.getStyle();
        int state = gameBody.getState();
        mCurrentBody = new GameBody(style,state);
        drawGameBody(mCurrentBody);
        invalidate();
    }

    public void setmNextBody(GameBody gameBody){
        int style = gameBody.getStyle();
        int state = gameBody.getState();
        mNextBody = new GameBody(style,state);
        drawGameBody(mNextBody);
        invalidate();
    }

//    @Override
//     public boolean handleMessage(Message msg) {
//        switch (msg.what){
//            case CURRENT_BODY_UPDATE:
//                setmCurrentBody((GameBody) msg.obj);
//                break;
//            case NEXT_BODY_UPDATE:
//                setmCurrentBody((GameBody) msg.obj);
//                break;
//        }
//        return true;
//    }

}
