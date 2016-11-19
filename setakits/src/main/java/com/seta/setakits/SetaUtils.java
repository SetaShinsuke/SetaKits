package com.seta.setakits;

import android.content.Context;
import android.content.Loader;

import com.seta.setakits.logs.LogX;

/**
 * Created by Seta.Driver on 2016/11/19.
 */

public class SetaUtils {
    private Context mAppContext;
    private static SetaUtils sSUApi;

    private SetaUtils(){

    }

    public static SetaUtils getInstance(){
        if(sSUApi==null){
            sSUApi = new SetaUtils();
        }
        return sSUApi;
    }

    public static void init(Context context){
        getInstance().mAppContext = context;
    }

    public static String getResString(int id){
        if(getInstance().mAppContext==null){
            SetaKitsException exception = new SetaKitsException("Error when using SetaUtils.getResString() : AppContext is null !");
            LogX.e(Constants.LOG_TAG_SETA_UTILS , exception.toString());
            throw exception;
        }
        return getInstance().mAppContext.getString(id);
    }
}
