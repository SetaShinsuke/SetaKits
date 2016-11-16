package com.seta.setakits.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.seta.setakits.logs.LogX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by SETA_WORK on 2016/11/16.
 */

public class BaseDAOHelper<T extends DBable> {
    private String tableName;
    private DAOHelpable<T> mDAOHelpable;

    public BaseDAOHelper(String name ,DAOHelpable<T> DAOHelpable){
        this.tableName = name;
        this.mDAOHelpable = DAOHelpable;
    }

    public T findObjById( SQLiteDatabase database , String id , boolean doClose){
        T emptyObj = mDAOHelpable.buildEntity();
        List<T> list = find(database ,
                                "UID=?", new String[]{String.valueOf(id)}, null, null, "1", false);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    /**
     * 从数据库中查找
     */
    private ArrayList<T> find(SQLiteDatabase sqLiteDatabase ,
                                String whereClause, String[] whereArgs,
                                String groupBy, String orderBy, String limit , boolean doClose) {
//        SQLiteDatabase sqLiteDatabase = KApi.getApi().getDbHelper().getReadableDatabase();
        T entity;
        java.util.ArrayList<T> toRet = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(tableName, null,
                whereClause, whereArgs, groupBy, null, orderBy, limit);
        try {
            while (c.moveToNext()) {
                entity = mDAOHelpable.buildEntity();
                mDAOHelpable.inflate(entity,c);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            if(doClose) {
                sqLiteDatabase.close();
            }
        }
        return toRet;
    }

    public ArrayList<T> findAll(SQLiteDatabase sqLiteDatabase){
        return find(sqLiteDatabase , null , null, null, null, null , true);
    }

    private boolean saveByHelper(T obj, SQLiteDatabase db) {
        ContentValues values = mDAOHelpable.getValues(obj);
        long ret;
        //如果数据库中已经有该id对应的数据，则进行update.否则insert.
        T inoutDb = findObjById(db , obj.getId(), false);
        if( inoutDb!=null){
            obj.setDbId( inoutDb.getDbId() );
        }else {
            obj.setDbId(null);
        }

        if (obj.getDbId() == null ) {
            ret = db.insert(tableName, null, values);
            obj.setDbId( ret );
        } else {
            ret = db.update(tableName, values, "ID = ?", new String[]{String.valueOf(obj.getDbId())});
        }

        return ret > 0;
    }

    public boolean saveOne(final T obj ,final DBHelper dbHelper) {
        boolean ret = false;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ret = saveByHelper(obj, db);
            db.close();
        } catch (Exception e) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Get new entry
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    saveByHelper(obj, db);
                    db.close();
                }
            }, 100);
        }
        return ret;
    }

    /**
     * 批量保存
     */
    public void saveInTx(Collection<T> objects , final DBHelper dbHelper , boolean doClose) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        try{
            sqLiteDatabase.beginTransaction();
            for(T object: objects){
                saveByHelper(object,sqLiteDatabase);
            }
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            LogX.e(getClass().getName() + " Error in saving inouts in transaction " + e.getMessage());
        }finally {
            sqLiteDatabase.endTransaction();
            if(doClose) {
                sqLiteDatabase.close();
            }
        }
    }
}
