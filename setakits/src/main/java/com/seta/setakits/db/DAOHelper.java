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

public class DAOHelper<T extends DBable> {
    private String tableName;
    private DAOHelpable<T> mDAOHelpable;

    public DAOHelper(String tableName, DAOHelpable<T> DAOHelpable) {
        this.tableName = tableName;
        this.mDAOHelpable = DAOHelpable;
    }

    /**
     * 通过 id 获取唯一的 {@link T} 对象
     * 查找完成后自动关闭数据库
     */
    public T getUniqueObjById(String uid) {
        if (uid == null) {
            return null;
        }
        T obj = mDAOHelpable.buildUniqueById(uid);
        T objDB = findObjById(uid);
        if (objDB == null) {
            saveOne(obj);
        }
        return obj;
    }

    private T findObjById(String id) {
        List<T> list = find("UID=?", new String[]{String.valueOf(id)}, null, null, "1", false);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    /**
     * 从数据库中查找
     */
    private ArrayList<T> find(String whereClause, String[] whereArgs,
                              String groupBy, String orderBy, String limit, boolean doClose) {
        SQLiteDatabase sqLiteDatabase = mDAOHelpable.getDB().getWritableDatabase();
        T entity;
        java.util.ArrayList<T> toRet = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(tableName, null,
                whereClause, whereArgs, groupBy, null, orderBy, limit);
        try {
            while (c.moveToNext()) {
                entity = mDAOHelpable.buildEntity();
                mDAOHelpable.inflate(entity, c);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
            if (doClose) {
                sqLiteDatabase.close();
            }
        }
        return toRet;
    }

    public ArrayList<T> findAll() {
        return find(null, null, null, null, null, true);
    }

    private boolean saveByHelper(T obj, SQLiteDatabase db) {
        ContentValues values = mDAOHelpable.getValues(obj);
        long ret;
        //如果数据库中已经有该id对应的数据，则进行update.否则insert.
        T inoutDb = findObjById(obj.getId());
        if (inoutDb != null) {
            obj.setDbId(inoutDb.getDbId());
        } else {
            obj.setDbId(null);
        }

        if (obj.getDbId() == null) {
            ret = db.insert(tableName, null, values);
            obj.setDbId(ret);
        } else {
            ret = db.update(tableName, values, "ID = ?", new String[]{String.valueOf(obj.getDbId())});
        }

        return ret > 0;
    }

    public boolean saveOne(final T obj) {
        boolean ret = false;
        try {
            SQLiteDatabase db = mDAOHelpable.getDB().getWritableDatabase();
            ret = saveByHelper(obj, db);
            db.close();
        } catch (Exception e) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Get new entry
                    SQLiteDatabase db = mDAOHelpable.getDB().getWritableDatabase();
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
    public void saveInTx(Collection<T> objects) {
        SQLiteDatabase sqLiteDatabase = mDAOHelpable.getDB().getWritableDatabase();
        try {
            sqLiteDatabase.beginTransaction();
            for (T object : objects) {
                saveByHelper(object, sqLiteDatabase);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            LogX.e(getClass().getName() + " Error in saving inouts in transaction " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = mDAOHelpable.getDB().getWritableDatabase();
        sqLiteDatabase.delete(tableName, null, null);
        sqLiteDatabase.close();
    }
}
