package com.seta.setakits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SETA_WORK on 2016/11/10.
 */

public class Constants {

    public static String LOG_TAG_S = "seta";

    public static class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }
}
