package com.seta.setakits;

import com.seta.setakits.logs.LogX;

/**
 * Created by SETA on 2015/10/7.
 */
public class CodeTimer {
    public static final String TAG_CODE_TIMER = "codeTimer";

    /**
     * Version 1 : 每个tag对应一个对象
     **/
    private long start = 0;
    public void start(String tag){
        this.start = System.currentTimeMillis();
        LogX.d( TAG_CODE_TIMER ,tag + " starts.");
    }
    public long end(String tag){
        long end = System.currentTimeMillis();
        long time = end - start;
        String s = time/1000 + "";
        time = time%1000;   //秒
        s = s + "." + time/100; //0.1秒
        time = time%100;
        s = s + time/10 + time%10 + "s";    //0.01~0.001秒

        LogX.d( TAG_CODE_TIMER ,tag + " ends : " + s);
        return time;
    }



    /**
     * Version 2 : 只需要创建一个对象（有可能对象不销毁?）
     **/
//    private HashMap<String,Long> records = new HashMap<>();
//    public void start(String tag){
//        records.put( tag , System.currentTimeMillis() );
////        this.start = System.currentTimeMillis();
//        Logger.d("timer",tag + " starts.");
//    }
//    public long end(String tag){
//        long end = System.currentTimeMillis();
//        long time = end - records.get(tag);
//        Logger.d("timer",tag + " ends : " + (time / 1000) + "." + (time % 1000) + "s");
//        return time;
//    }
}
