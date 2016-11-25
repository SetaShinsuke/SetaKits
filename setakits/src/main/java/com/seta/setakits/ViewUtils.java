package com.seta.setakits;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.seta.setakits.logs.LogX;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by SETA_WORK on 2016/11/25.
 */

public class ViewUtils {

    public static void showToast(Context context , String content){
        showToast(context,content,false);
    }

    public static void showToast(Context context , String content , boolean showLong){
        if(showLong){
            Toast.makeText(context,content,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * 隐藏输入法
     **/
    public static void hideInputMethod(View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            view.clearFocus();
//            inputManager.he
        } catch (Exception e) {
            e.getStackTrace();
            LogX.w("Hide Input Method Error.Detail : " + e.toString());
        }
    }
}
