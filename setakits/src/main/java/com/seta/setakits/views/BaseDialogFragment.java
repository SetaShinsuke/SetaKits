package com.seta.setakits.views;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.seta.setakits.logs.LogX;

/**
 * Created by Seta.Driver on 2016/11/19.
 */

public class BaseDialogFragment extends DialogFragment{
    private Context reshowContex;

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        }catch (Exception e){
            LogX.e(getClass().getName() + "" + e);
            try {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add( this,tag );
                transaction.commitAllowingStateLoss();
            }catch (Exception e2){
                LogX.e( getClass().getName() + "" + e2);
            }
        }
    }

    public void show(Context context){
        if(context==null || !(context instanceof FragmentActivity) ){
            return;
        }
        try {
            this.show(((FragmentActivity)context).getSupportFragmentManager() , "");
            this.reshowContex = context;
        }catch (Exception e){
            LogX.e(getClass().getName() + e);
            FragmentTransaction transaction
                    = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            transaction.add( this,"base_dialog" );
            transaction.commitAllowingStateLoss();
        }
    }

}
