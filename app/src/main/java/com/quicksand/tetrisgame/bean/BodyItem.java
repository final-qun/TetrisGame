package com.quicksand.tetrisgame.bean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.quicksand.tetrisgame.state.Style;

/**
 * Created by shamaker on 16/3/29.
 */
public class BodyItem extends View {

    private static final int GAME_ITEM_SIZE = 60;
    public static int mGameItemXCount;
    public static int mGameItemYCount;
    private int mOffsetX;
    private int mOffsetY;
    private int mGameMatrics[][];

    private final Paint mPaint = new Paint();

    public BodyItem(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("xiqun", "xiqun this is onSizeChanged");
        mGameItemXCount = (int) Math.floor(w / GAME_ITEM_SIZE);
        mGameItemYCount = (int) Math.floor(h / GAME_ITEM_SIZE);
        Log.i("xiqun","xiqun this is onSizeChanged mGameItemXCount:"+mGameItemXCount+":mGameItemYCount:"+mGameItemYCount);
        mOffsetX = ((w - mGameItemXCount * GAME_ITEM_SIZE) / 2);
        mOffsetY = ((h - mGameItemYCount * GAME_ITEM_SIZE) / 2);
        mGameMatrics = new int[mGameItemXCount][mGameItemYCount];
        clearItems();
    }

    public void clearItems() {
        for (int x = 0; x < mGameItemXCount; x++) {
            for (int y = 0; y < mGameItemYCount; y++) {
                setItem(Style.DEFAULT, x, y);
            }
        }
    }

    public void setItem(int state, int x, int y) {
        if(x < 0 || y < 0 || x >= mGameItemXCount || y >= mGameItemYCount){
            return;
        }
        if (mGameMatrics != null) {
            mGameMatrics[x][y] = state;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < mGameItemXCount; x++) {
            for (int y = 0; y < mGameItemYCount; y++) {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAlpha(30);
                switch (mGameMatrics[x][y]) {
                    case Style.DEFAULT:
                        mPaint.setColor(Color.GRAY);
                        break;
                    case Style.S:
                        //S:红色
                        mPaint.setColor(Color.argb(154,255,0,0));
                        break;
                    case Style.Z:
                        //Z:橙色
                        mPaint.setColor(Color.argb(154, 255, 128, 0));
                        break;
                    case Style.L:
                        //L:黄色
                        mPaint.setColor(Color.argb(154,255,255,0));
                        break;
                    case Style.J:
                        //J:绿色
                        mPaint.setColor(Color.argb(154,0,255,0));
                        break;
                    case Style.I:
                        //I:浅蓝
                        mPaint.setColor(Color.argb(154, 0, 255, 255));
                        break;
                    case Style.O:
                        //O:蓝色
                        mPaint.setColor(Color.argb(154,0,0,255));
                        break;
                    case Style.T:
                        //T:紫色
                        mPaint.setColor(Color.argb(154, 187, 0, 255));
                        break;
                }
                int left = mOffsetX + x * GAME_ITEM_SIZE;
                int top = mOffsetY + y * GAME_ITEM_SIZE;
                canvas.drawRect(left, top, left + GAME_ITEM_SIZE, top + GAME_ITEM_SIZE, mPaint);
                //画边框
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.BLACK);
                canvas.drawRect(left, top, left + GAME_ITEM_SIZE, top + GAME_ITEM_SIZE, mPaint);
                //canvas.drawRoundRect(left, top, left + GAME_ITEM_SIZE, top + GAME_ITEM_SIZE, mPaint);
            }
        }
    }
}
