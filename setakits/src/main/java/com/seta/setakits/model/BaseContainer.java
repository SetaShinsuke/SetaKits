package com.seta.setakits.model;

import com.seta.setakits.db.DAOHelpable;
import com.seta.setakits.db.DBable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SETA_WORK on 2016/11/17.
 */

public class BaseContainer<T extends DBable> {
    private HashMap<String,T> objectHashMap = new HashMap<>();
    private DAOHelpable<T> mDAOHelpable;

    public BaseContainer(DAOHelpable<T> daoHelpable){
        this.mDAOHelpable = daoHelpable;
    }

    /**
     * 把{@link T}对象存在内存;
     *
     * @param id 某类型id;
     * @param object 某类型对象;
     */
    public void put(String id,T object){
        objectHashMap.put(id,object);
//        if(id!=null && object!=null && object.getDescription()!=null){
//            T emo = desIdMap.get(object.getDescription());
//            desIdMap.put(object.getDescription(),object);
//        }
    }

    /**
     * 通过id获取内存中的{@link T};
     *
     * @param id 某类型id;
     * @return 某类型对象;
     */
    public T getUniqueTFromMem(String id){
        T object = objectHashMap.get(id);
        if(object==null){
            object = mDAOHelpable.buildEntity();
            object.setId(id);
            put(id, object);
        }
        return object;
    }

    /**
     * 将数据库中的某类型(全部)恢复到内存中
     */
    public void restore(){
        ArrayList<T> objects = mDAOHelpable.getHelper().findAll();
        for(T object:objects){
            put(object.getId(),object);
        }
    }

    /**
     * 把指定某类型存到数据库
     */
    public void updateTs2DB(final ArrayList<T> objects) {
        mDAOHelpable.getHelper().saveInTx(objects);
//        ThreadPoolManager.getDbThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                TDAO.saveInTx(objects);
//            }
//        });
    }
    /**
     * 把指定某类型存到数据库
     */
    public void updateTs2DB(T object) {
        ArrayList<T> objects = new ArrayList<>();
        objects.add(object);
        mDAOHelpable.getHelper().saveInTx(objects);
    }

    public HashMap<String,T> getAllTs(){
        return objectHashMap;
    }

    public void updateAll(ArrayList<T> objects){
        objectHashMap.clear();
        mDAOHelpable.getHelper().deleteAll();
        for(T object:objects){
            put(object.getId(),object);
        }
        updateTs2DB(objects);
    }
}
