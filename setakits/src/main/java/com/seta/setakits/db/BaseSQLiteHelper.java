package com.seta.setakits.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.seta.setakits.logs.LogX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static com.seta.setakits.logs.LogX.fastLog;

/**
 * Created by SETA on 2016/3/13.
 * A helper class to manage database creation and mVersion management.
 */
public abstract class BaseSQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private String mDatabaseName;
    private int mVersion;

    public BaseSQLiteHelper(Context context, String databaseName, boolean debugEnabled, int dbVersion) {
        super(context, databaseName, new CursorFactory(debugEnabled), dbVersion);
        construct(context, databaseName, dbVersion);
    }

    public BaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        construct(context, name, version);
    }

    public BaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        construct(context, name, version);
    }

    private void construct(Context context, String name, int version) {
        this.context = context;
        this.mDatabaseName = name;
        this.mVersion = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        fastLog("oncreate");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        fastLog("onupdate");
        updateTable(db,oldVersion,newVersion);
    }

    public abstract void createTable(SQLiteDatabase db);

    public abstract void updateTable(SQLiteDatabase db, int oldVersion, int newVersion);


    //调试用
    public void export() {
        LogX.d("Export Database.");
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + mDatabaseName;
        String backupDBPath = "bk_" + mDatabaseName;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        LogX.d("db path : " + currentDBPath + " || Backup path : " + backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
            LogX.e("Error when exporting database : " + e);
        }
    }
}
