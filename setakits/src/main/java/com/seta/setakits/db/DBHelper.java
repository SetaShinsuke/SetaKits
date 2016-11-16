package com.seta.setakits.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.seta.setakits.Constants;
import com.seta.setakits.logs.LogX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by SETA on 2016/3/13.
 * A helper class to manage database creation and version management.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 0;
    private static final boolean debugEnabled = false;
    private String dbFileName , dbBkFileName;

    private Context context;

    public DBHelper(Context context){
        super(context, Constants.DATABASE_NAME , new CursorFactory(debugEnabled), DATABASE_VERSION );
        this.context = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTable( SQLiteDatabase db){
    }

    //调试用
    public void export(){
        LogX.d("Export Database.");
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + context.getPackageName() + "/databases/" + Constants.DATABASE_NAME;
        String backupDBPath = Constants.DATABASE_NAME_BK;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
