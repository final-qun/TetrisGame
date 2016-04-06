package com.quicksand.xiqun.tetrisgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicksand.xiqun.tetrisgame.bean.GameBody;
import com.quicksand.xiqun.tetrisgame.state.Style;
import com.quicksand.xiqun.tetrisgame.util.TimeUitl;
import com.quicksand.xiqun.tetrisgame.view.BodyView;
import com.quicksand.xiqun.tetrisgame.view.GameView;


public class MainActivity extends Activity implements View.OnClickListener,View.OnTouchListener {

    private TextView mGameTime;
    private TextView mGameScore;

    private GameView mGameView;
    private BodyView mNextGameBody;
    private BodyView mCurrentGameBody;

    private Button mOptionDown;
    private Button mOptionLeft;
    private Button mOptionRight;
    private Button mOptionRotate;

    private static final int DELAY_TIME = 1000;
    private static final int TIME_UPDATE = 0x1111;
    private static final int VIEW_UPDATE = 0x1112;
    private static int currentTime = 0;

    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

        initBodyView();
        updateView();
    }

    private Handler updateShowBodyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Style.CURRENT_BODY_UPDATE:
                    mCurrentGameBody.clearItems();
                    mCurrentGameBody.setmCurrentBody((GameBody) msg.obj);
                    mCurrentGameBody.invalidate();
                    break;
                case Style.NEXT_BODY_UPDATE:
                    mNextGameBody.clearItems();
                    mNextGameBody.setmCurrentBody((GameBody) msg.obj);
                    mNextGameBody.invalidate();
                    break;
                case Style.GAME_OVER:
                    viewUpdateHandler.removeMessages(VIEW_UPDATE);
                    timeUpdateHandler.removeMessages(TIME_UPDATE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.game_over)
                            .setCancelable(false)
                            .setMessage(getResources().getString(R.string.game_over_lable, mGameScore.getText().toString(), currentTime))
                            .setNegativeButton(R.string.finish_game, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.this.finish();
                                }
                            })
                            .setPositiveButton(R.string.start_game, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("xiqun", "xiqun this is onClick");
                                    currentTime = 0;
                                    mGameView.clearGameView();
                                    mGameView.invalidate();

                                    updateView();
                                }
                            });
                    builder.show();
                    break;
            }
        }
    };

    private void initBodyView() {
        mGameView = new GameView(this);
        mGameView.setHandler(updateShowBodyHandler);
        RelativeLayout gameViewPanel = (RelativeLayout) findViewById(R.id.game_view_pl);
        gameViewPanel.invalidate();
        gameViewPanel.addView(mGameView);

        mNextGameBody = new BodyView(this);
        RelativeLayout nextBodyPanel = (RelativeLayout) findViewById(R.id.next_view_pl);
        nextBodyPanel.invalidate();
        nextBodyPanel.addView(mNextGameBody);

        mCurrentGameBody = new BodyView(this);
        RelativeLayout currentBodyPanel = (RelativeLayout) findViewById(R.id.current_view_pl);
        currentBodyPanel.invalidate();
        currentBodyPanel.addView(mCurrentGameBody);
    }

    private void initView() {
        mGameTime = (TextView) findViewById(R.id.game_time);
        mGameScore = (TextView) findViewById(R.id.game_score);

        mOptionLeft = (Button) findViewById(R.id.op_left);
        mOptionLeft.setOnClickListener(this);

        mOptionDown = (Button) findViewById(R.id.op_down);
        mOptionDown.setOnClickListener(this);

        mOptionRight = (Button) findViewById(R.id.op_right);
        mOptionRight.setOnClickListener(this);

        mOptionRotate = (Button) findViewById(R.id.op_rotate);
        mOptionRotate.setOnClickListener(this);
    }

    private Handler timeUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIME_UPDATE) {
                currentTime++;
                mGameTime.setText(TimeUitl.intToTime(currentTime));
                if (currentTime == 3600) {
                    timeUpdateHandler.removeMessages(TIME_UPDATE);
                }
                timeUpdateHandler.sendEmptyMessageDelayed(TIME_UPDATE, DELAY_TIME);
            }
            super.handleMessage(msg);
        }
    };

    private Handler viewUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == VIEW_UPDATE) {
                mGameView.updateView();
            }
            viewUpdateHandler.sendEmptyMessageDelayed(VIEW_UPDATE, DELAY_TIME);
            super.handleMessage(msg);
        }
    };

    private void updateView() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                timeUpdateHandler.sendEmptyMessageDelayed(TIME_UPDATE, DELAY_TIME);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mGameView.initGameView();
                viewUpdateHandler.sendEmptyMessageDelayed(VIEW_UPDATE, DELAY_TIME);
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.op_left:
                mGameView.clearItems();
                mGameView.moveGameBody(Style.TOLEFT);
                mGameView.invalidate();
                break;
            case R.id.op_down:
                mGameView.clearItems();
                mGameView.moveGameBody(Style.TODOWN);
                mGameView.invalidate();
                break;
            case R.id.op_right:
                mGameView.clearItems();
                mGameView.moveGameBody(Style.TORIGHT);
                mGameView.invalidate();
                break;
            case R.id.op_rotate:
                mGameView.clearItems();
                mGameView.rotateGameBody();
                mGameView.invalidate();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (v.getId() == R.id.op_down) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mGameView.setDownStep(3);
                    mGameView.clearItems();
                    mGameView.moveGameBody(Style.TODOWN);
                    mGameView.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    mGameView.setDownStep(1);
                    break;
            }
            return true;
        }
        return false;

    }
}
