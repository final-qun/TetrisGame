package com.quicksand.tetrisgame.util;

/**
 * Created by xiqun on 16/3/29.
 */
public class TimeUitl {
    public static String intToTime(int time){
        if(time >= 60 && time <3600){
            int min = time / 60;
            int sec = time % 60;
            if(min >= 10){
                if(sec >= 10){
                    return ""+min+":"+sec;
                }else{
                    return ""+min+":0"+sec;
                }
            }else{
                if(sec >= 10){
                    return "0"+min+":"+sec;
                }else{
                    return "0"+min+":0"+sec;
                }
            }
        } else if(time < 60){
            if(time >= 10){
                return "00:"+time;
            }else{
                return "00:0"+time;
            }
        } else {
            return "time over";
        }
    }
}
