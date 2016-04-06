package com.quicksand.tetrisgame.state;

/**
 * Created by shamaker on 16/3/30.
 */
public class Style {
    public static final int DEFAULT =0;
    public static final int S = 1;
    public static final int Z = 2;
    public static final int L = 3;
    public static final int J = 4;
    public static final int I = 5;
    public static final int O = 6;
    public static final int T = 7;

    public static final int NORMAL = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int RESERVE = 3;

    public static final int TODOWN = 1;
    public static final int TOLEFT = 2;
    public static final int TORIGHT = 3;

    public static final int CURRENT_BODY_UPDATE = 0x1101;
    public static final int NEXT_BODY_UPDATE = 0x1102;
    public static final int GAME_OVER = 0x1103;
}
