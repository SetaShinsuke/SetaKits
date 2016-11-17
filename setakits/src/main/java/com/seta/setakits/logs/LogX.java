package com.seta.setakits.logs;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.seta.setakits.Constants;

/**
 * Created by SETA_WORK on 2016/10/28.
 */

public class LogX {

    private static org.apache.log4j.Logger logger=null;
    public static int logLevel = Log.VERBOSE;
    public static int logFileLevel = Log.INFO;

    private static String logTag = Constants.LOG_TAG_S;

    public static void init(Context context , String logFileName){
        LogConfig.configure(context , logFileName);
        logger = org.apache.log4j.Logger.getLogger("Log");
        logger.trace("Log Init");
        boolean isDebuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (isDebuggable) {
            LogX.logLevel = Log.VERBOSE;
        } else {
            LogX.logLevel = Log.WARN;
        }
    }

    public static void setTag(String tag){
        logTag = tag;
    }

    private static String getTag(){
        return logTag;
    }

    public static void setLogLevel(int level , int fileLevel){
        logLevel = level;
        logFileLevel = fileLevel;
    }

    public static void fastLog( String s){
        Log.v(getTag(), "" + s );
    }

    public static void fastLog(String content,Object... args){
        try {
            fastLog(String.format(content,args));
        }catch (Exception e){
            fastLog(content);
        }
    }

    public static void v(String s){
        v(getTag(),s);
    }
    public static void v(String tag , String s){
        if(logLevel<=Log.VERBOSE) {
            Log.v(tag,s);
            if(logFileLevel<=Log.VERBOSE) {
                trace2File(tag, s);
            }
        }
    }

    /** Log分级
     * @param s
     */
    public static void e( String s){
        e(getTag(), s);
    }
    public static void e( String tag , String s){
        if(logLevel<=Log.ERROR) {
            Log.e(tag , s);
            if(logFileLevel<=Log.ERROR) {
                trace2File("Error ==============================\n" + tag,s);
            }
        }
    }

    public static void e(String tag,String s,Throwable t){
        if(logLevel<=Log.ERROR) {
            Log.e(tag , s , t);
            if(logFileLevel<=Log.ERROR) {
                trace2File("Error ==============================\n" + tag,s);
            }
        }
    }

    public static void d( String s){
        d(getTag(), s);
    }
    public static void d( String tag , String s){
        if(logLevel<=Log.DEBUG) {
            Log.d(tag, s);
            if(logFileLevel<=Log.DEBUG) {
                trace2File(tag,s);
            }
        }
    }

    public static void i( String s){
        i(getTag(), s );
    }
    public static void i( String tag , String s){
        if(logLevel<=Log.INFO) {
            Log.i(tag, s);
            if(logFileLevel<=Log.INFO) {
                trace2File(tag,s);
            }
        }
    }

    public static void w( String s){
        w(getTag(), s);
    }

    public static void w( String tag , String s){
        if(logLevel<=Log.WARN) {
            Log.w(tag, s);
            if(logFileLevel<=Log.WARN) {
                trace2File(tag,s);
            }
        }
    }

    public static void w(String tag,String s,Throwable t){
        if(logLevel<=Log.WARN) {
            Log.w(tag,s,t);
            if(logFileLevel<=Log.WARN) {
                trace2File(tag,s);
            }
        }
    }

    private static void trace2File(String tag,String msg){
        if(logger!=null) {
            logger.trace(tag + " " + msg);
        }
    }

    private static final String TTag = "tmp";
    public static void tLog(String msg){
        v(TTag,msg);
    }
}
