package com.seta.setakits.logs;

import android.content.Context;
import android.os.Environment;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

import static com.seta.setakits.Constants.LOG_TAG_S;

public class LogConfig {


    static private String fileName = "peep_log.log";

    private long maxFileSize = 1024 * 1024;
    public static int level = 1;
    public static int V = 1;
    public static int D = 2;
    public static int I = 3;
    public static int W = 4;
    public static int E = 5;

    public static void configure(Context context) {
        final LogConfigurator logConfigurator = new LogConfigurator();
//        LogX.v("hehe","getStorageState() : " + getStorageState());
        if (!getStorageState()) {
            return;
        }
        try {
            File dir = context.getExternalFilesDir(null);
            if (dir == null) {
                throw new NullPointerException("Logger初始化失败,file dir为空!");
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dir = dir.getParentFile();
            logConfigurator.setFileName(dir.getAbsolutePath() + File.separator + fileName);
            LogX.v(LOG_TAG_S, "Log fold : " + dir.getAbsolutePath() + File.separator + fileName);
            logConfigurator.setRootLevel(i2L(level));
            logConfigurator.setUseLogCatAppender(false);
            // Set log level of a specific logger
            logConfigurator.setLevel("org.apache", Level.ERROR);
            logConfigurator.configure();
        } catch (Exception e) {
            LogX.e(LOG_TAG_S, "Logger初始化失败!" + e);
        }

    }

    private static Level i2L(int l) {
        switch (l) {
            case 1:
                return Level.TRACE;
            case 2:
                return Level.DEBUG;
            case 3:
                return Level.INFO;
            case 4:
                return Level.WARN;
            case 5:
                return Level.ERROR;

        }
        return null;
    }

    private static boolean getStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
//        return false;
        return false;
    }

}