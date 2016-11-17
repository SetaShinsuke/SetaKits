package com.seta.setakits.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by SETA_WORK on 2016/11/16.
 */

public interface DAOHelpable<T extends DBable> {
    T buildEntity();
    T buildUniqueById(String id);
    void inflate(T entity , Cursor cursor);
//    boolean saveByHelper(T obj , SQLiteDatabase database);
    ContentValues getValues(T obj);
    BaseSQLiteHelper getDB();
    DAOHelper<T> getHelper();
}
